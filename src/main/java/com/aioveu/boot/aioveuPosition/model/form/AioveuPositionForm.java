package com.aioveu.boot.aioveuPosition.model.form;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;

/**
 * 公司岗位信息表单对象
 *
 * @author aioveu
 * @since 2025-08-18 16:26
 */
@Getter
@Setter
@Schema(description = "公司岗位信息表单对象")
public class AioveuPositionForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "岗位名称")
    @NotBlank(message = "岗位名称不能为空")
    @Size(max=50, message="岗位名称长度不能超过50个字符")
    private String positionName;

    @Schema(description = "所属部门ID")
    private Long deptId;    // 部门ID，用于存储

    /**
     * 所属部门名称
     */
    private String deptName;

    @Schema(description = "职级(1-10)")
    private Integer positionLevel;

    @Schema(description = "岗位描述")
    @Size(max=65535, message="岗位描述长度不能超过65535个字符")
    private String description;


}
