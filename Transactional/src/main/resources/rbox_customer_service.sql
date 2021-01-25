/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.1.68
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : 192.168.1.68:3306
 Source Schema         : rbox_customer_service

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 28/11/2020 18:36:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cs_field_change_log
-- ----------------------------
DROP TABLE IF EXISTS `cs_field_change_log`;
CREATE TABLE `cs_field_change_log`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `field_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字段名称',
  `original_value` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '原值',
  `new_value` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '新值',
  `field_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字段描述',
  `remark` varchar(3000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '备注',
  `last_updated_at` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `last_updated_by` int(20) NULL DEFAULT -100 COMMENT '修改人',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `created_by` int(20) NULL DEFAULT -100 COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '客服系统，字段更改日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cs_field_change_log
-- ----------------------------
INSERT INTO `cs_field_change_log` VALUES (5, 'online_status', '1', '3', '状态修改', '士大夫', '2020-09-19 11:23:47', 7906, '2020-09-19 11:23:47', 7906);
INSERT INTO `cs_field_change_log` VALUES (6, 'online_status', '3', '3', '状态修改', '士大夫', '2020-09-19 11:24:17', 7906, '2020-09-19 11:24:17', 7906);
INSERT INTO `cs_field_change_log` VALUES (7, 'online_status', '3', '2', '', '', '2020-09-19 11:25:04', 7906, '2020-09-19 11:25:04', 7906);

-- ----------------------------
-- Table structure for cs_message_template
-- ----------------------------
DROP TABLE IF EXISTS `cs_message_template`;
CREATE TABLE `cs_message_template`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板标题',
  `template` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板内容',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板code',
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板类型',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `is_enable` tinyint(1) NULL DEFAULT 1 COMMENT '是否启用',
  `created_by` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `last_updated_by` int(11) NULL DEFAULT NULL COMMENT '最后更新人',
  `last_updated_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cs_message_template
-- ----------------------------
INSERT INTO `cs_message_template` VALUES (1, '自寄', '尊敬的用户，售后单号：{aftersales}，{product}，运费寄付，{freightCompensation}收货地址：{address}，收件人：锐锢商城，联系电话：{mobile}，如有疑问请致电锐锢客服热：400-078-1888，工作时间周一至周日：9:00~18:00，感谢您的配合，祝您生意兴隆！点击填写物流信息', 'MANUAL_SELF_SEND_MAIL', 'APP', '手动push_客户自寄', 1, NULL, '2020-11-20 10:52:21', NULL, '2020-11-24 02:36:21');
INSERT INTO `cs_message_template` VALUES (2, '博世维修', '尊敬的用户，售后单号：{aftersales}，{product}产品，博世厂家维修寄回地址：杭州市滨江区滨康路567号102/1F，收件人：宋小洁，联系电话：0571-88875566。博世服务热线：400-826-8484，将整机与3-4个月内的收据加公章，机器一定不能拆。返厂后会收到维修编号，可以通过400电话查询维修进度。注：博世保修按第一次提供的凭证上的时间算起，保修时间6个月，耗材及人为损坏及自行拆卸不保（如：碳刷/过载/自行换配等）感谢您的配合，如有疑问请致电锐锢客服热线：400-068-8866，工作时间周一至周日：9:00~18:00，感谢您的配合，祝您生意兴隆！', 'MANUAL_BOSCH_SELF_SEND_MAIL', 'APP', '手动push_博世产品维修自寄', 1, NULL, '2020-11-20 13:50:35', NULL, '2020-11-23 09:25:13');
INSERT INTO `cs_message_template` VALUES (3, '少错货', '尊敬的用户，售后单号：{aftersales}，{product}产品少货/错货，需提供您到货外箱上的包含商品编码的照片，方便与仓库/供应商核实，烦请将图片发送公众号：锐锢商城，并注明对应售后单号，如有疑问请致电锐锢客服热线：400-068-8866，工作时间周一至周日：9:00~18:00，感谢您的配合，祝您生意兴隆！', 'MANUAL_LACK_WRONG_GOODS', 'APP', '手动push_少货/错货', 1, NULL, '2020-11-20 13:51:25', NULL, '2020-11-20 10:49:45');
INSERT INTO `cs_message_template` VALUES (4, '支付链接', '有一条维修单需要进行支付处理', 'MANUAL_PAYMENT_LINK', 'APP', '手动push_支付链接', 1, NULL, '2020-11-20 13:53:35', NULL, '2020-11-20 10:49:58');

-- ----------------------------
-- Table structure for cs_problem_type
-- ----------------------------
DROP TABLE IF EXISTS `cs_problem_type`;
CREATE TABLE `cs_problem_type`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `problem_type` tinyint(2) NOT NULL DEFAULT 1 COMMENT '业务类型1-售后单，2-工单',
  `problem_type_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 问题类型名称 ',
  `status` tinyint(2) NOT NULL DEFAULT 1 COMMENT '1-正常，0-锁定',
  `last_updated_at` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `last_updated_by` int(20) NULL DEFAULT -100 COMMENT '修改人',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `created_by` int(20) NULL DEFAULT -100 COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '客服系统，需要处理的问题类型' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cs_problem_type
-- ----------------------------
INSERT INTO `cs_problem_type` VALUES (1, 1, '售后单', 1, '2020-09-17 06:21:21', -100, '2020-09-17 06:20:57', -100);
INSERT INTO `cs_problem_type` VALUES (2, 2, '问题单', 1, '2020-09-17 06:21:26', -100, '2020-09-17 06:21:18', -100);

-- ----------------------------
-- Table structure for cs_processed_after_sales_order_r1
-- ----------------------------
DROP TABLE IF EXISTS `cs_processed_after_sales_order_r1`;
CREATE TABLE `cs_processed_after_sales_order_r1`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `aftersales_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '售后单号',
  `process_status` tinyint(2) NOT NULL DEFAULT 1 COMMENT '3已完成 4已关闭',
  `is_need_help` tinyint(2) NULL DEFAULT 0 COMMENT '求助flag 0 默认不求助 1 求助',
  `is_hang` tinyint(2) NULL DEFAULT 0 COMMENT '挂起flag 0 默认不挂起 1 挂起',
  `group_id` tinyint(2) NULL DEFAULT NULL COMMENT '组Id',
  `group_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组名称',
  `handler_id` int(11) NULL DEFAULT NULL COMMENT '处理人id',
  `handler` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理人名称',
  `created_by` int(20) NULL DEFAULT -100 COMMENT '创建人',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `last_updated_by` int(20) NULL DEFAULT -100 COMMENT '修改人',
  `last_updated_at` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `after_sale_no`(`aftersales_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '已处理r1售后单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cs_processed_after_sales_order_r1
-- ----------------------------
INSERT INTO `cs_processed_after_sales_order_r1` VALUES (1, 'AS202011260130025', 4, 0, 0, NULL, NULL, NULL, NULL, 295, '2020-11-27 10:12:00', -100, '2020-11-27 02:55:54');
INSERT INTO `cs_processed_after_sales_order_r1` VALUES (2, 'AS202011260130028', 4, 0, 0, NULL, NULL, NULL, NULL, 295, '2020-11-27 10:12:00', -100, '2020-11-27 02:55:54');
INSERT INTO `cs_processed_after_sales_order_r1` VALUES (3, 'AS202011200110004', 5, 0, 0, NULL, NULL, 7303, '袁林', 295, '2020-11-27 10:12:00', -100, '2020-11-27 11:38:18');

-- ----------------------------
-- Table structure for cs_processed_exception_order
-- ----------------------------
DROP TABLE IF EXISTS `cs_processed_exception_order`;
CREATE TABLE `cs_processed_exception_order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `exception_order_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '问题单单号',
  `process_status` tinyint(2) NOT NULL DEFAULT 1 COMMENT '3已完成 4已关闭',
  `is_need_help` tinyint(2) NULL DEFAULT 0 COMMENT '求助flag 0 默认不求助 1 求助',
  `is_hang` tinyint(2) NULL DEFAULT 0 COMMENT '挂起flag 0 默认不挂起 1 挂起',
  `group_id` tinyint(2) NULL DEFAULT NULL COMMENT '组Id',
  `group_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组名称',
  `handler_id` int(11) NULL DEFAULT NULL COMMENT '处理人id',
  `handler` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理人名称',
  `created_by` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `last_updated_by` int(11) NULL DEFAULT NULL COMMENT '最后更新人',
  `last_updated_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `problem_order_no`(`exception_order_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '已处理问题单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cs_processed_exception_order
-- ----------------------------
INSERT INTO `cs_processed_exception_order` VALUES (10, 'E20201108180150461009564', 3, 0, 0, NULL, NULL, NULL, '', 200001, '2020-11-27 10:12:00', NULL, '2020-11-27 02:56:25');
INSERT INTO `cs_processed_exception_order` VALUES (11, 'E20201108180152680283886', 3, 0, 0, NULL, NULL, NULL, '', 200001, '2020-11-27 10:12:00', NULL, '2020-11-27 02:56:25');
INSERT INTO `cs_processed_exception_order` VALUES (12, 'E20201108180413194528694', 3, 0, 0, NULL, NULL, NULL, '', 200001, '2020-11-27 10:12:00', NULL, '2020-11-27 02:56:25');
INSERT INTO `cs_processed_exception_order` VALUES (13, 'E20201108180417911584250', 3, 0, 0, NULL, NULL, NULL, '', 200001, '2020-11-27 10:12:00', NULL, '2020-11-27 02:56:25');
INSERT INTO `cs_processed_exception_order` VALUES (14, 'E20201108180423746349854', 3, 0, 0, NULL, NULL, NULL, '', 200001, '2020-11-27 10:12:00', NULL, '2020-11-27 02:56:25');
INSERT INTO `cs_processed_exception_order` VALUES (15, 'E20201125142307460286987', 3, 0, 0, NULL, NULL, NULL, '', 7303, '2020-11-27 10:12:00', NULL, '2020-11-27 02:56:25');
INSERT INTO `cs_processed_exception_order` VALUES (16, 'E20201108180028946501674', 4, 0, 0, NULL, NULL, NULL, NULL, 7303, '2020-11-27 10:12:00', NULL, '2020-11-27 02:56:25');
INSERT INTO `cs_processed_exception_order` VALUES (17, 'E20201124193902793561130', 4, 0, 0, NULL, NULL, NULL, NULL, 7303, '2020-11-27 10:12:00', NULL, '2020-11-27 02:56:25');

-- ----------------------------
-- Table structure for cs_to_be_processed_after_sales_order_r1
-- ----------------------------
DROP TABLE IF EXISTS `cs_to_be_processed_after_sales_order_r1`;
CREATE TABLE `cs_to_be_processed_after_sales_order_r1`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `aftersales_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '售后单号',
  `process_status` int(255) NULL DEFAULT NULL COMMENT '处理状态',
  `is_need_help` tinyint(2) NULL DEFAULT 0 COMMENT '求助flag 0 默认不求助 1 求助',
  `is_hang` tinyint(2) NULL DEFAULT 0 COMMENT '挂起flag 0 默认不挂起 1 挂起',
  `group_id` tinyint(2) NULL DEFAULT NULL COMMENT '组Id',
  `group_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组名称',
  `handler_id` int(11) NULL DEFAULT NULL COMMENT '处理人id',
  `handler` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理人名称',
  `expire_at` datetime(0) NULL DEFAULT NULL COMMENT '超时时间',
  `customer_id` int(11) NULL DEFAULT NULL COMMENT '客户Id',
  `created_by` int(20) NULL DEFAULT -100 COMMENT '创建人',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `last_updated_by` int(20) NULL DEFAULT -100 COMMENT '修改人',
  `last_updated_at` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `after_sale_no`(`aftersales_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 134 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '待处理r1售后单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cs_to_be_processed_after_sales_order_r1
-- ----------------------------
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (38, 'AS202011129930036', 2, 1, 1, NULL, NULL, 7303, '袁林', '2020-11-12 11:01:40', 109648, 666, '2020-11-27 10:12:00', 1365, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (39, 'AS202011129930038', 2, 0, 0, NULL, NULL, 7303, '袁林', '2020-11-12 11:01:40', 109648, 666, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (40, 'AS202011129930037', 1, 1, 0, NULL, NULL, 7928, '王冲', '2020-11-12 11:01:40', 109648, 666, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (41, 'AS202011129930039', 1, 0, 0, NULL, NULL, 7303, '张三', '2020-11-12 11:01:40', 109648, 666, '2020-11-27 10:12:00', NULL, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (42, 'AS202011129930040', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-12 11:01:40', 109648, 666, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (43, 'AS202011129930041', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-12 11:01:40', 109648, 666, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (44, 'AS202011129930042', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-12 11:01:40', 109648, 666, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (45, 'AS202011129930043', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-12 11:01:40', 109648, 666, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (46, 'AS202011190130018', 1, 0, 0, NULL, NULL, 7928, '王莹', '2020-11-20 00:24:54', 4860, 295, '2020-11-27 10:12:00', 1339, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (48, 'AS202011190130044', 1, 0, 0, NULL, NULL, 3019, '卢金科', '2020-11-19 14:52:21', 4860, 295, '2020-11-27 10:12:00', 1339, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (49, 'AS202011190130050', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-20 15:01:57', 4860, 295, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (59, 'AS202011200130022', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-20 17:46:30', 4860, 295, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (61, 'AS202011200130024', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-20 18:04:53', 4860, 295, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (62, 'AS202011200130025', 2, 0, 0, NULL, NULL, 7303, '袁林', '2020-11-20 18:16:56', 4860, 295, '2020-11-27 10:12:00', 1339, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (64, 'AS202011200110002', 2, 0, 0, NULL, NULL, 4860, NULL, NULL, NULL, 4860, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (65, 'AS202011200120002', 2, 0, 0, NULL, NULL, 4860, NULL, NULL, NULL, 4860, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (66, 'AS202011200120003', 2, 0, 0, NULL, NULL, 4860, NULL, NULL, NULL, 4860, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (68, 'AS202011200130028', 1, 0, 0, NULL, NULL, 8002, '张跃俊', '2020-11-20 18:55:16', 4860, 295, '2020-11-27 10:12:00', 1339, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (72, 'AS202011200130029', 2, 0, 0, NULL, NULL, 4860, NULL, NULL, NULL, 4860, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (74, 'AS202011200130030', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-20 19:04:38', 4860, 295, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (76, 'AS202011200110005', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-20 19:14:58', 4860, 295, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (77, 'AS202011200130031', 1, 0, 0, NULL, NULL, 3019, '卢金科', '2020-11-20 19:12:42', 4860, 295, '2020-11-27 10:12:00', 1339, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (78, 'AS202011200110006', 1, 0, 0, NULL, NULL, 3019, '卢金科', '2020-11-20 19:27:47', 4860, 295, '2020-11-27 10:12:00', 1339, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (79, 'AS202011200130032', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-20 19:28:51', 4860, 295, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (80, 'AS202011200130033', 1, 0, 0, NULL, NULL, 7929, '胡媛媛', '2020-11-20 19:29:08', 4860, 295, '2020-11-27 10:12:00', 1339, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (81, 'AS202011230110002', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-23 12:41:20', 4860, 295, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (82, 'AS202011230120002', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-23 12:53:10', 4860, 295, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (83, 'AS202011230120003', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-23 20:27:45', 4860, 295, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (84, 'AS202011230130004', 1, 0, 0, NULL, NULL, 8002, '张跃俊', '2020-11-23 20:39:15', 4860, 295, '2020-11-27 10:12:00', 1339, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (85, 'AS202011240130002', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-24 16:24:15', 4860, 295, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (86, 'AS202011240110002', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-24 16:34:52', 4860, 295, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (87, 'AS202011230110004', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-24 16:50:27', 4860, 295, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (94, 'AS202011240130003', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-24 18:56:01', 4860, 296, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (95, 'AS202011240110005', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-24 19:28:44', 4860, 296, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (96, 'AS202011240120003', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-24 19:40:05', 4860, 296, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (97, 'AS202011240120004', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-24 19:45:47', 4860, 296, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (98, 'AS202011260110002', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-26 12:46:36', 4860, 295, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (99, 'AS202011260130002', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-26 12:52:04', 4860, 295, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (100, 'AS202011260130003', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-26 13:05:54', 4860, 295, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (101, 'AS202009099930003', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-26 13:09:02', 4860, 295, '2020-11-27 10:12:00', -100, '2020-11-27 09:09:46');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (102, 'AS202011260130006', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-26 15:06:41', 4860, 295, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (103, 'AS202011260130007', 2, 0, 0, NULL, NULL, 4860, NULL, NULL, NULL, 4860, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (104, 'AS202011260130008', 2, 0, 0, NULL, NULL, 4860, NULL, NULL, NULL, 4860, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (105, 'AS202011260130009', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-26 15:21:24', 4860, 295, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (106, 'AS202011260130010', 2, 0, 0, NULL, NULL, 4860, NULL, NULL, NULL, 4860, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (107, 'AS202011260130011', 2, 0, 0, NULL, NULL, 4860, NULL, NULL, NULL, 4860, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (108, 'AS202011260130012', 2, 0, 0, NULL, NULL, 4860, NULL, NULL, NULL, 4860, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (109, 'AS202011260130013', 2, 0, 0, NULL, NULL, 4860, NULL, NULL, NULL, 4860, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (110, 'AS202011260130014', 2, 0, 0, NULL, NULL, 4860, NULL, NULL, NULL, 4860, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (111, 'AS202011260130015', 2, 0, 0, NULL, NULL, 4860, NULL, NULL, NULL, 4860, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (112, 'AS202011260130016', 2, 0, 0, NULL, NULL, 4860, NULL, NULL, NULL, 4860, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (113, 'AS202011260130017', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-26 15:48:32', 4860, 295, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (114, 'AS202011260130019', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-26 15:54:28', 4860, 295, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (115, 'AS202011260110004', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-26 17:07:03', 4860, 295, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (116, 'AS202011260130020', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-26 18:02:42', 4860, 295, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (117, 'AS202011260130021', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-26 18:07:46', 4860, 295, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (118, 'AS202011260130022', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-26 18:50:01', 4860, 295, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (119, 'AS202011260130023', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-26 19:01:24', 4860, 295, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (120, 'AS202011260130024', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-26 19:05:38', 4860, 295, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (122, 'AS202011260130026', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-26 19:21:41', 4860, 295, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (123, 'AS202011260110006', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-26 19:25:39', 4860, 295, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (124, 'AS202011260130027', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-26 19:31:10', 4860, 295, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (126, 'AS202011260130029', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-26 20:39:42', 4860, 295, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (127, 'AS202011260130030', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-26 20:41:08', 4860, 295, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (128, 'AS202011260130031', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-26 20:46:37', 4860, 295, '2020-11-27 10:12:00', -100, '2020-11-27 02:56:04');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (129, 'AS202011270130002', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-27 20:31:50', 4860, 295, '2020-11-27 18:31:50', -100, NULL);
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (130, 'AS202011270130003', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-27 20:32:22', 4860, 295, '2020-11-27 18:32:22', -100, NULL);
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (131, 'AS202011270130004', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-27 20:32:55', 4860, 295, '2020-11-27 18:32:55', -100, NULL);
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (132, 'AS202011280130003', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-28 19:28:12', 4860, 295, '2020-11-28 17:28:12', -100, NULL);
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (133, 'AS202011280130004', 1, 0, 0, NULL, NULL, NULL, NULL, '2020-11-28 19:45:58', 4860, 295, '2020-11-28 17:45:58', -100, NULL);

-- ----------------------------
-- Table structure for cs_to_be_processed_exception_order
-- ----------------------------
DROP TABLE IF EXISTS `cs_to_be_processed_exception_order`;
CREATE TABLE `cs_to_be_processed_exception_order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `exception_order_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '问题单单号',
  `process_status` tinyint(2) NOT NULL DEFAULT 1 COMMENT '1待处理 2处理中',
  `is_need_help` tinyint(2) NULL DEFAULT 0 COMMENT '求助flag 0 默认不求助 1 求助',
  `is_hang` tinyint(2) NULL DEFAULT 0 COMMENT '挂起flag 0 默认不挂起 1 挂起',
  `group_id` bigint(2) NULL DEFAULT NULL COMMENT '组Id',
  `group_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组名称',
  `handler_id` int(11) NULL DEFAULT NULL COMMENT '处理人id',
  `handler` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理人名称',
  `created_by` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `last_updated_by` int(11) NULL DEFAULT NULL COMMENT '最后更新人',
  `last_updated_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '待处理问题单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cs_to_be_processed_exception_order
-- ----------------------------
INSERT INTO `cs_to_be_processed_exception_order` VALUES (9, 'E20201108180132563039314', 2, 0, 0, NULL, NULL, 7928, '王莹', 200001, '2020-11-27 10:12:00', 7303, '2020-11-28 15:53:40');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (10, 'E20201108180138120656639', 1, 1, 0, NULL, NULL, 7928, '王莹', 200001, '2020-11-27 10:12:00', 200001, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (11, 'E20201108180202485831986', 1, 0, 0, NULL, NULL, 7928, '王莹', 200001, '2020-11-27 10:12:00', 200001, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (12, 'E20201108180204101571700', 1, 0, 0, NULL, NULL, 7928, '王莹', 200001, '2020-11-27 10:12:00', 200001, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (13, 'E20201108180213569251488', 1, 0, 0, NULL, NULL, 7928, '王莹', 200001, '2020-11-27 10:12:00', 7303, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (14, 'E20201108180220017493734', 2, 0, 1, NULL, NULL, 7928, '王莹', 200001, '2020-11-27 10:12:00', 7303, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (15, 'E20201108180334121914235', 2, 1, 0, NULL, NULL, 7928, '王莹', 200001, '2020-11-27 10:12:00', 7303, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (16, 'E20201108180339303434264', 2, 0, 1, NULL, NULL, 7928, '王莹', 200001, '2020-11-27 10:12:00', 7303, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (17, 'E20201108180344775055820', 2, 0, 1, NULL, NULL, 7928, '王莹', 200001, '2020-11-27 10:12:00', 7303, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (18, 'E20201120154536632086430', 2, 0, 0, NULL, NULL, 7928, '王莹', 7303, '2020-11-27 10:12:00', 7303, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (19, 'E20201120155425822838156', 2, 0, 0, NULL, NULL, 7928, '王莹', 7303, '2020-11-27 10:12:00', 7303, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (20, 'E20201120155455518402130', 2, 0, 0, NULL, NULL, 7928, '王莹', 7303, '2020-11-27 10:12:00', 7303, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (21, 'E20201120160417530623919', 2, 0, 0, NULL, NULL, NULL, '', 7303, '2020-11-27 10:12:00', 7303, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (22, 'E20201120164629715093098', 2, 0, 0, NULL, NULL, NULL, '', 7303, '2020-11-27 10:12:00', 7303, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (23, 'E20201120164817356371710', 1, 0, 0, 169, '售后维修组', NULL, NULL, 7303, '2020-11-27 10:12:00', 7303, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (24, 'E20201120172550779250437', 2, 0, 0, NULL, NULL, NULL, '', 7303, '2020-11-27 10:12:00', 7303, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (25, 'E20201120172554603365741', 2, 0, 0, NULL, NULL, NULL, '', 7303, '2020-11-27 10:12:00', 7303, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (26, 'E20201120172637398004736', 2, 0, 0, NULL, NULL, 7303, '袁林', 7303, '2020-11-27 10:12:00', NULL, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (27, 'E20201120172644514338809', 2, 0, 0, NULL, NULL, 7303, '袁林', 7303, '2020-11-27 10:12:00', NULL, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (28, 'E20201120172703337668291', 2, 0, 0, NULL, NULL, 7303, '袁林', 7303, '2020-11-27 10:12:00', NULL, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (29, 'E20201120172726675145912', 2, 0, 0, NULL, NULL, 7303, '袁林', 7303, '2020-11-27 10:12:00', NULL, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (30, 'E20201120172747468003641', 2, 0, 0, NULL, NULL, 7303, '袁林', 7303, '2020-11-27 10:12:00', NULL, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (31, 'E20201120190445405210601', 2, 0, 0, NULL, NULL, 7303, '袁林', 7303, '2020-11-27 10:12:00', NULL, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (32, 'E20201120191152442508163', 2, 0, 0, NULL, NULL, 7303, '袁林', 7303, '2020-11-27 10:12:00', NULL, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (33, 'E20201120205640120614603', 2, 0, 0, NULL, NULL, 7303, '袁林', 7303, '2020-11-27 10:12:00', NULL, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (34, 'E20201120205845748190224', 2, 0, 0, NULL, NULL, 7303, '袁林', 7303, '2020-11-27 10:12:00', NULL, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (35, 'E20201121161532695843671', 2, 0, 0, NULL, NULL, 7303, '袁林', 7303, '2020-11-27 10:12:00', NULL, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (36, 'E20201121175548321250229', 2, 0, 0, NULL, NULL, 7303, '袁林', 7303, '2020-11-27 10:12:00', NULL, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (37, 'E20201121205025034749040', 2, 0, 0, NULL, NULL, 7303, '袁林', 7303, '2020-11-27 10:12:00', NULL, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (38, 'E20201121205431875397862', 2, 0, 0, NULL, NULL, 7303, '袁林', 7303, '2020-11-27 10:12:00', NULL, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (39, 'E20201121205831574395034', 2, 0, 0, NULL, NULL, 7303, '袁林', 7303, '2020-11-27 10:12:00', NULL, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (40, 'E20201122111856618952072', 2, 0, 0, NULL, NULL, 7303, '袁林', 7303, '2020-11-27 10:12:00', NULL, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (41, 'E20201122112420291759846', 2, 0, 0, NULL, NULL, 7303, '袁林', 7303, '2020-11-27 10:12:00', NULL, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (42, 'E20201122134630831275940', 2, 0, 0, NULL, NULL, 7303, '袁林', 7303, '2020-11-27 10:12:00', NULL, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (43, 'E20201122135854696704617', 2, 0, 0, NULL, NULL, 7303, '袁林', 7303, '2020-11-27 10:12:00', 7303, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (44, 'E20201123095550656503118', 2, 0, 0, NULL, NULL, 7303, '袁林', 7303, '2020-11-27 10:12:00', NULL, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (45, 'E20201123095901580934082', 2, 0, 0, NULL, NULL, 7303, '袁林', 7303, '2020-11-27 10:12:00', NULL, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (46, 'E20201123163306268598275', 2, 0, 0, NULL, NULL, 7303, '袁林', 7303, '2020-11-27 10:12:00', NULL, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (47, 'E20201123163953447139667', 2, 0, 0, NULL, NULL, 7303, '袁林', 7303, '2020-11-27 10:12:00', NULL, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (48, 'E20201123171016345916255', 2, 0, 0, NULL, NULL, 7928, '王莹', 7303, '2020-11-27 10:12:00', 7303, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (49, 'E20201123172544894857806', 2, 0, 0, NULL, NULL, 7928, '王莹', 7303, '2020-11-27 10:12:00', 7303, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (50, 'E20201123173833946399526', 2, 0, 0, NULL, NULL, 7303, '袁林', 7303, '2020-11-27 10:12:00', NULL, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (51, 'E20201124111205195247039', 2, 0, 0, NULL, NULL, 7303, '袁林', 7303, '2020-11-27 10:12:00', NULL, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (52, 'E20201124162753184834689', 2, 0, 0, NULL, NULL, 7303, '袁林', 7303, '2020-11-27 10:12:00', NULL, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (53, 'E20201124165343478020896', 1, 0, 0, 169, '售后维修组', NULL, NULL, 7303, '2020-11-27 10:12:00', 7303, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (55, 'E20201126151545548520483', 1, 0, 0, NULL, NULL, NULL, NULL, 7303, '2020-11-27 10:12:00', NULL, '2020-11-27 02:56:11');
INSERT INTO `cs_to_be_processed_exception_order` VALUES (56, 'E20201126160023146482792', 1, 0, 0, NULL, NULL, NULL, NULL, 7303, '2020-11-27 10:12:00', NULL, '2020-11-27 02:56:11');

-- ----------------------------
-- Table structure for cs_user
-- ----------------------------
DROP TABLE IF EXISTS `cs_user`;
CREATE TABLE `cs_user`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `wx_work_user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '企业微信的用户主键',
  `user_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '员工工号',
  `nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `is_work` tinyint(2) NOT NULL DEFAULT 0 COMMENT '是否上线接单',
  `online_status` int(3) NULL DEFAULT 1 COMMENT '在线状态，1：在线，2：就餐，3：休息，4培训，-1 离线',
  `is_enabed` tinyint(2) NOT NULL DEFAULT 1 COMMENT '是否启用',
  `last_updated_at` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `last_updated_by` int(20) NULL DEFAULT -100 COMMENT '修改人',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `created_by` int(20) NULL DEFAULT -100 COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8824 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '客服系统，员工' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cs_user
-- ----------------------------
INSERT INTO `cs_user` VALUES (893, 'fengjuyuan', '100216', '冯居原', 0, 1, 1, '2020-10-29 11:53:26', 8167, '2019-08-07 10:03:40', 1);
INSERT INTO `cs_user` VALUES (1339, 'XuJing', '100375', '徐静', 0, 1, 1, '2020-11-16 10:54:07', 7303, '2019-09-23 03:42:02', 1);
INSERT INTO `cs_user` VALUES (1365, 'YuanKun', '100394', '袁坤', 0, 1, 1, '2020-08-13 16:06:22', 1383, '2019-09-23 03:42:05', 1);
INSERT INTO `cs_user` VALUES (3049, 'lihuabing', '101284', '李华兵', 0, 1, 1, '2020-10-14 11:38:05', 7303, '2020-09-17 18:09:35', -1);
INSERT INTO `cs_user` VALUES (3127, 'wangsidi', '101331', '王思頔', 0, 1, 1, '2020-11-09 17:30:12', 7303, '2020-10-12 18:39:38', -1);
INSERT INTO `cs_user` VALUES (7303, 'YuanLin', '袁林', '袁林', 0, NULL, 1, '2020-11-27 18:14:36', 3049, '2019-08-07 02:04:24', 1);
INSERT INTO `cs_user` VALUES (7543, 'YuXiaFei', 'WnnTnG1j', '俞霞菲', 0, 1, 1, '2020-10-19 13:35:11', 885, '2019-12-15 21:50:52', 1338);
INSERT INTO `cs_user` VALUES (7906, 'XueDaWei', 'PEH2LjdB', '薛大伟', 0, 2, 1, '2020-09-19 03:25:05', 1, '2020-06-05 02:39:47', 7303);
INSERT INTO `cs_user` VALUES (7907, 'GuWenJuan', 'FuqWrS2w', '谷文娟', 0, 1, 1, '2020-06-05 16:47:54', 1, '2020-06-05 02:39:47', 7303);
INSERT INTO `cs_user` VALUES (7928, 'WangYing', 'A3C9FORi', '王莹', 2, 3, 1, '2020-10-14 06:49:42', 985, '2020-06-05 02:39:48', 7303);
INSERT INTO `cs_user` VALUES (7929, 'HuYuanYuan', 'vqS4Jpit', '胡媛媛', 0, 1, 1, '2020-09-15 08:30:22', 7303, '2020-06-05 02:39:49', 7303);
INSERT INTO `cs_user` VALUES (7930, 'ZouXiaoJun', 'cvcEMcpu', '邹晓骏', 0, 1, 1, '2020-09-15 08:30:36', 7303, '2020-06-05 02:39:49', 7303);
INSERT INTO `cs_user` VALUES (7960, 'GaoHuiZhong', 'a9SzslZf', '高慧忠', 0, 1, 1, '2020-09-15 08:30:10', 7303, '2020-06-05 02:39:51', 7303);
INSERT INTO `cs_user` VALUES (8002, 'ZhangYueJun', 'xwULRcTe', '张跃俊', 0, 1, 1, '2020-09-15 08:29:53', 7303, '2020-06-05 02:39:54', 7303);
INSERT INTO `cs_user` VALUES (8823, 'zhouzhenbiao', '101127', '周振标', 0, 1, 1, '2020-11-27 14:45:36', 7303, '2020-08-03 18:54:14', -1);

-- ----------------------------
-- Table structure for cs_user_problem_type_asso
-- ----------------------------
DROP TABLE IF EXISTS `cs_user_problem_type_asso`;
CREATE TABLE `cs_user_problem_type_asso`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '员工工号',
  `problem_type` tinyint(2) NOT NULL DEFAULT 1 COMMENT '业务类型1-售后单，2-问题单',
  `sort` int(11) NULL DEFAULT 0 COMMENT '排序越大越前',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `last_updated_at` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `created_by` int(20) NOT NULL COMMENT '创建人',
  `last_updated_by` int(20) NOT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '员工和需要处理的问题的关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cs_user_problem_type_asso
-- ----------------------------
INSERT INTO `cs_user_problem_type_asso` VALUES (6, 'http://dev.launchpad', 1, 0, '2020-10-14 14:44:50', '2020-10-14 14:44:50', 985, 985);
INSERT INTO `cs_user_problem_type_asso` VALUES (7, 'http://dev.launchpad', 2, 0, '2020-10-14 14:44:50', '2020-10-14 14:44:50', 985, 985);
INSERT INTO `cs_user_problem_type_asso` VALUES (8, 'A3C9FORi', 1, 0, '2020-10-14 14:46:47', '2020-10-14 14:46:47', 985, 985);
INSERT INTO `cs_user_problem_type_asso` VALUES (9, 'A3C9FORi', 2, 0, '2020-10-14 14:46:47', '2020-10-14 14:46:47', 985, 985);

-- ----------------------------
-- Table structure for cs_user_status_log
-- ----------------------------
DROP TABLE IF EXISTS `cs_user_status_log`;
CREATE TABLE `cs_user_status_log`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `wx_work_user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '企业微信的用户主键',
  `user_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '员工工号',
  `nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `online_status` int(3) NULL DEFAULT 1 COMMENT '在线状态，1：在线，2：就餐，3：休息，4培训，-1 离线',
  `created_at` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `created_by` int(20) NULL DEFAULT -100 COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8003 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '客服系统，员工' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cs_user_status_log
-- ----------------------------
INSERT INTO `cs_user_status_log` VALUES (7303, 'YuanLin', '袁林', '袁林', NULL, '2019-08-07 02:04:24', 1);
INSERT INTO `cs_user_status_log` VALUES (7906, 'XueDaWei', 'PEH2LjdB', '薛大伟', 2, '2020-06-05 02:39:47', 7303);
INSERT INTO `cs_user_status_log` VALUES (7907, 'GuWenJuan', 'FuqWrS2w', '谷文娟', 1, '2020-06-05 02:39:47', 7303);
INSERT INTO `cs_user_status_log` VALUES (7928, 'WangYing', 'A3C9FORi', '王莹', 3, '2020-06-05 02:39:48', 7303);
INSERT INTO `cs_user_status_log` VALUES (7929, 'HuYuanYuan', 'vqS4Jpit', '胡媛媛', 1, '2020-06-05 02:39:49', 7303);
INSERT INTO `cs_user_status_log` VALUES (7930, 'ZouXiaoJun', 'cvcEMcpu', '邹晓骏', 1, '2020-06-05 02:39:49', 7303);
INSERT INTO `cs_user_status_log` VALUES (7960, 'GaoHuiZhong', 'a9SzslZf', '高慧忠', 1, '2020-06-05 02:39:51', 7303);
INSERT INTO `cs_user_status_log` VALUES (8002, 'ZhangYueJun', 'xwULRcTe', '张跃俊', 1, '2020-06-05 02:39:54', 7303);

SET FOREIGN_KEY_CHECKS = 1;
