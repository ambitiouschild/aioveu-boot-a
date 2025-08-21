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


}
