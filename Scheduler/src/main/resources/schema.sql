DROP TABLE IF EXISTS `spring_scheduled_cron`;
CREATE TABLE `spring_scheduled_cron`  (
  `cron_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `cron_key` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '定时任务完整类名',
  `cron_expression` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'cron表达式',
  `task_explain` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '任务描述',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态,1:正常;2:停用',
  PRIMARY KEY (`cron_id`) USING BTREE,
  UNIQUE INDEX `cron_key`(`cron_key`) USING BTREE,
  UNIQUE INDEX `cron_key_unique_idx`(`cron_key`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '定时任务表' ROW_FORMAT = Dynamic;
