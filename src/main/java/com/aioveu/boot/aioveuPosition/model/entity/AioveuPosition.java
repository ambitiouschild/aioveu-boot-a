package com.aioveu.boot.aioveuPosition.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import com.aioveu.boot.common.base.BaseEntity;

import java.time.LocalDateTime;

/**
 * 公司岗位信息实体对象
 *
 * @author aioveu
 * @since 2025-08-18 16:26
 */
@Getter
@Setter
@TableName("aioveu_position")
public class AioveuPosition {

    private static final long serialVersionUID = 1L;

    /**
     * 岗位ID
     */
    @TableId(value = "position_id", type = IdType.AUTO)
    private Long positionId;
    /**
     * 岗位名称
     */
    private String positionName;
    /**
     * 所属部门ID
     */
    private Long deptId;
    /**
     * 职级(1-10)
     */
    private Integer positionLevel;
    /**
     * 岗位描述
     */
    private String description;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
