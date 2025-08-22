package com.aioveu.boot.aioveuDepartment.model.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema( description = "公司部门选项视图对象")
public class DeptOptionVO {

    private Long deptId;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 带参数的构造函数
     *
     * @param deptId 部门ID
     * @param deptName 部门名称
     */
    public DeptOptionVO(Long deptId, String deptName) {
        this.deptId = deptId;
        this.deptName = deptName;
    }

}
