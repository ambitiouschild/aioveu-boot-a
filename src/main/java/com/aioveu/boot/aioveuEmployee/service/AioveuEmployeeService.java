package com.aioveu.boot.aioveuEmployee.service;

import com.aioveu.boot.aioveuDepartment.mapper.AioveuDepartmentMapper;
import com.aioveu.boot.aioveuEmployee.mapper.AioveuEmployeeMapper;
import com.aioveu.boot.aioveuEmployee.model.entity.AioveuEmployee;
import com.aioveu.boot.aioveuEmployee.model.form.AioveuEmployeeForm;
import com.aioveu.boot.aioveuEmployee.model.query.AioveuEmployeeQuery;
import com.aioveu.boot.aioveuEmployee.model.vo.AioveuEmployeeVO;
import com.aioveu.boot.aioveuEmployee.model.vo.EmployeeVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 员工信息服务类
 *
 * @author 可我不敌可爱
 * @since 2025-08-18 17:29
 */
public interface AioveuEmployeeService extends IService<AioveuEmployee> {



    Long getIdByName(String name);

    /**
     *员工信息分页列表
     *
     * @return {@link IPage<AioveuEmployeeVO>} 员工信息分页列表
     */
    IPage<AioveuEmployeeVO> getAioveuEmployeePage(AioveuEmployeeQuery queryParams);

    /**
     * 获取员工信息表单数据
     *
     * @param id 员工信息ID
     * @return 员工信息表单数据
     */
     AioveuEmployeeForm getAioveuEmployeeFormData(Long id);

    /**
     * 新增员工信息
     *
     * @param formData 员工信息表单对象
     * @return 是否新增成功
     */
    boolean saveAioveuEmployee(AioveuEmployeeForm formData);

    /**
     * 修改员工信息
     *
     * @param id   员工信息ID
     * @param formData 员工信息表单对象
     * @return 是否修改成功
     */
    boolean updateAioveuEmployee(Long id, AioveuEmployeeForm formData);

    /**
     * 删除员工信息
     *
     * @param ids 员工信息ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    boolean deleteAioveuEmployees(String ids);

    /**
     * 批量获取员工信息
     *
     * @param employeeIds 员工ID，多个以英文逗号(,)分割
     * @return 员工信息 Map
     */
    Map<Long, String> getEmployeeMapByIds(List<Long> employeeIds);


    /**
     * 获取所有员工列表（用于下拉选择框）
     *
     * @param
     * @return 员工选项列表
     */
    List<EmployeeVO> getAllEmployeeOptions();


    /**
     * 获取员工总数
     * @return 员工总数
     */
    long getEmployeeCount();
}
