package com.aioveu.boot.aioveuSalary.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aioveu.boot.aioveuSalary.model.entity.AioveuSalary;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveuSalary.model.query.AioveuSalaryQuery;
import com.aioveu.boot.aioveuSalary.model.vo.AioveuSalaryVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 员工工资明细Mapper接口
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 17:14
 */
@Mapper
public interface AioveuSalaryMapper extends BaseMapper<AioveuSalary> {

    /**
     * 获取员工工资明细分页数据
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return {@link Page<AioveuSalaryVO>} 员工工资明细分页列表
     */
    Page<AioveuSalaryVO> getAioveuSalaryPage(Page<AioveuSalaryVO> page, AioveuSalaryQuery queryParams);

}
