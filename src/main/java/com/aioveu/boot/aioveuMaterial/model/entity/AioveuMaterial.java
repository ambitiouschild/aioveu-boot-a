package com.aioveu.boot.aioveuMaterial.model.entity;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.aioveu.boot.common.base.BaseEntity;

/**
 * 物资实体对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 21:17
 */
@Getter
@Setter
@TableName("aioveu_material")
public class AioveuMaterial extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 物资名称
     */
    private String name;
    /**
     * 分类ID
     */
    private Integer categoryId;
    /**
     * 单位
     */
    private String unit;
    /**
     * 规格型号
     */
    private String spec;
    /**
     * 采购单价
     */
    private BigDecimal purchasePrice;
    /**
     * 供应商ID
     */
    private Integer supplierId;
    /**
     * 最低库存量
     */
    private Integer minStock;
    /**
     * 最高库存量
     */
    private Integer maxStock;
    /**
     * 启用状态
     */
    private Integer isActive;
    /**
     * 商品条码
     */
    private String barcode;
}
