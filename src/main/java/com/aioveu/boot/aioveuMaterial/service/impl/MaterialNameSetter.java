package com.aioveu.boot.aioveuMaterial.service.impl;

/*
 * @Description: TODO  批量设置物资名称到VO对象
 * @ClassName:  MaterialNameSetter
 * @Author: 可我不敌可爱
 * @Email: ambitiouschild@qq.com
 * @Date:  2025/8/26  23:03
 * @LastEditors: 可我不敌可爱
 * @LastEditTime: 2025/8/26 23:03
 */

import com.aioveu.boot.aioveuEmployee.service.AioveuEmployeeService;
import com.aioveu.boot.aioveuEmployee.service.impl.EmployeeNameSetter;
import com.aioveu.boot.aioveuMaterial.service.AioveuMaterialService;
import com.aioveu.boot.aioveuPerformance.model.vo.AioveuPerformanceVO;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.function.Function;

public class MaterialNameSetter {

    /**
     * 批量设置物资名称到VO对象
     *
     * @param vos VO对象列表
     * @param idGetter 从VO对象获取物资ID的方法
     * @param nameSetter 设置物资姓名到VO对象的方法
     * @param aioveuMaterialService 物资服务
     * @param <T> VO对象类型
     */
    public static <T> void setMaterialNames(
            List<T> vos,
            Function<T, Long> idGetter,
            NameSetter<T> nameSetter,
            AioveuMaterialService aioveuMaterialService
    ) {
        if (vos == null || vos.isEmpty()) {
            return;
        }

        // 获取所有物资ID
        List<Long> materialIds = vos.stream()
                .map(idGetter)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        if (materialIds.isEmpty()) {
            return;
        }

        // 批量查询物资信息
        Map<Long, String> materialMap = aioveuMaterialService.getMaterialMapByIds(materialIds);

        // 设置物资姓名
        vos.forEach(vo -> {
            Long materialId = idGetter.apply(vo);
            if (materialId != null && materialMap.containsKey(materialId)) {
                nameSetter.set(vo, materialMap.get(materialId));
            } else if (materialId != null) {
                nameSetter.set(vo, "未知物资");
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
 * @Description: TODO
 * @ClassName:  MaterialNameSetter
 * @Param:
 * @Return:
 * @Author: 可我不敌可爱
 * @Email: ambitiouschild@qq.com
 * @Date:  2025-08-26 23:08:05
 * @LastEditors: 可我不敌可爱
 * @LastEditTime: 2025-08-26 23:08:05
 *
 *
        @Autowired
        private AioveuMaterialService aioveuMaterialService;

        setMaterialNames(pageVO.getRecords());

        private void setMaterialNames(List<AioveuPerformanceVO> performanceVOS) {
            MaterialNameSetter.setMaterialNames(
                    performanceVOS,
                    AioveuPerformanceVO::getMaterialId, // 获取ID
                    AioveuPerformanceVO::setMaterialName, // 设置姓名
                    aioveuMaterialService
            );
        }
*/


/**
 * 批量设置名称到VO对象，将视图对象的物资id,转换为物资姓名
 */