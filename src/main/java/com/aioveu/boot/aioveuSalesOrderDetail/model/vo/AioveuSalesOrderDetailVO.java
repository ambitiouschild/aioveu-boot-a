package com.aioveu.boot.aioveuSalesOrderDetail.model.vo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

/**
 * 订单明细视图对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-22 00:42
 */
@Getter
@Setter
@Schema( description = "订单明细视图对象")
public class AioveuSalesOrderDetailVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "明细ID")
    private Integer id;
    @Schema(description = "订单ID")
    private Long orderId;

    /**
     * 订单名称
     */
    private String salesOrderNo;

    @Schema(description = "物资ID")
    private Long materialId;
    /**
     * 物资名称
     */
    private String materialName;

    @Schema(description = "数量")
    private BigDecimal quantity;
    @Schema(description = "单价")
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
    private String batchNumber;
    @Schema(description = "要求交货日期")
    private LocalDate deliveryDate;
    @Schema(description = "发货仓库ID")
    private Long warehouseId;

    /**
     * 发货仓库名称
     */
    private String warehouseName;

    @Schema(description = "明细状态")
    private Integer status;
    @Schema(description = "备注")
    private String notes;
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
