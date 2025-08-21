package com.aioveu.boot.aioveuTransaction.model.entity;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.aioveu.boot.common.base.BaseEntity;

/**
 * 客户交易记录实体对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-22 00:16
 */
@Getter
@Setter
@TableName("aioveu_transaction")
public class AioveuTransaction extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 交易编号
     */
    private String transactionNo;
    /**
     * 客户ID
     */
    private Integer customerId;
    /**
     * 联系人ID
     */
    private Integer contactId;
    /**
     * 交易日期
     */
    private LocalDateTime transactionDate;
    /**
     * 交易金额
     */
    private BigDecimal amount;
    /**
     * 货币代码
     */
    private String currency;
    /**
     * 支付方式
     */
    private Integer paymentMethod;
    /**
     * 支付状态
     */
    private Integer paymentStatus;
    /**
     * 交易类型
     */
    private Integer transactionType;
    /**
     * 发票号码
     */
    private String invoiceNo;
    /**
     * 产品ID
     */
    private Integer productId;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 单价
     */
    private BigDecimal unitPrice;
    /**
     * 折扣率
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
     * 总金额
     */
    private BigDecimal totalAmount;
    /**
     * 销售负责人ID
     */
    private Integer salesRepId;
    /**
     * 交易状态
     */
    private Integer transactionStatus;
    /**
     * 备注
     */
    private String notes;
}
