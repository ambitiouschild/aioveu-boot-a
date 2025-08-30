package com.aioveu.boot.aioveuInbound.service.impl;

import cn.idev.excel.util.StringUtils;
import com.aioveu.boot.aioveuDepartment.model.entity.AioveuDepartment;
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
import com.aioveu.boot.aioveuInbound.mapper.AioveuInboundMapper;
import com.aioveu.boot.aioveuInbound.service.AioveuInboundService;
import com.aioveu.boot.aioveuInbound.model.entity.AioveuInbound;
import com.aioveu.boot.aioveuInbound.model.form.AioveuInboundForm;
import com.aioveu.boot.aioveuInbound.model.query.AioveuInboundQuery;
import com.aioveu.boot.aioveuInbound.model.vo.AioveuInboundVO;
import com.aioveu.boot.aioveuInbound.converter.AioveuInboundConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
/**
 * 入库信息服务实现类
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 22:18
 */


@Slf4j
@Service
@RequiredArgsConstructor
public class AioveuInboundServiceImpl extends ServiceImpl<AioveuInboundMapper, AioveuInbound> implements AioveuInboundService {

    private final AioveuInboundConverter aioveuInboundConverter;

    @Autowired
    private AioveuEmployeeService aioveuEmployeeService;

    @Autowired
    private AioveuWarehouseService aioveuWarehouseService;

    @Autowired
    private AioveuMaterialService aioveuMaterialService;

    /**
    * 获取入库信息分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AioveuInboundVO>} 入库信息分页列表
    */
    @Override
    public IPage<AioveuInboundVO> getAioveuInboundPage(AioveuInboundQuery queryParams) {

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


        // 处理操作员名称映射
        if (StringUtils.isNotBlank(queryParams.getOperatorName())) {
            Long employeeId = aioveuEmployeeService.getIdByName(queryParams.getOperatorName());
            queryParams.setOperatorId(employeeId);
        }

        Page<AioveuInboundVO> pageVO = this.baseMapper.getAioveuInboundPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );

        // 设置员工名称
        setEmployeeNames(pageVO.getRecords());

        setWarehouseNames(pageVO.getRecords());

        setMaterialNames(pageVO.getRecords());

        return pageVO;
    }
    
    /**
     * 获取入库信息表单数据
     *
     * @param id 入库信息ID
     * @return 入库信息表单数据
     */
    @Override
    public AioveuInboundForm getAioveuInboundFormData(Long id) {
        // 1. 根据ID获取实体信息
        AioveuInbound entity = this.getById(id);
        if (entity == null) {
            throw new ServiceException("不存在");
        }
        // 2. 将实体转换为表单对象
        AioveuInboundForm form = aioveuInboundConverter.toForm(entity);

        // 3. 处理映射信息（如果存在）
        if (entity.getMaterialId() != null) {
            // 使用 MyBatis-Plus 的 LambdaQueryWrapper 查询信息
            // 创建一个 LambdaQueryWrapper 对象，用于构建查询条件
            // 泛型 <AioveuDepartment> 指定了查询的实体类型
            LambdaQueryWrapper<AioveuMaterial> Wrapper = new LambdaQueryWrapper<>();
            // 添加查询条件：部门ID等于指定值
            // AioveuDepartment::getDeptId 是方法引用，表示查询 dept_id 字段
            // entity.getDeptId() 是获取要查询的具体部门ID值
            Wrapper.eq(AioveuMaterial::getId, entity.getMaterialId())
                    // 指定只选择 dept_name 字段，而不是所有字段
                    // 这是一个性能优化，减少不必要的数据传输
                    .select(AioveuMaterial::getName); // 只选择需要的字段

            AioveuMaterial material = aioveuMaterialService.getOne(Wrapper);

            if (material != null) {
                form.setMaterialName(material.getName());
            }
        }

        if (entity.getWarehouseId() != null) {
            // 使用 MyBatis-Plus 的 LambdaQueryWrapper 查询信息
            // 创建一个 LambdaQueryWrapper 对象，用于构建查询条件
            // 泛型 <AioveuDepartment> 指定了查询的实体类型
            LambdaQueryWrapper<AioveuWarehouse> Wrapper = new LambdaQueryWrapper<>();
            // 添加查询条件：部门ID等于指定值
            // AioveuDepartment::getDeptId 是方法引用，表示查询 dept_id 字段
            // entity.getDeptId() 是获取要查询的具体部门ID值
            Wrapper.eq(AioveuWarehouse::getId, entity.getWarehouseId())
                    // 指定只选择 dept_name 字段，而不是所有字段
                    // 这是一个性能优化，减少不必要的数据传输
                    .select(AioveuWarehouse::getName); // 只选择需要的字段

            AioveuWarehouse warehouse = aioveuWarehouseService.getOne(Wrapper);

            if (warehouse != null) {
                form.setWarehouseName(warehouse.getName());
            }
        }

        if (entity.getOperatorId() != null) {
            // 使用 MyBatis-Plus 的 LambdaQueryWrapper 查询信息
            // 创建一个 LambdaQueryWrapper 对象，用于构建查询条件
            // 泛型 <AioveuDepartment> 指定了查询的实体类型
            LambdaQueryWrapper<AioveuEmployee> Wrapper = new LambdaQueryWrapper<>();
            // 添加查询条件：部门ID等于指定值
            // AioveuDepartment::getDeptId 是方法引用，表示查询 dept_id 字段
            // entity.getDeptId() 是获取要查询的具体部门ID值
            Wrapper.eq(AioveuEmployee::getEmployeeId, entity.getOperatorId())
                    // 指定只选择 dept_name 字段，而不是所有字段
                    // 这是一个性能优化，减少不必要的数据传输
                    .select(AioveuEmployee::getName); // 只选择需要的字段

            AioveuEmployee employee = aioveuEmployeeService.getOne(Wrapper);

            if (employee != null) {
                form.setOperatorName(employee.getName());
            }
        }

        return form;
    }
    
    /**
     * 新增入库信息
     *
     * @param formData 入库信息表单对象
     * @return 是否新增成功
     */
    @Override
    public boolean saveAioveuInbound(AioveuInboundForm formData) {

        // 字段1：检查编号是否唯一（对于不依赖外键的字段，不可重复）
        NameValidator.validateEntityUnique(
                formData,
                AioveuInboundForm::getInboundNo,
                AioveuInbound::getInboundNo,
                null,
                this,
                "入库单号："
        );
        // 字段2：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuInboundForm::getMaterialName,
                AioveuMaterial::getName,
                AioveuInboundForm::setMaterialId,
                AioveuMaterial::getId,
                aioveuMaterialService,
                "物资: "
        );
        // 字段3：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuInboundForm::getWarehouseName,
                AioveuWarehouse::getName,
                AioveuInboundForm::setWarehouseId,
                AioveuWarehouse::getId,
                aioveuWarehouseService,
                "仓库: "
        );
        // 字段4：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuInboundForm::getOperatorName,
                AioveuEmployee::getName,
                AioveuInboundForm::setOperatorId,
                AioveuEmployee::getEmployeeId,
                aioveuEmployeeService,
                "操作员: "
        );




        AioveuInbound entity = aioveuInboundConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新入库信息
     *
     * @param id   入库信息ID
     * @param formData 入库信息表单对象
     * @return 是否修改成功
     */
    @Override
    public boolean updateAioveuInbound(Long id,AioveuInboundForm formData) {

        // 1. 验证ID对应的记录是否存在
        AioveuInbound original = getById(id);
        if (original == null) {
            log.error("记录不存在: ID={}", id);
            throw new ServiceException("记录不存在");

        }

        // 字段2：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuInboundForm::getMaterialName,
                AioveuMaterial::getName,
                AioveuInboundForm::setMaterialId,
                AioveuMaterial::getId,
                aioveuMaterialService,
                "物资: "
        );
        // 字段3：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuInboundForm::getWarehouseName,
                AioveuWarehouse::getName,
                AioveuInboundForm::setWarehouseId,
                AioveuWarehouse::getId,
                aioveuWarehouseService,
                "仓库: "
        );
        // 字段4：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuInboundForm::getOperatorName,
                AioveuEmployee::getName,
                AioveuInboundForm::setOperatorId,
                AioveuEmployee::getEmployeeId,
                aioveuEmployeeService,
                "操作员: "
        );

        // 2. 将表单数据转换为实体对象
        AioveuInbound entity = aioveuInboundConverter.toEntity(formData);

        // 3. 设置ID
        entity.setId(id);

        // 4. 复制未修改的字段,保留审计字段(创建时间不变，更新时间用当前时间)
        entity.setCreateTime(original.getCreateTime());

        // 5. 执行更新
        return this.updateById(entity);
    }
    
    /**
     * 删除入库信息
     *
     * @param ids 入库信息ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    @Override
    public boolean deleteAioveuInbounds(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的入库信息数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

    /**
     * 批量设置名称到VO对象，将AioveuPerformanceVO绩效表视图对象的员工id,转换为员工姓名
     */
    private void setEmployeeNames(List<AioveuInboundVO> inboundVOS) {
        EmployeeNameSetter.setEmployeeNames(
                inboundVOS,
                AioveuInboundVO::getOperatorId, // 获取员工ID
                AioveuInboundVO::setOperatorName, // 设置员工姓名
                aioveuEmployeeService
        );
    }



    /**
     * 批量设置名称到VO对象，将视图对象的仓库id,转换为仓库姓名
     */

    private void setWarehouseNames(List<AioveuInboundVO> inboundVOS) {
        WarehouseNameSetter.setWarehouseNames(
                inboundVOS ,
                AioveuInboundVO::getWarehouseId, // 获取仓库ID
                AioveuInboundVO::setWarehouseName, // 设置仓库姓名
                aioveuWarehouseService
        );
    }




    /**
     * 批量设置名称到VO对象，将视图对象的物资id,转换为物资姓名
     */
    private void setMaterialNames(List<AioveuInboundVO> inboundVOS) {
        MaterialNameSetter.setMaterialNames(
                inboundVOS,
                AioveuInboundVO::getMaterialId, // 获取ID
                AioveuInboundVO::setMaterialName, // 设置姓名
                aioveuMaterialService
        );
    }
}
