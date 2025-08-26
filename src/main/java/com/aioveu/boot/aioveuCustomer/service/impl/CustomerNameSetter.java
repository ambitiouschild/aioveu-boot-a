package com.aioveu.boot.aioveuCustomer.service.impl;
import com.aioveu.boot.aioveuCustomer.service.AioveuCustomerService;
import com.aioveu.boot.aioveuEmployee.service.AioveuEmployeeService;
import com.aioveu.boot.aioveuEmployee.service.impl.EmployeeNameSetter;
import com.aioveu.boot.aioveuPerformance.model.vo.AioveuPerformanceVO;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.function.Function;
/*
 * @Description: TODO 批量设置客户姓名到VO对象
 * @ClassName:  CustomerNameSetter
 * @Author: 可我不敌可爱
 * @Email: ambitiouschild@qq.com
 * @Date:  2025/8/27  0:21
 * @LastEditors: 可我不敌可爱
 * @LastEditTime: 2025/8/27 0:21
 */


public class CustomerNameSetter {

    /**
     * 批量设置客户姓名到VO对象
     *
     * @param vos VO对象列表
     * @param idGetter 从VO对象获取客户ID的方法
     * @param nameSetter 设置客户姓名到VO对象的方法
     * @param aioveuCustomerService 客户服务
     * @param <T> VO对象类型
     */
    public static <T> void setCustomerNames(
            List<T> vos,
            Function<T, Long> idGetter,
            NameSetter<T> nameSetter,
            AioveuCustomerService aioveuCustomerService
    ) {
        if (vos == null || vos.isEmpty()) {
            return;
        }

        // 获取所有客户ID
        List<Long> customerIds = vos.stream()
                .map(idGetter)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        if (customerIds.isEmpty()) {
            return;
        }

        // 批量查询员工信息
        Map<Long, String> customerMap = aioveuCustomerService.getCustomerMapByIds(customerIds);

        // 设置员工姓名
        vos.forEach(vo -> {
            Long customerId = idGetter.apply(vo);
            if (customerId != null && customerMap.containsKey(customerId)) {
                nameSetter.set(vo, customerMap.get(customerId));
            } else if (customerId != null) {
                nameSetter.set(vo, "未知客户");
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
 * @Description: TODO  setCustomerNames工具类调用
 * @ClassName:  CustomerNameSetter
 * @Param:
 * @Return:
 * @Author: 可我不敌可爱
 * @Email: ambitiouschild@qq.com
 * @Date:  2025-08-27 00:26:54
 * @LastEditors: 可我不敌可爱
 * @LastEditTime: 2025-08-27 00:26:54
 *
    @Autowired
    private AioveuCustomerService aioveuCustomerService;

    setCustomerNames(pageVO.getRecords());


    private void setCustomerNames(List<AioveuPerformanceVO> performanceVOS) {
        CustomerNameSetter.setCustomerNames(
                performanceVOS,
                AioveuPerformanceVO::getCustomerId, // 获取ID
                AioveuPerformanceVO::setCustomerName, // 设置姓名
                aioveuCustomerService
        );
    }
*/

/**
 * 批量设置名称到VO对象，将视图对象的客户id,转换为客户姓名
 */