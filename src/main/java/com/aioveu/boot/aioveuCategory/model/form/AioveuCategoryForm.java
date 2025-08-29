package com.aioveu.boot.aioveuCategory.model.form;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;

/**
 * 物资分类表单对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 20:58
 */
@Getter
@Setter
@Schema(description = "物资分类表单对象")
public class AioveuCategoryForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "分类名称")
    @NotBlank(message = "分类名称不能为空")
    @Size(max=50, message="分类名称长度不能超过50个字符")
    private String name;

    @Schema(description = "父分类ID")
    private Long parentId;

    private String parentCategoryName;

    @Schema(description = "分类层级")
    private Integer level;

    @Schema(description = "分类描述")
    @Size(max=200, message="分类描述长度不能超过200个字符")
    private String description;


}
