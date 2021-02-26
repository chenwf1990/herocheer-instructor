ALTER TABLE `instructor`
  DROP INDEX `instructor_index01`,
  DROP INDEX `instructor_index02`,
  ADD INDEX `instructor_index01`(`cardNo`) USING BTREE,
  ADD UNIQUE INDEX `instructor_index02`(`phone`) USING BTREE;


ALTER TABLE `user`
  DROP INDEX `idx_certificateNo`,
  ADD INDEX `idx_certificateNo`(`certificateNo`) USING BTREE;

ALTER TABLE `instructor`
  MODIFY COLUMN `workUnit` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '工作单位' AFTER `phone`;


ALTER TABLE `instructor_apply`
  MODIFY COLUMN `workUnit` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '工作单位' AFTER `phone`;

ALTER TABLE `instructor_apply_audit_log`
  MODIFY COLUMN `workUnit` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '工作单位' AFTER `phone`;

ALTER TABLE `instructor`
  MODIFY COLUMN `guideStation` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '指导站点' AFTER `guideProject`;


ALTER TABLE `instructor_apply`
  MODIFY COLUMN `guideStation` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '指导站点' AFTER `guideProject`;

ALTER TABLE `instructor_apply_audit_log`
  MODIFY COLUMN `guideStation` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '指导站点' AFTER `guideProject`;

INSERT INTO `herocheer_instructor`.`sys_area`(`id`, `pid`, `areaName`, `areaCode`, `chinaCode`, `level`, `remark`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (526, 101, ' 一里社区', '0101020712', '350203009017', 5, NULL, NULL, NULL, 1610002958, NULL, NULL, 1610003019);