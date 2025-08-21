package com.aioveu.boot.aioveuSalesOrder.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.aioveu.boot.common.base.BaseEntity;

/**
 * 销售订单实体对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-22 00:27
 */
@Getter
@Setter
@TableName("aioveu_sales_order")
public class AioveuSalesOrder extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 订单编号（唯一）
     */
    private String orderNo;
    /**
     * 客户ID
     */
    private Integer customerId;
    /**
     * 联系人ID
     */
    private Integer contactId;
    /**
     * 下单时间
     */
    private LocalDateTime orderDate;
    /**
     * 预计交货日期
     */
    private LocalDate expectedDelivery;
    /**
     * 实际交货日期
     */
    private LocalDate actualDelivery;
    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;
    /**
     * 货币代码
     */
    private String currency;
    /**
     * 整体折扣率
     */
    private BigDecimal discount;
    /**
     * 税率
     */
    private BigDecimal taxRate;
    /**
     * 税额
     */
    private BigDecimal taxAmount;
    /**
     * 订单总额（含税）
     */
    private BigDecimal grandTotal;
    /**
     * 付款条件：1-预付全款，2-货到付款，3-月结30天，4-月结60天，5-其他
     */
    private Integer paymentTerms;
    /**
     * 支付状态
     */
    private Integer paymentStatus;
    /**
     * 订单状态
     */
    private Integer orderStatus;
    /**
     * 收货地址
     */
    private String shippingAddress;
    /**
     * 运输方式
     */
    private Integer shippingMethod;
    /**
     * 物流单号
     */
    private String trackingNo;
    /**
     * 销售负责人ID
     */
    private Integer salesRepId;
    /**
     * 操作员ID
     */
    private Integer operatorId;
    /**
     * 备注
     */
    private String notes;
}
