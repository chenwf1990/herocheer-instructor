ALTER TABLE `herocheer_instructor`.`user_collect`
  MODIFY COLUMN `type` int(11) NOT NULL COMMENT '收藏类型 1驿站  2健身视频' AFTER `objectName`