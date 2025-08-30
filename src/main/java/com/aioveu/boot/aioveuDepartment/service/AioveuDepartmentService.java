package com.aioveu.boot.aioveuDepartment.service;

import com.aioveu.boot.aioveuDepartment.model.entity.AioveuDepartment;
import com.aioveu.boot.aioveuDepartment.model.form.AioveuDepartmentForm;
import com.aioveu.boot.aioveuDepartment.model.query.AioveuDepartmentQuery;
import com.aioveu.boot.aioveuDepartment.model.vo.AioveuDepartmentVO;
import com.aioveu.boot.aioveuDepartment.model.vo.DeptOptionVO;
import com.aioveu.boot.aioveuDepartment.model.vo.ParentDeptOptionVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 公司部门组织结构服务类
 *
 * @author aioveu
 * @since 2025-08-18 14:42
 */
public interface AioveuDepartmentService extends IService<AioveuDepartment> {


    Long getIdByName(String name);
    /**
     *公司部门组织结构分页列表
     *
     * @return {@link IPage<AioveuDepartmentVO>} 公司部门组织结构分页列表
     */
    IPage<AioveuDepartmentVO> getAioveuDepartmentPage(AioveuDepartmentQuery queryParams);

    /**
     * 获取公司部门组织结构表单数据
     *
     * @param id 公司部门组织结构ID
     * @return 公司部门组织结构表单数据
     */
     AioveuDepartmentForm getAioveuDepartmentFormData(Long id);

    /**
     * 新增公司部门组织结构
     *
     * @param formData 公司部门组织结构表单对象
     * @return 是否新增成功
     */
    boolean saveAioveuDepartment(AioveuDepartmentForm formData);

    /**
     * 修改公司部门组织结构
     *
     * @param id   公司部门组织结构ID
     * @param formData 公司部门组织结构表单对象
     * @return 是否修改成功
     */
    boolean updateAioveuDepartment(Long id, AioveuDepartmentForm formData);

    /**
     * 删除公司部门组织结构
     *
     * @param ids 公司部门组织结构ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    boolean deleteAioveuDepartments(String ids);


    /**
     * 批量获取部门信息（新增方法）
     */
    Map<Long, String> getDepartmentMapByIds(List<Long> deptIds);

    /**
     * 根据部门名称列表获取部门ID映射
     * @param deptNames 部门名称列表
     * @return 部门名称到部门ID的映射
     */
    Map<String, Long> getDepartmentIdMapByNames(List<String> deptNames);


    /**
     * 获取所有部门列表（用于下拉选择框）
     *
     * @return 部门选项列表
     */
    List<DeptOptionVO> getAllDepartmentOptions();

    /**
     * 根据复合键列表获取复合键到ID的映射
     * @param compositeKeys 复合键列表（格式：名称+分隔符+其他字段）
     * @return 复合键到ID的映射Map
     */
    Map<String, Long> getDepartmentIdMapByCompositeKeys(List<String> compositeKeys);



}
