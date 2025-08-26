package com.aioveu.boot.aioveuContact.service.impl;

/*
 * @Description: TODO
 * @ClassName:  ContactNameSetter
 * @Author: 可我不敌可爱
 * @Email: ambitiouschild@qq.com
 * @Date:  2025/8/27  1:09
 * @LastEditors: 可我不敌可爱
 * @LastEditTime: 2025/8/27 1:09
 */

import com.aioveu.boot.aioveuContact.service.AioveuContactService;
import com.aioveu.boot.aioveuEmployee.service.AioveuEmployeeService;
import com.aioveu.boot.aioveuEmployee.service.impl.EmployeeNameSetter;
import com.aioveu.boot.aioveuPerformance.model.vo.AioveuPerformanceVO;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.function.Function;


public class ContactNameSetter {

    /**
     * 批量设置联系人姓名到VO对象
     *
     * @param vos VO对象列表
     * @param idGetter 从VO对象获取联系人ID的方法
     * @param nameSetter 设置联系人姓名到VO对象的方法
     * @param aioveuContactService 联系人服务
     * @param <T> VO对象类型
     */
    public static <T> void setContactNames(
            List<T> vos,
            Function<T, Long> idGetter,
            NameSetter<T> nameSetter,
            AioveuContactService aioveuContactService
    ) {
        if (vos == null || vos.isEmpty()) {
            return;
        }

        // 获取所有联系人ID
        List<Long> contactIds = vos.stream()
                .map(idGetter)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        if (contactIds.isEmpty()) {
            return;
        }

        // 批量查询联系人信息
        Map<Long, String> contactMap = aioveuContactService.getContactMapByIds(contactIds);

        // 设置联系人姓名
        vos.forEach(vo -> {
            Long contactId = idGetter.apply(vo);
            if (contactId != null && contactMap.containsKey(contactId)) {
                nameSetter.set(vo, contactMap.get(contactId));
            } else if (contactId != null) {
                nameSetter.set(vo, "未知联系人");
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
 * @Description: TODO   ContactNameSetter 姓名设置工具类
 * @ClassName:  ContactNameSetter
 * @Param:
 * @Return:
 * @Author: 可我不敌可爱
 * @Email: ambitiouschild@qq.com
 * @Date:  2025-08-27 01:14:08
 * @LastEditors: 可我不敌可爱
 * @LastEditTime: 2025-08-27 01:14:08
 *
    @Autowired
    private AioveuContactService aioveuContactService;

    setContactNames(pageVO.getRecords());


    private void setContactNames(List<AioveuPerformanceVO> performanceVOS) {
        ContactNameSetter.setContactNames(
                performanceVOS,
                AioveuPerformanceVO::getContactId, // 获取ID
                AioveuPerformanceVO::setContactName, // 设置姓名
                aioveuContactService
        );
    }

*/

/**
 * 批量设置名称到VO对象，将视图对象的联系人id,转换为联系人姓名
 */