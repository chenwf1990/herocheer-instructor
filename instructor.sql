ALTER TABLE course_info ADD COLUMN releaseTime bigint(20) DEFAULT NULL COMMENT '发布时间' AFTER signNumber;

ALTER TABLE reservation ADD COLUMN source int(2) DEFAULT 1 COMMENT '数据来源:1.线上2.线下' AFTER type;