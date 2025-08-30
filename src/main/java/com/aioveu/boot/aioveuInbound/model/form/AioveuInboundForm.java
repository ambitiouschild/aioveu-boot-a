package com.aioveu.boot.aioveuInbound.model.form;

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
 * 入库信息表单对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 22:18
 */
@Getter
@Setter
@Schema(description = "入库信息表单对象")
public class AioveuInboundForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "入库单号")
    @NotBlank(message = "入库单号不能为空")
    @Size(max=50, message="入库单号长度不能超过50个字符")
    private String inboundNo;

    @Schema(description = "物资ID")
    private Long materialId;

    private String materialName;

    @Schema(description = "仓库ID")
    private Long warehouseId;

    private String warehouseName;

    @Schema(description = "入库数量（支持小数计量）")
    @NotNull(message = "入库数量（支持小数计量）不能为空")
    private BigDecimal quantity;

    @Schema(description = "入库单价")
    @NotNull(message = "入库单价不能为空")
    private BigDecimal unitPrice;

    @Schema(description = "总金额")
    @NotNull(message = "总金额不能为空")
    private BigDecimal totalAmount;

    @Schema(description = "批次号")
    @Size(max=50, message="批次号长度不能超过50个字符")
    private String batchNumber;

    @Schema(description = "生产日期")
    private LocalDate productionDate;

    @Schema(description = "有效期至")
    private LocalDate expiryDate;

    @Schema(description = "供应商ID")
    private Integer supplierId;

    @Schema(description = "入库类型")
    private Integer inboundType;

    @Schema(description = "入库时间")
    @NotNull(message = "入库时间不能为空")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime inTime;

    @Schema(description = "操作员ID")
    private Long operatorId;

    /**
     * 员工姓名
     */
    private String operatorName;

    @Schema(description = "备注")
    @Size(max=500, message="备注长度不能超过500个字符")
    private String remark;


}
