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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.function.Function;
import java.util.function.ToLongFunction;


/**
 * 员工姓名验证器
 * 最成功的系统是平衡了抽象和具体的系统。抽象方法就像软件设计中的食盐：太少则设计平淡无力，太多则破坏整体风味。
 * <p>在构造函数中直接执行验证逻辑</p>
 */
public class NameValidator {


    //当需要处理多个字段的验证时，使用Map是一种高效的方式。Map可以帮助我们以键值对的形式存储和检索数据，特别适合用于批量验证的场景。
// 优化策略总结
//1.相同实体类型的多字段验证 → 使用Map批量查询
//（如：多个员工字段 - 申请人、审核人）
// 2.不同实体类型的多字段验证 → 使用单次查询 + 并行处理
//（如：物资、仓库、员工）
// 3.唯一性验证 → 保持单独处理（因业务逻辑特殊）

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
            Function<T, String> nameGetter,
            SFunction<E, String> fieldGetter,
            IdSetter<T> idSetter,
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
