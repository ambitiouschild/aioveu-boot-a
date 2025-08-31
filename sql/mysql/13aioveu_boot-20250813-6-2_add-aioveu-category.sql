

-- ----------------------------
-- 1. 创建数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS aioveu_boot CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;



-- 2. 创建表 && 数据初始化
-- ----------------------------
use aioveu_boot;
-- 物资管理模块(管理企业的物资（如办公用品、生产原料等），包括物资的入库、出库、库存量监控等。) - 物资分类表  共8张表
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_category`;

CREATE TABLE `aioveu_category` (
                                   `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '分类ID（主键）',
                                   `name` VARCHAR(50) NOT NULL COMMENT '分类名称',
                                   `parent_id` BIGINT UNSIGNED DEFAULT 0 COMMENT '父分类ID',
                                   `level` TINYINT UNSIGNED DEFAULT 1 COMMENT '分类层级',
                                   `description` VARCHAR(200) COMMENT '分类描述',
                                   `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

                                   PRIMARY KEY (`id`),

    -- 唯一约束确保分类名称唯一
                                   UNIQUE INDEX `uniq_name` (`name`),

    -- 索引优化
                                   INDEX `idx_parent` (`parent_id`)


) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='物资分类表';

-- 插入示例分类数据
INSERT INTO `aioveu_category` (name, parent_id, level, description)
VALUES
('办公设备', 0, 1, '办公室使用的各类设备'),
('IT设备', 0, 1, '信息技术相关设备'),
('生产设备', 0, 1, '生产制造相关设备'),
('实验室设备', 0, 1, '科研实验相关设备'),
('其他设备', 0, 1, '其他未分类设备'),

-- 办公设备子分类
('打印机', 1, 2, '各类打印机'),
('复印机', 1, 2, '复印设备'),
('扫描仪', 1, 2, '文档扫描设备'),
('投影仪', 1, 2, '投影设备'),

-- IT设备子分类
('服务器', 2, 2, '服务器设备'),
('网络设备', 2, 2, '路由器、交换机等'),
('存储设备', 2, 2, '存储服务器、NAS等'),
('工作站', 2, 2, '高性能计算工作站'),

-- 生产设备子分类
('机床', 3, 2, '各类加工机床'),
('注塑机', 3, 2, '塑料成型设备'),
('装配线', 3, 2, '生产线装配设备'),
('检测设备', 3, 2, '质量检测设备'),

-- 实验室设备子分类
('分析仪器', 4, 2, '化学分析仪器'),
('显微镜', 4, 2, '各类显微镜'),
('培养箱', 4, 2, '细胞/细菌培养设备'),
('实验台', 4, 2, '实验室工作台');