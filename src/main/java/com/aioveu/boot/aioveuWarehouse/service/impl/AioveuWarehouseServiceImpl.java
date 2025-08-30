package com.aioveu.boot.aioveuWarehouse.service.impl;

import cn.idev.excel.util.StringUtils;
import com.aioveu.boot.aioveuDepartment.mapper.AioveuDepartmentMapper;
import com.aioveu.boot.aioveuEmployee.model.entity.AioveuEmployee;
import com.aioveu.boot.aioveuEmployee.service.AioveuEmployeeService;
import com.aioveu.boot.aioveuEmployee.service.impl.EmployeeNameSetter;
import com.aioveu.boot.aioveuEmployee.service.impl.NameValidator;
import com.aioveu.boot.aioveuWarehouse.model.vo.WarehouseOptionVO;
import com.aliyun.oss.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aioveu.boot.aioveuWarehouse.mapper.AioveuWarehouseMapper;
import com.aioveu.boot.aioveuWarehouse.service.AioveuWarehouseService;
import com.aioveu.boot.aioveuWarehouse.model.entity.AioveuWarehouse;
import com.aioveu.boot.aioveuWarehouse.model.form.AioveuWarehouseForm;
import com.aioveu.boot.aioveuWarehouse.model.query.AioveuWarehouseQuery;
import com.aioveu.boot.aioveuWarehouse.model.vo.AioveuWarehouseVO;
import com.aioveu.boot.aioveuWarehouse.converter.AioveuWarehouseConverter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
/**
 * 仓库信息服务实现类
 *
 * @author 可我不敌可爱
 * @since 2025-08-21 21:42
 */


@Slf4j
@Service
@RequiredArgsConstructor
public class AioveuWarehouseServiceImpl extends ServiceImpl<AioveuWarehouseMapper, AioveuWarehouse> implements AioveuWarehouseService {

    private final AioveuWarehouseConverter aioveuWarehouseConverter;

    @Autowired
    private AioveuEmployeeService aioveuEmployeeService;


    @Autowired
    private AioveuWarehouseMapper aioveuWarehouseMapper;

    @Override
    public Long getIdByName(String name) {
        return aioveuWarehouseMapper.findIdByName(name);
    }


    /**
    * 获取仓库信息分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AioveuWarehouseVO>} 仓库信息分页列表
    */
    @Override
    public IPage<AioveuWarehouseVO> getAioveuWarehousePage(AioveuWarehouseQuery queryParams) {

        // 处理负责人名称映射
        if (StringUtils.isNotBlank(queryParams.getManagerName())) {
            Long employeeId = aioveuEmployeeService.getIdByName(queryParams.getManagerName());
            queryParams.setManagerId(employeeId);
        }



        Page<AioveuWarehouseVO> pageVO = this.baseMapper.getAioveuWarehousePage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );

        // 设置员工名称
        setEmployeeNames(pageVO.getRecords());

        return pageVO;
    }
    
    /**
     * 获取仓库信息表单数据
     *
     * @param id 仓库信息ID
     * @return 仓库信息表单数据
     */
    @Override
    public AioveuWarehouseForm getAioveuWarehouseFormData(Long id) {
        // 1. 根据ID获取实体信息
        AioveuWarehouse entity = this.getById(id);
        if (entity == null) {
            throw new ServiceException("不存在");
        }

        // 2. 将实体转换为表单对象
        AioveuWarehouseForm form = aioveuWarehouseConverter.toForm(entity);

        // 3. 处理映射信息（如果存在）
        if (entity.getManagerId() != null) {
            // 使用 MyBatis-Plus 的 LambdaQueryWrapper 查询信息
            // 创建一个 LambdaQueryWrapper 对象，用于构建查询条件
            // 泛型 <AioveuDepartment> 指定了查询的实体类型
            LambdaQueryWrapper<AioveuEmployee> Wrapper = new LambdaQueryWrapper<>();
            // 添加查询条件：部门ID等于指定值
            // AioveuDepartment::getDeptId 是方法引用，表示查询 dept_id 字段
            // entity.getDeptId() 是获取要查询的具体部门ID值
            Wrapper.eq(AioveuEmployee::getEmployeeId, entity.getManagerId())
                    // 指定只选择 dept_name 字段，而不是所有字段
                    // 这是一个性能优化，减少不必要的数据传输
                    .select(AioveuEmployee::getName); // 只选择需要的字段

            AioveuEmployee employee = aioveuEmployeeService.getOne(Wrapper);

            if (employee != null) {
                form.setManagerName(employee.getName());
            }
        }

        return form;
    }
    
    /**
     * 新增仓库信息
     *
     * @param formData 仓库信息表单对象
     * @return 是否新增成功
     */
    @Override
    public boolean saveAioveuWarehouse(AioveuWarehouseForm formData) {

        // 字段1：检查编号是否唯一（对于不依赖外键的字段，不可重复）
        NameValidator.validateEntityUnique(
                formData,
                AioveuWarehouseForm::getName,
                AioveuWarehouse::getName,
                null,
                this,
                "仓库名称"
        );

        // 字段2：检查编号是否唯一（对于不依赖外键的字段，不可重复）
        NameValidator.validateEntityUnique(
                formData,
                AioveuWarehouseForm::getCode,
                AioveuWarehouse::getCode,
                null,
                this,
                "仓库编码"
        );

        // 字段3：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuWarehouseForm::getManagerName,  // 获取经理姓名的方法
                AioveuEmployee::getName,  // 实体字段：员工姓名
//                (form, id) -> form.setManagerId(id), // 设置经理ID的方法  // 使用显式Lambda（推荐）
                AioveuWarehouseForm::setManagerId, // 直接使用方法引用
                AioveuEmployee::getEmployeeId, // 从员工实体获取ID的方法
                aioveuEmployeeService,  // 员工服务（不是this）
                "负责人： "  // 实体名称（用于错误消息）
        );


        AioveuWarehouse entity = aioveuWarehouseConverter.toEntity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新仓库信息
     *
     * @param id   仓库信息ID
     * @param formData 仓库信息表单对象
     * @return 是否修改成功
     */
    @Override
    public boolean updateAioveuWarehouse(Long id,AioveuWarehouseForm formData) {
        // 1. 验证ID对应的记录是否存在
        AioveuWarehouse original = getById(id);
        if (original == null) {
            log.error("记录不存在: ID={}", id);
            throw new ServiceException("记录不存在");

        }

        // 字段3：检查是否存在记录（对于必须依赖外键的字段,必须存在，可重复） //在相关字段加注解  @NotNull(message = "不存在"
        NameValidator.validateEntityExists(
                formData,
                AioveuWarehouseForm::getManagerName,  // 获取经理姓名的方法
                AioveuEmployee::getName,  // 实体字段：员工姓名
//                (form, id) -> form.setManagerId(id), // 设置经理ID的方法  // 使用显式Lambda（推荐）
                AioveuWarehouseForm::setManagerId, // 直接使用方法引用
                AioveuEmployee::getEmployeeId, // 从员工实体获取ID的方法
                aioveuEmployeeService,  // 员工服务（不是this）
                "负责人： "  // 实体名称（用于错误消息）
        );

        // 2. 将表单数据转换为实体对象
        AioveuWarehouse entity = aioveuWarehouseConverter.toEntity(formData);

        // 3. 设置ID
        entity.setId(id);

        // 4. 复制未修改的字段,保留审计字段(创建时间不变，更新时间用当前时间)
        entity.setCreateTime(original.getCreateTime());

        // 5. 执行更新
        return this.updateById(entity);
    }
    
    /**
     * 删除仓库信息
     *
     * @param ids 仓库信息ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    @Override
    public boolean deleteAioveuWarehouses(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的仓库信息数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

    /**
     * 批量设置名称到VO对象，将AioveuPerformanceVO绩效表视图对象的员工id,转换为员工姓名
     */
    private void setEmployeeNames(List<AioveuWarehouseVO> warehouseVOS) {
        EmployeeNameSetter.setEmployeeNames(
                warehouseVOS,
                AioveuWarehouseVO::getManagerId, // 获取员工ID
                AioveuWarehouseVO::setManagerName, // 设置员工姓名
                aioveuEmployeeService
        );
    }

    /**
     * 批量获取仓库信息（新增方法）
     */
    @Override
    public Map<Long, String> getWarehouseMapByIds(List<Long> warehouseIds) {
        if (warehouseIds == null || warehouseIds.isEmpty()) {
            return Map.of();
        }

        // 批量查询仓库信息
        List<AioveuWarehouse> warehouses = this.listByIds(warehouseIds);

        // 转换为Map: key=仓库ID, value=仓库名称
        return warehouses.stream()
                .collect(Collectors.toMap(
                        AioveuWarehouse::getId,
                        AioveuWarehouse::getName
                ));
    }

    /**
     * 获取所有仓库列表（用于下拉选择框）
     *
     * @return 仓库选项列表
     */
    @Override
    public List<WarehouseOptionVO> getAllWarehouseOptions() {
        // 查询所有仓库
        List<AioveuWarehouse> warehouses = this.list();

        // 转换为选项对象
        List<WarehouseOptionVO>  warehouseVO  = warehouses.stream()
                .map(warehouse -> new WarehouseOptionVO(warehouse.getId(), warehouse.getName()))
                .collect(Collectors.toList());

        return warehouseVO;
    }

}
