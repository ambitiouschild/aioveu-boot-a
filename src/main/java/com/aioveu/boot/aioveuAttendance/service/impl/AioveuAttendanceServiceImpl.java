package com.aioveu.boot.aioveuAttendance.service.impl;

import cn.idev.excel.util.StringUtils;
import com.aioveu.boot.aioveuDepartment.model.entity.AioveuDepartment;
import com.aioveu.boot.aioveuDepartment.model.vo.AioveuDepartmentVO;
import com.aioveu.boot.aioveuDepartment.service.AioveuDepartmentService;
import com.aioveu.boot.aioveuEmployee.model.entity.AioveuEmployee;
import com.aioveu.boot.aioveuEmployee.service.AioveuEmployeeService;
import com.aioveu.boot.aioveuEmployee.service.impl.EmployeeNameSetter;
import com.aioveu.boot.aioveuPosition.model.form.AioveuPositionForm;
import com.aioveu.boot.aioveuPosition.model.vo.AioveuPositionVO;
import com.aliyun.oss.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aioveu.boot.aioveuAttendance.mapper.AioveuAttendanceMapper;
import com.aioveu.boot.aioveuAttendance.service.AioveuAttendanceService;
import com.aioveu.boot.aioveuAttendance.model.entity.AioveuAttendance;
import com.aioveu.boot.aioveuAttendance.model.form.AioveuAttendanceForm;
import com.aioveu.boot.aioveuAttendance.model.query.AioveuAttendanceQuery;
import com.aioveu.boot.aioveuAttendance.model.vo.AioveuAttendanceVO;
import com.aioveu.boot.aioveuAttendance.converter.AioveuAttendanceConverter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

/**
 * 考勤信息服务实现类
 *
 * @author 可我不敌可爱
 * @since 2025-08-18 21:52
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class AioveuAttendanceServiceImpl extends ServiceImpl<AioveuAttendanceMapper, AioveuAttendance> implements AioveuAttendanceService {

    private final AioveuAttendanceConverter aioveuAttendanceConverter;
    //添加部门服务依赖,注入 `AioveuEmployeeService`用于查询员工信息
    private final AioveuEmployeeService aioveuEmployeeService;


    /**
    * 获取考勤信息分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AioveuAttendanceVO>} 考勤信息分页列表
    */
    @Override
    public IPage<AioveuAttendanceVO> getAioveuAttendancePage(AioveuAttendanceQuery queryParams) {
        // 处理员工名称映射
        if (StringUtils.isNotBlank(queryParams.getEmployeeName())) {
                Long employeeId = aioveuEmployeeService.getIdByName(queryParams.getEmployeeName());
            queryParams.setEmployeeId(employeeId);
        }


        Page<AioveuAttendanceVO> pageVO = this.baseMapper.getAioveuAttendancePage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );

        // 设置员工名称
        setEmployeeNames(pageVO.getRecords());

        return pageVO;
    }
    
    /**
     * 获取考勤信息表单数据
     *
     * @param id 考勤信息ID
     * @return 考勤信息表单数据
     */
    @Override
    public AioveuAttendanceForm getAioveuAttendanceFormData(Long id) {
        // 1. 根据ID获取实体信息
        AioveuAttendance entity = this.getById(id);
        if (entity == null) {
            throw new ServiceException("不存在");
        }
        // 2. 将实体转换为表单对象
        AioveuAttendanceForm form = aioveuAttendanceConverter.toForm(entity);

        // 3. 处理映射信息（如果存在）
        if (entity.getEmployeeId() != null) {
            LambdaQueryWrapper<AioveuEmployee> Wrapper = new LambdaQueryWrapper<>();
            Wrapper.eq(AioveuEmployee::getEmployeeId, entity.getEmployeeId())
                    .select(AioveuEmployee::getName); // 只选择需要的字段

            AioveuEmployee employee = aioveuEmployeeService.getOne(Wrapper);

            if (employee != null) {
                form.setEmployeeName(employee.getName());
            }
        }

//        // 设置员工姓名
//        if (entity.getAttendanceId() != null) {
//            AioveuEmployee employee = aioveuEmployeeService.getById(entity.getEmployeeId());
//            if (employee != null) {
//                form.setEmployeeName(employee.getName());
//            }
//        }


        return form;
    }
    
    /**
     * 新增考勤信息
     *
     * @param formData 考勤信息表单对象
     * @return 是否新增成功
     */
    @Override
    public boolean saveAioveuAttendance(AioveuAttendanceForm formData) {
        // 字段1：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在")
        LambdaQueryWrapper<AioveuEmployee> employeeWrapper = new LambdaQueryWrapper<>();
        employeeWrapper.eq(AioveuEmployee::getName, formData.getEmployeeName());

        AioveuEmployee employee = aioveuEmployeeService.getOne(employeeWrapper);
        if (employee != null) {
            formData.setEmployeeId(employee.getEmployeeId());
        } else {
            throw new RuntimeException("员工: " + formData.getEmployeeName() + " 不存在");
        }


        AioveuAttendance entity = aioveuAttendanceConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新考勤信息
     *
     * @param id   考勤信息ID
     * @param formData 考勤信息表单对象
     * @return 是否修改成功
     */
    @Override
    public boolean updateAioveuAttendance(Long id,AioveuAttendanceForm formData) {
        log.info("开始更新考勤记录: ID={}", id);

        // 1. 获取原始考勤信息
        AioveuAttendance original = getById(id);
        if (original == null) {
            log.error("考勤记录不存在: ID={}", id);
            throw new ServiceException("考勤记录不存在");
        }

        // 字段1：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在")
        LambdaQueryWrapper<AioveuEmployee> employeeWrapper = new LambdaQueryWrapper<>();
        employeeWrapper.eq(AioveuEmployee::getName, formData.getEmployeeName());

        AioveuEmployee employee = aioveuEmployeeService.getOne(employeeWrapper);
        if (employee != null) {
            formData.setEmployeeId(employee.getEmployeeId());
        } else {
            throw new RuntimeException("员工: " + formData.getEmployeeName() + " 不存在");
        }

        // 3. 将表单数据转换为实体对象
        AioveuAttendance entity = aioveuAttendanceConverter.toEntity(formData);

        // 4. 设置考勤ID
        entity.setAttendanceId(id);

        // 5. 复制未修改的字段
        entity.setCreateTime(original.getCreateTime());

        return this.updateById(entity);
    }
    
    /**
     * 删除考勤信息
     *
     * @param ids 考勤信息ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    @Override
    public boolean deleteAioveuAttendances(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的考勤信息数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }


    //---------------------------------------------------

//    /**
//     *  批量设置名称到VO对象，将AttendanceVO岗位表视图对象的员工id,转换为员工姓名  ,只被分页列表调用
//     */
//    private void setEmployeeNames(List<AioveuAttendanceVO> attendanceVOs) {
//        if (attendanceVOs == null || attendanceVOs.isEmpty()) {
//            return;
//        }
//
//        // 获取所有员工ID
//        List<Long> employeeIds = attendanceVOs.stream()
//                .map(AioveuAttendanceVO::getEmployeeId)
//                .filter(Objects::nonNull)
//                .distinct()
//                .collect(Collectors.toList());
//
//        if (employeeIds.isEmpty()) {
//            return;
//        }
//
//        // 批量查询员工信息
//        Map<Long, String> employeeMap = aioveuEmployeeService.getEmployeeMapByIds(employeeIds);
//
//        // 设置员工姓名
//        attendanceVOs.forEach(vo -> {
//            //遍历列表：使用 forEach方法遍历 employeeVOs中的每个员工对象（vo）。
//            //检查 vo.getDeptId()非空（防止空指针异常）
//            //同时检查 deptMap中包含该岗位ID的键（确保映射中存在对应关系）
//            if (vo.getEmployeeId() != null && employeeMap.containsKey(vo.getEmployeeId())) {
//                //通过 deptMap.getOrDefault()方法获取岗位名称：若存在则返回映射值，不存在则返回默认值「未知部门」
//                //调用 vo.setDeptName()将名称设置到员工对象中
//                vo.setEmployeeName(employeeMap.getOrDefault(vo.getEmployeeId(), "未知员工"));
//            }
//        });
//    }

    /**
     * 批量设置员工姓名到考勤VO对象
     */
    private void setEmployeeNames(List<AioveuAttendanceVO> attendanceVOS) {
        EmployeeNameSetter.setEmployeeNames(
                attendanceVOS,
                AioveuAttendanceVO::getEmployeeId, // 获取员工ID
                AioveuAttendanceVO::setEmployeeName, // 设置员工姓名
                aioveuEmployeeService
        );
    }


}
