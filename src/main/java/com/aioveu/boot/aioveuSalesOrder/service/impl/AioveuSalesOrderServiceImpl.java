package com.aioveu.boot.aioveuSalesOrder.service.impl;

import cn.idev.excel.util.StringUtils;
import com.aioveu.boot.aioveuContact.model.entity.AioveuContact;
import com.aioveu.boot.aioveuContact.service.AioveuContactService;
import com.aioveu.boot.aioveuContact.service.impl.ContactNameSetter;
import com.aioveu.boot.aioveuCustomer.model.entity.AioveuCustomer;
import com.aioveu.boot.aioveuCustomer.service.AioveuCustomerService;
import com.aioveu.boot.aioveuCustomer.service.impl.CustomerNameSetter;
import com.aioveu.boot.aioveuDepartment.mapper.AioveuDepartmentMapper;
import com.aioveu.boot.aioveuDepartment.model.entity.AioveuDepartment;
import com.aioveu.boot.aioveuDepartment.model.vo.DeptOptionVO;
import com.aioveu.boot.aioveuEmployee.model.entity.AioveuEmployee;
import com.aioveu.boot.aioveuEmployee.service.AioveuEmployeeService;
import com.aioveu.boot.aioveuEmployee.service.impl.EmployeeNameSetter;
import com.aioveu.boot.aioveuEmployee.service.impl.NameValidator;
import com.aioveu.boot.aioveuPerformance.model.vo.AioveuPerformanceVO;
import com.aioveu.boot.aioveuSalesOrder.model.vo.SalesOrderOptionVO;
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
import com.aioveu.boot.aioveuSalesOrder.mapper.AioveuSalesOrderMapper;
import com.aioveu.boot.aioveuSalesOrder.service.AioveuSalesOrderService;
import com.aioveu.boot.aioveuSalesOrder.model.entity.AioveuSalesOrder;
import com.aioveu.boot.aioveuSalesOrder.model.form.AioveuSalesOrderForm;
import com.aioveu.boot.aioveuSalesOrder.model.query.AioveuSalesOrderQuery;
import com.aioveu.boot.aioveuSalesOrder.model.vo.AioveuSalesOrderVO;
import com.aioveu.boot.aioveuSalesOrder.converter.AioveuSalesOrderConverter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
/**
 * 销售订单服务实现类
 *
 * @author 可我不敌可爱
 * @since 2025-08-22 00:27
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AioveuSalesOrderServiceImpl extends ServiceImpl<AioveuSalesOrderMapper, AioveuSalesOrder> implements AioveuSalesOrderService {

    private final AioveuSalesOrderConverter aioveuSalesOrderConverter;

    @Autowired
    private AioveuEmployeeService aioveuEmployeeService;

    @Autowired
    private AioveuCustomerService aioveuCustomerService;

    @Autowired
    private AioveuContactService aioveuContactService;


    @Autowired
    private AioveuSalesOrderMapper aioveuSalesOrderMapper;

    @Override
    public Long getIdByName(String name) {
        return aioveuSalesOrderMapper.findIdByName(name);
    }


    /**
    * 获取销售订单分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AioveuSalesOrderVO>} 销售订单分页列表
    */
    @Override
    public IPage<AioveuSalesOrderVO> getAioveuSalesOrderPage(AioveuSalesOrderQuery queryParams) {


        // 处理客户名称映射
        if (StringUtils.isNotBlank(queryParams.getCustomerName())) {
            Long customerd = aioveuCustomerService.getIdByName(queryParams.getCustomerName());
            queryParams.setCustomerId(customerd);
        }
        // 处理销售名称映射
        if (StringUtils.isNotBlank(queryParams.getSalesRepName())) {
            Long employeeId = aioveuEmployeeService.getIdByName(queryParams.getSalesRepName());
            queryParams.setSalesRepId(employeeId);
        }



        Page<AioveuSalesOrderVO> pageVO = this.baseMapper.getAioveuSalesOrderPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );

        setSalesRepNames(pageVO.getRecords());

        setOperatorNames(pageVO.getRecords());

        setCustomerNames(pageVO.getRecords());

        setContactNames(pageVO.getRecords());

        return pageVO;
    }
    
    /**
     * 获取销售订单表单数据
     *
     * @param id 销售订单ID
     * @return 销售订单表单数据
     */
    @Override
    public AioveuSalesOrderForm getAioveuSalesOrderFormData(Long id) {
        // 1. 根据ID获取实体信息
        AioveuSalesOrder entity = this.getById(id);
        if (entity == null) {
            throw new ServiceException("不存在");
        }
        // 2. 将实体转换为表单对象
        AioveuSalesOrderForm form = aioveuSalesOrderConverter.toForm(entity);

        // 3. 处理映射信息（如果存在）
        if (entity.getCustomerId() != null) {
            LambdaQueryWrapper<AioveuCustomer> Wrapper = new LambdaQueryWrapper<>();
            Wrapper.eq(AioveuCustomer::getId, entity.getCustomerId())
                    .select(AioveuCustomer::getName); // 只选择需要的字段

            AioveuCustomer customer = aioveuCustomerService.getOne(Wrapper);

            if (customer != null) {
                form.setCustomerName(customer.getName());
            }
        }

        if (entity.getContactId() != null) {
            LambdaQueryWrapper<AioveuContact> Wrapper = new LambdaQueryWrapper<>();
            Wrapper.eq(AioveuContact::getId, entity.getContactId())
                    .select(AioveuContact::getName); // 只选择需要的字段

            AioveuContact contact = aioveuContactService.getOne(Wrapper);

            if (contact != null) {
                form.setContactName(contact.getName());
            }
        }

        if (entity.getSalesRepId() != null) {
            LambdaQueryWrapper<AioveuEmployee> Wrapper = new LambdaQueryWrapper<>();
            Wrapper.eq(AioveuEmployee::getEmployeeId, entity.getSalesRepId())
                    .select(AioveuEmployee::getName); // 只选择需要的字段

            AioveuEmployee employee = aioveuEmployeeService.getOne(Wrapper);

            if (employee != null) {
                form.setSalesRepName(employee.getName());
            }
        }

        if (entity.getOperatorId() != null) {
            LambdaQueryWrapper<AioveuEmployee> Wrapper = new LambdaQueryWrapper<>();
            Wrapper.eq(AioveuEmployee::getEmployeeId, entity.getOperatorId())
                    .select(AioveuEmployee::getName); // 只选择需要的字段

            AioveuEmployee employee = aioveuEmployeeService.getOne(Wrapper);

            if (employee != null) {
                form.setOperatorName(employee.getName());
            }
        }


        return form;
    }
    
    /**
     * 新增销售订单
     *
     * @param formData 销售订单表单对象
     * @return 是否新增成功
     */
    @Override
    public boolean saveAioveuSalesOrder(AioveuSalesOrderForm formData) {

        // 字段1：检查编号是否唯一（对于不依赖外键的字段，不可重复）
        NameValidator.validateEntityUnique(
                formData,
                AioveuSalesOrderForm::getOrderNo,
                AioveuSalesOrder::getOrderNo,
                null,
                this,
                "订单编号： "
        );

        // 字段2：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuSalesOrderForm::getCustomerName,
                AioveuCustomer::getName,
                AioveuSalesOrderForm::setCustomerId,
                AioveuCustomer::getId,
                aioveuCustomerService,
                "客户： "
        );

        // 字段3：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuSalesOrderForm::getContactName,
                AioveuContact::getName,
                AioveuSalesOrderForm::setContactId,
                AioveuContact::getId,
                aioveuContactService,
                "联系人： "
        );

        // 字段4：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuSalesOrderForm::getSalesRepName,
                AioveuEmployee::getName,
                AioveuSalesOrderForm::setSalesRepId,
                AioveuEmployee::getEmployeeId,
                aioveuEmployeeService,
                "销售负责人： "
        );


        // 字段5：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuSalesOrderForm::getOperatorName,
                AioveuEmployee::getName,
                AioveuSalesOrderForm::setOperatorId,
                AioveuEmployee::getEmployeeId,
                aioveuEmployeeService,
                "操作员： "
        );


        AioveuSalesOrder entity = aioveuSalesOrderConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新销售订单
     *
     * @param id   销售订单ID
     * @param formData 销售订单表单对象
     * @return 是否修改成功
     */
    @Override
    public boolean updateAioveuSalesOrder(Long id,AioveuSalesOrderForm formData) {

        // 1. 验证ID对应的记录是否存在
        AioveuSalesOrder original = getById(id);
        if (original == null) {
            log.error("记录不存在: ID={}", id);
            throw new ServiceException("记录不存在");

        }

        // 字段2：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuSalesOrderForm::getCustomerName,
                AioveuCustomer::getName,
                AioveuSalesOrderForm::setCustomerId,
                AioveuCustomer::getId,
                aioveuCustomerService,
                "客户： "
        );

        // 字段3：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuSalesOrderForm::getContactName,
                AioveuContact::getName,
                AioveuSalesOrderForm::setContactId,
                AioveuContact::getId,
                aioveuContactService,
                "联系人： "
        );

        // 字段4：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuSalesOrderForm::getSalesRepName,
                AioveuEmployee::getName,
                AioveuSalesOrderForm::setSalesRepId,
                AioveuEmployee::getEmployeeId,
                aioveuEmployeeService,
                "销售负责人： "
        );


        // 字段5：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuSalesOrderForm::getOperatorName,
                AioveuEmployee::getName,
                AioveuSalesOrderForm::setOperatorId,
                AioveuEmployee::getEmployeeId,
                aioveuEmployeeService,
                "操作员： "
        );

        // 2. 将表单数据转换为实体对象
        AioveuSalesOrder entity = aioveuSalesOrderConverter.toEntity(formData);

        // 3. 设置ID
        entity.setId(id);

        // 4. 复制未修改的字段,保留审计字段(创建时间不变，更新时间用当前时间)
        entity.setCreateTime(original.getCreateTime());

        // 5. 执行更新
        return this.updateById(entity);
    }
    
    /**
     * 删除销售订单
     *
     * @param ids 销售订单ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    @Override
    public boolean deleteAioveuSalesOrders(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的销售订单数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

    /**
     * 批量获取订单信息（新增方法）
     */
    @Override
    public Map<Long, String> getSalesOrderMapByIds(List<Long> salesOrderIds) {
        if (salesOrderIds == null || salesOrderIds.isEmpty()) {
            return Map.of();
        }

        // 批量查询订单信息
        List<AioveuSalesOrder> salesOrders = this.listByIds(salesOrderIds);

        // 转换为Map: key=订单ID, value=订单名称
        return salesOrders.stream()
                .collect(Collectors.toMap(
                        AioveuSalesOrder::getId,
                        AioveuSalesOrder::getOrderNo
                ));
    }

    /**
     * 获取所有订单列表（用于下拉选择框）
     *
     * @return 订单选项列表
     */
    @Override
    public List<SalesOrderOptionVO> getAllSalesOrderOptions() {
        // 查询所有订单
        List<AioveuSalesOrder> salesOrders = this.list();

        // 转换为选项对象
        List<SalesOrderOptionVO>  salesOrderVO  = salesOrders.stream()
                .map(salesOrder -> new SalesOrderOptionVO(salesOrder.getId(), salesOrder.getOrderNo()))
                .collect(Collectors.toList());

        return salesOrderVO;
    }


    /**
     * 批量设置名称到VO对象，将视图对象的员工id,转换为员工姓名
     */
    private void setSalesRepNames(List<AioveuSalesOrderVO> salesOrderVOS) {
        EmployeeNameSetter.setEmployeeNames(
                salesOrderVOS,
                AioveuSalesOrderVO::getSalesRepId, // 获取ID
                AioveuSalesOrderVO::setSalesRepName, // 设置姓名
                aioveuEmployeeService
        );
    }

    /**
     * 批量设置名称到VO对象，将视图对象的员工id,转换为员工姓名
     */
    private void setOperatorNames(List<AioveuSalesOrderVO> salesOrderVOS) {
        EmployeeNameSetter.setEmployeeNames(
                salesOrderVOS,
                AioveuSalesOrderVO::getOperatorId, // 获取ID
                AioveuSalesOrderVO::setOperatorName, // 设置姓名
                aioveuEmployeeService
        );
    }

    /**
     * 批量设置名称到VO对象，将视图对象的客户id,转换为客户姓名
     */
    private void setCustomerNames(List<AioveuSalesOrderVO> salesOrderVOS) {
        CustomerNameSetter.setCustomerNames(
                salesOrderVOS,
                AioveuSalesOrderVO::getCustomerId, // 获取ID
                AioveuSalesOrderVO::setCustomerName, // 设置姓名
                aioveuCustomerService
        );
    }

    /**
     * 批量设置名称到VO对象，将视图对象的联系人id,转换为联系人姓名
     */
    private void setContactNames(List<AioveuSalesOrderVO> salesOrderVOS) {
        ContactNameSetter.setContactNames(
                salesOrderVOS,
                AioveuSalesOrderVO::getContactId, // 获取ID
                AioveuSalesOrderVO::setContactName, // 设置姓名
                aioveuContactService
        );
    }

}
