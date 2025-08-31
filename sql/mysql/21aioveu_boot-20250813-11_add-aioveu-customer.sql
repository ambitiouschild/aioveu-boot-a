

-- ----------------------------
-- 1. 创建数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS aioveu_boot CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;



-- 2. 创建表 && 数据初始化
-- ----------------------------
use aioveu_boot;
-- 客户管理模块(管理客户信息，包括客户的基本信息、联系人、交易记录等。) - 客户表  共五张表
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_customer`;

CREATE TABLE `aioveu_customer` (
                                   `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '客户ID（主键）',
                                   `customer_no` VARCHAR(50) NOT NULL COMMENT '客户编号（唯一）',
                                   `name` VARCHAR(100) NOT NULL COMMENT '客户名称',
                                   `type` TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '客户类型：1-企业，2-个人，3-政府，4-其他',
                                   `industry_id` INT UNSIGNED COMMENT '行业分类ID',
                                   `credit_rating` TINYINT UNSIGNED NOT NULL DEFAULT 3 COMMENT '信用等级：1-AAA，2-AA，3-A，4-B，5-C',
                                   `customer_status` TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '客户状态：1-潜在客户，2-活跃客户，3-休眠客户，4-流失客户',
                                   `address` VARCHAR(200) COMMENT '客户地址',
                                   `city` VARCHAR(50) COMMENT '所在城市',
                                   `province` VARCHAR(50) COMMENT '所在省份',
                                   `country` VARCHAR(50) DEFAULT '中国' COMMENT '国家',
                                   `postal_code` VARCHAR(20) COMMENT '邮政编码',
                                   `phone` VARCHAR(20) COMMENT '联系电话',
                                   `email` VARCHAR(100) COMMENT '电子邮箱',
                                   `website` VARCHAR(100) COMMENT '网址',
                                   `tax_id` VARCHAR(50) COMMENT '税号',
                                   `bank_account` VARCHAR(50) COMMENT '银行账号',
                                   `bank_name` VARCHAR(100) COMMENT '开户行',
                                   `annual_revenue` DECIMAL(15,2) UNSIGNED COMMENT '年营业额',
                                   `employee_count` INT UNSIGNED COMMENT '员工人数',
                                   `established_date` DATE COMMENT '成立日期',
                                   `source` TINYINT UNSIGNED COMMENT '客户来源：1-线上推广，2-线下活动，3-客户推荐，4-销售开发，5-其他',
                                   `sales_rep_id` BIGINT UNSIGNED COMMENT '销售负责人ID',
                                   `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

                                   PRIMARY KEY (`id`),

    -- 唯一约束确保客户编号唯一
                                   UNIQUE INDEX `uniq_customer_no` (`customer_no`),

    -- 唯一约束确保税号唯一
                                   UNIQUE INDEX `uniq_tax_id` (`tax_id`),

    -- 外键约束（确保销售负责人存在）
                                   CONSTRAINT `fk_customer_sales_rep`
                                       FOREIGN KEY (`sales_rep_id`)
                                           REFERENCES `aioveu_employee`(`employee_id`)
                                           ON DELETE SET NULL
                                           ON UPDATE CASCADE,

    -- 索引优化
                                   INDEX `idx_name` (`name`),
                                   INDEX `idx_type` (`type`),
                                   INDEX `idx_industry` (`industry_id`),
                                   INDEX `idx_credit` (`credit_rating`),
                                   INDEX `idx_status` (`customer_status`),
                                   INDEX `idx_city` (`city`),
                                   INDEX `idx_sales_rep` (`sales_rep_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='客户信息表';

-- 插入客户数据
INSERT INTO `aioveu_customer` (
    customer_no, name, type, industry_id, credit_rating, customer_status,
    address, city, province, postal_code, phone, email, website,
    tax_id, bank_account, bank_name, annual_revenue, employee_count,
    established_date, source, sales_rep_id
) VALUES
      ('CUST-001', '华为技术有限公司', 1, 2, 1, 2,
       '深圳市龙岗区坂田华为基地', '深圳', '广东', '518129', '0755-28780808', 'contact@huawei.com', 'www.huawei.com',
       '914403001922038726', '7559143870000001', '中国工商银行深圳分行', 891400000000.00, 195000,
       '1987-09-15', 4, 1),

      ('CUST-002', '腾讯科技', 1, 2, 1, 2,
       '深圳市南山区高新科技园', '深圳', '广东', '518057', '0755-86013388', 'service@tencent.com', 'www.tencent.com',
       '91440300708461136W', '7559143870000002', '中国建设银行深圳分行', 560100000000.00, 112771,
       '1998-11-11', 4, 2),

      ('CUST-003', '阿里巴巴集团', 1, 2, 1, 2,
       '杭州市余杭区文一西路969号', '杭州', '浙江', '311121', '0571-85022088', 'contact@alibaba.com', 'www.alibaba.com',
       '91330100779780458U', '5719143870000003', '中国银行杭州分行', 717300000000.00, 254941,
       '1999-09-09', 4, 3),

      ('CUST-004', '小米科技', 1, 2, 2, 2,
       '北京市海淀区清河中街68号', '北京', '北京', '100085', '4001005678', 'support@xiaomi.com', 'www.mi.com',
       '91110108551385082Q', '7559143870000004', '招商银行北京分行', 328300000000.00, 33427,
       '2010-04-06', 4, 4);