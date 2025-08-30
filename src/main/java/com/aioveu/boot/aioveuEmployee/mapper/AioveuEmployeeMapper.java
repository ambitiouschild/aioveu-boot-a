package com.aioveu.boot.aioveuEmployee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aioveu.boot.aioveuEmployee.model.entity.AioveuEmployee;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveuEmployee.model.query.AioveuEmployeeQuery;
import com.aioveu.boot.aioveuEmployee.model.vo.AioveuEmployeeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 员工信息Mapper接口
 *
 * @author 可我不敌可爱
 * @since 2025-08-18 17:29
 */
@Mapper
public interface AioveuEmployeeMapper extends BaseMapper<AioveuEmployee> {

    /**
     * 获取员工信息分页数据
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return {@link Page<AioveuEmployeeVO>} 员工信息分页列表
     */
    Page<AioveuEmployeeVO> getAioveuEmployeePage(Page<AioveuEmployeeVO> page, AioveuEmployeeQuery queryParams);


    @Select("SELECT employee_id FROM aioveu_employee WHERE name = #{name}")
    Long findIdByName(@Param("name") String name);
}
