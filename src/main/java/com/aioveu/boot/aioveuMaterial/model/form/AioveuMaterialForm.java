package com.aioveu.boot.aioveuMaterial.model.form;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import jakarta.validation.constraints.*;

/**
 * 物资表单对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 21:17
 */
@Getter
@Setter
@Schema(description = "物资表单对象")
public class AioveuMaterialForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "物资名称")
    @NotBlank(message = "物资名称不能为空")
    @Size(max=100, message="物资名称长度不能超过100个字符")
    private String name;

    @Schema(description = "分类ID")
    private Long categoryId;

    private String categoryName;

    @Schema(description = "采购单价")
    @NotNull(message = "采购单价不能为空")
    private BigDecimal purchasePrice;

    @Schema(description = "供应商ID")
    private Integer supplierId;

    @Schema(description = "最低库存量")
    private Integer minStock;

    @Schema(description = "最高库存量")
    private Integer maxStock;

    @Schema(description = "启用状态")
    @NotNull(message = "启用状态不能为空")
    private Integer isActive;

    @Schema(description = "商品条码")
    @NotBlank(message = "商品条码不能为空")
    @Size(max=20, message="商品条码长度不能超过20个字符")
    private String barcode;


}
