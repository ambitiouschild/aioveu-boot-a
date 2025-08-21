package com.aioveu.boot.aioveuWarehouse.converter;

import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveuWarehouse.model.entity.AioveuWarehouse;
import com.aioveu.boot.aioveuWarehouse.model.form.AioveuWarehouseForm;

/**
 * 仓库信息对象转换器
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 21:42
 */
@Mapper(componentModel = "spring")
public interface AioveuWarehouseConverter{

    AioveuWarehouseForm toForm(AioveuWarehouse entity);

    AioveuWarehouse toEntity(AioveuWarehouseForm formData);
}