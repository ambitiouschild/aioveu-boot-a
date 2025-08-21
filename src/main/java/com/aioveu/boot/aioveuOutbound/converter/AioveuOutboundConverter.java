package com.aioveu.boot.aioveuOutbound.converter;

import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveuOutbound.model.entity.AioveuOutbound;
import com.aioveu.boot.aioveuOutbound.model.form.AioveuOutboundForm;

/**
 * 出库记录对象转换器
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:26
 */
@Mapper(componentModel = "spring")
public interface AioveuOutboundConverter{

    AioveuOutboundForm toForm(AioveuOutbound entity);

    AioveuOutbound toEntity(AioveuOutboundForm formData);
}