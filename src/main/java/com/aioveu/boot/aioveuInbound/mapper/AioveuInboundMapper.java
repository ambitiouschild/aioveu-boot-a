package com.aioveu.boot.aioveuInbound.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aioveu.boot.aioveuInbound.model.entity.AioveuInbound;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveuInbound.model.query.AioveuInboundQuery;
import com.aioveu.boot.aioveuInbound.model.vo.AioveuInboundVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 入库信息Mapper接口
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 22:18
 */
@Mapper
public interface AioveuInboundMapper extends BaseMapper<AioveuInbound> {

    /**
     * 获取入库信息分页数据
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return {@link Page<AioveuInboundVO>} 入库信息分页列表
     */
    Page<AioveuInboundVO> getAioveuInboundPage(Page<AioveuInboundVO> page, AioveuInboundQuery queryParams);

}
