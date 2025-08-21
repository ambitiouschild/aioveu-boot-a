package com.aioveu.boot.aioveuSalesOrderDetail.model.query;

import com.aioveu.boot.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

/**
 * 订单明细分页查询对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-22 00:42
 */
@Schema(description ="订单明细查询对象")
@Getter
@Setter
public class AioveuSalesOrderDetailQuery extends BasePageQuery {

    @Schema(description = "明细ID")
    private Integer id;
    @Schema(description = "订单ID")
    private Integer orderId;
    @Schema(description = "物资ID")
    private Integer materialId;
    @Schema(description = "发货仓库ID")
    private Integer warehouseId;
    @Schema(description = "明细状态")
    private Integer status;
}
