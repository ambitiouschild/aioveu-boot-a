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
    private String name;
    /**
     * 带参数的构造函数
     *
     * @param employeeId 员工ID
     * @param name 员工姓名
     */
    public EmployeeVO(Long employeeId, String name) {
        this.employeeId = employeeId;
        this.name = name;
    }
}
