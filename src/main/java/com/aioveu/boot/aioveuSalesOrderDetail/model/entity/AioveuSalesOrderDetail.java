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
     *  移除字段声明后，不再有字段与方法冲突,
     *  Lombok 不会为这些字段生成 getter
     *  的自定义 getter 方法会被正确调用
     */

    /**
     * 小计
     */
    private BigDecimal subtotal;
    /**
     * 税额
     */
    private BigDecimal taxAmount;
    /**
     * 总金额
     */
    private BigDecimal totalPrice;

    /**
     * 是的，您理解得很正确。既然已经在实体类中实现了计算逻辑（通过 getter 方法），那么服务层就不需要再单独实现计算逻辑了
     * 1.实体类已经处理了计算
     * 当访问 getSubtotal()时，会自动计算小计
     * 当访问 getTaxAmount()时，会自动计算税额
     * 当访问 getTotalPrice()时，会自动计算总金额
     * 2.服务层只需正常使用实体类：
     *  在保存和更新操作中，直接使用实体类
     *  计算会在 getter 方法被调用时自动完成
     *  不需要在服务层添加额外的计算代码
     *
     *  为什么这样设计是合理的？
     * 1.封装性：
     * •计算逻辑封装在实体类内部
     * •服务层不需要关心具体计算细节
     * 2.一致性：
     * •无论在任何地方访问这些字段，计算结果都是一致的
     * •避免了在多个地方重复计算逻辑
     * 3.简化服务层：
     * •服务层保持简洁，只关注业务逻辑
     * •不需要添加额外的计算代码
     * 4.实时计算：
     * •每次访问字段都会重新计算，确保数据最新
     * •不需要在保存前手动计算
     *
     * 自定义的 getter 方法会覆盖 Lombok 的默认实现
     */

    // 计算小计  小计 = 数量 × (单价 × (1 - 折扣率))
    public BigDecimal getSubtotal() {
        if (quantity == null || unitPrice == null || discount == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal discountedPrice = unitPrice.multiply(BigDecimal.ONE.subtract(discount));
        return quantity.multiply(discountedPrice);
    }

    // 计算税额  税额 = 小计 × 税率
    public BigDecimal getTaxAmount() {
        if (taxRate == null || getSubtotal() == null) {
            return BigDecimal.ZERO;
        }
        return getSubtotal().multiply(taxRate);
    }

    // 计算总金额  总金额 = 小计 + 税额
    public BigDecimal getTotalPrice() {
        return getSubtotal().add(getTaxAmount());
    }

    //// 计算小计
    //    public BigDecimal calculateSubtotal() {
    //        if (quantity == null || unitPrice == null || discount == null) {
    //            return BigDecimal.ZERO;
    //        }
    //        BigDecimal discountedPrice = unitPrice.multiply(BigDecimal.ONE.subtract(discount));
    //        return quantity.multiply(discountedPrice).setScale(4, RoundingMode.HALF_UP);
    //    }
    //
    //    // 计算税额
    //    public BigDecimal calculateTaxAmount() {
    //        if (taxRate == null) {
    //            return BigDecimal.ZERO;
    //        }
    //        return calculateSubtotal().multiply(taxRate).setScale(4, RoundingMode.HALF_UP);
    //    }
    //
    //    // 计算总金额
    //    public BigDecimal calculateTotalPrice() {
    //        return calculateSubtotal().add(calculateTaxAmount()).setScale(4, RoundingMode.HALF_UP);
    //    }
    //
    //    // 在保存前计算所有值
    //    public void calculateAll() {
    //        this.subtotal = calculateSubtotal();
    //        this.taxAmount = calculateTaxAmount();
    //        this.totalPrice = calculateTotalPrice();
    //    }

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
