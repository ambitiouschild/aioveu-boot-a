package com.aioveu.boot.aioveuSalary.service;

import com.aioveu.boot.aioveuSalary.model.entity.AioveuSalary;
import com.aioveu.boot.aioveuSalary.model.form.AioveuSalaryForm;
import com.aioveu.boot.aioveuSalary.model.query.AioveuSalaryQuery;
import com.aioveu.boot.aioveuSalary.model.vo.AioveuSalaryVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 员工工资明细服务类
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 17:14
 */
public interface AioveuSalaryService extends IService<AioveuSalary> {

    /**
     *员工工资明细分页列表
     *
     * @return {@link IPage<AioveuSalaryVO>} 员工工资明细分页列表
     */
    IPage<AioveuSalaryVO> getAioveuSalaryPage(AioveuSalaryQuery queryParams);

    /**
     * 获取员工工资明细表单数据
     *
     * @param id 员工工资明细ID
     * @return 员工工资明细表单数据
     */
     AioveuSalaryForm getAioveuSalaryFormData(Long id);

    /**
     * 新增员工工资明细
     *
     * @param formData 员工工资明细表单对象
     * @return 是否新增成功
     */
    boolean saveAioveuSalary(AioveuSalaryForm formData);

    /**
     * 修改员工工资明细
     *
     * @param id   员工工资明细ID
     * @param formData 员工工资明细表单对象
     * @return 是否修改成功
     */
    boolean updateAioveuSalary(Long id, AioveuSalaryForm formData);

    /**
     * 删除员工工资明细
     *
     * @param ids 员工工资明细ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    boolean deleteAioveuSalarys(String ids);

}
