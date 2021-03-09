/*
 Navicat Premium Data Transfer

 Source Server         : 3336
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : localhost:3336
 Source Schema         : herocheer_instructor_1

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 09/03/2021 15:41:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity_recruit_approval
-- ----------------------------
DROP TABLE IF EXISTS `activity_recruit_approval`;
CREATE TABLE `activity_recruit_approval`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `recruitId` bigint(20) NOT NULL COMMENT '招募信息主表Id',
  `approvalStatus` int(11) NOT NULL COMMENT '审批状态 (1.通过2.驳回)',
  `approvalComments` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '审批意见',
  `createdId` bigint(20) NOT NULL COMMENT '创建人id',
  `createdBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `createdTime` bigint(20) NOT NULL COMMENT '创建时间',
  `updateId` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `updateBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '活动招募审批' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for activity_recruit_detail
-- ----------------------------
DROP TABLE IF EXISTS `activity_recruit_detail`;
CREATE TABLE `activity_recruit_detail`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `recruitId` bigint(20) NOT NULL COMMENT '招募信息主表ID',
  `serviceStartTime` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '开始服务时间-时分',
  `serviceEndTime` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '结束服务时间-时分',
  `serviceDate` bigint(20) DEFAULT NULL COMMENT '服务日期',
  `recruitNumber` int(11) NOT NULL DEFAULT 0 COMMENT '招募人数',
  `hadRecruitNumber` int(11) DEFAULT 0 COMMENT '已招募人数',
  `createdId` bigint(20) NOT NULL COMMENT '创建人id',
  `createdBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `createdTime` bigint(20) NOT NULL COMMENT '创建时间',
  `updateId` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `updateBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '活动招募-明细' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for activity_recruit_info
-- ----------------------------
DROP TABLE IF EXISTS `activity_recruit_info`;
CREATE TABLE `activity_recruit_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `recruitType` int(11) NOT NULL COMMENT '招募类型 (1.驿站招募2.活动招募)',
  `title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '招募内容',
  `image` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '招募照片',
  `address` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '地址',
  `longitude` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '经度',
  `latitude` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '纬度',
  `matchApproverId` bigint(20) DEFAULT NULL COMMENT '赛事-时长审批人ID',
  `matchApprover` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '赛事-时长审批人',
  `courierStationId` bigint(20) DEFAULT NULL COMMENT '驿站-所属驿站ID',
  `courierStation` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '驿站-所属驿站',
  `serviceStartDate` bigint(20) DEFAULT NULL COMMENT '驿站-服务开始日期',
  `serviceEndDate` bigint(20) DEFAULT NULL COMMENT '驿站-服务结束日期',
  `serviceHours` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '驿站-服务时段',
  `recruitNumber` int(11) DEFAULT NULL COMMENT '驿站-招募人数',
  `status` int(11) NOT NULL COMMENT '状态 (0.待审核1.撤回2.驳回3.招募待启动4.招募中5.招募结束)',
  `approvalStatus` int(11) DEFAULT NULL COMMENT '审批状态 (1.通过2.驳回)',
  `showBanner` int(11) NOT NULL DEFAULT 0 COMMENT '是否在banner显示(0.否1.是)',
  `placedTop` int(11) NOT NULL DEFAULT 0 COMMENT '是否置顶(0.否1.是)',
  `recruitStartDate` bigint(20) NOT NULL COMMENT '开始招募日期',
  `recruitEndDate` bigint(20) NOT NULL COMMENT '结束招募日期',
  `approvalComments` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '审批意见',
  `deptName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '部门名称',
  `isPublic` int(11) NOT NULL DEFAULT 0 COMMENT '是否公开(0.公开1.不公开)',
  `deptId` bigint(20) DEFAULT NULL COMMENT '部门id',
  `createdId` bigint(20) NOT NULL COMMENT '创建人id',
  `createdBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `createdTime` bigint(20) NOT NULL COMMENT '创建时间',
  `updateId` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `updateBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `approvalId` bigint(20) DEFAULT NULL COMMENT '审批人id',
  `approvalBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '审批人',
  `approvalTime` bigint(20) DEFAULT NULL COMMENT '审批时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '招募信息主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for courier_station
-- ----------------------------
DROP TABLE IF EXISTS `courier_station`;
CREATE TABLE `courier_station`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `areaCode` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '区域编码',
  `areaName` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '区域名称',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '驿站名称',
  `address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '驿站地址',
  `longitude` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '经度',
  `latitude` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '纬度',
  `gradeName` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '驿站等级名称',
  `scale` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '规模大小',
  `userId` bigint(20) NOT NULL COMMENT '负责人id',
  `userName` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '负责人名称',
  `pics` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '驿站照片 (最多3张，多个逗号隔开)',
  `openBeginTime` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '驿站开放开始时间段（时分）',
  `openEndTime` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '驿站开放结束时间段（时分）',
  `signScope` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '打卡范围',
  `state` int(1) NOT NULL DEFAULT 0 COMMENT '状态 0启用 1禁用',
  `remarks` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `createdId` bigint(20) NOT NULL COMMENT '创建人',
  `createdBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人名称',
  `createdTime` bigint(20) NOT NULL COMMENT '创建时间',
  `updateId` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updateBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人名称',
  `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `courier_station_index01`(`areaCode`) USING BTREE,
  INDEX `courier_station_index02`(`userId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '驿站' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for course_approval
-- ----------------------------
DROP TABLE IF EXISTS `course_approval`;
CREATE TABLE `course_approval`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `courseId` bigint(20) NOT NULL COMMENT '课程id',
  `approvalStatus` int(2) NOT NULL COMMENT '审批状态',
  `approvalComments` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '审批意见',
  `createdId` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `createdBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `createdTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `updateId` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `updateBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '课程审批 ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for course_info
-- ----------------------------
DROP TABLE IF EXISTS `course_info`;
CREATE TABLE `course_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程标题',
  `type` int(2) NOT NULL COMMENT '课程类型 (1.实践指导动作2.理论课)',
  `lecturerTeacherName` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '授课老师名',
  `lecturerTeacherId` bigint(20) DEFAULT NULL COMMENT '授课老师',
  `courseStartTime` bigint(20) NOT NULL COMMENT '课程开始时间',
  `courseEndTime` bigint(20) NOT NULL COMMENT '课程结束时间',
  `address` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程举办地址',
  `longitude` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '经度',
  `latitude` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '纬度',
  `limitNumber` int(11) NOT NULL COMMENT '限招人数',
  `signNumber` int(11) DEFAULT NULL COMMENT '报名人数',
  `signStartTime` bigint(20) NOT NULL COMMENT '报名开始时间',
  `signEndTime` bigint(20) NOT NULL COMMENT '报名结束时间',
  `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程描述',
  `image` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程照片',
  `approvalStatus` int(2) DEFAULT NULL COMMENT '审批状态',
  `approvalComments` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '审批意见',
  `showBanner` int(2) DEFAULT NULL COMMENT '是否在banner显示 (0.否1.是)',
  `placedTop` int(2) DEFAULT NULL COMMENT '是否置顶 (0.否1.是)',
  `deptName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '部门名称',
  `deptId` bigint(20) DEFAULT NULL COMMENT '部门id',
  `isPublic` int(11) NOT NULL DEFAULT 0 COMMENT '是否公开(0.公开1.不公开)',
  `state` int(2) DEFAULT 0 COMMENT '状态 (0.待审核1.撤回2.驳回3.待报名4.报名中5.报名结束)',
  `createdId` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `createdBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `createdTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `updateId` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `updateBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `approvalId` bigint(20) DEFAULT NULL COMMENT '审批人id',
  `approvalBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '审批人',
  `approvalTime` bigint(20) DEFAULT NULL COMMENT '审批时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '课程信息 ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for instructor
-- ----------------------------
DROP TABLE IF EXISTS `instructor`;
CREATE TABLE `instructor`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `sex` int(11) NOT NULL COMMENT '性别 0未知1男2女',
  `auditState` int(11) DEFAULT 0 COMMENT '审核状态 0待审核1审核通过2审核驳回',
  `auditTime` bigint(20) DEFAULT NULL COMMENT '审核时间',
  `auditIdea` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '审核意见',
  `cardNo` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '身份证号',
  `phone` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `workUnit` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '工作单位',
  `areaCode` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '区域编码',
  `areaName` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '区域名称',
  `guideProject` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '指导项目',
  `guideStation` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '指导站点',
  `certificateNo` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '证书编号',
  `certificateGrade` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '证书等级',
  `openingDate` bigint(20) NOT NULL COMMENT '发证日期',
  `auditUnitType` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '审批单位类型 WLJ_OTHER其他 WLJ_HLQ湖里区文旅局WLJ_SMQ思明区文旅局WLJ_JMQ集美区文旅局WLJ_HCQ海沧文旅局WLJ_XAQ翔安文旅局WLJ_TAQ同安文旅局WLJ_TYJ体育局',
  `auditUnitName` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '审批单位名称',
  `otherAuditUnitName` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '其他审批单位名称',
  `certificatePic` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '证书图片 （最多3张，多张逗号隔开）',
  `channel` int(11) DEFAULT NULL COMMENT '渠道0pc 1H5 2小程序 3ios 4Android',
  `openId` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '微信openId',
  `userId` bigint(20) DEFAULT NULL COMMENT '用户id',
  `headPic` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头像',
  `remarks` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `createdId` bigint(20) DEFAULT NULL COMMENT '创建人',
  `createdBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人名称',
  `createdTime` bigint(20) NOT NULL COMMENT '创建时间',
  `updateId` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updateBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人名称',
  `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `instructor_index02`(`phone`) USING BTREE,
  INDEX `instructor_index01`(`cardNo`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '指导员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for instructor_apply
-- ----------------------------
DROP TABLE IF EXISTS `instructor_apply`;
CREATE TABLE `instructor_apply`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `instructorId` bigint(20) DEFAULT NULL COMMENT '指导员id',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `sex` int(11) NOT NULL COMMENT '性别 0女1男2未知',
  `auditState` int(11) DEFAULT 0 COMMENT '审核状态 0待审核1审核通过2审核驳回',
  `auditTime` bigint(20) DEFAULT NULL COMMENT '审核时间',
  `auditIdea` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '审核意见',
  `cardNo` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '身份证号',
  `phone` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `workUnit` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '工作单位',
  `areaCode` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '区域编码',
  `areaName` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '区域名称',
  `guideProject` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '指导项目',
  `guideStation` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '指导站点',
  `certificateNo` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '证书编号',
  `certificateGrade` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '证书等级',
  `openingDate` bigint(20) NOT NULL COMMENT '发证日期',
  `auditUnitType` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '审批单位类型 WLJ_OTHER其他 WLJ_HLQ湖里区文旅局WLJ_SMQ思明区文旅局WLJ_JMQ集美区文旅局WLJ_HCQ海沧文旅局WLJ_XAQ翔安文旅局WLJ_TAQ同安文旅局WLJ_TYJ体育局',
  `auditUnitName` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '审批单位名称',
  `otherAuditUnitName` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '其他审批单位名称',
  `certificatePic` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '证书图片 （最多3张，多张逗号隔开）',
  `channel` int(11) DEFAULT NULL COMMENT '渠道0pc 1H5 2小程序 3ios 4Android',
  `openId` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '微信openId',
  `userId` bigint(20) DEFAULT NULL COMMENT '用户id',
  `headPic` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头像',
  `token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'token',
  `remarks` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `createdId` bigint(20) DEFAULT NULL COMMENT '创建人',
  `createdBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人名称',
  `createdTime` bigint(20) NOT NULL COMMENT '创建时间',
  `updateId` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updateBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人名称',
  `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `instructor_index01`(`cardNo`, `auditState`) USING BTREE,
  INDEX `instructor_index02`(`phone`, `auditState`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '指导员证书表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for instructor_apply_audit_log
-- ----------------------------
DROP TABLE IF EXISTS `instructor_apply_audit_log`;
CREATE TABLE `instructor_apply_audit_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `applyId` bigint(20) NOT NULL COMMENT '指导员申请单id',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `sex` int(11) NOT NULL COMMENT '性别 0女1男2未知',
  `auditState` int(11) DEFAULT 0 COMMENT '审核状态 0待审核1审核通过2审核驳回',
  `auditTime` bigint(20) DEFAULT NULL COMMENT '审核时间',
  `auditIdea` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '审核意见',
  `cardNo` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '身份证号',
  `phone` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `workUnit` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '工作单位',
  `areaCode` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '区域编码',
  `areaName` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '区域名称',
  `guideProject` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '指导项目',
  `guideStation` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '指导站点',
  `certificateNo` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '证书编号',
  `certificateGrade` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '证书等级',
  `openingDate` bigint(20) NOT NULL COMMENT '发证日期',
  `auditUnitType` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '审批单位类型 WLJ_OTHER其他 WLJ_HLQ湖里区文旅局WLJ_SMQ思明区文旅局WLJ_JMQ集美区文旅局WLJ_HCQ海沧文旅局WLJ_XAQ翔安文旅局WLJ_TAQ同安文旅局WLJ_TYJ体育局',
  `auditUnitName` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '审批单位名称',
  `otherAuditUnitName` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '其他审批单位名称',
  `certificatePic` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '证书图片 （最多3张，多张逗号隔开）',
  `channel` int(11) DEFAULT NULL COMMENT '渠道0pc 1H5 2小程序 3ios 4Android',
  `openId` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '微信openId',
  `userId` bigint(20) DEFAULT NULL COMMENT '用户id',
  `headPic` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头像',
  `remarks` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `createdId` bigint(20) DEFAULT NULL COMMENT '创建人',
  `createdBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人名称',
  `createdTime` bigint(20) NOT NULL COMMENT '创建时间',
  `updateId` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updateBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人名称',
  `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `instructor_index01`(`cardNo`, `auditState`) USING BTREE,
  INDEX `instructor_index02`(`phone`, `auditState`) USING BTREE,
  INDEX `instructor_index03`(`applyId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '指导员证书表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for instrutor_tmp
-- ----------------------------
DROP TABLE IF EXISTS `instrutor_tmp`;
CREATE TABLE `instrutor_tmp`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `identityCard` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT ' ' COMMENT '身份证号',
  `certificatePics` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT ' ' COMMENT '证书url',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for news_notice
-- ----------------------------
DROP TABLE IF EXISTS `news_notice`;
CREATE TABLE `news_notice`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type` int(11) NOT NULL COMMENT '类型 1新闻2活动',
  `pic` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '新闻图片',
  `title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `auditState` int(11) DEFAULT 0 COMMENT '审核状态 0待审核1通过2驳回3撤回4已发布',
  `auditTime` bigint(20) DEFAULT NULL COMMENT '审核时间',
  `auditIdea` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '审核意见',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
  `top` int(11) DEFAULT 0 COMMENT '置顶 0否 1是',
  `deptName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '部门名称',
  `deptId` bigint(20) DEFAULT NULL COMMENT '部门id',
  `remarks` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `isPublic` int(11) NOT NULL DEFAULT 0 COMMENT '是否公开(0.公开1.不公开)',
  `createdId` bigint(20) NOT NULL COMMENT '创建人',
  `createdBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人名称',
  `createdTime` bigint(20) NOT NULL COMMENT '创建时间',
  `updateId` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updateBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人名称',
  `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '新闻公告' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for news_notice_log
-- ----------------------------
DROP TABLE IF EXISTS `news_notice_log`;
CREATE TABLE `news_notice_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `newsNoticeId` bigint(20) NOT NULL COMMENT '新闻id',
  `auditState` int(11) DEFAULT 0 COMMENT '审核状态 0待审核1通过2驳回3撤回4已发布',
  `auditIdea` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '审核意见',
  `remarks` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `createdId` bigint(20) NOT NULL COMMENT '创建人',
  `createdBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人名称',
  `createdTime` bigint(20) NOT NULL COMMENT '创建时间',
  `updateId` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updateBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人名称',
  `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '新闻公告log' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for privilege_area
-- ----------------------------
DROP TABLE IF EXISTS `privilege_area`;
CREATE TABLE `privilege_area`  (
  `id` bigint(20) NOT NULL,
  `areaId` bigint(20) DEFAULT NULL,
  `roleId` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for reservation
-- ----------------------------
DROP TABLE IF EXISTS `reservation`;
CREATE TABLE `reservation`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `relevanceId` bigint(20) NOT NULL COMMENT '关联预约主表id',
  `type` int(2) DEFAULT NULL COMMENT '类型 (1.驿站招募2.赛事招募3.课程排序)',
  `workingId` bigint(20) DEFAULT NULL COMMENT '关联任务id',
  `title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '标题',
  `image` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '照片',
  `startTime` bigint(20) DEFAULT NULL COMMENT '开始时间',
  `endTime` bigint(20) DEFAULT NULL COMMENT '结束时间',
  `address` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '地址',
  `longitude` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '经度',
  `latitude` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '纬度',
  `userId` bigint(20) NOT NULL COMMENT '用户id',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '姓名',
  `identityNumber` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '身份证号',
  `phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '手机号',
  `status` int(2) DEFAULT 0 COMMENT '状态 (0.已预约1.取消预约2.已关闭)',
  `createdId` bigint(20) NOT NULL COMMENT '创建人id',
  `createdBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `createdTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `updateId` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `updateBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '预约记录表 ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for service_hours
-- ----------------------------
DROP TABLE IF EXISTS `service_hours`;
CREATE TABLE `service_hours`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `stationIds` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '驿站Id (多个用,号分隔)',
  `stationNames` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '驿站名称 (多个用,号分隔)',
  `serviceTimes` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '服务时间段',
  `createdId` bigint(20) NOT NULL COMMENT '创建人id',
  `createdBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `createdTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `updateId` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `updateBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '驿站服务时段 ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_area
-- ----------------------------
DROP TABLE IF EXISTS `sys_area`;
CREATE TABLE `sys_area`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) NOT NULL DEFAULT 0 COMMENT '父ID',
  `areaName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT ' ' COMMENT '区域名称',
  `areaCode` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT ' ' COMMENT '省-市-区-街道-社区',
  `chinaCode` char(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '行政编码——350000000000：福建省',
  `level` int(11) DEFAULT NULL COMMENT '级别—— 0：国家、1：省、2：市、3：区 4：街道 5：社区',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `createdId` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `createdBy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `createdTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `updateId` bigint(20) DEFAULT NULL COMMENT '更新者ID',
  `updateBy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者',
  `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 528 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '区域管理（省市区街道社区）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_area
-- ----------------------------
INSERT INTO `sys_area` VALUES (1, 0, '福建省', '01', '350000000000', 1, '0', NULL, NULL, 1610002958, 1, 'chenweifeng', 1610975198089);
INSERT INTO `sys_area` VALUES (2, 1, '厦门市', '0101', '350200000000', 2, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (3, 2, '海沧区', '010101', '350205000000', 3, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (4, 3, '海沧街道', '01010101', '350205001000', 4, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (5, 4, '海沧社区', '0101010101', '350205001001', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (6, 4, '温厝社区', '0101010102', '350205001005', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (7, 4, '海兴社区', '0101010103', '350205001009', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (8, 4, '海沧农场社区', '0101010104', '350205001014', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (9, 4, '青礁村', '0101010105', '350205001209', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (10, 4, '囷瑶村', '0101010106', '350205001207', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (11, 4, '古楼农场', '0101010107', '350205001213', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (12, 4, '渐美村', '0101010108', '350205001205', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (13, 4, '后井村', '0101010109', '350205001210', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (14, 4, '锦里村', '0101010110', '350205001211', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (15, 3, '嵩屿街道', '01010102', '350205003000', 4, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (16, 15, '东屿社区', '0101010201', '350205003007', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (17, 15, '钟山社区', '0101010202', '350205003003', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (18, 15, '海发社区', '0101010203', '350205003001', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (19, 15, '海达社区', '0101010204', '350205003004', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (20, 15, '海翔社区', '0101010205', '350205003009', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (21, 15, '海虹社区', '0101010206', '350205003006', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (22, 15, '海林社区', '0101010207', '350205003010', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (23, 15, '贞庵社区', '0101010208', '350205003202', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (24, 15, '鳌冠社区', '0101010209', '350205003002', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (25, 15, '未来海岸社区', '0101010210', '350205003005', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (26, 15, '北附小社区', '0101010211', '350205003008', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (27, 15, '石塘社区', '0101010212', '350205003201', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (28, 15, '海景社区', '0101010213', '350205003012', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (29, 15, '海盛社区', '0101010214', '350205003011', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (30, 3, '新阳街道', '01010103', '350205002000', 4, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (31, 30, '兴旺社区', '0101010301', '350205002008', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (32, 30, '祥露社区', '0101010302', '350205002007', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (33, 30, '霞阳社区', '0101010303', '350205002006', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (34, 30, '新垵村', '0101010304', '350205002214', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (35, 30, '兴祥社区', '0101010305', '350205002010', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (36, 30, '一农社区', '0101010306', '350205002009', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (37, 30, '新阳文化站', '0101010307', NULL, 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (38, 30, '新阳书院', '0101010308', NULL, 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (39, 3, '东孚街道', '01010104', '350205004000', 4, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (40, 39, '东埔社区', '0101010401', '350205004005', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (41, 39, '凤山社区', '0101010402', '350205004003', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (42, 39, '过坂社区', '0101010403', '350205004002', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (43, 39, '莲花社区', '0101010404', '350205004004', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (44, 39, '山边社区', '0101010405', '350205004006', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (45, 39, '天竺社区', '0101010406', '350205004001', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (46, 39, '寨后社区', '0101010407', '350205004007', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (47, 39, '贞贷村', '0101010408', '350205004211', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (48, 39, '芸尾村', '0101010409', '350205004209', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (49, 39, '后柯村', '0101010410', '350205004207', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (50, 39, '洪塘村', '0101010411', '350205004213', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (51, 39, '东瑶村', '0101010412', '350205004205', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (52, 39, '鼎美村', '0101010413', '350205004206', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (53, 2, '思明区', '010102', '350203000000', 3, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (54, 53, '鼓浪屿街道', '01010201', '350203012000', 4, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (55, 54, '内厝社区', '0101020101', '350203012008', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (56, 54, '龙头社区', '0101020102', '350203012005', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (57, 53, '鹭江社区', '01010202', '350203006000', 4, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (58, 57, '禾祥西', '0101020201', '350203006032', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (59, 57, '小学社区', '0101020202', '350203006029', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (60, 57, '营平社区', '0101020203', '350203006002', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (61, 57, '厦禾社区', '0101020204', '350203006015', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (62, 57, '双莲池社区', '0101020205', '350203006025', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (63, 57, '大同社区', '0101020206', '350203006004', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (64, 57, '鹭江道社区', '0101020207', '350203006009', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (65, 53, '厦港街道', '01010203', '350203001000', 4, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (66, 65, '鸿山社区', '0101020301', '350203001001', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (67, 65, '下沃社区', '0101020302', '350203001011', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (68, 65, '南华社区', '0101020303', '350203001020', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (69, 65, '沙坡尾社区', '0101020304', '350203001018', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (70, 65, '蜂巢山社区', '0101020305', '350203001004', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (71, 65, '巡司顶社区', '0101020306', '350203001015', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (72, 65, '福海社区', '0101020307', '350203001006', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (73, 53, '开元街道', '01010204', '350203007000', 4, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (74, 73, '虎溪社区', '0101020401', '350203007033', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (75, 73, '溪岸社区', '0101020402', '350203007013', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (76, 73, '阳台山社区', '0101020403', '350203007024', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (77, 73, '坑内社区', '0101020404', '350203007034', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (78, 73, '西边社区', '0101020405', '350203007017', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (79, 73, '后江社区', '0101020406', '350203007020', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (80, 73, '希望社区', '0101020407', '350203007029', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (81, 73, '美湖社区', '0101020408', '350203007028', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (82, 73, '美仁社区', '0101020409', '350203007015', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (83, 73, '天湖社区', '0101020410', '350203007038', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (84, 73, '湖滨社区', '0101020411', '350203007021', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (85, 73, '深田社区', '0101020412', '350203007010', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (86, 53, '滨海街道', '01010205', '350203005000', 4, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (87, 86, '白城社区', '0101020501', '350203005002', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (88, 86, '上李社区', '0101020502', '350203005005', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (89, 86, '黄厝社区', '0101020503', '350203005008', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (90, 86, '曾厝垵', '0101020504', '350203005007', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (91, 53, '梧村街道', '01010206', '350203008000', 4, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (92, 91, '金榜山社区', '0101020601', '350203008005', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (93, 91, '文灶社区', '0101020602', '350203008002', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (94, 91, '文屏社区', '0101020603', '350203008015', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (95, 91, '浦南社区', '0101020604', '350203008012', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (96, 91, '东坪社区', '0101020605', '350203008016', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (97, 91, '双涵社区', '0101020606', '350203008011', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (98, 91, '梧村社区', '0101020607', '350203008001', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (99, 91, '万寿北社区', '0101020608', '350203008013', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (100, 91, '溪东社区', '0101020609', '350203008022', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (101, 53, '筼筜街道', '01010207', '350203009000', 4, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (102, 101, '官任社区', '0101020701', '350203009057', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (103, 101, '西郭社区', '0101020702', '350203009007', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (104, 101, '金桥社区', '0101020703', '350203009051', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (105, 101, '湖光社区', '0101020704', '350203009016', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (106, 101, '振兴社区', '0101020705', '350203009021', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (107, 101, '四里社区', '0101020706', '350203009008', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (108, 101, '仙岳社区', '0101020707', '350203009065', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (109, 101, '育秀社区', '0101020708', '350203009038', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (110, 101, '岳阳社区', '0101020709', '350203009029', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (111, 101, '屿后社区', '0101020710', '350203009033', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (112, 101, '仙阁社区', '0101020711', '350203009043', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (113, 53, '嘉莲街道', '01010208', '350203011000', 4, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (114, 113, '莲兴社区', '0101020801', '350203011021', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (115, 113, '长青社区', '0101020802', '350203011027', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (116, 113, '莲花五村社区', '0101020803', '350203011022', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (117, 113, '盈翠社区', '0101020804', '350203011003', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (118, 113, '松柏社区', '0101020805', '350203011040', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (119, 113, '莲花北社区', '0101020806', '350203011004', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (120, 113, '莲西社区', '0101020807', '350203011014', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (121, 113, '莲秀社区', '0101020808', '350203011048', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (122, 113, '龙山社区', '0101020809', '350203011051', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (123, 113, '华福社区', '0101020810', '350203011050', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (124, 53, '莲前街道', '01010209', '350203010000', 4, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (125, 124, '龙山桥社区', '0101020901', '350203010007', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (126, 124, '莲成社区', '0101020902', '350203010035', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (127, 124, '前埔西社区', '0101020903', '350203010038', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (128, 124, '瑞景社区', '0101020904', '350203010026', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (129, 124, '塔埔社区', '0101020905', '350203010032', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (130, 124, '何厝社区', '0101020906', '350203010028', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (131, 124, '莲薇社区', '0101020907', '350203010022', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (132, 124, '莲怡社区', '0101020908', '350203010019', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (133, 124, '莲丰社区', '0101020909', '350203010024', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (134, 124, '洪文社区', '0101020910', '350203010030', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (135, 124, '前埔南社区', '0101020911', '350203010034', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (136, 124, '桥福社区', '0101020912', '350203010025', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (137, 124, '万景社区', '0101020913', '350203010036', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (138, 124, '金鸡亭社区', '0101020914', '350203010016', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (139, 124, '莲云社区', '0101020915', '350203010021', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (140, 124, '莲顺社区', '0101020916', '350203010020', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (141, 124, '莲翔社区', '0101020917', '350203010027', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (142, 124, '前埔东社区', '0101020918', '350203010037', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (143, 124, '前埔北社区', '0101020919', '350203010023', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (144, 124, '西林社区', '0101020920', '350203010031', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (145, 124, '前埔社区', '0101020921', '350203010029', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (146, 124, '岭兜社区', '0101020922', '350203010033', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (147, 124, '观音山社区', '0101020923', '350203010039', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (148, 2, '湖里区', '010103', '350206000000', 3, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (149, 148, '湖里街道', '01010301', '350206001000', 4, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (150, 149, '湖里社区', '0101030101', '350206001001', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (151, 149, '村里社区', '0101030102', '350206001002', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (152, 149, '徐厝社区', '0101030103', '350206001003', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (153, 149, '濠头社区', '0101030104', '350206001004', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (154, 149, '东渡社区', '0101030105', '350206001005', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (155, 149, '塘边社区', '0101030106', '350206001006', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (156, 149, '后浦社区', '0101030107', '350206001007', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (157, 149, '金鼎社区', '0101030108', '350206001011', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (158, 149, '南山社区', '0101030109', '350206001013', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (159, 149, '东荣社区', '0101030110', '350206001015', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (160, 149, '康乐社区', '0101030111', '350206001017', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (161, 149, '康晖社区', '0101030112', '350206001018', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (162, 149, '康泰社区', '0101030113', '350206001019', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (163, 149, '怡景社区', '0101030114', '350206001023', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (164, 149, '和通社区', '0101030115', '350206001025', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (165, 149, '新港社区', '0101030116', '350206001026', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (166, 149, '海天社区', '0101030117', '350206001028', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (167, 149, '兴华社区', '0101030118', '350206001029', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (168, 148, '殿前街道', '01010302', '350206002000', 4, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (169, 168, '兴隆社区', '0101030201', '350206002004', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (170, 168, '长乐社区', '0101030202', '350206002005', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (171, 168, '北站社区', '0101030203', '350206002006', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (172, 168, '神山社区', '0101030204', '350206002007', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (173, 168, '高殿社区', '0101030205', '350206002008', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (174, 168, '马垅社区', '0101030206', '350206002009', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (175, 168, '兴园社区', '0101030207', '350206002010', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (176, 168, '嘉福社区', '0101030208', '350206002011', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (177, 148, '禾山社区', '01010303', '350206003000', 4, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (178, 177, '坂尚社区', '0101030301', '350206003011', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (179, 177, '钟宅社区', '0101030302', '350206003012', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (180, 177, '岭下社区', '0101030303', '350206003019', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (181, 177, '围里社区', '0101030304', '350206003020', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (182, 177, '枋湖社区', '0101030305', '350206003010', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (183, 177, '禾欣社区', '0101030306', '350206003021', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (184, 177, '禾盛社区', '0101030307', '350206003024', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (185, 177, '禾山社区', '0101030308', '350206003023', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (186, 177, '禾缘社区', '0101030309', '350206003024', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (187, 177, '五缘湾北社区', '0101030310', '350206003025', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (188, 148, '江头街道', '01010304', '350206004000', 4, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (189, 188, '江头社区', '0101030401', '350206004001', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (190, 188, '吕厝社区', '0101030402', '350206004002', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (191, 188, '吕岭社区', '0101030403', '350206004003', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (192, 188, '园山社区', '0101030404', '350206004005', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (193, 188, '金尚社区', '0101030405', '350206004006', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (194, 188, '江村社区', '0101030406', '350206004016', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (195, 188, '蔡塘社区', '0101030407', '350206004017', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (196, 188, '后埔社区', '0101030408', '350206004018', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (197, 188, '金泰社区', '0101030409', NULL, 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (198, 148, '金山街道', '01010305', '350206005000', 4, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (199, 198, '高林社区', '0101030501', '350206005013', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (200, 198, '五通社区', '0101030502', '350206005014', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (201, 198, '后坑社区', '0101030503', '350206005015', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (202, 198, '金山社区', '0101030504', '350206005021', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (203, 198, '金林社区', '0101030505', '350206005022', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (204, 198, '金安社区', '0101030506', '350206005023', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (205, 198, '金海社区', '0101030507', '350206005026', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (206, 198, '湖边社区', '0101030508', '350206005024', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (207, 198, '金湖社区', '0101030509', '350206005025', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (208, 2, '同安区', '010104', '350212000000', 3, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (209, 208, '大同街道', '01010401', '350212001000', 4, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (210, 209, '同新社区', '0101040101', '350212001002', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (211, 209, '西安社区', '0101040102', '350212001003', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (212, 209, '三秀社区', '0101040103', '350212001004', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (213, 209, '后炉社区', '0101040104', '350212001005', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (214, 209, '溪边社区', '0101040105', '350212001007', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (215, 209, '城西社区', '0101040106', '350212001011', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (216, 209, '凤山社区', '0101040107', '350212001012', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (217, 209, '北门社区', '0101040108', '350212001013', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (218, 209, '西池社区', '0101040109', '350212001014', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (219, 209, '朝元社区', '0101040110', '350212001017', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (220, 209, '东山社区', '0101040111', '350212001018', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (221, 209, '碧岳社区', '0101040112', '350212001019', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (222, 209, '田洋村', '0101040113', '350212001203', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (223, 209, '古庄村', '0101040114', '350212001204', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (224, 209, '顶溪头村', '0101040115', '350212001207', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (225, 209, '东宅村', '0101040116', '350212001214', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (226, 209, '下溪头村', '0101040117', '350212001215', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (227, 209, '康浔村', '0101040118', '350212001216', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (228, 208, '祥平街道', '01010402', '350212002000', 4, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (229, 228, '西溪社区', '0101040201', '350212002001', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (230, 228, '陆丰社区', '0101040202', '350212002012', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (231, 228, '祥平社区', '0101040203', '350212002013', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (232, 228, '祥桥社区', '0101040204', '350212002014', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (233, 228, '杜桥社区', '0101040205', '350212002015', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (234, 228, '西洪塘社区', '0101040206', '350212002016', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (235, 228, '西湖社区', '0101040207', '350212002017', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (236, 228, '凤岗社区', '0101040208', '350212002018', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (237, 228, '溪声社区', '0101040209', '350212002019', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (238, 228, '阳翟社区', '0101040210', '350212002020', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (239, 228, '祥晖社区', '0101040211', '350212002021', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (240, 228, '芸溪社区', '0101040212', '350212002022', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (241, 228, '过溪村', '0101040213', '350212002202', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (242, 228, '卿朴村', '0101040214', '350212002217', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (243, 228, '瑶头村', '0101040215', '350212002219', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (244, 208, '莲花镇', '01010403', '350212105000', 4, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (245, 244, '莲花村', '0101040301', '350212105201', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (246, 244, '后埔村', '0101040302', '350212105202', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (247, 244, '蔗内村', '0101040303', '350212105203', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (248, 244, '内田村', '0101040304', '350212105204', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (249, 244, '上陵村', '0101040305', '350212105205', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (250, 244, '军营村', '0101040306', '350212105206', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (251, 244, '淡溪村', '0101040307', '350212105207', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (252, 244, '白交祠村', '0101040308', '350212105208', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (253, 244, '西坑村', '0101040309', '350212105209', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (254, 244, '罗溪村', '0101040310', '350212105210', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (255, 244, '尾林村', '0101040311', '350212105211', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (256, 244, '水洋村', '0101040312', '350212105212', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (257, 244, '小坪村', '0101040313', '350212105213', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (258, 244, '澳溪村', '0101040314', '350212105214', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (259, 244, '云埔村', '0101040315', '350212105215', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (260, 244, '云洋村', '0101040316', '350212105216', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (261, 244, '窑市村', '0101040317', '350212105217', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (262, 244, '溪东村', '0101040318', '350212105218', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (263, 244, '美埔村', '0101040319', '350212105219', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (264, 208, '新民镇', '01010404', '350212106000', 4, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (265, 264, '四口圳社区', '0101040401', '350212106001', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (266, 264, '后宅社区', '0101040402', '350212106002', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (267, 264, '禾山社区', '0101040403', '350212106003', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (268, 264, '梧侣社区', '0101040404', '350212106004', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (269, 264, '乌涂社区', '0101040405', '350212106005', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (270, 264, '西塘社区', '0101040406', '350212106006', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (271, 264, '湖安社区', '0101040407', '350212106007', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (272, 264, '洋厝埔村', '0101040408', '350212106207', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (273, 264, '西山村', '0101040409', '350212106208', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (274, 264, '蔡宅村', '0101040410', '350212106209', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (275, 264, '湖柑村', '0101040411', '350212106210', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (276, 264, '柑岭村', '0101040412', '350212106211', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (277, 264, '溪林村', '0101040413', '350212106212', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (278, 264, '南山村', '0101040414', '350212106214', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (279, 264, '新塘村', '0101040415', '350212106215', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (280, 264, '土楼村', '0101040416', '350212106216', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (281, 208, '洪塘镇', '01010405', '350212107000', 4, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (282, 281, '石浔社区', '0101040501', '350212107001', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (283, 281, '龙东社区', '0101040502', '350212107002', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (284, 281, '龙西社区', '0101040503', '350212107003', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (285, 281, '洪塘村', '0101040504', '350212107201', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (286, 281, '三忠村', '0101040505', '350212107202', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (287, 281, '苏店村', '0101040506', '350212107203', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (288, 281, '新霞村', '0101040507', '350212107204', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (289, 281, '郭山村', '0101040508', '350212107205', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (290, 281, '龙泉村', '0101040509', '350212107206', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (291, 281, '新厝村', '0101040510', '350212107207', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (292, 281, '新学村', '0101040511', '350212107208', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (293, 281, '苏厝村', '0101040512', '350212107209', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (294, 281, '大乡村', '0101040513', '350212107213', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (295, 281, '下墩村', '0101040514', '350212107214', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (296, 281, '塘边村', '0101040515', '350212107215', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (297, 281, '埔后村', '0101040516', '350212107216', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (298, 208, '西柯镇', '01010406', '350212108000', 4, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (299, 298, '丙洲社区', '0101040601', '350212108001', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (300, 298, '潘涂社区', '0101040602', '350212108002', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (301, 298, '洪塘头社区', '0101040603', '350212108003', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (302, 298, '后田社区', '0101040604', '350212108004', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (303, 298, '西柯社区', '0101040605', '350212108005', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (304, 298, '吕厝社区', '0101040606', '350212108006', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (305, 298, '浦头社区', '0101040607', '350212108007', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (306, 298, '下山头社区', '0101040608', '350212108008', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (307, 298, '埭头社区', '0101040609', '350212108009', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (308, 298, '官浔社区', '0101040610', '350212108010', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (309, 298, '美人山社区', '0101040611', '350212108011', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (310, 298, '滨海社区', '0101040612', '350212108012', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (311, 298, '怡海社区', '0101040613', '350212108013', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (312, 298, '西浦村', '0101040614', '350212108205', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (313, 298, '美星村', '0101040615', '350212108210', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (314, 208, '汀溪镇', '01010407', '350212109000', 4, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (315, 314, '隘头村', '0101040701', '350212109201', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (316, 314, '路下村', '0101040702', '350212109202', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (317, 314, '褒美村', '0101040703', '350212109203', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (318, 314, '古坑村', '0101040704', '350212109204', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (319, 314, '西源村', '0101040705', '350212109205', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (320, 314, '荏畲村', '0101040706', '350212109206', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (321, 314, '顶村村', '0101040707', '350212109207', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (322, 314, '堤内村', '0101040708', '350212109208', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (323, 314, '半岭村', '0101040709', '350212109209', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (324, 314, '前格村', '0101040710', '350212109210', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (325, 314, '五峰村', '0101040711', '350212109211', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (326, 314, '汪前村', '0101040712', '350212109212', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (327, 314, '造水村', '0101040713', '350212109213', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (328, 208, '五显镇', '01010408', '350212110000', 4, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (329, 328, '垵炉村', '0101040801', '350212110201', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (330, 328, '下峰村', '0101040802', '350212110202', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (331, 328, '布塘村', '0101040803', '350212110203', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (332, 328, '店仔村', '0101040804', '350212110204', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (333, 328, '溪西村', '0101040805', '350212110205', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (334, 328, '竹山村', '0101040806', '350212110206', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (335, 328, '后垄村', '0101040807', '350212110207', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (336, 328, '军村村', '0101040808', '350212110208', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (337, 328, '后塘村', '0101040809', '350212110209', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (338, 328, '上厝村', '0101040810', '350212110210', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (339, 328, '明溪村', '0101040811', '350212110211', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (340, 328, '宋宅村', '0101040812', '350212110212', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (341, 328, '四林村', '0101040813', '350212110213', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (342, 328, '西洋村', '0101040814', '350212110214', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (343, 328, '三秀山村', '0101040815', '350212110215', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (344, 2, '集美区', '010105', '350211000000', 3, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (345, 344, '侨英街道', '01010501', '350211002000', 4, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (346, 345, '浒井社区', '0101050101', '350211002001', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (347, 345, '叶厝社区', '0101050102', '350211002001', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (348, 345, '凤林美社区', '0101050103', '350211002003', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (349, 345, '孙厝社区', '0101050104', '350211002002', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (350, 345, '东安社区', '0101050105', '350211002006', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (351, 345, '兑山社区', '0101050106', '350211002001', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (352, 344, '杏滨街道', '01010502', '350211004000', 4, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (353, 352, '锦鹤社区', '0101050201', '350211004002', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (354, 352, '日东社区', '0101050202', '350211004007', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (355, 352, '三秀社区', '0101050203', '350211004011', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (356, 352, '马銮社区', '0101050204', '350211004012', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (357, 352, '西滨社区', '0101050205', '350211004013', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (358, 352, '前场社区', '0101050206', '350211004014', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (359, 352, '锦园社区', '0101050207', '350211004015', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (360, 352, '康城社区', '0101050208', '350211004016', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (361, 344, '集美街道', '01010503', '350211001000', 4, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (362, 361, '银亭社区', '0101050301', '350211001012', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (363, 361, '岑东社区', '0101050302', '350211001001', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (364, 361, '岑西社区', '0101050303', '350211001002', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (365, 361, '浔江社区', '0101050304', '350211001005', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (366, 361, '盛光社区', '0101050305', '350211001006', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (367, 344, '杏林街道', '01010504', '350211003000', 4, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (368, 367, '宁宝社区', '0101050401', '350211003012', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (369, 367, '纺织社区', '0101050402', '350211003003', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (370, 367, '曾营社区', '0101050403', '350211003008', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (371, 367, '杏北社区', '0101050404', '350211003017', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (372, 367, '高浦社区', '0101050405', '350211003016', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (373, 367, '杏林社区', '0101050406', '350211003014', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (374, 367, '西亭社区', '0101050407', '350211003013', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (375, 367, '内林社区', '0101050408', '350211003015', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (376, 344, '灌口镇', '01010505', '350211102000', 4, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (377, 376, '第一社区', '0101050501', '350211102001', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (378, 376, '第二社区', '0101050502', '350211102002', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (379, 376, '铁山社区', '0101050503', '350211102004', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (380, 376, '双乐社区', '0101050504', '350211102006', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (381, 376, '黄庄社区', '0101050505', '350211102005', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (382, 376, '上头亭社区', '0101050506', '350211102003', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (383, 376, '李林村', '0101050507', '350211102206', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (384, 376, '东辉村', '0101050508', '350211102207', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (385, 376, '陈井村', '0101050509', '350211102213', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (386, 376, '上塘村', '0101050510', '350211102204', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (387, 376, '顶许村', '0101050511', '350211102208', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (388, 376, '三社村', '0101050512', '350211102210', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (389, 376, '田头村', '0101050513', '350211102203', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (390, 376, '坑内村', '0101050514', '350211102201', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (391, 376, '深青村', '0101050515', '350211102202', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (392, 376, '浦林村', '0101050516', '350211102214', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (393, 376, '双岭村', '0101050517', '350211102205', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (394, 376, '井城村', '0101050518', '350211102212', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (395, 344, '后溪镇', '01010506', '350211103000', 4, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (396, 395, '新村社区', '0101050601', '350211103001', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (397, 395, '英村社区', '0101050602', '350211103002', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (398, 395, '三兴社区', '0101050603', '350211103003', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (399, 395, '前进村', '0101050604', '350211103202', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (400, 395, '溪西村', '0101050605', '350211103203', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (401, 395, '后溪村', '0101050606', '350211103204', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (402, 395, '仑上村', '0101050607', '350211103205', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (403, 395, '崎沟村', '0101050608', '350211103206', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (404, 395, '后垵村', '0101050609', '350211103208', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (405, 395, '东宅村', '0101050610', '350211103207', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (406, 395, '岩内村', '0101050611', '350211103209', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (407, 395, '黄地村', '0101050612', '350211103213', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (408, 2, '翔安区', '010106', '350213000000', 3, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (409, 408, '大嶝街道', '01010601', '350213001000', 4, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (410, 409, '北门社区', '0101060101', '350213001007', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (411, 409, '嶝崎社区', '0101060102', '350213001004', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (412, 409, '东埕社区', '0101060103', '350213001008', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (413, 409, '山头社区', '0101060104', '350213001002', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (414, 409, '双沪社区', '0101060105', '350213001005', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (415, 409, '田墘社区', '0101060106', '350213001001', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (416, 409, '小嶝社区', '0101060107', '350213001009', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (417, 409, '蟳窟社区', '0101060108', '350213001003', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (418, 409, '阳塘社区', '0101060109', '350213001006', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (419, 408, '马巷镇', '01010602', '350213102000', 4, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (420, 419, '垵边社区', '0101060201', '350213102020', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (421, 419, '滨安社区', '0101060202', '350213102035', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (422, 419, '蔡浦社区', '0101060203', '350213102011', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (423, 419, '陈新社区', '0101060204', '350213102009', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (424, 419, '城场社区', '0101060205', '350213102028', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (425, 419, '窗东社区', '0101060206', '350213102010', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (426, 419, '舫阳社区', '0101060207', '350213102026', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (427, 419, '何厝社区', '0101060208', '350213102032', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (428, 419, '洪溪社区', '0101060209', '350213102021', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (429, 419, '后滨社区', '0101060210', '350213102007', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (430, 419, '后莲社区', '0101060211', '350213102015', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (431, 419, '后亭社区', '0101060212', '350213102004', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (432, 419, '后许社区', '0101060213', '350213102017', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (433, 419, '井头社区', '0101060214', '350213102027', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (434, 419, '黎安社区', '0101060215', '350213102018', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (435, 419, '内垵社区', '0101060216', '350213102019', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (436, 419, '内官社区', '0101060217', '350213102031', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (437, 419, '前庵社区', '0101060218', '350213102030', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (438, 419, '琼头社区', '0101060219', '350213102008', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (439, 419, '三乡社区', '0101060220', '350213102003', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (440, 419, '山亭社区', '0101060221', '350213102012', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (441, 419, '沈井社区', '0101060222', '350213102029', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (442, 419, '市头社区', '0101060223', '350213102033', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (443, 419, '亭洋社区', '0101060224', '350213102013', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (444, 419, '同美社区', '0101060225', '350213102022', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (445, 419, '桐梓社区', '0101060226', '350213102006', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (446, 419, '五美社区', '0101060227', '350213102001', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (447, 419, '五星社区', '0101060228', '350213102005', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (448, 419, '西坂社区', '0101060229', '350213102025', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (449, 419, '西炉社区', '0101060230', '350213102023', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (450, 419, '友民社区', '0101060231', '350213102002', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (451, 419, '曾林社区', '0101060232', '350213102016', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (452, 419, '赵厝社区', '0101060233', '350213102024', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (453, 419, '郑坂社区', '0101060234', '350213102014', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (454, 419, '朱坑社区', '0101060235', '350213102034', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (455, 408, '内厝镇', '01010603', '350213111000', 4, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (456, 455, '锄山村', '0101060301', '350213111214', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (457, 455, '官路村', '0101060302', '350213111211', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (458, 455, '鸿山村', '0101060303', '350213111216', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (459, 455, '后垵村', '0101060304', '350213111203', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (460, 455, '后田村', '0101060305', '350213111217', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (461, 455, '黄厝村', '0101060306', '350213111204', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (462, 455, '莲前村', '0101060307', '350213111207', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (463, 455, '莲塘村', '0101060308', '350213111206', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (464, 455, '美山村', '0101060309', '350213111212', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (465, 455, '前垵村', '0101060310', '350213111202', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (466, 455, '琼坑村', '0101060311', '350213111215', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (467, 455, '上塘社区', '0101060312', '350213111001', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (468, 455, '霞美村', '0101060313', '350213111208', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (469, 455, '新垵村', '0101060314', '350213111213', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (470, 455, '许厝村', '0101060315', '350213111205', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (471, 455, '曾厝村', '0101060316', '350213111210', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (472, 455, '赵岗村', '0101060317', '350213111209', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (473, 408, '新店镇', '01010604', '350213104000', 4, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (474, 473, '垵山社区', '0101060401', '350213104031', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (475, 473, '澳头社区', '0101060402', '350213104021', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (476, 473, '蔡厝社区', '0101060403', '350213104026', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (477, 473, '陈塘社区', '0101060404', '350213104027', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (478, 473, '大宅社区', '0101060405', '350213104008', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (479, 473, '东界社区', '0101060406', '350213104032', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (480, 473, '东坑社区', '0101060407', '350213104013', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (481, 473, '东园社区', '0101060408', '350213104028', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (482, 473, '鼓锣社区', '0101060409', '350213104035', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (483, 473, '珩厝社区', '0101060410', '350213104006', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (484, 473, '洪厝社区', '0101060411', '350213104015', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (485, 473, '洪前社区', '0101060412', '350213104014', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (486, 473, '后村社区', '0101060413', '350213104025', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (487, 473, '湖头社区', '0101060414', '350213104012', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (488, 473, '莲河社区', '0101060415', '350213104003', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (489, 473, '刘五店社区', '0101060416', '350213104018', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (490, 473, '炉前社区', '0101060417', '350213104016', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (491, 473, '吕塘社区', '0101060418', '350213104009', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (492, 473, '茂林社区', '0101060419', '350213104007', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (493, 473, '欧厝社区', '0101060420', '350213104022', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (494, 473, '彭厝社区', '0101060421', '350213104023', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (495, 473, '浦边社区', '0101060422', '350213104034', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (496, 473, '浦园社区', '0101060423', '350213104019', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (497, 473, '前浯社区', '0101060424', '350213104024', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (498, 473, '沙美社区', '0101060425', '350213104029', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (499, 473, '西滨社区', '0101060426', '350213104020', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (500, 473, '溪尾社区', '0101060427', '350213104010', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (501, 473, '霞浯社区', '0101060428', '350213104004', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (502, 473, '下后滨社区', '0101060429', '350213104017', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (503, 473, '下许社区', '0101060430', '350213104030', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (504, 473, '祥吴社区', '0101060431', '350213104011', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (505, 473, '霄垄社区', '0101060432', '350213104005', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (506, 473, '新店社区', '0101060433', '350213104002', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (507, 473, '新兴社区', '0101060434', '350213104001', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (508, 473, '钟宅社区', '0101060435', '350213104033', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (509, 408, '新圩镇', '01010605', '350213103000', 4, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (510, 509, '村尾村', '0101060501', '350213103206', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (511, 509, '东寮社区', '0101060502', '350213103002', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (512, 509, '凤路村', '0101060503', '350213103205', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (513, 509, '古宅村', '0101060504', '350213103202', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (514, 509, '桂林村', '0101060505', '350213103213', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (515, 509, '后埔村', '0101060506', '350213103203', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (516, 509, '后亭村', '0101060507', '350213103215', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (517, 509, '金柄村', '0101060508', '350213103204', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (518, 509, '马塘村', '0101060509', '350213103216', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (519, 509, '面前埔村', '0101060510', '350213103209', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (520, 509, '上宅村', '0101060511', '350213103210', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (521, 509, '诗坂村', '0101060512', '350213103211', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (522, 509, '乌山村', '0101060513', '350213103207', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (523, 509, '新圩社区', '0101060514', '350213103001', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (524, 509, '云头村', '0101060515', '350213103208', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (525, 509, '庄垵村', '0101060516', '350213103214', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (526, 101, ' 一里社区', '0101020712', '350203009017', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);
INSERT INTO `sys_area` VALUES (527, 4, '高荣华', 'gaorh', '123456789', 4, '测试', 1, '超级管理员', 1614657483980, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) DEFAULT NULL COMMENT '父级id',
  `deptName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门名称或下属机构名称',
  `level` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '机构级别：Level的设计原则：0.*.* 其中，0是顶级结构，第一个*是顶级结构下的结构，第二个*是顶级结构下的结构下的结构',
  `sortNo` bigint(20) DEFAULT NULL COMMENT '排序号',
  `status` tinyint(4) DEFAULT NULL COMMENT '0：关闭、1：启用',
  `createdId` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `createdBy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `createdTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `updateId` bigint(20) DEFAULT NULL COMMENT '更新者ID',
  `updateBy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者',
  `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `parentName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '上级名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '机构表、部门表、部门架构表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '父ID',
  `dictName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典名称',
  `dictCode` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典编码使用汉字拼音，如：订单状态——DTZT',
  `sortNo` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `status` tinyint(4) DEFAULT NULL COMMENT '0：关闭、1：启用',
  `createdId` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `createdBy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `createdTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `updateId` bigint(20) DEFAULT NULL COMMENT '更新者ID',
  `updateBy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者',
  `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `AK_UQ_dictCode`(`dictCode`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 189 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1, 'ZDXM', '扇舞', 'SW', '4', NULL, 1, NULL, NULL, NULL, 1, '超级管理员', 1615195008191);
INSERT INTO `sys_dict` VALUES (2, 'ZDXM', '绸舞', 'CW', '2', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (3, 'ZDXM', '锅庄舞', 'GZW', '3', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (4, 'ZDXM', ' 健身舞', 'JSW', '4', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (5, 'ZDXM', ' 健身操舞', 'JSCW', '5', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (6, 'ZDXM', ' 健身气功', 'JSQG', '6', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (7, 'ZDXM', ' 健身拳', 'JSQ', '7', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (8, 'ZDXM', ' 健身舞蹈', 'JSWD', '8', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (9, 'ZDXM', ' 健身秧歌', 'JSYG', '9', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (10, 'ZDXM', ' 木兰拳', 'MLQ', '10', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (11, 'ZDXM', ' 木兰剑', 'MLJ', '11', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (12, 'ZDXM', ' 木兰扇', 'MLS', '12', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (13, 'ZDXM', ' 太极拳', 'TJQ', '13', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (14, 'ZDXM', ' 太极剑', 'TJJ', '14', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (15, 'ZDXM', ' 太极扇', 'TJS', '15', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (16, 'ZDXM', ' 广播体操', 'GBTC', '16', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (17, 'ZDXM', ' 拳操', 'QC', '17', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (18, 'ZDXM', ' 体质监测', 'TZJC', '18', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (19, 'ZDXM', ' 腰鼓', 'YG', '19', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (20, 'ZDXM', ' 瑜伽', 'YJ', '20', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (21, 'ZDXM', ' 老年花铃', 'LNHL', '21', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (22, 'ZDXM', ' 跳绳', 'TS', '22', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (23, 'ZDXM', ' 踢毽子', 'TJZ', '23', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (24, 'ZDXM', ' 柔力球', 'RLQ', '24', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (25, 'ZDXM', ' 五禽戏', 'WQX', '25', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (26, 'ZDXM', ' 洗髓经', 'XSJ', '26', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (27, 'ZDXM', ' 长剑', 'CJ', '27', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (28, 'ZDXM', ' 划船', 'HC', '28', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (29, 'ZDXM', ' 体育保健', 'TYBJ', '29', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (30, 'ZDXM', ' 功夫扇', 'GFS', '30', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (31, 'ZDXM', ' 体验式拓展培训师', 'TYSTZPXS', '31', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (32, 'ZDXM', ' 户外运动领队', 'HWYDLD', '32', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (33, 'ZDXM', ' 攀岩保护员', 'PYBHY', '33', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (34, 'ZDXM', ' 其他', 'QT', '34', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (35, 'ZDXM', '田径', 'TJ', '35', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (36, 'ZDXM', ' 游泳', 'YY', '36', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (37, 'ZDXM', ' 花样游泳', 'HYYY', '37', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (38, 'ZDXM', ' 潜水（含蹼泳）', 'QS', '38', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (39, 'ZDXM', ' 皮划艇', 'PHT', '39', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (40, 'ZDXM', ' 救生', 'JS', '40', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (41, 'ZDXM', ' 跳水', 'TS01', '41', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (42, 'ZDXM', ' 帆船（板）', 'FC', '42', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (43, 'ZDXM', ' 龙舟', 'LZ', '43', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (44, 'ZDXM', ' 赛艇', 'ST', '44', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (45, 'ZDXM', ' 飞艇', 'FT', '45', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (46, 'ZDXM', ' 摩托艇', 'MTT', '46', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (47, 'ZDXM', ' 航海模型', 'HHMX', '47', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (48, 'ZDXM', ' 风筝', 'FZ', '48', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (49, 'ZDXM', ' 热气球', 'RQQ', '49', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (50, 'ZDXM', ' 牵引伞', 'QYS', '50', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (51, 'ZDXM', ' 动力伞', 'DLS', '51', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (52, 'ZDXM', ' 轻型飞机', 'QXFJ', '52', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (53, 'ZDXM', ' 滑翔机', 'HXJ', '53', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (54, 'ZDXM', ' 滑翔伞', 'HXS', '54', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (55, 'ZDXM', ' 飞机跳伞', 'FJTS', '55', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (56, 'ZDXM', ' 超轻型飞机', 'CQXFJ', '56', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (57, 'ZDXM', ' 航天模型', 'HTMX', '57', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (58, 'ZDXM', ' 航空模型', 'HKMX', '58', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (59, 'ZDXM', ' 悬挂滑翔', 'XGHX', '59', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (60, 'ZDXM', ' 攀岩', 'PY', '60', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (61, 'ZDXM', ' 登山', 'DS', '61', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (62, 'ZDXM', ' 户外山地', 'HWSD', '62', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (63, 'ZDXM', ' 自行车', 'ZXC', '63', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (64, 'ZDXM', ' 摩托车', 'MTC', '64', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (65, 'ZDXM', ' 乒乓球', 'PPQ', '65', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (66, 'ZDXM', ' 羽毛球', 'YMQ', '66', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (67, 'ZDXM', ' 篮球', 'LQ', '67', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (68, 'ZDXM', ' 足球', 'ZQ', '68', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (69, 'ZDXM', ' 排球', 'PQ', '69', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (70, 'ZDXM', ' 棒球', 'BQ', '70', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (71, 'ZDXM', ' 橄榄球', 'GLQ', '71', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (72, 'ZDXM', ' 壁球', 'BQ01', '72', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (73, 'ZDXM', ' 门球', 'MQ', '73', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (74, 'ZDXM', ' 网球', 'WQ', '74', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (75, 'ZDXM', ' 软式网球', 'RSWQ', '75', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (76, 'ZDXM', ' 曲棍球', 'QK', '76', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (77, 'ZDXM', ' 垒球', 'LQ01', '77', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (78, 'ZDXM', ' 毽球', 'JQ', '78', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (79, 'ZDXM', ' 台球', 'TQ', '79', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (80, 'ZDXM', ' 手球', 'SQ', '80', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (81, 'ZDXM', ' 冰球', 'BQ02', '81', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (82, 'ZDXM', ' 沙滩排球', 'STPQ', '82', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (83, 'ZDXM', ' 地掷球', 'DZQ', '83', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (84, 'ZDXM', ' 保龄球', 'BLQ', '84', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (85, 'ZDXM', ' 水球', 'SQ01', '85', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (86, 'ZDXM', ' 高尔夫球', 'GEFQ', '86', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (87, 'ZDXM', ' 藤球', 'TQ01', '87', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (88, 'ZDXM', ' 轮滑', 'LH', '88', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (89, 'ZDXM', ' 短道速滑', 'DDSH', '89', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (90, 'ZDXM', ' 花样滑冰', 'HYHB', '90', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (91, 'ZDXM', ' 速度滑冰', 'SDHB', '91', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (92, 'ZDXM', ' 越野滑雪', 'YYHX', '92', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (93, 'ZDXM', ' 自由式滑雪', 'ZYSHX', '93', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (94, 'ZDXM', ' 高山滑雪', 'GSHX', '94', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (95, 'ZDXM', ' 跳台滑雪', 'TTHX', '95', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (96, 'ZDXM', ' 健美', 'JM', '96', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (97, 'ZDXM', ' 举重', 'JZ', '97', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (98, 'ZDXM', ' 拳击', 'QJ', '98', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (99, 'ZDXM', ' 柔道', 'RD', '99', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (100, 'ZDXM', ' 散打', 'SD', '100', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (101, 'ZDXM', ' 摔跤', 'SJ', '101', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (102, 'ZDXM', ' 跆拳道', 'TQD', '102', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (103, 'ZDXM', ' 飞镖', 'FB', '103', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (104, 'ZDXM', ' 射箭', 'SJ01', '104', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (105, 'ZDXM', ' 射击', 'SJ02', '105', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (106, 'ZDXM', ' 击剑', 'JJ', '106', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (107, 'ZDXM', ' 围棋', 'WQ01', '107', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (108, 'ZDXM', ' 桥牌', 'QB', '108', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (109, 'ZDXM', ' 国际象棋', 'GJXQ', '109', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (110, 'ZDXM', ' 中国象棋', 'ZGXQ', '110', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (111, 'ZDXM', ' 电子竞技', 'DZJJ', '111', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (112, 'ZDXM', ' 无线电测向', 'WXDCX', '112', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (113, 'ZDXM', ' 业余无线电', 'YYWXD', '113', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (114, 'ZDXM', ' 汽车', 'QC01', '114', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (115, 'ZDXM', ' 车辆模型', 'CLMX', '115', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (116, 'ZDXM', ' 体操', 'TC', '116', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (117, 'ZDXM', ' 艺术体操', 'YSTC', '117', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (118, 'ZDXM', ' 蹦床', 'BC', '118', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (119, 'ZDXM', ' 技巧', 'JQ01', '119', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (120, 'ZDXM', ' 铁人三项', 'TRSX', '120', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (121, 'ZDXM', ' 冬季两项', 'DJLX', '121', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (122, 'ZDXM', ' 现代五项', 'XDWX', '122', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (123, 'ZDXM', ' 健美操', 'JMC', '123', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (124, 'ZDXM', ' 定向', 'DX', '124', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (125, 'ZDXM', ' 信鸽', 'XG', '125', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (126, 'ZDXM', ' 钓鱼', 'DY', '126', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (127, 'ZDXM', ' 冰壶', 'BH', '127', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (128, 'ZDXM', ' 武术', 'WS', '128', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (129, 'ZDXM', ' 拔河', 'BH01', '129', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (130, 'ZDXM', ' 马术', 'MS', '130', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (131, 'ZDXM', ' 体育舞蹈', 'TYWD', '131', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (132, 'ZDXM', ' 舞龙舞狮', 'WLWS', '132', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (134, '0', '指导项目', 'ZDXM', '134', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (135, '0', '审批单位', 'SPDW', '1', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (136, 'SPDW', '厦门市体育局', 'WLJ_TYJ', '4', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (137, 'SPDW', '思明区文旅局', 'WLJ_SMQ', '5', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (138, 'SPDW', '湖里区文旅局', 'WLJ_HLQ', '6', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (139, 'SPDW', '集美区文旅局', 'WLJ_JMQ', '7', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (140, 'SPDW', '海沧区文旅局', 'WLJ_HCQ', '8', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (141, 'SPDW', '翔安区文旅局', 'WLJ_XAQ', '9', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (142, 'SPDW', '同安区文旅局', 'WLJ_TAQ', '10', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (143, '0', '证书等级', 'ZSDJ', '1', NULL, 1, NULL, NULL, NULL, 0, 'system', 1612407234990);
INSERT INTO `sys_dict` VALUES (144, 'ZSDJ', '国家级', 'GJJ', '2', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (146, 'ZSDJ', '一级', 'YJ01', '3', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (147, 'ZSDJ', '二级', 'EJ', '4', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (151, 'ZSDJ', '三级', 'SJ03', '5', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (154, 'SPDW', '其他', 'WLJ_OTHER', '11', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (167, 'ZDXM', ' 健身球', 'JSQ01', '135', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (169, NULL, '测试1', 'CS1', NULL, '0000', 0, 1, '高荣华', 1614757090896, 1, '高荣华', 1614762677848);
INSERT INTO `sys_dict` VALUES (170, NULL, '古武术', 'GWS', NULL, NULL, 0, 1, '高荣华', 1614762524615, 1, '高荣华', 1614766763525);
INSERT INTO `sys_dict` VALUES (171, NULL, '摸金校尉', 'MJXW', NULL, NULL, 1, 1, '高荣华', 1614770572668, 1, '高荣华', 1614770619630);
INSERT INTO `sys_dict` VALUES (172, NULL, '测试666', '22222', NULL, '1233', 1, 1, '高荣华', 1614821272338, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (173, 'ZDXM', '测试', '000', NULL, '2131321', 1, 1, '高荣华', 1614821397387, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (174, 'ZDXM', '高荣华', 'GRH', NULL, '测试', 1, 1, '超级管理员', 1614905375697, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (175, 'ZDXM', '荣华', 'RH', NULL, '测试中', 1, 1, '超级管理员', 1614905418862, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (177, 'ZDXM', '盗墓', 'DM', NULL, NULL, 1, 1, '超级管理员', 1614907649673, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (184, 'SPDW', '国家体育总局', 'WLJ_GJTYZJ', '1', '国家体育总局-GJTYZJ', 1, 1, '超级管理员', 1614912375799, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (185, 'SPDW', '福建省体育局', 'WLJ_FJSTYJ', '2', '福建省体育局-WLJ_FJSTYJ', 1, 1, '超级管理员', 1614912874428, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (186, NULL, '测试', '0001', NULL, '1', 1, 1, '超级管理员', 1615187661061, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (187, '0', '测试类型', 'CSLX', NULL, NULL, 1, 1, '超级管理员', 1615190810968, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (188, '0', '和计划发给很尴尬', 'HJHFJHGG', NULL, NULL, 1, 1, '超级管理员', 1615194095090, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) DEFAULT NULL COMMENT '父菜单',
  `menuName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名',
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '系统管理  [100000]| – 菜单管理 [100100]   | – 角色架构  [100200] | — 消息管理  [110000] | —订单消息 [110100]  ',
  `url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单URL',
  `orderNo` int(11) DEFAULT NULL COMMENT '序号',
  `icon` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图标',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `status` tinyint(4) DEFAULT NULL COMMENT '1：关闭、0：启用',
  `createdId` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `createdBy` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `createdTime` bigint(20) DEFAULT 0 COMMENT '创建时间',
  `updateId` bigint(20) DEFAULT NULL COMMENT '更新者ID',
  `updateBy` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者',
  `updateTime` bigint(20) DEFAULT 0 COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 69 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 9999, '体育指导员', 'A010000', '/sportPolitical/sportPoliticalManage', 100, 'el-icon-user-solid', NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (2, 9999, '驿站管理', 'B020000', '/stageManagement', 200, 'el-icon-s-shop', NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (3, 9999, '招募信息管理', 'C030000', '/recruitInfoManagement', 300, 'el-icon-s-comment', NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (4, 9999, '新闻活动管理', 'D040000', '/newsActiveManagement', 400, 'el-icon-camera-solid', NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (5, 9999, '活动记录管理', 'E050000', '/activeRecordManagement', 500, 'el-icon-video-camera-solid', NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (6, 9999, '值班管理', 'F060000', '/dutyManagement', 600, 'el-icon-s-cooperation', NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (7, 9999, '绩效考核', 'G070000', '/performanceAppraisal', 700, 'el-icon-s-management', NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (8, 9999, '积分管理', 'H080000', '/test', 800, 'test', NULL, 1, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (9, 9999, '系统管理', 'I090000', '/systemManagement', 900, 'el-icon-s-tools', NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (10, 1, '体育指导员管理', 'A010100', '/sportPolitical/sportPoliticalManage', 10, '', NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (11, 1, '体育指导员审核', 'A010200', '/sportPolitical/sportPoliticalAudit', 11, '', NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (12, 3, '招募信息发布', 'C030100', '/recruitInfoManagement/recruitInfoIssue', 12, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (13, 3, '招募信息审核', 'C030200', '/recruitInfoManagement/recruitInfoAudit', 13, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (14, 3, '招募信息查询', 'C030300', '/recruitInfoManagement/recruitInfoCheck', 14, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (15, 3, '招募详情', 'C030400', '/recruitInfoManagement/recruitZmXq', 15, NULL, NULL, 1, NULL, NULL, 0, NULL, NULL, 0);
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
INSERT INTO `sys_menu` VALUES (62, 9999, '课程管理', 'J100000', '/classManagement', 301, 'el-icon-notebook-2', NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (63, 62, '课程审核', 'J100100', '/classManagement/classAudit', 20, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (64, 62, '课程查询', 'J100200', '/classManagement/classCheck', 30, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (65, 62, '课程发布', 'J100300', '/classManagement/classIssue', 10, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (66, 62, '课程详情', 'J100400', '/classManagement/classDetail', 40, NULL, NULL, 1, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (67, 9999, '保险管理', 'K110000', '/insuranceManagement', 899, 'el-icon-suitcase-1\r\n', NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (68, 67, '保险查询', 'K110100', '/insuranceManagement/insuranceQuery', 38, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);

-- ----------------------------
-- Table structure for sys_operation
-- ----------------------------
DROP TABLE IF EXISTS `sys_operation`;
CREATE TABLE `sys_operation`  (
  `id` bigint(20) NOT NULL DEFAULT 0,
  `pid` bigint(20) DEFAULT NULL COMMENT '父ID',
  `icon` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图标',
  `operationName` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT ' 查看、添加、 编辑、 详情、 删除',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT ' 查看[get]    添加[add]   编辑[edit]  详情[detail]  删除[drop]',
  `orderNo` int(11) DEFAULT NULL COMMENT '序号',
  `status` tinyint(4) DEFAULT NULL COMMENT '0：关闭、1：启用',
  `createdId` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `createdBy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `createdTime` bigint(20) DEFAULT 0 COMMENT '创建时间',
  `updateId` bigint(20) DEFAULT NULL COMMENT '更新者ID',
  `updateBy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者',
  `updateTime` bigint(20) DEFAULT 0 COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '功能操作表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operation_log`;
CREATE TABLE `sys_operation_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bizId` bigint(20) DEFAULT NULL COMMENT '业务主键ID',
  `ip` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户IP',
  `module` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '业务模块',
  `bizType` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作类型：后台登录、密码修改',
  `context` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作内容',
  `uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'URI',
  `request` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '请求参数',
  `response` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '响应参数',
  `status` tinyint(4) DEFAULT NULL COMMENT '0：关闭、1：启用',
  `createdId` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `createdBy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `createdTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `updateId` bigint(20) DEFAULT NULL COMMENT '更新者ID',
  `updateBy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者',
  `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '后台用户操作日志表，如：后台登录、指导员入驻' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名',
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '编码',
  `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '描述',
  `status` tinyint(4) DEFAULT NULL COMMENT '0：关闭、1：启用',
  `createdId` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `createdBy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `createdTime` bigint(20) DEFAULT 0 COMMENT '创建时间',
  `updateId` bigint(20) DEFAULT NULL COMMENT '更新者ID',
  `updateBy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者',
  `updateTime` bigint(20) DEFAULT 0 COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 53 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '后台角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (2, '超级管理员', 'cjgly', 'test', 1, 0, 'system', 1610701669860, 0, 'system', 1610703073883);
INSERT INTO `sys_role` VALUES (45, '体育局', 'WLJ_TYJ', '审批单位-体育局', 1, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_role` VALUES (46, '思明区文旅局', 'WLJ_SMQ', '审批单位-思明区文旅局', 1, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_role` VALUES (47, '湖里区文旅局', 'WLJ_HLQ', '审批单位-湖里区文旅局', 1, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_role` VALUES (48, '集美区文旅局', 'WLJ_JMQ', '审批单位-集美区文旅局', 1, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_role` VALUES (49, '翔安区文旅局', 'WLJ_XAQ', '审批单位-翔安区文旅局', 1, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_role` VALUES (50, '同安区文旅局', 'WLJ_TAQ', '审批单位-同安区文旅局', 1, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `sys_role` VALUES (51, '海沧区文旅局', 'WLJ_HCQ', '审批单位-海沧区文旅局', 1, NULL, NULL, 0, 0, 'system', 0);
INSERT INTO `sys_role` VALUES (52, '新闻审批', 'NEWS_AUDIT', '新闻审批', 1, NULL, NULL, 0, NULL, NULL, 0);

-- ----------------------------
-- Table structure for sys_role_area
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_area`;
CREATE TABLE `sys_role_area`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `areaId` bigint(20) DEFAULT NULL COMMENT '区域ID',
  `roleId` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_area_role`(`roleId`) USING BTREE,
  INDEX `FK_role_area`(`areaId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `roleId` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menuId` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_menu_role`(`roleId`) USING BTREE,
  INDEX `FK_role_menu`(`menuId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 378 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (17, 1, 10);
INSERT INTO `sys_role_menu` VALUES (19, 3, 10);
INSERT INTO `sys_role_menu` VALUES (20, 4, 10);
INSERT INTO `sys_role_menu` VALUES (21, 5, 10);
INSERT INTO `sys_role_menu` VALUES (22, 6, 10);
INSERT INTO `sys_role_menu` VALUES (23, 7, 10);
INSERT INTO `sys_role_menu` VALUES (24, 8, 10);
INSERT INTO `sys_role_menu` VALUES (25, 9, 10);
INSERT INTO `sys_role_menu` VALUES (26, 10, 10);
INSERT INTO `sys_role_menu` VALUES (31, 11, 1);
INSERT INTO `sys_role_menu` VALUES (32, 12, 1);
INSERT INTO `sys_role_menu` VALUES (33, 13, 1);
INSERT INTO `sys_role_menu` VALUES (34, 14, 1);
INSERT INTO `sys_role_menu` VALUES (35, 15, 1);
INSERT INTO `sys_role_menu` VALUES (36, 16, 1);
INSERT INTO `sys_role_menu` VALUES (37, 17, 1);
INSERT INTO `sys_role_menu` VALUES (38, 18, 1);
INSERT INTO `sys_role_menu` VALUES (39, 19, 1);
INSERT INTO `sys_role_menu` VALUES (40, 20, 1);
INSERT INTO `sys_role_menu` VALUES (41, 21, 1);
INSERT INTO `sys_role_menu` VALUES (42, 22, 1);
INSERT INTO `sys_role_menu` VALUES (43, 23, 1);
INSERT INTO `sys_role_menu` VALUES (44, 24, 1);
INSERT INTO `sys_role_menu` VALUES (45, 25, 1);
INSERT INTO `sys_role_menu` VALUES (46, 26, 1);
INSERT INTO `sys_role_menu` VALUES (47, 27, 1);
INSERT INTO `sys_role_menu` VALUES (48, 28, 1);
INSERT INTO `sys_role_menu` VALUES (49, 29, 1);
INSERT INTO `sys_role_menu` VALUES (50, 30, 1);
INSERT INTO `sys_role_menu` VALUES (51, 31, 1);
INSERT INTO `sys_role_menu` VALUES (52, 32, 1);
INSERT INTO `sys_role_menu` VALUES (53, 33, 1);
INSERT INTO `sys_role_menu` VALUES (54, 34, 1);
INSERT INTO `sys_role_menu` VALUES (55, 35, 1);
INSERT INTO `sys_role_menu` VALUES (56, 36, 1);
INSERT INTO `sys_role_menu` VALUES (57, 37, 1);
INSERT INTO `sys_role_menu` VALUES (58, 38, 1);
INSERT INTO `sys_role_menu` VALUES (59, 39, 1);
INSERT INTO `sys_role_menu` VALUES (60, 40, 1);
INSERT INTO `sys_role_menu` VALUES (61, 41, 1);
INSERT INTO `sys_role_menu` VALUES (62, 42, 1);
INSERT INTO `sys_role_menu` VALUES (63, 43, 1);
INSERT INTO `sys_role_menu` VALUES (64, 44, 1);
INSERT INTO `sys_role_menu` VALUES (285, 55, 1);
INSERT INTO `sys_role_menu` VALUES (286, 55, 10);
INSERT INTO `sys_role_menu` VALUES (287, 55, 11);
INSERT INTO `sys_role_menu` VALUES (288, 55, 2);
INSERT INTO `sys_role_menu` VALUES (289, 55, 3);
INSERT INTO `sys_role_menu` VALUES (290, 55, 12);
INSERT INTO `sys_role_menu` VALUES (291, 55, 13);
INSERT INTO `sys_role_menu` VALUES (292, 55, 14);
INSERT INTO `sys_role_menu` VALUES (293, 55, 4);
INSERT INTO `sys_role_menu` VALUES (294, 55, 16);
INSERT INTO `sys_role_menu` VALUES (295, 55, 17);
INSERT INTO `sys_role_menu` VALUES (296, 55, 5);
INSERT INTO `sys_role_menu` VALUES (297, 55, 6);
INSERT INTO `sys_role_menu` VALUES (298, 55, 20);
INSERT INTO `sys_role_menu` VALUES (299, 55, 21);
INSERT INTO `sys_role_menu` VALUES (300, 55, 7);
INSERT INTO `sys_role_menu` VALUES (301, 55, 22);
INSERT INTO `sys_role_menu` VALUES (302, 55, 9);
INSERT INTO `sys_role_menu` VALUES (303, 55, 23);
INSERT INTO `sys_role_menu` VALUES (304, 55, 24);
INSERT INTO `sys_role_menu` VALUES (305, 55, 25);
INSERT INTO `sys_role_menu` VALUES (306, 55, 26);
INSERT INTO `sys_role_menu` VALUES (307, 55, 27);
INSERT INTO `sys_role_menu` VALUES (308, 55, 28);
INSERT INTO `sys_role_menu` VALUES (309, 55, 29);
INSERT INTO `sys_role_menu` VALUES (310, 55, 30);
INSERT INTO `sys_role_menu` VALUES (311, 55, 31);
INSERT INTO `sys_role_menu` VALUES (312, 55, 46);
INSERT INTO `sys_role_menu` VALUES (313, 55, 47);
INSERT INTO `sys_role_menu` VALUES (314, 55, 48);
INSERT INTO `sys_role_menu` VALUES (315, 55, 49);
INSERT INTO `sys_role_menu` VALUES (316, 55, 50);
INSERT INTO `sys_role_menu` VALUES (317, 2, 1);
INSERT INTO `sys_role_menu` VALUES (318, 2, 10);
INSERT INTO `sys_role_menu` VALUES (319, 2, 11);
INSERT INTO `sys_role_menu` VALUES (320, 2, 2);
INSERT INTO `sys_role_menu` VALUES (321, 2, 3);
INSERT INTO `sys_role_menu` VALUES (322, 2, 12);
INSERT INTO `sys_role_menu` VALUES (323, 2, 13);
INSERT INTO `sys_role_menu` VALUES (324, 2, 14);
INSERT INTO `sys_role_menu` VALUES (325, 2, 4);
INSERT INTO `sys_role_menu` VALUES (326, 2, 16);
INSERT INTO `sys_role_menu` VALUES (327, 2, 17);
INSERT INTO `sys_role_menu` VALUES (328, 2, 5);
INSERT INTO `sys_role_menu` VALUES (329, 2, 6);
INSERT INTO `sys_role_menu` VALUES (330, 2, 20);
INSERT INTO `sys_role_menu` VALUES (331, 2, 21);
INSERT INTO `sys_role_menu` VALUES (332, 2, 7);
INSERT INTO `sys_role_menu` VALUES (333, 2, 22);
INSERT INTO `sys_role_menu` VALUES (334, 2, 9);
INSERT INTO `sys_role_menu` VALUES (335, 2, 23);
INSERT INTO `sys_role_menu` VALUES (336, 2, 24);
INSERT INTO `sys_role_menu` VALUES (337, 2, 25);
INSERT INTO `sys_role_menu` VALUES (338, 2, 26);
INSERT INTO `sys_role_menu` VALUES (339, 2, 27);
INSERT INTO `sys_role_menu` VALUES (340, 2, 28);
INSERT INTO `sys_role_menu` VALUES (341, 2, 29);
INSERT INTO `sys_role_menu` VALUES (342, 2, 30);
INSERT INTO `sys_role_menu` VALUES (343, 2, 31);
INSERT INTO `sys_role_menu` VALUES (363, 58, 1);
INSERT INTO `sys_role_menu` VALUES (364, 58, 10);
INSERT INTO `sys_role_menu` VALUES (365, 58, 11);
INSERT INTO `sys_role_menu` VALUES (366, 58, 2);
INSERT INTO `sys_role_menu` VALUES (367, 58, 3);
INSERT INTO `sys_role_menu` VALUES (368, 58, 12);
INSERT INTO `sys_role_menu` VALUES (369, 58, 13);
INSERT INTO `sys_role_menu` VALUES (370, 58, 14);
INSERT INTO `sys_role_menu` VALUES (371, 58, 4);
INSERT INTO `sys_role_menu` VALUES (372, 58, 5);
INSERT INTO `sys_role_menu` VALUES (373, 58, 6);
INSERT INTO `sys_role_menu` VALUES (374, 58, 20);
INSERT INTO `sys_role_menu` VALUES (375, 58, 21);
INSERT INTO `sys_role_menu` VALUES (376, 58, 7);
INSERT INTO `sys_role_menu` VALUES (377, 58, 22);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `roleId` bigint(20) NOT NULL COMMENT '角色ID',
  `userId` bigint(20) NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_role_user`(`userId`) USING BTREE,
  INDEX `FK_user_role`(`roleId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 60 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (52, 46, 1);
INSERT INTO `sys_user_role` VALUES (53, 47, 1);
INSERT INTO `sys_user_role` VALUES (54, 48, 1);
INSERT INTO `sys_user_role` VALUES (55, 49, 1);
INSERT INTO `sys_user_role` VALUES (56, 50, 1);
INSERT INTO `sys_user_role` VALUES (57, 51, 1);
INSERT INTO `sys_user_role` VALUES (58, 52, 1);
INSERT INTO `sys_user_role` VALUES (59, 45, 1);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `imgUrl` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '头像',
  `nickName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '昵称',
  `userName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '用户名',
  `account` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '账号名称',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密码',
  `sex` int(11) DEFAULT NULL COMMENT '性别：0-未知、1-男、2-女',
  `userType` int(11) DEFAULT NULL COMMENT '用户类型：1：公众号用户、2：指导员、3：后台用户、4：后台管理员',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '手机号码',
  `source` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户来源：1-从i厦门绑定的用户；2-后台系统录入的',
  `certificateNo` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '证件号码',
  `channel` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '小程序（miniWeChat）、公众号（publicWeChat）',
  `birthday` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '生日',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '住址',
  `postcode` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮编',
  `openid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '公众号UnionID',
  `unionID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '微信开放平台UnionID',
  `insuranceStatus` int(11) DEFAULT NULL COMMENT '保险状态: 0-未购买 1-待审核 2-审核不通过 3-未生效 4-已生效 5-已过期',
  `commitmentStatus` tinyint(4) DEFAULT NULL COMMENT '是否已签署承诺书：0-否；1-是',
  `ixmUserId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'i厦门用户唯一标识id',
  `ixmUserName` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'i厦门用户名',
  `ixmRealNameLevel` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'i厦门是否实名',
  `ixmUserRealName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'i厦门用户真实姓名',
  `ixmLoginStatus` tinyint(4) DEFAULT NULL COMMENT 'i厦门微信公众号登录状态',
  `ixmToken` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'i厦门token',
  `status` tinyint(4) DEFAULT 1 COMMENT '0：关闭、1：启用',
  `createdId` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `createdBy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `createdTime` bigint(20) DEFAULT 0 COMMENT '创建者时间',
  `updateId` bigint(20) DEFAULT NULL COMMENT '更新者ID',
  `updateBy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者',
  `updateTime` bigint(20) DEFAULT 0 COMMENT '更新者时间',
  `guideProject` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `deptName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '部门',
  `deptId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '部门id',
  `workUnit` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '工作单位',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_phone`(`phone`) USING BTREE,
  INDEX `openid`(`openid`) USING BTREE,
  INDEX `idx_certificateNo`(`certificateNo`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '微信用户、后台用户、后台管理员' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, NULL, NULL, '超级管理员', 'admin', '$2a$10$TM0Ce3jn3.EMrX.t/hkXJuOck6Q80kOPUNzkfbsctH6kTPmvFS/RC', NULL, 4, NULL, '7819@qq.com', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, 1, 0, 'system', 1611892477931, NULL, NULL, 0, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for user_collect
-- ----------------------------
DROP TABLE IF EXISTS `user_collect`;
CREATE TABLE `user_collect`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `objectId` bigint(20) NOT NULL COMMENT '关联记录id',
  `objectName` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '关联记录名称',
  `type` int(11) NOT NULL COMMENT '收藏类型 1驿站',
  `userId` bigint(20) DEFAULT NULL COMMENT '用户id（暂时没用）',
  `openId` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '微信openId',
  `remarks` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '审批人',
  `createdId` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `createdBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `createdTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `updateId` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `updateBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `working_replace_card_index01`(`objectId`, `type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户收藏' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for working_replace_card
-- ----------------------------
DROP TABLE IF EXISTS `working_replace_card`;
CREATE TABLE `working_replace_card`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `workingScheduleUserId` bigint(20) NOT NULL COMMENT '关联记录id',
  `type` int(11) DEFAULT NULL COMMENT '打卡类型 1签到 2签退',
  `replaceCardTime` bigint(20) NOT NULL COMMENT '补卡时间',
  `replaceCardReason` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '补卡说明',
  `approvalStatus` int(11) DEFAULT 0 COMMENT '审批类型 (0.待审核1.通过2.驳回)',
  `approvalComments` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '审批意见',
  `approvalId` bigint(20) DEFAULT NULL COMMENT '审批人Id',
  `approvalBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '审批人',
  `approvalTime` bigint(20) DEFAULT NULL COMMENT '审批时间',
  `pics` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图片地址',
  `remarks` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '审批人',
  `createdId` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `createdBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `createdTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `updateId` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `updateBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `working_replace_card_index01`(`workingScheduleUserId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '值班补卡' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for working_schedule
-- ----------------------------
DROP TABLE IF EXISTS `working_schedule`;
CREATE TABLE `working_schedule`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `activityId` bigint(20) DEFAULT NULL COMMENT '活动id',
  `activityTitle` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '活动主题',
  `activityDetailId` bigint(20) DEFAULT NULL COMMENT '活动明细',
  `activityAddress` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '活动地址',
  `activityType` int(11) DEFAULT NULL COMMENT '活动类型 1驿站 2赛事',
  `courierStationId` bigint(20) DEFAULT NULL COMMENT '驿站id',
  `courierStationName` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '驿站名称',
  `scheduleTime` bigint(20) NOT NULL COMMENT '排班日期',
  `serviceTimeId` bigint(20) DEFAULT NULL COMMENT '服务时段id',
  `serviceBeginTime` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '服务开始时段 (时分)',
  `serviceEndTime` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '服务结束时段 (时分)',
  `remarks` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `createdId` bigint(20) NOT NULL COMMENT '创建人',
  `createdBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人名称',
  `createdTime` bigint(20) NOT NULL COMMENT '创建时间',
  `updateId` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updateBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人名称',
  `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `working_schedule_index01`(`courierStationId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '排班表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for working_schedule_user
-- ----------------------------
DROP TABLE IF EXISTS `working_schedule_user`;
CREATE TABLE `working_schedule_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `taskNo` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '任务编号',
  `workingScheduleId` bigint(20) NOT NULL COMMENT '排班id',
  `type` int(11) NOT NULL COMMENT '排班用户类型 (1站长 2固定排班人员 3预约)',
  `userId` bigint(20) NOT NULL COMMENT '用户id',
  `userName` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '用户名称',
  `certificateGrade` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '证书等级',
  `guideProject` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '指导项目',
  `signInTime` bigint(20) DEFAULT NULL COMMENT '签到时间',
  `signOutTime` bigint(20) DEFAULT NULL COMMENT '签退时间',
  `serviceTime` int(11) DEFAULT NULL COMMENT '服务时长 (单位：分)',
  `actualServiceTime` int(11) DEFAULT NULL COMMENT '实得时长(单位:分)',
  `approvalId` bigint(20) DEFAULT NULL COMMENT '审批人',
  `approvalType` int(11) DEFAULT NULL COMMENT '审批类型 1按照人员签到签退时间进行统计 2按照活动设定时间进行统计 3按实际情况填写时长',
  `approvalIdea` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '审批意见',
  `approvalTime` bigint(20) DEFAULT NULL COMMENT '审批时间',
  `reserveStatus` int(11) DEFAULT 0 COMMENT '预约状态 0预约 1撤销',
  `status` int(11) DEFAULT 1 COMMENT '是否需要审核 1待审核 2已审核',
  `remarks` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `createdId` bigint(20) NOT NULL COMMENT '创建人',
  `createdBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人名称',
  `createdTime` bigint(20) NOT NULL COMMENT '创建时间',
  `updateId` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updateBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人名称',
  `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `working_schedule_user_index01`(`workingScheduleId`, `type`) USING BTREE,
  INDEX `working_schedule_user_index02`(`userId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '排班表人员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for working_sign_record
-- ----------------------------
DROP TABLE IF EXISTS `working_sign_record`;
CREATE TABLE `working_sign_record`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `workingScheduleUserId` bigint(20) NOT NULL COMMENT '值班人员id',
  `replaceCardId` bigint(20) DEFAULT NULL COMMENT '补卡id',
  `type` int(11) NOT NULL COMMENT '打卡类型 1签到2签退',
  `isReissueCard` int(11) DEFAULT 0 COMMENT '是否补卡 0否 1是',
  `signStatus` int(11) DEFAULT 0 COMMENT '打卡状态 0正常 1异常',
  `signTime` bigint(20) NOT NULL COMMENT '打卡时间',
  `signPlace` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '打卡位置',
  `longitude` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '经度',
  `latitude` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '纬度',
  `pics` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图片 (多个逗号隔开)',
  `remarks` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `createdId` bigint(20) NOT NULL COMMENT '创建人',
  `createdBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人名称',
  `createdTime` bigint(20) NOT NULL COMMENT '创建时间',
  `updateId` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updateBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人名称',
  `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `working_sign_record_index01`(`workingScheduleUserId`, `type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '值班签到记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Function structure for getPunchCartStatus
-- ----------------------------
DROP FUNCTION IF EXISTS `getPunchCartStatus`;
delimiter ;;
CREATE DEFINER=`root`@`%` FUNCTION `getPunchCartStatus`(`serviceBeginTime` bigint,`serviceEndTime` bigint,`signInTime` bigint,`signOutTime` bigint,`status` INT) RETURNS int(11)
BEGIN
	if status = 2 Then RETURN 0;
	ELSEIF signInTime is not null and signOutTime is not null 
		  THEN if (signInTime <= serviceBeginTime or signOutTime <= serviceEndTime + 2 * 60 * 60 * 1000) THEN RETURN 0;
			END IF;
	ELSEIF signInTime is NOT null AND signOutTime is null 
			THEN if unix_timestamp(now())*1000 <= serviceBeginTime + 1 * 60 * 60 * 1000 and signInTime <= serviceBeginTime THEN RETURN 0;
					 ELSEIF unix_timestamp(now())*1000 >= serviceBeginTime + 1 * 60 * 60 * 1000 && unix_timestamp(now())*1000 <= serviceEndTime + 2 * 60 * 60 * 1000 THEN RETURN 0;
					 END IF;
	ELSEIF signInTime is null or signOutTime is null 
			THEN if unix_timestamp(now())*1000 <= serviceBeginTime THEN RETURN 0;		
			END IF;
	END IF;
	RETURN 1;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
