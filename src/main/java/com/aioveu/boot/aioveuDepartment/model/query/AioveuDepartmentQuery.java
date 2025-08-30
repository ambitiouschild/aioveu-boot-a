package com.aioveu.boot.aioveuDepartment.model.query;

import com.aioveu.boot.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 公司部门组织结构分页查询对象
 *
 * @author aioveu
 * @since 2025-08-18 14:42
 */
@Schema(description ="公司部门组织结构查询对象")
@Getter
@Setter
public class AioveuDepartmentQuery extends BasePageQuery {

    @Schema(description = "部门ID")
    private Long deptId;
    @Schema(description = "部门名称")
    private String deptName;
    @Schema(description = "上级部门ID")

    private Long parentDeptId;
    @Schema(description = "部门名称")
    private String parentDeptName;

    @Schema(description = "部门经理")
    private Integer managerId;
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
