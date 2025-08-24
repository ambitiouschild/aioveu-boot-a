package com.aioveu.boot.aioveuEmployee.model.vo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

/**
 * 员工信息视图对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-18 17:29
 */
@Getter
@Setter
@Schema( description = "员工信息视图对象")
public class AioveuEmployeeVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 部门名称
     */
    private String deptName;   // 新增：部门名称，用于前端列表显示
    /**
     * 岗位名称
     */
    private String positionName; // 新增：岗位名称，用于前端列表显示

    @Schema(description = "员工ID")
    private Integer employeeId;
    @Schema(description = "员工编号")
    private String empCode;
    @Schema(description = "姓名")
    private String name;
    @Schema(description = "性别")
    private Integer gender;
    @Schema(description = "出生日期")
    private LocalDate birthDate;
    @Schema(description = "身份证号")
    private String idCard;
    @Schema(description = "手机号码")
    private String phone;
    @Schema(description = "邮箱")
    private String email;
    @Schema(description = "所属部门")
    private Long deptId;
    @Schema(description = "岗位ID")
    private Long positionId;
    @Schema(description = "入职日期")
    private LocalDate hireDate;
    @Schema(description = "基本薪资")
    private BigDecimal salary;
    @Schema(description = "状态：0-离职，1-在职,2-休假,3-实习")
    private Integer status;
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
