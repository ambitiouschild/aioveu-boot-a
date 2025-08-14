package com.aioveu.boot.system.model.query;

import com.aioveu.boot.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 会员信息分页查询对象
 *
 * @author ambitiouschild
 * @since 2025-07-11 15:13
 */
@Schema(description ="会员信息查询对象")
@Getter
@Setter
public class MemberQuery extends BasePageQuery {

}
