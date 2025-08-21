package com.aioveu.boot.aioveuSalesOrderDetail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aioveu.boot.aioveuSalesOrderDetail.model.entity.AioveuSalesOrderDetail;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveuSalesOrderDetail.model.query.AioveuSalesOrderDetailQuery;
import com.aioveu.boot.aioveuSalesOrderDetail.model.vo.AioveuSalesOrderDetailVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单明细Mapper接口
 *
 * @author 可我不敌可爱
 * @since 2025-08-22 00:42
 */
@Mapper
public interface AioveuSalesOrderDetailMapper extends BaseMapper<AioveuSalesOrderDetail> {

    /**
     * 获取订单明细分页数据
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return {@link Page<AioveuSalesOrderDetailVO>} 订单明细分页列表
     */
    Page<AioveuSalesOrderDetailVO> getAioveuSalesOrderDetailPage(Page<AioveuSalesOrderDetailVO> page, AioveuSalesOrderDetailQuery queryParams);

}
