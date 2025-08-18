package com.aioveu.boot.aioveuPerformance.service;

import com.aioveu.boot.aioveuPerformance.model.entity.AioveuPerformance;
import com.aioveu.boot.aioveuPerformance.model.form.AioveuPerformanceForm;
import com.aioveu.boot.aioveuPerformance.model.query.AioveuPerformanceQuery;
import com.aioveu.boot.aioveuPerformance.model.vo.AioveuPerformanceVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 员工绩效考评服务类
 *
 * @author 可我不敌可爱
 * @since 2025-08-18 22:48
 */
public interface AioveuPerformanceService extends IService<AioveuPerformance> {

    /**
     *员工绩效考评分页列表
     *
     * @return {@link IPage<AioveuPerformanceVO>} 员工绩效考评分页列表
     */
    IPage<AioveuPerformanceVO> getAioveuPerformancePage(AioveuPerformanceQuery queryParams);

    /**
     * 获取员工绩效考评表单数据
     *
     * @param id 员工绩效考评ID
     * @return 员工绩效考评表单数据
     */
     AioveuPerformanceForm getAioveuPerformanceFormData(Long id);

    /**
     * 新增员工绩效考评
     *
     * @param formData 员工绩效考评表单对象
     * @return 是否新增成功
     */
    boolean saveAioveuPerformance(AioveuPerformanceForm formData);

    /**
     * 修改员工绩效考评
     *
     * @param id   员工绩效考评ID
     * @param formData 员工绩效考评表单对象
     * @return 是否修改成功
     */
    boolean updateAioveuPerformance(Long id, AioveuPerformanceForm formData);

    /**
     * 删除员工绩效考评
     *
     * @param ids 员工绩效考评ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    boolean deleteAioveuPerformances(String ids);

}
