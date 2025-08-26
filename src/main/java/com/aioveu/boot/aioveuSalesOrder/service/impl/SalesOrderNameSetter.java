package com.aioveu.boot.aioveuSalesOrder.service.impl;

/*
 * @Description: TODO  SalesOrderNameSetter
 * @ClassName:  SalesOrderNameSetter
 * @Author: 可我不敌可爱
 * @Email: ambitiouschild@qq.com
 * @Date:  2025/8/27  1:50
 * @LastEditors: 可我不敌可爱
 * @LastEditTime: 2025/8/27 1:50
 */

import com.aioveu.boot.aioveuEmployee.service.AioveuEmployeeService;
import com.aioveu.boot.aioveuEmployee.service.impl.EmployeeNameSetter;
import com.aioveu.boot.aioveuPerformance.model.vo.AioveuPerformanceVO;
import com.aioveu.boot.aioveuSalesOrder.service.AioveuSalesOrderService;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.function.Function;


public class SalesOrderNameSetter {

    /**
     * 批量设置订单姓名到VO对象
     *
     * @param vos VO对象列表
     * @param idGetter 从VO对象获取订单ID的方法
     * @param nameSetter 设置订单姓名到VO对象的方法
     * @param aioveuSalesOrderService 订单服务
     * @param <T> VO对象类型
     */
    public static <T> void setSalesOrderNames(
            List<T> vos,
            Function<T, Long> idGetter,
            NameSetter<T> nameSetter,
            AioveuSalesOrderService aioveuSalesOrderService
    ) {
        if (vos == null || vos.isEmpty()) {
            return;
        }

        // 获取所有订单ID
        List<Long> salesOrderIds = vos.stream()
                .map(idGetter)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        if (salesOrderIds.isEmpty()) {
            return;
        }

        // 批量查询订单信息
        Map<Long, String> salesOrderMap = aioveuSalesOrderService.getSalesOrderMapByIds(salesOrderIds);

        // 设置订单姓名
        vos.forEach(vo -> {
            Long salesOrderId = idGetter.apply(vo);
            if (salesOrderId != null && salesOrderMap.containsKey(salesOrderId)) {
                nameSetter.set(vo, salesOrderMap.get(salesOrderId));
            } else if (salesOrderId != null) {
                nameSetter.set(vo, "未知订单");
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
 * @Description: TODO  SalesOrderNameSetter
 * @ClassName:  SalesOrderNameSetter
 * @Param:
 * @Return:
 * @Author: 可我不敌可爱
 * @Email: ambitiouschild@qq.com
 * @Date:  2025-08-27 01:52:46
 * @LastEditors: 可我不敌可爱
 * @LastEditTime: 2025-08-27 01:52:46
 *
    @Autowired
    private AioveuSalesOrderService aioveuSalesOrderService;

    setSalesOrderNames(pageVO.getRecords());


    private void setSalesOrderNames(List<AioveuPerformanceVO> performanceVOS) {
        SalesOrderNameSetter.setSalesOrderNames(
                performanceVOS,
                AioveuPerformanceVO::getSalesOrderId, // 获取ID
                AioveuPerformanceVO::setSalesOrderNo, // 设置姓名
                aioveuSalesOrderService
        );
    }

*/

/**
 * 批量设置名称到VO对象，将视图对象的订单id,转换为订单姓名
 */