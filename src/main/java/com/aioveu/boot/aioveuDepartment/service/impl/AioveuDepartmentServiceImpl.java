package com.aioveu.boot.aioveuDepartment.service.impl;

import com.aioveu.boot.aioveuDepartment.model.vo.DeptOptionVO;
import com.aioveu.boot.aioveuDepartment.model.vo.ParentDeptOptionVO;
import com.aioveu.boot.aioveuEmployee.model.entity.AioveuEmployee;
import com.aioveu.boot.aioveuEmployee.service.AioveuEmployeeService;
import com.aliyun.oss.ServiceException;
import groovy.lang.Lazy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aioveu.boot.aioveuDepartment.mapper.AioveuDepartmentMapper;
import com.aioveu.boot.aioveuDepartment.service.AioveuDepartmentService;
import com.aioveu.boot.aioveuDepartment.model.entity.AioveuDepartment;
import com.aioveu.boot.aioveuDepartment.model.form.AioveuDepartmentForm;
import com.aioveu.boot.aioveuDepartment.model.query.AioveuDepartmentQuery;
import com.aioveu.boot.aioveuDepartment.model.vo.AioveuDepartmentVO;
import com.aioveu.boot.aioveuDepartment.converter.AioveuDepartmentConverter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

/**
 * 公司部门组织结构服务实现类
 *
 * @author aioveu
 * @since 2025-08-18 14:42
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AioveuDepartmentServiceImpl extends ServiceImpl<AioveuDepartmentMapper, AioveuDepartment> implements AioveuDepartmentService {

    private final AioveuDepartmentConverter aioveuDepartmentConverter;

    //确保 AioveuEmployeeService被正确注入到当前类中（例如通过@Autowired注解），然后通过实例调用 getById方法
    //在控制器层解决：但是这样会破坏分层架构，不推荐
//    private final AioveuEmployeeService aioveuEmployeeService;



    /**
    * 获取公司部门组织结构分页列表
    * 先进行 tovo,在vo层操作
    * @param queryParams 查询参数
    * @return {@link IPage<AioveuDepartmentVO>} 公司部门组织结构分页列表
    */
    @Override
    public IPage<AioveuDepartmentVO> getAioveuDepartmentPage(AioveuDepartmentQuery queryParams) {
        Page<AioveuDepartmentVO> pageVO = this.baseMapper.getAioveuDepartmentPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );

        // 设置部门名称
        setParentDeptNames(pageVO.getRecords());

//        // 设置经理姓名
//        setManagerNames(pageVO.getRecords());

        return pageVO;
    }
    
    /**
     * 获取公司部门组织结构表单数据
     *
     * @param id 公司部门组织结构ID
     * @return 公司部门组织结构表单数据
     */
    @Override
    public AioveuDepartmentForm getAioveuDepartmentFormData(Long id) {
        AioveuDepartment entity = this.getById(id);

        if (entity == null) {
            throw new ServiceException("部门不存在");
        }

        AioveuDepartmentForm form = aioveuDepartmentConverter.toForm(entity);

        // 设置上级部门名称
        if (entity.getParentDeptId() != null) {
            AioveuDepartment parentDept = getById(entity.getParentDeptId());
            if (parentDept != null) {
                form.setParentDeptName(parentDept.getDeptName());
            } else {
                // 上级部门不存在
                form.setParentDeptName("未知部门");
            }
        } else {
            // 没有上级部门
            form.setParentDeptName("无上级部门");
        }

        //在部门服务中不设置经理姓名，而是在展示层（如Controller）通过其他方式获取。这样部门服务就不需要依赖员工相关的组件。
        //
        //例如，在Controller中获取部门信息后，再调用员工服务获取经理姓名。这样部门服务就只负责部门相关的逻辑，不会依赖员工服务

        return form;
    }
    
    /**
     * 新增公司部门组织结构
     *
     * @param formData 公司部门组织结构表单对象
     * @return 是否新增成功
     */
    @Override
    public boolean saveAioveuDepartment(AioveuDepartmentForm formData) {
        AioveuDepartment entity = aioveuDepartmentConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新公司部门组织结构
     *
     * @param id   公司部门组织结构ID
     * @param formData 公司部门组织结构表单对象
     * @return 是否修改成功
     */
    @Override
    public boolean updateAioveuDepartment(Long id,AioveuDepartmentForm formData) {
        log.info("开始更新部门: ID={}", id);

        // 1. 获取原始部门信息
        AioveuDepartment original = getById(id);
        if (original == null) {
            log.error("部门不存在: ID={}", id);
            throw new ServiceException("部门不存在");
        }

        // 2. 将表单数据转换为实体对象  表单里没有id,所以要把id传进来
        /*
        * 是的，表单对象（通常是前端提交的数据）通常不包含ID，因为ID通常通过URL路径参数传递（对于更新操作）。
        * 因此，在服务层中，您需要将传入的ID设置到实体对象中，然后再执行更新
        * 是的，表单对象（通常是前端提交的数据）通常不包含ID，因为ID通常通过URL路径参数传递（对于更新操作）。因此，在服务层中，您需要将传入的ID设置到实体对象中，然后再执行更新。
          在更新操作中，表单数据通常不包含ID（如 deptId），因为ID是通过URL路径参数传递的。
        * 因此，在服务层中，您需要将传入的ID设置到实体对象中，然后再执行更新
        关键步骤
        1.从路径参数获取ID：
            •在控制器中，通过 @PathVariable获取ID
            •将ID传递给服务层
        2.在服务层设置ID：
            •将表单数据转换为实体对象
            •将传入的ID设置到实体对象中
            •然后执行更新

        为什么需要手动设置ID？
        1.安全考虑：
            •不允许前端直接设置ID
            •ID由后端控制
        2.RESTful 设计：
            •资源ID通过URL路径传递
            •请求体只包含需要更新的字段
        3.数据完整性：
            •确保更新的是指定的资源
            •避免ID被意外修改
        * */
        AioveuDepartment entity = aioveuDepartmentConverter.toEntity(formData);
        entity.setDeptId(id); // 设置部门ID
        log.info("转换后的实体对象: {}", entity);

        // 3. 复制未修改的字段
        entity.setCreateTime(original.getCreateTime());


        return this.updateById(entity);
    }
    
    /**
     * 删除公司部门组织结构
     *
     * @param ids 公司部门组织结构ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    @Override
    public boolean deleteAioveuDepartments(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的公司部门组织结构数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }


    /**
     * 批量获取部门信息（新增方法）
     */
    @Override
    public Map<Long, String> getDepartmentMapByIds(List<Long> deptIds) {
        if (deptIds == null || deptIds.isEmpty()) {
            return Map.of();
        }

        // 批量查询部门信息
        List<AioveuDepartment> departments = this.listByIds(deptIds);

        // 转换为Map: key=部门ID, value=部门名称
        return departments.stream()
                .collect(Collectors.toMap(
                        AioveuDepartment::getDeptId,
                        AioveuDepartment::getDeptName
                ));
    }

    /**
     * 获取所有部门列表（用于下拉选择框）
     *
     * @return 部门选项列表
     */
    @Override
    public List<DeptOptionVO> getAllDepartmentOptions() {
        // 查询所有部门
        List<AioveuDepartment> departments = this.list();

        // 转换为选项对象
        List<DeptOptionVO>  DeptOptionVO  = departments.stream()
                .map(dept -> new DeptOptionVO(dept.getDeptId(), dept.getDeptName()))
                .collect(Collectors.toList());

        return DeptOptionVO;
    }


    /**
     *  批量设置名称到VO对象，将PositionVO岗位表视图对象的部门id,转换为部门名称，只被分页列表调用
     */
    private void setParentDeptNames(List<AioveuDepartmentVO> departmentVOs) {
        if (departmentVOs == null || departmentVOs.isEmpty()) {
            return;
        }

        // 获取所有上级部门ID（去重）
        List<Long> parentDeptIds   = departmentVOs.stream()
                .map(AioveuDepartmentVO::getParentDeptId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        if (parentDeptIds.isEmpty()) {
            // 所有部门都没有上级部门，设置默认值
            return;
        }

        // 批量查询部门信息
        Map<Long, String> deptMap = getDepartmentMapByIds(parentDeptIds);

        // 设置部门名称
        departmentVOs.forEach(vo -> {
            //遍历列表：使用 forEach方法遍历 employeeVOs中的每个员工对象（vo）。
            //检查 vo.getDeptId()非空（防止空指针异常）
            //同时检查 deptMap中包含该岗位ID的键（确保映射中存在对应关系）
            if (vo.getParentDeptId() != null && deptMap.containsKey(vo.getParentDeptId())) {
                //通过 deptMap.getOrDefault()方法获取岗位名称：若存在则返回映射值
                //调用 vo.setDeptName()将名称设置到员工对象中
                vo.setParentDeptName(deptMap.get(vo.getParentDeptId()));
            }else if (vo.getParentDeptId() == null) {
                // 没有上级部门
                vo.setParentDeptName("");
            }
        });
    }

    /**
     *  批量设置名称到VO对象，将AioveuDepartmentVO岗位表视图对象的部门id,转换为经理姓名，只被分页列表调用
     */
//    private void setManagerNames(List<AioveuDepartmentVO> departmentVOs) {
//        if (departmentVOs == null || departmentVOs.isEmpty()) {
//            return;
//        }
//
//        // 获取所有ManagerID（去重）
//        List<Long> ManagerIds  = departmentVOs.stream()
//                .map(AioveuDepartmentVO::getManagerId)
//                .filter(Objects::nonNull)
//                .distinct()
//                .collect(Collectors.toList());
//
//        if (ManagerIds.isEmpty()) {
//            return;
//        }
//
//        // 批量查询部门信息
//        Map<Long, String> managerMap = aioveuEmployeeService.getEmployeeMapByIds(ManagerIds);
//
//        // 设置部门名称
//        departmentVOs.forEach(vo -> {
//            //遍历列表：使用 forEach方法遍历 employeeVOs中的每个员工对象（vo）。
//            //检查 vo.getDeptId()非空（防止空指针异常）
//            //同时检查 deptMap中包含该岗位ID的键（确保映射中存在对应关系）
//            if (vo.getManagerId() != null && managerMap.containsKey(vo.getManagerId())) {
//                //通过 deptMap.getOrDefault()方法获取岗位名称：若存在则返回映射值
//                //调用 vo.setDeptName()将名称设置到员工对象中
//                vo.setManagerName(managerMap.get(vo.getManagerId()));
//            }else if (vo.getManagerId() == null) {
//                // 没有上级部门
//                vo.setManagerName("");
//            }
//        });
//    }

}
