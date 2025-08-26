package com.aioveu.boot.aioveuCategory.model.vo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 物资分类视图对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 20:58
 */
@Getter
@Setter
@Schema( description = "物资分类视图对象")
public class AioveuCategoryVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "分类ID")
    private Long id;
    @Schema(description = "分类名称")
    private String name;
    @Schema(description = "父分类ID")
    private Long parentId;

    private String parentCategoryName;


    @Schema(description = "分类层级")
    private Integer level;
    @Schema(description = "分类描述")
    private String description;
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
