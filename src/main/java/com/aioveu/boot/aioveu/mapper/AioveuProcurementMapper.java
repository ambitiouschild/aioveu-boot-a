package com.aioveu.boot.aioveu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aioveu.boot.aioveu.model.entity.AioveuProcurement;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveu.model.query.AioveuProcurementQuery;
import com.aioveu.boot.aioveu.model.vo.AioveuProcurementVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 采购流程Mapper接口
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:07
 */
@Mapper
public interface AioveuProcurementMapper extends BaseMapper<AioveuProcurement> {

    /**
     * 获取采购流程分页数据
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return {@link Page<AioveuProcurementVO>} 采购流程分页列表
     */
    Page<AioveuProcurementVO> getAioveuProcurementPage(Page<AioveuProcurementVO> page, AioveuProcurementQuery queryParams);

}
