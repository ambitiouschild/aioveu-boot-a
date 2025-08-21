package com.aioveu.boot.aioveuTransaction.converter;

import org.mapstruct.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveuTransaction.model.entity.AioveuTransaction;
import com.aioveu.boot.aioveuTransaction.model.form.AioveuTransactionForm;

/**
 * 客户交易记录对象转换器
 *
 * @author 可我不敌可爱
 * @since 2025-08-22 00:16
 */
@Mapper(componentModel = "spring")
public interface AioveuTransactionConverter{

    AioveuTransactionForm toForm(AioveuTransaction entity);

    AioveuTransaction toEntity(AioveuTransactionForm formData);
}