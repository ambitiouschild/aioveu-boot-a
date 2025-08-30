package com.aioveu.boot.aioveuSalesOrderDetail.service.impl;

import cn.idev.excel.util.StringUtils;
import com.aioveu.boot.aioveuDepartment.model.entity.AioveuDepartment;
import com.aioveu.boot.aioveuEmployee.service.impl.NameValidator;
import com.aioveu.boot.aioveuMaterial.model.entity.AioveuMaterial;
import com.aioveu.boot.aioveuMaterial.model.vo.AioveuMaterialVO;
import com.aioveu.boot.aioveuMaterial.service.AioveuMaterialService;
import com.aioveu.boot.aioveuMaterial.service.impl.MaterialNameSetter;
import com.aioveu.boot.aioveuPerformance.model.vo.AioveuPerformanceVO;
import com.aioveu.boot.aioveuSalesOrder.model.entity.AioveuSalesOrder;
import com.aioveu.boot.aioveuSalesOrder.service.AioveuSalesOrderService;
import com.aioveu.boot.aioveuSalesOrder.service.impl.SalesOrderNameSetter;
import com.aioveu.boot.aioveuWarehouse.model.entity.AioveuWarehouse;
import com.aioveu.boot.aioveuWarehouse.model.form.AioveuWarehouseForm;
import com.aioveu.boot.aioveuWarehouse.service.AioveuWarehouseService;
import com.aioveu.boot.aioveuWarehouse.service.impl.WarehouseNameSetter;
import com.aliyun.oss.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.math.BigDecimal;
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

    @Autowired
    private AioveuWarehouseService aioveuWarehouseService;

    @Autowired
    private AioveuMaterialService aioveuMaterialService;

    @Autowired
    private AioveuSalesOrderService aioveuSalesOrderService;

    /**
    * 获取订单明细分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AioveuSalesOrderDetailVO>} 订单明细分页列表
    */
    @Override
    public IPage<AioveuSalesOrderDetailVO> getAioveuSalesOrderDetailPage(AioveuSalesOrderDetailQuery queryParams) {

        // 处理订单名称映射
        if (StringUtils.isNotBlank(queryParams.getOrderName())) {
            Long salesOrderId = aioveuSalesOrderService.getIdByName(queryParams.getOrderName());
            queryParams.setOrderId(salesOrderId);
        }

        // 处理物资名称映射
        if (StringUtils.isNotBlank(queryParams.getMaterialName())) {
            Long materialId = aioveuMaterialService.getIdByName(queryParams.getMaterialName());
            queryParams.setMaterialId(materialId);
        }

        // 处理仓库名称映射
        if (StringUtils.isNotBlank(queryParams.getWarehouseName())) {
            Long warehouseId = aioveuWarehouseService.getIdByName(queryParams.getWarehouseName());
            queryParams.setWarehouseId(warehouseId);
        }
        Page<AioveuSalesOrderDetailVO> pageVO = this.baseMapper.getAioveuSalesOrderDetailPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );

        setWarehouseNames(pageVO.getRecords());


        setMaterialNames(pageVO.getRecords());

        setSalesOrderNames(pageVO.getRecords());

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
        // 1. 根据ID获取实体信息
        AioveuSalesOrderDetail entity = this.getById(id);
        if (entity == null) {
            throw new ServiceException("不存在");
        }
        // 2. 将实体转换为表单对象
        AioveuSalesOrderDetailForm form = aioveuSalesOrderDetailConverter.toForm(entity);

        // 3. 处理映射信息（如果存在）
        if (entity.getOrderId() != null) {
            LambdaQueryWrapper<AioveuSalesOrder> Wrapper = new LambdaQueryWrapper<>();
            Wrapper.eq(AioveuSalesOrder::getId, entity.getOrderId())
                    .select(AioveuSalesOrder::getOrderNo); // 只选择需要的字段

            AioveuSalesOrder salesOrder = aioveuSalesOrderService.getOne(Wrapper);

            if (salesOrder != null) {
                form.setOrderName(salesOrder.getOrderNo());
            }
        }

        if (entity.getMaterialId() != null) {
            LambdaQueryWrapper<AioveuMaterial> Wrapper = new LambdaQueryWrapper<>();
            Wrapper.eq(AioveuMaterial::getId, entity.getMaterialId())
                    .select(AioveuMaterial::getName); // 只选择需要的字段

            AioveuMaterial material = aioveuMaterialService.getOne(Wrapper);

            if (material != null) {
                form.setMaterialName(material.getName());
            }
        }

        if (entity.getWarehouseId() != null) {
            LambdaQueryWrapper<AioveuWarehouse> Wrapper = new LambdaQueryWrapper<>();
            Wrapper.eq(AioveuWarehouse::getId, entity.getWarehouseId())
                    .select(AioveuWarehouse::getName); // 只选择需要的字段

            AioveuWarehouse warehouse = aioveuWarehouseService.getOne(Wrapper);

            if (warehouse != null) {
                form.setWarehouseName(warehouse.getName());
            }
        }


        return form;
    }
    
    /**
     * 新增订单明细
     *
     * @param formData 订单明细表单对象
     * @return 是否新增成功
     */
    @Override
    public boolean saveAioveuSalesOrderDetail(AioveuSalesOrderDetailForm formData) {

        // 字段1：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuSalesOrderDetailForm::getOrderName,
                AioveuSalesOrder::getOrderNo,
                AioveuSalesOrderDetailForm::setOrderId,
                AioveuSalesOrder::getId,
                aioveuSalesOrderService,
                "订单： "
        );

        // 字段2：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuSalesOrderDetailForm::getMaterialName,
                AioveuMaterial::getName,
                AioveuSalesOrderDetailForm::setMaterialId,
                AioveuMaterial::getId,
                aioveuMaterialService,
                "物资： "
        );

        // 字段3：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuSalesOrderDetailForm::getWarehouseName,
                AioveuWarehouse::getName,
                AioveuSalesOrderDetailForm::setWarehouseId,
                AioveuWarehouse::getId,
                aioveuWarehouseService,
                "仓库： "
        );
        // 将表单数据转换为实体（计算字段会在getter中自动计算）
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

        // 字段1：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuSalesOrderDetailForm::getOrderName,
                AioveuSalesOrder::getOrderNo,
                AioveuSalesOrderDetailForm::setOrderId,
                AioveuSalesOrder::getId,
                aioveuSalesOrderService,
                "订单： "
        );

        // 字段2：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuSalesOrderDetailForm::getMaterialName,
                AioveuMaterial::getName,
                AioveuSalesOrderDetailForm::setMaterialId,
                AioveuMaterial::getId,
                aioveuMaterialService,
                "物资： "
        );

        // 字段3：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuSalesOrderDetailForm::getWarehouseName,
                AioveuWarehouse::getName,
                AioveuSalesOrderDetailForm::setWarehouseId,
                AioveuWarehouse::getId,
                aioveuWarehouseService,
                "仓库： "
        );

        // 2. 将表单数据转换为实体对象（计算字段会在getter中自动计算）
        AioveuSalesOrderDetail entity = aioveuSalesOrderDetailConverter.toEntity(formData);

        // 自动计算并获取值
//        BigDecimal subtotal = entity.getSubtotal(); // 自动计算小计
//        BigDecimal taxAmount = entity.getTaxAmount(); // 自动计算税额
//        BigDecimal totalPrice = entity.getTotalPrice(); // 自动计算总金额

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




    /**
     * 批量设置名称到VO对象，将视图对象的仓库id,转换为仓库姓名
     */
    private void setWarehouseNames(List<AioveuSalesOrderDetailVO> salesOrderDetailVOS) {
        WarehouseNameSetter.setWarehouseNames(
                salesOrderDetailVOS ,
                AioveuSalesOrderDetailVO::getWarehouseId, // 获取仓库ID
                AioveuSalesOrderDetailVO::setWarehouseName, // 设置仓库姓名
                aioveuWarehouseService
        );
    }




    /**
     * 批量设置名称到VO对象，将视图对象的物资id,转换为物资姓名
     */
    private void setMaterialNames(List<AioveuSalesOrderDetailVO> salesOrderDetailVOS) {
        MaterialNameSetter.setMaterialNames(
                salesOrderDetailVOS,
                AioveuSalesOrderDetailVO::getMaterialId, // 获取ID
                AioveuSalesOrderDetailVO::setMaterialName, // 设置姓名
                aioveuMaterialService
        );
    }

    /**
     * 批量设置名称到VO对象，将视图对象的订单id,转换为订单姓名
     */
    private void setSalesOrderNames(List<AioveuSalesOrderDetailVO> salesOrderDetailVOS) {
        SalesOrderNameSetter.setSalesOrderNames(
                salesOrderDetailVOS,
                AioveuSalesOrderDetailVO::getOrderId, // 获取ID
                AioveuSalesOrderDetailVO::setSalesOrderNo, // 设置姓名
                aioveuSalesOrderService
        );
    }
}
