package com.aioveu.boot.aioveuSalesOrder.converter;

import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveuSalesOrder.model.entity.AioveuSalesOrder;
import com.aioveu.boot.aioveuSalesOrder.model.form.AioveuSalesOrderForm;

/**
 * 销售订单对象转换器
 *
 * @author 可我不敌可爱
 * @since 2025-08-22 00:27
 */
@Mapper(componentModel = "spring")
public interface AioveuSalesOrderConverter{

    AioveuSalesOrderForm toForm(AioveuSalesOrder entity);

    AioveuSalesOrder toEntity(AioveuSalesOrderForm formData);
}