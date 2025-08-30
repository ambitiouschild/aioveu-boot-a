package com.aioveu.boot.aioveuPerformance.service.impl;

import cn.idev.excel.util.StringUtils;
import com.aioveu.boot.aioveuEmployee.model.entity.AioveuEmployee;
import com.aioveu.boot.aioveuEmployee.service.AioveuEmployeeService;
import com.aioveu.boot.aioveuEmployee.service.impl.EmployeeNameSetter;
import com.aioveu.boot.aioveuEmployee.service.impl.NameValidator;
import com.aliyun.oss.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aioveu.boot.aioveuPerformance.mapper.AioveuPerformanceMapper;
import com.aioveu.boot.aioveuPerformance.service.AioveuPerformanceService;
import com.aioveu.boot.aioveuPerformance.model.entity.AioveuPerformance;
import com.aioveu.boot.aioveuPerformance.model.form.AioveuPerformanceForm;
import com.aioveu.boot.aioveuPerformance.model.query.AioveuPerformanceQuery;
import com.aioveu.boot.aioveuPerformance.model.vo.AioveuPerformanceVO;
import com.aioveu.boot.aioveuPerformance.converter.AioveuPerformanceConverter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

/**
 * 员工绩效考评服务实现类
 *
 * @author 可我不敌可爱
 * @since 2025-08-18 22:48
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AioveuPerformanceServiceImpl extends ServiceImpl<AioveuPerformanceMapper, AioveuPerformance> implements AioveuPerformanceService {

    private final AioveuPerformanceConverter aioveuPerformanceConverter;

    private static final BigDecimal GRADE_A_THRESHOLD = new BigDecimal("90.0");
    private static final BigDecimal GRADE_B_THRESHOLD = new BigDecimal("80.0");
    private static final BigDecimal GRADE_C_THRESHOLD = new BigDecimal("70.0");

    //添加部门服务依赖,注入 `AioveuDepartmentService`用于查询部门信息
    private final AioveuEmployeeService aioveuEmployeeService;

    /**
    * 获取员工绩效考评分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AioveuPerformanceVO>} 员工绩效考评分页列表
    */
    @Override
    public IPage<AioveuPerformanceVO> getAioveuPerformancePage(AioveuPerformanceQuery queryParams) {

        // 处理员工名称映射
        if (StringUtils.isNotBlank(queryParams.getEmployeeName())) {
            Long employeeId = aioveuEmployeeService.getIdByName(queryParams.getEmployeeName());
            queryParams.setEmployeeId(employeeId);
        }


        Page<AioveuPerformanceVO> pageVO = this.baseMapper.getAioveuPerformancePage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );

        // 设置员工姓名
        setEmployeeNames(pageVO.getRecords());


        return pageVO;
    }
    
    /**
     * 获取员工绩效考评表单数据
     *
     * @param id 员工绩效考评ID
     * @return 员工绩效考评表单数据
     */
    @Override
    public AioveuPerformanceForm getAioveuPerformanceFormData(Long id) {
        // 1. 根据ID获取实体信息
        AioveuPerformance entity = this.getById(id);
        if (entity == null) {
            throw new ServiceException("不存在");
        }
        // 2. 将实体转换为表单对象
        AioveuPerformanceForm form = aioveuPerformanceConverter.toForm(entity);

        // 3. 处理映射信息（如果存在）
        if (entity.getEmployeeId() != null) {
            // 使用 MyBatis-Plus 的 LambdaQueryWrapper 查询信息
            // 创建一个 LambdaQueryWrapper 对象，用于构建查询条件
            // 泛型 <AioveuDepartment> 指定了查询的实体类型
            LambdaQueryWrapper<AioveuEmployee> Wrapper = new LambdaQueryWrapper<>();
            // 添加查询条件：部门ID等于指定值
            // AioveuDepartment::getDeptId 是方法引用，表示查询 dept_id 字段
            // entity.getDeptId() 是获取要查询的具体部门ID值
            Wrapper.eq(AioveuEmployee::getEmployeeId, entity.getEmployeeId())
                    // 指定只选择 dept_name 字段，而不是所有字段
                    // 这是一个性能优化，减少不必要的数据传输
                    .select(AioveuEmployee::getName); // 只选择需要的字段

            AioveuEmployee employee = aioveuEmployeeService.getOne(Wrapper);

            if (employee != null) {
                form.setEmployeeName(employee.getName());
            }
        }


//        // 设置员工姓名
//        if (entity.getEmployeeId() != null) { //通过实例变量（调用非静态方法
//            AioveuEmployee employee = aioveuEmployeeService.getById(entity.getEmployeeId());
//            if (employee != null) {
//                form.setEmployeeName(employee.getName());
//            }
//        }
        return form;
    }
    
    /**
     * 新增员工绩效考评
     *
     * @param formData 员工绩效考评表单对象
     * @return 是否新增成功
     */
    @Override
    public boolean saveAioveuPerformance(AioveuPerformanceForm formData) {


        NameValidator.validateEntityExists(
                formData,
                AioveuPerformanceForm::getEmployeeName, // 方法引用
                AioveuEmployee::getName, // 方法引用
                AioveuPerformanceForm::setEmployeeId,
                AioveuEmployee::getEmployeeId, // 方法引用返回 long
                aioveuEmployeeService,
                "员工"
        );

        AioveuPerformance entity = aioveuPerformanceConverter.toEntity(formData);
        // 4. 计算绩效等级（在Java层计算）
        calculatePerformanceGrade(entity);
        return this.save(entity);
    }
    
    /**
     * 更新员工绩效考评
     *
     * @param id   员工绩效考评ID
     * @param formData 员工绩效考评表单对象
     * @return 是否修改成功
     */
    @Override
    public boolean updateAioveuPerformance(Long id,AioveuPerformanceForm formData) {
        log.info("开始更新绩效考评记录: ID={}", id);

        // 1. 获取原始绩效考评信息
        AioveuPerformance original = getById(id);
        if (original == null) {
            log.error("绩效考评记录不存在: ID={}", id);
            throw new ServiceException("绩效考评记录不存在");
        }

        NameValidator.validateEntityExists(
                formData,
                AioveuPerformanceForm::getEmployeeName, // 方法引用
                AioveuEmployee::getName, // 方法引用
                AioveuPerformanceForm::setEmployeeId,
                AioveuEmployee::getEmployeeId, // 方法引用返回 long
                aioveuEmployeeService,
                "员工"
        );

        // 3. 将表单数据转换为实体对象
        AioveuPerformance entity = aioveuPerformanceConverter.toEntity(formData);


        // 4. 设置绩效考评ID
        entity.setId(id);

        // 4.复制未修改的字段
        entity.setCreateTime(original.getCreateTime());

        // 4. 计算绩效等级（在Java层计算）
        calculatePerformanceGrade(entity);




        // 6. 执行更新 // 6. 使用UpdateWrapper更新，排除生成列  updateWithWrapper(entity);
        return this.updateById(entity);
    }

    private void calculatePerformanceGrade(AioveuPerformance entity) {
        if (entity.getKpiScore() == null) {
            entity.setPerformanceGrade("D");
            return;
        }

        //在Java中，不能直接将 Integer赋值给 BigDecimal，需要进行类型转换
        BigDecimal kpiScore = entity.getKpiScore();
        if (kpiScore.compareTo(GRADE_A_THRESHOLD) >= 0) {
            entity.setPerformanceGrade("A");
        } else if (kpiScore.compareTo(GRADE_B_THRESHOLD) >= 0) {
            entity.setPerformanceGrade("B");
        } else if (kpiScore.compareTo(GRADE_C_THRESHOLD) >= 0) {
            entity.setPerformanceGrade("C");
        } else {
            entity.setPerformanceGrade("D");
        }
    }

    /**
     * 删除员工绩效考评
     *
     * @param ids 员工绩效考评ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    @Override
    public boolean deleteAioveuPerformances(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的员工绩效考评数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }


    /**
     *  批量设置名称到VO对象，将AioveuPerformanceVO绩效表视图对象的员工id,转换为员工姓名，只被分页列表调用
     */
//    private void setEmployeeNames(List<AioveuPerformanceVO> performanceVOS) {
//        if (performanceVOS == null || performanceVOS.isEmpty()) {
//            return;
//        }
//
//        // 获取所有员工ID
//        List<Long> employeeIds = performanceVOS.stream()
//                .map(AioveuPerformanceVO::getEmployeeId)
//                .filter(Objects::nonNull)
//                .distinct()
//                .collect(Collectors.toList());
//
//        if (employeeIds.isEmpty()) {
//            return;
//        }
//
//        // 批量查询信息
//        Map<Long, String> employeeMap = aioveuEmployeeService.getEmployeeMapByIds(employeeIds);
//
//        // 设置名称
//        performanceVOS.forEach(vo -> {
//            //遍历列表：使用 forEach方法遍历 VOs中的每个员工对象（vo）。
//            //检查 vo.getId()非空（防止空指针异常）
//            //同时检查 Map中包含该ID的键（确保映射中存在对应关系）
//            if (vo.getEmployeeId() != null && employeeMap.containsKey(vo.getEmployeeId())) {
//                //通过 Map.getOrDefault()方法获取名称：若存在则返回映射值，不存在则返回默认值
//                //调用 vo.setName()将名称设置到员工对象中
//                vo.setEmployeeName(employeeMap.getOrDefault(vo.getEmployeeId(), "未知员工"));
//            }
//        });
//    }

    /**
     * 批量设置名称到VO对象，将AioveuPerformanceVO绩效表视图对象的员工id,转换为员工姓名
     */
    private void setEmployeeNames(List<AioveuPerformanceVO> performanceVOS) {
        EmployeeNameSetter.setEmployeeNames(
                performanceVOS,
                AioveuPerformanceVO::getEmployeeId, // 获取员工ID
                AioveuPerformanceVO::setEmployeeName, // 设置员工姓名
                aioveuEmployeeService
        );
    }
}
