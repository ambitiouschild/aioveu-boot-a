package com.aioveu.boot.aioveuCategory.converter;

import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveuCategory.model.entity.AioveuCategory;
import com.aioveu.boot.aioveuCategory.model.form.AioveuCategoryForm;

/**
 * 物资分类对象转换器
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 20:58
 */
@Mapper(componentModel = "spring")
public interface AioveuCategoryConverter{

    AioveuCategoryForm toForm(AioveuCategory entity);

    AioveuCategory toEntity(AioveuCategoryForm formData);
}