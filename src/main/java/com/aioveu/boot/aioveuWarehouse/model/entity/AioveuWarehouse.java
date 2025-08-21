package com.aioveu.boot.aioveuWarehouse.model.entity;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.aioveu.boot.common.base.BaseEntity;

/**
 * 仓库信息实体对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 21:42
 */
@Getter
@Setter
@TableName("aioveu_warehouse")
public class AioveuWarehouse extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 仓库名称
     */
    private String name;
    /**
     * 仓库编码
     */
    private String code;
    /**
     * 仓库位置
     */
    private String location;
    /**
     * 仓库面积
     */
    private BigDecimal area;
    /**
     * 存储容量
     */
    private BigDecimal capacity;
    /**
     * 负责人ID
     */
    private Integer managerId;
    /**
     * 联系电话
     */
    private String contactPhone;
    /**
     * 仓库描述
     */
    private String description;
    /**
     * 启用状态
     */
    private Integer isActive;
}
