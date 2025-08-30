package com.aioveu.boot.aioveuSalesOrder.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aioveu.boot.aioveuSalesOrder.model.entity.AioveuSalesOrder;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveuSalesOrder.model.query.AioveuSalesOrderQuery;
import com.aioveu.boot.aioveuSalesOrder.model.vo.AioveuSalesOrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 销售订单Mapper接口
 *
 * @author 可我不敌可爱
 * @since 2025-08-22 00:27
 */
@Mapper
public interface AioveuSalesOrderMapper extends BaseMapper<AioveuSalesOrder> {

    /**
     * 获取销售订单分页数据
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return {@link Page<AioveuSalesOrderVO>} 销售订单分页列表
     */
    Page<AioveuSalesOrderVO> getAioveuSalesOrderPage(Page<AioveuSalesOrderVO> page, AioveuSalesOrderQuery queryParams);


    @Select("SELECT id FROM aioveu_sales_order WHERE order_No = #{name}")
    Long findIdByName(@Param("name") String name);

}
