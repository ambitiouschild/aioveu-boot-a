package com.aioveu.boot.aioveuSalary.service.impl;

import cn.idev.excel.util.StringUtils;
import com.aioveu.boot.aioveuEmployee.model.entity.AioveuEmployee;
import com.aioveu.boot.aioveuEmployee.service.AioveuEmployeeService;
import com.aioveu.boot.aioveuEmployee.service.impl.EmployeeNameSetter;
import com.aioveu.boot.aioveuEmployee.service.impl.NameValidator;
import com.aliyun.oss.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aioveu.boot.aioveuSalary.mapper.AioveuSalaryMapper;
import com.aioveu.boot.aioveuSalary.service.AioveuSalaryService;
import com.aioveu.boot.aioveuSalary.model.entity.AioveuSalary;
import com.aioveu.boot.aioveuSalary.model.form.AioveuSalaryForm;
import com.aioveu.boot.aioveuSalary.model.query.AioveuSalaryQuery;
import com.aioveu.boot.aioveuSalary.model.vo.AioveuSalaryVO;
import com.aioveu.boot.aioveuSalary.converter.AioveuSalaryConverter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

/**
 * 员工工资明细服务实现类
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 17:14
 */
@Service
@RequiredArgsConstructor
public class AioveuSalaryServiceImpl extends ServiceImpl<AioveuSalaryMapper, AioveuSalary> implements AioveuSalaryService {

    private final AioveuSalaryConverter aioveuSalaryConverter;
    @Autowired
    private AioveuEmployeeService aioveuEmployeeService;

    /**
    * 获取员工工资明细分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AioveuSalaryVO>} 员工工资明细分页列表
    */
    @Override
    public IPage<AioveuSalaryVO> getAioveuSalaryPage(AioveuSalaryQuery queryParams) {

        // 处理部门名称映射
        if (StringUtils.isNotBlank(queryParams.getEmployeeName())) {
            Long employeeId = aioveuEmployeeService.getIdByName(queryParams.getEmployeeName());
            queryParams.setEmployeeId(employeeId);
        }

        Page<AioveuSalaryVO> pageVO = this.baseMapper.getAioveuSalaryPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );

        // 设置员工名称
        setEmployeeNames(pageVO.getRecords());

        return pageVO;
    }
    
    /**
     * 获取员工工资明细表单数据
     *
     * @param id 员工工资明细ID
     * @return 员工工资明细表单数据
     */
    @Override
    public AioveuSalaryForm getAioveuSalaryFormData(Long id) {
        // 1. 根据ID获取实体信息
        AioveuSalary entity = this.getById(id);
        if (entity == null) {
            throw new ServiceException("不存在");
        }
        // 2. 将实体转换为表单对象
        AioveuSalaryForm form = aioveuSalaryConverter.toForm(entity);

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

//        // 设置名称
//        if (entity.getEmployeeId() != null) { //通过实例变量调用非静态方法
//            AioveuEmployee employee = aioveuEmployeeService.getById(entity.getEmployeeId());
//            if (employee != null) {
//                form.setEmployeeName(employee.getName());
//            }
//        }
        return form;
    }
    
    /**
     * 新增员工工资明细
     *
     * @param formData 员工工资明细表单对象
     * @return 是否新增成功
     */
    @Override
    public boolean saveAioveuSalary(AioveuSalaryForm formData) {


        // 字段3：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuSalaryForm::getEmployeeName,  // 获取经理姓名的方法
                AioveuEmployee::getName,  // 实体字段：员工姓名
//                (form, id) -> form.setManagerId(id), // 设置经理ID的方法  // 使用显式Lambda（推荐）
                AioveuSalaryForm::setEmployeeId, // 直接使用方法引用
                AioveuEmployee::getEmployeeId, // 从员工实体获取ID的方法
                aioveuEmployeeService,  // 员工服务（不是this）
                "员工"  // 实体名称（用于错误消息）
        );

        AioveuSalary entity = aioveuSalaryConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新员工工资明细
     *
     * @param id   员工工资明细ID
     * @param formData 员工工资明细表单对象
     * @return 是否修改成功
     */
    @Override
    public boolean updateAioveuSalary(Long id,AioveuSalaryForm formData) {
        // 1. 验证ID对应的记录是否存在
        AioveuSalary original = getById(id);
        if (original == null) {
            throw new RuntimeException("工资记录不存在，ID: " + id);
        }

        // 字段3：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuSalaryForm::getEmployeeName,  // 获取经理姓名的方法
                AioveuEmployee::getName,  // 实体字段：员工姓名
//                (form, id) -> form.setManagerId(id), // 设置经理ID的方法  // 使用显式Lambda（推荐）
                AioveuSalaryForm::setEmployeeId, // 直接使用方法引用
                AioveuEmployee::getEmployeeId, // 从员工实体获取ID的方法
                aioveuEmployeeService,  // 员工服务（不是this）
                "员工"  // 实体名称（用于错误消息）
        );

        // 2. 转换表单数据到实体对象并设置ID
        AioveuSalary entity = aioveuSalaryConverter.toEntity(formData);
        entity.setId(id);  // 设置主键ID

        // 3. 保留审计字段(创建时间不变，更新时间用当前时间)
        entity.setCreateTime(original.getCreateTime());

        // 4. 重新计算实发工资
        BigDecimal netSalary = entity.getBaseSalary()
                .add(entity.getPerformanceBonus() != null ? entity.getPerformanceBonus() : BigDecimal.ZERO)
                .add(entity.getOvertimePay() != null ? entity.getOvertimePay() : BigDecimal.ZERO)
                .add(entity.getAllowances() != null ? entity.getAllowances() : BigDecimal.ZERO)
                .subtract(entity.getSocialSecurity() != null ? entity.getSocialSecurity() : BigDecimal.ZERO)
                .subtract(entity.getHousingFund() != null ? entity.getHousingFund() : BigDecimal.ZERO)
                .subtract(entity.getTax() != null ? entity.getTax() : BigDecimal.ZERO)
                .subtract(entity.getOtherDeductions() != null ? entity.getOtherDeductions() : BigDecimal.ZERO);
        entity.setNetSalary(netSalary);

        /*BigDecimal netSalary = baseSalary
         *    + performanceBonus
         *    + overtimePay
         *    + allowances
         *    - socialSecurity
         *    - housingFund
         *    - tax
         *    - otherDeductions;
         *   entity.setNetSalary(netSalary);
        **/

        // 5. 检查唯一性约束 (employee_id + salary_period)
        if (isDuplicateRecord(entity, original)) {
            throw new RuntimeException("相同员工在工资周期内的记录已存在");
        }

        // 6. 执行更新
        return this.updateById(entity);
    }

    // 检查唯一性约束的辅助方法
    private boolean isDuplicateRecord(AioveuSalary newData, AioveuSalary original) {
        // 关键字段未修改时无需检查
        if (Objects.equals(newData.getEmployeeId(), original.getEmployeeId()) &&
                Objects.equals(newData.getSalaryPeriod(), original.getSalaryPeriod())) {
            return false;
        }

        // 查询是否已存在相同记录
        LambdaQueryWrapper<AioveuSalary> query = new LambdaQueryWrapper<>();
        query.eq(AioveuSalary::getEmployeeId, newData.getEmployeeId())
                .eq(AioveuSalary::getSalaryPeriod, newData.getSalaryPeriod())
                .ne(AioveuSalary::getId, newData.getId()); // 排除自身记录

        return count(query) > 0;
    }
    
    /**
     * 删除员工工资明细
     *
     * @param ids 员工工资明细ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    @Override
    public boolean deleteAioveuSalarys(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的员工工资明细数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

    /**
     * 批量设置员工姓名到薪资VO对象
     */
    private void setEmployeeNames(List<AioveuSalaryVO> salaryVOS) {
        EmployeeNameSetter.setEmployeeNames(
                salaryVOS,
                AioveuSalaryVO::getEmployeeId, // 获取员工ID
                AioveuSalaryVO::setEmployeeName, // 设置员工姓名
                aioveuEmployeeService
        );
    }
}
