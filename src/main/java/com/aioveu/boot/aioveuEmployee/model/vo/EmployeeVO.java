package com.aioveu.boot.aioveuEmployee.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema( description = "员工选项")
public class EmployeeVO {

    private Long employeeId;
    /**
     * 员工姓名
     */
    private String employeeName;
    /**
     * 带参数的构造函数
     *
     * @param employeeId 员工ID
     * @param employeeName 员工姓名
     */
    public EmployeeVO(Long employeeId, String employeeName) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
    }
}
