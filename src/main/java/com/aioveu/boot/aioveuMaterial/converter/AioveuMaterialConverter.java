package com.aioveu.boot.aioveuMaterial.converter;

import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveuMaterial.model.entity.AioveuMaterial;
import com.aioveu.boot.aioveuMaterial.model.form.AioveuMaterialForm;

/**
 * 物资对象转换器
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 21:17
 */
@Mapper(componentModel = "spring")
public interface AioveuMaterialConverter{

    AioveuMaterialForm toForm(AioveuMaterial entity);

    AioveuMaterial toEntity(AioveuMaterialForm formData);
}