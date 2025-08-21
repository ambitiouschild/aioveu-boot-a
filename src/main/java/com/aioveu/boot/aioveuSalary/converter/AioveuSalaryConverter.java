package com.aioveu.boot.aioveuSalary.converter;

import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveuSalary.model.entity.AioveuSalary;
import com.aioveu.boot.aioveuSalary.model.form.AioveuSalaryForm;

/**
 * 员工工资明细对象转换器
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 17:14
 */
@Mapper(componentModel = "spring")
public interface AioveuSalaryConverter{

    AioveuSalaryForm toForm(AioveuSalary entity);

    AioveuSalary toEntity(AioveuSalaryForm formData);
}