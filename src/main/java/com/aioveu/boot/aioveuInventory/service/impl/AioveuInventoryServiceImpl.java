package com.aioveu.boot.aioveuInventory.service.impl;

import cn.idev.excel.util.StringUtils;
import com.aioveu.boot.aioveuEmployee.service.impl.NameValidator;
import com.aioveu.boot.aioveuMaterial.model.entity.AioveuMaterial;
import com.aioveu.boot.aioveuMaterial.service.AioveuMaterialService;
import com.aioveu.boot.aioveuMaterial.service.impl.MaterialNameSetter;
import com.aioveu.boot.aioveuWarehouse.model.entity.AioveuWarehouse;
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
import com.aioveu.boot.aioveuInventory.mapper.AioveuInventoryMapper;
import com.aioveu.boot.aioveuInventory.service.AioveuInventoryService;
import com.aioveu.boot.aioveuInventory.model.entity.AioveuInventory;
import com.aioveu.boot.aioveuInventory.model.form.AioveuInventoryForm;
import com.aioveu.boot.aioveuInventory.model.query.AioveuInventoryQuery;
import com.aioveu.boot.aioveuInventory.model.vo.AioveuInventoryVO;
import com.aioveu.boot.aioveuInventory.converter.AioveuInventoryConverter;

import java.util.Arrays;
import java.util.List;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 库存信息服务实现类
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 21:56
 */


@Slf4j
@Service
@RequiredArgsConstructor
public class AioveuInventoryServiceImpl extends ServiceImpl<AioveuInventoryMapper, AioveuInventory> implements AioveuInventoryService {

    private final AioveuInventoryConverter aioveuInventoryConverter;

    @Autowired
    private AioveuWarehouseService aioveuWarehouseService;

    @Autowired
    private AioveuMaterialService aioveuMaterialService;

    /**
    * 获取库存信息分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AioveuInventoryVO>} 库存信息分页列表
    */
    @Override
    public IPage<AioveuInventoryVO> getAioveuInventoryPage(AioveuInventoryQuery queryParams) {

        // 处理仓库名称映射
        if (StringUtils.isNotBlank(queryParams.getWarehouseName())) {
            Long warehouseId = aioveuWarehouseService.getIdByName(queryParams.getWarehouseName());
            queryParams.setWarehouseId(warehouseId);
        }


        // 处理物资名称映射
        if (StringUtils.isNotBlank(queryParams.getMaterialName())) {
            Long materialId = aioveuMaterialService.getIdByName(queryParams.getMaterialName());
            queryParams.setMaterialId(materialId);
        }

        Page<AioveuInventoryVO> pageVO = this.baseMapper.getAioveuInventoryPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );

        setWarehouseNames(pageVO.getRecords());

        setMaterialNames(pageVO.getRecords());

        return pageVO;
    }
    
    /**
     * 获取库存信息表单数据
     *
     * @param id 库存信息ID
     * @return 库存信息表单数据
     */
    @Override
    public AioveuInventoryForm getAioveuInventoryFormData(Long id) {
        // 1. 根据ID获取实体信息
        AioveuInventory entity = this.getById(id);
        if (entity == null) {
            throw new ServiceException("不存在");
        }

        // 2. 将实体转换为表单对象
        AioveuInventoryForm form = aioveuInventoryConverter.toForm(entity);

        // 3. 处理映射信息（如果存在）
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

        return form;
    }
    
    /**
     * 新增库存信息
     *
     * @param formData 库存信息表单对象
     * @return 是否新增成功
     */
    @Override
    public boolean saveAioveuInventory(AioveuInventoryForm formData) {
        // 字段1：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuInventoryForm::getWarehouseName,
                AioveuWarehouse::getName,
                AioveuInventoryForm::setWarehouseId,
                AioveuWarehouse::getId, // 方法引用返回 long
                aioveuWarehouseService,
                "仓库："
        );

        // 字段2：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuInventoryForm::getMaterialName,
                AioveuMaterial::getName,
                AioveuInventoryForm::setMaterialId,
                AioveuMaterial::getId, // 方法引用返回 long
                aioveuMaterialService,
                "物资："
        );




        AioveuInventory entity = aioveuInventoryConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新库存信息
     *
     * @param id   库存信息ID
     * @param formData 库存信息表单对象
     * @return 是否修改成功
     */
    @Override
    public boolean updateAioveuInventory(Long id,AioveuInventoryForm formData) {

        // 1. 验证ID对应的记录是否存在
        AioveuInventory original = getById(id);
        if (original == null) {
            log.error("记录不存在: ID={}", id);
            throw new ServiceException("记录不存在");

        }

        // 字段1：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuInventoryForm::getWarehouseName,
                AioveuWarehouse::getName,
                AioveuInventoryForm::setWarehouseId,
                AioveuWarehouse::getId, // 方法引用返回 long
                aioveuWarehouseService,
                "仓库："
        );

        // 字段2：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuInventoryForm::getMaterialName,
                AioveuMaterial::getName,
                AioveuInventoryForm::setMaterialId,
                AioveuMaterial::getId, // 方法引用返回 long
                aioveuMaterialService,
                "物资："
        );

        // 2. 将表单数据转换为实体对象
        AioveuInventory entity = aioveuInventoryConverter.toEntity(formData);

        // 3. 设置ID
        entity.setId(id);

        // 4. 复制未修改的字段,保留审计字段(创建时间不变，更新时间用当前时间)
        entity.setCreateTime(original.getCreateTime());

        // 5. 执行更新
        return this.updateById(entity);
    }
    
    /**
     * 删除库存信息
     *
     * @param ids 库存信息ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    @Override
    public boolean deleteAioveuInventorys(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的库存信息数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }




    /**
     * 批量设置名称到VO对象，将视图对象的仓库id,转换为仓库姓名
     */
    private void setWarehouseNames(List<AioveuInventoryVO> inventoryVOS) {
        WarehouseNameSetter.setWarehouseNames(
                inventoryVOS ,
                AioveuInventoryVO::getWarehouseId, // 获取仓库ID
                AioveuInventoryVO::setWarehouseName, // 设置仓库姓名
                aioveuWarehouseService
        );
    }



    /**
     * 批量设置名称到VO对象，将视图对象的物资id,转换为物资姓名
     */
    private void setMaterialNames(List<AioveuInventoryVO> inventoryVOS) {
        MaterialNameSetter.setMaterialNames(
                inventoryVOS,
                AioveuInventoryVO::getMaterialId, // 获取ID
                AioveuInventoryVO::setMaterialName, // 设置姓名
                aioveuMaterialService
        );
    }
}
