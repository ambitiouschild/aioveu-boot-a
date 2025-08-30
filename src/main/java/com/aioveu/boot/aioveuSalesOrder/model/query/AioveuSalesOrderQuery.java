package com.aioveu.boot.aioveuSalesOrder.model.query;

import com.aioveu.boot.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

/**
 * 销售订单分页查询对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-22 00:27
 */
@Schema(description ="销售订单查询对象")
@Getter
@Setter
public class AioveuSalesOrderQuery extends BasePageQuery {

    @Schema(description = "订单ID（主键）")
    private Integer id;
    @Schema(description = "订单编号（唯一）")
    private String orderNo;
    @Schema(description = "客户ID")
    private Long customerId;

    private String customerName;

    @Schema(description = "下单时间")
    private List<String> orderDate;
    @Schema(description = "支付状态")
    private Integer paymentStatus;
    @Schema(description = "订单状态")
    private Integer orderStatus;
    @Schema(description = "运输方式")
    private Integer shippingMethod;
    @Schema(description = "销售负责人ID")
    private Long salesRepId;

    private String salesRepName;

}
