

-- ----------------------------
-- 1. 创建数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS aioveu_boot CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;



-- 2. 创建表 && 数据初始化
-- ----------------------------
use aioveu_boot;
-- 物资管理模块(管理企业的物资（如办公用品、生产原料等），包括物资的入库、出库、库存量监控等。) - 物资表
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_material`;

CREATE TABLE `aioveu_material` (
                                   `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '物资ID',
                                   name VARCHAR(100) NOT NULL COMMENT '物资名称',
                                   category_id BIGINT UNSIGNED NOT NULL COMMENT '分类ID',
                                   unit VARCHAR(20) NOT NULL DEFAULT '个' COMMENT '单位',
                                   spec VARCHAR(50) COMMENT '规格型号',
                                   purchase_price DECIMAL(12,2) UNSIGNED NOT NULL COMMENT '采购单价（正数）',
                                   `supplier_id` INT UNSIGNED COMMENT '供应商ID',
                                   `min_stock` INT UNSIGNED DEFAULT 0 COMMENT '最低库存量（非负整数）',
                                   `max_stock` INT UNSIGNED DEFAULT 100 COMMENT '最高库存量（非负整数）',
                                   `is_active` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '启用状态：0-禁用，1-启用',
                                   `barcode` VARCHAR(20) UNIQUE COMMENT '商品条码（唯一标识）',  -- 唯一约束确保条码唯一
                                   `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',


                                   PRIMARY KEY (`id`),

    -- 外键约束（确保分类存在）
                                   CONSTRAINT `fk_material_category`
                                       FOREIGN KEY (`category_id`)
                                           REFERENCES `aioveu_category`(`id`)
                                           ON DELETE RESTRICT
                                           ON UPDATE CASCADE,

    -- 检查约束（确保库存范围合理）
                                   CONSTRAINT `chk_stock_range`
                                       CHECK (`min_stock` <= `max_stock`),  -- 检查库存范围合理性

    -- 索引优化
                                   INDEX `idx_category` (`category_id`),  -- 加速分类查询
                                   INDEX `idx_supplier` (`supplier_id`),  -- 加速供应商查询
                                   INDEX `idx_barcode` (`barcode`),   -- 加速条码查询
                                   INDEX `idx_active` (`is_active`)   -- 加速状态过滤



) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='物资表';

-- ALTER TABLE aioveu_material ADD COLUMN barcode VARCHAR(20) COMMENT '商品条码';

-- 插入示例数据
INSERT INTO `aioveu_material` (
    name, category_id, unit, spec, purchase_price,
    supplier_id, min_stock, max_stock, barcode
) VALUES
      -- 办公用品类
      ('A4复印纸', 1, '包', '80g/500张', 25.99, 2001, 50, 200, '6923456789012'),
      ('黑色签字笔', 1, '盒', '0.5mm/12支装', 15.50, 2001, 100, 500, '6934567890123'),
      ('文件夹', 1, '个', 'A4尺寸/蓝色', 3.80, 2001, 200, 1000, '6945678901234'),
      ('便签纸', 1, '本', '75x75mm/100页', 5.20, 2001, 150, 800, '6956789012345'),

      -- 电子产品类
      ('笔记本电脑', 2, '台', '15.6英寸/i7/16GB', 6500.00, 2002, 5, 20, '6967890123456'),
      ('无线鼠标', 2, '个', '蓝牙/静音', 89.00, 2002, 20, 100, '6978901234567'),
      ('USB闪存盘', 2, '个', '64GB/USB3.0', 45.00, 2002, 50, 300, '6989012345678'),
      ('显示器', 2, '台', '27英寸/4K', 1999.00, 2002, 3, 15, '6990123456789'),

      -- 生产原料类
      ('钢材', 3, '吨', 'Q235/Φ20mm', 4200.00, 2003, 10, 50, '7001234567890'),
      ('塑料颗粒', 3, '千克', 'PP/白色', 12.50, 2003, 500, 2000, '7012345678901'),
      ('电子元件', 3, '盒', 'SMD/1000pcs', 85.00, 2003, 100, 500, '7023456789012'),
      ('润滑油', 3, '桶', '20L/工业级', 280.00, 2003, 5, 20, '7034567890123'),

      -- 清洁用品类
      ('洗手液', 11, '瓶', '500ml/抑菌', 18.00, 2004, 30, 150, '7045678901234'),
      ('消毒湿巾', 11, '包', '80片/桶装', 25.00, 2004, 50, 300, '7056789012345'),
      ('垃圾袋', 11, '卷', '大号/50个', 15.00, 2004, 100, 500, '7067890123456'),
      ('空气清新剂', 11, '罐', '300ml/柠檬味', 22.00, 2004, 20, 100, '7078901234567'),

      -- 工具设备类
      ('电动螺丝刀', 11, '把', '12V/锂电', 199.00, 2005, 10, 50, '7089012345678'),
      ('万用表', 11, '台', '数字/自动量程', 150.00, 2005, 5, 30, '7090123456789'),
      ('安全帽', 11, '顶', 'ABS材质/黄色', 35.00, 2005, 50, 200, '7101234567890'),
      ('工具箱', 11 ,'套', '32件套', 280.00, 2005, 8, 40, '7112345678901');