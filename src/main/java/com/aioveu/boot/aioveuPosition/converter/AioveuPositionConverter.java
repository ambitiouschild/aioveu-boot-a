package com.aioveu.boot.aioveuPosition.converter;

import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveuPosition.model.entity.AioveuPosition;
import com.aioveu.boot.aioveuPosition.model.form.AioveuPositionForm;

/**
 * 公司岗位信息对象转换器
 *
 * @author aioveu
 * @since 2025-08-18 16:26
 */
@Mapper(componentModel = "spring")
public interface AioveuPositionConverter{

    AioveuPositionForm toForm(AioveuPosition entity);

    AioveuPosition toEntity(AioveuPositionForm formData);
}