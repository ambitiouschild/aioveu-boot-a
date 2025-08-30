package com.aioveu.boot.aioveuDepartment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aioveu.boot.aioveuDepartment.model.entity.AioveuDepartment;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveuDepartment.model.query.AioveuDepartmentQuery;
import com.aioveu.boot.aioveuDepartment.model.vo.AioveuDepartmentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 公司部门组织结构Mapper接口
 *
 * @author aioveu
 * @since 2025-08-18 14:42
 */
@Mapper
public interface AioveuDepartmentMapper extends BaseMapper<AioveuDepartment> {

    // 不需要额外的方法，MyBatis-Plus 会自动生成基本CRUD
    /**
     * 获取公司部门组织结构分页数据
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return {@link Page<AioveuDepartmentVO>} 公司部门组织结构分页列表
     */
    Page<AioveuDepartmentVO> getAioveuDepartmentPage(Page<AioveuDepartmentVO> page, AioveuDepartmentQuery queryParams);


    @Select("SELECT dept_id FROM aioveu_department WHERE dept_name = #{name}")
    Long findIdByName(@Param("name") String name);

}
