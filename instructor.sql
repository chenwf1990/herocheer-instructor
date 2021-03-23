ALTER TABLE `herocheer_instructor`.`user_collect`
  MODIFY COLUMN `type` int(11) NOT NULL COMMENT '收藏类型 1驿站  2健身视频' AFTER `objectName`;

CREATE TABLE `banner` (
                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                        `name` varchar(64) NOT NULL COMMENT 'banner名称',
                        `position` int(1) NOT NULL COMMENT 'banner位置 1首页',
                        `sort` int(11) NOT NULL COMMENT '排序',
                        `pic` varchar(100) NOT NULL COMMENT '图片地址',
                        `isPublic` int(1) NOT NULL COMMENT '是否上下架 0上架 1下架',
                        `linkType` int(1) NOT NULL COMMENT '链接方式 1url 2课程 3驿站招募 4赛事招募 5新闻',
                        `linkName` varchar(256) DEFAULT NULL COMMENT '链接名称',
                        `linkValue` varchar(256) DEFAULT NULL COMMENT '链接值',
                        `createdId` bigint(20) DEFAULT NULL COMMENT '创建者ID',
                        `createdBy` varchar(30) DEFAULT NULL COMMENT '创建者',
                        `createdTime` bigint(20) DEFAULT '0' COMMENT '创建时间',
                        `updateId` bigint(20) DEFAULT NULL COMMENT '更新者ID',
                        `updateBy` varchar(30) DEFAULT NULL COMMENT '更新者',
                        `updateTime` bigint(20) DEFAULT '0' COMMENT '更新时间',
                        PRIMARY KEY (`id`) USING BTREE,
                        KEY `banner_index01` (`isPublic`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='banner管理';


CREATE TABLE `fitness_video` (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT,
                               `title` varchar(64) NOT NULL COMMENT '健身主题',
                               `content` varchar(512) NOT NULL COMMENT '内容描述',
                               `sort` int(11) DEFAULT '99999' COMMENT '排序',
                               `pic` varchar(256) DEFAULT NULL COMMENT '封面图片地址',
                               `videoUrl` varchar(256) NOT NULL COMMENT '视频地址',
                               `state` int(1) NOT NULL COMMENT '状态 0启用 1禁用',
                               `deptId` bigint(20) NOT NULL COMMENT '机构id',
                               `deptName` varchar(256) DEFAULT NULL COMMENT '机构名称',
                               `browseNum` int(11) DEFAULT NULL COMMENT '浏览数量',
                               `createdId` bigint(20) DEFAULT NULL COMMENT '创建者ID',
                               `createdBy` varchar(30) DEFAULT NULL COMMENT '创建者',
                               `createdTime` bigint(20) DEFAULT '0' COMMENT '创建时间',
                               `updateId` bigint(20) DEFAULT NULL COMMENT '更新者ID',
                               `updateBy` varchar(30) DEFAULT NULL COMMENT '更新者',
                               `updateTime` bigint(20) DEFAULT '0' COMMENT '更新时间',
                               PRIMARY KEY (`id`) USING BTREE,
                               KEY `fitness_video_index01` (`state`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='健身视频管理';


ALTER TABLE `herocheer_instructor`.`course_info`
  MODIFY COLUMN `approvalStatus` int(2) NULL DEFAULT NULL COMMENT '审批状态0待审核 1通过 2驳回 3撤回 4草稿' AFTER `image`;

  -- 调整菜单页面 gaorh 20210323
  drop table if exists sys_menu;

/*==============================================================*/
/* Table: sys_menu                                              */
/*==============================================================*/
CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) DEFAULT NULL COMMENT '父菜单',
  `menuName` varchar(50) NOT NULL COMMENT '菜单名',
  `code` varchar(20) NOT NULL COMMENT '系统管理  [100000]| – 菜单管理 [100100]   | – 角色架构  [100200] | — 消息管理  [110000] | —订单消息 [110100]  ',
  `url` varchar(100) NOT NULL COMMENT '菜单URL',
  `orderNo` int(11) DEFAULT NULL COMMENT '序号',
  `icon` varchar(30) DEFAULT NULL COMMENT '图标',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` tinyint(4) DEFAULT NULL COMMENT '1：关闭、0：启用',
  `createdId` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `createdBy` varchar(30) DEFAULT NULL COMMENT '创建者',
  `createdTime` bigint(20) DEFAULT '0' COMMENT '创建时间',
  `updateId` bigint(20) DEFAULT NULL COMMENT '更新者ID',
  `updateBy` varchar(30) DEFAULT NULL COMMENT '更新者',
  `updateTime` bigint(20) DEFAULT '0' COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='菜单表';

INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (1, 9999, '体育指导员', 'A010000', '/sportPolitical/sportPoliticalManage', 100, 'el-icon-user-solid', NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (2, 9999, '驿站管理', 'B020000', '/stageManagement', 200, 'el-icon-s-shop', NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (3, 9999, '赛事活动管理', 'C030000', '/recruitInfoManagement', 300, 'el-icon-s-comment', NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (9, 9999, '系统管理', 'H080000', '/systemManagement', 900, 'el-icon-s-tools', NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (10, 1, '体育指导员管理', 'A010100', '/sportPolitical/sportPoliticalManage', 110, '', NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (11, 1, '体育指导员审核', 'A010200', '/sportPolitical/sportPoliticalAudit', 120, '', NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (12, 3, '赛事活动招募发布', 'C030100', '/recruitInfoManagement/recruitInfoIssue', 310, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (13, 3, '赛事活动招募审核', 'C030200', '/recruitInfoManagement/recruitInfoAudit', 320, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (14, 3, '赛事活动招募查看', 'C030300', '/recruitInfoManagement/recruitInfoCheck', 330, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (16, 2, '新闻公告发布', 'B020800', '/newsActiveManagement/newsActiveIssue', 280, NULL, NULL, 0, NULL, NULL, 0, 0, 'system', 1616059842536);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (17, 2, '新闻公告审核', 'B020900', '/newsActiveManagement/newsActiveAudit', 290, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (20, 2, '驿站排班管理', 'B020600', '/dutyManagement/WorkforceManagement', 260, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (21, 2, '驿站值班打卡审核', 'B020700', '/dutyManagement/dutyRecord', 270, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (22, 72, '服务时长统计', 'F060400', '/performanceAppraisal/serviceStatistics', 640, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (23, 9, '区域管理', 'H090100', '/systemManagement/areaManagement', 910, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (24, 2, '驿站服务时段设置', 'B020200', '/systemManagement/serveTimesManagement', 220, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (25, 9, '微信用户管理', 'H090200', '/systemManagement/weChartManagement', 920, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (26, 9, '机构管理', 'H090300', '/systemManagement/organizationManagement', 930, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (27, 9, '用户管理', 'H090400', '/systemManagement/userManagement', 940, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (28, 9, '角色管理', 'H090500', '/systemManagement/roleManagement', 950, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (29, 9, '字典管理', 'H090700', '/systemManagement/dictionariesManagement', 970, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (30, 9, '模块管理', 'H090800', '/systemManagement/moduleManagement', 980, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (31, 9, '操作日志', 'H090900', '/systemManagement/operationLogManagement', 990, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (60, 5, '活动任务管理', 'E501000', '/activeRecordManagement/index', 18, NULL, NULL, 1, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (61, 3, '赛事活动打卡审核', 'C030400', '/activeRecordManagement', 340, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (62, 9999, '课程管理', 'D040000', '/classManagement', 400, 'el-icon-notebook-2', NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (63, 62, '培训课程审核', 'D040300', '/classManagement/classAudit', 430, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (64, 62, '培训课程信息查看', 'D040400', '/classManagement/classCheck', 440, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (65, 62, '培训课程发布', 'D040200', '/classManagement/classIssue', 420, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (67, 9999, '保险查询', 'G070000', '/insuranceManagement/insuranceQuery', 700, 'el-icon-suitcase-1\r\n', NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (69, 9, '字典组管理', 'H090600', '/systemManagement/dictionariesGroupManagement', 960, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (70, 9999, 'Banner管理', 'E050000', '/bannerManage/index', 500, 'el-icon-wallet', NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (71, 62, '健身视频发布', 'D040100', '/classManagement/fitnessVideoIssue', 410, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (72, 9999, '积分兑换', 'F060000', '/integralExchange', 600, 'el-icon-coin', NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (74, 2, ' 驿站招募发布', 'B020300', '/stageManagement/stageRecruitInfoIssue', 230, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (75, 2, ' 驿站招募审核', 'B020400', '/stageManagement/stageRecruitInfoAudit', 240, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (76, 2, '驿站招募查询', 'B020500', '/stageManagement/stageRecruitInfoCheck', 250, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (77, 2, '驿站信息管理', 'B020100', '/stageManagement', 210, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (78, 2, '驿站招募详情', 'B021000', '/recruitInfoManagement/recruitZmXq', 299, NULL, NULL, 1, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (79, 62, '课程详情', 'D040500', '/classManagement/classDetail', 499, NULL, NULL, 1, NULL, NULL, 0, NULL, NULL, 0);
