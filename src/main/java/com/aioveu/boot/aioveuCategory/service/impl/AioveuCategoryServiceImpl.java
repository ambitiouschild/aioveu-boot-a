package com.aioveu.boot.aioveuCategory.service.impl;

import com.aioveu.boot.aioveuCategory.model.vo.CategoryOptionVO;
import com.aioveu.boot.aioveuEmployee.service.impl.NameValidator;
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
import com.aioveu.boot.aioveuCategory.mapper.AioveuCategoryMapper;
import com.aioveu.boot.aioveuCategory.service.AioveuCategoryService;
import com.aioveu.boot.aioveuCategory.model.entity.AioveuCategory;
import com.aioveu.boot.aioveuCategory.model.form.AioveuCategoryForm;
import com.aioveu.boot.aioveuCategory.model.query.AioveuCategoryQuery;
import com.aioveu.boot.aioveuCategory.model.vo.AioveuCategoryVO;
import com.aioveu.boot.aioveuCategory.converter.AioveuCategoryConverter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 物资分类服务实现类
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 20:58
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AioveuCategoryServiceImpl extends ServiceImpl<AioveuCategoryMapper, AioveuCategory> implements AioveuCategoryService {

    private final AioveuCategoryConverter aioveuCategoryConverter;

    @Autowired
    private AioveuCategoryMapper aioveuCategoryMapper;

    @Override
    public Long getIdByName(String name) {
        return aioveuCategoryMapper.findIdByName(name);
    }


    /**
    * 获取物资分类分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AioveuCategoryVO>} 物资分类分页列表
    */
    @Override
    public IPage<AioveuCategoryVO> getAioveuCategoryPage(AioveuCategoryQuery queryParams) {
        Page<AioveuCategoryVO> pageVO = this.baseMapper.getAioveuCategoryPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );

        setParentCategoryNames(pageVO.getRecords());

        return pageVO;
    }
    
    /**
     * 获取物资分类表单数据
     *
     * @param id 物资分类ID
     * @return 物资分类表单数据
     */
    @Override
    public AioveuCategoryForm getAioveuCategoryFormData(Long id) {
        // 1. 根据ID获取实体信息
        AioveuCategory entity = this.getById(id);
        if (entity == null) {
            throw new ServiceException("不存在");
        }
        // 2. 将实体转换为表单对象
        AioveuCategoryForm form = aioveuCategoryConverter.toForm(entity);

        // 3. 处理映射信息（如果存在）
        if (entity.getParentId() != null) {
            LambdaQueryWrapper<AioveuCategory> Wrapper = new LambdaQueryWrapper<>();
            Wrapper.eq(AioveuCategory::getId, entity.getParentId())
                    .select(AioveuCategory::getName); // 只选择需要的字段

            AioveuCategory category = this.getOne(Wrapper);

            if (category != null) {
                form.setParentCategoryName(category.getName());
            }
        }

        return form;
    }
    
    /**
     * 新增物资分类
     *
     * @param formData 物资分类表单对象
     * @return 是否新增成功
     */
    @Override
    public boolean saveAioveuCategory(AioveuCategoryForm formData) {

        //直接验证分类是否唯一
        // 字段1：检查是否唯一（对于不依赖外键的字段，不可重复）
        LambdaQueryWrapper<AioveuCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AioveuCategory::getName, formData.getName());
        if (count(wrapper) > 0) {
            throw new RuntimeException("分类: " + formData.getName() + " 已存在");
        }

        // 字段3：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuCategoryForm::getParentCategoryName,
                AioveuCategory::getName,
                AioveuCategoryForm::setParentId,
                AioveuCategory::getId,
                this,
                "分类： "
        );

        AioveuCategory entity = aioveuCategoryConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新物资分类
     *
     * @param id   物资分类ID
     * @param formData 物资分类表单对象
     * @return 是否修改成功
     */
    @Override
    public boolean updateAioveuCategory(Long id,AioveuCategoryForm formData) {
        // 1. 验证ID对应的记录是否存在
        AioveuCategory original = getById(id);
        if (original == null) {
            log.error("记录不存在: ID={}", id);
            throw new ServiceException("记录不存在");

        }

        // 字段3：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuCategoryForm::getParentCategoryName,
                AioveuCategory::getName,
                AioveuCategoryForm::setParentId,
                AioveuCategory::getId,
                this,
                "分类： "
        );

        // 2. 将表单数据转换为实体对象
        AioveuCategory entity = aioveuCategoryConverter.toEntity(formData);

        // 3. 设置ID
        entity.setId(id);
        // 4. 复制未修改的字段,保留审计字段(创建时间不变，更新时间用当前时间)
        entity.setCreateTime(original.getCreateTime());

        // 6. 执行更新
        return this.updateById(entity);
    }
    
    /**
     * 删除物资分类
     *
     * @param ids 物资分类ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    @Override
    public boolean deleteAioveuCategorys(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的物资分类数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

    /**
     * 批量获取库存分类信息（新增方法）
     */
    @Override
    public Map<Long, String> getCategoryMapByIds(List<Long> categoryIds) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            return Map.of();
        }

        // 批量查询库存分类信息
        List<AioveuCategory> categorys = this.listByIds(categoryIds);

        // 转换为Map: key=库存分类ID, value=库存分类名称
        return categorys.stream()
                .collect(Collectors.toMap(
                        AioveuCategory::getId,
                        AioveuCategory::getName
                ));
    }

    /**
     * 获取所有库存分类列表（用于下拉选择框）
     *
     * @return 库存分类选项列表
     */
    @Override
    public List<CategoryOptionVO> getAllCategoryOptions() {
        // 查询所有部门
        List<AioveuCategory> categorys = this.list();

        // 转换为选项对象
        List<CategoryOptionVO>  categoryOptionVO  = categorys.stream()
                .map(category -> new CategoryOptionVO(category.getId(), category.getName()))
                .collect(Collectors.toList());

        return categoryOptionVO;
    }



    /**
     * 批量设置名称到VO对象，将视图对象的父分类id,转换为父分类姓名
     */
    private void setParentCategoryNames(List<AioveuCategoryVO> categoryVOS) {
        CategoryNameSetter.setCategoryNames(
                categoryVOS ,
                AioveuCategoryVO::getParentId, // 获取库存分类ID
                AioveuCategoryVO::setParentCategoryName, // 设置库存分类姓名
                this
        );
    }


}
