package com.aioveu.boot.aioveuCategory.model.entity;

import lombok.Getter;
import lombok.Setter;
import com.baomidou.mybatisplus.annotation.TableName;
import com.aioveu.boot.common.base.BaseEntity;

/**
 * 物资分类实体对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 20:58
 */
@Getter
@Setter
@TableName("aioveu_category")
public class AioveuCategory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 分类名称
     */
    private String name;
    /**
     * 父分类ID
     */
    private Integer parentId;
    /**
     * 分类层级
     */
    private Integer level;
    /**
     * 分类描述
     */
    private String description;
}
