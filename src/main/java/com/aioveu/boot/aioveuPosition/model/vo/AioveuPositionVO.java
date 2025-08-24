package com.aioveu.boot.aioveuPosition.model.vo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 公司岗位信息视图对象
 *
 * @author aioveu
 * @since 2025-08-18 16:26
 */
@Getter
@Setter
@Schema( description = "公司岗位信息视图对象")
public class AioveuPositionVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "岗位ID")
    private Long positionId;
    @Schema(description = "岗位名称")
    private String positionName;
    @Schema(description = "所属部门ID")
    private Long deptId;    // 部门ID，用于关联和存储

    /**
     * 部门名称
     */
    private String deptName;   // 新增：部门名，用于前端显示

    @Schema(description = "职级(1-10)")
    private Integer positionLevel;
    @Schema(description = "岗位描述")
    private String description;
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
