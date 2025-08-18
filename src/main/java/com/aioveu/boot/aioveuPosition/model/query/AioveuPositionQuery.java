package com.aioveu.boot.aioveuPosition.model.query;

import com.aioveu.boot.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 公司岗位信息分页查询对象
 *
 * @author aioveu
 * @since 2025-08-18 16:26
 */
@Schema(description ="公司岗位信息查询对象")
@Getter
@Setter
public class AioveuPositionQuery extends BasePageQuery {

    @Schema(description = "岗位ID")
    private Integer positionId;
    @Schema(description = "岗位名称")
    private String positionName;
    @Schema(description = "所属部门ID")
    private Integer deptId;
    @Schema(description = "职级(1-10)")
    private Integer positionLevel;
    @Schema(description = "岗位描述")
    private String description;
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
