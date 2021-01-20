package com.herocheer.instructor.service.impl;

import com.herocheer.instructor.dao.SysDictDao;
import com.herocheer.instructor.domain.entity.SysDict;
import com.herocheer.instructor.service.SysDictService;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author gaorh
 * @desc 系统字典表(SysDict)表服务实现类
 * @date 2021-01-07 17:18:16
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Slf4j
public class SysDictServiceImpl extends BaseServiceImpl<SysDictDao, SysDict, Long> implements SysDictService {

}