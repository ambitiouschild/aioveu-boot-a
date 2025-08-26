package com.aioveu.boot.aioveuCategory.model.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema( description = "库存分类选项")
public class CategoryOptionVO {

    private Long categoryId;
    /**
     * 库存分类名称
     */
    private String categoryName;
    /**
     * 带参数的构造函数
     *
     * @param categoryId 库存分类ID
     * @param categoryName 库存分类名称
     */
    public CategoryOptionVO(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}
