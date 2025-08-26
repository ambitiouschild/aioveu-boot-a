package com.aioveu.boot.aioveuWarehouse.service.impl;


import com.aioveu.boot.aioveuEmployee.service.AioveuEmployeeService;
import com.aioveu.boot.aioveuWarehouse.service.AioveuWarehouseService;
import org.glassfish.jaxb.core.v2.TODO;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.Objects;
import java.util.function.Function;

/*
 * @Description: TODO   仓库名称设置工具类
 * @ClassName:  WarehouseNameSetter
 * @Param:  
 * @Return: 
 * @Author: 可我不敌可爱
 * @Email: ambitiouschild@qq.com
 * @Date:  2025-08-26 20:40:23
 * @LastEditors: 可我不敌可爱
 * @LastEditTime: 2025-08-26 20:40:23
*/

public class WarehouseNameSetter {

    /**
     * 批量设置仓库名称到VO对象
     *
     * @param vos VO对象列表
     * @param idGetter 从VO对象获取仓库ID的方法
     * @param nameSetter 设置仓库名称到VO对象的方法
     * @param aioveuWarehouseService 仓库服务
     * @param <T> VO对象类型
     */
    public static <T> void setWarehouseNames(
            List<T> vos,
            Function<T, Long> idGetter,
            NameSetter<T> nameSetter,
            AioveuWarehouseService aioveuWarehouseService
    ) {
        if (vos == null || vos.isEmpty()) {
            return;
        }

        // 获取所有仓库ID
        List<Long> warehouseIds = vos.stream()
                .map(idGetter)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        if (warehouseIds.isEmpty()) {
            return;
        }

        // 批量查询仓库信息
        Map<Long, String> warehouseMap = aioveuWarehouseService.getWarehouseMapByIds(warehouseIds);

        // 设置员工姓名
        vos.forEach(vo -> {
            Long warehouseId = idGetter.apply(vo);
            if (warehouseId != null && warehouseMap.containsKey(warehouseId)) {
                nameSetter.set(vo, warehouseMap.get(warehouseId));
            } else if (warehouseId != null) {
                nameSetter.set(vo, "未知仓库");
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
 * @ClassName:  WarehouseOptionVO
 * @Param:
 * @Return:
 * @Author: 可我不敌可爱
 * @Email: ambitiouschild@qq.com
 * @Date:  2025-08-26 20:47:44
 * @LastEditors: 可我不敌可爱
 * @LastEditTime: 2025-08-26 20:47:44
 *
    @Autowired
    private AioveuWarehouseService aioveuWarehouseService;

    setWarehouseNames(pageVO.getRecords());

    private void setWarehouseNames(List<AioveuMaterialVO> materialVOS) {
        WarehouseNameSetter.setWarehouseNames(
            materialVOS ,
            AioveuMaterialVO::getWarehouseId, // 获取仓库ID
            AioveuMaterialVO::setWarehouseName, // 设置仓库姓名
            aioveuWarehouseService
    );
    }
*/

/**
 * 批量设置名称到VO对象，将视图对象的仓库id,转换为仓库姓名
 */