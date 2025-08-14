package com.aioveu.boot.system.model.vo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 会员信息视图对象
 *
 * @author ambitiouschild
 * @since 2025-07-11 15:13
 */
@Getter
@Setter
@Schema( description = "会员信息视图对象")
public class MemberVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "会员编号")
    private Integer id;
    @Schema(description = "会员姓名")
    private String name;
    @Schema(description = "会员手机号")
    private String mobile;
    @Schema(description = "性别")
    private Integer gender;
    @Schema(description = "会员年龄")
    private Integer age;

    @Schema(description = "创建时间")
    private LocalDateTime  createTime;  //推荐使用 Java 8 的 LocalDateTime (优于传统的 Date/Timestamp)

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
    @Schema(description = "是否删除(1:已删除;0:未删除)")
    private Integer isDeleted;
}
