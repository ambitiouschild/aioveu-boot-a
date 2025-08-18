package com.aioveu.boot.aioveuEmployee.converter;

import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveuEmployee.model.entity.AioveuEmployee;
import com.aioveu.boot.aioveuEmployee.model.form.AioveuEmployeeForm;

/**
 * 员工信息对象转换器
 *
 * @author 可我不敌可爱
 * @since 2025-08-18 17:29
 */
@Mapper(componentModel = "spring")
public interface AioveuEmployeeConverter{

    AioveuEmployeeForm toForm(AioveuEmployee entity);

    AioveuEmployee toEntity(AioveuEmployeeForm formData);
}