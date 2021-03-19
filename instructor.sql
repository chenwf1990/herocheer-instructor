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
                        `linkValue` varchar(256) DEFAULT NULL COMMENT '链接值',
                        `createdId` bigint(20) DEFAULT NULL COMMENT '创建者ID',
                        `createdBy` varchar(30) DEFAULT NULL COMMENT '创建者',
                        `createdTime` bigint(20) DEFAULT '0' COMMENT '创建时间',
                        `updateId` bigint(20) DEFAULT NULL COMMENT '更新者ID',
                        `updateBy` varchar(30) DEFAULT NULL COMMENT '更新者',
                        `updateTime` bigint(20) DEFAULT '0' COMMENT '更新时间',
                        PRIMARY KEY (`id`) USING BTREE,
                        KEY `banner_index01` (`isPublic`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='banner管理';


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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='健身视频管理';