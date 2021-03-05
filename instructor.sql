ALTER TABLE `instructor`
  MODIFY COLUMN `cardNo` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '身份证号' AFTER `auditIdea`;
ALTER TABLE `instructor_apply`
  MODIFY COLUMN `cardNo` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '身份证号' AFTER `auditIdea`;
ALTER TABLE `instructor_apply_audit_log`
  MODIFY COLUMN `cardNo` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '身份证号' AFTER `auditIdea`;

ALTER TABLE `herocheer_instructor`.`user`
  MODIFY COLUMN `certificateNo` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '证件号码' AFTER `source`;

UPDATE `herocheer_instructor`.`sys_role` SET `roleName` = '厦门市体育局' WHERE `id` = 45;
INSERT INTO `herocheer_instructor`.`sys_role`(`id`, `roleName`, `code`, `description`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (NULL, '国家体育总局', 'WLJ_GJTYZJ', '审批单位-国家体育总局-WLJ_GJTYZJ', 1, NULL, NULL, 0, 0, 'system', 0);
INSERT INTO `herocheer_instructor`.`sys_role`(`id`, `roleName`, `code`, `description`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (NULL, '福建省体育局', 'WLJ_FJSTYJ', '审批单位-福建省体育局-WLJ_FJSTYJ', 1, NULL, NULL, 0, 0, 'system', 0);
INSERT INTO `herocheer_instructor`.`sys_dict`(`id`, `pid`, `dictName`, `dictCode`, `sortNo`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (NULL, 'SPDW', '国家体育总局', 'WLJ_GJTYZJ', '1', '国家体育总局-GJTYZJ', 1, 1, '超级管理员', 1614912375799, NULL, NULL, NULL);
INSERT INTO `herocheer_instructor`.`sys_dict`(`id`, `pid`, `dictName`, `dictCode`, `sortNo`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (NULL, 'SPDW', '福建省体育局', 'WLJ_FJSTYJ', '2', '福建省体育局-WLJ_FJSTYJ', 1, 1, '超级管理员', 1614912874428, NULL, NULL, NULL);
UPDATE `herocheer_instructor`.`sys_dict` SET `sortNo` = '1' WHERE `id` = 184;
UPDATE `herocheer_instructor`.`sys_dict` SET `sortNo` = '2' WHERE `id` = 185;
UPDATE `herocheer_instructor`.`sys_dict` SET `sortNo` = '3' WHERE `id` = 178;
UPDATE `herocheer_instructor`.`sys_dict` SET `sortNo` = '4' WHERE `id` = 136;
UPDATE `herocheer_instructor`.`sys_dict` SET `sortNo` = '5' WHERE `id` = 137;
UPDATE `herocheer_instructor`.`sys_dict` SET `sortNo` = '6' WHERE `id` = 138;
UPDATE `herocheer_instructor`.`sys_dict` SET `sortNo` = '7' WHERE `id` = 139;
UPDATE `herocheer_instructor`.`sys_dict` SET `sortNo` = '8' WHERE `id` = 140;
UPDATE `herocheer_instructor`.`sys_dict` SET `sortNo` = '9' WHERE `id` = 141;
UPDATE `herocheer_instructor`.`sys_dict` SET `sortNo` = '10' WHERE `id` = 142;
UPDATE `herocheer_instructor`.`sys_dict` SET `sortNo` = '11' WHERE `id` = 154;
UPDATE `herocheer_instructor`.`sys_dict` SET `dictName` = '厦门市体育局' WHERE `id` = 136;


ALTER TABLE `herocheer_instructor`.`instructor`
  MODIFY COLUMN `phone` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号' AFTER `cardNo`;
ALTER TABLE `herocheer_instructor`.`instructor_apply`
  MODIFY COLUMN `phone` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号' AFTER `cardNo`;
ALTER TABLE `herocheer_instructor`.`instructor_apply_audit_log`
  MODIFY COLUMN `phone` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号' AFTER `cardNo`;
ALTER TABLE `herocheer_instructor`.`user`
  MODIFY COLUMN `phone` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '手机号码' AFTER `email`;