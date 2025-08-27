package com.aioveu.boot.aioveuDepartment.model.vo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 公司部门组织结构视图对象
 *
 * @author aioveu
 * @since 2025-08-18 14:42
 */
@Getter
@Setter
@Schema( description = "公司部门组织结构视图对象")
public class AioveuDepartmentVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "部门ID")
    private Long deptId;
    @Schema(description = "部门名称")
    private String deptName;
    @Schema(description = "上级部门ID")
    private Long parentDeptId;

    /**
     * 上级部门名称
     */
    private String parentDeptName;

    //打破循环调用
//    @Schema(description = "部门经理")
//    private Long managerId;

    /**
     * 部门经理姓名
     */
    private String managerName;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;




}
