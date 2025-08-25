package com.aioveu.boot.aioveuDepartment.model.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema( description = "公司上级部门选项视图对象")
public class ParentDeptOptionVO {


    private Long parentDeptId;
    /**
     * 部门名称
     */
    private String parentDeptName;
    /**
     * 带参数的构造函数
     *
     * @param parentDeptId 上级部门ID
     * @param parentDeptName 上级部门名称
     */
    public ParentDeptOptionVO(Long parentDeptId, String parentDeptName) {
        this.parentDeptId = parentDeptId;
        this.parentDeptName = parentDeptName;
    }
}
