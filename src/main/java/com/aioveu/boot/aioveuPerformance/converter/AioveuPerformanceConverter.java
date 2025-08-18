package com.aioveu.boot.aioveuPerformance.converter;

import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveuPerformance.model.entity.AioveuPerformance;
import com.aioveu.boot.aioveuPerformance.model.form.AioveuPerformanceForm;

/**
 * 员工绩效考评对象转换器
 *
 * @author 可我不敌可爱
 * @since 2025-08-18 22:48
 */
@Mapper(componentModel = "spring")
public interface AioveuPerformanceConverter{

    AioveuPerformanceForm toForm(AioveuPerformance entity);

    AioveuPerformance toEntity(AioveuPerformanceForm formData);
}