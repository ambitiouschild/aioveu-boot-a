package com.aioveu.boot.aioveuTransaction.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aioveu.boot.aioveuTransaction.model.entity.AioveuTransaction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveuTransaction.model.query.AioveuTransactionQuery;
import com.aioveu.boot.aioveuTransaction.model.vo.AioveuTransactionVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 客户交易记录Mapper接口
 *
 * @author 可我不敌可爱
 * @since 2025-08-22 00:16
 */
@Mapper
public interface AioveuTransactionMapper extends BaseMapper<AioveuTransaction> {

    /**
     * 获取客户交易记录分页数据
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return {@link Page<AioveuTransactionVO>} 客户交易记录分页列表
     */
    Page<AioveuTransactionVO> getAioveuTransactionPage(Page<AioveuTransactionVO> page, AioveuTransactionQuery queryParams);

}
