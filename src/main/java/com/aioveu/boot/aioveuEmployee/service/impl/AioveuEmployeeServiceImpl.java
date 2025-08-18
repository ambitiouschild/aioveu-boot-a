package com.aioveu.boot.aioveuEmployee.service.impl;

import com.aliyun.oss.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aioveu.boot.aioveuEmployee.mapper.AioveuEmployeeMapper;
import com.aioveu.boot.aioveuEmployee.service.AioveuEmployeeService;
import com.aioveu.boot.aioveuEmployee.model.entity.AioveuEmployee;
import com.aioveu.boot.aioveuEmployee.model.form.AioveuEmployeeForm;
import com.aioveu.boot.aioveuEmployee.model.query.AioveuEmployeeQuery;
import com.aioveu.boot.aioveuEmployee.model.vo.AioveuEmployeeVO;
import com.aioveu.boot.aioveuEmployee.converter.AioveuEmployeeConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
/**
 * 员工信息服务实现类
 *
 * @author 可我不敌可爱
 * @since 2025-08-18 17:29
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AioveuEmployeeServiceImpl extends ServiceImpl<AioveuEmployeeMapper, AioveuEmployee> implements AioveuEmployeeService {

    private final AioveuEmployeeConverter aioveuEmployeeConverter;

    /**
    * 获取员工信息分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<AioveuEmployeeVO>} 员工信息分页列表
    */
    @Override
    public IPage<AioveuEmployeeVO> getAioveuEmployeePage(AioveuEmployeeQuery queryParams) {
        Page<AioveuEmployeeVO> pageVO = this.baseMapper.getAioveuEmployeePage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );
        return pageVO;
    }
    
    /**
     * 获取员工信息表单数据
     *
     * @param id 员工信息ID
     * @return 员工信息表单数据
     */
    @Override
    public AioveuEmployeeForm getAioveuEmployeeFormData(Long id) {
        AioveuEmployee entity = this.getById(id);
        return aioveuEmployeeConverter.toForm(entity);
    }
    
    /**
     * 新增员工信息
     *
     * @param formData 员工信息表单对象
     * @return 是否新增成功
     */
    @Override
    public boolean saveAioveuEmployee(AioveuEmployeeForm formData) {
        // 1. 检查员工编号是否唯一
        LambdaQueryWrapper<AioveuEmployee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AioveuEmployee::getEmpCode, formData.getEmpCode());

        if (count(wrapper) > 0) {
            throw new ServiceException("员工编号 " + formData.getEmpCode() + " 已存在");
        }

        // 2. 将表单数据转换为实体对象
        AioveuEmployee entity = aioveuEmployeeConverter.toEntity(formData);

        // 3. 执行插入
        return this.save(entity);
    }
    
    /**
     * 更新员工信息
     *
     * @param id   员工信息ID
     * @param formData 员工信息表单对象
     * @return 是否修改成功
     * 您在修改员工信息时没有更改员工编号，但后端仍然报错员工编号重复。
     * 这通常是因为后端在更新时没有正确判断员工编号是否被修改，即使没有修改也执行了唯一性检查
     */
    @Override
    public boolean updateAioveuEmployee(Long id,AioveuEmployeeForm formData) {
        //添加员工编号唯一性检查逻辑
        log.info("开始更新员工: ID={}", id);

        // 1. 获取原始员工信息
        AioveuEmployee original = getById(id);
        if (original == null) {
            log.error("员工不存在: ID={}", id);
            throw new ServiceException("员工不存在");
        }
        // 2. 检查员工编号是否被修改
        String originalEmpCode = original.getEmpCode();
        String newEmpCode = formData.getEmpCode();
        log.info("原始员工编号: {}, 新员工编号: {}", originalEmpCode, newEmpCode);


        // 只有当员工编号被修改时才检查唯一性
        if (!originalEmpCode.equals(newEmpCode)) {
            log.info("员工编号被修改，检查唯一性");
            // 3. 检查新员工编号是否唯一（排除当前员工）
            LambdaQueryWrapper<AioveuEmployee> wrapper = new LambdaQueryWrapper<>();
            //创建一个LambdaQueryWrapper对象，用于构建查询条件。LambdaQueryWrapper是MyBatis-Plus提供的链式条件构造器，支持Lambda表达式，避免字段名的硬编码。
            wrapper.eq(AioveuEmployee::getEmpCode, newEmpCode)
                    //eq方法表示等值查询，即查询emp_code字段等于newEmpCode值的记录。
                    //AioveuEmployee::getEmpCode是Lambda表达式，指定要查询的字段（即emp_code列）。
                    //newEmpCode是要匹配的值（即新员工编号）。
                    .ne(AioveuEmployee::getEmployeeId, id); // 排除当前员工
            //ne方法表示不等值查询，即查询employee_id字段不等于id的记录。
            //AioveuEmployee::getEmployeeId指定要查询的字段（即employee_id列）。
            //id是当前员工的ID（即要排除的ID）

            //查找员工编号（emp_code）等于新员工编号（newEmpCode）且员工ID（employee_id）不等于当前员工ID（id）的记录。

            long count = count(wrapper);
            log.info("员工编号 {} 的重复记录数: {}", newEmpCode, count);

            if (count(wrapper) > 0) {
                throw new ServiceException("员工编号 " + newEmpCode + " 已存在");
            }
        }else {
            log.info("员工编号未修改，跳过唯一性检查");
        }

        // 4. 将表单数据转换为实体对象
        AioveuEmployee entity = aioveuEmployeeConverter.toEntity(formData);
        entity.setEmployeeId(id); // 设置员工ID
        log.info("转换后的实体对象: {}", entity);

        // 5. 复制未修改的字段（如创建时间等）
        entity.setCreateTime(original.getCreateTime());

        // 6. 执行更新
        return this.updateById(entity);
    }
    
    /**
     * 删除员工信息
     *
     * @param ids 员工信息ID，多个以英文逗号(,)分割
     * @return 是否删除成功
     */
    @Override
    public boolean deleteAioveuEmployees(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的员工信息数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

}
