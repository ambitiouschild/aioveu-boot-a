

-- ----------------------------
-- 1. 创建数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS aioveu_boot CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;



-- 2. 创建表 && 数据初始化
-- ----------------------------
use aioveu_boot;
-- 客户管理模块(管理客户信息，包括客户的基本信息、联系人、交易记录等。) - 联系人表
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_contact`;

CREATE TABLE `aioveu_contact` (
                                  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '联系人ID（主键）',
                                  `customer_id` BIGINT UNSIGNED NOT NULL COMMENT '客户ID',
                                  `name` VARCHAR(50) NOT NULL COMMENT '联系人姓名',
                                  `position` VARCHAR(50) COMMENT '职位',
                                  `department` VARCHAR(50) COMMENT '部门',
                                  `mobile` VARCHAR(20) COMMENT '手机号码',
                                  `phone` VARCHAR(20) COMMENT '办公电话',
                                  `email` VARCHAR(100) COMMENT '电子邮箱',
                                  `wechat` VARCHAR(50) COMMENT '微信号',
                                  `is_primary` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否是主要联系人：0-否，1-是',
                                  `gender` TINYINT(1) COMMENT '性别：0-女，1-男',
                                  `birthday` DATE COMMENT '生日',
                                  `hobbies` VARCHAR(200) COMMENT '兴趣爱好',
                                  `notes` TEXT COMMENT '备注',
                                  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

                                  PRIMARY KEY (`id`),

    -- 外键约束（确保客户存在）
                                  CONSTRAINT `fk_contact_customer`
                                      FOREIGN KEY (`customer_id`)
                                          REFERENCES `aioveu_customer`(`id`)
                                          ON DELETE CASCADE
                                          ON UPDATE CASCADE,

    -- 唯一约束：确保每个客户只有一个主要联系人
                                  UNIQUE INDEX `uniq_primary_contact` (`customer_id`, `is_primary`),

    -- 索引优化
                                  INDEX `idx_customer` (`customer_id`),
                                  INDEX `idx_name` (`name`),
                                  INDEX `idx_mobile` (`mobile`),
                                  INDEX `idx_email` (`email`),
                                  INDEX `idx_primary` (`is_primary`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='客户联系人表';

-- 插入联系人数据
INSERT INTO `aioveu_contact` (
    customer_id, name, position, department,
    mobile, phone, email, wechat,
    is_primary, gender, birthday, hobbies
) VALUES
      -- 华为联系人
      (1, '张华', '采购经理', '采购部',
       '13800138001', '0755-12345678', 'zhanghua@huawei.com', 'zhanghua_hw',
       1, 1, '1985-03-15', '登山、摄影'),

      (1, '李梅', '技术总监', '研发部',
       '13900139002', '0755-87654321', 'limei@huawei.com', 'limei_hw',
       0, 0, '1988-07-22', '阅读、旅行'),

      -- 腾讯联系人
      (2, '王强', '市场总监', '市场部',
       '13700137003', '0755-23456789', 'wangqiang@tencent.com', 'wangqiang_tx',
       1, 1, '1983-11-05', '高尔夫、品茶'),

      (2, '赵雪', '财务经理', '财务部',
       '13600136004', '0755-98765432', 'zhaoxue@tencent.com', 'zhaoxue_tx',
       0, 0, '1990-02-18', '瑜伽、烹饪'),

      -- 阿里巴巴联系人
      (3, '陈明', '运营总监', '运营部',
       '13500135005', '0571-12345678', 'chenming@alibaba.com', 'chenming_ali',
       1, 1, '1980-09-30', '足球、音乐'),

      (3, '刘芳', '人力资源', '人力资源部',
       '13400134006', '0571-87654321', 'liufang@alibaba.com', 'liufang_ali',
       0, 0, '1987-04-12', '绘画、舞蹈');