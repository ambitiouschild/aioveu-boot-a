package com.aioveu.boot.aioveuSalesOrder.model.vo;

/*
 * @Description: TODO  订单选项
 * @ClassName:  SalesOrderOptionVO
 * @Author: 可我不敌可爱
 * @Email: ambitiouschild@qq.com
 * @Date:  2025/8/27  1:39
 * @LastEditors: 可我不敌可爱
 * @LastEditTime: 2025/8/27 1:39
 */

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema( description = "订单选项")
public class SalesOrderOptionVO {

    private Long salesOrderId;
    /**
     * 订单名称
     */
    private String salesOrderNo;
    /**
     * 带参数的构造函数
     *
     * @param salesOrderId 订单ID
     * @param salesOrderNo 部门编号
     */
    public SalesOrderOptionVO(Long salesOrderId, String salesOrderNo) {
        this.salesOrderId = salesOrderId;
        this.salesOrderNo = salesOrderNo;
    }

}
