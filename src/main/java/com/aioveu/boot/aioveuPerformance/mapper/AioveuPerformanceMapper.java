package com.aioveu.boot.aioveuPerformance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aioveu.boot.aioveuPerformance.model.entity.AioveuPerformance;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveuPerformance.model.query.AioveuPerformanceQuery;
import com.aioveu.boot.aioveuPerformance.model.vo.AioveuPerformanceVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 员工绩效考评Mapper接口
 *
 * @author 可我不敌可爱
 * @since 2025-08-18 22:48
 */
@Mapper
public interface AioveuPerformanceMapper extends BaseMapper<AioveuPerformance> {

    /**
     * 获取员工绩效考评分页数据
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return {@link Page<AioveuPerformanceVO>} 员工绩效考评分页列表
     */
    Page<AioveuPerformanceVO> getAioveuPerformancePage(Page<AioveuPerformanceVO> page, AioveuPerformanceQuery queryParams);

}
