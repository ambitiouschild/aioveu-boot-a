package com.aioveu.boot.aioveuEmployee.service.impl;

/*
 * @Description: TODO 员工姓名验证器
 * @ClassName:  EmployeeNameValidator
 * @Author: 可我不敌可爱
 * @Email: ambitiouschild@qq.com
 * @Date:  2025/8/30  2:47
 * @LastEditors: 可我不敌可爱
 * @LastEditTime: 2025/8/30 2:47
 */

//构造函数命名错误：构造函数名必须与类名完全一致（包括大小写） public employeeName

// 之前（构造函数）
//public EmployeeNameValidator(...) {...}

// 之后（静态方法）
//public static <T> void validateAndSetEmployeeId(...) {...}

import com.aioveu.boot.aioveuWarehouse.service.impl.WarehouseNameValidator;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.function.Function;
import java.util.function.ToLongFunction;


/**
 * 员工姓名验证器
 *
 * <p>在构造函数中直接执行验证逻辑</p>
 */
public class EmployeeNameValidator {

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
            Function<T, String> nameGetter,
            SFunction<E, String> fieldGetter,
            WarehouseNameValidator.IdSetter<T> idSetter,
            ToLongFunction<E> idExtractor,
            IService<E> entityService,
            String entityName
    ) {
        LambdaQueryWrapper<E> wrapper = new LambdaQueryWrapper<>();

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
 * @Description: TODO  员工姓名验证器
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

        EmployeeNameValidator.notNull(
                formData,
                AioveuWarehouseForm::getManagerName,  // 获取经理姓名的方法
                AioveuEmployee::getName,  // 实体字段：员工姓名
//                (form, id) -> form.setManagerId(id), // 设置经理ID的方法  // 使用显式Lambda（推荐）
                AioveuWarehouseForm::setManagerId, // 直接使用方法引用
                AioveuEmployee::getEmployeeId, // 从员工实体获取ID的方法
                aioveuEmployeeService,  // 员工服务（不是this）
                "经理"  // 实体名称（用于错误消息）
        );

*/
