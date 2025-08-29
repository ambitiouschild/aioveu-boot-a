package com.aioveu.boot.aioveuWarehouse.service.impl;

/*
 * @Description: TODO  仓库姓名验证器
 * @ClassName:  WarehouseNameValidator
 * @Author: 可我不敌可爱
 * @Email: ambitiouschild@qq.com
 * @Date:  2025/8/30  4:49
 * @LastEditors: 可我不敌可爱
 * @LastEditTime: 2025/8/30 4:49
 */

//构造函数命名错误：构造函数名必须与类名完全一致（包括大小写） public employeeName

// 之前（构造函数）
//public EmployeeNameValidator(...) {...}

// 之后（静态方法）
//public static <T> void validateAndSetEmployeeId(...) {...}

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.function.Function;
import java.util.function.ToLongFunction;


public class WarehouseNameValidator {


    /**
     * 验证实体存在并设置ID
     *
     * @param <T> 表单数据类型
     * @param <E> 实体类型
     * @param formData 表单数据对象
     * @param nameGetter 获取名称的函数
     * @param fieldGetter 实体字段的getter方法
     * @param idSetter 设置ID的函数
     * @param entityService 实体服务
     * @param entityName 实体名称（用于错误消息）
     */

    //构造函数命名错误：构造函数名必须与类名完全一致（包括大小写） public employeeName

    // 之前（构造函数）
    //public EmployeeNameValidator(...) {...}

    // 之后（静态方法）
    //public static <T> void validateAndSetEmployeeId(...) {...}

    public static <T, E> void validateEntityUnique(
            T formData,
            Function<T, String> nameGetter, // 使用 Function
            SFunction<E, String> fieldGetter,
            Function<T, Long> idSetter,
            IService<E> entityService,  // 使用通用服务
            String entityName
    ) {
        // 字段1：检查编号是否唯一（对于不依赖外键的字段，不可重复）
        LambdaQueryWrapper<E> wrapper = new LambdaQueryWrapper<>();
        // 正确调用：传递 formData 参数
        wrapper.eq(fieldGetter, nameGetter.apply(formData));

        if (entityService.count(wrapper) > 0) {
            throw new RuntimeException( entityName + nameGetter.apply(formData) + " 已存在");
        }
    }

    /**
     * 验证实体存在并设置ID
     *
     * @param <T> 表单数据类型
     * @param <E> 实体类型
     * @param formData 表单数据对象
     * @param nameGetter 获取名称的函数
     * @param fieldGetter 实体字段的getter方法
     * @param idSetter 设置ID的函数
     * @param entityService 实体服务
     * @param entityName 实体名称（用于错误消息）
     */
    public  static <T, E> void  validateEntityExists(
            T formData,
            Function<T, String> nameGetter, // 使用 Function
            SFunction<E, String> fieldGetter,
            IdSetter<T> idSetter,
            ToLongFunction<E> idExtractor, // 新增：从实体E中提取ID
            IService<E> entityService,  // 使用通用服务
            String entityName
    ) {
        // 字段1：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在")
        LambdaQueryWrapper<E> wrapper = new LambdaQueryWrapper<>();
        // 正确调用：传递 formData 参数
        wrapper.eq(fieldGetter, nameGetter.apply(formData));

        E entity = entityService.getOne(wrapper);
        if (entity != null) {
            idSetter.set(formData,idExtractor.applyAsLong(entity));
        } else {
            throw new RuntimeException( entityName + nameGetter.apply(formData) + " 不存在");
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
 * @Description: TODO  仓库姓名验证器
 * @ClassName:  EmployeeNameValidator
 * @Param:
 * @Return:
 * @Author: 可我不敌可爱
 * @Email: ambitiouschild@qq.com
 * @Date:  2025-08-30 03:18:20
 * @LastEditors: 可我不敌可爱
 * @LastEditTime: 2025-08-30 03:18:20
 *
 *

        // 字段1：检查编号是否唯一（对于不依赖外键的字段，不可重复）
        WarehouseNameValidator.count(
                formData,
                AioveuWarehouseForm::getName,
                AioveuWarehouse::getName,
                null,
                this
        );


        // 字段3：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        WarehouseNameValidator.validateEntityExists(
            formData,
            AioveuWarehouseForm::getWarehouseName,
            AioveuWarehouse::getName,
            AioveuWarehouseForm::setManagerId,
            AioveuWarehouse::getId, // 方法引用返回 long
            aioveuWarehouseService,
            "仓库"
        );

*/
