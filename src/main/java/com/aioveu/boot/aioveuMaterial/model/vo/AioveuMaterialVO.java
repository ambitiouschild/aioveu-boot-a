package com.aioveu.boot.aioveuMaterial.model.vo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

/**
 * 物资视图对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 21:17
 */
@Getter
@Setter
@Schema( description = "物资视图对象")
public class AioveuMaterialVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "物资ID")
    private Integer id;
    @Schema(description = "物资名称")
    private String name;
    @Schema(description = "分类ID")
    private Long categoryId;

    /**
     * 库存分类名称
     */
    private String categoryName;

    @Schema(description = "单位")
    private String unit;
    @Schema(description = "规格型号")
    private String spec;
    @Schema(description = "采购单价")
    private BigDecimal purchasePrice;
    @Schema(description = "供应商ID")
    private Integer supplierId;
    @Schema(description = "最低库存量")
    private Integer minStock;
    @Schema(description = "最高库存量")
    private Integer maxStock;
    @Schema(description = "启用状态")
    private Integer isActive;
    @Schema(description = "商品条码")
    private String barcode;
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
