package com.aioveu.boot.aioveuContact.model.query;

import com.aioveu.boot.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 客户联系人分页查询对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:59
 */
@Schema(description ="客户联系人查询对象")
@Getter
@Setter
public class AioveuContactQuery extends BasePageQuery {

    @Schema(description = "联系人ID（主键）")
    private Integer id;
    @Schema(description = "客户ID")
    private Long customerId;

    private String customerName;

    @Schema(description = "联系人姓名")
    private String name;
    @Schema(description = "手机号码")
    private String mobile;
    @Schema(description = "电子邮箱")
    private String email;
}
