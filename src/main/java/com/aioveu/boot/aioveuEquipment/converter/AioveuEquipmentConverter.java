package com.aioveu.boot.aioveuEquipment.converter;

import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveuEquipment.model.entity.AioveuEquipment;
import com.aioveu.boot.aioveuEquipment.model.form.AioveuEquipmentForm;

/**
 * 设备管理对象转换器
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 22:50
 */
@Mapper(componentModel = "spring")
public interface AioveuEquipmentConverter{

    AioveuEquipmentForm toForm(AioveuEquipment entity);

    AioveuEquipment toEntity(AioveuEquipmentForm formData);
}