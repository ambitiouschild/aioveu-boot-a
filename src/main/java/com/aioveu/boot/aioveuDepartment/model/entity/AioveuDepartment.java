package com.aioveu.boot.aioveuDepartment.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import com.aioveu.boot.common.base.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 公司部门组织结构实体对象
 *
 * @author aioveu
 * @since 2025-08-18 14:42
 */
@Getter
@Setter
@TableName("aioveu_department")
public class AioveuDepartment {

    private static final long serialVersionUID = 1L;

    /**
     * 这个基础实体类定义了 id字段作为主键，
     * 而您的 AioveuDepartment实体类可能同时继承了 BaseEntity并定义了自己的 deptId字段，
     * 导致两个主键字段冲突
     *  1.修改 AioveuDepartment实体类
     *  2.修改 BaseEntity类（可选但推荐）
     *  3.更新所有引用 deptId的地方  // 在 Service 实现中  // 在 Controller 中
     *  4. 更新 Mapper XML 文件
     *  5. 更新前端代码
     */

    /**
     * 不修改BaseEntity，也不让AioveuDepartment继承BaseEntity。这样我们就可以自由定义部门实体类的字段，而不受BaseEntity的约束
     * 如果不修改 BaseEntity并且不继承它，那么 AioveuDepartment实体类可以完全独立定义，不受 BaseEntity的任何约束。
     * 这是最简洁、最直接的解决方案。
     关键优势
     1.完全独立：
     •部门实体类不再受 BaseEntity的任何约束
     •可以自由定义部门特有的字段和方法
     2.简化代码：
     •不需要处理继承关系
     •不需要覆盖父类方法
     •不需要处理字段冲突
     3.更清晰的职责：
     •部门实体类只关注部门相关的属性和行为
     •避免将通用逻辑与特定领域逻辑混合
     4.更好的可维护性：
     •修改部门实体不会影响其他实体
     •更容易理解和维护代码
     */

    /**
     * 部门ID  // 不再定义 deptId 字段，直接使用父类的 id 字段
     */
    @TableId(value = "dept_id", type = IdType.AUTO)
    private Long deptId;

    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 上级部门ID
     */
    private Long parentDeptId;
    /**
     * 部门经理
     */
    private Long managerId;

    // 父类中已有 createTime 和 updateTime
    /**
     * 创建时间 在实体类中添加双重注解
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")  //处理 JSON 序列化/反序列化
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") //处理表单参数绑定 两者使用相同的日期格式
    private LocalDateTime createTime;

    /**
     * 更新时间 在实体类中添加双重注解
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")  //处理 JSON 序列化/反序列化
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  //处理表单参数绑定 两者使用相同的日期格式
    private LocalDateTime updateTime;
}
