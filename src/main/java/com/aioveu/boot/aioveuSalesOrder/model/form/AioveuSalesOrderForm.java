package com.aioveu.boot.aioveuSalesOrder.model.form;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import jakarta.validation.constraints.*;

/**
 * 销售订单表单对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-22 00:27
 */
@Getter
@Setter
@Schema(description = "销售订单表单对象")
public class AioveuSalesOrderForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "订单编号（唯一）")
    @NotBlank(message = "订单编号（唯一）不能为空")
    @Size(max=50, message="订单编号（唯一）长度不能超过50个字符")
    private String orderNo;

    @Schema(description = "客户ID")
    private Long customerId;

    private String customerName;

    @Schema(description = "联系人ID")
    private Long contactId;

    private String contactName;

    @Schema(description = "下单时间")
    @NotNull(message = "下单时间不能为空")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;

    @Schema(description = "预计交货日期")
    private LocalDate expectedDelivery;

    @Schema(description = "实际交货日期")
    private LocalDate actualDelivery;

    @Schema(description = "订单总金额")
    @NotNull(message = "订单总金额不能为空")
    private BigDecimal totalAmount;

    @Schema(description = "货币代码")
    @Size(max=3, message="货币代码长度不能超过3个字符")
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
    @NotNull(message = "支付状态不能为空")
    private Integer paymentStatus;

    @Schema(description = "订单状态")
    @NotNull(message = "订单状态不能为空")
    private Integer orderStatus;

    @Schema(description = "收货地址")
    @Size(max=200, message="收货地址长度不能超过200个字符")
    private String shippingAddress;

    @Schema(description = "运输方式")
    private Integer shippingMethod;

    @Schema(description = "物流单号")
    @Size(max=50, message="物流单号长度不能超过50个字符")
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
    @Size(max=65535, message="备注长度不能超过65535个字符")
    private String notes;


}
