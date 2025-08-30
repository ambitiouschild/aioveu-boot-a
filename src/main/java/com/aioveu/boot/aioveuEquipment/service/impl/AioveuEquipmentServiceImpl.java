package com.aioveu.boot.aioveuEquipment.service.impl;

import cn.idev.excel.util.StringUtils;
import com.aioveu.boot.aioveuCategory.model.entity.AioveuCategory;
import com.aioveu.boot.aioveuCategory.service.AioveuCategoryService;
import com.aioveu.boot.aioveuCategory.service.impl.CategoryNameSetter;
import com.aioveu.boot.aioveuDepartment.model.entity.AioveuDepartment;
import com.aioveu.boot.aioveuDepartment.service.AioveuDepartmentService;
import com.aioveu.boot.aioveuDepartment.service.impl.DepartmentNameSetter;
import com.aioveu.boot.aioveuEmployee.model.entity.AioveuEmployee;
import com.aioveu.boot.aioveuEmployee.service.AioveuEmployeeService;
import com.aioveu.boot.aioveuEmployee.service.impl.EmployeeNameSetter;
import com.aioveu.boot.aioveuEmployee.service.impl.NameValidator;
import com.aioveu.boot.aioveuMaterial.model.vo.AioveuMaterialVO;
import com.aioveu.boot.aioveuWarehouse.model.entity.AioveuWarehouse;
import com.aioveu.boot.aioveuWarehouse.model.form.AioveuWarehouseForm;
import com.aliyun.oss.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aioveu.boot.aioveuEquipment.mapper.AioveuEquipmentMapper;
import com.aioveu.boot.aioveuEquipment.service.AioveuEquipmentService;
import com.aioveu.boot.aioveuEquipment.model.entity.AioveuEquipment;
import com.aioveu.boot.aioveuEquipment.model.form.AioveuEquipmentForm;
import com.aioveu.boot.aioveuEquipment.model.query.AioveuEquipmentQuery;
import com.aioveu.boot.aioveuEquipment.model.vo.AioveuEquipmentVO;
import com.aioveu.boot.aioveuEquipment.converter.AioveuEquipmentConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 设备管理服务实现类
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 22:50
 */


@Slf4j
@Service
@RequiredArgsConstructor
public class AioveuEquipmentServiceImpl extends ServiceImpl<AioveuEquipmentMapper, AioveuEquipment> implements AioveuEquipmentService {

    private final AioveuEquipmentConverter aioveuEquipmentConverter;

    @Autowired
    private AioveuEmployeeService aioveuEmployeeService;

    @Autowired
    private AioveuCategoryService aioveuCategoryService;

    @Autowired
    private AioveuDepartmentService aioveuDepartmentService;

    /**
    * 获取设备管理分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AioveuEquipmentVO>} 设备管理分页列表
    */
    @Override
    public IPage<AioveuEquipmentVO> getAioveuEquipmentPage(AioveuEquipmentQuery queryParams) {

        // 处理部门名称映射
        if (StringUtils.isNotBlank(queryParams.getCategoryName())) {
            Long categoryId = aioveuCategoryService.getIdByName(queryParams.getCategoryName());
            queryParams.setCategoryId(categoryId);
        }

        // 处理部门名称映射
        if (StringUtils.isNotBlank(queryParams.getResponsiblePersonName())) {
            Long employeeId = aioveuEmployeeService.getIdByName(queryParams.getResponsiblePersonName());
            queryParams.setResponsiblePerson(employeeId);
        }

        Page<AioveuEquipmentVO> pageVO = this.baseMapper.getAioveuEquipmentPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );

        // 设置员工名称
        setEmployeeNames(pageVO.getRecords());

        setCategoryNames(pageVO.getRecords());

        DepartmentNameSetter.setDepartmentNames(
                pageVO.getRecords() ,
                AioveuEquipmentVO::getDepartmentId, // 获取库存分类ID
                AioveuEquipmentVO::setDepartmentName, // 设置库存分类姓名
                aioveuDepartmentService
        );

        return pageVO;
    }
    
    /**
     * 获取设备管理表单数据
     *
     * @param id 设备管理ID
     * @return 设备管理表单数据
     */
    @Override
    public AioveuEquipmentForm getAioveuEquipmentFormData(Long id) {
        // 1. 根据ID获取实体信息
        AioveuEquipment entity = this.getById(id);
        if (entity == null) {
            throw new ServiceException("不存在");
        }
        // 2. 将实体转换为表单对象
        AioveuEquipmentForm form = aioveuEquipmentConverter.toForm(entity);

        // 3. 处理映射信息（如果存在）
        if (entity.getCategoryId() != null) {
            LambdaQueryWrapper<AioveuCategory> Wrapper = new LambdaQueryWrapper<>();
            Wrapper.eq(AioveuCategory::getId, entity.getCategoryId())
                    .select(AioveuCategory::getName); // 只选择需要的字段

            AioveuCategory category = aioveuCategoryService.getOne(Wrapper);

            if (category != null) {
                form.setCategoryName(category.getName());
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

        if (entity.getResponsiblePerson() != null) {
            LambdaQueryWrapper<AioveuEmployee> Wrapper = new LambdaQueryWrapper<>();
            Wrapper.eq(AioveuEmployee::getEmployeeId, entity.getResponsiblePerson())
                    .select(AioveuEmployee::getName); // 只选择需要的字段

            AioveuEmployee employee = aioveuEmployeeService.getOne(Wrapper);

            if (employee != null) {
                form.setResponsiblePersonName(employee.getName());
            }
        }


        return form;
    }
    
    /**
     * 新增设备管理
     *
     * @param formData 设备管理表单对象
     * @return 是否新增成功
     */
    @Override
    public boolean saveAioveuEquipment(AioveuEquipmentForm formData) {

        // 字段1：检查编号是否唯一（对于不依赖外键的字段，不可重复）
        NameValidator.validateEntityUnique(
                formData,
                AioveuEquipmentForm::getAssetNo,
                AioveuEquipment::getAssetNo,
                null,
                this,
                "资产编号： "
        );

        // 字段1：检查编号是否唯一（对于不依赖外键的字段，不可重复）
        NameValidator.validateEntityUnique(
                formData,
                AioveuEquipmentForm::getName,
                AioveuEquipment::getName,
                null,
                this,
                "设备名称： "
        );

        // 字段3：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuEquipmentForm::getCategoryName,
                AioveuCategory::getName,
                AioveuEquipmentForm::setCategoryId,
                AioveuCategory::getId,
                aioveuCategoryService,
                "设备分类： "
        );

        // 字段3：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuEquipmentForm::getDepartmentName,
                AioveuDepartment::getDeptName,
                AioveuEquipmentForm::setDepartmentId,
                AioveuDepartment::getDeptId,
                aioveuDepartmentService,
                "所属部门： "
        );

        // 字段4：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuEquipmentForm::getResponsiblePersonName,
                AioveuEmployee::getName,
                AioveuEquipmentForm::setResponsiblePerson,
                AioveuEmployee::getEmployeeId,
                aioveuEmployeeService,
                "责任人： "
        );

        AioveuEquipment entity = aioveuEquipmentConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新设备管理
     *
     * @param id   设备管理ID
     * @param formData 设备管理表单对象
     * @return 是否修改成功
     */
    @Override
    public boolean updateAioveuEquipment(Long id,AioveuEquipmentForm formData) {

        // 1. 验证ID对应的记录是否存在
        AioveuEquipment original = getById(id);
        if (original == null) {
            log.error("记录不存在: ID={}", id);
            throw new ServiceException("记录不存在");

        }

        // 字段3：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuEquipmentForm::getCategoryName,
                AioveuCategory::getName,
                AioveuEquipmentForm::setCategoryId,
                AioveuCategory::getId,
                aioveuCategoryService,
                "设备分类： "
        );

        // 字段3：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuEquipmentForm::getDepartmentName,
                AioveuDepartment::getDeptName,
                AioveuEquipmentForm::setDepartmentId,
                AioveuDepartment::getDeptId,
                aioveuDepartmentService,
                "所属部门： "
        );

        // 字段4：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuEquipmentForm::getResponsiblePersonName,
                AioveuEmployee::getName,
                AioveuEquipmentForm::setResponsiblePerson,
                AioveuEmployee::getEmployeeId,
                aioveuEmployeeService,
                "责任人： "
        );

        // 2. 将表单数据转换为实体对象
        AioveuEquipment entity = aioveuEquipmentConverter.toEntity(formData);

        // 3. 设置ID
        entity.setId(id);

        // 4. 复制未修改的字段,保留审计字段(创建时间不变，更新时间用当前时间)
        entity.setCreateTime(original.getCreateTime());

        // 5. 执行更新
        return this.updateById(entity);
    }
    
    /**
     * 删除设备管理
     *
     * @param ids 设备管理ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    @Override
    public boolean deleteAioveuEquipments(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的设备管理数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

    /**
     * 批量设置名称到VO对象，将AioveuPerformanceVO绩效表视图对象的员工id,转换为员工姓名
     */
    private void setEmployeeNames(List<AioveuEquipmentVO> equipmentVOS) {
        EmployeeNameSetter.setEmployeeNames(
                equipmentVOS,
                AioveuEquipmentVO::getResponsiblePerson, // 获取员工ID
                AioveuEquipmentVO::setResponsiblePersonName, // 设置员工姓名
                aioveuEmployeeService
        );
    }




    /**
     * 批量设置名称到VO对象，将视图对象的id,转换为姓名
     */
    private void setCategoryNames(List<AioveuEquipmentVO> equipmentVOS) {
        CategoryNameSetter.setCategoryNames(
                equipmentVOS ,
                AioveuEquipmentVO::getCategoryId, // 获取库存分类ID
                AioveuEquipmentVO::setCategoryName, // 设置库存分类姓名
                aioveuCategoryService
        );
    }
}
