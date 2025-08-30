package com.aioveu.boot.aioveuSalesOrder.service;

import com.aioveu.boot.aioveuSalesOrder.model.entity.AioveuSalesOrder;
import com.aioveu.boot.aioveuSalesOrder.model.form.AioveuSalesOrderForm;
import com.aioveu.boot.aioveuSalesOrder.model.query.AioveuSalesOrderQuery;
import com.aioveu.boot.aioveuSalesOrder.model.vo.AioveuSalesOrderVO;
import com.aioveu.boot.aioveuSalesOrder.model.vo.SalesOrderOptionVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 销售订单服务类
 *
 * @author 可我不敌可爱
 * @since 2025-08-22 00:27
 */
public interface AioveuSalesOrderService extends IService<AioveuSalesOrder> {


    Long getIdByName(String name);
    /**
     *销售订单分页列表
     *
     * @return {@link IPage<AioveuSalesOrderVO>} 销售订单分页列表
     */
    IPage<AioveuSalesOrderVO> getAioveuSalesOrderPage(AioveuSalesOrderQuery queryParams);

    /**
     * 获取销售订单表单数据
     *
     * @param id 销售订单ID
     * @return 销售订单表单数据
     */
     AioveuSalesOrderForm getAioveuSalesOrderFormData(Long id);

    /**
     * 新增销售订单
     *
     * @param formData 销售订单表单对象
     * @return 是否新增成功
     */
    boolean saveAioveuSalesOrder(AioveuSalesOrderForm formData);

    /**
     * 修改销售订单
     *
     * @param id   销售订单ID
     * @param formData 销售订单表单对象
     * @return 是否修改成功
     */
    boolean updateAioveuSalesOrder(Long id, AioveuSalesOrderForm formData);

    /**
     * 删除销售订单
     *
     * @param ids 销售订单ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    boolean deleteAioveuSalesOrders(String ids);

    /**
     * 批量获取订单信息（新增方法）
     */
    Map<Long, String> getSalesOrderMapByIds(List<Long> salesOrderIds);


    /**
     * 获取所有订单列表（用于下拉选择框）
     *
     * @return 订单选项列表
     */
    List<SalesOrderOptionVO> getAllSalesOrderOptions();

}
