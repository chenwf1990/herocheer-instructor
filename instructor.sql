CREATE TABLE `brand_info` (
                              `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                              `brandName` varchar(128) NOT NULL COMMENT '品牌名',
                              `numbering` varchar(32) NOT NULL COMMENT '编号',
                              `isEnable` int(2) NOT NULL DEFAULT '0' COMMENT '是否启用 (0.启用 1.关闭)',
                              `createdId` bigint(20) NOT NULL COMMENT '创建人id',
                              `createdBy` varchar(32) DEFAULT NULL COMMENT '创建人',
                              `createdTime` bigint(20) NOT NULL COMMENT '创建时间',
                              `updateId` bigint(20) DEFAULT NULL COMMENT '更新人id',
                              `updateBy` varchar(32) DEFAULT NULL COMMENT '更新人',
                              `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COMMENT='品牌管理 ';

CREATE TABLE `equipment_borrow` (
                                    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                    `borrowReceipt` varchar(32) NOT NULL COMMENT '借用单据',
                                    `courierId` bigint(20) NOT NULL COMMENT '驿站id',
                                    `courierName` varchar(128) DEFAULT NULL COMMENT '驿站名',
                                    `borrower` varchar(128) NOT NULL COMMENT '借用人',
                                    `gender` int(2) NOT NULL DEFAULT '0' COMMENT '性别 (0.未知 1.男 2.女)',
                                    `identityNumber` varchar(128) NOT NULL COMMENT '身份证号码',
                                    `phoneNumber` varchar(128) NOT NULL COMMENT '手机号',
                                    `borrowStartTime` bigint(20) NOT NULL COMMENT '借用开始时间',
                                    `borrowEndTime` bigint(20) NOT NULL COMMENT '借用结束时间',
                                    `status` int(2) NOT NULL DEFAULT '0' COMMENT '状态 (0.待审核 1.待签收 2.待归还 3.已归还 4.驳回)',
                                    `remandStatus` int(2) NOT NULL DEFAULT '0' COMMENT '归还状态 (0.默认1.申请归还 2.已归还待用户确认 3.已确认)',
                                    `remandTime` bigint(20) DEFAULT NULL COMMENT '归还时间',
                                    `borrowEquipment` varchar(512) DEFAULT NULL COMMENT '借用器材',
                                    `remandEquipment` varchar(512) DEFAULT NULL COMMENT '归还器材',
                                    `lenderId` bigint(20) DEFAULT NULL COMMENT '借出人id',
                                    `createdId` bigint(20) NOT NULL COMMENT '创建人id',
                                    `createdBy` varchar(32) DEFAULT NULL COMMENT '创建人',
                                    `createdTime` bigint(20) NOT NULL COMMENT '创建时间',
                                    `updateId` bigint(20) DEFAULT NULL COMMENT '更新人id',
                                    `updateBy` varchar(32) DEFAULT NULL COMMENT '更新人',
                                    `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COMMENT='器材借用 ';

CREATE TABLE `equipment_borrow_details` (
                                            `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                            `borrowId` bigint(20) NOT NULL COMMENT '借用id',
                                            `equipmentId` bigint(20) NOT NULL COMMENT '器材id',
                                            `equipmentName` varchar(128) DEFAULT NULL COMMENT '器材名',
                                            `brandName` varchar(128) NOT NULL COMMENT '品牌名',
                                            `model` varchar(128) DEFAULT NULL COMMENT '规格型号',
                                            `borrowQuantity` int(4) NOT NULL DEFAULT '0' COMMENT '借用数量',
                                            `actualBorrowQuantity` int(4) DEFAULT '0' COMMENT '实际借用数量',
                                            `damageQuantity` int(4) DEFAULT '0' COMMENT '损坏数量',
                                            `remandQuantity` int(4) DEFAULT '0' COMMENT '归还数量',
                                            `unreturnedQuantity` int(4) DEFAULT '0' COMMENT '待归还数量',
                                            `remandStatus` int(2) NOT NULL DEFAULT '0' COMMENT '归还状态(0.待归还 1.已归还)',
                                            `borrowBy` varchar(32) DEFAULT NULL COMMENT '借出人',
                                            `borrowUserId` bigint(20) DEFAULT NULL COMMENT '借出人id',
                                            `borrowTime` bigint(20) DEFAULT NULL,
                                            `phoneNumber` varchar(128) DEFAULT NULL COMMENT '借出人手机号码',
                                            `createdId` bigint(20) NOT NULL COMMENT '创建人id',
                                            `createdBy` varchar(32) DEFAULT NULL COMMENT '创建人',
                                            `createdTime` bigint(20) NOT NULL COMMENT '创建时间',
                                            `updateId` bigint(20) DEFAULT NULL COMMENT '更新人id',
                                            `updateBy` varchar(32) DEFAULT NULL COMMENT '更新人',
                                            `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
                                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8mb4 COMMENT='器材借用详情 ';

CREATE TABLE `equipment_damage` (
                                    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                    `borrowId` bigint(20) DEFAULT NULL COMMENT '器材借用id',
                                    `status` int(2) NOT NULL DEFAULT '0' COMMENT '状态 (0.遗失1.损坏)',
                                    `damageReason` varchar(512) DEFAULT NULL COMMENT '损坏原因',
                                    `createdId` bigint(20) DEFAULT NULL COMMENT '创建人id',
                                    `createdBy` varchar(32) DEFAULT NULL COMMENT '创建人',
                                    `createdTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
                                    `updateId` bigint(20) DEFAULT NULL COMMENT '更新人id',
                                    `updateBy` varchar(32) DEFAULT NULL COMMENT '更新人',
                                    `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COMMENT='器材损坏 ';

CREATE TABLE `equipment_damage_details` (
                                            `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                            `damageId` bigint(20) DEFAULT NULL COMMENT '损坏id',
                                            `borrowDetailsId` bigint(20) DEFAULT NULL COMMENT '借用详情Id',
                                            `damagQuantity` int(11) DEFAULT NULL COMMENT '损坏数量',
                                            `createdId` bigint(20) DEFAULT NULL COMMENT '创建人id',
                                            `createdBy` varchar(32) DEFAULT NULL COMMENT '创建人',
                                            `createdTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
                                            `updateId` bigint(20) DEFAULT NULL COMMENT '更新人id',
                                            `updateBy` varchar(32) DEFAULT NULL COMMENT '更新人',
                                            `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
                                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COMMENT='器材损坏关联借用信息表 ';

CREATE TABLE `equipment_info` (
                                  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                  `courierId` bigint(20) NOT NULL COMMENT '驿站Id',
                                  `courierName` varchar(128) NOT NULL COMMENT '驿站名',
                                  `equipmentName` varchar(128) NOT NULL COMMENT '器材名',
                                  `model` varchar(128) DEFAULT NULL COMMENT '规格型号',
                                  `brandId` bigint(20) NOT NULL COMMENT '品牌id',
                                  `brandName` varchar(32) NOT NULL COMMENT '品牌名',
                                  `stockNumber` int(4) NOT NULL DEFAULT '0' COMMENT '库存数量',
                                  `remarks` varchar(512) DEFAULT NULL COMMENT '备注',
                                  `createdId` bigint(20) NOT NULL COMMENT '创建人id',
                                  `createdBy` varchar(32) DEFAULT NULL COMMENT '创建人',
                                  `createdTime` bigint(20) NOT NULL COMMENT '创建时间',
                                  `updateId` bigint(20) DEFAULT NULL COMMENT '更新人id',
                                  `updateBy` varchar(32) DEFAULT NULL COMMENT '更新人',
                                  `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COMMENT='器材信息 ';

CREATE TABLE `equipment_info_log` (
                                      `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                      `equipmentId` bigint(20) DEFAULT NULL COMMENT '原器材Id',
                                      `damageId` bigint(20) DEFAULT NULL COMMENT '报废id',
                                      `courierId` bigint(20) NOT NULL COMMENT '驿站Id',
                                      `courierName` varchar(128) NOT NULL COMMENT '驿站名',
                                      `equipmentName` varchar(128) NOT NULL COMMENT '器材名',
                                      `model` varchar(128) DEFAULT NULL COMMENT '规格型号',
                                      `brandId` bigint(20) NOT NULL COMMENT '品牌id',
                                      `brandName` varchar(32) NOT NULL COMMENT '品牌名',
                                      `stockNumber` varchar(32) NOT NULL COMMENT '库存数量',
                                      `remarks` varchar(512) DEFAULT NULL COMMENT '备注',
                                      `createdId` bigint(20) NOT NULL COMMENT '创建人id',
                                      `createdBy` varchar(32) DEFAULT NULL COMMENT '创建人',
                                      `createdTime` bigint(20) NOT NULL COMMENT '创建时间',
                                      `updateId` bigint(20) DEFAULT NULL COMMENT '更新人id',
                                      `updateBy` varchar(32) DEFAULT NULL COMMENT '更新人',
                                      `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
                                      PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8mb4 COMMENT='器材信息日志 ';

CREATE TABLE `equipment_remand` (
                                    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                    `borrowId` bigint(20) NOT NULL COMMENT '借用单据id',
                                    `borrowDetailsId` bigint(20) NOT NULL COMMENT '关联借用器材id',
                                    `remandQuantity` int(4) NOT NULL DEFAULT '0' COMMENT '归还数量',
                                    `receiveBy` varchar(32) DEFAULT NULL COMMENT '接收人',
                                    `receiveId` bigint(20) DEFAULT NULL COMMENT '接收人id',
                                    `receiveTime` bigint(20) DEFAULT NULL COMMENT '接收时间',
                                    `phoneNumber` varchar(128) DEFAULT NULL COMMENT '接收人手机号码',
                                    `remandStatus` int(2) NOT NULL DEFAULT '0' COMMENT '状态 (0.默认1.申请归还 2.已归还待用户确认 3.已确认)',
                                    `createdId` bigint(20) NOT NULL COMMENT '创建人id',
                                    `createdBy` varchar(32) DEFAULT NULL COMMENT '创建人',
                                    `createdTime` bigint(20) NOT NULL COMMENT '创建时间',
                                    `updateId` bigint(20) DEFAULT NULL COMMENT '更新人id',
                                    `updateBy` varchar(32) DEFAULT NULL COMMENT '更新人',
                                    `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4 COMMENT='器材归还记录 ';