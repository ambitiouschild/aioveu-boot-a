package com.aioveu.boot.aioveuInventory.model.query;

import com.aioveu.boot.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

/**
 * 库存信息分页查询对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 21:56
 */
@Schema(description ="库存信息查询对象")
@Getter
@Setter
public class AioveuInventoryQuery extends BasePageQuery {

    @Schema(description = "库存ID")
    private Integer id;
    @Schema(description = "仓库ID")
    private Long warehouseId;

    private String warehouseName;
    @Schema(description = "物资ID")
    private Long materialId;

    private String materialName;
    @Schema(description = "生产日期")
    private List<String> productionDate;
    @Schema(description = "有效期至")
    private List<String> expiryDate;
    @Schema(description = "最后入库时间")
    private List<String> lastInbound;
    @Schema(description = "最后出库时间")
    private List<String> lastOutbound;
}
