package com.aioveu.boot.aioveuDepartment.service.impl;

/*
 * @Description: TODO   通过复合键（名称+其他字段）查找对应的ID值
 * @ClassName:  DepartmentIdFinderWithCompositeKey
 * @Author: 可我不敌可爱
 * @Email: ambitiouschild@qq.com
 * @Date:  2025/8/27  17:06
 * @LastEditors: 可我不敌可爱
 * @LastEditTime: 2025/8/27 17:06
 */

import com.aioveu.boot.aioveuDepartment.service.AioveuDepartmentService;
import com.aioveu.boot.aioveuSalesOrder.service.AioveuSalesOrderService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.function.Function;


public class DepartmentIdFinderWithCompositeKey {

    /**
     * 通过复合键（名称+其他字段）查找对应的ID值
     *
     * @param form 单个表单对象
     * @param compositeKeyGetter 从VO对象获取复合键的方法
     * @param idSetter 设置订单ID到VO对象的方法
     * @param aioveuDepartmentService 服务
     * @param <T> VO对象类型
     */
    public static <T> void setDepartmentIdsByCompositeKeysform(
            T form,
            Function<T, String> compositeKeyGetter,
            IdSetter<T> idSetter,
            AioveuDepartmentService aioveuDepartmentService
    ) {
        if (form == null) {
            return;
        }

        // 获取复合键
        String compositeKey = compositeKeyGetter.apply(form);
        if (compositeKey == null || compositeKey.trim().isEmpty()) {
            return;
        }


        // 批量查询信息，获取复合键到ID的映射
        List<String> compositeKeys = Collections.singletonList(compositeKey);
        Map<String, Long> compositeKeyToIdMap = aioveuDepartmentService.getDepartmentIdMapByCompositeKeys(compositeKeys);

        // 设置ID
        if (compositeKeyToIdMap.containsKey(compositeKey)) {
            idSetter.set(form, compositeKeyToIdMap.get(compositeKey));
        } else {
            // 处理找不到对应ID的情况
            idSetter.set(form, null);
        }
    }

    /**
     * 通过复合键（名称+其他字段）查找对应的ID值 - 处理对象列表
     *
     * @param forms 表单对象列表
     * @param compositeKeyGetter 从VO对象获取复合键的方法
     * @param idSetter 设置ID到VO对象的方法
     * @param aioveuDepartmentService 服务
     * @param <T> VO对象类型
     */
    public static <T> void setDepartmentIdsByCompositeKeysforms(
            List<T> forms,
            Function<T, String> compositeKeyGetter,
            IdSetter<T> idSetter,
            AioveuDepartmentService aioveuDepartmentService
    ) {
        if (forms == null || forms.isEmpty()) {
            return;
        }

        // 获取所有复合键
        List<String> compositeKeys = forms.stream()
                .map(compositeKeyGetter)
                .filter(Objects::nonNull)
                .filter(key -> !key.trim().isEmpty())
                .distinct()
                .collect(Collectors.toList());

        if (compositeKeys.isEmpty()) {
            return;
        }

        // 批量查询信息，获取复合键到ID的映射
        Map<String, Long> compositeKeyToIdMap = aioveuDepartmentService.getDepartmentIdMapByCompositeKeys(compositeKeys);

        // 设置ID
        forms.forEach(form -> {
            String compositeKey = compositeKeyGetter.apply(form);
            if (compositeKey != null && compositeKeyToIdMap.containsKey(compositeKey)) {
                idSetter.set(form, compositeKeyToIdMap.get(compositeKey));
            } else if (compositeKey != null) {
                // 处理找不到对应ID的情况
                idSetter.set(form, null);
            }
        });
    }

    /**
     * 设置ID的函数式接口
     *
     * @param <T> VO对象类型
     */
    @FunctionalInterface
    public interface IdSetter<T> {
        void set(T vo, Long id);
    }
}
