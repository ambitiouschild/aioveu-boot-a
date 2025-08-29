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

import com.aioveu.boot.aioveuEmployee.model.entity.AioveuEmployee;
import com.aioveu.boot.aioveuEmployee.service.AioveuEmployeeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.function.Function;
import java.util.function.Supplier;


/**
 * 员工姓名验证器
 *
 * <p>在构造函数中直接执行验证逻辑</p>
 */
public class EmployeeNameValidator<T> {

    /**
     * 构造函数
     *
     * @param formData 表单数据对象
     * @param nameGetter 获取员工姓名的函数
     * @param idSetter 设置员工ID的函数
     * @param aioveuEmployeeService 员工服务
     */

    //构造函数命名错误：构造函数名必须与类名完全一致（包括大小写） public employeeName

    // 之前（构造函数）
    //public EmployeeNameValidator(...) {...}

    // 之后（静态方法）
    //public static <T> void validateAndSetEmployeeId(...) {...}

    public  EmployeeNameValidator(
            T formData,
            Function<T, String> nameGetter, // 使用 Function
            IdSetter<T> idSetter, // 使用大写的 IdSetter
            AioveuEmployeeService aioveuEmployeeService
    ) {
        // 字段1：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在")
        LambdaQueryWrapper<AioveuEmployee> employeeWrapper = new LambdaQueryWrapper<>();
        // 正确调用：传递 formData 参数
        employeeWrapper.eq(AioveuEmployee::getName, nameGetter.apply(formData));

        AioveuEmployee employee = aioveuEmployeeService.getOne(employeeWrapper);
        if (employee != null) {
            idSetter.set(formData,employee.getEmployeeId());
        } else {
            throw new RuntimeException("员工: " + nameGetter.apply(formData) + " 不存在");
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

        new EmployeeNameValidator<>(
                formData,
                (form) -> form.getEmployeeName(), // Lambda 表达式
                (form, id) -> form.setEmployeeId(id),
                aioveuEmployeeService

        );

*/
