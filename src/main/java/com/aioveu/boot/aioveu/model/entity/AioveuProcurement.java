package com.aioveu.boot.aioveu.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.aioveu.boot.common.base.BaseEntity;

/**
 * 采购流程实体对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:07
 */
@Getter
@Setter
@TableName("aioveu_procurement")
public class AioveuProcurement extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 采购单号
     */
    private String procurementNo;
    /**
     * 供应商ID
     */
    private Integer supplierId;
    /**
     * 物资ID
     */
    private Integer materialId;
    /**
     * 采购数量
     */
    private Integer quantity;
    /**
     * 采购单价
     */
    private BigDecimal unitPrice;
    /**
     * 总金额
     */
    private BigDecimal totalAmount;
    /**
     * 下单时间
     */
    private LocalDateTime orderDate;
    /**
     * 预计到货日期
     */
    private LocalDate expectedDelivery;
    /**
     * 实际到货日期
     */
    private LocalDate actualDelivery;
    /**
     * 签收时间
     */
    private LocalDateTime receiptDate;
    /**
     * 入库仓库ID
     */
    private Integer warehouseId;
    /**
     * 入库时间
     */
    private LocalDateTime inboundDate;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 申请人ID
     */
    private Integer applicantId;
    /**
     * 审核人ID
     */
    private Integer reviewerId;
    /**
     * 审核时间
     */
    private LocalDateTime reviewTime;
    /**
     * 备注
     */
    private String remark;
}
