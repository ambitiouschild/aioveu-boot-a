package com.aioveu.boot.aioveuTransaction.service.impl;

import com.aliyun.oss.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aioveu.boot.aioveuTransaction.mapper.AioveuTransactionMapper;
import com.aioveu.boot.aioveuTransaction.service.AioveuTransactionService;
import com.aioveu.boot.aioveuTransaction.model.entity.AioveuTransaction;
import com.aioveu.boot.aioveuTransaction.model.form.AioveuTransactionForm;
import com.aioveu.boot.aioveuTransaction.model.query.AioveuTransactionQuery;
import com.aioveu.boot.aioveuTransaction.model.vo.AioveuTransactionVO;
import com.aioveu.boot.aioveuTransaction.converter.AioveuTransactionConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
/**
 * 客户交易记录服务实现类
 *
 * @author 可我不敌可爱
 * @since 2025-08-22 00:16
 */



@Slf4j
@Service
@RequiredArgsConstructor
public class AioveuTransactionServiceImpl extends ServiceImpl<AioveuTransactionMapper, AioveuTransaction> implements AioveuTransactionService {

    private final AioveuTransactionConverter aioveuTransactionConverter;

    /**
    * 获取客户交易记录分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AioveuTransactionVO>} 客户交易记录分页列表
    */
    @Override
    public IPage<AioveuTransactionVO> getAioveuTransactionPage(AioveuTransactionQuery queryParams) {
        Page<AioveuTransactionVO> pageVO = this.baseMapper.getAioveuTransactionPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );
        return pageVO;
    }
    
    /**
     * 获取客户交易记录表单数据
     *
     * @param id 客户交易记录ID
     * @return 客户交易记录表单数据
     */
    @Override
    public AioveuTransactionForm getAioveuTransactionFormData(Long id) {
        AioveuTransaction entity = this.getById(id);
        return aioveuTransactionConverter.toForm(entity);
    }
    
    /**
     * 新增客户交易记录
     *
     * @param formData 客户交易记录表单对象
     * @return 是否新增成功
     */
    @Override
    public boolean saveAioveuTransaction(AioveuTransactionForm formData) {
        AioveuTransaction entity = aioveuTransactionConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新客户交易记录
     *
     * @param id   客户交易记录ID
     * @param formData 客户交易记录表单对象
     * @return 是否修改成功
     */
    @Override
    public boolean updateAioveuTransaction(Long id,AioveuTransactionForm formData) {

        // 1. 验证ID对应的记录是否存在
        AioveuTransaction original = getById(id);
        if (original == null) {
            log.error("记录不存在: ID={}", id);
            throw new ServiceException("记录不存在");

        }

        // 2. 将表单数据转换为实体对象
        AioveuTransaction entity = aioveuTransactionConverter.toEntity(formData);

        // 3. 设置ID
        entity.setId(id);

        // 4. 复制未修改的字段,保留审计字段(创建时间不变，更新时间用当前时间)
        entity.setCreateTime(original.getCreateTime());

        // 5. 执行更新
        return this.updateById(entity);
    }
    
    /**
     * 删除客户交易记录
     *
     * @param ids 客户交易记录ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    @Override
    public boolean deleteAioveuTransactions(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的客户交易记录数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

}
