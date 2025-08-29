package com.aioveu.boot.aioveuOutbound.model.vo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * 出库记录视图对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:26
 */
@Getter
@Setter
@Schema( description = "出库记录视图对象")
public class AioveuOutboundVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "出库ID")
    private Integer id;
    @Schema(description = "出库单号")
    private String outboundNo;
    @Schema(description = "物资ID")
    private Long materialId;

    /**
     * 物资名称
     */
    private String materialName;

    @Schema(description = "仓库ID")
    private Long warehouseId;

    /**
     * 仓库名称
     */
    private String warehouseName;


    @Schema(description = "出库数量")
    private BigDecimal quantity;
    @Schema(description = "批次号")
    private String batchNumber;
    @Schema(description = "出库时间")
    private LocalDateTime outTime;
    @Schema(description = "操作员ID")
    private Long operatorId;

    /**
     * 操作员姓名
     */
    private String operatorName;


    @Schema(description = "领用人ID")
    private Long recipientId;

    /**
     * 领用人姓名
     */
    private String recipientName;


    @Schema(description = "领用部门ID")
    private Long departmentId;

    private String departmentName;

    @Schema(description = "用途说明")
    private String purpose;
    @Schema(description = "关联项目ID")
    private Integer projectId;
    @Schema(description = "状态")
    private Integer status;
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
