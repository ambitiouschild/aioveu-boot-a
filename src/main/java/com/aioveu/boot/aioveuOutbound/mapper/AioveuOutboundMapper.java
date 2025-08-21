package com.aioveu.boot.aioveuOutbound.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aioveu.boot.aioveuOutbound.model.entity.AioveuOutbound;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveuOutbound.model.query.AioveuOutboundQuery;
import com.aioveu.boot.aioveuOutbound.model.vo.AioveuOutboundVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 出库记录Mapper接口
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:26
 */
@Mapper
public interface AioveuOutboundMapper extends BaseMapper<AioveuOutbound> {

    /**
     * 获取出库记录分页数据
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return {@link Page<AioveuOutboundVO>} 出库记录分页列表
     */
    Page<AioveuOutboundVO> getAioveuOutboundPage(Page<AioveuOutboundVO> page, AioveuOutboundQuery queryParams);

}
