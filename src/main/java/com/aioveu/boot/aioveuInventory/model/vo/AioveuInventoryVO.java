package com.aioveu.boot.aioveuInventory.model.vo;

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
 * 库存信息视图对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 21:56
 */
@Getter
@Setter
@Schema( description = "库存信息视图对象")
public class AioveuInventoryVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "库存ID")
    private Integer id;
    @Schema(description = "仓库ID")
    private Long warehouseId;

    /**
     * 仓库名称
     */
    private String warehouseName;

    @Schema(description = "物资ID")
    private Long materialId;

    /**
     * 物资名称
     */
    private String MaterialName;

    @Schema(description = "当前库存数量")
    private BigDecimal quantity;
    @Schema(description = "批次号")
    private String batchNumber;
    @Schema(description = "生产日期")
    private LocalDate productionDate;
    @Schema(description = "有效期至")
    private LocalDate expiryDate;
    @Schema(description = "最后入库时间")
    private LocalDateTime lastInbound;
    @Schema(description = "最后出库时间")
    private LocalDateTime lastOutbound;
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
