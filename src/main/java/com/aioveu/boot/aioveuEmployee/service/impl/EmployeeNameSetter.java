package com.aioveu.boot.aioveuEmployee.service.impl;


import com.aioveu.boot.aioveuEmployee.service.AioveuEmployeeService;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 员工姓名设置工具类
 */

public class EmployeeNameSetter {
    /**
     * 批量设置员工姓名到VO对象
     *
     * @param vos VO对象列表
     * @param idGetter 从VO对象获取员工ID的方法
     * @param nameSetter 设置员工姓名到VO对象的方法
     * @param employeeService 员工服务
     * @param <T> VO对象类型
     */
    public static <T> void setEmployeeNames(
            List<T> vos,
            Function<T, Long> idGetter,
            NameSetter<T> nameSetter,
            AioveuEmployeeService employeeService
    ) {
        if (vos == null || vos.isEmpty()) {
            return;
        }

        // 获取所有员工ID
        List<Long> employeeIds = vos.stream()
                .map(idGetter)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        if (employeeIds.isEmpty()) {
            return;
        }

        // 批量查询员工信息
        Map<Long, String> employeeMap = employeeService.getEmployeeMapByIds(employeeIds);

        // 设置员工姓名
        vos.forEach(vo -> {
            Long employeeId = idGetter.apply(vo);
            if (employeeId != null && employeeMap.containsKey(employeeId)) {
                nameSetter.set(vo, employeeMap.get(employeeId));
            } else if (employeeId != null) {
                nameSetter.set(vo, "未知员工");
            }
        });
    }

    /**
     * 设置姓名的函数式接口
     *
     * @param <T> VO对象类型
     */
    @FunctionalInterface
    public interface NameSetter<T> {
        void set(T vo, String name);
    }
}
