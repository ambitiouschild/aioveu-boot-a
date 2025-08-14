package com.aioveu.boot.system.converter;

import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.system.model.entity.Member;
import com.aioveu.boot.system.model.form.MemberForm;

/**
 * 会员信息对象转换器
 *
 * @author ambitiouschild
 * @since 2025-07-11 15:13
 */
@Mapper(componentModel = "spring")
public interface MemberConverter{

    MemberForm toForm(Member entity);

    Member toEntity(MemberForm formData);
}