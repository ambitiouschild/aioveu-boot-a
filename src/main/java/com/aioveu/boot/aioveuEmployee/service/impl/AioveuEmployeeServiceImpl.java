package com.aioveu.boot.aioveuEmployee.service.impl;

import cn.idev.excel.util.StringUtils;
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
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import groovy.lang.Lazy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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



    @Autowired
    private AioveuEmployeeMapper aioveuEmployeeMapper;

    @Override
    public Long getIdByName(String name) {
        return aioveuEmployeeMapper.findIdByName(name);
    }

    /**
    * 获取员工信息分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AioveuEmployeeVO>} 员工信息分页列表
    */
    @Override
    public IPage<AioveuEmployeeVO> getAioveuEmployeePage(AioveuEmployeeQuery queryParams) {

        // 处理部门名称映射
        if (StringUtils.isNotBlank(queryParams.getDeptName())) {
            Long deptId = aioveuDepartmentService.getIdByName(queryParams.getDeptName());
            queryParams.setDeptId(deptId);
        }

        // 处理岗位名称映射
        if (StringUtils.isNotBlank(queryParams.getPositionName())) {
            Long positionId = aioveuPositionService.getIdByName(queryParams.getPositionName());
            queryParams.setPositionId(positionId);
        }

        // 创建分页对象，并强制添加创建时间降序排序（最新在前）
        Page<AioveuEmployeeVO> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        page.addOrder(OrderItem.desc("create_time")); // 添加这一行即可

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
        // 1. 根据ID获取实体信息
        AioveuEmployee entity = this.getById(id);
        if (entity == null) {
            throw new ServiceException("不存在");
        }
        // 2. 将实体转换为表单对象
        AioveuEmployeeForm form = aioveuEmployeeConverter.toForm(entity);

        // 3. 处理映射信息（如果存在）
        if (entity.getDeptId() != null) {
            // 使用 MyBatis-Plus 的 LambdaQueryWrapper 查询信息
            LambdaQueryWrapper<AioveuDepartment> Wrapper = new LambdaQueryWrapper<>();
            Wrapper.eq(AioveuDepartment::getDeptId, entity.getDeptId())
                    .select(AioveuDepartment::getDeptName); // 只选择需要的字段

            AioveuDepartment department = aioveuDepartmentService.getOne(Wrapper);

            if (department != null) {
                form.setDeptName(department.getDeptName());
            }
        }

        if (entity.getPositionId() != null) {
            // 使用 MyBatis-Plus 的 LambdaQueryWrapper 查询信息
            LambdaQueryWrapper<AioveuPosition> Wrapper = new LambdaQueryWrapper<>();
            Wrapper.eq(AioveuPosition::getPositionId, entity.getPositionId())
                    .select(AioveuPosition::getPositionName); // 只选择需要的字段

            AioveuPosition position = aioveuPositionService.getOne(Wrapper);
            //AioveuDepartment department = aioveuDepartmentService.getOne(Wrapper);

            if (position != null) {
                form.setPositionName(position.getPositionName());
            }
        }


//        // 设置部门名称
//        if (entity.getDeptId() != null) {
//            AioveuDepartment department = aioveuDepartmentService.getById(entity.getDeptId());
//            if (department != null) {
//                form.setPositionName(department.getDeptName());
//            }
//        }
//
//        // 设置岗位名称
//        if (entity.getPositionId() != null) {
//            AioveuPosition position = aioveuPositionService.getById(entity.getPositionId());
//            if (position != null) {
//                form.setPositionName(position.getPositionName());
//            }
//        }

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
        // 字段1：检查员工编号是否唯一（对于不依赖外键的字段，不可重复）
        LambdaQueryWrapper<AioveuEmployee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AioveuEmployee::getEmpCode, formData.getEmpCode());
        if (count(queryWrapper) > 0) {
            throw new ServiceException("员工编号: " + formData.getEmpCode() + " 已存在");
        }

        // 问题：继续使用同一个queryWrapper，添加了额外条件
        // 字段2：检查是否已存在相同名称的记录（对于不依赖外键的字段，不可重复）
        LambdaQueryWrapper<AioveuEmployee> nameQueryWrapper = new LambdaQueryWrapper<>();
        nameQueryWrapper.eq(AioveuEmployee::getName, formData.getName());
        if (count(nameQueryWrapper) > 0) {
            throw new RuntimeException("姓名: "+ formData.getName() +"已存在");
        }

        // 字段3：检查员工编号是否唯一（对于不依赖外键的字段，不可重复）
        LambdaQueryWrapper<AioveuEmployee> idWrapper = new LambdaQueryWrapper<>();
        idWrapper.eq(AioveuEmployee::getIdCard, formData.getIdCard());
        if (count(idWrapper) > 0) {
            throw new ServiceException("身份证号: " + formData.getIdCard() + " 已存在");
        }

        // 字段4：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "岗位不存在")
        LambdaQueryWrapper<AioveuDepartment> depWrapper = new LambdaQueryWrapper<>();
        depWrapper.eq(AioveuDepartment::getDeptName, formData.getDeptName());

        AioveuDepartment department = aioveuDepartmentService.getOne(depWrapper);
        if (department != null) {
            formData.setDeptId(department.getDeptId());
        } else {
            throw new RuntimeException("部门: " + formData.getDeptName() + " 不存在");
        }

        // 字段5：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "岗位不存在")
        LambdaQueryWrapper<AioveuPosition> positionWrapper = new LambdaQueryWrapper<>();
        positionWrapper.eq(AioveuPosition::getPositionName, formData.getPositionName());

        AioveuPosition position = aioveuPositionService.getOne(positionWrapper);
        if (position != null) {
            formData.setPositionId(position.getPositionId());
        } else {
            throw new RuntimeException("岗位： " + formData.getPositionName() + " 不存在");
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
        //--------------------------------------------------------------------------------

        // 字段4：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "岗位不存在")
        LambdaQueryWrapper<AioveuDepartment> depWrapper = new LambdaQueryWrapper<>();
        depWrapper.eq(AioveuDepartment::getDeptName, formData.getDeptName());

        AioveuDepartment department = aioveuDepartmentService.getOne(depWrapper);
        if (department != null) {
            formData.setDeptId(department.getDeptId());
        } else {
            throw new RuntimeException("部门: " + formData.getDeptName() + " 不存在");
        }

        // 字段5：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "岗位不存在")
        LambdaQueryWrapper<AioveuPosition> positionWrapper = new LambdaQueryWrapper<>();
        positionWrapper.eq(AioveuPosition::getPositionName, formData.getPositionName());

        AioveuPosition position = aioveuPositionService.getOne(positionWrapper);
        if (position != null) {
            formData.setPositionId(position.getPositionId());
        } else {
            throw new RuntimeException("岗位： " + formData.getPositionName() + " 不存在");
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

        // 使用 LambdaQueryWrapper，编译时安全
//        List<AioveuEmployee> employees = lambdaQuery()
//                .select(AioveuEmployee::getEmployeeId, AioveuEmployee::getName)
//                .in(AioveuEmployee::getEmployeeId, employeeIds)
//                .list();

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
