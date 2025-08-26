package com.aioveu.boot.aioveuSalesOrder.model.vo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * 销售订单视图对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-22 00:27
 */
@Getter
@Setter
@Schema( description = "销售订单视图对象")
public class AioveuSalesOrderVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "订单ID（主键）")
    private Integer id;
    @Schema(description = "订单编号（唯一）")
    private String orderNo;
    @Schema(description = "客户ID")
    private Long customerId;

    /**
     * 客户名称
     */
    private String customerName;


    @Schema(description = "联系人ID")
    private Long contactId;

    /**
     * 联系人名称
     */
    private String contactName;


    @Schema(description = "下单时间")
    private LocalDateTime orderDate;
    @Schema(description = "预计交货日期")
    private LocalDate expectedDelivery;
    @Schema(description = "实际交货日期")
    private LocalDate actualDelivery;
    @Schema(description = "订单总金额")
    private BigDecimal totalAmount;
    @Schema(description = "货币代码")
    private String currency;
    @Schema(description = "整体折扣率")
    private BigDecimal discount;
    @Schema(description = "税率")
    private BigDecimal taxRate;
    @Schema(description = "税额")
    private BigDecimal taxAmount;
    @Schema(description = "订单总额（含税）")
    private BigDecimal grandTotal;
    @Schema(description = "付款条件：1-预付全款，2-货到付款，3-月结30天，4-月结60天，5-其他")
    private Integer paymentTerms;
    @Schema(description = "支付状态")
    private Integer paymentStatus;
    @Schema(description = "订单状态")
    private Integer orderStatus;
    @Schema(description = "收货地址")
    private String shippingAddress;
    @Schema(description = "运输方式")
    private Integer shippingMethod;
    @Schema(description = "物流单号")
    private String trackingNo;
    @Schema(description = "销售负责人ID")
    private Long salesRepId;

    /**
     * 销售负责人姓名
     */
    private String salesRepName;


    @Schema(description = "操作员ID")
    private Long operatorId;

    /**
     * 操作员姓名
     */
    private String operatorName;


    @Schema(description = "备注")
    private String notes;
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
