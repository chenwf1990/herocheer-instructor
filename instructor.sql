
-- 更初始菜单数据 by gaorh 20210222
-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `pid` bigint NULL DEFAULT NULL COMMENT '父菜单',
  `menuName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名',
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '系统管理  [100000]| – 菜单管理 [100100]   | – 角色架构  [100200] | — 消息管理  [110000] | —订单消息 [110100]  ',
  `url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单URL',
  `orderNo` int NULL DEFAULT NULL COMMENT '序号',
  `icon` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` tinyint NULL DEFAULT NULL COMMENT '1：关闭、0：启用',
  `createdId` bigint NULL DEFAULT NULL COMMENT '创建者ID',
  `createdBy` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `createdTime` bigint NULL DEFAULT 0 COMMENT '创建时间',
  `updateId` bigint NULL DEFAULT NULL COMMENT '更新者ID',
  `updateBy` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `updateTime` bigint NULL DEFAULT 0 COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 67 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 9999, '体育指导员', 'A010000', '/sportPolitical/sportPoliticalManage', 1, 'el-icon-user-solid', NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (2, 9999, '驿站管理', 'B020000', '/stageManagement', 2, 'el-icon-s-shop', NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (3, 9999, '招募信息管理', 'C030000', '/recruitInfoManagement', 3, 'el-icon-s-comment', NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (4, 9999, '新闻活动管理', 'D040000', '/newsActiveManagement', 4, 'el-icon-camera-solid', NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (5, 9999, '活动记录管理', 'E050000', '/activeRecordManagement', 5, 'el-icon-video-camera-solid', NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (6, 9999, '值班管理', 'F060000', '/dutyManagement', 6, 'el-icon-s-cooperation', NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (7, 9999, '绩效考核', 'G070000', '/performanceAppraisal', 7, 'el-icon-s-management', NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (8, 9999, '积分管理', 'H080000', '/test', 8, 'test', NULL, 1, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (9, 9999, '系统管理', 'I090000', '/systemManagement', 9, 'el-icon-s-tools', NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (10, 1, '体育指导员管理', 'A010100', '/sportPolitical/sportPoliticalManage', 10, '', NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (11, 1, '体育指导员审核', 'A010200', '/sportPolitical/sportPoliticalAudit', 11, '', NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (12, 3, '招募信息发布', 'C030100', '/recruitInfoManagement/recruitInfoIssue', 12, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (13, 3, '招募信息审核', 'C030200', '/recruitInfoManagement/recruitInfoAudit', 13, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (14, 3, '招募信息查询', 'C030300', '/recruitInfoManagement/recruitInfoCheck', 14, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (15, 3, '驿站招募详情', 'C030400', '/recruitInfoManagement/recruitZmXq', 15, NULL, NULL, 1, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (16, 4, '新闻活动发布', 'D040100', '/newsActiveManagement/newsActiveIssue', 16, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (17, 4, '新闻活动审核', 'D040200', '/newsActiveManagement/newsActiveAudit', 17, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (20, 6, '排班管理', 'F060100', '/dutyManagement/WorkforceManagement', 20, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (21, 6, '值班记录', 'F060200', '/dutyManagement/dutyRecord', 21, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (22, 7, '服务统计', 'G070100', '/performanceAppraisal/serviceStatistics', 22, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (23, 9, '区域管理', 'I090100', '/systemManagement/areaManagement', 23, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (24, 9, '驿站服务时段设置', 'I090200', '/systemManagement/serveTimesManagement', 24, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (25, 9, '微信用户管理', 'I090300', '/systemManagement/weChartManagement', 25, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (26, 9, '机构管理', 'I090400', '/systemManagement/organizationManagement', 26, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (27, 9, '用户管理', 'I090500', '/systemManagement/userManagement', 27, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (28, 9, '角色管理', 'I090600', '/systemManagement/roleManagement', 28, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (29, 9, '字典管理', 'I090700', '/systemManagement/dictionariesManagement', 29, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (30, 9, '模块管理', 'I090800', '/systemManagement/moduleManagement', 30, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (31, 9, '操作日志', 'I099000', '/systemManagement/operationLogManagement', 31, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (60, 5, '活动任务管理', 'E501000', '/activeRecordManagement/index', 18, NULL, NULL, 1, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (61, 5, '活动记录核实', 'E502000', '/activeRecordManagement/verify', 19, NULL, NULL, 1, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (62, 9999, '课程管理', 'J100000', '/classManagement', 32, 'el-icon-notebook-2', NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (63, 62, '课程审核', 'J100100', '/classManagement/classAudit', 33, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (64, 62, '课程查询', 'J100200', '/classManagement/classCheck', 34, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (65, 62, '课程发布', 'J100300', '/classManagement/classIssue', 35, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (66, 62, '课程详情', 'J100400', '/classManagement/classDetail', 36, NULL, NULL, 1, NULL, NULL, 0, NULL, NULL, 0);


-- 用户表添加工作单位字段 by gaorh 20210222
Alter table `user` add column workUnit VARCHAR(255);
ALTER TABLE `user` MODIFY COLUMN workUnit VARCHAR(255) COMMENT '工作单位';