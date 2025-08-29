package com.aioveu.boot.aioveuCategory.service.impl;

/*
 * @Description: TODO  分类姓名验证器
 * @ClassName:  CategoryNameValidator
 * @Author: 可我不敌可爱
 * @Email: ambitiouschild@qq.com
 * @Date:  2025/8/30  3:58
 * @LastEditors: 可我不敌可爱
 * @LastEditTime: 2025/8/30 3:58
 */

import com.aioveu.boot.aioveuCategory.model.entity.AioveuCategory;
import com.aioveu.boot.aioveuCategory.service.AioveuCategoryService;
import com.aliyun.oss.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.function.Function;

import static com.baomidou.mybatisplus.extension.toolkit.Db.count;


public class CategoryNameValidator<T> {

    /**
     * 构造函数
     *
     * @param formData 表单数据对象
     * @param nameGetter 获取姓名的函数
     * @param idSetter 设置ID的函数
     */

    //构造函数命名：构造函数名必须与类名完全一致（包括大小写）


    public  CategoryNameValidator(
            T formData,
            Function<T, String> nameGetter, // 使用 Function
            IdSetter<T> idSetter, // 使用大写的 IdSetter
            AioveuCategoryService aioveuCategoryService // 使用更通用的类型
    ) {


        // 字段1：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在")

        LambdaQueryWrapper<AioveuCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AioveuCategory::getName, nameGetter.apply(formData));
        AioveuCategory one = aioveuCategoryService.getOne(wrapper);
        if (one != null) {
            idSetter.set(formData,one.getId());
        } else {
            throw new RuntimeException("分类: " + nameGetter.apply(formData) + " 不存在");
        }
    }

    /**
     * 设置ID的函数式接口
     *
     * @param <T> VO对象类型
     */
    @FunctionalInterface
    public interface IdSetter<T> {
        void set(T formData, Long id);
    }

}

/*

        // 字段2：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在")
        new CategoryNameValidator<>(
                formData,
                (form) -> form.getCategoryName(), // Lambda 表达式
                (form, id) -> form.setCategoryId(id),
                aioveuCategoryService

        );

*/