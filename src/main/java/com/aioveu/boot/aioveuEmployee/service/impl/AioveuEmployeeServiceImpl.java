package com.aioveu.boot.aioveuEmployee.service.impl;

import com.aioveu.boot.aioveuDepartment.model.entity.AioveuDepartment;
import com.aioveu.boot.aioveuDepartment.model.vo.DeptOptionVO;
import com.aioveu.boot.aioveuDepartment.service.AioveuDepartmentService;
import com.aioveu.boot.aioveuEmployee.model.vo.EmployeeVO;
import com.aioveu.boot.aioveuPosition.model.entity.AioveuPosition;
import com.aioveu.boot.aioveuPosition.model.form.AioveuPositionForm;
import com.aioveu.boot.aioveuPosition.model.vo.AioveuPositionVO;
import com.aioveu.boot.aioveuPosition.service.AioveuPositionService;
import com.aliyun.oss.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aioveu.boot.aioveuEmployee.mapper.AioveuEmployeeMapper;
import com.aioveu.boot.aioveuEmployee.service.AioveuEmployeeService;
import com.aioveu.boot.aioveuEmployee.model.entity.AioveuEmployee;
import com.aioveu.boot.aioveuEmployee.model.form.AioveuEmployeeForm;
import com.aioveu.boot.aioveuEmployee.model.query.AioveuEmployeeQuery;
import com.aioveu.boot.aioveuEmployee.model.vo.AioveuEmployeeVO;
import com.aioveu.boot.aioveuEmployee.converter.AioveuEmployeeConverter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
/**
 * 员工信息服务实现类
 *
 * @author 可我不敌可爱
 * @since 2025-08-18 17:29
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AioveuEmployeeServiceImpl extends ServiceImpl<AioveuEmployeeMapper, AioveuEmployee> implements AioveuEmployeeService {

    private final AioveuEmployeeConverter aioveuEmployeeConverter;

    //添加部门服务依赖,注入 `AioveuDepartmentService`用于查询部门信息
    private final AioveuDepartmentService aioveuDepartmentService;

    //添加岗位服务依赖,注入 `AioveuPositionService`用于查询岗位信息
    private final AioveuPositionService aioveuPositionService;

    private final AioveuEmployeeMapper employeeMapper;


    /**
    * 获取员工信息分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AioveuEmployeeVO>} 员工信息分页列表
    */
    @Override
    public IPage<AioveuEmployeeVO> getAioveuEmployeePage(AioveuEmployeeQuery queryParams) {
        Page<AioveuEmployeeVO> pageVO = this.baseMapper.getAioveuEmployeePage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );
        // 设置部门名称
        setDeptNames(pageVO.getRecords());

        // 设置岗位名称
        setPositionNames(pageVO.getRecords());

        return pageVO;
    }
    
    /**
     * 获取员工信息表单数据
     *
     * @param id 员工信息ID
     * @return 员工信息表单数据
     */
    @Override
    public AioveuEmployeeForm getAioveuEmployeeFormData(Long id) {
        AioveuEmployee entity = this.getById(id);
        AioveuEmployeeForm form = aioveuEmployeeConverter.toForm(entity);


        // 设置部门名称
        if (entity.getDeptId() != null) {
            AioveuDepartment department = aioveuDepartmentService.getById(entity.getDeptId());
            if (department != null) {
                form.setPositionName(department.getDeptName());
            }
        }

        // 设置岗位名称
        if (entity.getPositionId() != null) {
            AioveuPosition position = aioveuPositionService.getById(entity.getPositionId());
            if (position != null) {
                form.setPositionName(position.getPositionName());
            }
        }

        return form;
    }
    
    /**
     * 新增员工信息
     *
     * @param formData 员工信息表单对象
     * @return 是否新增成功
     */
    @Override
    public boolean saveAioveuEmployee(AioveuEmployeeForm formData) {
        // 1. 检查员工编号是否唯一
        LambdaQueryWrapper<AioveuEmployee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AioveuEmployee::getEmpCode, formData.getEmpCode());

        if (count(wrapper) > 0) {
            throw new ServiceException("员工编号 " + formData.getEmpCode() + " 已存在");
        }

        // 2. 将表单数据转换为实体对象
        AioveuEmployee entity = aioveuEmployeeConverter.toEntity(formData);

        // 3. 执行插入
        return this.save(entity);
    }
    
    /**
     * 更新员工信息
     *
     * @param employeeId   员工信息ID
     * @param formData 员工信息表单对象
     * @return 是否修改成功
     * 您在修改员工信息时没有更改员工编号，但后端仍然报错员工编号重复。
     * 这通常是因为后端在更新时没有正确判断员工编号是否被修改，即使没有修改也执行了唯一性检查
     */
    @Override
    public boolean updateAioveuEmployee(Long employeeId,AioveuEmployeeForm formData) {
        //添加员工编号唯一性检查逻辑
        log.info("开始更新员工: ID={}", employeeId);

        // 1. 获取原始员工信息
        AioveuEmployee original = getById(employeeId);
        if (original == null) {
            log.error("员工不存在: ID={}", employeeId);
            throw new ServiceException("员工不存在");
        }
        // 2. 检查员工编号是否被修改
        String originalEmpCode = original.getEmpCode();
        String newEmpCode = formData.getEmpCode();
        log.info("原始员工编号: {}, 新员工编号: {}", originalEmpCode, newEmpCode);


        // 只有当员工编号被修改时才检查唯一性
        if (!originalEmpCode.equals(newEmpCode)) {
            log.info("员工编号被修改，检查唯一性");
            // 3. 检查新员工编号是否唯一（排除当前员工）
            LambdaQueryWrapper<AioveuEmployee> wrapper = new LambdaQueryWrapper<>();
            //创建一个LambdaQueryWrapper对象，用于构建查询条件。LambdaQueryWrapper是MyBatis-Plus提供的链式条件构造器，支持Lambda表达式，避免字段名的硬编码。
            wrapper.eq(AioveuEmployee::getEmpCode, newEmpCode)
                    //eq方法表示等值查询，即查询emp_code字段等于newEmpCode值的记录。
                    //AioveuEmployee::getEmpCode是Lambda表达式，指定要查询的字段（即emp_code列）。
                    //newEmpCode是要匹配的值（即新员工编号）。
                    .ne(AioveuEmployee::getEmployeeId, employeeId); // 排除当前员工
            //ne方法表示不等值查询，即查询employee_id字段不等于id的记录。
            //AioveuEmployee::getEmployeeId指定要查询的字段（即employee_id列）。
            //id是当前员工的ID（即要排除的ID）

            //查找员工编号（emp_code）等于新员工编号（newEmpCode）且员工ID（employee_id）不等于当前员工ID（id）的记录。

            long count = count(wrapper);
            log.info("员工编号 {} 的重复记录数: {}", newEmpCode, count);

            if (count(wrapper) > 0) {
                throw new ServiceException("员工编号 " + newEmpCode + " 已存在");
            }
        }else {
            log.info("员工编号未修改，跳过唯一性检查");
        }

        // 4. 将表单数据转换为实体对象
        AioveuEmployee entity = aioveuEmployeeConverter.toEntity(formData);
        entity.setEmployeeId(employeeId); // 设置员工ID
        log.info("转换后的实体对象: {}", entity);

        // 5. 复制未修改的字段（如创建时间等）
        entity.setCreateTime(original.getCreateTime());

        // 6. 执行更新
        return this.updateById(entity);
    }
    
    /**
     * 删除员工信息
     *
     * @param ids 员工信息ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    @Override
    public boolean deleteAioveuEmployees(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的员工信息数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }


    /**
     * 批量设置名称到VO对象，将EmployeeVO员工表视图对象的部门id,转换为部门名称
     */
    private void setDeptNames(List<AioveuEmployeeVO> employeeVOs) {
        if (employeeVOs == null || employeeVOs.isEmpty()) {
            return;
        }

        // 获取所有部门ID
        List<Long> deptIds = employeeVOs.stream()
                .map(AioveuEmployeeVO::getDeptId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        if (deptIds.isEmpty()) {
            return;
        }

        // 批量查询部门信息
        Map<Long, String> deptMap = aioveuDepartmentService.getDepartmentMapByIds(deptIds);

        // 设置部门名称
        employeeVOs.forEach(vo -> {
            //遍历列表：使用 forEach方法遍历 employeeVOs中的每个员工对象（vo）。
            //检查 vo.getDeptId()非空（防止空指针异常）
            //同时检查 deptMap中包含该岗位ID的键（确保映射中存在对应关系）
            if (vo.getDeptId() != null && deptMap.containsKey(vo.getDeptId())) {
                //通过 deptMap.getOrDefault()方法获取岗位名称：若存在则返回映射值，不存在则返回默认值「未知部门」
                //调用 vo.setDeptName()将名称设置到员工对象中
                vo.setDeptName(deptMap.getOrDefault(vo.getDeptId(), "未知部门"));
            }
        });
    }


    /**
     * 批量设置名称到VO对象，将EmployeeVO员工表视图对象的岗位id,转换为岗位名称
     */
    private void setPositionNames(List<AioveuEmployeeVO> employeeVOs) {
        if (employeeVOs == null || employeeVOs.isEmpty()) {
            return;
        }

        // 获取所有岗位ID
        List<Long> PositionIds = employeeVOs.stream()
                .map(AioveuEmployeeVO::getPositionId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        if (PositionIds.isEmpty()) {
            return;
        }

        // 批量查询岗位信息
        Map<Long, String> positionMap = aioveuPositionService.getPositionMapByIds(PositionIds);

        // 设置部门名称
        employeeVOs.forEach(vo -> {
            //遍历列表：使用 forEach方法遍历 employeeVOs中的每个员工对象（vo）。
            //检查 vo.getPositionId()非空（防止空指针异常）
            //同时检查 positionMap中包含该岗位ID的键（确保映射中存在对应关系）
            if (vo.getPositionId() != null && positionMap.containsKey(vo.getPositionId())) {
                //通过 positionMap.getOrDefault()方法获取岗位名称：若存在则返回映射值，不存在则返回默认值「未知岗位」
                //调用 vo.setPositionName()将名称设置到员工对象中
                vo.setPositionName(positionMap.getOrDefault(vo.getPositionId(), "未知岗位"));
            }
        });
    }


    /**
     * 批量获取员工信息（新增方法）
     */
    @Override
    public Map<Long, String> getEmployeeMapByIds(List<Long> employeeIds) {
        if (employeeIds == null || employeeIds.isEmpty()) {
            return Map.of();
        }

        // 批量查询部门信息
        List<AioveuEmployee> employees = this.listByIds(employeeIds);

        // 转换为Map: key=员工ID, value=员工姓名
        return employees.stream()
                .collect(Collectors.toMap(
                        AioveuEmployee::getEmployeeId,
                        AioveuEmployee::getName
                ));
    }

    /**
     * 获取所有员工列表（用于下拉选择框）
     *
     * @return 员工选项列表
     */
    @Override
    public List<EmployeeVO> getAllEmployeeOptions() {
        // 查询所有部门
        List<AioveuEmployee> employees = this.list();

        // 转换为选项对象
        List<EmployeeVO>  EmployeeVO  = employees.stream()
                .map(employee -> new EmployeeVO(employee.getEmployeeId(), employee.getName()))
                .collect(Collectors.toList());

        return EmployeeVO;
    }



    /**
     * 获取员工总数
     * @return 员工总数
     */
    @Override
    public long getEmployeeCount() {
        return employeeMapper.selectCount(null);
    }


}
