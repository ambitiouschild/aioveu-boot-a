package com.aioveu.boot.aioveuPosition.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema( description = "职位选项")
public class PositionOptionVO {

    private Long positionId;
    /**
     * 岗位名称
     */
    private String positionName;

    /**
     * 带参数的构造函数
     *
     * @param positionId 部门ID
     * @param positionName 部门名称
     */
    public PositionOptionVO(Long positionId, String positionName) {
        this.positionId = positionId;
        this.positionName = positionName;
    }
}
