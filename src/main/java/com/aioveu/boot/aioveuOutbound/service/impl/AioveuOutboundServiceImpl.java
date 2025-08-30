package com.aioveu.boot.aioveuOutbound.service.impl;

import com.aioveu.boot.aioveuDepartment.model.entity.AioveuDepartment;
import com.aioveu.boot.aioveuDepartment.service.AioveuDepartmentService;
import com.aioveu.boot.aioveuDepartment.service.impl.DepartmentNameSetter;
import com.aioveu.boot.aioveuEmployee.model.entity.AioveuEmployee;
import com.aioveu.boot.aioveuEmployee.service.AioveuEmployeeService;
import com.aioveu.boot.aioveuEmployee.service.impl.EmployeeNameSetter;
import com.aioveu.boot.aioveuEmployee.service.impl.NameValidator;
import com.aioveu.boot.aioveuMaterial.model.entity.AioveuMaterial;
import com.aioveu.boot.aioveuMaterial.model.vo.AioveuMaterialVO;
import com.aioveu.boot.aioveuMaterial.service.AioveuMaterialService;
import com.aioveu.boot.aioveuMaterial.service.impl.MaterialNameSetter;
import com.aioveu.boot.aioveuPerformance.model.vo.AioveuPerformanceVO;
import com.aioveu.boot.aioveuWarehouse.model.entity.AioveuWarehouse;
import com.aioveu.boot.aioveuWarehouse.model.form.AioveuWarehouseForm;
import com.aioveu.boot.aioveuWarehouse.service.AioveuWarehouseService;
import com.aioveu.boot.aioveuWarehouse.service.impl.WarehouseNameSetter;
import com.aliyun.oss.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aioveu.boot.aioveuOutbound.mapper.AioveuOutboundMapper;
import com.aioveu.boot.aioveuOutbound.service.AioveuOutboundService;
import com.aioveu.boot.aioveuOutbound.model.entity.AioveuOutbound;
import com.aioveu.boot.aioveuOutbound.model.form.AioveuOutboundForm;
import com.aioveu.boot.aioveuOutbound.model.query.AioveuOutboundQuery;
import com.aioveu.boot.aioveuOutbound.model.vo.AioveuOutboundVO;
import com.aioveu.boot.aioveuOutbound.converter.AioveuOutboundConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import cn.idev.excel.util.StringUtils;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
/**
 * 出库记录服务实现类
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:26
 */


@Slf4j
@Service
@RequiredArgsConstructor
public class AioveuOutboundServiceImpl extends ServiceImpl<AioveuOutboundMapper, AioveuOutbound> implements AioveuOutboundService {

    private final AioveuOutboundConverter aioveuOutboundConverter;

    @Autowired
    private AioveuEmployeeService aioveuEmployeeService;

    @Autowired
    private AioveuWarehouseService aioveuWarehouseService;

    @Autowired
    private AioveuMaterialService aioveuMaterialService;

    @Autowired
    private AioveuDepartmentService aioveuDepartmentService;

    /**
    * 获取出库记录分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AioveuOutboundVO>} 出库记录分页列表
    */
    @Override
    public IPage<AioveuOutboundVO> getAioveuOutboundPage(AioveuOutboundQuery queryParams) {


        // 处理物资名称映射
        if (StringUtils.isNotBlank(queryParams.getMaterialName())) {
            Long materialId = aioveuMaterialService.getIdByName(queryParams.getMaterialName());
            queryParams.setMaterialId(materialId);
        }

        // 处理仓库名称映射
        if (StringUtils.isNotBlank(queryParams.getWarehouseName())) {
            Long warehouseId = aioveuWarehouseService.getIdByName(queryParams.getWarehouseName());
            queryParams.setWarehouseId(warehouseId);
        }

        // 处理领用人名称映射
        if (StringUtils.isNotBlank(queryParams.getRecipientName())) {
            Long employeeId = aioveuEmployeeService.getIdByName(queryParams.getRecipientName());
            queryParams.setRecipientId(employeeId);
        }


        Page<AioveuOutboundVO> pageVO = this.baseMapper.getAioveuOutboundPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );

        setOperatorNames(pageVO.getRecords());

        setRecipientNames(pageVO.getRecords());

        setWarehouseNames(pageVO.getRecords());

        setMaterialNames(pageVO.getRecords());

        DepartmentNameSetter.setDepartmentNames(
                pageVO.getRecords() ,
                AioveuOutboundVO::getDepartmentId, // 获取ID
                AioveuOutboundVO::setDepartmentName, // 设置姓名
                aioveuDepartmentService
        );


        return pageVO;
    }
    
    /**
     * 获取出库记录表单数据
     *
     * @param id 出库记录ID
     * @return 出库记录表单数据
     */
    @Override
    public AioveuOutboundForm getAioveuOutboundFormData(Long id) {
        // 1. 根据ID获取实体信息
        AioveuOutbound entity = this.getById(id);
        if (entity == null) {
            throw new ServiceException("不存在");
        }
        // 2. 将实体转换为表单对象
        AioveuOutboundForm form = aioveuOutboundConverter.toForm(entity);

        // 3. 处理映射信息（如果存在）
        if (entity.getMaterialId() != null) {
            LambdaQueryWrapper<AioveuMaterial> Wrapper = new LambdaQueryWrapper<>();
            Wrapper.eq(AioveuMaterial::getId, entity.getMaterialId())
                    .select(AioveuMaterial::getName); // 只选择需要的字段

            AioveuMaterial material = aioveuMaterialService.getOne(Wrapper);

            if (material != null) {
                form.setMaterialName(material.getName());
            }
        }

        if (entity.getWarehouseId() != null) {
            LambdaQueryWrapper<AioveuWarehouse> Wrapper = new LambdaQueryWrapper<>();
            Wrapper.eq(AioveuWarehouse::getId, entity.getWarehouseId())
                    .select(AioveuWarehouse::getName); // 只选择需要的字段

            AioveuWarehouse warehouse = aioveuWarehouseService.getOne(Wrapper);

            if (warehouse != null) {
                form.setWarehouseName(warehouse.getName());
            }
        }

        if (entity.getOperatorId() != null) {
            LambdaQueryWrapper<AioveuEmployee> Wrapper = new LambdaQueryWrapper<>();
            Wrapper.eq(AioveuEmployee::getEmployeeId, entity.getOperatorId())
                    .select(AioveuEmployee::getName); // 只选择需要的字段

            AioveuEmployee employee = aioveuEmployeeService.getOne(Wrapper);

            if (employee != null) {
                form.setOperatorName(employee.getName());
            }
        }

        if (entity.getRecipientId() != null) {
            LambdaQueryWrapper<AioveuEmployee> Wrapper = new LambdaQueryWrapper<>();
            Wrapper.eq(AioveuEmployee::getEmployeeId, entity.getRecipientId())
                    .select(AioveuEmployee::getName); // 只选择需要的字段

            AioveuEmployee employee = aioveuEmployeeService.getOne(Wrapper);

            if (employee != null) {
                form.setRecipientName(employee.getName());
            }
        }

        if (entity.getDepartmentId() != null) {
            LambdaQueryWrapper<AioveuDepartment> Wrapper = new LambdaQueryWrapper<>();
            Wrapper.eq(AioveuDepartment::getDeptId, entity.getDepartmentId())
                    .select(AioveuDepartment::getDeptName); // 只选择需要的字段

            AioveuDepartment department = aioveuDepartmentService.getOne(Wrapper);

            if (department != null) {
                form.setDepartmentName(department.getDeptName());
            }
        }

        return form;
    }
    
    /**
     * 新增出库记录
     *
     * @param formData 出库记录表单对象
     * @return 是否新增成功
     */
    @Override
    public boolean saveAioveuOutbound(AioveuOutboundForm formData) {
        // 字段1：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuOutboundForm::getMaterialName,
                AioveuMaterial::getName,
                AioveuOutboundForm::setMaterialId,
                AioveuMaterial::getId,
                aioveuMaterialService,
                "物资： "
        );

        // 字段2：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuOutboundForm::getWarehouseName,
                AioveuWarehouse::getName,
                AioveuOutboundForm::setWarehouseId,
                AioveuWarehouse::getId,
                aioveuWarehouseService,
                "仓库： "
        );

        // 字段3：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuOutboundForm::getOperatorName,
                AioveuEmployee::getName,
                AioveuOutboundForm::setOperatorId,
                AioveuEmployee::getEmployeeId,
                aioveuEmployeeService,
                "操作员： "
        );

        // 字段4：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuOutboundForm::getRecipientName,
                AioveuEmployee::getName,
                AioveuOutboundForm::setRecipientId,
                AioveuEmployee::getEmployeeId,
                aioveuEmployeeService,
                "领用人： "
        );

        // 字段5：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuOutboundForm::getDepartmentName,
                AioveuDepartment::getDeptName,
                AioveuOutboundForm::setDepartmentId,
                AioveuDepartment::getDeptId,
                aioveuDepartmentService,
                "领用部门： "
        );

        AioveuOutbound entity = aioveuOutboundConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新出库记录
     *
     * @param id   出库记录ID
     * @param formData 出库记录表单对象
     * @return 是否修改成功
     */
    @Override
    public boolean updateAioveuOutbound(Long id,AioveuOutboundForm formData) {

        // 1. 验证ID对应的记录是否存在
        AioveuOutbound original = getById(id);
        if (original == null) {
            log.error("记录不存在: ID={}", id);
            throw new ServiceException("记录不存在");

        }

        // 字段1：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuOutboundForm::getMaterialName,
                AioveuMaterial::getName,
                AioveuOutboundForm::setMaterialId,
                AioveuMaterial::getId,
                aioveuMaterialService,
                "物资： "
        );

        // 字段2：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuOutboundForm::getWarehouseName,
                AioveuWarehouse::getName,
                AioveuOutboundForm::setWarehouseId,
                AioveuWarehouse::getId,
                aioveuWarehouseService,
                "仓库： "
        );

        // 字段3：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuOutboundForm::getOperatorName,
                AioveuEmployee::getName,
                AioveuOutboundForm::setOperatorId,
                AioveuEmployee::getEmployeeId,
                aioveuEmployeeService,
                "操作员： "
        );

        // 字段4：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuOutboundForm::getRecipientName,
                AioveuEmployee::getName,
                AioveuOutboundForm::setRecipientId,
                AioveuEmployee::getEmployeeId,
                aioveuEmployeeService,
                "领用人： "
        );

        // 字段5：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuOutboundForm::getDepartmentName,
                AioveuDepartment::getDeptName,
                AioveuOutboundForm::setDepartmentId,
                AioveuDepartment::getDeptId,
                aioveuDepartmentService,
                "领用部门： "
        );

        // 2. 将表单数据转换为实体对象
        AioveuOutbound entity = aioveuOutboundConverter.toEntity(formData);

        // 3. 设置ID
        entity.setId(id);

        // 4. 复制未修改的字段,保留审计字段(创建时间不变，更新时间用当前时间)
        entity.setCreateTime(original.getCreateTime());

        // 5. 执行更新
        return this.updateById(entity);
    }
    
    /**
     * 删除出库记录
     *
     * @param ids 出库记录ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    @Override
    public boolean deleteAioveuOutbounds(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的出库记录数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

    /**
     * 批量设置名称到VO对象，将视图对象的员工id,转换为员工姓名
     */
    private void setOperatorNames(List<AioveuOutboundVO> outboundVOS) {
        EmployeeNameSetter.setEmployeeNames(
                outboundVOS,
                AioveuOutboundVO::getOperatorId, // 获取ID
                AioveuOutboundVO::setOperatorName, // 设置姓名
                aioveuEmployeeService
        );
    }

    /**
     * 批量设置名称到VO对象，将视图对象的员工id,转换为员工姓名
     */
    private void setRecipientNames(List<AioveuOutboundVO> outboundVOS) {
        EmployeeNameSetter.setEmployeeNames(
                outboundVOS,
                AioveuOutboundVO::getRecipientId, // 获取ID
                AioveuOutboundVO::setRecipientName, // 设置姓名
                aioveuEmployeeService
        );
    }




    /**
     * 批量设置名称到VO对象，将视图对象的仓库id,转换为仓库姓名
     */
    private void setWarehouseNames(List<AioveuOutboundVO> outboundVOS) {
        WarehouseNameSetter.setWarehouseNames(
                outboundVOS ,
                AioveuOutboundVO::getWarehouseId, // 获取仓库ID
                AioveuOutboundVO::setWarehouseName, // 设置仓库姓名
                aioveuWarehouseService
        );
    }




    /**
     * 批量设置名称到VO对象，将视图对象的物资id,转换为物资姓名
     */
    private void setMaterialNames(List<AioveuOutboundVO> outboundVOS) {
        MaterialNameSetter.setMaterialNames(
                outboundVOS,
                AioveuOutboundVO::getMaterialId, // 获取ID
                AioveuOutboundVO::setMaterialName, // 设置姓名
                aioveuMaterialService
        );
    }
}
