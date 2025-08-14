package com.aioveu.boot.system.model.form;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * 会员信息表单对象
 *
 * @author ambitiouschild
 * @since 2025-07-11 15:13
 */
@Getter
@Setter
@Schema(description = "会员信息表单对象")
public class MemberForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;



    /*
    * 根据日志，问题在于新增会员时，后端接口要求会员编号（id）不能为空，但新增会员时不应该需要id（因为id是系统生成的）。
    * 错误信息显示：MethodArgumentNotValidException: Validation failed for argument [0] ... default message [会员编号不能为空]
    * 方案一：在MemberForm类中，将id字段的@NotNull注解移除，或者使用分组校验（为新增和更新分别指定不同的校验规则）。
    * 方案二：如果无法修改后端，前端在新增时传递一个虚拟的id（不推荐，因为应该由后端生成id）。
    *
    * 从错误日志看，前端在新增时提交的数据中id为null，而后端要求id不能为null。所以，根本问题在于后端校验逻辑需要调整。
    * 另外，前端在新增时不应该提交id字段（或者提交时忽略id字段）。但根据错误信息，前端提交的数据中id字段为null，而后端要求不能为null。
    *
    * 最佳解决方案是修改后端校验规则，对新增操作不校验id的非空。
    *
    * 强烈建议修改后端校验规则。
    * */
    @Schema(description = "会员编号")
//    @NotNull(message = "会员编号不能为空")
    private Integer id;

    @Schema(description = "会员姓名")
    @Size(max=50, message="会员姓名长度不能超过50个字符")
    private String name;

    @Schema(description = "会员手机号")
    @Size(max=20, message="会员手机号长度不能超过20个字符")
    private String mobile;

    @Schema(description = "性别")
    private Integer gender;

    @Schema(description = "会员年龄")
    private Integer age;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime  updateTime;

    @Schema(description = "是否删除(1:已删除;0:未删除)")
    private Integer isDeleted;


}
