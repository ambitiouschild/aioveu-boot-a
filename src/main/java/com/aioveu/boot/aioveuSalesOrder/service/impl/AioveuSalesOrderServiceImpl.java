package com.aioveu.boot.aioveuSalesOrder.service.impl;

import com.aioveu.boot.aioveuEmployee.service.AioveuEmployeeService;
import com.aioveu.boot.aioveuEmployee.service.impl.EmployeeNameSetter;
import com.aioveu.boot.aioveuPerformance.model.vo.AioveuPerformanceVO;
import com.aliyun.oss.ServiceException;
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


    /**
    * 获取销售订单分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AioveuSalesOrderVO>} 销售订单分页列表
    */
    @Override
    public IPage<AioveuSalesOrderVO> getAioveuSalesOrderPage(AioveuSalesOrderQuery queryParams) {
        Page<AioveuSalesOrderVO> pageVO = this.baseMapper.getAioveuSalesOrderPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );

        setSalesRepNames(pageVO.getRecords());

        setOperatorNames(pageVO.getRecords());

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
        AioveuSalesOrder entity = this.getById(id);
        return aioveuSalesOrderConverter.toForm(entity);
    }
    
    /**
     * 新增销售订单
     *
     * @param formData 销售订单表单对象
     * @return 是否新增成功
     */
    @Override
    public boolean saveAioveuSalesOrder(AioveuSalesOrderForm formData) {
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

}
