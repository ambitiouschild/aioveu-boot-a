package com.aioveu.boot.aioveuCustomer.model.query;

import com.aioveu.boot.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

/**
 * 客户信息分页查询对象
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:42
 */
@Schema(description ="客户信息查询对象")
@Getter
@Setter
public class AioveuCustomerQuery extends BasePageQuery {

    @Schema(description = "客户ID")
    private Integer id;
    @Schema(description = "客户编号")
    private String customerNo;
    @Schema(description = "客户名称")
    private String name;
    @Schema(description = "客户类型")
    private Integer type;
    @Schema(description = "信用等级")
    private Integer creditRating;
    @Schema(description = "客户状态")
    private Integer customerStatus;
    @Schema(description = "所在城市")
    private String city;
    @Schema(description = "所在省份")
    private String province;
    @Schema(description = "联系电话")
    private String phone;
    @Schema(description = "客户来源")
    private Integer source;
    @Schema(description = "销售负责人ID")
    private Long salesRepId;

    private String salesRepName;
}
