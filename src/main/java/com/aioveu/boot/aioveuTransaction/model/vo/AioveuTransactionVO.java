package com.aioveu.boot.aioveuTransaction.model.vo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * 客户交易记录视图对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-22 00:16
 */
@Getter
@Setter
@Schema( description = "客户交易记录视图对象")
public class AioveuTransactionVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "交易ID")
    private Integer id;
    @Schema(description = "交易编号")
    private String transactionNo;
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

    @Schema(description = "交易日期")
    private LocalDateTime transactionDate;
    @Schema(description = "交易金额")
    private BigDecimal amount;
    @Schema(description = "货币代码")
    private String currency;
    @Schema(description = "支付方式")
    private Integer paymentMethod;
    @Schema(description = "支付状态")
    private Integer paymentStatus;
    @Schema(description = "交易类型")
    private Integer transactionType;
    @Schema(description = "发票号码")
    private String invoiceNo;
    @Schema(description = "产品ID")
    private Integer productId;
    @Schema(description = "数量")
    private Integer quantity;
    @Schema(description = "单价")
    private BigDecimal unitPrice;
    @Schema(description = "折扣率")
    private BigDecimal discount;
    @Schema(description = "税率")
    private BigDecimal taxRate;
    @Schema(description = "税额")
    private BigDecimal taxAmount;
    @Schema(description = "总金额")
    private BigDecimal totalAmount;
    @Schema(description = "销售负责人ID")
    private Long salesRepId;

    /**
     * 销售负责人姓名
     */
    private String salesRepName;

    @Schema(description = "交易状态")
    private Integer transactionStatus;
    @Schema(description = "备注")
    private String notes;
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
