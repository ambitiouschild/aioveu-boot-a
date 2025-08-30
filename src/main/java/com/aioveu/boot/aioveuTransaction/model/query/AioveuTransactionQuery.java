package com.aioveu.boot.aioveuTransaction.model.query;

import com.aioveu.boot.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

/**
 * 客户交易记录分页查询对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-22 00:16
 */
@Schema(description ="客户交易记录查询对象")
@Getter
@Setter
public class AioveuTransactionQuery extends BasePageQuery {

    @Schema(description = "交易ID")
    private Integer id;
    @Schema(description = "交易编号")
    private String transactionNo;
    @Schema(description = "客户ID")
    private Long customerId;

    private String customerName;

    @Schema(description = "联系人ID")
    private Long contactId;

    private String contactName;

    @Schema(description = "交易日期")
    private List<String> transactionDate;
    @Schema(description = "支付方式")
    private Integer paymentMethod;
    @Schema(description = "支付状态")
    private Integer paymentStatus;
    @Schema(description = "交易类型")
    private Integer transactionType;
    @Schema(description = "交易状态")
    private Integer transactionStatus;
}
