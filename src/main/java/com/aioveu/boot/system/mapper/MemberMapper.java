package com.aioveu.boot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aioveu.boot.system.model.entity.Member;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.system.model.query.MemberQuery;
import com.aioveu.boot.system.model.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员信息Mapper接口
 *
 * @author ambitiouschild
 * @since 2025-07-11 15:13
 */
@Mapper
public interface MemberMapper extends BaseMapper<Member> {

    /**
     * 获取会员信息分页数据
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return {@link Page<MemberVO>} 会员信息分页列表
     */
    Page<MemberVO> getMemberPage(Page<MemberVO> page, MemberQuery queryParams);

}
