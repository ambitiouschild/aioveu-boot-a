package com.aioveu.boot.aioveuOutbound.model.query;

import com.aioveu.boot.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

/**
 * 出库记录分页查询对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:26
 */
@Schema(description ="出库记录查询对象")
@Getter
@Setter
public class AioveuOutboundQuery extends BasePageQuery {

    @Schema(description = "出库ID")
    private Integer id;
    @Schema(description = "出库单号")
    private String outboundNo;
    @Schema(description = "物资ID")
    private Long materialId;

    private String materialName;

    @Schema(description = "仓库ID")
    private Long warehouseId;

    private String warehouseName;
    @Schema(description = "出库时间")
    private List<String> outTime;
    @Schema(description = "领用人ID")
    private Long recipientId;

    private String recipientName;

    @Schema(description = "状态")
    private Integer status;
}
