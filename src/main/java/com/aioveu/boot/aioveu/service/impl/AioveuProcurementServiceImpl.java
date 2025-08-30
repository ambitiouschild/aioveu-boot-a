package com.aioveu.boot.aioveu.service.impl;

import cn.idev.excel.util.StringUtils;
import com.aioveu.boot.aioveuDepartment.model.entity.AioveuDepartment;
import com.aioveu.boot.aioveuEmployee.model.entity.AioveuEmployee;
import com.aioveu.boot.aioveuEmployee.service.AioveuEmployeeService;
import com.aioveu.boot.aioveuEmployee.service.impl.EmployeeNameSetter;
import com.aioveu.boot.aioveuEmployee.service.impl.NameValidator;
import com.aioveu.boot.aioveuMaterial.model.entity.AioveuMaterial;
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
import com.aioveu.boot.aioveu.mapper.AioveuProcurementMapper;
import com.aioveu.boot.aioveu.service.AioveuProcurementService;
import com.aioveu.boot.aioveu.model.entity.AioveuProcurement;
import com.aioveu.boot.aioveu.model.form.AioveuProcurementForm;
import com.aioveu.boot.aioveu.model.query.AioveuProcurementQuery;
import com.aioveu.boot.aioveu.model.vo.AioveuProcurementVO;
import com.aioveu.boot.aioveu.converter.AioveuProcurementConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
/**
 * 采购流程服务实现类
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:07
 */


@Slf4j
@Service
@RequiredArgsConstructor
public class AioveuProcurementServiceImpl extends ServiceImpl<AioveuProcurementMapper, AioveuProcurement> implements AioveuProcurementService {

    private final AioveuProcurementConverter aioveuProcurementConverter;

    @Autowired
    private AioveuWarehouseService aioveuWarehouseService;

    @Autowired
    private AioveuEmployeeService aioveuEmployeeService;

    @Autowired
    private AioveuMaterialService aioveuMaterialService;

    /**
    * 获取采购流程分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AioveuProcurementVO>} 采购流程分页列表
    */
    @Override
    public IPage<AioveuProcurementVO> getAioveuProcurementPage(AioveuProcurementQuery queryParams) {

        // 处理物资名称映射
        if (StringUtils.isNotBlank(queryParams.getMaterialName())) {
            Long materialId = aioveuMaterialService.getIdByName(queryParams.getMaterialName());
            queryParams.setMaterialId(materialId);
        }


        // 处理申请人名称映射
        if (StringUtils.isNotBlank(queryParams.getApplicantName())) {
            Long employeeId = aioveuEmployeeService.getIdByName(queryParams.getApplicantName());
            queryParams.setApplicantId(employeeId);
        }

        Page<AioveuProcurementVO> pageVO = this.baseMapper.getAioveuProcurementPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );

        // 设置申请人
        setApplicantNames(pageVO.getRecords());

        WarehouseNameSetter.setWarehouseNames(
                pageVO.getRecords(),
                AioveuProcurementVO::getWarehouseId, // 获取ID
                AioveuProcurementVO::setWarehouseName, // 设置姓名
                aioveuWarehouseService
        );


        // 设置审核人
        setReviewerNames(pageVO.getRecords());

        setMaterialNames(pageVO.getRecords());

        return pageVO;
    }
    
    /**
     * 获取采购流程表单数据
     *
     * @param id 采购流程ID
     * @return 采购流程表单数据
     */
    @Override
    public AioveuProcurementForm getAioveuProcurementFormData(Long id) {
        // 1. 根据ID获取实体信息
        AioveuProcurement entity = this.getById(id);
        if (entity == null) {
            throw new ServiceException("不存在");
        }
        // 2. 将实体转换为表单对象
        AioveuProcurementForm form = aioveuProcurementConverter.toForm(entity);

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

        if (entity.getApplicantId() != null) {
            LambdaQueryWrapper<AioveuEmployee> Wrapper = new LambdaQueryWrapper<>();
            Wrapper.eq(AioveuEmployee::getEmployeeId, entity.getApplicantId())
                    .select(AioveuEmployee::getName); // 只选择需要的字段

            AioveuEmployee employee = aioveuEmployeeService.getOne(Wrapper);

            if (employee != null) {
                form.setApplicantName(employee.getName());
            }
        }

        if (entity.getReviewerId() != null) {
            LambdaQueryWrapper<AioveuEmployee> Wrapper = new LambdaQueryWrapper<>();
            Wrapper.eq(AioveuEmployee::getEmployeeId, entity.getReviewerId())
                    .select(AioveuEmployee::getName); // 只选择需要的字段

            AioveuEmployee employee = aioveuEmployeeService.getOne(Wrapper);

            if (employee != null) {
                form.setReviewerName(employee.getName());
            }
        }



        return form;
    }
    
    /**
     * 新增采购流程
     *
     * @param formData 采购流程表单对象
     * @return 是否新增成功
     */
    @Override
    public boolean saveAioveuProcurement(AioveuProcurementForm formData) {

        // 字段1：检查编号是否唯一（对于不依赖外键的字段，不可重复）
        NameValidator.validateEntityUnique(
                formData,
                AioveuProcurementForm::getProcurementNo,
                AioveuProcurement::getProcurementNo,
                null,
                this,
                "采购单号： "
        );

        // 字段2：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuProcurementForm::getMaterialName,
                AioveuMaterial::getName,
                AioveuProcurementForm::setMaterialId,
                AioveuMaterial::getId,
                aioveuMaterialService,
                "物资： "
        );

        // 字段3：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuProcurementForm::getWarehouseName,
                AioveuWarehouse::getName,
                AioveuProcurementForm::setWarehouseId,
                AioveuWarehouse::getId,
                aioveuWarehouseService,
                "仓库: "
        );

        // 字段3：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuProcurementForm::getApplicantName,
                AioveuEmployee::getName,
                AioveuProcurementForm::setApplicantId,
                AioveuEmployee::getEmployeeId,
                aioveuEmployeeService,
                "申请人: "
        );


        // 字段3：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuProcurementForm::getReviewerName,
                AioveuEmployee::getName,
                AioveuProcurementForm::setReviewerId,
                AioveuEmployee::getEmployeeId,
                aioveuEmployeeService,
                "审核人: "
        );





        AioveuProcurement entity = aioveuProcurementConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新采购流程
     *
     * @param id   采购流程ID
     * @param formData 采购流程表单对象
     * @return 是否修改成功
     */
    @Override
    public boolean updateAioveuProcurement(Long id,AioveuProcurementForm formData) {

        // 1. 验证ID对应的记录是否存在
        AioveuProcurement original = getById(id);
        if (original == null) {
            log.error("记录不存在: ID={}", id);
            throw new ServiceException("记录不存在");

        }

        // 字段2：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuProcurementForm::getMaterialName,
                AioveuMaterial::getName,
                AioveuProcurementForm::setMaterialId,
                AioveuMaterial::getId,
                aioveuMaterialService,
                "物资： "
        );

        // 字段3：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuProcurementForm::getWarehouseName,
                AioveuWarehouse::getName,
                AioveuProcurementForm::setWarehouseId,
                AioveuWarehouse::getId,
                aioveuWarehouseService,
                "仓库: "
        );

        // 字段3：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuProcurementForm::getApplicantName,
                AioveuEmployee::getName,
                AioveuProcurementForm::setApplicantId,
                AioveuEmployee::getEmployeeId,
                aioveuEmployeeService,
                "申请人: "
        );


        // 字段3：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuProcurementForm::getReviewerName,
                AioveuEmployee::getName,
                AioveuProcurementForm::setReviewerId,
                AioveuEmployee::getEmployeeId,
                aioveuEmployeeService,
                "审核人: "
        );

        // 2. 将表单数据转换为实体对象
        AioveuProcurement entity = aioveuProcurementConverter.toEntity(formData);

        // 3. 设置ID
        entity.setId(id);

        // 4. 复制未修改的字段,保留审计字段(创建时间不变，更新时间用当前时间)
        entity.setCreateTime(original.getCreateTime());

        // 5. 执行更新
        return this.updateById(entity);
    }
    
    /**
     * 删除采购流程
     *
     * @param ids 采购流程ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    @Override
    public boolean deleteAioveuProcurements(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的采购流程数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }


    /**
     * 批量设置名称到VO对象，将视图对象的员工id,转换为员工姓名
     */
    private void setApplicantNames(List<AioveuProcurementVO> procurementVOS) {
        EmployeeNameSetter.setEmployeeNames(
                procurementVOS,
                AioveuProcurementVO::getApplicantId, // 获取员工ID
                AioveuProcurementVO::setApplicantName, // 设置员工姓名
                aioveuEmployeeService
        );
    }

    /**
     * 批量设置名称到VO对象，将视图对象的员工id,转换为员工姓名
     */
    private void setReviewerNames(List<AioveuProcurementVO> procurementVOS) {
        EmployeeNameSetter.setEmployeeNames(
                procurementVOS,
                AioveuProcurementVO::getReviewerId, // 获取员工ID
                AioveuProcurementVO::setReviewerName, // 设置员工姓名
                aioveuEmployeeService
        );
    }



    /**
     * 批量设置名称到VO对象，将视图对象的物资id,转换为物资姓名
     */
    private void setMaterialNames(List<AioveuProcurementVO> procurementVOS) {
        MaterialNameSetter.setMaterialNames(
                procurementVOS,
                AioveuProcurementVO::getMaterialId, // 获取ID
                AioveuProcurementVO::setMaterialName, // 设置姓名
                aioveuMaterialService
        );
    }

}
