package com.aioveu.boot.aioveuAttendance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aioveu.boot.aioveuAttendance.model.entity.AioveuAttendance;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aioveu.boot.aioveuAttendance.model.query.AioveuAttendanceQuery;
import com.aioveu.boot.aioveuAttendance.model.vo.AioveuAttendanceVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 考勤信息Mapper接口
 *
 * @author 可我不敌可爱
 * @since 2025-08-18 21:52
 */
@Mapper
public interface AioveuAttendanceMapper extends BaseMapper<AioveuAttendance> {

    /**
     * 获取考勤信息分页数据
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return {@link Page<AioveuAttendanceVO>} 考勤信息分页列表
     */
    Page<AioveuAttendanceVO> getAioveuAttendancePage(Page<AioveuAttendanceVO> page, AioveuAttendanceQuery queryParams);

}
