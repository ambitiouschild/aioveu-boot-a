package com.aioveu.boot.aioveuMaterial.model.query;

import com.aioveu.boot.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

/**
 * 物资分页查询对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 21:17
 */
@Schema(description ="物资查询对象")
@Getter
@Setter
public class AioveuMaterialQuery extends BasePageQuery {

    @Schema(description = "物资ID")
    private Integer id;
    @Schema(description = "物资名称")
    private String name;
    @Schema(description = "分类ID")
    private Long categoryId;

    private String categoryName;

    @Schema(description = "启用状态")
    private Integer isActive;
    @Schema(description = "商品条码")
    private String barcode;
}
