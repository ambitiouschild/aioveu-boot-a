package com.aioveu.boot.aioveuAttendance.converter;

import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveuAttendance.model.entity.AioveuAttendance;
import com.aioveu.boot.aioveuAttendance.model.form.AioveuAttendanceForm;

/**
 * 考勤信息对象转换器
 *
 * @author 可我不敌可爱
 * @since 2025-08-18 21:52
 */
@Mapper(componentModel = "spring")
public interface AioveuAttendanceConverter{

    AioveuAttendanceForm toForm(AioveuAttendance entity);

    AioveuAttendance toEntity(AioveuAttendanceForm formData);
}