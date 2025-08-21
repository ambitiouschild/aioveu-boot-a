package com.aioveu.boot.aioveuCategory.model.query;

import com.aioveu.boot.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 物资分类分页查询对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 20:58
 */
@Schema(description ="物资分类查询对象")
@Getter
@Setter
public class AioveuCategoryQuery extends BasePageQuery {

    @Schema(description = "分类ID")
    private Integer id;
    @Schema(description = "分类名称")
    private String name;
    @Schema(description = "分类描述")
    private String description;
}
