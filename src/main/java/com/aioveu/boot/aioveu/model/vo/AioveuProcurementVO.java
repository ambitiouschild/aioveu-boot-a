package com.aioveu.boot.aioveu.model.vo;

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
 * 采购流程视图对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:07
 */
@Getter
@Setter
@Schema( description = "采购流程视图对象")
public class AioveuProcurementVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "采购ID")
    private Integer id;
    @Schema(description = "采购单号")
    private String procurementNo;
    @Schema(description = "供应商ID")
    private Integer supplierId;
    @Schema(description = "物资ID")
    private Long materialId;

    /**
     * 物资名称
     */
    private String materialName;

    @Schema(description = "采购数量")
    private Integer quantity;
    @Schema(description = "采购单价")
    private BigDecimal unitPrice;
    @Schema(description = "总金额")
    private BigDecimal totalAmount;
    @Schema(description = "下单时间")
    private LocalDateTime orderDate;
    @Schema(description = "预计到货日期")
    private LocalDate expectedDelivery;
    @Schema(description = "实际到货日期")
    private LocalDate actualDelivery;
    @Schema(description = "签收时间")
    private LocalDateTime receiptDate;
    @Schema(description = "入库仓库ID")
    private Long warehouseId;

    private String warehouseName;

    @Schema(description = "入库时间")
    private LocalDateTime inboundDate;
    @Schema(description = "状态")
    private Integer status;
    @Schema(description = "申请人ID")
    private Long applicantId;

    /**
     * 申请人姓名
     */
    private String applicantName;

    @Schema(description = "审核人ID")
    private Long reviewerId;

    /**
     * 审核人姓名
     */
    private String reviewerName;

    @Schema(description = "审核时间")
    private LocalDateTime reviewTime;
    @Schema(description = "备注")
    private String remark;
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
