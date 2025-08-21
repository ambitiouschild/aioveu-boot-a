package com.aioveu.boot.aioveuOutbound.service;

import com.aioveu.boot.aioveuOutbound.model.entity.AioveuOutbound;
import com.aioveu.boot.aioveuOutbound.model.form.AioveuOutboundForm;
import com.aioveu.boot.aioveuOutbound.model.query.AioveuOutboundQuery;
import com.aioveu.boot.aioveuOutbound.model.vo.AioveuOutboundVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 出库记录服务类
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 23:26
 */
public interface AioveuOutboundService extends IService<AioveuOutbound> {

    /**
     *出库记录分页列表
     *
     * @return {@link IPage<AioveuOutboundVO>} 出库记录分页列表
     */
    IPage<AioveuOutboundVO> getAioveuOutboundPage(AioveuOutboundQuery queryParams);

    /**
     * 获取出库记录表单数据
     *
     * @param id 出库记录ID
     * @return 出库记录表单数据
     */
     AioveuOutboundForm getAioveuOutboundFormData(Long id);

    /**
     * 新增出库记录
     *
     * @param formData 出库记录表单对象
     * @return 是否新增成功
     */
    boolean saveAioveuOutbound(AioveuOutboundForm formData);

    /**
     * 修改出库记录
     *
     * @param id   出库记录ID
     * @param formData 出库记录表单对象
     * @return 是否修改成功
     */
    boolean updateAioveuOutbound(Long id, AioveuOutboundForm formData);

    /**
     * 删除出库记录
     *
     * @param ids 出库记录ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    boolean deleteAioveuOutbounds(String ids);

}
