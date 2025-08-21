package com.aioveu.boot.aioveuSalesOrderDetail.converter;

import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveuSalesOrderDetail.model.entity.AioveuSalesOrderDetail;
import com.aioveu.boot.aioveuSalesOrderDetail.model.form.AioveuSalesOrderDetailForm;

/**
 * 订单明细对象转换器
 *
 * @author 可我不敌可爱
 * @since 2025-08-22 00:42
 */
@Mapper(componentModel = "spring")
public interface AioveuSalesOrderDetailConverter{

    AioveuSalesOrderDetailForm toForm(AioveuSalesOrderDetail entity);

    AioveuSalesOrderDetail toEntity(AioveuSalesOrderDetailForm formData);
}