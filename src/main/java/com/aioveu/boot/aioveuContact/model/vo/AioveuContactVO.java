package com.aioveu.boot.aioveuContact.model.vo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 客户联系人视图对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:59
 */
@Getter
@Setter
@Schema( description = "客户联系人视图对象")
public class AioveuContactVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "联系人ID（主键）")
    private Integer id;
    @Schema(description = "客户ID")
    private Long customerId;
    /**
     * 客户名称
     */
    private String customerName;

    @Schema(description = "联系人姓名")
    private String name;
    @Schema(description = "职位")
    private String position;
    @Schema(description = "部门")
    private String department;
    @Schema(description = "手机号码")
    private String mobile;
    @Schema(description = "办公电话")
    private String phone;
    @Schema(description = "电子邮箱")
    private String email;
    @Schema(description = "微信号")
    private String wechat;
    @Schema(description = "是否是主要联系人：0-否，1-是")
    private Integer isPrimary;
    @Schema(description = "性别")
    private Integer gender;
    @Schema(description = "生日")
    private LocalDate birthday;
    @Schema(description = "兴趣爱好")
    private String hobbies;
    @Schema(description = "备注")
    private String notes;
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
