package com.aioveu.boot.aioveu.converter;

import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveu.model.entity.AioveuProcurement;
import com.aioveu.boot.aioveu.model.form.AioveuProcurementForm;

/**
 * 采购流程对象转换器
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:07
 */
@Mapper(componentModel = "spring")
public interface AioveuProcurementConverter{

    AioveuProcurementForm toForm(AioveuProcurement entity);

    AioveuProcurement toEntity(AioveuProcurementForm formData);
}