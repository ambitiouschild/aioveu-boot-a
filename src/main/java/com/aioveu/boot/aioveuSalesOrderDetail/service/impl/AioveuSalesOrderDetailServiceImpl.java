package com.aioveu.boot.aioveuSalesOrderDetail.service.impl;

import com.aliyun.oss.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aioveu.boot.aioveuSalesOrderDetail.mapper.AioveuSalesOrderDetailMapper;
import com.aioveu.boot.aioveuSalesOrderDetail.service.AioveuSalesOrderDetailService;
import com.aioveu.boot.aioveuSalesOrderDetail.model.entity.AioveuSalesOrderDetail;
import com.aioveu.boot.aioveuSalesOrderDetail.model.form.AioveuSalesOrderDetailForm;
import com.aioveu.boot.aioveuSalesOrderDetail.model.query.AioveuSalesOrderDetailQuery;
import com.aioveu.boot.aioveuSalesOrderDetail.model.vo.AioveuSalesOrderDetailVO;
import com.aioveu.boot.aioveuSalesOrderDetail.converter.AioveuSalesOrderDetailConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
/**
 * 订单明细服务实现类
 *
 * @author 可我不敌可爱
 * @since 2025-08-22 00:42
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AioveuSalesOrderDetailServiceImpl extends ServiceImpl<AioveuSalesOrderDetailMapper, AioveuSalesOrderDetail> implements AioveuSalesOrderDetailService {

    private final AioveuSalesOrderDetailConverter aioveuSalesOrderDetailConverter;

    /**
    * 获取订单明细分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AioveuSalesOrderDetailVO>} 订单明细分页列表
    */
    @Override
    public IPage<AioveuSalesOrderDetailVO> getAioveuSalesOrderDetailPage(AioveuSalesOrderDetailQuery queryParams) {
        Page<AioveuSalesOrderDetailVO> pageVO = this.baseMapper.getAioveuSalesOrderDetailPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );
        return pageVO;
    }
    
    /**
     * 获取订单明细表单数据
     *
     * @param id 订单明细ID
     * @return 订单明细表单数据
     */
    @Override
    public AioveuSalesOrderDetailForm getAioveuSalesOrderDetailFormData(Long id) {
        AioveuSalesOrderDetail entity = this.getById(id);
        return aioveuSalesOrderDetailConverter.toForm(entity);
    }
    
    /**
     * 新增订单明细
     *
     * @param formData 订单明细表单对象
     * @return 是否新增成功
     */
    @Override
    public boolean saveAioveuSalesOrderDetail(AioveuSalesOrderDetailForm formData) {
        AioveuSalesOrderDetail entity = aioveuSalesOrderDetailConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新订单明细
     *
     * @param id   订单明细ID
     * @param formData 订单明细表单对象
     * @return 是否修改成功
     */
    @Override
    public boolean updateAioveuSalesOrderDetail(Long id,AioveuSalesOrderDetailForm formData) {

        // 1. 验证ID对应的记录是否存在
        AioveuSalesOrderDetail original = getById(id);
        if (original == null) {
            log.error("记录不存在: ID={}", id);
            throw new ServiceException("记录不存在");

        }

        // 2. 将表单数据转换为实体对象
        AioveuSalesOrderDetail entity = aioveuSalesOrderDetailConverter.toEntity(formData);

        // 3. 设置ID
        entity.setId(id);

        // 4. 复制未修改的字段,保留审计字段(创建时间不变，更新时间用当前时间)
        entity.setCreateTime(original.getCreateTime());

        // 5. 执行更新
        return this.updateById(entity);
    }

    
    /**
     * 删除订单明细
     *
     * @param ids 订单明细ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    @Override
    public boolean deleteAioveuSalesOrderDetails(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的订单明细数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

}
