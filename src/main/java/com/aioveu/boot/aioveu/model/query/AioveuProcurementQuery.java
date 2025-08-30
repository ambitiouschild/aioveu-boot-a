package com.aioveu.boot.aioveu.model.query;

import com.aioveu.boot.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

/**
 * 采购流程分页查询对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:07
 */
@Schema(description ="采购流程查询对象")
@Getter
@Setter
public class AioveuProcurementQuery extends BasePageQuery {

    @Schema(description = "采购ID")
    private Integer id;
    @Schema(description = "采购单号")
    private String procurementNo;
    @Schema(description = "供应商ID")
    private Integer supplierId;
    @Schema(description = "物资ID")
    private Long materialId;

    private String materialName;

    @Schema(description = "下单时间")
    private List<String> orderDate;
    @Schema(description = "状态")
    private Integer status;
    @Schema(description = "申请人ID")
    private Long applicantId;

    private String applicantName;
}
