package com.aioveu.boot.aioveuSalesOrderDetail.service;

import com.aioveu.boot.aioveuSalesOrderDetail.model.entity.AioveuSalesOrderDetail;
import com.aioveu.boot.aioveuSalesOrderDetail.model.form.AioveuSalesOrderDetailForm;
import com.aioveu.boot.aioveuSalesOrderDetail.model.query.AioveuSalesOrderDetailQuery;
import com.aioveu.boot.aioveuSalesOrderDetail.model.vo.AioveuSalesOrderDetailVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 订单明细服务类
 *
 * @author 可我不敌可爱
 * @since 2025-08-22 00:42
 */
public interface AioveuSalesOrderDetailService extends IService<AioveuSalesOrderDetail> {

    /**
     *订单明细分页列表
     *
     * @return {@link IPage<AioveuSalesOrderDetailVO>} 订单明细分页列表
     */
    IPage<AioveuSalesOrderDetailVO> getAioveuSalesOrderDetailPage(AioveuSalesOrderDetailQuery queryParams);

    /**
     * 获取订单明细表单数据
     *
     * @param id 订单明细ID
     * @return 订单明细表单数据
     */
     AioveuSalesOrderDetailForm getAioveuSalesOrderDetailFormData(Long id);

    /**
     * 新增订单明细
     *
     * @param formData 订单明细表单对象
     * @return 是否新增成功
     */
    boolean saveAioveuSalesOrderDetail(AioveuSalesOrderDetailForm formData);

    /**
     * 修改订单明细
     *
     * @param id   订单明细ID
     * @param formData 订单明细表单对象
     * @return 是否修改成功
     */
    boolean updateAioveuSalesOrderDetail(Long id, AioveuSalesOrderDetailForm formData);

    /**
     * 删除订单明细
     *
     * @param ids 订单明细ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    boolean deleteAioveuSalesOrderDetails(String ids);

}
