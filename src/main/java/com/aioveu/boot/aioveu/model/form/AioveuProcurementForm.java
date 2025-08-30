package com.aioveu.boot.aioveu.model.form;

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
 * 采购流程表单对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:07
 */
@Getter
@Setter
@Schema(description = "采购流程表单对象")
public class AioveuProcurementForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "采购单号")
    @NotBlank(message = "采购单号不能为空")
    @Size(max=50, message="采购单号长度不能超过50个字符")
    private String procurementNo;

    @Schema(description = "供应商ID")
    private Integer supplierId;

    @Schema(description = "物资ID")
    private Long materialId;

    private String materialName;

    @Schema(description = "采购数量")
    @NotNull(message = "采购数量不能为空")
    private Integer quantity;

    @Schema(description = "采购单价")
    @NotNull(message = "采购单价不能为空")
    private BigDecimal unitPrice;

    @Schema(description = "总金额")
    @NotNull(message = "总金额不能为空")
    private BigDecimal totalAmount;

    @Schema(description = "下单时间")
    @NotNull(message = "下单时间不能为空")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;

    @Schema(description = "预计到货日期")
    private LocalDate expectedDelivery;

    @Schema(description = "实际到货日期")
    private LocalDate actualDelivery;

    @Schema(description = "签收时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime receiptDate;

    @Schema(description = "入库仓库ID")
    private Long warehouseId;

    private String warehouseName;

    @Schema(description = "入库时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime inboundDate;

    @Schema(description = "状态")
    @NotNull(message = "状态不能为空")
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
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reviewTime;

    @Schema(description = "备注")
    @Size(max=500, message="备注长度不能超过500个字符")
    private String remark;


}
