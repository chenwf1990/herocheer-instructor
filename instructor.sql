
-- 新增菜单信息 by gaorh 20210222
INSERT INTO `herocheer_instructor`.`sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (67, 9999, '保险管理', 'K110000', '/insuranceManagement', 37, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `herocheer_instructor`.`sys_menu`(`id`, `pid`, `menuName`, `code`, `url`, `orderNo`, `icon`, `remark`, `status`, `createdId`, `createdBy`, `createdTime`, `updateId`, `updateBy`, `updateTime`) VALUES (68, 67, '保险查询', 'K110100', '/insuranceManagement/insuranceQuery', 38, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0);

-- 扩字段长度 by gaorh 20210223
alter table  `user` modify column imgUrl varchar(255);