package com.aioveu.boot.aioveuDepartment.model.form;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;

/**
 * 公司部门组织结构表单对象
 *
 * @author aioveu
 * @since 2025-08-18 14:42
 */
@Getter
@Setter
@Schema(description = "公司部门组织结构表单对象")
public class AioveuDepartmentForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "部门名称")
    @NotBlank(message = "部门名称不能为空")
    @Size(max=50, message="部门名称长度不能超过50个字符")
    private String deptName;

    @Schema(description = "上级部门ID")
    private Long parentDeptId;

    private String parentDeptName;

    @Schema(description = "部门经理")
    private Integer managerId;


    private String managerName;


}
