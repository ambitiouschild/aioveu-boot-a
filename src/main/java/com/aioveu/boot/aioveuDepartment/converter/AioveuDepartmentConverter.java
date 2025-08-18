package com.aioveu.boot.aioveuDepartment.converter;

import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveuDepartment.model.entity.AioveuDepartment;
import com.aioveu.boot.aioveuDepartment.model.form.AioveuDepartmentForm;

/**
 * 公司部门组织结构对象转换器
 *
 * @author aioveu
 * @since 2025-08-18 14:42
 */
@Mapper(componentModel = "spring")
public interface AioveuDepartmentConverter{

    AioveuDepartmentForm toForm(AioveuDepartment entity);

    AioveuDepartment toEntity(AioveuDepartmentForm formData);
}