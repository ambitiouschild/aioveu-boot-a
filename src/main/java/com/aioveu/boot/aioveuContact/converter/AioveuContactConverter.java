package com.aioveu.boot.aioveuContact.converter;

import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveuContact.model.entity.AioveuContact;
import com.aioveu.boot.aioveuContact.model.form.AioveuContactForm;

/**
 * 客户联系人对象转换器
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:59
 */
@Mapper(componentModel = "spring")
public interface AioveuContactConverter{

    AioveuContactForm toForm(AioveuContact entity);

    AioveuContact toEntity(AioveuContactForm formData);
}