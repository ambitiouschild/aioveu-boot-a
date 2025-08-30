package com.aioveu.boot.aioveuSalesOrderDetail.model.form;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.*;

/**
 * 订单明细表单对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-22 00:42
 */
@Getter
@Setter
@Schema(description = "订单明细表单对象")
public class AioveuSalesOrderDetailForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "订单ID")
    private Long orderId;

    private String orderName;

    @Schema(description = "物资ID")
    private Long materialId;

    private String materialName;

    @Schema(description = "数量")
    @NotNull(message = "数量不能为空")
    private BigDecimal quantity;

    @Schema(description = "单价")
    @NotNull(message = "单价不能为空")
    private BigDecimal unitPrice;

    @Schema(description = "折扣率")
    private BigDecimal discount;

    @Schema(description = "税率")
    private BigDecimal taxRate;

    @Schema(description = "小计")
    private BigDecimal subtotal;

    @Schema(description = "税额")
    private BigDecimal taxAmount;

    @Schema(description = "总金额")
    private BigDecimal totalPrice;

    @Schema(description = "批次号")
    @Size(max=50, message="批次号长度不能超过50个字符")
    private String batchNumber;

    @Schema(description = "要求交货日期")
    private LocalDate deliveryDate;

    @Schema(description = "发货仓库ID")
    private Long warehouseId;

    private String warehouseName;

    @Schema(description = "明细状态")
    private Integer status;

    @Schema(description = "备注")
    @Size(max=200, message="备注长度不能超过200个字符")
    private String notes;


}
