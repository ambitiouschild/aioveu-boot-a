package com.aioveu.boot.aioveuCategory.model.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema( description = "库存父分类选项")

public class ParentCategoryOptionVO {

    private Long parentCategoryId;
    /**
     * 库存父分类名称
     */
    private String parenCategorytname;
    /**
     * 带参数的构造函数
     *
     * @param parentCategoryId 库存父分类ID
     * @param parenCategorytname 库存父分类名称
     */
    public ParentCategoryOptionVO(Long parentCategoryId, String parenCategorytname) {
        this.parentCategoryId = parentCategoryId;
        this.parenCategorytname = parenCategorytname;
    }
}
