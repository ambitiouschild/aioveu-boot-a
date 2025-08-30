package com.aioveu.boot.aioveuEmployee.model.query;

import com.aioveu.boot.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

/**
 * 员工信息分页查询对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-18 17:29
 */
@Schema(description ="员工信息查询对象")
@Getter
@Setter
public class AioveuEmployeeQuery extends BasePageQuery {

    @Schema(description = "员工ID")
    private Integer employeeId;
    @Schema(description = "员工编号")
    private String empCode;
    @Schema(description = "姓名")
    private String name;
    @Schema(description = "性别")
    private Integer gender;
    @Schema(description = "出生日期")
    private List<String> birthDate;
    @Schema(description = "身份证号")
    private String idCard;
    @Schema(description = "手机号码")
    private String phone;
    @Schema(description = "邮箱")
    private String email;
    @Schema(description = "所属部门")
    private Long deptId;

    @Schema(description = "部门名称")
    private String deptName;


    @Schema(description = "岗位ID")
    private Long positionId;

    @Schema(description = "岗位名称")
    private String positionName;

    @Schema(description = "入职日期")
    private List<String> hireDate;
    @Schema(description = "基本薪资")
    private BigDecimal salary;
    @Schema(description = "状态：0-离职，1-在职,2-休假,3-实习")
    private Integer status;
    @Schema(description = "创建时间")
    private List<String> createTime;
    @Schema(description = "更新时间")
    private List<String> updateTime;
}
