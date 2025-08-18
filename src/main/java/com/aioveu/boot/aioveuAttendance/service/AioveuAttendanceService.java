package com.aioveu.boot.aioveuAttendance.service;

import com.aioveu.boot.aioveuAttendance.model.entity.AioveuAttendance;
import com.aioveu.boot.aioveuAttendance.model.form.AioveuAttendanceForm;
import com.aioveu.boot.aioveuAttendance.model.query.AioveuAttendanceQuery;
import com.aioveu.boot.aioveuAttendance.model.vo.AioveuAttendanceVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 考勤信息服务类
 *
 * @author 可我不敌可爱
 * @since 2025-08-18 21:52
 */
public interface AioveuAttendanceService extends IService<AioveuAttendance> {

    /**
     *考勤信息分页列表
     *
     * @return {@link IPage<AioveuAttendanceVO>} 考勤信息分页列表
     */
    IPage<AioveuAttendanceVO> getAioveuAttendancePage(AioveuAttendanceQuery queryParams);

    /**
     * 获取考勤信息表单数据
     *
     * @param id 考勤信息ID
     * @return 考勤信息表单数据
     */
     AioveuAttendanceForm getAioveuAttendanceFormData(Long id);

    /**
     * 新增考勤信息
     *
     * @param formData 考勤信息表单对象
     * @return 是否新增成功
     */
    boolean saveAioveuAttendance(AioveuAttendanceForm formData);

    /**
     * 修改考勤信息
     *
     * @param id   考勤信息ID
     * @param formData 考勤信息表单对象
     * @return 是否修改成功
     */
    boolean updateAioveuAttendance(Long id, AioveuAttendanceForm formData);

    /**
     * 删除考勤信息
     *
     * @param ids 考勤信息ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    boolean deleteAioveuAttendances(String ids);

}
