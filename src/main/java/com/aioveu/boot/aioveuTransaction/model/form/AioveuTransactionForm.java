package com.aioveu.boot.aioveuTransaction.model.form;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import jakarta.validation.constraints.*;

/**
 * 客户交易记录表单对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-22 00:16
 */
@Getter
@Setter
@Schema(description = "客户交易记录表单对象")
public class AioveuTransactionForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "交易编号")
    @NotBlank(message = "交易编号不能为空")
    @Size(max=50, message="交易编号长度不能超过50个字符")
    private String transactionNo;

    @Schema(description = "客户ID")
    private Long customerId;

    private String customerName;

    @Schema(description = "联系人ID")
    private Long contactId;

    private String contactName;

    @Schema(description = "交易日期")
    @NotNull(message = "交易日期不能为空")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime transactionDate;

    @Schema(description = "交易金额")
    @NotNull(message = "交易金额不能为空")
    private BigDecimal amount;

    @Schema(description = "货币代码")
    @Size(max=3, message="货币代码长度不能超过3个字符")
    private String currency;

    @Schema(description = "支付方式")
    @NotNull(message = "支付方式不能为空")
    private Integer paymentMethod;

    @Schema(description = "支付状态")
    @NotNull(message = "支付状态不能为空")
    private Integer paymentStatus;

    @Schema(description = "交易类型")
    @NotNull(message = "交易类型不能为空")
    private Integer transactionType;

    @Schema(description = "发票号码")
    @Size(max=50, message="发票号码长度不能超过50个字符")
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
    @NotNull(message = "交易状态不能为空")
    private Integer transactionStatus;

    @Schema(description = "备注")
    @Size(max=65535, message="备注长度不能超过65535个字符")
    private String notes;


}
