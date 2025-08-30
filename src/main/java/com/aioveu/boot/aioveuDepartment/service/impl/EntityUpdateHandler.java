package com.aioveu.boot.aioveuDepartment.service.impl;

import com.aioveu.boot.aioveuDepartment.model.entity.AioveuDepartment;
import com.aioveu.boot.aioveuDepartment.model.form.AioveuDepartmentForm;
import com.aioveu.boot.aioveuEmployee.model.entity.AioveuEmployee;
import com.aioveu.boot.aioveuEmployee.model.form.AioveuEmployeeForm;
import com.aliyun.oss.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

/**
 * 通用更新处理器
 *
 * @param <ID> 实体ID类型
 * @param <ENTITY> 实体类型
 * @param <FORM> 表单类型
 */
@Slf4j
@Component

/*
 * @Description: TODO
 * @ClassName:  EntityUpdateHandler
 * @Author: 可我不敌可爱
 * @Email: ambitiouschild@qq.com
 * @Date:  2025/8/30  10:03
 * @LastEditors: 可我不敌可爱
 * @LastEditTime: 2025/8/30 10:03
 */

public class EntityUpdateHandler<ID, ENTITY, FORM> {

    //我们已经在之前的代码中通过依赖注入了EntityUpdateHandler的实例，并通过泛型参数指定了具体的类型。
    // 但是，如果在一个服务中需要更新多种实体，我们可以注入多个不同泛型参数的EntityUpdateHandler实例。

//    @Component
//    public class DepartmentUpdateHandler extends EntityUpdateHandler<Long, AioveuDepartment, AioveuDepartmentForm> {
//        // 可以添加部门特定的逻辑
//    }
//
//    @Component
//    public class EmployeeUpdateHandler extends EntityUpdateHandler<Long, AioveuEmployee, AioveuEmployeeForm> {
//        // 可以添加员工特定的逻辑
//    }

// 1.如果需要更新的多个字段属于同一个实体，那么我们已经支持（通过表单转换器一次转换多个字段）。
//2.如果要更新的多个字段属于不同的实体，那么建议拆分为多次更新，每次更新一个实体，使用不同的处理器实例。
//3.通过创建具体类型的子类，我们可以复用处理器基类，同时保留扩展点。


    /**
     * 执行更新操作
     *
     * @param id 实体ID
     * @param formData 表单数据
     * @param updateFunction 实际更新函数
     * @param findFunction 查找函数
     * @param converter 表单转换器
     * @return 更新结果
     */
    public boolean handleUpdate(
            ID id,
            FORM formData,
            UpdateFunction<ID, ENTITY> updateFunction,
            FindFunction<ID, ENTITY> findFunction,
            FormConverter<FORM, ENTITY> converter,
            IdSetter<ENTITY, ID> idSetter
    ) {
        log.info("开始更新实体: ID={}", id);

        // 获取原始实体
        ENTITY original = findFunction.find(id);
        if (original == null) {
            log.error("实体不存在: ID={}", id);
            throw new ServiceException("实体不存在");
        }

        // 执行自定义前置处理
        preUpdateProcessing(id, formData, original);

        // 将表单数据转换为实体对象
        ENTITY entity = converter.toEntity(formData);


        idSetter.set(entity, id);

        // 保留原始创建时间
        preserveCreationDate(entity, original, converter);

        // 执行更新操作
        boolean result = updateFunction.update(entity);
        log.info("更新结果: {}", result);

        return result;
    }

    /**
     * 自定义前置处理钩子方法
     */
    protected void preUpdateProcessing(ID id, FORM formData, ENTITY original) {
        // 子类可覆盖此方法添加自定义逻辑
    }


    /**
     * 保留原始创建时间
     */
    private void preserveCreationDate(ENTITY entity, ENTITY original, FormConverter<FORM, ENTITY> converter) {
        // 如果转换器支持获取创建时间，使用转换器方法
        if (converter instanceof FormWithCreationDateConverter) {
            ((FormWithCreationDateConverter<FORM, ENTITY>) converter).setCreationDate(entity, original);
        } else {
            // 反射获取和设置创建时间
            try {
                Class<?> entityClass = entity.getClass();
                java.lang.reflect.Field createTimeField = entityClass.getDeclaredField("createTime");
                createTimeField.setAccessible(true);
                Object originalCreateTime = createTimeField.get(original);
                createTimeField.set(entity, originalCreateTime);
            } catch (Exception e) {
                log.warn("保留创建时间失败，将忽略", e);
            }
        }
    }

    // ======== 函数接口 ========

    @FunctionalInterface
    public interface IdSetter<ENTITY, ID> {
        void set(ENTITY  entity, ID id);
    }

    @FunctionalInterface
    public interface UpdateFunction<ID, ENTITY> {
        boolean update(ENTITY entity);
    }

    @FunctionalInterface
    public interface FindFunction<ID, ENTITY> {
        ENTITY find(ID id);
    }

    public interface FormConverter<FORM, ENTITY> {
        ENTITY toEntity(FORM form);
    }

    public interface FormWithIdConverter<FORM, ENTITY, ID> extends FormConverter<FORM, ENTITY> {
        ENTITY toEntity(FORM form, ID id);
    }

    public interface FormWithCreationDateConverter<FORM, ENTITY> extends FormConverter<FORM, ENTITY> {
        void setCreationDate(ENTITY entity, ENTITY original);
    }
}
