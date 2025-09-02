/*
 Navicat Premium Data Transfer

 Source Server         : aioveu_Cloud_98_3309
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : 139.224.209.98:3309
 Source Schema         : aioveu_boot

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 01/09/2025 00:57:29
*/
CREATE DATABASE IF NOT EXISTS aioveu_boot CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;


SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for aioveu_attendance
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_attendance`;
CREATE TABLE `aioveu_attendance`  (
  `attendance_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `employee_id` bigint UNSIGNED NOT NULL COMMENT '员工ID',
  `date` date NOT NULL COMMENT '日期',
  `checkin_time` datetime NULL DEFAULT NULL COMMENT '上班打卡时间',
  `checkout_time` datetime NULL DEFAULT NULL COMMENT '下班打卡时间',
  `work_hours` decimal(4, 1) NULL DEFAULT NULL COMMENT '工作时长(小时)',
  `status` tinyint NULL DEFAULT NULL COMMENT '考勤状态：0-正常，1-迟到，2-早退，3-缺勤，4-休假',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`attendance_id`) USING BTREE,
  INDEX `idx_date`(`date` ASC) USING BTREE,
  INDEX `idx_check_time`(`checkin_time` ASC) USING BTREE,
  INDEX `idx_employee`(`employee_id` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  CONSTRAINT `fk_attendance_employee` FOREIGN KEY (`employee_id`) REFERENCES `aioveu_employee` (`employee_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '考勤表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of aioveu_attendance
-- ----------------------------
INSERT INTO `aioveu_attendance` VALUES (1, 1, '2025-08-15', '2025-08-15 09:00:00', '2025-08-15 17:30:00', 8.0, 0, '2025-08-15 09:00:00', '2025-08-15 09:00:00');
INSERT INTO `aioveu_attendance` VALUES (2, 2, '2025-08-15', '2025-08-15 09:00:00', '2025-08-15 17:30:00', 8.0, 0, '2025-08-15 09:00:00', '2025-08-15 09:00:00');
INSERT INTO `aioveu_attendance` VALUES (3, 3, '2025-08-15', '2025-08-15 09:00:00', '2025-08-15 17:30:00', 8.0, 0, '2025-08-15 09:00:00', '2025-08-15 09:00:00');
INSERT INTO `aioveu_attendance` VALUES (4, 4, '2025-08-15', '2025-08-15 09:00:00', '2025-08-15 17:30:00', 8.0, 0, '2025-08-15 09:00:00', '2025-08-15 09:00:00');
INSERT INTO `aioveu_attendance` VALUES (5, 1, '2025-08-15', '2025-08-15 09:00:00', '2025-08-15 17:30:00', 8.0, 0, '2025-08-15 09:00:00', '2025-08-15 09:00:00');

-- ----------------------------
-- Table structure for aioveu_category
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_category`;
CREATE TABLE `aioveu_category`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '分类ID（主键）',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类名称',
  `parent_id` bigint UNSIGNED NULL DEFAULT 0 COMMENT '父分类ID',
  `level` tinyint UNSIGNED NULL DEFAULT 1 COMMENT '分类层级',
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_name`(`name` ASC) USING BTREE,
  INDEX `idx_parent`(`parent_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '物资分类表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of aioveu_category
-- ----------------------------
INSERT INTO `aioveu_category` VALUES (1, '办公设备', 0, 1, '办公室使用的各类设备', '2025-08-31 19:29:25', '2025-08-31 19:29:25');
INSERT INTO `aioveu_category` VALUES (2, 'IT设备', 0, 1, '信息技术相关设备', '2025-08-31 19:29:25', '2025-08-31 19:29:25');
INSERT INTO `aioveu_category` VALUES (3, '生产设备', 0, 1, '生产制造相关设备', '2025-08-31 19:29:25', '2025-08-31 19:29:25');
INSERT INTO `aioveu_category` VALUES (4, '实验室设备', 0, 1, '科研实验相关设备', '2025-08-31 19:29:25', '2025-08-31 19:29:25');
INSERT INTO `aioveu_category` VALUES (5, '其他设备', 0, 1, '其他未分类设备', '2025-08-31 19:29:25', '2025-08-31 19:29:25');
INSERT INTO `aioveu_category` VALUES (6, '打印机', 1, 2, '各类打印机', '2025-08-31 19:29:25', '2025-08-31 19:29:25');
INSERT INTO `aioveu_category` VALUES (7, '复印机', 1, 2, '复印设备', '2025-08-31 19:29:25', '2025-08-31 19:29:25');
INSERT INTO `aioveu_category` VALUES (8, '扫描仪', 1, 2, '文档扫描设备', '2025-08-31 19:29:25', '2025-08-31 19:29:25');
INSERT INTO `aioveu_category` VALUES (9, '投影仪', 1, 2, '投影设备', '2025-08-31 19:29:25', '2025-08-31 19:29:25');
INSERT INTO `aioveu_category` VALUES (10, '服务器', 2, 2, '服务器设备', '2025-08-31 19:29:25', '2025-08-31 19:29:25');
INSERT INTO `aioveu_category` VALUES (11, '网络设备', 2, 2, '路由器、交换机等', '2025-08-31 19:29:25', '2025-08-31 19:29:25');
INSERT INTO `aioveu_category` VALUES (12, '存储设备', 2, 2, '存储服务器、NAS等', '2025-08-31 19:29:25', '2025-08-31 19:29:25');
INSERT INTO `aioveu_category` VALUES (13, '工作站', 2, 2, '高性能计算工作站', '2025-08-31 19:29:25', '2025-08-31 19:29:25');
INSERT INTO `aioveu_category` VALUES (14, '机床', 3, 2, '各类加工机床', '2025-08-31 19:29:25', '2025-08-31 19:29:25');
INSERT INTO `aioveu_category` VALUES (15, '注塑机', 3, 2, '塑料成型设备', '2025-08-31 19:29:25', '2025-08-31 19:29:25');
INSERT INTO `aioveu_category` VALUES (16, '装配线', 3, 2, '生产线装配设备', '2025-08-31 19:29:25', '2025-08-31 19:29:25');
INSERT INTO `aioveu_category` VALUES (17, '检测设备', 3, 2, '质量检测设备', '2025-08-31 19:29:25', '2025-08-31 19:29:25');
INSERT INTO `aioveu_category` VALUES (18, '分析仪器', 4, 2, '化学分析仪器', '2025-08-31 19:29:25', '2025-08-31 19:29:25');
INSERT INTO `aioveu_category` VALUES (19, '显微镜', 4, 2, '各类显微镜', '2025-08-31 19:29:25', '2025-08-31 19:29:25');
INSERT INTO `aioveu_category` VALUES (20, '培养箱', 4, 2, '细胞/细菌培养设备', '2025-08-31 19:29:25', '2025-08-31 19:29:25');
INSERT INTO `aioveu_category` VALUES (21, '实验台', 4, 2, '实验室工作台', '2025-08-31 19:29:25', '2025-08-31 19:29:25');

-- ----------------------------
-- Table structure for aioveu_contact
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_contact`;
CREATE TABLE `aioveu_contact`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '联系人ID（主键）',
  `customer_id` bigint UNSIGNED NOT NULL COMMENT '客户ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '联系人姓名',
  `position` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职位',
  `department` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '办公电话',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子邮箱',
  `wechat` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信号',
  `is_primary` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否是主要联系人：0-否，1-是',
  `gender` tinyint(1) NULL DEFAULT NULL COMMENT '性别：0-女，1-男',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `hobbies` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '兴趣爱好',
  `notes` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_primary_contact`(`customer_id` ASC, `is_primary` ASC) USING BTREE,
  INDEX `idx_customer`(`customer_id` ASC) USING BTREE,
  INDEX `idx_name`(`name` ASC) USING BTREE,
  INDEX `idx_mobile`(`mobile` ASC) USING BTREE,
  INDEX `idx_email`(`email` ASC) USING BTREE,
  INDEX `idx_primary`(`is_primary` ASC) USING BTREE,
  CONSTRAINT `fk_contact_customer` FOREIGN KEY (`customer_id`) REFERENCES `aioveu_customer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '客户联系人表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of aioveu_contact
-- ----------------------------
INSERT INTO `aioveu_contact` VALUES (1, 1, '张华', '采购经理', '采购部', '13800138001', '0755-12345678', 'zhanghua@huawei.com', 'zhanghua_hw', 1, 1, '1985-03-15', '登山、摄影', NULL, '2025-08-31 19:29:42', '2025-08-31 19:29:42');
INSERT INTO `aioveu_contact` VALUES (2, 1, '李梅', '技术总监', '研发部', '13900139002', '0755-87654321', 'limei@huawei.com', 'limei_hw', 0, 0, '1988-07-22', '阅读、旅行', NULL, '2025-08-31 19:29:42', '2025-08-31 19:29:42');
INSERT INTO `aioveu_contact` VALUES (3, 2, '王强', '市场总监', '市场部', '13700137003', '0755-23456789', 'wangqiang@tencent.com', 'wangqiang_tx', 1, 1, '1983-11-05', '高尔夫、品茶', NULL, '2025-08-31 19:29:42', '2025-08-31 19:29:42');
INSERT INTO `aioveu_contact` VALUES (4, 2, '赵雪', '财务经理', '财务部', '13600136004', '0755-98765432', 'zhaoxue@tencent.com', 'zhaoxue_tx', 0, 0, '1990-02-18', '瑜伽、烹饪', NULL, '2025-08-31 19:29:42', '2025-08-31 19:29:42');
INSERT INTO `aioveu_contact` VALUES (5, 3, '陈明', '运营总监', '运营部', '13500135005', '0571-12345678', 'chenming@alibaba.com', 'chenming_ali', 1, 1, '1980-09-30', '足球、音乐', NULL, '2025-08-31 19:29:42', '2025-08-31 19:29:42');
INSERT INTO `aioveu_contact` VALUES (6, 3, '刘芳', '人力资源', '人力资源部', '13400134006', '0571-87654321', 'liufang@alibaba.com', 'liufang_ali', 0, 0, '1987-04-12', '绘画、舞蹈', NULL, '2025-08-31 19:29:42', '2025-08-31 19:29:42');

-- ----------------------------
-- Table structure for aioveu_customer
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_customer`;
CREATE TABLE `aioveu_customer`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '客户ID（主键）',
  `customer_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户编号（唯一）',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户名称',
  `type` tinyint UNSIGNED NOT NULL DEFAULT 1 COMMENT '客户类型：1-企业，2-个人，3-政府，4-其他',
  `industry_id` int UNSIGNED NULL DEFAULT NULL COMMENT '行业分类ID',
  `credit_rating` tinyint UNSIGNED NOT NULL DEFAULT 3 COMMENT '信用等级：1-AAA，2-AA，3-A，4-B，5-C',
  `customer_status` tinyint UNSIGNED NOT NULL DEFAULT 1 COMMENT '客户状态：1-潜在客户，2-活跃客户，3-休眠客户，4-流失客户',
  `address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户地址',
  `city` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所在城市',
  `province` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所在省份',
  `country` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '中国' COMMENT '国家',
  `postal_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮政编码',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子邮箱',
  `website` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网址',
  `tax_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '税号',
  `bank_account` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '银行账号',
  `bank_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开户行',
  `annual_revenue` decimal(15, 2) UNSIGNED NULL DEFAULT NULL COMMENT '年营业额',
  `employee_count` int UNSIGNED NULL DEFAULT NULL COMMENT '员工人数',
  `established_date` date NULL DEFAULT NULL COMMENT '成立日期',
  `source` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '客户来源：1-线上推广，2-线下活动，3-客户推荐，4-销售开发，5-其他',
  `sales_rep_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '销售负责人ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_customer_no`(`customer_no` ASC) USING BTREE,
  UNIQUE INDEX `uniq_tax_id`(`tax_id` ASC) USING BTREE,
  INDEX `idx_name`(`name` ASC) USING BTREE,
  INDEX `idx_type`(`type` ASC) USING BTREE,
  INDEX `idx_industry`(`industry_id` ASC) USING BTREE,
  INDEX `idx_credit`(`credit_rating` ASC) USING BTREE,
  INDEX `idx_status`(`customer_status` ASC) USING BTREE,
  INDEX `idx_city`(`city` ASC) USING BTREE,
  INDEX `idx_sales_rep`(`sales_rep_id` ASC) USING BTREE,
  CONSTRAINT `fk_customer_sales_rep` FOREIGN KEY (`sales_rep_id`) REFERENCES `aioveu_employee` (`employee_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '客户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of aioveu_customer
-- ----------------------------
INSERT INTO `aioveu_customer` VALUES (1, 'CUST-001', '华为技术有限公司', 1, 2, 1, 2, '深圳市龙岗区坂田华为基地', '深圳', '广东', '中国', '518129', '0755-28780808', 'contact@huawei.com', 'www.huawei.com', '914403001922038726', '7559143870000001', '中国工商银行深圳分行', 891400000000.00, 195000, '1987-09-15', 4, 1, '2025-08-31 19:29:40', '2025-08-31 19:29:40');
INSERT INTO `aioveu_customer` VALUES (2, 'CUST-002', '腾讯科技', 1, 2, 1, 2, '深圳市南山区高新科技园', '深圳', '广东', '中国', '518057', '0755-86013388', 'service@tencent.com', 'www.tencent.com', '91440300708461136W', '7559143870000002', '中国建设银行深圳分行', 560100000000.00, 112771, '1998-11-11', 4, 2, '2025-08-31 19:29:40', '2025-08-31 19:29:40');
INSERT INTO `aioveu_customer` VALUES (3, 'CUST-003', '阿里巴巴集团', 1, 2, 1, 2, '杭州市余杭区文一西路969号', '杭州', '浙江', '中国', '311121', '0571-85022088', 'contact@alibaba.com', 'www.alibaba.com', '91330100779780458U', '5719143870000003', '中国银行杭州分行', 717300000000.00, 254941, '1999-09-09', 4, 3, '2025-08-31 19:29:40', '2025-08-31 19:29:40');
INSERT INTO `aioveu_customer` VALUES (4, 'CUST-004', '小米科技', 1, 2, 2, 2, '北京市海淀区清河中街68号', '北京', '北京', '中国', '100085', '4001005678', 'support@xiaomi.com', 'www.mi.com', '91110108551385082Q', '7559143870000004', '招商银行北京分行', 328300000000.00, 33427, '2010-04-06', 4, 4, '2025-08-31 19:29:40', '2025-08-31 19:29:40');

-- ----------------------------
-- Table structure for aioveu_department
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_department`;
CREATE TABLE `aioveu_department`  (
  `dept_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `dept_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门名称',
  `parent_dept_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '上级部门ID，用于构建部门树',
  `manager_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '部门经理，关联employee表',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`dept_id`) USING BTREE,
  UNIQUE INDEX `idx_dept_name`(`dept_name` ASC) USING BTREE,
  INDEX `fk_department_parent`(`parent_dept_id` ASC) USING BTREE,
  INDEX `fk_department_manager`(`manager_id` ASC) USING BTREE,
  CONSTRAINT `fk_department_manager` FOREIGN KEY (`manager_id`) REFERENCES `aioveu_employee` (`employee_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_department_parent` FOREIGN KEY (`parent_dept_id`) REFERENCES `aioveu_department` (`dept_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '公司部门组织结构表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of aioveu_department
-- ----------------------------
INSERT INTO `aioveu_department` VALUES (1, '技术研发中心', NULL, 1, '2025-08-31 19:29:16', '2025-08-31 19:29:16');
INSERT INTO `aioveu_department` VALUES (2, '产品研发部', 1, 2, '2025-08-31 19:29:16', '2025-08-31 19:29:16');
INSERT INTO `aioveu_department` VALUES (3, '质量保障部', 1, 3, '2025-08-31 19:29:16', '2025-08-31 19:29:16');
INSERT INTO `aioveu_department` VALUES (4, '市场营销中心', NULL, 1, '2025-08-31 19:29:16', '2025-08-31 19:29:16');
INSERT INTO `aioveu_department` VALUES (5, '品牌推广部', 4, 2, '2025-08-31 19:29:16', '2025-08-31 19:29:16');
INSERT INTO `aioveu_department` VALUES (6, '客户关系部', 4, 3, '2025-08-31 19:29:16', '2025-08-31 19:29:16');
INSERT INTO `aioveu_department` VALUES (7, '人力资源中心', NULL, 1, '2025-08-31 19:29:16', '2025-08-31 19:29:16');
INSERT INTO `aioveu_department` VALUES (8, '人才发展部', 7, 2, '2025-08-31 19:29:16', '2025-08-31 19:29:16');
INSERT INTO `aioveu_department` VALUES (9, '薪酬福利部', 7, 3, '2025-08-31 19:29:16', '2025-08-31 19:29:16');

-- ----------------------------
-- Table structure for aioveu_employee
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_employee`;
CREATE TABLE `aioveu_employee`  (
  `employee_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '员工ID',
  `emp_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '员工编号',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `gender` tinyint(1) NULL DEFAULT NULL COMMENT '性别：0-女，1-男',
  `birth_date` date NULL DEFAULT NULL COMMENT '出生日期',
  `id_card` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `dept_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '所属部门',
  `position_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '岗位ID',
  `hire_date` date NOT NULL COMMENT '入职日期',
  `salary` decimal(10, 2) NULL DEFAULT NULL COMMENT '基本薪资',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '状态：0-离职，1-在职,2-休假,3-实习',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`employee_id`) USING BTREE,
  UNIQUE INDEX `uk_emp_code`(`emp_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_id_card`(`id_card` ASC) USING BTREE,
  INDEX `fk_employee_position`(`position_id` ASC) USING BTREE,
  INDEX `idx_name`(`name` ASC) USING BTREE,
  INDEX `idx_dept_position`(`dept_id` ASC, `position_id` ASC) USING BTREE,
  INDEX `idx_hire_date`(`hire_date` ASC) USING BTREE,
  CONSTRAINT `fk_employee_department` FOREIGN KEY (`dept_id`) REFERENCES `aioveu_department` (`dept_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_employee_position` FOREIGN KEY (`position_id`) REFERENCES `aioveu_position` (`position_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '员工信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of aioveu_employee
-- ----------------------------
INSERT INTO `aioveu_employee` VALUES (1, 'EMP1001', '李白', 1, '1990-05-20', '410823199005201234', '13061656199', 'libai@aioveu.com', 1, 1, '2020-03-15', 20000.00, 1, '2025-08-31 19:29:19', '2025-08-31 19:29:19');
INSERT INTO `aioveu_employee` VALUES (2, 'EMP1002', '杜甫', 1, '1988-11-12', '410823198811121235', '13061656198', 'dufu@aioveu.com', 1, 2, '2019-07-22', 30000.00, 1, '2025-08-31 19:29:19', '2025-08-31 19:29:19');
INSERT INTO `aioveu_employee` VALUES (3, 'EMP1003', '李清照', 0, '1995-02-28', '410823199502281236', '13061656197', 'liqingzhao@aioveu.com', 2, 5, '2021-01-10', 50000.00, 1, '2025-08-31 19:29:19', '2025-08-31 19:29:19');
INSERT INTO `aioveu_employee` VALUES (4, 'EMP1004', '白居易', 1, '1992-08-15', '410823199208151237', '13061656196', 'baijuyi@aioveu.com', 3, 6, '2022-05-30', 50000.00, 1, '2025-08-31 19:29:19', '2025-08-31 19:29:19');
INSERT INTO `aioveu_employee` VALUES (5, 'EMP1005', '苏轼', 1, '1993-04-18', '410823199304181238', '13061656195', 'sushi@aioveu.com', 4, 7, '2023-02-14', 35000.00, 1, '2025-08-31 19:29:19', '2025-08-31 19:29:19');

-- ----------------------------
-- Table structure for aioveu_equipment
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_equipment`;
CREATE TABLE `aioveu_equipment`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '设备ID（主键）',
  `asset_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资产编号（唯一）',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '设备名称',
  `category_id` bigint UNSIGNED NOT NULL COMMENT '设备分类ID',
  `model` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备型号',
  `serial_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '序列号（唯一）',
  `location_id` int UNSIGNED NULL DEFAULT NULL COMMENT '位置ID（关联位置表）',
  `department_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '所属部门ID',
  `responsible_person` bigint UNSIGNED NULL DEFAULT NULL COMMENT '责任人（关联员工表）',
  `purchase_date` date NULL DEFAULT NULL COMMENT '采购日期',
  `purchase_price` decimal(12, 2) UNSIGNED NULL DEFAULT NULL COMMENT '采购价格',
  `warranty_period` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '保修期（月）',
  `maintenance_cycle` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '维保周期（月）',
  `last_maintenance` date NULL DEFAULT NULL COMMENT '上次维保日期',
  `next_maintenance` date NULL DEFAULT NULL COMMENT '下次维保日期',
  `status` tinyint UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态：1-在用，2-闲置，3-维修中，4-报废',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_asset_no`(`asset_no` ASC) USING BTREE,
  UNIQUE INDEX `serial_no`(`serial_no` ASC) USING BTREE,
  INDEX `fk_equipment_department`(`department_id` ASC) USING BTREE,
  INDEX `fk_equipment_responsible`(`responsible_person` ASC) USING BTREE,
  INDEX `idx_name`(`name` ASC) USING BTREE,
  INDEX `idx_category`(`category_id` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  INDEX `idx_next_maintenance`(`next_maintenance` ASC) USING BTREE,
  CONSTRAINT `fk_equipment_category` FOREIGN KEY (`category_id`) REFERENCES `aioveu_category` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_equipment_department` FOREIGN KEY (`department_id`) REFERENCES `aioveu_department` (`dept_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_equipment_responsible` FOREIGN KEY (`responsible_person`) REFERENCES `aioveu_employee` (`employee_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '设备管理表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of aioveu_equipment
-- ----------------------------
INSERT INTO `aioveu_equipment` VALUES (1, 'ASSET-001', '激光打印机', 3, 'HP LaserJet Pro M404dn', 'SN-PRT-001', 1, 1, 1, '2022-01-15', 1200.00, 24, 6, '2023-05-10', NULL, 1, NULL, '2025-08-31 19:29:34', '2025-08-31 19:29:34');
INSERT INTO `aioveu_equipment` VALUES (2, 'ASSET-002', '投影仪', 9, 'Epson EB-U05', 'SN-PROJ-001', 2, 2, 2, '2022-03-20', 3500.00, 36, 12, '2023-04-15', NULL, 1, NULL, '2025-08-31 19:29:34', '2025-08-31 19:29:34');
INSERT INTO `aioveu_equipment` VALUES (3, 'ASSET-003', '服务器', 10, 'Dell PowerEdge R740', 'SN-SVR-001', 3, 3, 3, '2021-11-10', 25000.00, 48, 6, '2023-06-01', NULL, 1, NULL, '2025-08-31 19:29:34', '2025-08-31 19:29:34');
INSERT INTO `aioveu_equipment` VALUES (4, 'ASSET-004', '空调', 5, '格力 KFR-72LW', 'SN-AC-001', 4, 4, 4, '2020-07-05', 6500.00, 60, 3, '2023-05-20', NULL, 1, NULL, '2025-08-31 19:29:34', '2025-08-31 19:29:34');

-- ----------------------------
-- Table structure for aioveu_inbound
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_inbound`;
CREATE TABLE `aioveu_inbound`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '入库ID（主键）',
  `inbound_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '入库单号（唯一）',
  `material_id` bigint UNSIGNED NOT NULL COMMENT '物资ID',
  `warehouse_id` bigint UNSIGNED NOT NULL COMMENT '仓库ID',
  `quantity` decimal(12, 4) UNSIGNED NOT NULL COMMENT '入库数量（支持小数计量）',
  `unit_price` decimal(12, 4) UNSIGNED NOT NULL COMMENT '入库单价',
  `total_amount` decimal(12, 4) UNSIGNED NOT NULL COMMENT '总金额',
  `batch_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '批次号',
  `production_date` date NULL DEFAULT NULL COMMENT '生产日期',
  `expiry_date` date NULL DEFAULT NULL COMMENT '有效期至',
  `supplier_id` int UNSIGNED NULL DEFAULT NULL COMMENT '供应商ID',
  `inbound_type` tinyint UNSIGNED NOT NULL DEFAULT 1 COMMENT '入库类型：1-采购入库，2-退货入库，3-调拨入库，4-其他',
  `in_time` datetime NOT NULL COMMENT '入库时间',
  `operator_id` bigint UNSIGNED NOT NULL COMMENT '操作员ID（关联员工表）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_inbound_no`(`inbound_no` ASC) USING BTREE,
  INDEX `fk_inbound_operator`(`operator_id` ASC) USING BTREE,
  INDEX `idx_material`(`material_id` ASC) USING BTREE,
  INDEX `idx_warehouse`(`warehouse_id` ASC) USING BTREE,
  INDEX `idx_supplier`(`supplier_id` ASC) USING BTREE,
  INDEX `idx_inbound_type`(`inbound_type` ASC) USING BTREE,
  INDEX `idx_in_time`(`in_time` ASC) USING BTREE,
  CONSTRAINT `fk_inbound_material` FOREIGN KEY (`material_id`) REFERENCES `aioveu_material` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_inbound_operator` FOREIGN KEY (`operator_id`) REFERENCES `aioveu_employee` (`employee_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_inbound_warehouse` FOREIGN KEY (`warehouse_id`) REFERENCES `aioveu_warehouse` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '入库表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of aioveu_inbound
-- ----------------------------
INSERT INTO `aioveu_inbound` VALUES (1, 'IN-202306-001', 1, 1, 100.0000, 25.9900, 2599.0000, 'BATCH-2023-06-001', '2023-06-01', '2024-06-01', 2001, 1, '2023-06-05 10:30:00', 1, '采购入库', '2025-08-31 19:29:32', '2025-08-31 19:29:32');
INSERT INTO `aioveu_inbound` VALUES (2, 'IN-202306-002', 2, 1, 50.0000, 15.5000, 775.0000, 'BATCH-2023-06-002', '2023-06-05', '2025-06-05', 2001, 1, '2023-06-05 14:15:00', 1, '采购入库', '2025-08-31 19:29:32', '2025-08-31 19:29:32');
INSERT INTO `aioveu_inbound` VALUES (3, 'IN-202306-003', 3, 2, 2000.0000, 12.5000, 25000.0000, 'BATCH-2023-05-001', '2023-05-15', '2024-05-15', 2003, 1, '2023-06-06 09:00:00', 2, '生产原料采购', '2025-08-31 19:29:32', '2025-08-31 19:29:32');
INSERT INTO `aioveu_inbound` VALUES (4, 'IN-202306-004', 4, 3, 50.0000, 89.0000, 4450.0000, 'BATCH-2023-07-001', '2023-07-01', '2026-07-01', 2002, 1, '2023-06-07 11:20:00', 3, '电子产品采购', '2025-08-31 19:29:32', '2025-08-31 19:29:32');

-- ----------------------------
-- Table structure for aioveu_inventory
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_inventory`;
CREATE TABLE `aioveu_inventory`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '库存ID（主键）',
  `warehouse_id` bigint UNSIGNED NOT NULL COMMENT '仓库ID',
  `material_id` bigint UNSIGNED NOT NULL COMMENT '物资ID',
  `quantity` decimal(12, 4) UNSIGNED NOT NULL DEFAULT 0.0000 COMMENT '当前库存数量（支持小数计量）',
  `batch_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '批次号',
  `production_date` date NULL DEFAULT NULL COMMENT '生产日期',
  `expiry_date` date NULL DEFAULT NULL COMMENT '有效期至',
  `last_inbound` datetime NULL DEFAULT NULL COMMENT '最后入库时间',
  `last_outbound` datetime NULL DEFAULT NULL COMMENT '最后出库时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_warehouse_material_batch`(`warehouse_id` ASC, `material_id` ASC, `batch_number` ASC) USING BTREE,
  INDEX `idx_warehouse`(`warehouse_id` ASC) USING BTREE,
  INDEX `idx_material`(`material_id` ASC) USING BTREE,
  INDEX `idx_expiry`(`expiry_date` ASC) USING BTREE,
  INDEX `idx_batch`(`batch_number` ASC) USING BTREE,
  CONSTRAINT `fk_inventory_material` FOREIGN KEY (`material_id`) REFERENCES `aioveu_material` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_inventory_warehouse` FOREIGN KEY (`warehouse_id`) REFERENCES `aioveu_warehouse` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '库存表（记录每个仓库每个物资的库存）' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of aioveu_inventory
-- ----------------------------
INSERT INTO `aioveu_inventory` VALUES (1, 1, 1, 150.0000, 'BATCH-2023-06-001', '2023-06-01', '2024-06-01', NULL, NULL, '2025-08-31 19:29:30', '2025-08-31 19:29:30');
INSERT INTO `aioveu_inventory` VALUES (2, 1, 2, 300.0000, 'BATCH-2023-06-002', '2023-06-05', '2025-06-05', NULL, NULL, '2025-08-31 19:29:30', '2025-08-31 19:29:30');
INSERT INTO `aioveu_inventory` VALUES (3, 1, 3, 800.0000, 'BATCH-2023-06-003', '2023-06-10', NULL, NULL, NULL, '2025-08-31 19:29:30', '2025-08-31 19:29:30');
INSERT INTO `aioveu_inventory` VALUES (4, 2, 4, 2000.0000, 'BATCH-2023-05-001', '2023-05-15', '2024-05-15', NULL, NULL, '2025-08-31 19:29:30', '2025-08-31 19:29:30');
INSERT INTO `aioveu_inventory` VALUES (5, 2, 5, 15.0000, 'BATCH-2023-07-001', '2023-07-01', '2026-07-01', NULL, NULL, '2025-08-31 19:29:30', '2025-08-31 19:29:30');
INSERT INTO `aioveu_inventory` VALUES (6, 3, 6, 50.0000, 'BATCH-2023-04-001', '2023-04-10', '2025-04-10', NULL, NULL, '2025-08-31 19:29:30', '2025-08-31 19:29:30');
INSERT INTO `aioveu_inventory` VALUES (7, 3, 7, 100.0000, 'BATCH-2023-07-002', '2023-07-05', NULL, NULL, NULL, '2025-08-31 19:29:30', '2025-08-31 19:29:30');

-- ----------------------------
-- Table structure for aioveu_material
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_material`;
CREATE TABLE `aioveu_material`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '物资ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '物资名称',
  `category_id` bigint UNSIGNED NOT NULL COMMENT '分类ID',
  `unit` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '个' COMMENT '单位',
  `spec` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规格型号',
  `purchase_price` decimal(12, 2) UNSIGNED NOT NULL COMMENT '采购单价（正数）',
  `supplier_id` int UNSIGNED NULL DEFAULT NULL COMMENT '供应商ID',
  `min_stock` int UNSIGNED NULL DEFAULT 0 COMMENT '最低库存量（非负整数）',
  `max_stock` int UNSIGNED NULL DEFAULT 100 COMMENT '最高库存量（非负整数）',
  `is_active` tinyint(1) NOT NULL DEFAULT 1 COMMENT '启用状态：0-禁用，1-启用',
  `barcode` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品条码（唯一标识）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `barcode`(`barcode` ASC) USING BTREE,
  INDEX `idx_category`(`category_id` ASC) USING BTREE,
  INDEX `idx_supplier`(`supplier_id` ASC) USING BTREE,
  INDEX `idx_barcode`(`barcode` ASC) USING BTREE,
  INDEX `idx_active`(`is_active` ASC) USING BTREE,
  CONSTRAINT `fk_material_category` FOREIGN KEY (`category_id`) REFERENCES `aioveu_category` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `chk_stock_range` CHECK (`min_stock` <= `max_stock`)
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '物资表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of aioveu_material
-- ----------------------------
INSERT INTO `aioveu_material` VALUES (1, 'A4复印纸', 1, '包', '80g/500张', 25.99, 2001, 50, 200, 1, '6923456789012', '2025-08-31 19:29:26', '2025-08-31 19:29:26');
INSERT INTO `aioveu_material` VALUES (2, '黑色签字笔', 1, '盒', '0.5mm/12支装', 15.50, 2001, 100, 500, 1, '6934567890123', '2025-08-31 19:29:26', '2025-08-31 19:29:26');
INSERT INTO `aioveu_material` VALUES (3, '文件夹', 1, '个', 'A4尺寸/蓝色', 3.80, 2001, 200, 1000, 1, '6945678901234', '2025-08-31 19:29:26', '2025-08-31 19:29:26');
INSERT INTO `aioveu_material` VALUES (4, '便签纸', 1, '本', '75x75mm/100页', 5.20, 2001, 150, 800, 1, '6956789012345', '2025-08-31 19:29:26', '2025-08-31 19:29:26');
INSERT INTO `aioveu_material` VALUES (5, '笔记本电脑', 2, '台', '15.6英寸/i7/16GB', 6500.00, 2002, 5, 20, 1, '6967890123456', '2025-08-31 19:29:26', '2025-08-31 19:29:26');
INSERT INTO `aioveu_material` VALUES (6, '无线鼠标', 2, '个', '蓝牙/静音', 89.00, 2002, 20, 100, 1, '6978901234567', '2025-08-31 19:29:26', '2025-08-31 19:29:26');
INSERT INTO `aioveu_material` VALUES (7, 'USB闪存盘', 2, '个', '64GB/USB3.0', 45.00, 2002, 50, 300, 1, '6989012345678', '2025-08-31 19:29:26', '2025-08-31 19:29:26');
INSERT INTO `aioveu_material` VALUES (8, '显示器', 2, '台', '27英寸/4K', 1999.00, 2002, 3, 15, 1, '6990123456789', '2025-08-31 19:29:26', '2025-08-31 19:29:26');
INSERT INTO `aioveu_material` VALUES (9, '钢材', 3, '吨', 'Q235/Φ20mm', 4200.00, 2003, 10, 50, 1, '7001234567890', '2025-08-31 19:29:26', '2025-08-31 19:29:26');
INSERT INTO `aioveu_material` VALUES (10, '塑料颗粒', 3, '千克', 'PP/白色', 12.50, 2003, 500, 2000, 1, '7012345678901', '2025-08-31 19:29:26', '2025-08-31 19:29:26');
INSERT INTO `aioveu_material` VALUES (11, '电子元件', 3, '盒', 'SMD/1000pcs', 85.00, 2003, 100, 500, 1, '7023456789012', '2025-08-31 19:29:26', '2025-08-31 19:29:26');
INSERT INTO `aioveu_material` VALUES (12, '润滑油', 3, '桶', '20L/工业级', 280.00, 2003, 5, 20, 1, '7034567890123', '2025-08-31 19:29:26', '2025-08-31 19:29:26');
INSERT INTO `aioveu_material` VALUES (13, '洗手液', 11, '瓶', '500ml/抑菌', 18.00, 2004, 30, 150, 1, '7045678901234', '2025-08-31 19:29:26', '2025-08-31 19:29:26');
INSERT INTO `aioveu_material` VALUES (14, '消毒湿巾', 11, '包', '80片/桶装', 25.00, 2004, 50, 300, 1, '7056789012345', '2025-08-31 19:29:26', '2025-08-31 19:29:26');
INSERT INTO `aioveu_material` VALUES (15, '垃圾袋', 11, '卷', '大号/50个', 15.00, 2004, 100, 500, 1, '7067890123456', '2025-08-31 19:29:26', '2025-08-31 19:29:26');
INSERT INTO `aioveu_material` VALUES (16, '空气清新剂', 11, '罐', '300ml/柠檬味', 22.00, 2004, 20, 100, 1, '7078901234567', '2025-08-31 19:29:26', '2025-08-31 19:29:26');
INSERT INTO `aioveu_material` VALUES (17, '电动螺丝刀', 11, '把', '12V/锂电', 199.00, 2005, 10, 50, 1, '7089012345678', '2025-08-31 19:29:26', '2025-08-31 19:29:26');
INSERT INTO `aioveu_material` VALUES (18, '万用表', 11, '台', '数字/自动量程', 150.00, 2005, 5, 30, 1, '7090123456789', '2025-08-31 19:29:26', '2025-08-31 19:29:26');
INSERT INTO `aioveu_material` VALUES (19, '安全帽', 11, '顶', 'ABS材质/黄色', 35.00, 2005, 50, 200, 1, '7101234567890', '2025-08-31 19:29:26', '2025-08-31 19:29:26');
INSERT INTO `aioveu_material` VALUES (20, '工具箱', 11, '套', '32件套', 280.00, 2005, 8, 40, 1, '7112345678901', '2025-08-31 19:29:26', '2025-08-31 19:29:26');

-- ----------------------------
-- Table structure for aioveu_outbound
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_outbound`;
CREATE TABLE `aioveu_outbound`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '出库ID（主键）',
  `outbound_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '出库单号（唯一）',
  `material_id` bigint UNSIGNED NOT NULL COMMENT '物资ID',
  `warehouse_id` bigint UNSIGNED NOT NULL COMMENT '仓库ID',
  `quantity` decimal(12, 4) UNSIGNED NOT NULL COMMENT '出库数量',
  `batch_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '批次号',
  `out_time` datetime NOT NULL COMMENT '出库时间',
  `operator_id` bigint UNSIGNED NOT NULL COMMENT '操作员ID（关联员工表）',
  `recipient_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '领用人ID（关联员工表）',
  `department_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '领用部门ID',
  `purpose` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用途说明',
  `project_id` int UNSIGNED NULL DEFAULT NULL COMMENT '关联项目ID',
  `status` tinyint UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态：1-待出库，2-已出库，3-已取消',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_outbound_no`(`outbound_no` ASC) USING BTREE,
  INDEX `fk_outbound_department`(`department_id` ASC) USING BTREE,
  INDEX `idx_material`(`material_id` ASC) USING BTREE,
  INDEX `idx_warehouse`(`warehouse_id` ASC) USING BTREE,
  INDEX `idx_operator`(`operator_id` ASC) USING BTREE,
  INDEX `idx_recipient`(`recipient_id` ASC) USING BTREE,
  INDEX `idx_out_time`(`out_time` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  CONSTRAINT `fk_outbound_department` FOREIGN KEY (`department_id`) REFERENCES `aioveu_department` (`dept_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_outbound_material` FOREIGN KEY (`material_id`) REFERENCES `aioveu_material` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_outbound_operator` FOREIGN KEY (`operator_id`) REFERENCES `aioveu_employee` (`employee_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_outbound_recipient` FOREIGN KEY (`recipient_id`) REFERENCES `aioveu_employee` (`employee_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_outbound_warehouse` FOREIGN KEY (`warehouse_id`) REFERENCES `aioveu_warehouse` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '出库记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of aioveu_outbound
-- ----------------------------
INSERT INTO `aioveu_outbound` VALUES (1, 'OUT-202306-001', 1, 1, 50.0000, 'BATCH-2023-06-001', '2023-06-05 14:30:00', 1, 1, 1, '部门日常办公用品领用', 3001, 2, '2025-08-31 19:29:38', '2025-08-31 19:29:38');
INSERT INTO `aioveu_outbound` VALUES (2, 'OUT-202306-002', 2, 1, 20.0000, 'BATCH-2023-06-002', '2023-06-06 10:15:00', 1, 2, 2, '项目组办公用品领用', 3002, 2, '2025-08-31 19:29:38', '2025-08-31 19:29:38');
INSERT INTO `aioveu_outbound` VALUES (3, 'OUT-202306-003', 3, 2, 500.0000, 'BATCH-2023-05-001', '2023-06-07 09:45:00', 2, 3, 3, '生产原料领用', 3003, 2, '2025-08-31 19:29:38', '2025-08-31 19:29:38');
INSERT INTO `aioveu_outbound` VALUES (4, 'OUT-202306-004', 4, 3, 10.0000, 'BATCH-2023-07-001', '2023-06-08 11:20:00', 3, 4, 4, '设备维修配件更换', 3004, 2, '2025-08-31 19:29:38', '2025-08-31 19:29:38');

-- ----------------------------
-- Table structure for aioveu_performance
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_performance`;
CREATE TABLE `aioveu_performance`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '绩效记录ID',
  `employee_id` bigint UNSIGNED NOT NULL COMMENT '员工ID',
  `period_year` smallint UNSIGNED NOT NULL COMMENT '考核年份',
  `period_quarter` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '考核季度(1-4)',
  `kpi_score` tinyint UNSIGNED NULL DEFAULT NULL COMMENT 'KPI评分(1-100分)',
  `productivity` decimal(5, 2) UNSIGNED NULL DEFAULT NULL COMMENT '生产率百分比(%)',
  `review` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '主管评语',
  `performance_grade` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '绩效等级',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_employee`(`employee_id` ASC) USING BTREE,
  INDEX `idx_period`(`period_year` ASC, `period_quarter` ASC) USING BTREE,
  CONSTRAINT `fk_performance_employee` FOREIGN KEY (`employee_id`) REFERENCES `aioveu_employee` (`employee_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '员工绩效考评表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of aioveu_performance
-- ----------------------------
INSERT INTO `aioveu_performance` VALUES (1, 1, 2024, 1, 92, 98.50, '张同学本季度表现优异，超额完成所有KPI指标。在项目A中发挥关键作用，带领团队提前两周完成交付。', 'A', '2024-03-31 18:00:00', '2025-08-31 21:36:58');
INSERT INTO `aioveu_performance` VALUES (2, 1, 2024, 2, 88, 95.20, '整体表现良好，但在项目C的需求分析阶段出现延误。建议加强时间管理能力，提高任务分解效率。', 'B', '2024-06-30 18:00:00', '2025-08-31 21:37:00');
INSERT INTO `aioveu_performance` VALUES (3, 2, 2024, 1, 75, 87.30, '基本达成考核目标，但在团队协作方面有待提高。建议多参与部门技术分享活动，增强沟通能力。', 'C', '2024-03-31 18:00:00', '2025-08-31 21:37:03');
INSERT INTO `aioveu_performance` VALUES (4, 3, 2024, 1, 95, 99.00, '杰出表现！不仅完成本职工作，还主动协助团队解决关键技术难题。提出的流程优化方案预计可节省10%工时。', 'D', '2024-03-31 18:00:00', '2025-08-31 21:37:05');
INSERT INTO `aioveu_performance` VALUES (5, 2, 2024, 2, 82, 90.50, '有明显进步，积极参与团队协作并按时完成任务。在客户沟通中展现出良好的专业素养。', 'A', '2024-06-30 18:00:00', '2025-08-31 21:37:06');
INSERT INTO `aioveu_performance` VALUES (6, 3, 2024, 2, 97, 99.50, '持续卓越表现！主导开发的自动化工具已推广至全部门使用，预计每年可节省人力成本约20万元。', 'B', '2024-06-30 18:00:00', '2025-08-31 21:37:12');

-- ----------------------------
-- Table structure for aioveu_position
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_position`;
CREATE TABLE `aioveu_position`  (
  `position_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `position_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位名称',
  `dept_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '所属部门ID',
  `position_level` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '职级(1-10，数字越大职级越高)',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '岗位描述',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`position_id`) USING BTREE,
  UNIQUE INDEX `idx_dept_position`(`dept_id` ASC, `position_name` ASC) USING BTREE,
  CONSTRAINT `fk_position_department` FOREIGN KEY (`dept_id`) REFERENCES `aioveu_department` (`dept_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '公司岗位信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of aioveu_position
-- ----------------------------
INSERT INTO `aioveu_position` VALUES (1, '董事长', 1, 10, '公司最高决策人，管理总经理', '2025-08-31 19:29:17', '2025-08-31 19:29:17');
INSERT INTO `aioveu_position` VALUES (2, '总经理', 1, 9, '负责公司日常管理，协调各部门工作', '2025-08-31 19:29:17', '2025-08-31 19:29:17');
INSERT INTO `aioveu_position` VALUES (3, '法务总监', 3, 8, '负责公司法律事务管理', '2025-08-31 19:29:17', '2025-08-31 19:29:17');
INSERT INTO `aioveu_position` VALUES (4, '财务总监', 4, 8, '负责公司财务管理工作', '2025-08-31 19:29:17', '2025-08-31 19:29:17');
INSERT INTO `aioveu_position` VALUES (5, '研发总监', 2, 8, '负责技术研发部门管理工作', '2025-08-31 19:29:17', '2025-08-31 19:29:17');
INSERT INTO `aioveu_position` VALUES (6, '高级工程师', 2, 6, '负责核心产品研发工作', '2025-08-31 19:29:17', '2025-08-31 19:29:17');
INSERT INTO `aioveu_position` VALUES (7, '财务专员', 4, 4, '负责日常财务处理工作', '2025-08-31 19:29:17', '2025-08-31 19:29:17');
INSERT INTO `aioveu_position` VALUES (8, '法务专员', 3, 4, '处理公司合同和合规事务', '2025-08-31 19:29:17', '2025-08-31 19:29:17');

-- ----------------------------
-- Table structure for aioveu_procurement
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_procurement`;
CREATE TABLE `aioveu_procurement`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '采购ID（主键）',
  `procurement_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '采购单号（唯一）',
  `supplier_id` int UNSIGNED NOT NULL COMMENT '供应商ID',
  `material_id` bigint UNSIGNED NOT NULL COMMENT '物资ID',
  `quantity` int UNSIGNED NOT NULL COMMENT '采购数量',
  `unit_price` decimal(12, 4) UNSIGNED NOT NULL COMMENT '采购单价',
  `total_amount` decimal(12, 4) UNSIGNED NOT NULL COMMENT '总金额',
  `order_date` datetime NOT NULL COMMENT '下单时间',
  `expected_delivery` date NULL DEFAULT NULL COMMENT '预计到货日期',
  `actual_delivery` date NULL DEFAULT NULL COMMENT '实际到货日期',
  `receipt_date` datetime NULL DEFAULT NULL COMMENT '签收时间',
  `warehouse_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '入库仓库ID',
  `inbound_date` datetime NULL DEFAULT NULL COMMENT '入库时间',
  `status` tinyint UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态：1-待审核，2-已下单，3-已发货，4-已收货，5-已入库，6-已取消',
  `applicant_id` bigint UNSIGNED NOT NULL COMMENT '申请人ID（关联员工表）',
  `reviewer_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '审核人ID',
  `review_time` datetime NULL DEFAULT NULL COMMENT '审核时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_procurement_no`(`procurement_no` ASC) USING BTREE,
  INDEX `fk_procurement_warehouse`(`warehouse_id` ASC) USING BTREE,
  INDEX `fk_procurement_applicant`(`applicant_id` ASC) USING BTREE,
  INDEX `fk_procurement_reviewer`(`reviewer_id` ASC) USING BTREE,
  INDEX `idx_supplier`(`supplier_id` ASC) USING BTREE,
  INDEX `idx_material`(`material_id` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  INDEX `idx_order_date`(`order_date` ASC) USING BTREE,
  INDEX `idx_expected_delivery`(`expected_delivery` ASC) USING BTREE,
  CONSTRAINT `fk_procurement_applicant` FOREIGN KEY (`applicant_id`) REFERENCES `aioveu_employee` (`employee_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_procurement_material` FOREIGN KEY (`material_id`) REFERENCES `aioveu_material` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_procurement_reviewer` FOREIGN KEY (`reviewer_id`) REFERENCES `aioveu_employee` (`employee_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_procurement_warehouse` FOREIGN KEY (`warehouse_id`) REFERENCES `aioveu_warehouse` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '采购流程表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of aioveu_procurement
-- ----------------------------
INSERT INTO `aioveu_procurement` VALUES (1, 'PO-202306-001', 2001, 1, 100, 25.9900, 2599.0000, '2023-06-01 10:30:00', '2023-06-05', NULL, NULL, NULL, NULL, 2, 1, 1, NULL, NULL, '2025-08-31 19:29:36', '2025-08-31 19:29:36');
INSERT INTO `aioveu_procurement` VALUES (2, 'PO-202306-002', 2001, 2, 50, 15.5000, 775.0000, '2023-06-01 14:15:00', '2023-06-06', NULL, NULL, NULL, NULL, 2, 1, 1, NULL, NULL, '2025-08-31 19:29:36', '2025-08-31 19:29:36');
INSERT INTO `aioveu_procurement` VALUES (3, 'PO-202306-003', 2003, 3, 2000, 12.5000, 25000.0000, '2023-06-02 09:00:00', '2023-06-07', NULL, NULL, NULL, NULL, 2, 2, 2, NULL, NULL, '2025-08-31 19:29:36', '2025-08-31 19:29:36');
INSERT INTO `aioveu_procurement` VALUES (4, 'PO-202306-004', 2002, 4, 50, 89.0000, 4450.0000, '2023-06-03 11:20:00', '2023-06-08', NULL, NULL, NULL, NULL, 2, 3, 2, NULL, NULL, '2025-08-31 19:29:36', '2025-08-31 19:29:36');

-- ----------------------------
-- Table structure for aioveu_salary
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_salary`;
CREATE TABLE `aioveu_salary`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '工资记录ID',
  `employee_id` bigint UNSIGNED NOT NULL COMMENT '员工ID',
  `salary_period` date NOT NULL COMMENT '工资周期（月份）',
  `base_salary` decimal(12, 2) NOT NULL DEFAULT 0.00 COMMENT '基本工资',
  `performance_bonus` decimal(12, 2) NULL DEFAULT 0.00 COMMENT '绩效奖金',
  `overtime_pay` decimal(12, 2) NULL DEFAULT 0.00 COMMENT '加班补贴',
  `allowances` decimal(12, 2) NULL DEFAULT 0.00 COMMENT '各类津贴',
  `social_security` decimal(12, 2) NULL DEFAULT 0.00 COMMENT '社保扣除',
  `housing_fund` decimal(12, 2) NULL DEFAULT 0.00 COMMENT '住房公积金',
  `tax` decimal(12, 2) NULL DEFAULT 0.00 COMMENT '个人所得税',
  `other_deductions` decimal(12, 2) NULL DEFAULT 0.00 COMMENT '其他扣款',
  `net_salary` decimal(12, 2) NOT NULL COMMENT '实发工资',
  `payment_date` date NULL DEFAULT NULL COMMENT '实际发放日期',
  `payment_status` tinyint(1) NULL DEFAULT 0 COMMENT '发放状态：0-未发放，1-已发放，2-发放失败',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_employee_period`(`employee_id` ASC, `salary_period` ASC) USING BTREE,
  INDEX `idx_period`(`salary_period` ASC) USING BTREE,
  INDEX `idx_payment_status`(`payment_status` ASC) USING BTREE,
  CONSTRAINT `fk_salary_employee` FOREIGN KEY (`employee_id`) REFERENCES `aioveu_employee` (`employee_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '员工工资明细表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of aioveu_salary
-- ----------------------------
INSERT INTO `aioveu_salary` VALUES (1, 1, '2024-12-01', 10000.00, 1500.00, 300.00, 800.00, 850.00, 600.00, 280.00, 0.00, 10870.00, '2024-12-05', 1, '2025-08-31 19:29:23', '2025-08-31 19:29:23');
INSERT INTO `aioveu_salary` VALUES (2, 2, '2024-12-01', 8000.00, 1200.00, 150.00, 500.00, 700.00, 500.00, 210.00, 50.00, 7390.00, '2024-12-05', 1, '2025-08-31 19:29:23', '2025-08-31 19:29:23');
INSERT INTO `aioveu_salary` VALUES (3, 3, '2025-01-01', 12000.00, 2000.00, 500.00, 1000.00, 900.00, 800.00, 320.00, 100.00, 12380.00, NULL, 0, '2025-08-31 19:29:23', '2025-08-31 19:29:23');
INSERT INTO `aioveu_salary` VALUES (4, 1, '2025-01-01', 10000.00, 1300.00, 200.00, 800.00, 850.00, 600.00, 250.00, 0.00, 10600.00, NULL, 2, '2025-08-31 19:29:23', '2025-08-31 19:29:23');

-- ----------------------------
-- Table structure for aioveu_sales_order
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_sales_order`;
CREATE TABLE `aioveu_sales_order`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '订单ID（主键）',
  `order_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单编号（唯一）',
  `customer_id` bigint UNSIGNED NOT NULL COMMENT '客户ID',
  `contact_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '联系人ID',
  `order_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
  `expected_delivery` date NULL DEFAULT NULL COMMENT '预计交货日期',
  `actual_delivery` date NULL DEFAULT NULL COMMENT '实际交货日期',
  `total_amount` decimal(15, 2) UNSIGNED NOT NULL COMMENT '订单总金额',
  `currency` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'CNY' COMMENT '货币代码',
  `discount` decimal(5, 2) NULL DEFAULT 0.00 COMMENT '整体折扣率',
  `tax_rate` decimal(5, 2) NULL DEFAULT 0.00 COMMENT '税率',
  `tax_amount` decimal(12, 2) NULL DEFAULT NULL COMMENT '税额',
  `grand_total` decimal(15, 2) NULL DEFAULT NULL COMMENT '订单总额（含税）',
  `payment_terms` tinyint UNSIGNED NULL DEFAULT 1 COMMENT '付款条件：1-预付全款，2-货到付款，3-月结30天，4-月结60天，5-其他',
  `payment_status` tinyint UNSIGNED NOT NULL DEFAULT 1 COMMENT '支付状态：1-未支付，2-部分支付，3-已支付',
  `order_status` tinyint UNSIGNED NOT NULL DEFAULT 1 COMMENT '订单状态：1-待处理，2-已确认，3-生产中，4-已发货，5-已完成，6-已取消',
  `shipping_address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货地址',
  `shipping_method` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '运输方式：1-快递，2-物流，3-自提，4-其他',
  `tracking_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '物流单号',
  `sales_rep_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '销售负责人ID',
  `operator_id` bigint UNSIGNED NOT NULL COMMENT '操作员ID',
  `notes` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_order_no`(`order_no` ASC) USING BTREE,
  INDEX `fk_order_contact`(`contact_id` ASC) USING BTREE,
  INDEX `fk_order_operator`(`operator_id` ASC) USING BTREE,
  INDEX `idx_customer`(`customer_id` ASC) USING BTREE,
  INDEX `idx_order_date`(`order_date` ASC) USING BTREE,
  INDEX `idx_order_status`(`order_status` ASC) USING BTREE,
  INDEX `idx_payment_status`(`payment_status` ASC) USING BTREE,
  INDEX `idx_sales_rep`(`sales_rep_id` ASC) USING BTREE,
  INDEX `idx_tracking_no`(`tracking_no` ASC) USING BTREE,
  CONSTRAINT `fk_order_contact` FOREIGN KEY (`contact_id`) REFERENCES `aioveu_contact` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_order_customer` FOREIGN KEY (`customer_id`) REFERENCES `aioveu_customer` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_order_operator` FOREIGN KEY (`operator_id`) REFERENCES `aioveu_employee` (`employee_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_order_sales_rep` FOREIGN KEY (`sales_rep_id`) REFERENCES `aioveu_employee` (`employee_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '销售订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of aioveu_sales_order
-- ----------------------------
INSERT INTO `aioveu_sales_order` VALUES (1, 'SO-202306-001', 1, 1, '2023-06-10 09:30:00', '2023-06-20', NULL, 50000.00, 'CNY', 0.05, 0.13, NULL, 54825.00, 3, 2, 2, '深圳市龙岗区坂田华为基地', 1, NULL, 1, 1, NULL, '2025-08-31 19:29:46', '2025-08-31 19:29:46');
INSERT INTO `aioveu_sales_order` VALUES (2, 'SO-202306-002', 2, 3, '2023-06-11 14:15:00', '2023-06-25', NULL, 30000.00, 'CNY', 0.00, 0.13, NULL, 33900.00, 1, 3, 3, '深圳市南山区高新科技园', 2, NULL, 2, 2, NULL, '2025-08-31 19:29:46', '2025-08-31 19:29:46');
INSERT INTO `aioveu_sales_order` VALUES (3, 'SO-202306-003', 3, 5, '2023-06-12 10:45:00', '2023-07-05', NULL, 150000.00, 'CNY', 0.10, 0.13, NULL, 154050.00, 4, 1, 1, '杭州市余杭区文一西路969号', 3, NULL, 3, 3, NULL, '2025-08-31 19:29:46', '2025-08-31 19:29:46');
INSERT INTO `aioveu_sales_order` VALUES (4, 'SO-202306-004', 4, 5, '2023-06-13 11:20:00', '2023-06-30', NULL, 75000.00, 'CNY', 0.00, 0.13, NULL, 84750.00, 2, 1, 2, '北京市海淀区清河中街68号', 1, NULL, 4, 4, '11', '2025-08-31 19:29:46', '2025-08-31 23:25:55');

-- ----------------------------
-- Table structure for aioveu_sales_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_sales_order_detail`;
CREATE TABLE `aioveu_sales_order_detail`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '明细ID（主键）',
  `order_id` bigint UNSIGNED NOT NULL COMMENT '订单ID',
  `material_id` bigint UNSIGNED NOT NULL COMMENT '物资ID',
  `quantity` decimal(12, 4) UNSIGNED NOT NULL COMMENT '数量',
  `unit_price` decimal(12, 4) UNSIGNED NOT NULL COMMENT '单价',
  `discount` decimal(5, 2) NULL DEFAULT 0.00 COMMENT '折扣率',
  `tax_rate` decimal(5, 2) NULL DEFAULT 0.00 COMMENT '税率',
  `subtotal` decimal(15, 4) NULL DEFAULT NULL COMMENT '小计',
  `tax_amount` decimal(15, 4) NULL DEFAULT NULL COMMENT '税额',
  `total_price` decimal(15, 4) NULL DEFAULT NULL COMMENT '总金额',
  `batch_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '批次号',
  `delivery_date` date NULL DEFAULT NULL COMMENT '要求交货日期',
  `warehouse_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '发货仓库ID',
  `status` tinyint UNSIGNED NULL DEFAULT 1 COMMENT '明细状态：1-待处理，2-备货中，3-已发货，4-已收货，5-已取消',
  `notes` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order`(`order_id` ASC) USING BTREE,
  INDEX `idx_material`(`material_id` ASC) USING BTREE,
  INDEX `idx_warehouse`(`warehouse_id` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  CONSTRAINT `fk_detail_material` FOREIGN KEY (`material_id`) REFERENCES `aioveu_material` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_detail_order` FOREIGN KEY (`order_id`) REFERENCES `aioveu_sales_order` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_detail_warehouse` FOREIGN KEY (`warehouse_id`) REFERENCES `aioveu_warehouse` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单明细表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of aioveu_sales_order_detail
-- ----------------------------
INSERT INTO `aioveu_sales_order_detail` VALUES (1, 1, 1, 100.0000, 25.9900, 0.05, 0.13, NULL, NULL, NULL, 'BATCH-2023-06-001', '2023-06-20', 1, 3, NULL, '2025-08-31 19:29:47', '2025-08-31 19:29:47');
INSERT INTO `aioveu_sales_order_detail` VALUES (2, 1, 2, 50.0000, 15.5000, 0.10, 0.13, NULL, NULL, NULL, 'BATCH-2023-06-002', '2023-06-20', 1, 3, NULL, '2025-08-31 19:29:47', '2025-08-31 19:29:47');
INSERT INTO `aioveu_sales_order_detail` VALUES (3, 2, 3, 2000.0000, 12.5000, 0.00, 0.13, NULL, NULL, NULL, 'BATCH-2023-05-001', '2023-06-25', 2, 2, NULL, '2025-08-31 19:29:47', '2025-08-31 19:29:47');
INSERT INTO `aioveu_sales_order_detail` VALUES (4, 3, 4, 50.0000, 89.0000, 0.15, 0.13, NULL, NULL, NULL, 'BATCH-2023-07-001', '2023-07-05', 3, 1, NULL, '2025-08-31 19:29:47', '2025-08-31 19:29:47');
INSERT INTO `aioveu_sales_order_detail` VALUES (5, 4, 5, 10.0000, 199.0000, 0.00, 0.13, NULL, NULL, NULL, 'BATCH-2023-06-003', '2023-06-30', 1, 1, NULL, '2025-08-31 19:29:47', '2025-08-31 19:29:47');

-- ----------------------------
-- Table structure for aioveu_transaction
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_transaction`;
CREATE TABLE `aioveu_transaction`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '交易ID（主键）',
  `transaction_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '交易编号（唯一）',
  `customer_id` bigint UNSIGNED NOT NULL COMMENT '客户ID',
  `contact_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '联系人ID',
  `transaction_date` datetime NOT NULL COMMENT '交易日期',
  `amount` decimal(15, 2) NOT NULL COMMENT '交易金额',
  `currency` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'CNY' COMMENT '货币代码',
  `payment_method` tinyint UNSIGNED NOT NULL COMMENT '支付方式：1-现金，2-银行转账，3-信用卡，4-支付宝，5-微信支付，6-其他',
  `payment_status` tinyint UNSIGNED NOT NULL DEFAULT 1 COMMENT '支付状态：1-待支付，2-部分支付，3-已支付，4-已退款',
  `transaction_type` tinyint UNSIGNED NOT NULL DEFAULT 1 COMMENT '交易类型：1-销售，2-服务，3-退款，4-其他',
  `invoice_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发票号码',
  `product_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '产品ID',
  `quantity` int UNSIGNED NULL DEFAULT NULL COMMENT '数量',
  `unit_price` decimal(12, 2) NULL DEFAULT NULL COMMENT '单价',
  `discount` decimal(5, 2) NULL DEFAULT 0.00 COMMENT '折扣率',
  `tax_rate` decimal(5, 2) NULL DEFAULT 0.00 COMMENT '税率',
  `tax_amount` decimal(12, 2) NULL DEFAULT NULL COMMENT '税额',
  `total_amount` decimal(15, 2) NULL DEFAULT NULL COMMENT '总金额',
  `sales_rep_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '销售负责人ID',
  `transaction_status` tinyint UNSIGNED NOT NULL DEFAULT 1 COMMENT '交易状态：1-进行中，2-已完成，3-已取消',
  `notes` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_transaction_no`(`transaction_no` ASC) USING BTREE,
  UNIQUE INDEX `uniq_invoice_no`(`invoice_no` ASC) USING BTREE,
  INDEX `fk_transaction_contact`(`contact_id` ASC) USING BTREE,
  INDEX `idx_customer`(`customer_id` ASC) USING BTREE,
  INDEX `idx_transaction_date`(`transaction_date` ASC) USING BTREE,
  INDEX `idx_payment_status`(`payment_status` ASC) USING BTREE,
  INDEX `idx_transaction_type`(`transaction_type` ASC) USING BTREE,
  INDEX `idx_sales_rep`(`sales_rep_id` ASC) USING BTREE,
  CONSTRAINT `fk_transaction_contact` FOREIGN KEY (`contact_id`) REFERENCES `aioveu_contact` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_transaction_customer` FOREIGN KEY (`customer_id`) REFERENCES `aioveu_customer` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_transaction_sales_rep` FOREIGN KEY (`sales_rep_id`) REFERENCES `aioveu_employee` (`employee_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '客户交易记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of aioveu_transaction
-- ----------------------------
INSERT INTO `aioveu_transaction` VALUES (1, 'TRX-202306-001', 1, 1, '2023-06-15 10:30:00', 50000.00, 'CNY', 2, 3, 1, 'INV-202306-001', 101, 10, 5000.00, 0.00, 0.13, 6500.00, 56500.00, 1, 2, NULL, '2025-08-31 19:29:44', '2025-08-31 19:29:44');
INSERT INTO `aioveu_transaction` VALUES (2, 'TRX-202306-002', 2, 3, '2023-06-16 14:15:00', 30000.00, 'CNY', 4, 3, 2, 'INV-202306-002', 102, 1, 30000.00, 0.00, 0.13, 3900.00, 33900.00, 2, 2, NULL, '2025-08-31 19:29:44', '2025-08-31 19:29:44');
INSERT INTO `aioveu_transaction` VALUES (3, 'TRX-202306-003', 3, 5, '2023-06-17 09:45:00', 150000.00, 'CNY', 2, 3, 1, 'INV-202306-003', 103, 5, 30000.00, 0.05, 0.13, 17550.00, 149550.00, 3, 2, NULL, '2025-08-31 19:29:44', '2025-08-31 19:29:44');
INSERT INTO `aioveu_transaction` VALUES (4, 'TRX-202306-004', 4, 5, '2023-06-18 11:20:00', -5000.00, 'CNY', 2, 4, 3, 'REF-202306-001', 101, 1, 5000.00, 0.00, 0.13, 650.00, -5650.00, 4, 2, NULL, '2025-08-31 19:29:44', '2025-08-31 19:29:44');

-- ----------------------------
-- Table structure for aioveu_warehouse
-- ----------------------------
DROP TABLE IF EXISTS `aioveu_warehouse`;
CREATE TABLE `aioveu_warehouse`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '仓库ID（主键）',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '仓库名称',
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '仓库编码（唯一标识）',
  `location` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '仓库位置',
  `area` decimal(10, 2) UNSIGNED NULL DEFAULT NULL COMMENT '仓库面积（平方米）',
  `capacity` decimal(10, 2) UNSIGNED NULL DEFAULT NULL COMMENT '存储容量（立方米）',
  `manager_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '负责人ID（关联员工表）',
  `contact_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '仓库描述',
  `is_active` tinyint(1) NOT NULL DEFAULT 1 COMMENT '启用状态：0-停用，1-启用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `code`(`code` ASC) USING BTREE,
  UNIQUE INDEX `uniq_code`(`code` ASC) USING BTREE,
  INDEX `fk_warehouse_manager`(`manager_id` ASC) USING BTREE,
  INDEX `idx_name`(`name` ASC) USING BTREE,
  INDEX `idx_location`(`location` ASC) USING BTREE,
  INDEX `idx_active`(`is_active` ASC) USING BTREE,
  CONSTRAINT `fk_warehouse_manager` FOREIGN KEY (`manager_id`) REFERENCES `aioveu_employee` (`employee_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '仓库信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of aioveu_warehouse
-- ----------------------------
INSERT INTO `aioveu_warehouse` VALUES (1, '总部中心仓', 'WH-001', '北京市朝阳区科技园A栋B1层', 2000.00, 15000.00, 1, '010-12345678', '公司总部主要仓库，存放办公用品和电子产品', 1, '2025-08-31 19:29:28', '2025-08-31 19:29:28');
INSERT INTO `aioveu_warehouse` VALUES (2, '南方分仓', 'WH-002', '广州市天河区工业园C区8号', 1500.00, 12000.00, 2, '020-87654321', '华南地区分仓，主要存放季节性物资', 1, '2025-08-31 19:29:28', '2025-08-31 19:29:28');
INSERT INTO `aioveu_warehouse` VALUES (3, '生产原料仓', 'WH-003', '上海市浦东新区制造基地', 3000.00, 25000.00, 3, '021-11223344', '生产原材料专用仓库，恒温恒湿控制', 1, '2025-08-31 19:29:28', '2025-08-31 19:29:28');

-- ----------------------------
-- Table structure for gen_config
-- ----------------------------
DROP TABLE IF EXISTS `gen_config`;
CREATE TABLE `gen_config`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `table_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '表名',
  `module_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '模块名',
  `package_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '包名',
  `business_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务名',
  `entity_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '实体类名',
  `author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '作者',
  `parent_menu_id` bigint NULL DEFAULT NULL COMMENT '上级菜单ID，对应sys_menu的id ',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` bit(1) NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tablename`(`table_name` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '代码生成基础配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of gen_config
-- ----------------------------

-- ----------------------------
-- Table structure for gen_field_config
-- ----------------------------
DROP TABLE IF EXISTS `gen_field_config`;
CREATE TABLE `gen_field_config`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `config_id` bigint NOT NULL COMMENT '关联的配置ID',
  `column_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `column_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `column_length` int NULL DEFAULT NULL,
  `field_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字段名称',
  `field_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字段类型',
  `field_sort` int NULL DEFAULT NULL COMMENT '字段排序',
  `field_comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字段描述',
  `max_length` int NULL DEFAULT NULL,
  `is_required` tinyint(1) NULL DEFAULT NULL COMMENT '是否必填',
  `is_show_in_list` tinyint(1) NULL DEFAULT 0 COMMENT '是否在列表显示',
  `is_show_in_form` tinyint(1) NULL DEFAULT 0 COMMENT '是否在表单显示',
  `is_show_in_query` tinyint(1) NULL DEFAULT 0 COMMENT '是否在查询条件显示',
  `query_type` tinyint NULL DEFAULT NULL COMMENT '查询方式',
  `form_type` tinyint NULL DEFAULT NULL COMMENT '表单类型',
  `dict_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典类型',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `config_id`(`config_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '代码生成字段配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of gen_field_config
-- ----------------------------

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '会员编号',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会员姓名',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会员手机号',
  `gender` tinyint NULL DEFAULT NULL COMMENT '性别',
  `age` int NULL DEFAULT NULL COMMENT '会员年龄',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除(1:已删除;0:未删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会员信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of member
-- ----------------------------

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `config_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '配置名称',
  `config_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '配置key',
  `config_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '配置值',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新人ID',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除标识(0-未删除 1-已删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, '系统限流QPS', 'IP_QPS_THRESHOLD_LIMIT', '10000', '单个IP请求的最大每秒查询数（QPS）阈值Key', '2025-08-31 19:27:52', 1, '2025-09-01 00:36:28', 4, 0);

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '部门名称',
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '部门编号',
  `parent_id` bigint NULL DEFAULT 0 COMMENT '父节点id',
  `tree_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '父节点id路径',
  `sort` smallint NULL DEFAULT 0 COMMENT '显示顺序',
  `status` tinyint NULL DEFAULT 1 COMMENT '状态(1-正常 0-禁用)',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '逻辑删除标识(1-已删除 0-未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_code`(`code` ASC) USING BTREE COMMENT '部门编号唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '部门表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, '可我不敌可爱', 'Aioveu', 0, '0', 1, 1, 1, NULL, 1, '2025-08-31 19:27:20', 0);
INSERT INTO `sys_dept` VALUES (2, '研发部门', 'RD001', 1, '0,1', 1, 1, 2, NULL, 2, '2025-08-31 19:27:20', 0);
INSERT INTO `sys_dept` VALUES (3, '测试部门', 'QA001', 1, '0,1', 1, 1, 2, NULL, 2, '2025-08-31 19:27:21', 0);

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键 ',
  `dict_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '类型编码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '类型名称',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态(0:正常;1:禁用)',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '是否删除(1-删除，0-未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_dict_code`(`dict_code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1, 'gender', '性别', 1, NULL, '2025-08-31 19:27:23', 1, '2025-08-31 19:27:23', 1, 0);
INSERT INTO `sys_dict` VALUES (2, 'notice_type', '通知类型', 1, NULL, '2025-08-31 19:27:23', 1, '2025-08-31 19:27:23', 1, 0);
INSERT INTO `sys_dict` VALUES (3, 'notice_level', '通知级别', 1, NULL, '2025-08-31 19:27:24', 1, '2025-08-31 19:27:24', 1, 0);
INSERT INTO `sys_dict` VALUES (4, 'employee_status', '员工状态', 1, '员工状态：0-离职，1-在职,2-休假,3-实习', '2025-08-31 21:28:43', NULL, '2025-08-31 21:28:43', NULL, 0);
INSERT INTO `sys_dict` VALUES (5, 'attendance_status', '考勤状态', 1, '考勤状态：0-正常，1-迟到，2-早退，3-缺勤，4-休假', '2025-08-31 21:30:09', NULL, '2025-08-31 21:30:09', NULL, 0);
INSERT INTO `sys_dict` VALUES (6, 'position_level', '职级(1-10)', 1, '职级(1-10，数字越大职级越高)', '2025-08-31 21:31:41', NULL, '2025-08-31 21:31:41', NULL, 0);
INSERT INTO `sys_dict` VALUES (7, 'performance_period_quarter', '考核季度(1-4)', 1, '考核季度(1-4)', '2025-08-31 21:34:27', NULL, '2025-08-31 21:34:27', NULL, 0);
INSERT INTO `sys_dict` VALUES (8, 'performance_grade', '绩效等级', 1, 'A b c d', '2025-08-31 21:36:02', NULL, '2025-08-31 21:36:02', NULL, 0);
INSERT INTO `sys_dict` VALUES (9, 'salary_payment_status', '工资发放状态', 1, '发放状态：0-未发放，1-已发放，2-发放失败', '2025-08-31 21:38:06', NULL, '2025-08-31 21:38:06', NULL, 0);
INSERT INTO `sys_dict` VALUES (10, 'material_is_active', '物资启用状态', 1, '启用状态：0-禁用，1-启用', '2025-08-31 21:39:01', NULL, '2025-08-31 21:39:01', NULL, 0);
INSERT INTO `sys_dict` VALUES (11, 'warehouse_is_active', '仓库启用状态', 1, '启用状态：0-停用，1-启用', '2025-08-31 21:39:42', NULL, '2025-08-31 21:39:42', NULL, 0);
INSERT INTO `sys_dict` VALUES (12, 'inbound_type', '入库类型', 1, '入库类型：1-采购入库，2-退货入库，3-调拨入库，4-其他', '2025-08-31 21:41:46', NULL, '2025-08-31 21:41:46', NULL, 0);
INSERT INTO `sys_dict` VALUES (13, 'equipment_status', '设备状态', 1, '状态：1-在用，2-闲置，3-维修中，4-报废', '2025-08-31 21:42:49', NULL, '2025-08-31 21:42:49', NULL, 0);
INSERT INTO `sys_dict` VALUES (14, 'procurement_status', '采购状态', 1, '状态：1-待审核，2-已下单，3-已发货，4-已收货，5-已入库，6-已取消', '2025-08-31 21:43:45', NULL, '2025-08-31 21:43:45', NULL, 0);
INSERT INTO `sys_dict` VALUES (15, 'outbound_status', '出库状态', 1, '状态：1-待出库，2-已出库，3-已取', '2025-08-31 21:45:19', NULL, '2025-08-31 21:45:19', NULL, 0);
INSERT INTO `sys_dict` VALUES (16, 'customer_customer_type', '客户类型', 1, '客户类型：1-企业，2-个人，3-政府，4-其他', '2025-08-31 21:46:13', NULL, '2025-08-31 21:46:13', NULL, 0);
INSERT INTO `sys_dict` VALUES (17, 'customer_credit_rating', '信用等级', 1, '信用等级：1-AAA，2-AA，3-A，4-B，5-C', '2025-08-31 21:47:19', NULL, '2025-08-31 21:47:19', NULL, 0);
INSERT INTO `sys_dict` VALUES (18, 'customer_status', '客户状态', 1, '客户状态：1-潜在客户，2-活跃客户，3-休眠客户，4-流失客户', '2025-08-31 21:48:30', NULL, '2025-08-31 21:48:30', NULL, 0);
INSERT INTO `sys_dict` VALUES (19, 'customer_source', '客户来源', 1, '客户来源：1-线上推广，2-线下活动，3-客户推荐，4-销售开发，5-其他', '2025-08-31 21:49:24', NULL, '2025-08-31 21:49:24', NULL, 0);
INSERT INTO `sys_dict` VALUES (20, 'contact_is_primary', '是否是主要联系人', 1, '是否是主要联系人：0-否，1-是', '2025-08-31 21:50:26', NULL, '2025-08-31 21:50:26', NULL, 0);
INSERT INTO `sys_dict` VALUES (21, 'transaction_payment_method', '交易支付方式', 1, '支付方式：1-现金，2-银行转账，3-信用卡，4-支付宝，5-微信支付，6-其他', '2025-08-31 21:51:02', NULL, '2025-08-31 21:51:02', NULL, 0);
INSERT INTO `sys_dict` VALUES (22, 'transaction_payment_status', '交易支付状态', 1, '支付状态：1-待支付，2-部分支付，3-已支付，4-已退款', '2025-08-31 21:53:55', NULL, '2025-08-31 21:53:55', NULL, 0);
INSERT INTO `sys_dict` VALUES (23, 'transaction_type', '交易类型', 1, '交易类型：1-销售，2-服务，3-退款，4-其他', '2025-08-31 21:54:47', NULL, '2025-08-31 21:54:47', NULL, 0);
INSERT INTO `sys_dict` VALUES (24, 'transaction_status', '交易状态', 1, '交易状态：1-进行中，2-已完成，3-已取', '2025-08-31 21:55:53', NULL, '2025-08-31 21:55:53', NULL, 0);
INSERT INTO `sys_dict` VALUES (25, 'salesOrder_payment_terms', '订单付款条件', 1, '付款条件：1-预付全款，2-货到付款，3-月结30天，4-月结60天，5-其他', '2025-08-31 21:56:43', NULL, '2025-08-31 21:56:43', NULL, 0);
INSERT INTO `sys_dict` VALUES (26, 'salesOrder_payment_status', '订单支付状态', 1, '支付状态：1-未支付，2-部分支付，3-已支付', '2025-08-31 21:57:54', NULL, '2025-08-31 21:57:54', NULL, 0);
INSERT INTO `sys_dict` VALUES (27, 'salesOrder_order_status', '订单状态', 1, '订单状态：1-待处理，2-已确认，3-生产中，4-已发货，5-已完成，6-已取消', '2025-08-31 21:58:34', NULL, '2025-08-31 21:58:34', NULL, 0);
INSERT INTO `sys_dict` VALUES (28, 'salesOrder_shipping_method', '订单运输方式', 1, '运输方式：1-快递，2-物流，3-自提，4-其他', '2025-08-31 22:00:01', NULL, '2025-08-31 22:00:01', NULL, 0);
INSERT INTO `sys_dict` VALUES (29, 'salesOrderDetail_status', '订单明细状态', 1, '明细状态：1-待处理，2-备货中，3-已发货，4-已收货，5-已取消', '2025-08-31 22:00:57', NULL, '2025-08-31 22:00:57', NULL, 0);

-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dict_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '关联字典编码，与sys_dict表中的dict_code对应',
  `value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典项值',
  `label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典项标签',
  `tag_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签类型，用于前端样式展示（如success、warning等）',
  `status` tinyint NULL DEFAULT 0 COMMENT '状态（1-正常，0-禁用）',
  `sort` int NULL DEFAULT 0 COMMENT '排序',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 125 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典项表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_item
-- ----------------------------
INSERT INTO `sys_dict_item` VALUES (1, 'gender', '1', '男', 'primary', 1, 1, NULL, '2025-08-31 19:27:25', 1, '2025-08-31 19:27:25', 1);
INSERT INTO `sys_dict_item` VALUES (2, 'gender', '2', '女', 'danger', 1, 2, NULL, '2025-08-31 19:27:25', 1, '2025-08-31 19:27:25', 1);
INSERT INTO `sys_dict_item` VALUES (3, 'gender', '0', '保密', 'info', 1, 3, NULL, '2025-08-31 19:27:26', 1, '2025-08-31 19:27:26', 1);
INSERT INTO `sys_dict_item` VALUES (4, 'notice_type', '1', '系统升级', 'success', 1, 1, '', '2025-08-31 19:27:26', 1, '2025-08-31 19:27:26', 1);
INSERT INTO `sys_dict_item` VALUES (5, 'notice_type', '2', '系统维护', 'primary', 1, 2, '', '2025-08-31 19:27:26', 1, '2025-08-31 19:27:26', 1);
INSERT INTO `sys_dict_item` VALUES (6, 'notice_type', '3', '安全警告', 'danger', 1, 3, '', '2025-08-31 19:27:26', 1, '2025-08-31 19:27:26', 1);
INSERT INTO `sys_dict_item` VALUES (7, 'notice_type', '4', '假期通知', 'success', 1, 4, '', '2025-08-31 19:27:26', 1, '2025-08-31 19:27:26', 1);
INSERT INTO `sys_dict_item` VALUES (8, 'notice_type', '5', '公司新闻', 'primary', 1, 5, '', '2025-08-31 19:27:27', 1, '2025-08-31 19:27:27', 1);
INSERT INTO `sys_dict_item` VALUES (9, 'notice_type', '99', '其他', 'info', 1, 99, '', '2025-08-31 19:27:27', 1, '2025-08-31 19:27:27', 1);
INSERT INTO `sys_dict_item` VALUES (10, 'notice_level', 'L', '低', 'info', 1, 1, '', '2025-08-31 19:27:27', 1, '2025-08-31 19:27:27', 1);
INSERT INTO `sys_dict_item` VALUES (11, 'notice_level', 'M', '中', 'warning', 1, 2, '', '2025-08-31 19:27:27', 1, '2025-08-31 19:27:27', 1);
INSERT INTO `sys_dict_item` VALUES (12, 'notice_level', 'H', '高', 'danger', 1, 3, '', '2025-08-31 19:27:27', 1, '2025-08-31 19:27:27', 1);
INSERT INTO `sys_dict_item` VALUES (13, 'gender', '3', '外星人', 'success', 1, 3, NULL, '2025-08-31 21:28:11', NULL, '2025-08-31 21:28:11', NULL);
INSERT INTO `sys_dict_item` VALUES (14, 'employee_status', '0', '离职', 'info', 1, 0, NULL, '2025-08-31 21:29:09', NULL, '2025-08-31 21:29:09', NULL);
INSERT INTO `sys_dict_item` VALUES (15, 'employee_status', '1', '在职', 'success', 1, 1, NULL, '2025-08-31 21:29:19', NULL, '2025-08-31 21:29:19', NULL);
INSERT INTO `sys_dict_item` VALUES (16, 'employee_status', '2', '休假', 'warning', 1, 2, NULL, '2025-08-31 21:29:42', NULL, '2025-08-31 21:29:42', NULL);
INSERT INTO `sys_dict_item` VALUES (17, 'employee_status', '3', '实习', 'primary', 1, 3, NULL, '2025-08-31 21:29:54', NULL, '2025-08-31 21:29:54', NULL);
INSERT INTO `sys_dict_item` VALUES (18, 'attendance_status', '0', '正常', 'success', 1, 0, NULL, '2025-08-31 21:30:24', NULL, '2025-08-31 21:30:24', NULL);
INSERT INTO `sys_dict_item` VALUES (19, 'attendance_status', '1', '迟到', 'warning', 1, 1, NULL, '2025-08-31 21:30:43', NULL, '2025-08-31 21:30:43', NULL);
INSERT INTO `sys_dict_item` VALUES (20, 'attendance_status', '2', '早退', 'primary', 1, 2, NULL, '2025-08-31 21:30:58', NULL, '2025-08-31 21:30:58', NULL);
INSERT INTO `sys_dict_item` VALUES (21, 'attendance_status', '3', '缺勤', 'danger', 1, 3, NULL, '2025-08-31 21:31:09', NULL, '2025-08-31 21:31:09', NULL);
INSERT INTO `sys_dict_item` VALUES (22, 'attendance_status', '4', '休假', 'success', 1, 4, NULL, '2025-08-31 21:31:22', NULL, '2025-08-31 21:31:22', NULL);
INSERT INTO `sys_dict_item` VALUES (23, 'position_level', '1', '1', 'primary', 1, 1, NULL, '2025-08-31 21:32:25', NULL, '2025-08-31 21:32:25', NULL);
INSERT INTO `sys_dict_item` VALUES (24, 'position_level', '2', '2', 'primary', 1, 2, NULL, '2025-08-31 21:32:32', NULL, '2025-08-31 21:32:32', NULL);
INSERT INTO `sys_dict_item` VALUES (25, 'position_level', '3', '3', 'success', 1, 3, NULL, '2025-08-31 21:32:38', NULL, '2025-08-31 21:32:38', NULL);
INSERT INTO `sys_dict_item` VALUES (26, 'position_level', '4', '4', 'success', 1, 4, NULL, '2025-08-31 21:32:44', NULL, '2025-08-31 21:32:44', NULL);
INSERT INTO `sys_dict_item` VALUES (27, 'position_level', '5', '5', 'info', 1, 5, NULL, '2025-08-31 21:32:51', NULL, '2025-08-31 21:32:51', NULL);
INSERT INTO `sys_dict_item` VALUES (28, 'position_level', '6', '6', 'info', 1, 6, NULL, '2025-08-31 21:33:00', NULL, '2025-08-31 21:33:00', NULL);
INSERT INTO `sys_dict_item` VALUES (29, 'position_level', '7', '7', 'warning', 1, 7, NULL, '2025-08-31 21:33:12', NULL, '2025-08-31 21:33:12', NULL);
INSERT INTO `sys_dict_item` VALUES (30, 'position_level', '8', '8', 'warning', 1, 8, NULL, '2025-08-31 21:33:22', NULL, '2025-08-31 21:33:22', NULL);
INSERT INTO `sys_dict_item` VALUES (31, 'position_level', '9', '9', 'danger', 1, 9, NULL, '2025-08-31 21:33:32', NULL, '2025-08-31 21:33:32', NULL);
INSERT INTO `sys_dict_item` VALUES (32, 'position_level', '10', '10', 'danger', 1, 10, NULL, '2025-08-31 21:33:44', NULL, '2025-08-31 21:33:44', NULL);
INSERT INTO `sys_dict_item` VALUES (33, 'performance_period_quarter', '1', '第 1 季度', 'success', 1, 1, NULL, '2025-08-31 21:35:06', NULL, '2025-08-31 21:35:06', NULL);
INSERT INTO `sys_dict_item` VALUES (34, 'performance_period_quarter', '2', '第 2 季度', 'danger', 1, 2, NULL, '2025-08-31 21:35:19', NULL, '2025-08-31 21:35:19', NULL);
INSERT INTO `sys_dict_item` VALUES (35, 'performance_period_quarter', '3', '第 3 季度', 'warning', 1, 3, NULL, '2025-08-31 21:35:31', NULL, '2025-08-31 21:35:31', NULL);
INSERT INTO `sys_dict_item` VALUES (36, 'performance_period_quarter', '4', '第 4 季度', 'primary', 1, 4, NULL, '2025-08-31 21:35:43', NULL, '2025-08-31 21:35:43', NULL);
INSERT INTO `sys_dict_item` VALUES (37, 'performance_grade', 'A', 'A', 'success', 1, 1, NULL, '2025-08-31 21:37:28', NULL, '2025-08-31 21:37:28', NULL);
INSERT INTO `sys_dict_item` VALUES (38, 'performance_grade', 'B', 'B', 'primary', 1, 2, NULL, '2025-08-31 21:37:35', NULL, '2025-08-31 21:37:35', NULL);
INSERT INTO `sys_dict_item` VALUES (39, 'performance_grade', 'C', 'C', 'warning', 1, 3, NULL, '2025-08-31 21:37:44', NULL, '2025-08-31 21:37:44', NULL);
INSERT INTO `sys_dict_item` VALUES (40, 'performance_grade', 'D', 'D', 'danger', 1, 4, NULL, '2025-08-31 21:37:50', NULL, '2025-08-31 21:37:50', NULL);
INSERT INTO `sys_dict_item` VALUES (41, 'salary_payment_status', '0', '未发放', 'info', 1, 0, NULL, '2025-08-31 21:38:21', NULL, '2025-08-31 21:38:21', NULL);
INSERT INTO `sys_dict_item` VALUES (42, 'salary_payment_status', '1', '已发放', 'success', 1, 1, NULL, '2025-08-31 21:38:31', NULL, '2025-08-31 21:38:31', NULL);
INSERT INTO `sys_dict_item` VALUES (43, 'salary_payment_status', '2', '发放失败', 'danger', 1, 2, NULL, '2025-08-31 21:38:45', NULL, '2025-08-31 21:38:45', NULL);
INSERT INTO `sys_dict_item` VALUES (44, 'material_is_active', '0', '禁用', 'danger', 1, 0, NULL, '2025-08-31 21:39:14', NULL, '2025-08-31 21:39:14', NULL);
INSERT INTO `sys_dict_item` VALUES (45, 'material_is_active', '1', '启用', 'success', 1, 1, NULL, '2025-08-31 21:39:22', NULL, '2025-08-31 21:39:22', NULL);
INSERT INTO `sys_dict_item` VALUES (46, 'warehouse_is_active', '0', '停用', 'danger', 1, 0, NULL, '2025-08-31 21:39:56', NULL, '2025-08-31 21:39:56', NULL);
INSERT INTO `sys_dict_item` VALUES (47, 'warehouse_is_active', '1', '启用', 'success', 1, 1, NULL, '2025-08-31 21:40:03', NULL, '2025-08-31 21:40:03', NULL);
INSERT INTO `sys_dict_item` VALUES (48, 'inbound_type', '1', '采购入库', 'success', 1, 1, NULL, '2025-08-31 21:42:01', NULL, '2025-08-31 21:42:01', NULL);
INSERT INTO `sys_dict_item` VALUES (49, 'inbound_type', '2', '退货入库', 'danger', 1, 2, NULL, '2025-08-31 21:42:13', NULL, '2025-08-31 21:42:13', NULL);
INSERT INTO `sys_dict_item` VALUES (50, 'inbound_type', '3', '调拨入库', 'primary', 1, 3, NULL, '2025-08-31 21:42:26', NULL, '2025-08-31 21:42:26', NULL);
INSERT INTO `sys_dict_item` VALUES (51, 'inbound_type', '4', '其他', 'warning', 1, 4, NULL, '2025-08-31 21:42:35', NULL, '2025-08-31 21:42:35', NULL);
INSERT INTO `sys_dict_item` VALUES (52, 'equipment_status', '1', '在用', 'success', 1, 1, NULL, '2025-08-31 21:43:01', NULL, '2025-08-31 21:43:01', NULL);
INSERT INTO `sys_dict_item` VALUES (53, 'equipment_status', '2', '闲置', 'warning', 1, 2, NULL, '2025-08-31 21:43:10', NULL, '2025-08-31 21:43:10', NULL);
INSERT INTO `sys_dict_item` VALUES (54, 'equipment_status', '3', '维修中', 'primary', 1, 3, NULL, '2025-08-31 21:43:23', NULL, '2025-08-31 21:43:23', NULL);
INSERT INTO `sys_dict_item` VALUES (55, 'equipment_status', '4', '报废', 'danger', 1, 4, NULL, '2025-08-31 21:43:32', NULL, '2025-08-31 21:43:32', NULL);
INSERT INTO `sys_dict_item` VALUES (56, 'procurement_status', '1', '待审核', 'primary', 1, 1, NULL, '2025-08-31 21:44:07', NULL, '2025-08-31 21:44:07', NULL);
INSERT INTO `sys_dict_item` VALUES (57, 'procurement_status', '2', '已下单', 'warning', 1, 2, NULL, '2025-08-31 21:44:19', NULL, '2025-08-31 21:44:19', NULL);
INSERT INTO `sys_dict_item` VALUES (58, 'procurement_status', '3', '已发货', 'success', 1, 3, NULL, '2025-08-31 21:44:30', NULL, '2025-08-31 21:44:30', NULL);
INSERT INTO `sys_dict_item` VALUES (59, 'procurement_status', '4', '已收货', 'success', 1, 4, NULL, '2025-08-31 21:44:43', NULL, '2025-08-31 21:44:43', NULL);
INSERT INTO `sys_dict_item` VALUES (60, 'procurement_status', '5', '已入库', 'success', 1, 5, NULL, '2025-08-31 21:44:51', NULL, '2025-08-31 21:44:51', NULL);
INSERT INTO `sys_dict_item` VALUES (61, 'procurement_status', '6', '已取消', 'danger', 1, 6, NULL, '2025-08-31 21:45:02', NULL, '2025-08-31 21:45:02', NULL);
INSERT INTO `sys_dict_item` VALUES (62, 'outbound_status', '1', '待出库', 'warning', 1, 1, NULL, '2025-08-31 21:45:32', NULL, '2025-08-31 21:45:32', NULL);
INSERT INTO `sys_dict_item` VALUES (63, 'outbound_status', '2', '已出库', 'primary', 1, 2, NULL, '2025-08-31 21:45:41', NULL, '2025-08-31 21:45:41', NULL);
INSERT INTO `sys_dict_item` VALUES (64, 'outbound_status', '3', '已取', 'success', 1, 3, NULL, '2025-08-31 21:45:54', NULL, '2025-08-31 21:45:54', NULL);
INSERT INTO `sys_dict_item` VALUES (65, 'customer_customer_type', '1', '企业', 'primary', 1, 1, NULL, '2025-08-31 21:46:27', NULL, '2025-08-31 21:46:27', NULL);
INSERT INTO `sys_dict_item` VALUES (66, 'customer_customer_type', '2', '个人', 'success', 1, 2, NULL, '2025-08-31 21:46:36', NULL, '2025-08-31 21:46:36', NULL);
INSERT INTO `sys_dict_item` VALUES (67, 'customer_customer_type', '3', '政府', 'danger', 1, 3, NULL, '2025-08-31 21:46:47', NULL, '2025-08-31 21:46:47', NULL);
INSERT INTO `sys_dict_item` VALUES (68, 'customer_customer_type', '4', '其他', 'warning', 1, 4, NULL, '2025-08-31 21:47:02', NULL, '2025-08-31 21:47:02', NULL);
INSERT INTO `sys_dict_item` VALUES (69, 'customer_credit_rating', '1', 'AAA', 'success', 1, 1, NULL, '2025-08-31 21:47:30', NULL, '2025-08-31 21:47:30', NULL);
INSERT INTO `sys_dict_item` VALUES (70, 'customer_credit_rating', '2', 'AA', 'primary', 1, 2, NULL, '2025-08-31 21:47:38', NULL, '2025-08-31 21:47:38', NULL);
INSERT INTO `sys_dict_item` VALUES (71, 'customer_credit_rating', '3', 'A', 'warning', 1, 3, NULL, '2025-08-31 21:47:57', NULL, '2025-08-31 21:47:57', NULL);
INSERT INTO `sys_dict_item` VALUES (72, 'customer_credit_rating', '4', 'B', 'info', 1, 4, NULL, '2025-08-31 21:48:09', NULL, '2025-08-31 21:48:09', NULL);
INSERT INTO `sys_dict_item` VALUES (73, 'customer_credit_rating', '5', 'C', 'danger', 1, 5, NULL, '2025-08-31 21:48:16', NULL, '2025-08-31 21:48:16', NULL);
INSERT INTO `sys_dict_item` VALUES (74, 'customer_status', '1', '潜在客户', 'primary', 0, 1, NULL, '2025-08-31 21:48:42', NULL, '2025-08-31 21:48:42', NULL);
INSERT INTO `sys_dict_item` VALUES (75, 'customer_status', '2', '活跃客户', 'success', 1, 2, NULL, '2025-08-31 21:48:50', NULL, '2025-08-31 21:48:50', NULL);
INSERT INTO `sys_dict_item` VALUES (76, 'customer_status', '3', '休眠客户', 'warning', 1, 3, NULL, '2025-08-31 21:49:00', NULL, '2025-08-31 21:49:00', NULL);
INSERT INTO `sys_dict_item` VALUES (77, 'customer_status', '4', '流失客户', 'danger', 1, 4, NULL, '2025-08-31 21:49:09', NULL, '2025-08-31 21:49:09', NULL);
INSERT INTO `sys_dict_item` VALUES (78, 'customer_source', '1', '线上推广', 'primary', 1, 1, NULL, '2025-08-31 21:49:35', NULL, '2025-08-31 21:49:35', NULL);
INSERT INTO `sys_dict_item` VALUES (79, 'customer_source', '2', '线下活动', 'success', 1, 2, NULL, '2025-08-31 21:49:45', NULL, '2025-08-31 21:49:45', NULL);
INSERT INTO `sys_dict_item` VALUES (80, 'customer_source', '3', '客户推荐', 'warning', 1, 3, NULL, '2025-08-31 21:49:55', NULL, '2025-08-31 21:49:55', NULL);
INSERT INTO `sys_dict_item` VALUES (81, 'customer_source', '4', '销售开发', 'danger', 1, 4, NULL, '2025-08-31 21:50:03', NULL, '2025-08-31 21:50:03', NULL);
INSERT INTO `sys_dict_item` VALUES (82, 'customer_source', '5', '其他', 'info', 1, 5, NULL, '2025-08-31 21:50:11', NULL, '2025-08-31 21:50:11', NULL);
INSERT INTO `sys_dict_item` VALUES (83, 'contact_is_primary', '0', '否', 'danger', 1, 0, NULL, '2025-08-31 21:50:40', NULL, '2025-08-31 21:50:40', NULL);
INSERT INTO `sys_dict_item` VALUES (84, 'contact_is_primary', '1', '是', 'success', 1, 1, NULL, '2025-08-31 21:50:48', NULL, '2025-08-31 21:50:48', NULL);
INSERT INTO `sys_dict_item` VALUES (85, 'transaction_payment_method', '1', '现金', 'primary', 1, 1, NULL, '2025-08-31 21:51:51', NULL, '2025-08-31 21:51:51', NULL);
INSERT INTO `sys_dict_item` VALUES (86, 'transaction_payment_method', '2', '银行转账', 'warning', 1, 2, NULL, '2025-08-31 21:52:00', NULL, '2025-08-31 21:52:00', NULL);
INSERT INTO `sys_dict_item` VALUES (87, 'transaction_payment_method', '3', '信用卡', 'warning', 1, 3, NULL, '2025-08-31 21:52:29', NULL, '2025-08-31 21:52:29', NULL);
INSERT INTO `sys_dict_item` VALUES (88, 'transaction_payment_method', '4', '支付宝', 'primary', 1, 4, NULL, '2025-08-31 21:52:45', NULL, '2025-08-31 21:52:45', NULL);
INSERT INTO `sys_dict_item` VALUES (89, 'transaction_payment_method', '5', '微信支付', 'success', 1, 5, NULL, '2025-08-31 21:52:56', NULL, '2025-08-31 21:52:56', NULL);
INSERT INTO `sys_dict_item` VALUES (90, 'transaction_payment_method', '6', '其他', 'info', 1, 6, NULL, '2025-08-31 21:53:05', NULL, '2025-08-31 21:53:05', NULL);
INSERT INTO `sys_dict_item` VALUES (91, 'transaction_payment_status', '1', '待支付', 'warning', 1, 1, NULL, '2025-08-31 21:54:08', NULL, '2025-08-31 21:54:08', NULL);
INSERT INTO `sys_dict_item` VALUES (92, 'transaction_payment_status', '2', '部分支付', 'primary', 1, 2, NULL, '2025-08-31 21:54:17', NULL, '2025-08-31 21:54:17', NULL);
INSERT INTO `sys_dict_item` VALUES (93, 'transaction_payment_status', '3', '已支付', 'success', 1, 3, NULL, '2025-08-31 21:54:26', NULL, '2025-08-31 21:54:26', NULL);
INSERT INTO `sys_dict_item` VALUES (94, 'transaction_payment_status', '4', '已退款', 'danger', 1, 4, NULL, '2025-08-31 21:54:34', NULL, '2025-08-31 21:54:34', NULL);
INSERT INTO `sys_dict_item` VALUES (95, 'transaction_type', '1', '销售', 'danger', 1, 1, NULL, '2025-08-31 21:55:01', NULL, '2025-08-31 21:55:01', NULL);
INSERT INTO `sys_dict_item` VALUES (96, 'transaction_type', '2', '服务', 'success', 1, 2, NULL, '2025-08-31 21:55:09', NULL, '2025-08-31 21:55:09', NULL);
INSERT INTO `sys_dict_item` VALUES (97, 'transaction_type', '3', '退款', 'warning', 1, 3, NULL, '2025-08-31 21:55:17', NULL, '2025-08-31 21:55:17', NULL);
INSERT INTO `sys_dict_item` VALUES (98, 'transaction_type', '4', '其他', 'info', 1, 4, NULL, '2025-08-31 21:55:33', NULL, '2025-08-31 21:55:38', NULL);
INSERT INTO `sys_dict_item` VALUES (99, 'transaction_status', '1', '进行中', 'primary', 1, 1, NULL, '2025-08-31 21:56:06', NULL, '2025-08-31 21:56:06', NULL);
INSERT INTO `sys_dict_item` VALUES (100, 'transaction_status', '2', '已完成', 'warning', 1, 2, NULL, '2025-08-31 21:56:16', NULL, '2025-08-31 21:56:22', NULL);
INSERT INTO `sys_dict_item` VALUES (101, 'transaction_status', '3', '已取', 'success', 1, 3, NULL, '2025-08-31 21:56:31', NULL, '2025-08-31 21:56:31', NULL);
INSERT INTO `sys_dict_item` VALUES (102, 'salesOrder_payment_terms', '1', '预付全款', 'success', 1, 1, NULL, '2025-08-31 21:56:59', NULL, '2025-08-31 21:56:59', NULL);
INSERT INTO `sys_dict_item` VALUES (103, 'salesOrder_payment_terms', '2', '货到付款', 'warning', 1, 2, NULL, '2025-08-31 21:57:12', NULL, '2025-08-31 21:57:12', NULL);
INSERT INTO `sys_dict_item` VALUES (104, 'salesOrder_payment_terms', '3', '月结30天', 'primary', 1, 3, NULL, '2025-08-31 21:57:23', NULL, '2025-08-31 21:57:23', NULL);
INSERT INTO `sys_dict_item` VALUES (105, 'salesOrder_payment_terms', '4', '月结60天', 'danger', 1, 4, NULL, '2025-08-31 21:57:33', NULL, '2025-08-31 21:57:33', NULL);
INSERT INTO `sys_dict_item` VALUES (106, 'salesOrder_payment_terms', '5', '其他', 'info', 1, 5, NULL, '2025-08-31 21:57:42', NULL, '2025-08-31 21:57:42', NULL);
INSERT INTO `sys_dict_item` VALUES (107, 'salesOrder_payment_status', '1', '未支付', 'danger', 1, 1, NULL, '2025-08-31 21:58:04', NULL, '2025-08-31 21:58:04', NULL);
INSERT INTO `sys_dict_item` VALUES (108, 'salesOrder_payment_status', '2', '部分支付', 'primary', 1, 2, NULL, '2025-08-31 21:58:13', NULL, '2025-08-31 21:58:13', NULL);
INSERT INTO `sys_dict_item` VALUES (109, 'salesOrder_payment_status', '3', '已支付', 'success', 1, 3, NULL, '2025-08-31 21:58:22', NULL, '2025-08-31 21:58:22', NULL);
INSERT INTO `sys_dict_item` VALUES (110, 'salesOrder_order_status', '1', '待处理', 'warning', 1, 1, NULL, '2025-08-31 21:58:46', NULL, '2025-08-31 21:58:46', NULL);
INSERT INTO `sys_dict_item` VALUES (111, 'salesOrder_order_status', '2', '已确认', 'primary', 1, 2, NULL, '2025-08-31 21:59:03', NULL, '2025-08-31 21:59:03', NULL);
INSERT INTO `sys_dict_item` VALUES (112, 'salesOrder_order_status', '3', '生产中', 'warning', 1, 3, NULL, '2025-08-31 21:59:13', NULL, '2025-08-31 21:59:13', NULL);
INSERT INTO `sys_dict_item` VALUES (113, 'salesOrder_order_status', '4', '已发货', 'primary', 1, 4, NULL, '2025-08-31 21:59:30', NULL, '2025-08-31 21:59:30', NULL);
INSERT INTO `sys_dict_item` VALUES (114, 'salesOrder_order_status', '5', '已完成', 'success', 1, 5, NULL, '2025-08-31 21:59:39', NULL, '2025-08-31 21:59:39', NULL);
INSERT INTO `sys_dict_item` VALUES (115, 'salesOrder_order_status', '6', '已取消', 'danger', 1, 6, NULL, '2025-08-31 21:59:47', NULL, '2025-08-31 21:59:47', NULL);
INSERT INTO `sys_dict_item` VALUES (116, 'salesOrder_shipping_method', '1', '快递', 'primary', 1, 1, NULL, '2025-08-31 22:00:12', NULL, '2025-08-31 22:00:12', NULL);
INSERT INTO `sys_dict_item` VALUES (117, 'salesOrder_shipping_method', '2', '物流', 'warning', 1, 1, NULL, '2025-08-31 22:00:21', NULL, '2025-08-31 22:00:21', NULL);
INSERT INTO `sys_dict_item` VALUES (118, 'salesOrder_shipping_method', '3', '自提', 'success', 1, 3, NULL, '2025-08-31 22:00:31', NULL, '2025-08-31 22:00:31', NULL);
INSERT INTO `sys_dict_item` VALUES (119, 'salesOrder_shipping_method', '4', '其他', 'info', 1, 4, NULL, '2025-08-31 22:00:39', NULL, '2025-08-31 22:00:39', NULL);
INSERT INTO `sys_dict_item` VALUES (120, 'salesOrderDetail_status', '1', '待处理', 'warning', 1, 1, NULL, '2025-08-31 22:01:07', NULL, '2025-08-31 22:01:07', NULL);
INSERT INTO `sys_dict_item` VALUES (121, 'salesOrderDetail_status', '2', '备货中', 'primary', 1, 2, NULL, '2025-08-31 22:01:17', NULL, '2025-08-31 22:01:17', NULL);
INSERT INTO `sys_dict_item` VALUES (122, 'salesOrderDetail_status', '3', '已发货', 'success', 1, 3, NULL, '2025-08-31 22:01:26', NULL, '2025-08-31 22:01:26', NULL);
INSERT INTO `sys_dict_item` VALUES (123, 'salesOrderDetail_status', '4', '已收货', 'success', 1, 4, NULL, '2025-08-31 22:01:36', NULL, '2025-08-31 22:01:36', NULL);
INSERT INTO `sys_dict_item` VALUES (124, 'salesOrderDetail_status', '5', '已取消', 'danger', 1, 5, NULL, '2025-08-31 22:01:43', NULL, '2025-08-31 22:01:43', NULL);

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `module` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '日志模块',
  `request_method` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '请求方式',
  `request_params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '请求参数(批量请求参数可能会超过text)',
  `response_content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '返回参数',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '日志内容',
  `request_uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求路径',
  `method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '方法名',
  `ip` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `province` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '省份',
  `city` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '城市',
  `execution_time` bigint NULL DEFAULT NULL COMMENT '执行时间(ms)',
  `browser` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '浏览器',
  `browser_version` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '浏览器版本',
  `os` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '终端系统',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '逻辑删除标识(1-已删除 0-未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_create_time`(`create_time`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 427 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES (1, 'LOGIN', 'POST', 'admin 123456', NULL, '登录', '/api/v1/auth/login', NULL, '172.18.0.1', '0', '内网IP', 484, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-08-31 19:40:10', 0);
INSERT INTO `sys_log` VALUES (2, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '172.18.0.1', '0', '内网IP', 53, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-08-31 19:42:07', 0);
INSERT INTO `sys_log` VALUES (3, 'LOGIN', 'POST', 'admin 123456', NULL, '登录', '/api/v1/auth/login', NULL, '172.18.0.1', '0', '内网IP', 55, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-08-31 19:42:16', 0);
INSERT INTO `sys_log` VALUES (4, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '172.18.0.1', '0', '内网IP', 13, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-08-31 19:42:16', 0);
INSERT INTO `sys_log` VALUES (5, 'USER', 'GET', '{\"isRoot\":false,\"pageNum\":1,\"pageSize\":10}', NULL, '用户分页列表', '/api/v1/users/page', NULL, '172.18.0.1', '0', '内网IP', 17, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-08-31 19:42:29', 0);
INSERT INTO `sys_log` VALUES (6, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '172.18.0.1', '0', '内网IP', 15, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-08-31 19:42:30', 0);
INSERT INTO `sys_log` VALUES (7, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 29, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-08-31 19:42:32', 0);
INSERT INTO `sys_log` VALUES (8, 'DEPT', 'GET', '{}', NULL, '部门列表', '/api/v1/dept', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-08-31 19:43:38', 0);
INSERT INTO `sys_log` VALUES (9, 'LOGIN', 'POST', 'admin 123456', NULL, '登录', '/api/v1/auth/login', NULL, '172.18.0.1', '0', '内网IP', 55, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-08-31 19:58:03', 0);
INSERT INTO `sys_log` VALUES (10, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-08-31 19:58:03', 0);
INSERT INTO `sys_log` VALUES (11, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '172.18.0.1', '0', '内网IP', 6, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-08-31 19:58:19', 0);
INSERT INTO `sys_log` VALUES (12, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 7, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-08-31 19:58:20', 0);
INSERT INTO `sys_log` VALUES (13, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '172.18.0.1', '0', '内网IP', 51, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-08-31 20:02:23', 0);
INSERT INTO `sys_log` VALUES (14, 'LOGIN', 'POST', 'admin 123456', NULL, '登录', '/api/v1/auth/login', NULL, '172.18.0.1', '0', '内网IP', 52, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-08-31 20:02:38', 0);
INSERT INTO `sys_log` VALUES (15, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-08-31 20:02:38', 0);
INSERT INTO `sys_log` VALUES (16, 'USER', 'GET', '{\"isRoot\":false,\"pageNum\":1,\"pageSize\":10}', NULL, '用户分页列表', '/api/v1/users/page', NULL, '172.18.0.1', '0', '内网IP', 7, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-08-31 20:02:44', 0);
INSERT INTO `sys_log` VALUES (17, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-08-31 20:02:47', 0);
INSERT INTO `sys_log` VALUES (18, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 6, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-08-31 20:02:49', 0);
INSERT INTO `sys_log` VALUES (19, 'DEPT', 'GET', '{}', NULL, '部门列表', '/api/v1/dept', NULL, '172.18.0.1', '0', '内网IP', 2, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-08-31 20:02:50', 0);
INSERT INTO `sys_log` VALUES (20, 'DICT', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '字典分页列表', '/api/v1/dicts/page', NULL, '172.18.0.1', '0', '内网IP', 6, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-08-31 20:02:51', 0);
INSERT INTO `sys_log` VALUES (21, 'SETTING', 'GET', '{\"keywords\":\"\",\"pageNum\":1,\"pageSize\":10}', NULL, '系统配置分页列表', '/api/v1/config/page', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-08-31 20:02:53', 0);
INSERT INTO `sys_log` VALUES (22, 'USER', 'GET', '', NULL, '获取个人中心用户信息', '/api/v1/users/profile', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-08-31 20:03:45', 0);
INSERT INTO `sys_log` VALUES (23, 'USER', 'GET', '{\"isRoot\":false,\"pageNum\":1,\"pageSize\":10}', NULL, '用户分页列表', '/api/v1/users/page', NULL, '172.18.0.1', '0', '内网IP', 6, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-08-31 20:04:51', 0);
INSERT INTO `sys_log` VALUES (24, 'USER', 'POST', '{\"username\":\"aioveu\",\"nickname\":\"可我不敌可爱\",\"mobile\":\"13061656199\",\"gender\":1,\"email\":\"ambitiouschild@qq.com\",\"status\":1,\"deptId\":1,\"roleIds\":[2]}', NULL, '新增用户', '/api/v1/users', NULL, '172.18.0.1', '0', '内网IP', 406, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-08-31 20:05:27', 0);
INSERT INTO `sys_log` VALUES (25, 'USER', 'GET', '{\"isRoot\":false,\"pageNum\":1,\"pageSize\":10}', NULL, '用户分页列表', '/api/v1/users/page', NULL, '172.18.0.1', '0', '内网IP', 6, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-08-31 20:05:27', 0);
INSERT INTO `sys_log` VALUES (26, 'LOGIN', 'DELETE', '{}', NULL, '注销', '/api/v1/auth/logout', NULL, '172.18.0.1', '0', '内网IP', 0, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-08-31 20:05:32', 0);
INSERT INTO `sys_log` VALUES (27, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '172.18.0.1', '0', '内网IP', 58, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-08-31 20:05:37', 0);
INSERT INTO `sys_log` VALUES (28, 'LOGIN', 'POST', 'aioveu 123456', NULL, '登录', '/api/v1/auth/login', NULL, '172.18.0.1', '0', '内网IP', 54, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-08-31 20:05:45', 0);
INSERT INTO `sys_log` VALUES (29, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:05:45', 0);
INSERT INTO `sys_log` VALUES (30, 'USER', 'GET', '{\"isRoot\":false,\"pageNum\":1,\"pageSize\":10}', NULL, '用户分页列表', '/api/v1/users/page', NULL, '172.18.0.1', '0', '内网IP', 7, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:05:45', 0);
INSERT INTO `sys_log` VALUES (31, 'LOGIN', 'DELETE', '{}', NULL, '注销', '/api/v1/auth/logout', NULL, '172.18.0.1', '0', '内网IP', 0, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:05:58', 0);
INSERT INTO `sys_log` VALUES (32, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '172.18.0.1', '0', '内网IP', 51, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-08-31 20:06:02', 0);
INSERT INTO `sys_log` VALUES (33, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:06:02', 0);
INSERT INTO `sys_log` VALUES (34, 'USER', 'GET', '{\"isRoot\":false,\"pageNum\":1,\"pageSize\":10}', NULL, '用户分页列表', '/api/v1/users/page', NULL, '172.18.0.1', '0', '内网IP', 6, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:06:02', 0);
INSERT INTO `sys_log` VALUES (35, 'USER', 'GET', '', NULL, '获取个人中心用户信息', '/api/v1/users/profile', NULL, '172.18.0.1', '0', '内网IP', 2, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:06:05', 0);
INSERT INTO `sys_log` VALUES (36, 'USER', 'PUT', '{\"avatar\":\"https://minio.aioveu.com/aioveu/20250831/ed8021fddd6943fd805e414323deaae9.png\"}', NULL, '个人中心修改用户信息', '/api/v1/users/profile', NULL, '172.18.0.1', '0', '内网IP', 81, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:06:16', 0);
INSERT INTO `sys_log` VALUES (37, 'USER', 'GET', '', NULL, '获取个人中心用户信息', '/api/v1/users/profile', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:06:18', 0);
INSERT INTO `sys_log` VALUES (38, 'USER', 'GET', '', NULL, '获取个人中心用户信息', '/api/v1/users/profile', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:06:35', 0);
INSERT INTO `sys_log` VALUES (39, 'USER', 'GET', '', NULL, '获取个人中心用户信息', '/api/v1/users/profile', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:06:36', 0);
INSERT INTO `sys_log` VALUES (40, 'USER', 'GET', '', NULL, '获取个人中心用户信息', '/api/v1/users/profile', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:06:37', 0);
INSERT INTO `sys_log` VALUES (41, 'LOGIN', 'DELETE', '{}', NULL, '注销', '/api/v1/auth/logout', NULL, '172.18.0.1', '0', '内网IP', 1, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:06:39', 0);
INSERT INTO `sys_log` VALUES (42, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '172.18.0.1', '0', '内网IP', 51, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-08-31 20:06:42', 0);
INSERT INTO `sys_log` VALUES (43, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '172.18.0.1', '0', '内网IP', 2, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:06:43', 0);
INSERT INTO `sys_log` VALUES (44, 'USER', 'GET', '', NULL, '获取个人中心用户信息', '/api/v1/users/profile', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:06:43', 0);
INSERT INTO `sys_log` VALUES (45, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '172.18.0.1', '0', '内网IP', 51, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-08-31 20:07:06', 0);
INSERT INTO `sys_log` VALUES (46, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:07:06', 0);
INSERT INTO `sys_log` VALUES (47, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 6, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:07:42', 0);
INSERT INTO `sys_log` VALUES (48, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 33, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:14:24', 0);
INSERT INTO `sys_log` VALUES (49, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 23, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:18:29', 0);
INSERT INTO `sys_log` VALUES (50, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 7, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:22:10', 0);
INSERT INTO `sys_log` VALUES (51, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 10, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:22:47', 0);
INSERT INTO `sys_log` VALUES (52, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:23:08', 0);
INSERT INTO `sys_log` VALUES (53, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 7, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:23:42', 0);
INSERT INTO `sys_log` VALUES (54, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:23:54', 0);
INSERT INTO `sys_log` VALUES (55, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:26:08', 0);
INSERT INTO `sys_log` VALUES (56, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '172.18.0.1', '0', '内网IP', 61, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:26:11', 0);
INSERT INTO `sys_log` VALUES (57, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '172.18.0.1', '0', '内网IP', 7, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:26:18', 0);
INSERT INTO `sys_log` VALUES (58, 'LOGIN', 'DELETE', '{}', NULL, '注销', '/api/v1/auth/logout', NULL, '172.18.0.1', '0', '内网IP', 58, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:26:21', 0);
INSERT INTO `sys_log` VALUES (59, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '172.18.0.1', '0', '内网IP', 165, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-08-31 20:26:25', 0);
INSERT INTO `sys_log` VALUES (60, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '172.18.0.1', '0', '内网IP', 26, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:26:25', 0);
INSERT INTO `sys_log` VALUES (61, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:26:26', 0);
INSERT INTO `sys_log` VALUES (62, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 6, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:26:40', 0);
INSERT INTO `sys_log` VALUES (63, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 6, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:27:13', 0);
INSERT INTO `sys_log` VALUES (64, 'LOGIN', 'DELETE', '{}', NULL, '注销', '/api/v1/auth/logout', NULL, '172.18.0.1', '0', '内网IP', 1, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:27:16', 0);
INSERT INTO `sys_log` VALUES (65, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '172.18.0.1', '0', '内网IP', 52, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-08-31 20:27:19', 0);
INSERT INTO `sys_log` VALUES (66, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:27:19', 0);
INSERT INTO `sys_log` VALUES (67, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:27:19', 0);
INSERT INTO `sys_log` VALUES (68, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '172.18.0.1', '0', '内网IP', 52, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-08-31 20:27:34', 0);
INSERT INTO `sys_log` VALUES (69, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:27:34', 0);
INSERT INTO `sys_log` VALUES (70, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:27:51', 0);
INSERT INTO `sys_log` VALUES (71, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:28:37', 0);
INSERT INTO `sys_log` VALUES (72, 'LOGIN', 'DELETE', '{}', NULL, '注销', '/api/v1/auth/logout', NULL, '172.18.0.1', '0', '内网IP', 0, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:28:48', 0);
INSERT INTO `sys_log` VALUES (73, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '172.18.0.1', '0', '内网IP', 52, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-08-31 20:28:50', 0);
INSERT INTO `sys_log` VALUES (74, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:28:50', 0);
INSERT INTO `sys_log` VALUES (75, 'USER', 'GET', '{\"isRoot\":false,\"pageNum\":1,\"pageSize\":10}', NULL, '用户分页列表', '/api/v1/users/page', NULL, '172.18.0.1', '0', '内网IP', 81, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:28:54', 0);
INSERT INTO `sys_log` VALUES (76, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:29:01', 0);
INSERT INTO `sys_log` VALUES (77, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 6, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:29:02', 0);
INSERT INTO `sys_log` VALUES (78, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 7, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:29:20', 0);
INSERT INTO `sys_log` VALUES (79, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:29:36', 0);
INSERT INTO `sys_log` VALUES (80, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:34:13', 0);
INSERT INTO `sys_log` VALUES (81, 'LOGIN', 'DELETE', '{}', NULL, '注销', '/api/v1/auth/logout', NULL, '172.18.0.1', '0', '内网IP', 0, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:34:16', 0);
INSERT INTO `sys_log` VALUES (82, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '172.18.0.1', '0', '内网IP', 56, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-08-31 20:34:18', 0);
INSERT INTO `sys_log` VALUES (83, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:34:19', 0);
INSERT INTO `sys_log` VALUES (84, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:34:19', 0);
INSERT INTO `sys_log` VALUES (85, 'USER', 'GET', '{\"isRoot\":false,\"pageNum\":1,\"pageSize\":10}', NULL, '用户分页列表', '/api/v1/users/page', NULL, '172.18.0.1', '0', '内网IP', 7, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:34:29', 0);
INSERT INTO `sys_log` VALUES (86, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:34:53', 0);
INSERT INTO `sys_log` VALUES (87, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 7, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:35:49', 0);
INSERT INTO `sys_log` VALUES (88, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:35:50', 0);
INSERT INTO `sys_log` VALUES (89, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:36:04', 0);
INSERT INTO `sys_log` VALUES (90, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:36:16', 0);
INSERT INTO `sys_log` VALUES (91, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 6, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:37:19', 0);
INSERT INTO `sys_log` VALUES (92, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:37:39', 0);
INSERT INTO `sys_log` VALUES (93, 'USER', 'GET', '{\"isRoot\":false,\"pageNum\":1,\"pageSize\":10}', NULL, '用户分页列表', '/api/v1/users/page', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:38:28', 0);
INSERT INTO `sys_log` VALUES (94, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:38:39', 0);
INSERT INTO `sys_log` VALUES (95, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 6, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:39:53', 0);
INSERT INTO `sys_log` VALUES (96, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '172.18.0.1', '0', '内网IP', 52, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-08-31 20:40:06', 0);
INSERT INTO `sys_log` VALUES (97, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:40:07', 0);
INSERT INTO `sys_log` VALUES (98, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '172.18.0.1', '0', '内网IP', 51, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-08-31 20:40:35', 0);
INSERT INTO `sys_log` VALUES (99, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:40:35', 0);
INSERT INTO `sys_log` VALUES (100, 'USER', 'GET', '', NULL, '获取个人中心用户信息', '/api/v1/users/profile', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-08-31 20:42:17', 0);
INSERT INTO `sys_log` VALUES (101, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-08-31 20:44:34', 0);
INSERT INTO `sys_log` VALUES (102, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 6, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-08-31 20:44:43', 0);
INSERT INTO `sys_log` VALUES (103, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-08-31 20:44:45', 0);
INSERT INTO `sys_log` VALUES (104, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-08-31 20:46:38', 0);
INSERT INTO `sys_log` VALUES (105, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-08-31 20:46:41', 0);
INSERT INTO `sys_log` VALUES (106, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 6, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-08-31 20:51:22', 0);
INSERT INTO `sys_log` VALUES (107, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-08-31 20:52:02', 0);
INSERT INTO `sys_log` VALUES (108, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-08-31 20:53:01', 0);
INSERT INTO `sys_log` VALUES (109, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-08-31 20:53:19', 0);
INSERT INTO `sys_log` VALUES (110, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-08-31 20:53:51', 0);
INSERT INTO `sys_log` VALUES (111, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-08-31 20:54:09', 0);
INSERT INTO `sys_log` VALUES (112, 'USER', 'GET', '{\"isRoot\":false,\"pageNum\":1,\"pageSize\":10}', NULL, '用户分页列表', '/api/v1/users/page', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-08-31 20:54:12', 0);
INSERT INTO `sys_log` VALUES (113, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-08-31 20:54:12', 0);
INSERT INTO `sys_log` VALUES (114, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-08-31 20:54:18', 0);
INSERT INTO `sys_log` VALUES (115, 'LOGIN', 'DELETE', '{}', NULL, '注销', '/api/v1/auth/logout', NULL, '172.18.0.1', '0', '内网IP', 0, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-08-31 20:54:32', 0);
INSERT INTO `sys_log` VALUES (116, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '172.18.0.1', '0', '内网IP', 52, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-08-31 20:54:35', 0);
INSERT INTO `sys_log` VALUES (117, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:54:35', 0);
INSERT INTO `sys_log` VALUES (118, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:54:36', 0);
INSERT INTO `sys_log` VALUES (119, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:54:53', 0);
INSERT INTO `sys_log` VALUES (120, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:56:41', 0);
INSERT INTO `sys_log` VALUES (121, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:57:00', 0);
INSERT INTO `sys_log` VALUES (122, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:57:10', 0);
INSERT INTO `sys_log` VALUES (123, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:57:20', 0);
INSERT INTO `sys_log` VALUES (124, 'USER', 'GET', '{\"isRoot\":false,\"pageNum\":1,\"pageSize\":10}', NULL, '用户分页列表', '/api/v1/users/page', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:57:23', 0);
INSERT INTO `sys_log` VALUES (125, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:57:26', 0);
INSERT INTO `sys_log` VALUES (126, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:57:32', 0);
INSERT INTO `sys_log` VALUES (127, 'LOGIN', 'DELETE', '{}', NULL, '注销', '/api/v1/auth/logout', NULL, '172.18.0.1', '0', '内网IP', 0, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:57:34', 0);
INSERT INTO `sys_log` VALUES (128, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '172.18.0.1', '0', '内网IP', 50, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-08-31 20:57:37', 0);
INSERT INTO `sys_log` VALUES (129, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '172.18.0.1', '0', '内网IP', 2, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:57:37', 0);
INSERT INTO `sys_log` VALUES (130, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:57:38', 0);
INSERT INTO `sys_log` VALUES (131, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:58:08', 0);
INSERT INTO `sys_log` VALUES (132, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:58:10', 0);
INSERT INTO `sys_log` VALUES (133, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:58:34', 0);
INSERT INTO `sys_log` VALUES (134, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:58:47', 0);
INSERT INTO `sys_log` VALUES (135, 'LOGIN', 'DELETE', '{}', NULL, '注销', '/api/v1/auth/logout', NULL, '172.18.0.1', '0', '内网IP', 0, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:58:57', 0);
INSERT INTO `sys_log` VALUES (136, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '172.18.0.1', '0', '内网IP', 51, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-08-31 20:59:06', 0);
INSERT INTO `sys_log` VALUES (137, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '172.18.0.1', '0', '内网IP', 2, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:59:06', 0);
INSERT INTO `sys_log` VALUES (138, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:59:30', 0);
INSERT INTO `sys_log` VALUES (139, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:59:43', 0);
INSERT INTO `sys_log` VALUES (140, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 20:59:52', 0);
INSERT INTO `sys_log` VALUES (141, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 21:00:06', 0);
INSERT INTO `sys_log` VALUES (142, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 21:00:12', 0);
INSERT INTO `sys_log` VALUES (143, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 21:00:18', 0);
INSERT INTO `sys_log` VALUES (144, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 21:00:24', 0);
INSERT INTO `sys_log` VALUES (145, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 21:00:30', 0);
INSERT INTO `sys_log` VALUES (146, 'USER', 'GET', '{\"isRoot\":false,\"pageNum\":1,\"pageSize\":10}', NULL, '用户分页列表', '/api/v1/users/page', NULL, '172.18.0.1', '0', '内网IP', 6, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 21:00:44', 0);
INSERT INTO `sys_log` VALUES (147, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 21:00:46', 0);
INSERT INTO `sys_log` VALUES (148, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 21:00:54', 0);
INSERT INTO `sys_log` VALUES (149, 'LOGIN', 'DELETE', '{}', NULL, '注销', '/api/v1/auth/logout', NULL, '172.18.0.1', '0', '内网IP', 0, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 21:00:57', 0);
INSERT INTO `sys_log` VALUES (150, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '172.18.0.1', '0', '内网IP', 52, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-08-31 21:01:00', 0);
INSERT INTO `sys_log` VALUES (151, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '172.18.0.1', '0', '内网IP', 2, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 21:01:00', 0);
INSERT INTO `sys_log` VALUES (152, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '172.18.0.1', '0', '内网IP', 2, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 21:01:00', 0);
INSERT INTO `sys_log` VALUES (153, 'DEPT', 'GET', '{}', NULL, '部门列表', '/api/v1/dept', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 21:27:47', 0);
INSERT INTO `sys_log` VALUES (154, 'DICT', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '字典分页列表', '/api/v1/dicts/page', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 21:27:48', 0);
INSERT INTO `sys_log` VALUES (155, 'DICT', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '字典分页列表', '/api/v1/dicts/page', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 21:28:43', 0);
INSERT INTO `sys_log` VALUES (156, 'DICT', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '字典分页列表', '/api/v1/dicts/page', NULL, '172.18.0.1', '0', '内网IP', 2, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 21:30:10', 0);
INSERT INTO `sys_log` VALUES (157, 'DICT', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '字典分页列表', '/api/v1/dicts/page', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 21:31:41', 0);
INSERT INTO `sys_log` VALUES (158, 'DICT', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '字典分页列表', '/api/v1/dicts/page', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 21:34:28', 0);
INSERT INTO `sys_log` VALUES (159, 'DICT', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '字典分页列表', '/api/v1/dicts/page', NULL, '172.18.0.1', '0', '内网IP', 2, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 21:36:02', 0);
INSERT INTO `sys_log` VALUES (160, 'DICT', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '字典分页列表', '/api/v1/dicts/page', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 21:38:06', 0);
INSERT INTO `sys_log` VALUES (161, 'DICT', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '字典分页列表', '/api/v1/dicts/page', NULL, '172.18.0.1', '0', '内网IP', 2, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 21:39:01', 0);
INSERT INTO `sys_log` VALUES (162, 'DICT', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '字典分页列表', '/api/v1/dicts/page', NULL, '172.18.0.1', '0', '内网IP', 2, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 21:39:43', 0);
INSERT INTO `sys_log` VALUES (163, 'DICT', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '字典分页列表', '/api/v1/dicts/page', NULL, '172.18.0.1', '0', '内网IP', 2, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 21:41:46', 0);
INSERT INTO `sys_log` VALUES (164, 'DICT', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '字典分页列表', '/api/v1/dicts/page', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 21:42:50', 0);
INSERT INTO `sys_log` VALUES (165, 'DICT', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '字典分页列表', '/api/v1/dicts/page', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 21:43:46', 0);
INSERT INTO `sys_log` VALUES (166, 'DICT', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '字典分页列表', '/api/v1/dicts/page', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 21:45:20', 0);
INSERT INTO `sys_log` VALUES (167, 'DICT', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '字典分页列表', '/api/v1/dicts/page', NULL, '172.18.0.1', '0', '内网IP', 2, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 21:46:13', 0);
INSERT INTO `sys_log` VALUES (168, 'DICT', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '字典分页列表', '/api/v1/dicts/page', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 21:47:19', 0);
INSERT INTO `sys_log` VALUES (169, 'DICT', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '字典分页列表', '/api/v1/dicts/page', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 21:48:31', 0);
INSERT INTO `sys_log` VALUES (170, 'DICT', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '字典分页列表', '/api/v1/dicts/page', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 21:49:24', 0);
INSERT INTO `sys_log` VALUES (171, 'DICT', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '字典分页列表', '/api/v1/dicts/page', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 21:50:27', 0);
INSERT INTO `sys_log` VALUES (172, 'DICT', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '字典分页列表', '/api/v1/dicts/page', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 21:51:03', 0);
INSERT INTO `sys_log` VALUES (173, 'DICT', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '字典分页列表', '/api/v1/dicts/page', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 21:53:55', 0);
INSERT INTO `sys_log` VALUES (174, 'DICT', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '字典分页列表', '/api/v1/dicts/page', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 21:54:47', 0);
INSERT INTO `sys_log` VALUES (175, 'DICT', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '字典分页列表', '/api/v1/dicts/page', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 21:55:53', 0);
INSERT INTO `sys_log` VALUES (176, 'DICT', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '字典分页列表', '/api/v1/dicts/page', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 21:56:43', 0);
INSERT INTO `sys_log` VALUES (177, 'DICT', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '字典分页列表', '/api/v1/dicts/page', NULL, '172.18.0.1', '0', '内网IP', 2, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 21:57:54', 0);
INSERT INTO `sys_log` VALUES (178, 'DICT', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '字典分页列表', '/api/v1/dicts/page', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 21:58:35', 0);
INSERT INTO `sys_log` VALUES (179, 'DICT', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '字典分页列表', '/api/v1/dicts/page', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 22:00:01', 0);
INSERT INTO `sys_log` VALUES (180, 'DICT', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '字典分页列表', '/api/v1/dicts/page', NULL, '172.18.0.1', '0', '内网IP', 2, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 22:00:57', 0);
INSERT INTO `sys_log` VALUES (181, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 22:05:24', 0);
INSERT INTO `sys_log` VALUES (182, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 22:05:56', 0);
INSERT INTO `sys_log` VALUES (183, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 22:06:15', 0);
INSERT INTO `sys_log` VALUES (184, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 22:06:27', 0);
INSERT INTO `sys_log` VALUES (185, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 7, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 22:06:37', 0);
INSERT INTO `sys_log` VALUES (186, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 22:06:48', 0);
INSERT INTO `sys_log` VALUES (187, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 22:07:03', 0);
INSERT INTO `sys_log` VALUES (188, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 22:07:14', 0);
INSERT INTO `sys_log` VALUES (189, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 22:07:25', 0);
INSERT INTO `sys_log` VALUES (190, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 22:07:38', 0);
INSERT INTO `sys_log` VALUES (191, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 22:07:52', 0);
INSERT INTO `sys_log` VALUES (192, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 6, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 22:08:04', 0);
INSERT INTO `sys_log` VALUES (193, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 22:08:19', 0);
INSERT INTO `sys_log` VALUES (194, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 22:08:29', 0);
INSERT INTO `sys_log` VALUES (195, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 22:08:42', 0);
INSERT INTO `sys_log` VALUES (196, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 22:08:52', 0);
INSERT INTO `sys_log` VALUES (197, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 22:09:26', 0);
INSERT INTO `sys_log` VALUES (198, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 22:09:43', 0);
INSERT INTO `sys_log` VALUES (199, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 6, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 22:09:52', 0);
INSERT INTO `sys_log` VALUES (200, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 22:10:03', 0);
INSERT INTO `sys_log` VALUES (201, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 22:10:14', 0);
INSERT INTO `sys_log` VALUES (202, 'LOGIN', 'DELETE', '{}', NULL, '注销', '/api/v1/auth/logout', NULL, '172.18.0.1', '0', '内网IP', 0, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 22:10:16', 0);
INSERT INTO `sys_log` VALUES (203, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '172.18.0.1', '0', '内网IP', 51, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-08-31 22:10:19', 0);
INSERT INTO `sys_log` VALUES (204, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 22:10:19', 0);
INSERT INTO `sys_log` VALUES (205, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 22:10:20', 0);
INSERT INTO `sys_log` VALUES (206, 'LOGIN', 'DELETE', '{}', NULL, '注销', '/api/v1/auth/logout', NULL, '172.18.0.1', '0', '内网IP', 0, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 22:29:05', 0);
INSERT INTO `sys_log` VALUES (207, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '172.18.0.1', '0', '内网IP', 51, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-08-31 22:29:10', 0);
INSERT INTO `sys_log` VALUES (208, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '172.18.0.1', '0', '内网IP', 2, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 22:29:10', 0);
INSERT INTO `sys_log` VALUES (209, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 8, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 22:57:23', 0);
INSERT INTO `sys_log` VALUES (210, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 22:58:34', 0);
INSERT INTO `sys_log` VALUES (211, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 22:58:59', 0);
INSERT INTO `sys_log` VALUES (212, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 6, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 22:59:18', 0);
INSERT INTO `sys_log` VALUES (213, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 22:59:37', 0);
INSERT INTO `sys_log` VALUES (214, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 22:59:53', 0);
INSERT INTO `sys_log` VALUES (215, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:00:12', 0);
INSERT INTO `sys_log` VALUES (216, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:00:30', 0);
INSERT INTO `sys_log` VALUES (217, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:00:46', 0);
INSERT INTO `sys_log` VALUES (218, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:01:01', 0);
INSERT INTO `sys_log` VALUES (219, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:01:26', 0);
INSERT INTO `sys_log` VALUES (220, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:01:39', 0);
INSERT INTO `sys_log` VALUES (221, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:01:55', 0);
INSERT INTO `sys_log` VALUES (222, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 6, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:02:06', 0);
INSERT INTO `sys_log` VALUES (223, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:02:20', 0);
INSERT INTO `sys_log` VALUES (224, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:02:34', 0);
INSERT INTO `sys_log` VALUES (225, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:02:47', 0);
INSERT INTO `sys_log` VALUES (226, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:02:58', 0);
INSERT INTO `sys_log` VALUES (227, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:03:08', 0);
INSERT INTO `sys_log` VALUES (228, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 6, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:03:19', 0);
INSERT INTO `sys_log` VALUES (229, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:03:33', 0);
INSERT INTO `sys_log` VALUES (230, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:04:13', 0);
INSERT INTO `sys_log` VALUES (231, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:05:06', 0);
INSERT INTO `sys_log` VALUES (232, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:05:16', 0);
INSERT INTO `sys_log` VALUES (233, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:05:24', 0);
INSERT INTO `sys_log` VALUES (234, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 6, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:05:33', 0);
INSERT INTO `sys_log` VALUES (235, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:05:45', 0);
INSERT INTO `sys_log` VALUES (236, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:06:01', 0);
INSERT INTO `sys_log` VALUES (237, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:06:12', 0);
INSERT INTO `sys_log` VALUES (238, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:06:26', 0);
INSERT INTO `sys_log` VALUES (239, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:06:40', 0);
INSERT INTO `sys_log` VALUES (240, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:06:50', 0);
INSERT INTO `sys_log` VALUES (241, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:07:00', 0);
INSERT INTO `sys_log` VALUES (242, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:07:09', 0);
INSERT INTO `sys_log` VALUES (243, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:07:24', 0);
INSERT INTO `sys_log` VALUES (244, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 20, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:07:35', 0);
INSERT INTO `sys_log` VALUES (245, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:07:46', 0);
INSERT INTO `sys_log` VALUES (246, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:07:57', 0);
INSERT INTO `sys_log` VALUES (247, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:08:07', 0);
INSERT INTO `sys_log` VALUES (248, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:08:18', 0);
INSERT INTO `sys_log` VALUES (249, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:08:27', 0);
INSERT INTO `sys_log` VALUES (250, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:08:37', 0);
INSERT INTO `sys_log` VALUES (251, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:08:49', 0);
INSERT INTO `sys_log` VALUES (252, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:09:00', 0);
INSERT INTO `sys_log` VALUES (253, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:09:10', 0);
INSERT INTO `sys_log` VALUES (254, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 6, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:09:22', 0);
INSERT INTO `sys_log` VALUES (255, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:10:31', 0);
INSERT INTO `sys_log` VALUES (256, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:10:41', 0);
INSERT INTO `sys_log` VALUES (257, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:11:01', 0);
INSERT INTO `sys_log` VALUES (258, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:11:18', 0);
INSERT INTO `sys_log` VALUES (259, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:11:35', 0);
INSERT INTO `sys_log` VALUES (260, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:11:55', 0);
INSERT INTO `sys_log` VALUES (261, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:12:11', 0);
INSERT INTO `sys_log` VALUES (262, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:12:29', 0);
INSERT INTO `sys_log` VALUES (263, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:12:43', 0);
INSERT INTO `sys_log` VALUES (264, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:12:53', 0);
INSERT INTO `sys_log` VALUES (265, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:13:04', 0);
INSERT INTO `sys_log` VALUES (266, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:13:15', 0);
INSERT INTO `sys_log` VALUES (267, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:13:24', 0);
INSERT INTO `sys_log` VALUES (268, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:13:35', 0);
INSERT INTO `sys_log` VALUES (269, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:13:48', 0);
INSERT INTO `sys_log` VALUES (270, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:13:57', 0);
INSERT INTO `sys_log` VALUES (271, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:14:07', 0);
INSERT INTO `sys_log` VALUES (272, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:14:17', 0);
INSERT INTO `sys_log` VALUES (273, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:14:34', 0);
INSERT INTO `sys_log` VALUES (274, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:14:44', 0);
INSERT INTO `sys_log` VALUES (275, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:14:53', 0);
INSERT INTO `sys_log` VALUES (276, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:15:01', 0);
INSERT INTO `sys_log` VALUES (277, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:15:11', 0);
INSERT INTO `sys_log` VALUES (278, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:15:27', 0);
INSERT INTO `sys_log` VALUES (279, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 9, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:15:37', 0);
INSERT INTO `sys_log` VALUES (280, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:15:47', 0);
INSERT INTO `sys_log` VALUES (281, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:15:58', 0);
INSERT INTO `sys_log` VALUES (282, 'DEPT', 'GET', '{}', NULL, '部门列表', '/api/v1/dept', NULL, '172.18.0.1', '0', '内网IP', 2, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:16:02', 0);
INSERT INTO `sys_log` VALUES (283, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '172.18.0.1', '0', '内网IP', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:16:19', 0);
INSERT INTO `sys_log` VALUES (284, 'LOGIN', 'DELETE', '{}', NULL, '注销', '/api/v1/auth/logout', NULL, '172.18.0.1', '0', '内网IP', 1, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:16:21', 0);
INSERT INTO `sys_log` VALUES (285, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '172.18.0.1', '0', '内网IP', 53, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-08-31 23:16:24', 0);
INSERT INTO `sys_log` VALUES (286, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '172.18.0.1', '0', '内网IP', 2, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:16:25', 0);
INSERT INTO `sys_log` VALUES (287, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:16:25', 0);
INSERT INTO `sys_log` VALUES (288, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 6, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:18:01', 0);
INSERT INTO `sys_log` VALUES (289, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 16, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:19:21', 0);
INSERT INTO `sys_log` VALUES (290, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:20:16', 0);
INSERT INTO `sys_log` VALUES (291, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 6, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:20:22', 0);
INSERT INTO `sys_log` VALUES (292, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:20:28', 0);
INSERT INTO `sys_log` VALUES (293, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:20:34', 0);
INSERT INTO `sys_log` VALUES (294, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 6, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:20:49', 0);
INSERT INTO `sys_log` VALUES (295, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:21:11', 0);
INSERT INTO `sys_log` VALUES (296, 'LOGIN', 'DELETE', '{}', NULL, '注销', '/api/v1/auth/logout', NULL, '172.18.0.1', '0', '内网IP', 0, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:22:16', 0);
INSERT INTO `sys_log` VALUES (297, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '172.18.0.1', '0', '内网IP', 51, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-08-31 23:22:19', 0);
INSERT INTO `sys_log` VALUES (298, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '172.18.0.1', '0', '内网IP', 2, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:22:19', 0);
INSERT INTO `sys_log` VALUES (299, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '172.18.0.1', '0', '内网IP', 51, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-08-31 23:22:19', 0);
INSERT INTO `sys_log` VALUES (300, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '172.18.0.1', '0', '内网IP', 2, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:22:19', 0);
INSERT INTO `sys_log` VALUES (301, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 6, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:23:59', 0);
INSERT INTO `sys_log` VALUES (302, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 6, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:24:09', 0);
INSERT INTO `sys_log` VALUES (303, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:24:17', 0);
INSERT INTO `sys_log` VALUES (304, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:24:22', 0);
INSERT INTO `sys_log` VALUES (305, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:24:26', 0);
INSERT INTO `sys_log` VALUES (306, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 6, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:24:30', 0);
INSERT INTO `sys_log` VALUES (307, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 6, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:25:22', 0);
INSERT INTO `sys_log` VALUES (308, 'LOGIN', 'DELETE', '{}', NULL, '注销', '/api/v1/auth/logout', NULL, '172.18.0.1', '0', '内网IP', 1, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:25:35', 0);
INSERT INTO `sys_log` VALUES (309, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '172.18.0.1', '0', '内网IP', 59, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-08-31 23:25:39', 0);
INSERT INTO `sys_log` VALUES (310, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '172.18.0.1', '0', '内网IP', 2, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:25:39', 0);
INSERT INTO `sys_log` VALUES (311, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 5, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:26:36', 0);
INSERT INTO `sys_log` VALUES (312, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 6, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:26:53', 0);
INSERT INTO `sys_log` VALUES (313, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 6, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:26:55', 0);
INSERT INTO `sys_log` VALUES (314, 'LOGIN', 'DELETE', '{}', NULL, '注销', '/api/v1/auth/logout', NULL, '172.18.0.1', '0', '内网IP', 0, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:27:03', 0);
INSERT INTO `sys_log` VALUES (315, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '172.18.0.1', '0', '内网IP', 50, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-08-31 23:27:05', 0);
INSERT INTO `sys_log` VALUES (316, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '172.18.0.1', '0', '内网IP', 2, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:27:06', 0);
INSERT INTO `sys_log` VALUES (317, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.18.0.1', '0', '内网IP', 4, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:28:44', 0);
INSERT INTO `sys_log` VALUES (318, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '180.173.140.249', '上海', '上海市', 430, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-08-31 23:41:19', 0);
INSERT INTO `sys_log` VALUES (319, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '180.173.140.249', '上海', '上海市', 101, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:41:20', 0);
INSERT INTO `sys_log` VALUES (320, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '180.173.140.249', '上海', '上海市', 225, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-08-31 23:42:23', 0);
INSERT INTO `sys_log` VALUES (321, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '180.173.140.249', '上海', '上海市', 29, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:42:24', 0);
INSERT INTO `sys_log` VALUES (322, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '180.173.140.249', '上海', '上海市', 278, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-08-31 23:43:24', 0);
INSERT INTO `sys_log` VALUES (323, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '180.173.140.249', '上海', '上海市', 57, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:43:24', 0);
INSERT INTO `sys_log` VALUES (324, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '180.173.140.249', '上海', '上海市', 485, 'Safari', '18.6', 'OSX', NULL, '2025-08-31 23:44:22', 0);
INSERT INTO `sys_log` VALUES (325, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '180.173.140.249', '上海', '上海市', 43, 'Safari', '18.6', 'OSX', 4, '2025-08-31 23:44:22', 0);
INSERT INTO `sys_log` VALUES (326, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '172.87.31.202', '加利福尼亚', '0', 174, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:45:20', 0);
INSERT INTO `sys_log` VALUES (327, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '172.87.31.202', '加利福尼亚', '0', 144, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:45:34', 0);
INSERT INTO `sys_log` VALUES (328, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.87.31.202', '加利福尼亚', '0', 375, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:45:37', 0);
INSERT INTO `sys_log` VALUES (329, 'LOGIN', 'DELETE', '{}', NULL, '注销', '/api/v1/auth/logout', NULL, '172.87.31.202', '加利福尼亚', '0', 16, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:45:49', 0);
INSERT INTO `sys_log` VALUES (330, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '172.87.31.202', '加利福尼亚', '0', 319, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-08-31 23:45:57', 0);
INSERT INTO `sys_log` VALUES (331, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '172.87.31.202', '加利福尼亚', '0', 51, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:45:59', 0);
INSERT INTO `sys_log` VALUES (332, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '172.87.31.202', '加利福尼亚', '0', 236, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-08-31 23:46:00', 0);
INSERT INTO `sys_log` VALUES (333, 'LOGIN', 'DELETE', '{}', NULL, '注销', '/api/v1/auth/logout', NULL, '180.173.140.249', '上海', '上海市', 6, 'Safari', '18.6', 'OSX', 4, '2025-08-31 23:46:33', 0);
INSERT INTO `sys_log` VALUES (334, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '180.173.140.249', '上海', '上海市', 389, 'Safari', '18.6', 'OSX', NULL, '2025-08-31 23:46:38', 0);
INSERT INTO `sys_log` VALUES (335, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '180.173.140.249', '上海', '上海市', 65, 'Safari', '18.6', 'OSX', 4, '2025-08-31 23:46:39', 0);
INSERT INTO `sys_log` VALUES (336, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '180.173.140.249', '上海', '上海市', 187, 'Safari', '18.6', 'OSX', NULL, '2025-08-31 23:54:39', 0);
INSERT INTO `sys_log` VALUES (337, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '180.173.140.249', '上海', '上海市', 30, 'Safari', '18.6', 'OSX', 4, '2025-08-31 23:54:39', 0);
INSERT INTO `sys_log` VALUES (338, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '117.136.119.54', '上海', '上海市', 194, 'MicroMessenger', '8.0.61', 'iPhone', NULL, '2025-08-31 23:55:15', 0);
INSERT INTO `sys_log` VALUES (339, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '117.136.119.54', '上海', '上海市', 18, 'MicroMessenger', '8.0.61', 'iPhone', 4, '2025-08-31 23:55:16', 0);
INSERT INTO `sys_log` VALUES (340, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '180.173.140.249', '上海', '上海市', 185, 'Safari', NULL, 'iPad', NULL, '2025-08-31 23:57:51', 0);
INSERT INTO `sys_log` VALUES (341, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '180.173.140.249', '上海', '上海市', 29, 'Safari', NULL, 'iPad', 4, '2025-08-31 23:57:51', 0);
INSERT INTO `sys_log` VALUES (342, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '117.136.119.54', '上海', '上海市', 121, 'MicroMessenger', '8.0.61', 'iPhone', NULL, '2025-09-01 00:00:42', 0);
INSERT INTO `sys_log` VALUES (343, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '117.136.119.54', '上海', '上海市', 19, 'MicroMessenger', '8.0.61', 'iPhone', 4, '2025-09-01 00:00:42', 0);
INSERT INTO `sys_log` VALUES (344, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '117.136.119.54', '上海', '上海市', 138, 'MicroMessenger', '8.0.61', 'iPhone', NULL, '2025-09-01 00:02:12', 0);
INSERT INTO `sys_log` VALUES (345, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '117.136.119.54', '上海', '上海市', 24, 'MicroMessenger', '8.0.61', 'iPhone', 4, '2025-09-01 00:02:12', 0);
INSERT INTO `sys_log` VALUES (346, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '172.87.31.202', '加利福尼亚', '0', 114, 'MicroMessenger', '8.0.61', 'iPhone', NULL, '2025-09-01 00:03:14', 0);
INSERT INTO `sys_log` VALUES (347, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '172.87.31.202', '加利福尼亚', '0', 25, 'MicroMessenger', '8.0.61', 'iPhone', 4, '2025-09-01 00:03:15', 0);
INSERT INTO `sys_log` VALUES (348, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '172.87.31.202', '加利福尼亚', '0', 128, 'MicroMessenger', '8.0.61', 'iPhone', NULL, '2025-09-01 00:06:18', 0);
INSERT INTO `sys_log` VALUES (349, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '172.87.31.202', '加利福尼亚', '0', 19, 'MicroMessenger', '8.0.61', 'iPhone', 4, '2025-09-01 00:06:19', 0);
INSERT INTO `sys_log` VALUES (350, 'USER', 'GET', '{\"isRoot\":false,\"pageNum\":1,\"pageSize\":10}', NULL, '用户分页列表', '/api/v1/users/page', NULL, '172.87.31.202', '加利福尼亚', '0', 99, 'MicroMessenger', '8.0.61', 'iPhone', 4, '2025-09-01 00:06:39', 0);
INSERT INTO `sys_log` VALUES (351, 'LOGIN', 'DELETE', '{}', NULL, '注销', '/api/v1/auth/logout', NULL, '180.173.140.249', '上海', '上海市', 1, 'Safari', NULL, 'iPad', 4, '2025-09-01 00:07:16', 0);
INSERT INTO `sys_log` VALUES (352, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '180.173.140.249', '上海', '上海市', 251, 'Safari', NULL, 'iPad', NULL, '2025-09-01 00:07:23', 0);
INSERT INTO `sys_log` VALUES (353, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '180.173.140.249', '上海', '上海市', 12, 'Safari', NULL, 'iPad', 4, '2025-09-01 00:07:23', 0);
INSERT INTO `sys_log` VALUES (354, 'LOGIN', 'DELETE', '{}', NULL, '注销', '/api/v1/auth/logout', NULL, '180.173.140.249', '上海', '上海市', 2, 'Safari', NULL, 'iPad', 4, '2025-09-01 00:07:48', 0);
INSERT INTO `sys_log` VALUES (355, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '180.173.140.249', '上海', '上海市', 363, 'Safari', NULL, 'iPad', NULL, '2025-09-01 00:07:54', 0);
INSERT INTO `sys_log` VALUES (356, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '180.173.140.249', '上海', '上海市', 25, 'Safari', NULL, 'iPad', 4, '2025-09-01 00:07:54', 0);
INSERT INTO `sys_log` VALUES (357, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '180.173.140.249', '上海', '上海市', 42, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:10:48', 0);
INSERT INTO `sys_log` VALUES (358, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '180.173.140.249', '上海', '上海市', 212, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:10:53', 0);
INSERT INTO `sys_log` VALUES (359, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '180.173.140.249', '上海', '上海市', 217, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:10:57', 0);
INSERT INTO `sys_log` VALUES (360, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '180.173.140.249', '上海', '上海市', 114, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:11:00', 0);
INSERT INTO `sys_log` VALUES (361, 'USER', 'GET', '{\"isRoot\":false,\"pageNum\":1,\"pageSize\":20}', NULL, '用户分页列表', '/api/v1/users/page', NULL, '180.173.140.249', '上海', '上海市', 101, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:11:26', 0);
INSERT INTO `sys_log` VALUES (362, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '172.87.31.202', '加利福尼亚', '0', 103, 'MicroMessenger', '8.0.61', 'iPhone', NULL, '2025-09-01 00:18:21', 0);
INSERT INTO `sys_log` VALUES (363, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '172.87.31.202', '加利福尼亚', '0', 13, 'MicroMessenger', '8.0.61', 'iPhone', 4, '2025-09-01 00:18:22', 0);
INSERT INTO `sys_log` VALUES (364, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '180.173.140.249', '上海', '上海市', 107, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-09-01 00:19:18', 0);
INSERT INTO `sys_log` VALUES (365, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '180.173.140.249', '上海', '上海市', 32, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:19:18', 0);
INSERT INTO `sys_log` VALUES (366, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '180.173.140.249', '上海', '上海市', 70, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:21:13', 0);
INSERT INTO `sys_log` VALUES (367, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '180.173.140.249', '上海', '上海市', 142, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:22:30', 0);
INSERT INTO `sys_log` VALUES (368, 'LOGIN', 'DELETE', '{}', NULL, '注销', '/api/v1/auth/logout', NULL, '180.173.140.249', '上海', '上海市', 3, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:22:39', 0);
INSERT INTO `sys_log` VALUES (369, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '180.173.140.249', '上海', '上海市', 112, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-09-01 00:22:42', 0);
INSERT INTO `sys_log` VALUES (370, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '180.173.140.249', '上海', '上海市', 16, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:22:42', 0);
INSERT INTO `sys_log` VALUES (371, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '180.173.140.249', '上海', '上海市', 110, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:22:42', 0);
INSERT INTO `sys_log` VALUES (372, 'LOGIN', 'DELETE', '{}', NULL, '注销', '/api/v1/auth/logout', NULL, '180.173.140.249', '上海', '上海市', 2, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:23:10', 0);
INSERT INTO `sys_log` VALUES (373, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '180.173.140.249', '上海', '上海市', 104, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-09-01 00:23:16', 0);
INSERT INTO `sys_log` VALUES (374, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '180.173.140.249', '上海', '上海市', 39, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:23:17', 0);
INSERT INTO `sys_log` VALUES (375, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '180.173.140.249', '上海', '上海市', 81, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:23:27', 0);
INSERT INTO `sys_log` VALUES (376, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '180.173.140.249', '上海', '上海市', 30, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:26:56', 0);
INSERT INTO `sys_log` VALUES (377, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '180.173.140.249', '上海', '上海市', 46, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:27:07', 0);
INSERT INTO `sys_log` VALUES (378, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '180.173.140.249', '上海', '上海市', 78, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:28:02', 0);
INSERT INTO `sys_log` VALUES (379, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '180.173.140.249', '上海', '上海市', 102, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:28:14', 0);
INSERT INTO `sys_log` VALUES (380, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '180.173.140.249', '上海', '上海市', 36, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:28:41', 0);
INSERT INTO `sys_log` VALUES (381, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '180.173.140.249', '上海', '上海市', 122, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:28:54', 0);
INSERT INTO `sys_log` VALUES (382, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '180.173.140.249', '上海', '上海市', 33, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:29:20', 0);
INSERT INTO `sys_log` VALUES (383, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '180.173.140.249', '上海', '上海市', 40, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:29:26', 0);
INSERT INTO `sys_log` VALUES (384, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '180.173.140.249', '上海', '上海市', 44, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:30:29', 0);
INSERT INTO `sys_log` VALUES (385, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '180.173.140.249', '上海', '上海市', 109, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-09-01 00:32:01', 0);
INSERT INTO `sys_log` VALUES (386, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '180.173.140.249', '上海', '上海市', 11, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:32:01', 0);
INSERT INTO `sys_log` VALUES (387, 'OTHER', 'GET', '{\"excludeTables\":[\"gen_config\",\"gen_field_config\"],\"pageNum\":1,\"pageSize\":10}', NULL, '代码生成分页列表', '/api/v1/codegen/table/page', NULL, '180.173.140.249', '上海', '上海市', 74, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:32:04', 0);
INSERT INTO `sys_log` VALUES (388, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '180.173.140.249', '上海', '上海市', 131, 'Safari', NULL, 'iPad', NULL, '2025-09-01 00:32:50', 0);
INSERT INTO `sys_log` VALUES (389, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '180.173.140.249', '上海', '上海市', 32, 'Safari', NULL, 'iPad', 4, '2025-09-01 00:32:51', 0);
INSERT INTO `sys_log` VALUES (390, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '180.173.140.249', '上海', '上海市', 103, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-09-01 00:33:27', 0);
INSERT INTO `sys_log` VALUES (391, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '180.173.140.249', '上海', '上海市', 10, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:33:27', 0);
INSERT INTO `sys_log` VALUES (392, 'LOGIN', 'DELETE', '{}', NULL, '注销', '/api/v1/auth/logout', NULL, '180.173.140.249', '上海', '上海市', 1, 'Safari', NULL, 'iPad', 4, '2025-09-01 00:33:54', 0);
INSERT INTO `sys_log` VALUES (393, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '180.173.140.249', '上海', '上海市', 102, 'Safari', NULL, 'iPad', NULL, '2025-09-01 00:33:58', 0);
INSERT INTO `sys_log` VALUES (394, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '180.173.140.249', '上海', '上海市', 12, 'Safari', NULL, 'iPad', 4, '2025-09-01 00:33:58', 0);
INSERT INTO `sys_log` VALUES (395, 'LOGIN', 'DELETE', '{}', NULL, '注销', '/api/v1/auth/logout', NULL, '180.173.140.249', '上海', '上海市', 1, 'Safari', '18.6', 'OSX', NULL, '2025-09-01 00:34:39', 0);
INSERT INTO `sys_log` VALUES (396, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '180.173.140.249', '上海', '上海市', 99, 'Safari', '18.6', 'OSX', NULL, '2025-09-01 00:34:43', 0);
INSERT INTO `sys_log` VALUES (397, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '180.173.140.249', '上海', '上海市', 8, 'Safari', '18.6', 'OSX', 4, '2025-09-01 00:34:43', 0);
INSERT INTO `sys_log` VALUES (398, 'SETTING', 'GET', '{\"keywords\":\"\",\"pageNum\":1,\"pageSize\":10}', NULL, '系统配置分页列表', '/api/v1/config/page', NULL, '180.173.140.249', '上海', '上海市', 33, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:36:19', 0);
INSERT INTO `sys_log` VALUES (399, 'SETTING', 'PUT', '{} {\"id\":1,\"configName\":\"系统限流QPS\",\"configKey\":\"IP_QPS_THRESHOLD_LIMIT\",\"configValue\":\"10000\",\"remark\":\"单个IP请求的最大每秒查询数（QPS）阈值Key\"}', NULL, '修改系统配置', '/api/v1/config/1', NULL, '180.173.140.249', '上海', '上海市', 100, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:36:28', 0);
INSERT INTO `sys_log` VALUES (400, 'SETTING', 'GET', '{\"keywords\":\"\",\"pageNum\":1,\"pageSize\":10}', NULL, '系统配置分页列表', '/api/v1/config/page', NULL, '180.173.140.249', '上海', '上海市', 41, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:36:28', 0);
INSERT INTO `sys_log` VALUES (401, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '172.87.31.202', '加利福尼亚', '0', 103, 'MicroMessenger', '8.0.61', 'iPhone', NULL, '2025-09-01 00:36:32', 0);
INSERT INTO `sys_log` VALUES (402, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '172.87.31.202', '加利福尼亚', '0', 16, 'MicroMessenger', '8.0.61', 'iPhone', 4, '2025-09-01 00:36:32', 0);
INSERT INTO `sys_log` VALUES (403, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '180.173.140.249', '上海', '上海市', 17, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:38:20', 0);
INSERT INTO `sys_log` VALUES (404, 'USER', 'GET', '{\"isRoot\":false,\"pageNum\":1,\"pageSize\":10}', NULL, '用户分页列表', '/api/v1/users/page', NULL, '180.173.140.249', '上海', '上海市', 31, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:38:21', 0);
INSERT INTO `sys_log` VALUES (405, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '180.173.140.249', '上海', '上海市', 112, 'Safari', '18.6', 'OSX', NULL, '2025-09-01 00:38:34', 0);
INSERT INTO `sys_log` VALUES (406, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '180.173.140.249', '上海', '上海市', 14, 'Safari', '18.6', 'OSX', 4, '2025-09-01 00:38:34', 0);
INSERT INTO `sys_log` VALUES (407, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '172.87.31.202', '加利福尼亚', '0', 109, 'MicroMessenger', '8.0.61', 'iPhone', NULL, '2025-09-01 00:44:45', 0);
INSERT INTO `sys_log` VALUES (408, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '172.87.31.202', '加利福尼亚', '0', 15, 'MicroMessenger', '8.0.61', 'iPhone', 4, '2025-09-01 00:44:45', 0);
INSERT INTO `sys_log` VALUES (409, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '180.173.140.249', '上海', '上海市', 32, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:46:38', 0);
INSERT INTO `sys_log` VALUES (410, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '180.173.140.249', '上海', '上海市', 142, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:46:49', 0);
INSERT INTO `sys_log` VALUES (411, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '180.173.140.249', '上海', '上海市', 108, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:46:53', 0);
INSERT INTO `sys_log` VALUES (412, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '180.173.140.249', '上海', '上海市', 45, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:47:03', 0);
INSERT INTO `sys_log` VALUES (413, 'DICT', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '字典分页列表', '/api/v1/dicts/page', NULL, '180.173.140.249', '上海', '上海市', 15, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:48:23', 0);
INSERT INTO `sys_log` VALUES (414, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '180.173.140.249', '上海', '上海市', 54, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:48:49', 0);
INSERT INTO `sys_log` VALUES (415, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '180.173.140.249', '上海', '上海市', 37, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:49:00', 0);
INSERT INTO `sys_log` VALUES (416, 'LOGIN', 'DELETE', '{}', NULL, '注销', '/api/v1/auth/logout', NULL, '180.173.140.249', '上海', '上海市', 7, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:49:09', 0);
INSERT INTO `sys_log` VALUES (417, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '180.173.140.249', '上海', '上海市', 105, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-09-01 00:49:11', 0);
INSERT INTO `sys_log` VALUES (418, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '180.173.140.249', '上海', '上海市', 16, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:49:11', 0);
INSERT INTO `sys_log` VALUES (419, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '180.173.140.249', '上海', '上海市', 72, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:49:11', 0);
INSERT INTO `sys_log` VALUES (420, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '180.173.140.249', '上海', '上海市', 125, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:49:39', 0);
INSERT INTO `sys_log` VALUES (421, 'MENU', 'GET', '{}', NULL, '菜单列表', '/api/v1/menus', NULL, '180.173.140.249', '上海', '上海市', 47, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:50:25', 0);
INSERT INTO `sys_log` VALUES (422, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '172.87.31.202', '加利福尼亚', '0', 98, 'MicroMessenger', '8.0.61', 'iPhone', NULL, '2025-09-01 00:51:17', 0);
INSERT INTO `sys_log` VALUES (423, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '172.87.31.202', '加利福尼亚', '0', 14, 'MicroMessenger', '8.0.61', 'iPhone', 4, '2025-09-01 00:51:18', 0);
INSERT INTO `sys_log` VALUES (424, 'USER', 'GET', '{\"isRoot\":false,\"pageNum\":1,\"pageSize\":10}', NULL, '用户分页列表', '/api/v1/users/page', NULL, '180.173.140.249', '上海', '上海市', 32, 'Chrome', '139.0.0.0', 'Windows 10 or Windows Server 2016', 4, '2025-09-01 00:52:31', 0);
INSERT INTO `sys_log` VALUES (425, 'LOGIN', 'POST', 'aioveu aioveu', NULL, '登录', '/api/v1/auth/login', NULL, '172.87.31.202', '加利福尼亚', '0', 102, 'MicroMessenger', '8.0.61', 'iPhone', NULL, '2025-09-01 00:53:24', 0);
INSERT INTO `sys_log` VALUES (426, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '172.87.31.202', '加利福尼亚', '0', 31, 'MicroMessenger', '8.0.61', 'iPhone', 4, '2025-09-01 00:53:24', 0);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `parent_id` bigint NOT NULL COMMENT '父菜单ID',
  `tree_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '父节点ID路径',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称',
  `type` tinyint NOT NULL COMMENT '菜单类型（1-菜单 2-目录 3-外链 4-按钮）',
  `route_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由名称（Vue Router 中用于命名路由）',
  `route_path` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由路径（Vue Router 中定义的 URL 路径）',
  `component` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组件路径（组件页面完整路径，相对于 src/views/，缺省后缀 .vue）',
  `perm` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '【按钮】权限标识',
  `always_show` tinyint NULL DEFAULT 0 COMMENT '【目录】只有一个子路由是否始终显示（1-是 0-否）',
  `keep_alive` tinyint NULL DEFAULT 0 COMMENT '【菜单】是否开启页面缓存（1-是 0-否）',
  `visible` tinyint(1) NULL DEFAULT 1 COMMENT '显示状态（1-显示 0-隐藏）',
  `sort` int NULL DEFAULT 0 COMMENT '排序',
  `icon` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `redirect` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '跳转路径',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `params` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由参数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 250 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, '0', '系统管理', 2, '', '/system', 'Layout', NULL, NULL, NULL, 1, 5, 'system', '/system/user', '2025-08-31 19:27:28', '2025-08-31 20:23:42', NULL);
INSERT INTO `sys_menu` VALUES (2, 1, '0,1', '用户管理', 1, 'User', 'user', 'system/user/index', NULL, NULL, 1, 1, 1, 'el-icon-User', NULL, '2025-08-31 19:27:28', '2025-08-31 19:27:28', NULL);
INSERT INTO `sys_menu` VALUES (3, 1, '0,1', '角色管理', 1, 'Role', 'role', 'system/role/index', NULL, NULL, 1, 1, 2, 'role', NULL, '2025-08-31 19:27:28', '2025-08-31 19:27:28', NULL);
INSERT INTO `sys_menu` VALUES (4, 1, '0,1', '菜单管理', 1, 'SysMenu', 'menu', 'system/menu/index', NULL, NULL, 1, 1, 3, 'menu', NULL, '2025-08-31 19:27:28', '2025-08-31 19:27:28', NULL);
INSERT INTO `sys_menu` VALUES (5, 1, '0,1', '部门管理', 1, 'Dept', 'dept', 'system/dept/index', NULL, NULL, 1, 1, 4, 'tree', NULL, '2025-08-31 19:27:28', '2025-08-31 19:27:28', NULL);
INSERT INTO `sys_menu` VALUES (6, 1, '0,1', '字典管理', 1, 'Dict', 'dict', 'system/dict/index', NULL, NULL, 1, 1, 5, 'dict', NULL, '2025-08-31 19:27:29', '2025-08-31 19:27:29', NULL);
INSERT INTO `sys_menu` VALUES (20, 0, '0', '多级菜单', 2, NULL, '/multi-level', 'Layout', NULL, 1, NULL, 1, 9, 'cascader', '', '2025-08-31 19:27:29', '2025-08-31 19:27:29', NULL);
INSERT INTO `sys_menu` VALUES (21, 20, '0,20', '菜单一级', 1, NULL, 'multi-level1', 'demo/multi-level/level1', NULL, 1, NULL, 1, 1, '', '', '2025-08-31 19:27:29', '2025-08-31 19:27:29', NULL);
INSERT INTO `sys_menu` VALUES (22, 21, '0,20,21', '菜单二级', 1, NULL, 'multi-level2', 'demo/multi-level/children/level2', NULL, 0, NULL, 1, 1, '', NULL, '2025-08-31 19:27:29', '2025-08-31 19:27:29', NULL);
INSERT INTO `sys_menu` VALUES (23, 22, '0,20,21,22', '菜单三级-1', 1, NULL, 'multi-level3-1', 'demo/multi-level/children/children/level3-1', NULL, 0, 1, 1, 1, '', '', '2025-08-31 19:27:29', '2025-08-31 19:27:29', NULL);
INSERT INTO `sys_menu` VALUES (24, 22, '0,20,21,22', '菜单三级-2', 1, NULL, 'multi-level3-2', 'demo/multi-level/children/children/level3-2', NULL, 0, 1, 1, 2, '', '', '2025-08-31 19:27:29', '2025-08-31 19:27:29', NULL);
INSERT INTO `sys_menu` VALUES (26, 0, '0', '平台文档', 2, '', '/doc', 'Layout', NULL, NULL, NULL, 1, 8, 'document', 'https://juejin.cn/post/7228990409909108793', '2025-08-31 19:27:30', '2025-08-31 19:27:30', NULL);
INSERT INTO `sys_menu` VALUES (30, 26, '0,26', '平台文档(外链)', 1, 'internal-doc-2', 'internal-doc-2', 'demo/internal-doc', NULL, 1, 1, 1, 2, 'document', '', '2025-08-31 19:27:30', '2025-09-01 00:46:49', NULL);
INSERT INTO `sys_menu` VALUES (31, 2, '0,1,2', '用户新增', 4, NULL, '', NULL, 'sys:user:add', NULL, NULL, 1, 1, '', '', '2025-08-31 19:27:30', '2025-08-31 19:27:30', NULL);
INSERT INTO `sys_menu` VALUES (32, 2, '0,1,2', '用户编辑', 4, NULL, '', NULL, 'sys:user:edit', NULL, NULL, 1, 2, '', '', '2025-08-31 19:27:30', '2025-08-31 19:27:30', NULL);
INSERT INTO `sys_menu` VALUES (33, 2, '0,1,2', '用户删除', 4, NULL, '', NULL, 'sys:user:delete', NULL, NULL, 1, 3, '', '', '2025-08-31 19:27:30', '2025-08-31 19:27:30', NULL);
INSERT INTO `sys_menu` VALUES (36, 0, '0', '组件封装', 2, NULL, '/component', 'Layout', NULL, NULL, NULL, 1, 10, 'menu', '', '2025-08-31 19:27:30', '2025-08-31 19:27:30', NULL);
INSERT INTO `sys_menu` VALUES (37, 36, '0,36', '富文本编辑器', 1, NULL, 'wang-editor', 'demo/wang-editor', NULL, NULL, 1, 1, 2, '', '', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (38, 36, '0,36', '图片上传', 1, NULL, 'upload', 'demo/upload', NULL, NULL, 1, 1, 3, '', '', '2025-08-31 19:27:31', '2025-08-31 19:27:31', NULL);
INSERT INTO `sys_menu` VALUES (39, 36, '0,36', '图标选择器', 1, NULL, 'icon-selector', 'demo/icon-selector', NULL, NULL, 1, 1, 4, '', '', '2025-08-31 19:27:31', '2025-08-31 19:27:31', NULL);
INSERT INTO `sys_menu` VALUES (40, 0, '0', '接口文档', 2, NULL, '/api', 'Layout', NULL, 1, NULL, 1, 7, 'api', '', '2025-08-31 19:27:31', '2025-08-31 19:27:31', NULL);
INSERT INTO `sys_menu` VALUES (41, 40, '0,40', 'Apifox', 1, NULL, 'apifox', 'demo/api/apifox', NULL, NULL, 1, 1, 1, 'api', '', '2025-08-31 19:27:31', '2025-08-31 19:27:31', NULL);
INSERT INTO `sys_menu` VALUES (70, 3, '0,1,3', '角色新增', 4, NULL, '', NULL, 'sys:role:add', NULL, NULL, 1, 2, '', NULL, '2025-08-31 19:27:31', '2025-08-31 19:27:31', NULL);
INSERT INTO `sys_menu` VALUES (71, 3, '0,1,3', '角色编辑', 4, NULL, '', NULL, 'sys:role:edit', NULL, NULL, 1, 3, '', NULL, '2025-08-31 19:27:31', '2025-08-31 19:27:31', NULL);
INSERT INTO `sys_menu` VALUES (72, 3, '0,1,3', '角色删除', 4, NULL, '', NULL, 'sys:role:delete', NULL, NULL, 1, 4, '', NULL, '2025-08-31 19:27:31', '2025-08-31 19:27:31', NULL);
INSERT INTO `sys_menu` VALUES (73, 4, '0,1,4', '菜单新增', 4, NULL, '', NULL, 'sys:menu:add', NULL, NULL, 1, 1, '', NULL, '2025-08-31 19:27:31', '2025-08-31 19:27:31', NULL);
INSERT INTO `sys_menu` VALUES (74, 4, '0,1,4', '菜单编辑', 4, NULL, '', NULL, 'sys:menu:edit', NULL, NULL, 1, 3, '', NULL, '2025-08-31 19:27:31', '2025-08-31 19:27:31', NULL);
INSERT INTO `sys_menu` VALUES (75, 4, '0,1,4', '菜单删除', 4, NULL, '', NULL, 'sys:menu:delete', NULL, NULL, 1, 3, '', NULL, '2025-08-31 19:27:31', '2025-08-31 19:27:31', NULL);
INSERT INTO `sys_menu` VALUES (76, 5, '0,1,5', '部门新增', 4, NULL, '', NULL, 'sys:dept:add', NULL, NULL, 1, 1, '', NULL, '2025-08-31 19:27:31', '2025-08-31 19:27:31', NULL);
INSERT INTO `sys_menu` VALUES (77, 5, '0,1,5', '部门编辑', 4, NULL, '', NULL, 'sys:dept:edit', NULL, NULL, 1, 2, '', NULL, '2025-08-31 19:27:32', '2025-08-31 19:27:32', NULL);
INSERT INTO `sys_menu` VALUES (78, 5, '0,1,5', '部门删除', 4, NULL, '', NULL, 'sys:dept:delete', NULL, NULL, 1, 3, '', NULL, '2025-08-31 19:27:32', '2025-08-31 19:27:32', NULL);
INSERT INTO `sys_menu` VALUES (79, 6, '0,1,6', '字典新增', 4, NULL, '', NULL, 'sys:dict:add', NULL, NULL, 1, 1, '', NULL, '2025-08-31 19:27:32', '2025-08-31 19:27:32', NULL);
INSERT INTO `sys_menu` VALUES (81, 6, '0,1,6', '字典编辑', 4, NULL, '', NULL, 'sys:dict:edit', NULL, NULL, 1, 2, '', NULL, '2025-08-31 19:27:32', '2025-08-31 19:27:32', NULL);
INSERT INTO `sys_menu` VALUES (84, 6, '0,1,6', '字典删除', 4, NULL, '', NULL, 'sys:dict:delete', NULL, NULL, 1, 3, '', NULL, '2025-08-31 19:27:32', '2025-08-31 19:27:32', NULL);
INSERT INTO `sys_menu` VALUES (88, 2, '0,1,2', '重置密码', 4, NULL, '', NULL, 'sys:user:reset-password', NULL, NULL, 1, 4, '', NULL, '2025-08-31 19:27:33', '2025-08-31 19:27:33', NULL);
INSERT INTO `sys_menu` VALUES (89, 0, '0', '功能演示', 2, NULL, '/function', 'Layout', NULL, NULL, NULL, 1, 12, 'menu', '', '2025-08-31 19:27:33', '2025-08-31 19:27:33', NULL);
INSERT INTO `sys_menu` VALUES (90, 89, '0,89', 'Websocket', 1, NULL, '/function/websocket', 'demo/websocket', NULL, NULL, 1, 1, 3, '', '', '2025-08-31 19:27:33', '2025-08-31 19:27:33', NULL);
INSERT INTO `sys_menu` VALUES (95, 36, '0,36', '字典组件', 1, NULL, 'dict-demo', 'demo/dictionary', NULL, NULL, 1, 1, 4, '', '', '2025-08-31 19:27:33', '2025-08-31 19:27:33', NULL);
INSERT INTO `sys_menu` VALUES (97, 89, '0,89', 'Icons', 1, NULL, 'icon-demo', 'demo/icons', NULL, NULL, 1, 1, 2, 'el-icon-Notification', '', '2025-08-31 19:27:33', '2025-08-31 19:27:33', NULL);
INSERT INTO `sys_menu` VALUES (102, 26, '0,26', 'document', 1, 'internal-doc', 'internal-doc', 'demo/internal-doc', NULL, NULL, NULL, 1, 1, 'document', '', '2025-08-31 19:27:33', '2025-09-01 00:27:07', NULL);
INSERT INTO `sys_menu` VALUES (105, 2, '0,1,2', '用户查询', 4, NULL, '', NULL, 'sys:user:query', 0, 0, 1, 0, '', NULL, '2025-08-31 19:27:34', '2025-08-31 19:27:34', NULL);
INSERT INTO `sys_menu` VALUES (106, 2, '0,1,2', '用户导入', 4, NULL, '', NULL, 'sys:user:import', NULL, NULL, 1, 5, '', NULL, '2025-08-31 19:27:34', '2025-08-31 19:27:34', NULL);
INSERT INTO `sys_menu` VALUES (107, 2, '0,1,2', '用户导出', 4, NULL, '', NULL, 'sys:user:export', NULL, NULL, 1, 6, '', NULL, '2025-08-31 19:27:34', '2025-08-31 19:27:34', NULL);
INSERT INTO `sys_menu` VALUES (108, 36, '0,36', '增删改查', 1, NULL, 'curd', 'demo/curd/index', NULL, NULL, 1, 1, 0, '', '', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (109, 36, '0,36', '列表选择器', 1, NULL, 'table-select', 'demo/table-select/index', NULL, NULL, 1, 1, 1, '', '', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (110, 0, '0', '路由参数', 2, NULL, '/route-param', 'Layout', NULL, 1, 1, 1, 11, 'el-icon-ElementPlus', NULL, '2025-08-31 19:27:34', '2025-08-31 19:27:34', NULL);
INSERT INTO `sys_menu` VALUES (111, 110, '0,110', '参数(type=1)', 1, NULL, 'route-param-type1', 'demo/route-param', NULL, 0, 1, 1, 1, 'el-icon-Star', NULL, '2025-08-31 19:27:34', '2025-08-31 19:27:34', '{\"type\": \"1\"}');
INSERT INTO `sys_menu` VALUES (112, 110, '0,110', '参数(type=2)', 1, NULL, 'route-param-type2', 'demo/route-param', NULL, 0, 1, 1, 2, 'el-icon-StarFilled', NULL, '2025-08-31 19:27:35', '2025-08-31 19:27:35', '{\"type\": \"2\"}');
INSERT INTO `sys_menu` VALUES (117, 1, '0,1', '系统日志', 1, 'Log', 'log', 'system/log/index', NULL, 0, 1, 1, 6, 'document', NULL, '2025-08-31 19:27:35', '2025-08-31 19:27:35', NULL);
INSERT INTO `sys_menu` VALUES (118, 0, '0', '系统工具', 2, NULL, '/codegen', 'Layout', NULL, 0, 1, 1, 6, 'menu', NULL, '2025-08-31 19:27:35', '2025-08-31 20:23:54', NULL);
INSERT INTO `sys_menu` VALUES (119, 118, '0,118', '代码生成', 1, 'Codegen', 'codegen', 'codegen/index', NULL, 0, 1, 1, 1, 'code', NULL, '2025-08-31 19:27:36', '2025-08-31 19:27:36', NULL);
INSERT INTO `sys_menu` VALUES (120, 1, '0,1', '系统配置', 1, 'Config', 'config', 'system/config/index', NULL, 0, 1, 1, 7, 'setting', NULL, '2025-08-31 19:27:36', '2025-08-31 19:27:36', NULL);
INSERT INTO `sys_menu` VALUES (121, 120, '0,1,120', '系统配置查询', 4, NULL, '', NULL, 'sys:config:query', 0, 1, 1, 1, '', NULL, '2025-08-31 19:27:36', '2025-08-31 19:27:36', NULL);
INSERT INTO `sys_menu` VALUES (122, 120, '0,1,120', '系统配置新增', 4, NULL, '', NULL, 'sys:config:add', 0, 1, 1, 2, '', NULL, '2025-08-31 19:27:36', '2025-08-31 19:27:36', NULL);
INSERT INTO `sys_menu` VALUES (123, 120, '0,1,120', '系统配置修改', 4, NULL, '', NULL, 'sys:config:update', 0, 1, 1, 3, '', NULL, '2025-08-31 19:27:36', '2025-08-31 19:27:36', NULL);
INSERT INTO `sys_menu` VALUES (124, 120, '0,1,120', '系统配置删除', 4, NULL, '', NULL, 'sys:config:delete', 0, 1, 1, 4, '', NULL, '2025-08-31 19:27:36', '2025-08-31 19:27:36', NULL);
INSERT INTO `sys_menu` VALUES (125, 120, '0,1,120', '系统配置刷新', 4, NULL, '', NULL, 'sys:config:refresh', 0, 1, 1, 5, '', NULL, '2025-08-31 19:27:36', '2025-08-31 19:27:36', NULL);
INSERT INTO `sys_menu` VALUES (126, 1, '0,1', '通知公告', 1, 'Notice', 'notice', 'system/notice/index', NULL, NULL, NULL, 1, 9, '', NULL, '2025-08-31 19:27:36', '2025-08-31 19:27:36', NULL);
INSERT INTO `sys_menu` VALUES (127, 126, '0,1,126', '通知查询', 4, NULL, '', NULL, 'sys:notice:query', NULL, NULL, 1, 1, '', NULL, '2025-08-31 19:27:36', '2025-08-31 19:27:36', NULL);
INSERT INTO `sys_menu` VALUES (128, 126, '0,1,126', '通知新增', 4, NULL, '', NULL, 'sys:notice:add', NULL, NULL, 1, 2, '', NULL, '2025-08-31 19:27:36', '2025-08-31 19:27:36', NULL);
INSERT INTO `sys_menu` VALUES (129, 126, '0,1,126', '通知编辑', 4, NULL, '', NULL, 'sys:notice:edit', NULL, NULL, 1, 3, '', NULL, '2025-08-31 19:27:37', '2025-08-31 19:27:37', NULL);
INSERT INTO `sys_menu` VALUES (130, 126, '0,1,126', '通知删除', 4, NULL, '', NULL, 'sys:notice:delete', NULL, NULL, 1, 4, '', NULL, '2025-08-31 19:27:37', '2025-08-31 19:27:37', NULL);
INSERT INTO `sys_menu` VALUES (133, 126, '0,1,126', '通知发布', 4, NULL, '', NULL, 'sys:notice:publish', 0, 1, 1, 5, '', NULL, '2025-08-31 19:27:37', '2025-08-31 19:27:37', NULL);
INSERT INTO `sys_menu` VALUES (134, 126, '0,1,126', '通知撤回', 4, NULL, '', NULL, 'sys:notice:revoke', 0, 1, 1, 6, '', NULL, '2025-08-31 19:27:37', '2025-08-31 19:27:37', NULL);
INSERT INTO `sys_menu` VALUES (135, 1, '0,1', '字典项', 1, 'DictItem', 'dict-item', 'system/dict/dict-item', NULL, 0, 1, 0, 6, '', NULL, '2025-08-31 19:27:37', '2025-08-31 19:27:37', NULL);
INSERT INTO `sys_menu` VALUES (136, 135, '0,1,135', '字典项新增', 4, NULL, '', NULL, 'sys:dict-item:add', NULL, NULL, 1, 2, '', NULL, '2025-08-31 19:27:37', '2025-08-31 19:27:37', NULL);
INSERT INTO `sys_menu` VALUES (137, 135, '0,1,135', '字典项编辑', 4, NULL, '', NULL, 'sys:dict-item:edit', NULL, NULL, 1, 3, '', NULL, '2025-08-31 19:27:37', '2025-08-31 19:27:37', NULL);
INSERT INTO `sys_menu` VALUES (138, 135, '0,1,135', '字典项删除', 4, NULL, '', NULL, 'sys:dict-item:delete', NULL, NULL, 1, 4, '', NULL, '2025-08-31 19:27:37', '2025-08-31 19:27:37', NULL);
INSERT INTO `sys_menu` VALUES (139, 3, '0,1,3', '角色查询', 4, NULL, '', NULL, 'sys:role:query', NULL, NULL, 1, 1, '', NULL, '2025-08-31 19:27:37', '2025-08-31 19:27:37', NULL);
INSERT INTO `sys_menu` VALUES (140, 4, '0,1,4', '菜单查询', 4, NULL, '', NULL, 'sys:menu:query', NULL, NULL, 1, 1, '', NULL, '2025-08-31 19:27:37', '2025-08-31 19:27:37', NULL);
INSERT INTO `sys_menu` VALUES (141, 5, '0,1,5', '部门查询', 4, NULL, '', NULL, 'sys:dept:query', NULL, NULL, 1, 1, '', NULL, '2025-08-31 19:27:37', '2025-08-31 19:27:37', NULL);
INSERT INTO `sys_menu` VALUES (142, 6, '0,1,6', '字典查询', 4, NULL, '', NULL, 'sys:dict:query', NULL, NULL, 1, 1, '', NULL, '2025-08-31 19:27:37', '2025-08-31 19:27:37', NULL);
INSERT INTO `sys_menu` VALUES (143, 135, '0,1,135', '字典项查询', 4, NULL, '', NULL, 'sys:dict-item:query', NULL, NULL, 1, 1, '', NULL, '2025-08-31 19:27:38', '2025-08-31 19:27:38', NULL);
INSERT INTO `sys_menu` VALUES (144, 26, '0,26', '后端文档', 1, 'internal-doc-3', 'internal-doc-3', 'demo/internal-doc', NULL, 1, 1, 1, 3, 'document', '', '2024-10-05 23:36:03', '2025-09-01 00:46:53', NULL);
INSERT INTO `sys_menu` VALUES (145, 26, '0,26', '移动端文档', 3, 'internal-doc-4', 'https://aioveu.com', 'demo/internal-doc-4', NULL, 1, 1, 1, 4, 'document', '', '2024-10-05 23:36:03', '2025-09-01 00:50:25', NULL);
INSERT INTO `sys_menu` VALUES (146, 36, '0,36', '拖拽组件', 1, NULL, 'drag', 'demo/drag', NULL, NULL, NULL, 1, 5, '', '', '2025-03-31 14:14:45', '2025-03-31 14:14:52', NULL);
INSERT INTO `sys_menu` VALUES (147, 36, '0,36', '滚动文本', 1, NULL, 'text-scroll', 'demo/text-scroll', NULL, NULL, NULL, 1, 6, '', '', '2025-03-31 14:14:49', '2025-03-31 14:14:56', NULL);
INSERT INTO `sys_menu` VALUES (148, 89, '0,89', '字典实时同步', 1, NULL, 'dict-sync', 'demo/dict-sync', NULL, NULL, NULL, 1, 3, '', '', '2025-03-31 14:14:49', '2025-03-31 14:14:56', NULL);
INSERT INTO `sys_menu` VALUES (149, 0, '0', '人员管理', 2, NULL, '/aioveuUser', 'Layout', NULL, 1, 1, 1, 1, 'user', NULL, '2025-08-31 20:14:23', '2025-08-31 20:44:43', NULL);
INSERT INTO `sys_menu` VALUES (150, 149, '0,149', '部门管理', 1, 'aioveu_department', 'aioveu_department', 'aioveuDepartment/aioveu-department/index', NULL, 1, 1, 1, 1, 'menu', NULL, '2025-08-31 20:18:28', '2025-08-31 20:59:42', NULL);
INSERT INTO `sys_menu` VALUES (151, 150, '0,149,150', '部门查询', 4, NULL, NULL, NULL, 'aioveuDepartment:aioveu-department:query', 0, 1, 1, 1, NULL, NULL, '2025-08-31 20:22:10', '2025-08-31 20:22:10', NULL);
INSERT INTO `sys_menu` VALUES (152, 150, '0,149,150', '部门新增', 4, NULL, NULL, NULL, 'aioveuDepartment:aioveu-department:add', 0, 1, 1, 2, NULL, NULL, '2025-08-31 20:22:47', '2025-08-31 20:22:47', NULL);
INSERT INTO `sys_menu` VALUES (153, 150, '0,149,150', '部门编辑', 4, NULL, NULL, NULL, 'aioveuDepartment:aioveu-department:edit', 0, 1, 1, 3, NULL, NULL, '2025-08-31 20:23:08', '2025-08-31 20:23:08', NULL);
INSERT INTO `sys_menu` VALUES (154, 150, '0,149,150', '部门删除', 4, NULL, NULL, NULL, 'aioveuDepartment:aioveu-department:delete', 0, 1, 1, 4, NULL, NULL, '2025-08-31 20:26:07', '2025-08-31 20:26:07', NULL);
INSERT INTO `sys_menu` VALUES (155, 149, '0,149', '岗位管理', 1, 'aioveu_position', 'aioveu_position', 'aioveuPosition/aioveu-position/index', NULL, 1, 1, 1, 2, 'menu', NULL, '2025-08-31 20:52:01', '2025-08-31 21:00:06', NULL);
INSERT INTO `sys_menu` VALUES (156, 149, '0,149', '员工管理', 1, 'AioveuEmployee', 'aioveu_employee', 'aioveuEmployee/aioveu-employee/index', NULL, 1, 1, 1, 3, 'menu', NULL, '2025-08-31 20:53:01', '2025-08-31 21:00:12', NULL);
INSERT INTO `sys_menu` VALUES (157, 149, '0,149', '考勤管理', 1, 'aioveu_attendance', 'aioveu_attendance', 'aioveuAttendance/aioveu-attendance/index', NULL, 1, 1, 1, 4, 'menu', NULL, '2025-08-31 20:53:18', '2025-08-31 21:00:17', NULL);
INSERT INTO `sys_menu` VALUES (158, 149, '0,149', '绩效管理', 1, 'aioveu_performance', 'aioveu_performance', 'aioveuPerformance/aioveu-performance/index', NULL, 1, 1, 1, 5, 'menu', NULL, '2025-08-31 20:53:51', '2025-08-31 21:00:23', NULL);
INSERT INTO `sys_menu` VALUES (159, 149, '0,149', '薪资管理', 1, 'aioveu_salary', 'aioveu_salary', 'aioveuSalary/aioveu-salary/index', NULL, 1, 1, 1, 6, NULL, NULL, '2025-08-31 20:54:09', '2025-08-31 21:00:30', NULL);
INSERT INTO `sys_menu` VALUES (160, 155, '0,149,155', '岗位查询', 4, NULL, NULL, NULL, 'aioveuPosition:aioveu-position:query', 0, 1, 1, 1, NULL, NULL, '2025-08-31 20:56:41', '2025-08-31 20:56:41', NULL);
INSERT INTO `sys_menu` VALUES (161, 155, '0,149,155', '岗位新增', 4, NULL, NULL, NULL, 'aioveuPosition:aioveu-position:add', 0, 1, 1, 2, NULL, NULL, '2025-08-31 20:56:59', '2025-08-31 20:56:59', NULL);
INSERT INTO `sys_menu` VALUES (162, 155, '0,149,155', '岗位编辑', 4, NULL, NULL, NULL, 'aioveuPosition:aioveu-position:edit', 0, 1, 1, 3, NULL, NULL, '2025-08-31 20:57:10', '2025-08-31 20:57:10', NULL);
INSERT INTO `sys_menu` VALUES (163, 155, '0,149,155', '岗位删除', 4, NULL, NULL, NULL, 'aioveuPosition:aioveu-position:delete', 0, 1, 1, 4, NULL, NULL, '2025-08-31 20:57:20', '2025-08-31 20:57:20', NULL);
INSERT INTO `sys_menu` VALUES (164, 156, '0,149,156', '员工查询', 4, NULL, NULL, NULL, 'aioveuEmployee:aioveu-employee:query', 0, 1, 1, 1, NULL, NULL, '2025-08-31 22:05:56', '2025-08-31 22:05:56', NULL);
INSERT INTO `sys_menu` VALUES (165, 156, '0,149,156', '员工新增', 4, NULL, NULL, NULL, 'aioveuEmployee:aioveu-employee:add', 0, 1, 1, 2, NULL, NULL, '2025-08-31 22:06:14', '2025-08-31 22:06:14', NULL);
INSERT INTO `sys_menu` VALUES (166, 156, '0,149,156', '员工编辑', 4, NULL, NULL, NULL, 'aioveuEmployee:aioveu-employee:edit', 0, 1, 1, 3, NULL, NULL, '2025-08-31 22:06:27', '2025-08-31 22:06:27', NULL);
INSERT INTO `sys_menu` VALUES (167, 156, '0,149,156', '员工删除', 4, NULL, NULL, NULL, 'aioveuEmployee:aioveu-employee:delete', 0, 1, 1, 4, NULL, NULL, '2025-08-31 22:06:37', '2025-08-31 22:06:37', NULL);
INSERT INTO `sys_menu` VALUES (168, 157, '0,149,157', '考勤查询', 4, NULL, NULL, NULL, 'aioveuAttendance:aioveu-attendance:query', 0, 1, 1, 1, NULL, NULL, '2025-08-31 22:06:48', '2025-08-31 22:06:48', NULL);
INSERT INTO `sys_menu` VALUES (169, 157, '0,149,157', '考勤新增', 4, NULL, NULL, NULL, 'aioveuAttendance:aioveu-attendance:add', 0, 1, 1, 2, NULL, NULL, '2025-08-31 22:07:02', '2025-08-31 22:07:02', NULL);
INSERT INTO `sys_menu` VALUES (170, 157, '0,149,157', '考勤编辑', 4, NULL, NULL, NULL, 'aioveuAttendance:aioveu-attendance:edit', 0, 1, 1, 3, NULL, NULL, '2025-08-31 22:07:13', '2025-08-31 22:07:13', NULL);
INSERT INTO `sys_menu` VALUES (171, 157, '0,149,157', '考勤删除', 4, NULL, NULL, NULL, 'aioveuAttendance:aioveu-attendance:delete', 0, 1, 1, 4, NULL, NULL, '2025-08-31 22:07:24', '2025-08-31 22:07:24', NULL);
INSERT INTO `sys_menu` VALUES (172, 158, '0,149,158', '绩效查询', 4, NULL, NULL, NULL, 'aioveuPerformance:aioveu-performance:query', 0, 1, 1, 1, NULL, NULL, '2025-08-31 22:07:38', '2025-08-31 22:07:38', NULL);
INSERT INTO `sys_menu` VALUES (173, 158, '0,149,158', '绩效新增', 4, NULL, NULL, NULL, 'aioveuPerformance:aioveu-performance:add', 0, 1, 1, 2, NULL, NULL, '2025-08-31 22:07:52', '2025-08-31 22:07:52', NULL);
INSERT INTO `sys_menu` VALUES (174, 158, '0,149,158', '绩效编辑', 4, NULL, NULL, NULL, 'aioveuPerformance:aioveu-performance:edit', 0, 1, 1, 3, NULL, NULL, '2025-08-31 22:08:04', '2025-08-31 22:08:04', NULL);
INSERT INTO `sys_menu` VALUES (175, 158, '0,149,158', '绩效删除', 4, NULL, NULL, NULL, 'aioveuPerformance:aioveu-performance:delete', 0, 1, 1, 4, NULL, NULL, '2025-08-31 22:08:19', '2025-08-31 22:08:19', NULL);
INSERT INTO `sys_menu` VALUES (176, 159, '0,149,159', '薪资查询', 4, NULL, NULL, NULL, 'aioveuSalary:aioveu-salary:query', 0, 1, 1, 1, NULL, NULL, '2025-08-31 22:08:29', '2025-08-31 22:08:29', NULL);
INSERT INTO `sys_menu` VALUES (178, 159, '0,149,159', '薪资新增', 4, NULL, NULL, NULL, 'aioveuSalary:aioveu-salary:add', 0, 1, 1, 2, NULL, NULL, '2025-08-31 22:08:52', '2025-08-31 22:08:52', NULL);
INSERT INTO `sys_menu` VALUES (179, 159, '0,149,159', '薪资编辑', 4, NULL, NULL, NULL, 'aioveuSalary:aioveu-salary:edit', 0, 1, 1, 3, NULL, NULL, '2025-08-31 22:09:43', '2025-08-31 22:09:43', NULL);
INSERT INTO `sys_menu` VALUES (180, 159, '0,149,159', '薪资删除', 4, NULL, NULL, NULL, 'aioveuSalary:aioveu-salary:delete', 0, 1, 1, 4, NULL, NULL, '2025-08-31 22:09:52', '2025-08-31 22:09:52', NULL);
INSERT INTO `sys_menu` VALUES (181, 0, '0', '物资管理', 2, NULL, '/aioveuMaterial', 'Layout', NULL, 1, 1, 1, 2, 'message', NULL, '2025-08-31 22:58:34', '2025-08-31 23:10:40', NULL);
INSERT INTO `sys_menu` VALUES (182, 181, '0,181', '物资分类管理', 1, 'aioveu_category', 'aioveu_category', 'aioveuCategory/aioveu-category/index', NULL, 0, 1, 1, 1, 'menu', NULL, '2025-08-31 22:58:59', '2025-08-31 22:58:59', NULL);
INSERT INTO `sys_menu` VALUES (183, 181, '0,181', '物资管理', 1, 'aioveu_material', 'aioveu_material', 'aioveuMaterial/aioveu-material/index', NULL, 1, 1, 1, 2, 'menu', NULL, '2025-08-31 22:59:18', '2025-08-31 22:59:18', NULL);
INSERT INTO `sys_menu` VALUES (184, 181, '0,181', '仓库管理', 1, 'aioveu_warehouse', 'aioveu_warehouse', 'aioveuWarehouse/aioveu-warehouse/index', NULL, 1, 1, 1, 3, 'menu', NULL, '2025-08-31 22:59:36', '2025-08-31 22:59:36', NULL);
INSERT INTO `sys_menu` VALUES (185, 181, '0,181', '库存管理', 1, 'aioveu_inventory', 'aioveu_inventory', 'aioveuInventory/aioveu-inventory/index', NULL, 1, 1, 1, 4, 'menu', NULL, '2025-08-31 22:59:52', '2025-08-31 22:59:52', NULL);
INSERT INTO `sys_menu` VALUES (186, 181, '0,181', '入库管理', 1, 'aioveu_inbound', 'aioveu_inbound', 'aioveuInbound/aioveu-inbound/index', NULL, 1, 1, 1, 5, 'menu', NULL, '2025-08-31 23:00:12', '2025-08-31 23:00:12', NULL);
INSERT INTO `sys_menu` VALUES (187, 181, '0,181', '设备管理', 1, 'aioveu_equipment', 'aioveu_equipment', 'aioveuEquipment/aioveu-equipment/index', NULL, 1, 1, 1, 6, 'menu', NULL, '2025-08-31 23:00:30', '2025-08-31 23:00:30', NULL);
INSERT INTO `sys_menu` VALUES (188, 181, '0,181', '采购管理', 1, 'aioveu_procurement', 'aioveu_procurement', 'aioveu/aioveu-procurement/index', NULL, 1, 1, 1, 7, 'menu', NULL, '2025-08-31 23:00:46', '2025-08-31 23:19:20', NULL);
INSERT INTO `sys_menu` VALUES (189, 181, '0,181', '出库管理', 1, 'aioveu_outbound', 'aioveu_outbound', 'aioveuOutbound/aioveu-outbound/index', NULL, 1, 1, 1, 8, 'menu', NULL, '2025-08-31 23:01:01', '2025-08-31 23:01:01', NULL);
INSERT INTO `sys_menu` VALUES (190, 182, '0,181,182', '物资分类查询', 4, NULL, NULL, NULL, 'aioveuCategory:aioveu-category:query', 0, 1, 1, 1, NULL, NULL, '2025-08-31 23:01:25', '2025-08-31 23:01:25', NULL);
INSERT INTO `sys_menu` VALUES (191, 182, '0,181,182', '物资分类新增', 4, NULL, NULL, NULL, 'aioveuCategory:aioveu-category:add', 0, 1, 1, 2, NULL, NULL, '2025-08-31 23:01:39', '2025-08-31 23:01:39', NULL);
INSERT INTO `sys_menu` VALUES (192, 182, '0,181,182', '物资分类编辑', 4, NULL, NULL, NULL, 'aioveuCategory:aioveu-category:edit', 0, 1, 1, 3, NULL, NULL, '2025-08-31 23:01:54', '2025-08-31 23:01:54', NULL);
INSERT INTO `sys_menu` VALUES (194, 182, '0,181,182', '物资分类删除', 4, NULL, NULL, NULL, 'aioveuCategory:aioveu-category:delete', 0, 1, 1, 4, NULL, NULL, '2025-08-31 23:02:34', '2025-08-31 23:02:34', NULL);
INSERT INTO `sys_menu` VALUES (195, 183, '0,181,183', '物资查询', 4, NULL, NULL, NULL, 'aioveuMaterial:aioveu-material:query', 0, 1, 1, 1, NULL, NULL, '2025-08-31 23:02:47', '2025-08-31 23:02:47', NULL);
INSERT INTO `sys_menu` VALUES (196, 183, '0,181,183', '物资新增', 4, NULL, NULL, NULL, 'aioveuMaterial:aioveu-material:add', 0, 1, 1, 2, NULL, NULL, '2025-08-31 23:02:58', '2025-08-31 23:02:58', NULL);
INSERT INTO `sys_menu` VALUES (197, 183, '0,181,183', '物资编辑', 4, NULL, NULL, NULL, 'aioveuMaterial:aioveu-material:edit', 0, 1, 1, 3, NULL, NULL, '2025-08-31 23:03:08', '2025-08-31 23:03:08', NULL);
INSERT INTO `sys_menu` VALUES (198, 183, '0,181,183', '物资删除', 4, NULL, NULL, NULL, 'aioveuMaterial:aioveu-material:delete', 0, 1, 1, 4, NULL, NULL, '2025-08-31 23:03:19', '2025-08-31 23:03:19', NULL);
INSERT INTO `sys_menu` VALUES (200, 184, '0,181,184', '仓库查询', 4, NULL, NULL, NULL, 'aioveuWarehouse:aioveu-warehouse:query', 0, 1, 1, 1, NULL, NULL, '2025-08-31 23:05:06', '2025-08-31 23:05:06', NULL);
INSERT INTO `sys_menu` VALUES (201, 184, '0,181,184', '仓库新增', 4, NULL, NULL, NULL, 'aioveuWarehouse:aioveu-warehouse:add', 0, 1, 1, 2, NULL, NULL, '2025-08-31 23:05:16', '2025-08-31 23:05:16', NULL);
INSERT INTO `sys_menu` VALUES (202, 184, '0,181,184', '仓库编辑', 4, NULL, NULL, NULL, 'aioveuWarehouse:aioveu-warehouse:edit', 0, 1, 1, 1, NULL, NULL, '2025-08-31 23:05:23', '2025-08-31 23:05:23', NULL);
INSERT INTO `sys_menu` VALUES (203, 184, '0,181,184', '仓库删除', 4, NULL, NULL, NULL, 'aioveuWarehouse:aioveu-warehouse:delete', 0, 1, 1, 4, NULL, NULL, '2025-08-31 23:05:33', '2025-08-31 23:05:33', NULL);
INSERT INTO `sys_menu` VALUES (204, 185, '0,181,185', '库存查询', 4, NULL, NULL, NULL, 'aioveuInventory:aioveu-inventory:query', 0, 1, 1, 1, NULL, NULL, '2025-08-31 23:05:45', '2025-08-31 23:05:45', NULL);
INSERT INTO `sys_menu` VALUES (205, 185, '0,181,185', '库存新增', 4, NULL, NULL, NULL, 'aioveuInventory:aioveu-inventory:add', 0, 1, 1, 2, NULL, NULL, '2025-08-31 23:06:01', '2025-08-31 23:06:01', NULL);
INSERT INTO `sys_menu` VALUES (206, 185, '0,181,185', '库存编辑', 4, NULL, NULL, NULL, 'aioveuInventory:aioveu-inventory:edit', 0, 1, 1, 3, NULL, NULL, '2025-08-31 23:06:12', '2025-08-31 23:06:12', NULL);
INSERT INTO `sys_menu` VALUES (207, 185, '0,181,185', '库存删除', 4, NULL, NULL, NULL, 'aioveuInventory:aioveu-inventory:delete', 0, 1, 1, 4, NULL, NULL, '2025-08-31 23:06:26', '2025-08-31 23:06:26', NULL);
INSERT INTO `sys_menu` VALUES (208, 186, '0,181,186', '入库查询', 4, NULL, NULL, NULL, 'aioveuInbound:aioveu-inbound:query', 0, 1, 1, 1, NULL, NULL, '2025-08-31 23:06:40', '2025-08-31 23:06:40', NULL);
INSERT INTO `sys_menu` VALUES (209, 186, '0,181,186', '入库新增', 4, NULL, NULL, NULL, 'aioveuInbound:aioveu-inbound:add', 0, 1, 1, 2, NULL, NULL, '2025-08-31 23:06:49', '2025-08-31 23:06:49', NULL);
INSERT INTO `sys_menu` VALUES (210, 186, '0,181,186', '入库编辑', 4, NULL, NULL, NULL, 'aioveuInbound:aioveu-inbound:edit', 0, 1, 1, 3, NULL, NULL, '2025-08-31 23:07:00', '2025-08-31 23:07:00', NULL);
INSERT INTO `sys_menu` VALUES (211, 186, '0,181,186', '入库删除', 4, NULL, NULL, NULL, 'aioveuInbound:aioveu-inbound:delete', 0, 1, 1, 4, NULL, NULL, '2025-08-31 23:07:09', '2025-08-31 23:07:09', NULL);
INSERT INTO `sys_menu` VALUES (212, 187, '0,181,187', '设备查询', 4, NULL, NULL, NULL, 'aioveuEquipment:aioveu-equipment:query', 0, 1, 1, 1, NULL, NULL, '2025-08-31 23:07:24', '2025-08-31 23:07:24', NULL);
INSERT INTO `sys_menu` VALUES (213, 187, '0,181,187', '设备新增', 4, NULL, NULL, NULL, 'aioveuEquipment:aioveu-equipment:add', 0, 1, 1, 2, NULL, NULL, '2025-08-31 23:07:34', '2025-08-31 23:07:34', NULL);
INSERT INTO `sys_menu` VALUES (214, 187, '0,181,187', '设备编辑', 4, NULL, NULL, NULL, 'aioveuEquipment:aioveu-equipment:edit', 0, 1, 1, 3, NULL, NULL, '2025-08-31 23:07:45', '2025-08-31 23:07:45', NULL);
INSERT INTO `sys_menu` VALUES (215, 187, '0,181,187', '设备删除', 4, NULL, NULL, NULL, 'aioveuEquipment:aioveu-equipment:delete', 0, 1, 1, 4, NULL, NULL, '2025-08-31 23:07:57', '2025-08-31 23:07:57', NULL);
INSERT INTO `sys_menu` VALUES (216, 188, '0,181,188', '采购查询', 4, NULL, NULL, NULL, 'aioveu:aioveu-procurement:query', 0, 1, 1, 1, NULL, NULL, '2025-08-31 23:08:07', '2025-08-31 23:20:15', NULL);
INSERT INTO `sys_menu` VALUES (217, 188, '0,181,188', '采购新增', 4, NULL, NULL, NULL, 'aioveu:aioveu-procurement:add', 0, 1, 1, 2, NULL, NULL, '2025-08-31 23:08:17', '2025-08-31 23:20:21', NULL);
INSERT INTO `sys_menu` VALUES (218, 188, '0,181,188', '采购编辑', 4, NULL, NULL, NULL, 'aioveu:aioveu-procurement:edit', 0, 1, 1, 3, NULL, NULL, '2025-08-31 23:08:26', '2025-08-31 23:20:28', NULL);
INSERT INTO `sys_menu` VALUES (219, 188, '0,181,188', '采购删除', 4, NULL, NULL, NULL, 'aioveu:aioveu-procurement:delete', 0, 1, 1, 4, NULL, NULL, '2025-08-31 23:08:37', '2025-08-31 23:20:33', NULL);
INSERT INTO `sys_menu` VALUES (220, 189, '0,181,189', '出库查询', 4, NULL, NULL, NULL, 'aioveuOutbound:aioveu-outbound:query', 0, 1, 1, 1, NULL, NULL, '2025-08-31 23:08:49', '2025-08-31 23:08:49', NULL);
INSERT INTO `sys_menu` VALUES (221, 189, '0,181,189', '出库新增', 4, NULL, NULL, NULL, 'aioveuOutbound:aioveu-outbound:add', 0, 1, 1, 2, NULL, NULL, '2025-08-31 23:09:00', '2025-08-31 23:09:00', NULL);
INSERT INTO `sys_menu` VALUES (222, 189, '0,181,189', '出库编辑', 4, NULL, NULL, NULL, 'aioveuOutbound:aioveu-outbound:edit', 0, 1, 1, 3, NULL, NULL, '2025-08-31 23:09:10', '2025-08-31 23:09:10', NULL);
INSERT INTO `sys_menu` VALUES (223, 189, '0,181,189', '出库删除', 4, NULL, NULL, NULL, 'aioveuOutbound:aioveu-outbound:delete', 0, 1, 1, 4, NULL, NULL, '2025-08-31 23:09:22', '2025-08-31 23:09:22', NULL);
INSERT INTO `sys_menu` VALUES (224, 0, '0', '客户管理', 2, NULL, '/aioveuMember', 'Layout', NULL, 0, 1, 1, 3, 'csdn', NULL, '2025-08-31 23:10:31', '2025-08-31 23:10:31', NULL);
INSERT INTO `sys_menu` VALUES (225, 224, '0,224', '客户管理', 1, 'aioveu_customer', 'aioveu_customer', 'aioveuCustomer/aioveu-customer/index', NULL, 1, 1, 1, 1, 'menu', NULL, '2025-08-31 23:11:01', '2025-08-31 23:11:01', NULL);
INSERT INTO `sys_menu` VALUES (226, 224, '0,224', '联系人管理', 1, 'aioveu_contact', 'aioveu_contact', 'aioveuContact/aioveu-contact/index', NULL, 1, 1, 1, 2, 'menu', NULL, '2025-08-31 23:11:17', '2025-08-31 23:11:17', NULL);
INSERT INTO `sys_menu` VALUES (227, 224, '0,224', '交易管理', 1, 'aioveu_transaction', 'aioveu_transaction', 'aioveuTransaction/aioveu-transaction/index', NULL, 1, 1, 1, 3, 'menu', NULL, '2025-08-31 23:11:35', '2025-08-31 23:11:35', NULL);
INSERT INTO `sys_menu` VALUES (228, 224, '0,224', '订单管理', 1, 'aioveu_sales_order', 'aioveu_sales_order', 'aioveuSalesOrder/aioveu-sales-order/index', NULL, 1, 1, 1, 4, 'menu', NULL, '2025-08-31 23:11:55', '2025-08-31 23:24:08', NULL);
INSERT INTO `sys_menu` VALUES (229, 224, '0,224', '订单明细管理', 1, 'aioveu_sales_order_detail', 'aioveu_sales_order_detail', 'aioveuSalesOrderDetail/aioveu-sales-order-detail/index', NULL, 1, 1, 1, 5, 'menu', NULL, '2025-08-31 23:12:11', '2025-08-31 23:12:11', NULL);
INSERT INTO `sys_menu` VALUES (230, 225, '0,224,225', '客户查询', 4, NULL, NULL, NULL, 'aioveuCustomer:aioveu-customer:query', 0, 1, 1, 1, NULL, NULL, '2025-08-31 23:12:28', '2025-08-31 23:12:28', NULL);
INSERT INTO `sys_menu` VALUES (231, 225, '0,224,225', '客户新增', 4, NULL, NULL, NULL, 'aioveuCustomer:aioveu-customer:add', 0, 1, 1, 2, NULL, NULL, '2025-08-31 23:12:43', '2025-08-31 23:12:43', NULL);
INSERT INTO `sys_menu` VALUES (232, 225, '0,224,225', '客户编辑', 4, NULL, NULL, NULL, 'aioveuCustomer:aioveu-customer:edit', 0, 1, 1, 3, NULL, NULL, '2025-08-31 23:12:52', '2025-08-31 23:12:52', NULL);
INSERT INTO `sys_menu` VALUES (233, 225, '0,224,225', '客户删除', 4, NULL, NULL, NULL, 'aioveuCustomer:aioveu-customer:delete', 0, 1, 1, 4, NULL, NULL, '2025-08-31 23:13:03', '2025-08-31 23:13:03', NULL);
INSERT INTO `sys_menu` VALUES (234, 226, '0,224,226', '联系人查询', 4, NULL, NULL, NULL, 'aioveuContact:aioveu-contact:query', 0, 1, 1, 1, NULL, NULL, '2025-08-31 23:13:14', '2025-08-31 23:13:14', NULL);
INSERT INTO `sys_menu` VALUES (235, 226, '0,224,226', '联系人新增', 4, NULL, NULL, NULL, 'aioveuContact:aioveu-contact:add', 0, 1, 1, 2, NULL, NULL, '2025-08-31 23:13:24', '2025-08-31 23:13:24', NULL);
INSERT INTO `sys_menu` VALUES (236, 226, '0,224,226', '联系人编辑', 4, NULL, NULL, NULL, 'aioveuContact:aioveu-contact:edit', 0, 1, 1, 3, NULL, NULL, '2025-08-31 23:13:34', '2025-08-31 23:13:34', NULL);
INSERT INTO `sys_menu` VALUES (237, 226, '0,224,226', '联系人删除', 4, NULL, NULL, NULL, 'aioveuContact:aioveu-contact:delete', 0, 1, 1, 4, NULL, NULL, '2025-08-31 23:13:48', '2025-08-31 23:13:48', NULL);
INSERT INTO `sys_menu` VALUES (238, 227, '0,224,227', '交易查询', 4, NULL, NULL, NULL, 'aioveuTransaction:aioveu-transaction:query', 0, 1, 1, 1, NULL, NULL, '2025-08-31 23:13:57', '2025-08-31 23:13:57', NULL);
INSERT INTO `sys_menu` VALUES (239, 227, '0,224,227', '交易新增', 4, NULL, NULL, NULL, 'aioveuTransaction:aioveu-transaction:add', 0, 1, 1, 2, NULL, NULL, '2025-08-31 23:14:07', '2025-08-31 23:14:07', NULL);
INSERT INTO `sys_menu` VALUES (240, 227, '0,224,227', '交易编辑', 4, NULL, NULL, NULL, 'aioveuTransaction:aioveu-transaction:edit', 0, 1, 1, 3, NULL, NULL, '2025-08-31 23:14:16', '2025-08-31 23:14:16', NULL);
INSERT INTO `sys_menu` VALUES (241, 227, '0,224,227', '交易删除', 4, NULL, NULL, NULL, 'aioveuTransaction:aioveu-transaction:delete', 0, 1, 1, 4, NULL, NULL, '2025-08-31 23:14:34', '2025-08-31 23:14:34', NULL);
INSERT INTO `sys_menu` VALUES (242, 228, '0,224,228', '订单查询', 4, NULL, NULL, NULL, 'aioveuSalesOrder:aioveu-sales-order:query', 0, 1, 1, 1, NULL, NULL, '2025-08-31 23:14:44', '2025-08-31 23:24:17', NULL);
INSERT INTO `sys_menu` VALUES (243, 228, '0,224,228', '订单新增', 4, NULL, NULL, NULL, 'aioveuSalesOrder:aioveu-sales-order:add', 0, 1, 1, 2, NULL, NULL, '2025-08-31 23:14:52', '2025-08-31 23:24:22', NULL);
INSERT INTO `sys_menu` VALUES (244, 228, '0,224,228', '订单编辑', 4, NULL, NULL, NULL, 'aioveuSalesOrder:aioveu-sales-order:edit', 0, 1, 1, 3, NULL, NULL, '2025-08-31 23:15:01', '2025-08-31 23:24:26', NULL);
INSERT INTO `sys_menu` VALUES (245, 228, '0,224,228', '订单删除', 4, NULL, NULL, NULL, 'aioveuSalesOrder:aioveu-sales-order:delete', 0, 1, 1, 4, NULL, NULL, '2025-08-31 23:15:11', '2025-08-31 23:24:30', NULL);
INSERT INTO `sys_menu` VALUES (246, 229, '0,224,229', '订单明细查询', 4, NULL, NULL, NULL, 'aioveuSalesOrderDetail:aioveu-sales-order-detail:query', 0, 1, 1, 1, NULL, NULL, '2025-08-31 23:15:26', '2025-08-31 23:15:26', NULL);
INSERT INTO `sys_menu` VALUES (247, 229, '0,224,229', '订单明细新增', 4, NULL, NULL, NULL, 'aioveuSalesOrderDetail:aioveu-sales-order-detail:add', 0, 1, 1, 2, NULL, NULL, '2025-08-31 23:15:37', '2025-08-31 23:15:37', NULL);
INSERT INTO `sys_menu` VALUES (248, 229, '0,224,229', '订单明细编辑', 4, NULL, NULL, NULL, 'aioveuSalesOrderDetail:aioveu-sales-order-detail:edit', 0, 1, 1, 3, NULL, NULL, '2025-08-31 23:15:47', '2025-08-31 23:15:47', NULL);
INSERT INTO `sys_menu` VALUES (249, 229, '0,224,229', '订单明细删除', 4, NULL, NULL, NULL, 'aioveuSalesOrderDetail:aioveu-sales-order-detail:delete', 0, 1, 1, 4, NULL, NULL, '2025-08-31 23:15:57', '2025-08-31 23:26:52', NULL);

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '通知标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '通知内容',
  `type` tinyint NOT NULL COMMENT '通知类型（关联字典编码：notice_type）',
  `level` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '通知等级（字典code：notice_level）',
  `target_type` tinyint NOT NULL COMMENT '目标类型（1: 全体, 2: 指定）',
  `target_user_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '目标人ID集合（多个使用英文逗号,分割）',
  `publisher_id` bigint NULL DEFAULT NULL COMMENT '发布人ID',
  `publish_status` tinyint NULL DEFAULT 0 COMMENT '发布状态（0: 未发布, 1: 已发布, -1: 已撤回）',
  `publish_time` datetime NULL DEFAULT NULL COMMENT '发布时间',
  `revoke_time` datetime NULL DEFAULT NULL COMMENT '撤回时间',
  `create_by` bigint NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除（0: 未删除, 1: 已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '通知公告表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES (1, 'v2.12.0 新增系统日志，访问趋势统计功能。', '<p>1. 消息通知</p><p>2. 字典重构</p><p>3. 代码生成</p>', 1, 'L', 1, '2', 1, 1, '2025-08-31 19:27:53', '2025-08-31 19:27:53', 2, '2025-08-31 19:27:53', 1, '2025-08-31 19:27:53', 0);
INSERT INTO `sys_notice` VALUES (2, 'v2.13.0 新增菜单搜索。', '<p>1. 消息通知</p><p>2. 字典重构</p><p>3. 代码生成</p>', 1, 'L', 1, '2', 1, 1, '2025-08-31 19:27:53', '2025-08-31 19:27:53', 2, '2025-08-31 19:27:53', 1, '2025-08-31 19:27:53', 0);
INSERT INTO `sys_notice` VALUES (3, 'v2.14.0 新增个人中心。', '<p>1. 消息通知</p><p>2. 字典重构</p><p>3. 代码生成</p>', 1, 'L', 1, '2', 2, 1, '2025-08-31 19:27:53', '2025-08-31 19:27:53', 2, '2025-08-31 19:27:53', 2, '2025-08-31 19:27:53', 0);
INSERT INTO `sys_notice` VALUES (4, 'v2.15.0 登录页面改造。', '<p>1. 消息通知</p><p>2. 字典重构</p><p>3. 代码生成</p>', 1, 'L', 1, '2', 2, 1, '2025-08-31 19:27:53', '2025-08-31 19:27:53', 2, '2025-08-31 19:27:53', 2, '2025-08-31 19:27:53', 0);
INSERT INTO `sys_notice` VALUES (5, 'v2.16.0 通知公告、字典翻译组件。', '<p>1. 消息通知</p><p>2. 字典重构</p><p>3. 代码生成</p>', 1, 'L', 1, '2', 2, 1, '2025-08-31 19:27:53', '2025-08-31 19:27:53', 2, '2025-08-31 19:27:53', 2, '2025-08-31 19:27:53', 0);
INSERT INTO `sys_notice` VALUES (6, '系统将于本周六凌晨 2 点进行维护，预计维护时间为 2 小时。', '<p>1. 消息通知</p><p>2. 字典重构</p><p>3. 代码生成</p>', 2, 'H', 1, '2', 2, 1, '2025-08-31 19:27:54', '2025-08-31 19:27:54', 2, '2025-08-31 19:27:54', 2, '2025-08-31 19:27:54', 0);
INSERT INTO `sys_notice` VALUES (7, '最近发现一些钓鱼邮件，请大家提高警惕，不要点击陌生链接。', '<p>1. 消息通知</p><p>2. 字典重构</p><p>3. 代码生成</p>', 3, 'L', 1, '2', 2, 1, '2025-08-31 19:27:54', '2025-08-31 19:27:54', 2, '2025-08-31 19:27:54', 2, '2025-08-31 19:27:54', 0);
INSERT INTO `sys_notice` VALUES (8, '国庆假期从 10 月 1 日至 10 月 7 日放假，共 7 天。', '<p>1. 消息通知</p><p>2. 字典重构</p><p>3. 代码生成</p>', 4, 'L', 1, '2', 2, 1, '2025-08-31 19:27:54', '2025-08-31 19:27:54', 2, '2025-08-31 19:27:54', 2, '2025-08-31 19:27:54', 0);
INSERT INTO `sys_notice` VALUES (9, '公司将在 10 月 15 日举办新产品发布会，敬请期待。', '公司将在 10 月 15 日举办新产品发布会，敬请期待。', 5, 'H', 1, '2', 2, 1, '2025-08-31 19:27:54', '2025-08-31 19:27:54', 2, '2025-08-31 19:27:54', 2, '2025-08-31 19:27:54', 0);
INSERT INTO `sys_notice` VALUES (10, 'v2.16.1 版本发布。', 'v2.16.1 版本修复了 WebSocket 重复连接导致的后台线程阻塞问题，优化了通知公告。', 1, 'M', 1, '2', 2, 1, '2025-08-31 19:27:54', '2025-08-31 19:27:54', 2, '2025-08-31 19:27:54', 2, '2025-08-31 19:27:54', 0);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色编码',
  `sort` int NULL DEFAULT NULL COMMENT '显示顺序',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '角色状态(1-正常 0-停用)',
  `data_scope` tinyint NULL DEFAULT NULL COMMENT '数据权限(1-所有数据 2-部门及子部门数据 3-本部门数据 4-本人数据)',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除标识(0-未删除 1-已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_name`(`name` ASC) USING BTREE COMMENT '角色名称唯一索引',
  UNIQUE INDEX `uk_code`(`code` ASC) USING BTREE COMMENT '角色编码唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'ROOT', 1, 1, 1, NULL, '2025-08-31 19:27:39', NULL, '2025-08-31 19:27:39', 0);
INSERT INTO `sys_role` VALUES (2, '系统管理员', 'ADMIN', 2, 1, 1, NULL, '2025-08-31 19:27:39', NULL, NULL, 0);
INSERT INTO `sys_role` VALUES (3, '访问游客', 'GUEST', 3, 1, 3, NULL, '2025-08-31 19:27:39', NULL, '2025-08-31 19:27:39', 0);
INSERT INTO `sys_role` VALUES (4, '系统管理员1', 'ADMIN1', 4, 1, 1, NULL, '2025-08-31 19:27:39', NULL, NULL, 0);
INSERT INTO `sys_role` VALUES (5, '系统管理员2', 'ADMIN2', 5, 1, 1, NULL, '2025-08-31 19:27:39', NULL, NULL, 0);
INSERT INTO `sys_role` VALUES (6, '系统管理员3', 'ADMIN3', 6, 1, 1, NULL, '2025-08-31 19:27:39', NULL, NULL, 0);
INSERT INTO `sys_role` VALUES (7, '系统管理员4', 'ADMIN4', 7, 1, 1, NULL, '2025-08-31 19:27:39', NULL, NULL, 0);
INSERT INTO `sys_role` VALUES (8, '系统管理员5', 'ADMIN5', 8, 1, 1, NULL, '2025-08-31 19:27:39', NULL, NULL, 0);
INSERT INTO `sys_role` VALUES (9, '系统管理员6', 'ADMIN6', 9, 1, 1, NULL, '2025-08-31 19:27:39', NULL, NULL, 0);
INSERT INTO `sys_role` VALUES (10, '系统管理员7', 'ADMIN7', 10, 1, 1, NULL, '2025-08-31 19:27:39', NULL, NULL, 0);
INSERT INTO `sys_role` VALUES (11, '系统管理员8', 'ADMIN8', 11, 1, 1, NULL, '2025-08-31 19:27:39', NULL, NULL, 0);
INSERT INTO `sys_role` VALUES (12, '系统管理员9', 'ADMIN9', 12, 1, 1, NULL, '2025-08-31 19:27:39', NULL, NULL, 0);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  UNIQUE INDEX `uk_roleid_menuid`(`role_id` ASC, `menu_id` ASC) USING BTREE COMMENT '角色菜单唯一索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 2);
INSERT INTO `sys_role_menu` VALUES (2, 3);
INSERT INTO `sys_role_menu` VALUES (2, 4);
INSERT INTO `sys_role_menu` VALUES (2, 5);
INSERT INTO `sys_role_menu` VALUES (2, 6);
INSERT INTO `sys_role_menu` VALUES (2, 20);
INSERT INTO `sys_role_menu` VALUES (2, 21);
INSERT INTO `sys_role_menu` VALUES (2, 22);
INSERT INTO `sys_role_menu` VALUES (2, 23);
INSERT INTO `sys_role_menu` VALUES (2, 24);
INSERT INTO `sys_role_menu` VALUES (2, 26);
INSERT INTO `sys_role_menu` VALUES (2, 30);
INSERT INTO `sys_role_menu` VALUES (2, 31);
INSERT INTO `sys_role_menu` VALUES (2, 32);
INSERT INTO `sys_role_menu` VALUES (2, 33);
INSERT INTO `sys_role_menu` VALUES (2, 36);
INSERT INTO `sys_role_menu` VALUES (2, 37);
INSERT INTO `sys_role_menu` VALUES (2, 38);
INSERT INTO `sys_role_menu` VALUES (2, 39);
INSERT INTO `sys_role_menu` VALUES (2, 40);
INSERT INTO `sys_role_menu` VALUES (2, 41);
INSERT INTO `sys_role_menu` VALUES (2, 70);
INSERT INTO `sys_role_menu` VALUES (2, 71);
INSERT INTO `sys_role_menu` VALUES (2, 72);
INSERT INTO `sys_role_menu` VALUES (2, 73);
INSERT INTO `sys_role_menu` VALUES (2, 74);
INSERT INTO `sys_role_menu` VALUES (2, 75);
INSERT INTO `sys_role_menu` VALUES (2, 76);
INSERT INTO `sys_role_menu` VALUES (2, 77);
INSERT INTO `sys_role_menu` VALUES (2, 78);
INSERT INTO `sys_role_menu` VALUES (2, 79);
INSERT INTO `sys_role_menu` VALUES (2, 81);
INSERT INTO `sys_role_menu` VALUES (2, 84);
INSERT INTO `sys_role_menu` VALUES (2, 88);
INSERT INTO `sys_role_menu` VALUES (2, 89);
INSERT INTO `sys_role_menu` VALUES (2, 90);
INSERT INTO `sys_role_menu` VALUES (2, 95);
INSERT INTO `sys_role_menu` VALUES (2, 97);
INSERT INTO `sys_role_menu` VALUES (2, 102);
INSERT INTO `sys_role_menu` VALUES (2, 105);
INSERT INTO `sys_role_menu` VALUES (2, 106);
INSERT INTO `sys_role_menu` VALUES (2, 107);
INSERT INTO `sys_role_menu` VALUES (2, 108);
INSERT INTO `sys_role_menu` VALUES (2, 109);
INSERT INTO `sys_role_menu` VALUES (2, 110);
INSERT INTO `sys_role_menu` VALUES (2, 111);
INSERT INTO `sys_role_menu` VALUES (2, 112);
INSERT INTO `sys_role_menu` VALUES (2, 117);
INSERT INTO `sys_role_menu` VALUES (2, 118);
INSERT INTO `sys_role_menu` VALUES (2, 119);
INSERT INTO `sys_role_menu` VALUES (2, 120);
INSERT INTO `sys_role_menu` VALUES (2, 121);
INSERT INTO `sys_role_menu` VALUES (2, 122);
INSERT INTO `sys_role_menu` VALUES (2, 123);
INSERT INTO `sys_role_menu` VALUES (2, 124);
INSERT INTO `sys_role_menu` VALUES (2, 125);
INSERT INTO `sys_role_menu` VALUES (2, 126);
INSERT INTO `sys_role_menu` VALUES (2, 127);
INSERT INTO `sys_role_menu` VALUES (2, 128);
INSERT INTO `sys_role_menu` VALUES (2, 129);
INSERT INTO `sys_role_menu` VALUES (2, 130);
INSERT INTO `sys_role_menu` VALUES (2, 133);
INSERT INTO `sys_role_menu` VALUES (2, 134);
INSERT INTO `sys_role_menu` VALUES (2, 135);
INSERT INTO `sys_role_menu` VALUES (2, 136);
INSERT INTO `sys_role_menu` VALUES (2, 137);
INSERT INTO `sys_role_menu` VALUES (2, 138);
INSERT INTO `sys_role_menu` VALUES (2, 139);
INSERT INTO `sys_role_menu` VALUES (2, 140);
INSERT INTO `sys_role_menu` VALUES (2, 141);
INSERT INTO `sys_role_menu` VALUES (2, 142);
INSERT INTO `sys_role_menu` VALUES (2, 143);
INSERT INTO `sys_role_menu` VALUES (2, 144);
INSERT INTO `sys_role_menu` VALUES (2, 145);
INSERT INTO `sys_role_menu` VALUES (2, 146);
INSERT INTO `sys_role_menu` VALUES (2, 147);
INSERT INTO `sys_role_menu` VALUES (2, 148);
INSERT INTO `sys_role_menu` VALUES (2, 149);
INSERT INTO `sys_role_menu` VALUES (2, 150);
INSERT INTO `sys_role_menu` VALUES (2, 151);
INSERT INTO `sys_role_menu` VALUES (2, 152);
INSERT INTO `sys_role_menu` VALUES (2, 153);
INSERT INTO `sys_role_menu` VALUES (2, 154);
INSERT INTO `sys_role_menu` VALUES (2, 155);
INSERT INTO `sys_role_menu` VALUES (2, 156);
INSERT INTO `sys_role_menu` VALUES (2, 157);
INSERT INTO `sys_role_menu` VALUES (2, 158);
INSERT INTO `sys_role_menu` VALUES (2, 159);
INSERT INTO `sys_role_menu` VALUES (2, 160);
INSERT INTO `sys_role_menu` VALUES (2, 161);
INSERT INTO `sys_role_menu` VALUES (2, 162);
INSERT INTO `sys_role_menu` VALUES (2, 163);
INSERT INTO `sys_role_menu` VALUES (2, 164);
INSERT INTO `sys_role_menu` VALUES (2, 165);
INSERT INTO `sys_role_menu` VALUES (2, 166);
INSERT INTO `sys_role_menu` VALUES (2, 167);
INSERT INTO `sys_role_menu` VALUES (2, 168);
INSERT INTO `sys_role_menu` VALUES (2, 169);
INSERT INTO `sys_role_menu` VALUES (2, 170);
INSERT INTO `sys_role_menu` VALUES (2, 171);
INSERT INTO `sys_role_menu` VALUES (2, 172);
INSERT INTO `sys_role_menu` VALUES (2, 173);
INSERT INTO `sys_role_menu` VALUES (2, 174);
INSERT INTO `sys_role_menu` VALUES (2, 175);
INSERT INTO `sys_role_menu` VALUES (2, 176);
INSERT INTO `sys_role_menu` VALUES (2, 178);
INSERT INTO `sys_role_menu` VALUES (2, 179);
INSERT INTO `sys_role_menu` VALUES (2, 180);
INSERT INTO `sys_role_menu` VALUES (2, 181);
INSERT INTO `sys_role_menu` VALUES (2, 182);
INSERT INTO `sys_role_menu` VALUES (2, 183);
INSERT INTO `sys_role_menu` VALUES (2, 184);
INSERT INTO `sys_role_menu` VALUES (2, 185);
INSERT INTO `sys_role_menu` VALUES (2, 186);
INSERT INTO `sys_role_menu` VALUES (2, 187);
INSERT INTO `sys_role_menu` VALUES (2, 188);
INSERT INTO `sys_role_menu` VALUES (2, 189);
INSERT INTO `sys_role_menu` VALUES (2, 190);
INSERT INTO `sys_role_menu` VALUES (2, 191);
INSERT INTO `sys_role_menu` VALUES (2, 192);
INSERT INTO `sys_role_menu` VALUES (2, 194);
INSERT INTO `sys_role_menu` VALUES (2, 195);
INSERT INTO `sys_role_menu` VALUES (2, 196);
INSERT INTO `sys_role_menu` VALUES (2, 197);
INSERT INTO `sys_role_menu` VALUES (2, 198);
INSERT INTO `sys_role_menu` VALUES (2, 200);
INSERT INTO `sys_role_menu` VALUES (2, 201);
INSERT INTO `sys_role_menu` VALUES (2, 202);
INSERT INTO `sys_role_menu` VALUES (2, 203);
INSERT INTO `sys_role_menu` VALUES (2, 204);
INSERT INTO `sys_role_menu` VALUES (2, 205);
INSERT INTO `sys_role_menu` VALUES (2, 206);
INSERT INTO `sys_role_menu` VALUES (2, 207);
INSERT INTO `sys_role_menu` VALUES (2, 208);
INSERT INTO `sys_role_menu` VALUES (2, 209);
INSERT INTO `sys_role_menu` VALUES (2, 210);
INSERT INTO `sys_role_menu` VALUES (2, 211);
INSERT INTO `sys_role_menu` VALUES (2, 212);
INSERT INTO `sys_role_menu` VALUES (2, 213);
INSERT INTO `sys_role_menu` VALUES (2, 214);
INSERT INTO `sys_role_menu` VALUES (2, 215);
INSERT INTO `sys_role_menu` VALUES (2, 216);
INSERT INTO `sys_role_menu` VALUES (2, 217);
INSERT INTO `sys_role_menu` VALUES (2, 218);
INSERT INTO `sys_role_menu` VALUES (2, 219);
INSERT INTO `sys_role_menu` VALUES (2, 220);
INSERT INTO `sys_role_menu` VALUES (2, 221);
INSERT INTO `sys_role_menu` VALUES (2, 222);
INSERT INTO `sys_role_menu` VALUES (2, 223);
INSERT INTO `sys_role_menu` VALUES (2, 224);
INSERT INTO `sys_role_menu` VALUES (2, 225);
INSERT INTO `sys_role_menu` VALUES (2, 226);
INSERT INTO `sys_role_menu` VALUES (2, 227);
INSERT INTO `sys_role_menu` VALUES (2, 228);
INSERT INTO `sys_role_menu` VALUES (2, 229);
INSERT INTO `sys_role_menu` VALUES (2, 230);
INSERT INTO `sys_role_menu` VALUES (2, 231);
INSERT INTO `sys_role_menu` VALUES (2, 232);
INSERT INTO `sys_role_menu` VALUES (2, 233);
INSERT INTO `sys_role_menu` VALUES (2, 234);
INSERT INTO `sys_role_menu` VALUES (2, 235);
INSERT INTO `sys_role_menu` VALUES (2, 236);
INSERT INTO `sys_role_menu` VALUES (2, 237);
INSERT INTO `sys_role_menu` VALUES (2, 238);
INSERT INTO `sys_role_menu` VALUES (2, 239);
INSERT INTO `sys_role_menu` VALUES (2, 240);
INSERT INTO `sys_role_menu` VALUES (2, 241);
INSERT INTO `sys_role_menu` VALUES (2, 242);
INSERT INTO `sys_role_menu` VALUES (2, 243);
INSERT INTO `sys_role_menu` VALUES (2, 244);
INSERT INTO `sys_role_menu` VALUES (2, 245);
INSERT INTO `sys_role_menu` VALUES (2, 246);
INSERT INTO `sys_role_menu` VALUES (2, 247);
INSERT INTO `sys_role_menu` VALUES (2, 248);
INSERT INTO `sys_role_menu` VALUES (2, 249);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `nickname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
  `gender` tinyint(1) NULL DEFAULT 1 COMMENT '性别((1-男 2-女 0-保密)',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `dept_id` int NULL DEFAULT NULL COMMENT '部门ID',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户头像',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系方式',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '状态(1-正常 0-禁用)',
  `email` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除标识(0-未删除 1-已删除)',
  `openid` char(28) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '微信 openid',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `login_name`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'root', '可我不敌可爱', 0, '$2a$10$xVWsNOhHrCxh5UbpCE7/HuJ.PAOKcYAqRxD2CO2nVnJS.IAXkr5aq', NULL, 'https://minio.aioveu.com/aioveu/20250810/352485413d814dee87d9b7160dfe916e.png', '13061656199', 1, 'ambitiouschild@qq.com', '2025-08-31 19:27:48', NULL, '2025-08-31 19:27:48', NULL, 0, NULL);
INSERT INTO `sys_user` VALUES (2, 'admin', '系统管理员', 1, '$2a$10$xVWsNOhHrCxh5UbpCE7/HuJ.PAOKcYAqRxD2CO2nVnJS.IAXkr5aq', 1, 'https://minio.aioveu.com/aioveu/20250810/352485413d814dee87d9b7160dfe916e.png', '13061656199', 1, 'ambitiouschild@qq.com', '2025-08-31 19:27:48', NULL, '2025-08-31 19:27:48', NULL, 0, NULL);
INSERT INTO `sys_user` VALUES (3, 'test', '测试小用户', 1, '$2a$10$xVWsNOhHrCxh5UbpCE7/HuJ.PAOKcYAqRxD2CO2nVnJS.IAXkr5aq', 3, 'https://minio.aioveu.com/aioveu/20250810/352485413d814dee87d9b7160dfe916e.png', '13061656199', 1, 'ambitiouschild@qq.com', '2025-08-31 19:27:48', NULL, '2025-08-31 19:27:48', NULL, 0, NULL);
INSERT INTO `sys_user` VALUES (4, 'aioveu', '可我不敌可爱', 1, '$2a$10$yTnPzczp2Pr9VMrXDKd60eu0Nlp5rhQ27saGitg/pdrhdbMlhAi0W', 1, 'https://minio.aioveu.com/aioveu/20250810/352485413d814dee87d9b7160dfe916e.png', '13061656199', 1, 'ambitiouschild@qq.com', '2025-08-31 20:05:27', 2, '2025-08-31 20:06:16', NULL, 0, NULL);

-- ----------------------------
-- Table structure for sys_user_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_notice`;
CREATE TABLE `sys_user_notice`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `notice_id` bigint NOT NULL COMMENT '公共通知id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `is_read` bigint NULL DEFAULT 0 COMMENT '读取状态（0: 未读, 1: 已读）',
  `read_time` datetime NULL DEFAULT NULL COMMENT '阅读时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '逻辑删除(0: 未删除, 1: 已删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户通知公告表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_notice
-- ----------------------------
INSERT INTO `sys_user_notice` VALUES (1, 1, 2, 1, NULL, '2025-08-31 19:27:55', '2025-08-31 19:27:55', 0);
INSERT INTO `sys_user_notice` VALUES (2, 2, 2, 1, NULL, '2025-08-31 19:27:55', '2025-08-31 19:27:55', 0);
INSERT INTO `sys_user_notice` VALUES (3, 3, 2, 1, NULL, '2025-08-31 19:27:55', '2025-08-31 19:27:55', 0);
INSERT INTO `sys_user_notice` VALUES (4, 4, 2, 1, NULL, '2025-08-31 19:27:55', '2025-08-31 19:27:55', 0);
INSERT INTO `sys_user_notice` VALUES (5, 5, 2, 1, NULL, '2025-08-31 19:27:55', '2025-08-31 19:27:55', 0);
INSERT INTO `sys_user_notice` VALUES (6, 6, 2, 1, NULL, '2025-08-31 19:27:55', '2025-08-31 19:27:55', 0);
INSERT INTO `sys_user_notice` VALUES (7, 7, 2, 1, NULL, '2025-08-31 19:27:55', '2025-08-31 19:27:55', 0);
INSERT INTO `sys_user_notice` VALUES (8, 8, 2, 1, NULL, '2025-08-31 19:27:56', '2025-08-31 19:27:56', 0);
INSERT INTO `sys_user_notice` VALUES (9, 9, 2, 1, NULL, '2025-08-31 19:27:56', '2025-08-31 19:27:56', 0);
INSERT INTO `sys_user_notice` VALUES (10, 10, 2, 1, NULL, '2025-08-31 19:27:56', '2025-08-31 19:27:56', 0);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户和角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2);
INSERT INTO `sys_user_role` VALUES (3, 3);
INSERT INTO `sys_user_role` VALUES (4, 2);

SET FOREIGN_KEY_CHECKS = 1;
