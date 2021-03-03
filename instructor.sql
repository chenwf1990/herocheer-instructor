ALTER TABLE `instructor`
  MODIFY COLUMN `cardNo` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '身份证号' AFTER `auditIdea`;
ALTER TABLE `instructor_apply`
  MODIFY COLUMN `cardNo` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '身份证号' AFTER `auditIdea`;
ALTER TABLE `instructor_apply_audit_log`
  MODIFY COLUMN `cardNo` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '身份证号' AFTER `auditIdea`;

ALTER TABLE `herocheer_instructor`.`user`
  MODIFY COLUMN `certificateNo` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '证件号码' AFTER `source`;