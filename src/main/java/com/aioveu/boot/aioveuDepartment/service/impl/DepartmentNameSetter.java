package com.aioveu.boot.aioveuDepartment.service.impl;

/*
 * @Description: TODO  DepartmentNameSetter
 * @ClassName:  DepartmentNameSetter
 * @Author: 可我不敌可爱
 * @Email: ambitiouschild@qq.com
 * @Date:  2025/8/27  15:17
 * @LastEditors: 可我不敌可爱
 * @LastEditTime: 2025/8/27 15:17
 */

import com.aioveu.boot.aioveuDepartment.service.AioveuDepartmentService;
import com.aioveu.boot.aioveuEmployee.service.impl.EmployeeNameSetter;
import com.aioveu.boot.aioveuPerformance.model.vo.AioveuPerformanceVO;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.function.Function;



public class DepartmentNameSetter {

    /**
     * 批量设置部门姓名到VO对象
     *
     * @param vos VO对象列表
     * @param idGetter 从VO对象获取部门ID的方法
     * @param nameSetter 设置部门姓名到VO对象的方法
     * @param aioveuDepartmentService 部门服务
     * @param <T> VO对象类型
     */
    public static <T> void setDepartmentNames(
            List<T> vos,
            Function<T, Long> idGetter,
            NameSetter<T> nameSetter,
            AioveuDepartmentService aioveuDepartmentService
    ) {
        if (vos == null || vos.isEmpty()) {
            return;
        }

        // 获取所有部门ID
        List<Long> departmentIds = vos.stream()
                .map(idGetter)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        if (departmentIds.isEmpty()) {
            return;
        }

        // 批量查询部门信息
        Map<Long, String> departmentMap = aioveuDepartmentService.getDepartmentMapByIds(departmentIds);

        // 设置员工姓名
        vos.forEach(vo -> {
            Long departmentId = idGetter.apply(vo);
            if (departmentId != null && departmentMap.containsKey(departmentId)) {
                nameSetter.set(vo, departmentMap.get(departmentId));
            } else if (departmentId != null) {
                nameSetter.set(vo, "");
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

/*
 * @Description: TODO DepartmentNameSetter
 * @ClassName:  DepartmentNameSetter
 * @Param:
 * @Return:
 * @Author: 可我不敌可爱
 * @Email: ambitiouschild@qq.com
 * @Date:  2025-08-27 15:20:31
 * @LastEditors: 可我不敌可爱
 * @LastEditTime: 2025-08-27 15:20:31
 *

    @Autowired
    private AioveuDepartmentService aioveuDepartmentService;

    setDepartmentNames(pageVO.getRecords());


    private void setDepartmentNames(List<AioveuPerformanceVO> performanceVOS) {
        DepartmentNameSetter.setDepartmentNames(
                performanceVOS,
                AioveuPerformanceVO::getDepartmentId, // 获取ID
                AioveuPerformanceVO::setDepartmentName, // 设置姓名
                aioveuDepartmentService
        );
    }

*/

/**
 * 批量设置名称到VO对象，将视图对象的部门id,转换为部门姓名
 */