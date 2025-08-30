package com.aioveu.boot.aioveuInbound.model.query;

import com.aioveu.boot.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.LinkOption;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

/**
 * 入库信息分页查询对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 22:18
 */
@Schema(description ="入库信息查询对象")
@Getter
@Setter
public class AioveuInboundQuery extends BasePageQuery {

    @Schema(description = "入库ID（主键）")
    private Integer id;
    @Schema(description = "入库单号")
    private String inboundNo;
    @Schema(description = "物资ID")
    private Long materialId;

    private String materialName;


    @Schema(description = "仓库ID")
    private Long warehouseId;

    private String warehouseName;

    @Schema(description = "入库类型")
    private Integer inboundType;
    @Schema(description = "入库时间")
    private List<String> inTime;
    @Schema(description = "操作员ID")
    private Long operatorId;

    private String operatorName;
}
