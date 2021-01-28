package com.herocheer.instructor.service;

import com.herocheer.instructor.domain.entity.UserCollect;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.vo.UserCollectVo;

/**
 * @author chenwf
 * @desc  用户收藏(UserCollect)表服务接口
 * @date 2021-01-26 11:24:26
 * @company 厦门熙重电子科技有限公司
 */
public interface UserCollectService extends BaseService<UserCollect,Long> {
    /**
     * @author chenwf
     * @desc  收藏/取消收藏
     * @date 2021-01-26 11:24:26
     * @param userCollectVo
     * @return
     */
    int userCollect(UserCollectVo userCollectVo);
}