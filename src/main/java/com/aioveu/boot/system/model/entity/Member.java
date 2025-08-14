package com.aioveu.boot.system.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import com.aioveu.boot.common.base.BaseEntity;

import java.time.LocalDateTime;

/**
 * 会员信息实体对象
 *
 * @author ambitiouschild
 * @since 2025-07-11 15:13
 */
@Getter
@Setter
@TableName("member")
public class Member extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 会员姓名
     */
    private String name;
    /**
     * 会员手机号
     */
    private String mobile;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 会员年龄
     */
    private Integer age;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 是否删除(1:已删除;0:未删除)
     */
    private Integer isDeleted;

    // 使用乐观锁（如果配置了）
    @Version
    private Integer version;

}
