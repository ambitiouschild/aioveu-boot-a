package com.aioveu.boot.aioveuCustomer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aioveu.boot.aioveuCustomer.model.entity.AioveuCustomer;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveuCustomer.model.query.AioveuCustomerQuery;
import com.aioveu.boot.aioveuCustomer.model.vo.AioveuCustomerVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 客户信息Mapper接口
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:42
 */
@Mapper
public interface AioveuCustomerMapper extends BaseMapper<AioveuCustomer> {

    /**
     * 获取客户信息分页数据
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return {@link Page<AioveuCustomerVO>} 客户信息分页列表
     */
    Page<AioveuCustomerVO> getAioveuCustomerPage(Page<AioveuCustomerVO> page, AioveuCustomerQuery queryParams);

    @Select("SELECT id FROM aioveu_customer WHERE name = #{name}")
    Long findIdByName(@Param("name") String name);


}
