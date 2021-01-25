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

 Date: 07/12/2020 14:20:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cs_to_be_processed_after_sales_order_r1
-- ----------------------------
DROP TABLE IF EXISTS `cs_to_be_processed_after_sales_order_r1`;
CREATE TABLE `cs_to_be_processed_after_sales_order_r1`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `aftersales_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '售后单号',
  `process_status` int(255) NULL DEFAULT NULL COMMENT '处理状态 1待处理 2处理中',
  `is_need_help` tinyint(2) NULL DEFAULT 0 COMMENT '求助flag 0 默认不求助 1 求助',
  `is_hang` tinyint(2) NULL DEFAULT 0 COMMENT '挂起flag 0 默认不挂起 1 挂起',
  `group_id` tinyint(2) NULL DEFAULT NULL COMMENT '组Id',
  `group_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组名称',
  `handler_id` int(11) NULL DEFAULT NULL COMMENT '处理人id',
  `handler` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理人名称',
  `unnormal_status` int(1) NULL DEFAULT 0 COMMENT '异常状态 0正常1验货异常客服跟进2验货异常仓库跟进3退款单未生成客服跟进',
  `expire_at` datetime(0) NULL DEFAULT NULL COMMENT '超时时间',
  `customer_id` int(11) NULL DEFAULT NULL COMMENT '客户Id',
  `created_by` int(20) NULL DEFAULT NULL COMMENT '创建人',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `last_updated_by` int(20) NULL DEFAULT NULL COMMENT '修改人',
  `last_updated_at` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `after_sale_no`(`aftersales_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 54 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '待处理r1售后单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cs_to_be_processed_after_sales_order_r1
-- ----------------------------
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (36, 'AS202012050610002', 3, 1, 0, NULL, NULL, 7303, '袁林', NULL, '2020-12-05 19:57:28', 67094, 295, '2020-12-05 17:57:28', 1339, '2020-12-06 13:44:10');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (37, 'AS202012050610003', 2, 0, 0, NULL, NULL, 1339, '徐静', NULL, '2020-12-06 13:43:18', 67094, 295, '2020-12-05 20:43:18', 1339, '2020-12-06 13:52:15');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (38, 'AS202012069930002', 1, 1, 0, NULL, NULL, NULL, NULL, NULL, '2020-12-06 13:40:40', 46603, 290, '2020-12-06 11:40:40', 1339, '2020-12-06 13:44:27');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (39, 'AS202012069910002', 3, 0, 0, NULL, NULL, 1339, '徐静', NULL, '2020-12-06 13:58:49', 46603, 8823, '2020-12-06 11:58:49', 1339, '2020-12-06 18:29:37');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (41, 'AS202012060610003', 1, 1, 1, NULL, NULL, NULL, NULL, NULL, '2020-12-06 15:57:27', 67094, 1339, '2020-12-06 13:57:27', 1365, '2020-12-07 11:00:35');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (42, 'AS202012069920003', 1, 0, 0, NULL, NULL, NULL, NULL, NULL, '2020-12-06 16:13:30', 46603, 8823, '2020-12-06 14:13:30', NULL, NULL);
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (49, 'AS202012069910008', 1, 0, 0, NULL, NULL, NULL, NULL, NULL, '2020-12-07 14:00:18', 46603, 8823, '2020-12-06 21:00:18', NULL, NULL);
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (50, 'AS202012069920004', 1, 0, 0, NULL, NULL, NULL, NULL, NULL, '2020-12-07 14:20:34', 46603, 8823, '2020-12-06 21:20:34', NULL, NULL);
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (51, 'AS202012079930002', 3, 0, 0, NULL, NULL, NULL, NULL, NULL, '2020-12-07 12:43:29', 46603, 2994, '2020-12-07 10:43:29', 2994, '2020-12-07 10:44:11');
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (52, 'AS202012079930003', 1, 0, 0, NULL, NULL, NULL, NULL, NULL, '2020-12-07 12:57:48', 46603, 2994, '2020-12-07 10:57:48', NULL, NULL);
INSERT INTO `cs_to_be_processed_after_sales_order_r1` VALUES (53, 'AS202012070110002', 1, 0, 0, NULL, NULL, NULL, NULL, NULL, '2020-12-07 13:47:55', 5100, 212, '2020-12-07 11:47:55', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
