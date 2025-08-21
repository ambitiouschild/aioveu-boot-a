package com.aioveu.boot.aioveuCustomer.converter;

import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveuCustomer.model.entity.AioveuCustomer;
import com.aioveu.boot.aioveuCustomer.model.form.AioveuCustomerForm;

/**
 * 客户信息对象转换器
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:42
 */
@Mapper(componentModel = "spring")
public interface AioveuCustomerConverter{

    AioveuCustomerForm toForm(AioveuCustomer entity);

    AioveuCustomer toEntity(AioveuCustomerForm formData);
}