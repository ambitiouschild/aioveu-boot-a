package com.aioveu.boot.aioveuCustomer.model.vo;

/*
 * @Description: TODO  获取所有客户vo列表（用于下拉选择框）
 * @ClassName:  CustomerOptionVO
 * @Author: 可我不敌可爱
 * @Email: ambitiouschild@qq.com
 * @Date:  2025/8/27  0:10
 * @LastEditors: 可我不敌可爱
 * @LastEditTime: 2025/8/27 0:10
 */

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema( description = "客户选项")
public class CustomerOptionVO {

    private Long customerId;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 带参数的构造函数
     *
     * @param customerId 客户ID
     * @param customerName 客户名称
     */
    public CustomerOptionVO(Long customerId, String customerName) {
        this.customerId = customerId;
        this.customerName = customerName;
    }
}
