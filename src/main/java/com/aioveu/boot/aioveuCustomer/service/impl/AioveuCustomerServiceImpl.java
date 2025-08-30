package com.aioveu.boot.aioveuCustomer.service.impl;
import cn.idev.excel.util.StringUtils;
import com.aioveu.boot.aioveuContact.mapper.AioveuContactMapper;
import com.aioveu.boot.aioveuCustomer.model.vo.CustomerOptionVO;
import com.aioveu.boot.aioveuDepartment.mapper.AioveuDepartmentMapper;
import com.aioveu.boot.aioveuDepartment.model.entity.AioveuDepartment;
import com.aioveu.boot.aioveuDepartment.model.vo.DeptOptionVO;
import com.aioveu.boot.aioveuEmployee.model.entity.AioveuEmployee;
import com.aioveu.boot.aioveuEmployee.service.AioveuEmployeeService;
import com.aioveu.boot.aioveuEmployee.service.impl.EmployeeNameSetter;
import com.aioveu.boot.aioveuEmployee.service.impl.NameValidator;
import com.aioveu.boot.aioveuPerformance.model.vo.AioveuPerformanceVO;
import com.aioveu.boot.aioveuWarehouse.model.entity.AioveuWarehouse;
import com.aioveu.boot.aioveuWarehouse.model.form.AioveuWarehouseForm;
import com.aliyun.oss.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aioveu.boot.aioveuCustomer.mapper.AioveuCustomerMapper;
import com.aioveu.boot.aioveuCustomer.service.AioveuCustomerService;
import com.aioveu.boot.aioveuCustomer.model.entity.AioveuCustomer;
import com.aioveu.boot.aioveuCustomer.model.form.AioveuCustomerForm;
import com.aioveu.boot.aioveuCustomer.model.query.AioveuCustomerQuery;
import com.aioveu.boot.aioveuCustomer.model.vo.AioveuCustomerVO;
import com.aioveu.boot.aioveuCustomer.converter.AioveuCustomerConverter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
/**
 * 客户信息服务实现类
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:42
 */
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AioveuCustomerServiceImpl extends ServiceImpl<AioveuCustomerMapper, AioveuCustomer> implements AioveuCustomerService {

    private final AioveuCustomerConverter aioveuCustomerConverter;

    @Autowired
    private AioveuEmployeeService aioveuEmployeeService;


    @Autowired
    private AioveuCustomerMapper aioveuCustomerMapper;

    @Override
    public Long getIdByName(String name) {
        return aioveuCustomerMapper.findIdByName(name);
    }

    /**
    * 获取客户信息分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AioveuCustomerVO>} 客户信息分页列表
    */
    @Override
    public IPage<AioveuCustomerVO> getAioveuCustomerPage(AioveuCustomerQuery queryParams) {

        // 处理销售负责人名称映射
        if (StringUtils.isNotBlank(queryParams.getSalesRepName())) {
            Long employeeId = aioveuEmployeeService.getIdByName(queryParams.getSalesRepName());
            queryParams.setSalesRepId(employeeId);
        }


        Page<AioveuCustomerVO> pageVO = this.baseMapper.getAioveuCustomerPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );

        setSalesRepNames(pageVO.getRecords());


        return pageVO;
    }
    
    /**
     * 获取客户信息表单数据
     *
     * @param id 客户信息ID
     * @return 客户信息表单数据
     */
    @Override
    public AioveuCustomerForm getAioveuCustomerFormData(Long id) {
        // 1. 根据ID获取实体信息
        AioveuCustomer entity = this.getById(id);
        if (entity == null) {
            throw new ServiceException("不存在");
        }
        // 2. 将实体转换为表单对象
        AioveuCustomerForm form = aioveuCustomerConverter.toForm(entity);

        // 3. 处理映射信息（如果存在）
        if (entity.getSalesRepId() != null) {
            LambdaQueryWrapper<AioveuEmployee> Wrapper = new LambdaQueryWrapper<>();
            Wrapper.eq(AioveuEmployee::getEmployeeId, entity.getSalesRepId())
                    .select(AioveuEmployee::getName); // 只选择需要的字段

            AioveuEmployee employee = aioveuEmployeeService.getOne(Wrapper);

            if (employee != null) {
                form.setSalesRepName(employee.getName());
            }
        }

        return form;
    }
    
    /**
     * 新增客户信息
     *
     * @param formData 客户信息表单对象
     * @return 是否新增成功
     */
    @Override
    public boolean saveAioveuCustomer(AioveuCustomerForm formData) {
        // 字段1：检查编号是否唯一（对于不依赖外键的字段，不可重复）
        NameValidator.validateEntityUnique(
                formData,
                AioveuCustomerForm::getCustomerNo,
                AioveuCustomer::getCustomerNo,
                null,
                this,
                "客户编号： "
        );

        // 字段1：检查编号是否唯一（对于不依赖外键的字段，不可重复）
        NameValidator.validateEntityUnique(
                formData,
                AioveuCustomerForm::getName,
                AioveuCustomer::getName,
                null,
                this,
                "客户名称： "
        );

        // 字段4：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuCustomerForm::getSalesRepName,
                AioveuEmployee::getName,
                AioveuCustomerForm::setSalesRepId,
                AioveuEmployee::getEmployeeId,
                aioveuEmployeeService,
                "销售负责人： "
        );


        AioveuCustomer entity = aioveuCustomerConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新客户信息
     *
     * @param id   客户信息ID
     * @param formData 客户信息表单对象
     * @return 是否修改成功
     */
    @Override
    public boolean updateAioveuCustomer(Long id,AioveuCustomerForm formData) {

        // 1. 验证ID对应的记录是否存在
        AioveuCustomer original = getById(id);
        if (original == null) {
            log.error("记录不存在: ID={}", id);
            throw new ServiceException("记录不存在");

        }

        // 字段4：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuCustomerForm::getSalesRepName,
                AioveuEmployee::getName,
                AioveuCustomerForm::setSalesRepId,
                AioveuEmployee::getEmployeeId,
                aioveuEmployeeService,
                "销售负责人： "
        );

        // 2. 将表单数据转换为实体对象
        AioveuCustomer entity = aioveuCustomerConverter.toEntity(formData);

        // 3. 设置ID
        entity.setId(id);

        // 4. 复制未修改的字段,保留审计字段(创建时间不变，更新时间用当前时间)
        entity.setCreateTime(original.getCreateTime());

        // 5. 执行更新
        return this.updateById(entity);
    }
    
    /**
     * 删除客户信息
     *
     * @param ids 客户信息ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    @Override
    public boolean deleteAioveuCustomers(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的客户信息数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

    /**
     * 批量设置名称到VO对象，将视图对象的员工id,转换为员工姓名
     */
    private void setSalesRepNames(List<AioveuCustomerVO> customerVOS) {
        EmployeeNameSetter.setEmployeeNames(
                customerVOS,
                AioveuCustomerVO::getSalesRepId, // 获取ID
                AioveuCustomerVO::setSalesRepName, // 设置姓名
                aioveuEmployeeService
        );
    }

    /**
     * 批量获取客户信息（新增方法）
     */
    @Override
    public Map<Long, String> getCustomerMapByIds(List<Long> customerIds) {
        if (customerIds == null || customerIds.isEmpty()) {
            return Map.of();
        }

        // 批量查询客户信息
        List<AioveuCustomer> customers = this.listByIds(customerIds);

        // 转换为Map: key=客户ID, value=客户名称
        return customers.stream()
                .collect(Collectors.toMap(
                        AioveuCustomer::getId,
                        AioveuCustomer::getName
                ));
    }

    /**
     * 获取所有客户列表（用于下拉选择框）
     *
     * @return 客户选项列表
     */
    @Override
    public List<CustomerOptionVO> getAllCustomerOptions() {
        // 查询所有客户
        List<AioveuCustomer> customers = this.list();

        // 转换为选项对象
        List<CustomerOptionVO>  customerOptionVO  = customers.stream()
                .map(customer -> new CustomerOptionVO(customer.getId(), customer.getName()))
                .collect(Collectors.toList());

        return customerOptionVO;
    }

}
