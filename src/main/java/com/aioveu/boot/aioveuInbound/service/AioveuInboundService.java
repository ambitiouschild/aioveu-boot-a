package com.aioveu.boot.aioveuInbound.service;

import com.aioveu.boot.aioveuInbound.model.entity.AioveuInbound;
import com.aioveu.boot.aioveuInbound.model.form.AioveuInboundForm;
import com.aioveu.boot.aioveuInbound.model.query.AioveuInboundQuery;
import com.aioveu.boot.aioveuInbound.model.vo.AioveuInboundVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 入库信息服务类
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 22:18
 */
public interface AioveuInboundService extends IService<AioveuInbound> {

    /**
     *入库信息分页列表
     *
     * @return {@link IPage<AioveuInboundVO>} 入库信息分页列表
     */
    IPage<AioveuInboundVO> getAioveuInboundPage(AioveuInboundQuery queryParams);

    /**
     * 获取入库信息表单数据
     *
     * @param id 入库信息ID
     * @return 入库信息表单数据
     */
     AioveuInboundForm getAioveuInboundFormData(Long id);

    /**
     * 新增入库信息
     *
     * @param formData 入库信息表单对象
     * @return 是否新增成功
     */
    boolean saveAioveuInbound(AioveuInboundForm formData);

    /**
     * 修改入库信息
     *
     * @param id   入库信息ID
     * @param formData 入库信息表单对象
     * @return 是否修改成功
     */
    boolean updateAioveuInbound(Long id, AioveuInboundForm formData);

    /**
     * 删除入库信息
     *
     * @param ids 入库信息ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    boolean deleteAioveuInbounds(String ids);

}
