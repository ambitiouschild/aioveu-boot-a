package com.aioveu.boot.aioveuInventory.converter;

import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveuInventory.model.entity.AioveuInventory;
import com.aioveu.boot.aioveuInventory.model.form.AioveuInventoryForm;

/**
 * 库存信息对象转换器
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 21:56
 */
@Mapper(componentModel = "spring")
public interface AioveuInventoryConverter{

    AioveuInventoryForm toForm(AioveuInventory entity);

    AioveuInventory toEntity(AioveuInventoryForm formData);
}