package com.aioveu.boot.aioveuInbound.converter;

import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveuInbound.model.entity.AioveuInbound;
import com.aioveu.boot.aioveuInbound.model.form.AioveuInboundForm;

/**
 * 入库信息对象转换器
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 22:18
 */
@Mapper(componentModel = "spring")
public interface AioveuInboundConverter{

    AioveuInboundForm toForm(AioveuInbound entity);

    AioveuInbound toEntity(AioveuInboundForm formData);
}