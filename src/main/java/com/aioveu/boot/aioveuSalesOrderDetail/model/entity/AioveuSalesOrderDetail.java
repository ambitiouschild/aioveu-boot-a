package com.aioveu.boot.aioveuSalesOrderDetail.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableName;
import com.aioveu.boot.common.base.BaseEntity;

/**
 * 订单明细实体对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-22 00:42
 */
@Getter
@Setter
@TableName("aioveu_sales_order_detail")
public class AioveuSalesOrderDetail extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    private Integer orderId;
    /**
     * 物资ID
     */
    private Integer materialId;
    /**
     * 数量
     */
    private BigDecimal quantity;
    /**
     * 单价
     */
    private BigDecimal unitPrice;
    /**
     * 折扣率
     */
    private BigDecimal discount;
    /**
     * 税率
     */
    private BigDecimal taxRate;
    /**
     * 小计
     */
    // 添加注解忽略生成列
    @TableField(exist = false)
    private BigDecimal subtotal;
    /**
     * 税额
     */
    @TableField(exist = false)
    private BigDecimal taxAmount;
    /**
     * 总金额
     */
    @TableField(exist = false)
    private BigDecimal totalPrice;
    /**
     * 批次号
     */
    private String batchNumber;
    /**
     * 要求交货日期
     */
    private LocalDate deliveryDate;
    /**
     * 发货仓库ID
     */
    private Integer warehouseId;
    /**
     * 明细状态
     */
    private Integer status;
    /**
     * 备注
     */
    private String notes;
}
