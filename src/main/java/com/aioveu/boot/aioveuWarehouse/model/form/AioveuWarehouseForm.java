package com.aioveu.boot.aioveuWarehouse.model.form;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import jakarta.validation.constraints.*;

/**
 * 仓库信息表单对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 21:42
 */
@Getter
@Setter
@Schema(description = "仓库信息表单对象")
public class AioveuWarehouseForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "仓库名称")
    @NotBlank(message = "仓库名称不能为空")
    @Size(max=50, message="仓库名称长度不能超过50个字符")
    private String name;

    @Schema(description = "仓库编码")
    @NotBlank(message = "仓库编码不能为空")
    @Size(max=20, message="仓库编码长度不能超过20个字符")
    private String code;

    @Schema(description = "仓库位置")
    @NotBlank(message = "仓库位置不能为空")
    @Size(max=100, message="仓库位置长度不能超过100个字符")
    private String location;


    @Schema(description = "仓库面积")
    private BigDecimal area;
    @Schema(description = "存储容量")
    private BigDecimal capacity;
    @Schema(description = "负责人ID")
    private Long managerId;

    /**
     * 员工姓名
     */
    private String managerName;


    @Schema(description = "联系电话")
    private String contactPhone;
    @Schema(description = "仓库描述")
    private String description;
    @Schema(description = "启用状态")
    private Integer isActive;

}
