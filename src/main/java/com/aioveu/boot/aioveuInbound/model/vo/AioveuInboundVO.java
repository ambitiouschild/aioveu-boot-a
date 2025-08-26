package com.aioveu.boot.aioveuInbound.model.vo;

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
 * 入库信息视图对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 22:18
 */
@Getter
@Setter
@Schema( description = "入库信息视图对象")
public class AioveuInboundVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "入库ID（主键）")
    private Integer id;
    @Schema(description = "入库单号")
    private String inboundNo;
    @Schema(description = "物资ID")
    private Long materialId;

    /**
     * 物资名称
     */
    private String MaterialName;

    @Schema(description = "仓库ID")
    private Long warehouseId;

    /**
     * 仓库名称
     */
    private String warehouseName;

    @Schema(description = "入库数量（支持小数计量）")
    private BigDecimal quantity;
    @Schema(description = "入库单价")
    private BigDecimal unitPrice;
    @Schema(description = "总金额")
    private BigDecimal totalAmount;
    @Schema(description = "批次号")
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
    private LocalDateTime inTime;
    @Schema(description = "操作员ID")
    private Long operatorId;

    /**
     * 员工姓名
     */
    private String operatorName;


    @Schema(description = "备注")
    private String remark;
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
