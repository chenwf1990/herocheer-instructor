package com.herocheer.instructor.service.impl;

import com.herocheer.instructor.domain.entity.UserCollect;
import com.herocheer.instructor.dao.UserCollectDao;
import com.herocheer.instructor.domain.vo.UserCollectVo;
import com.herocheer.instructor.service.UserCollectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenwf
 * @desc  用户收藏(UserCollect)表服务实现类
 * @date 2021-01-26 11:24:26
 * @company 厦门熙重电子科技有限公司
 */
@Service
public class UserCollectServiceImpl extends BaseServiceImpl<UserCollectDao, UserCollect,Long> implements UserCollectService {

    /**
     * @param userCollectVo
     * @return
     * @author chenwf
     * @desc 收藏/取消收藏
     * @date 2021-01-26 11:24:26
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int userCollect(UserCollectVo userCollectVo) {
        Map<String,Object> params = new HashMap<>();
        params.put("type", userCollectVo.getType());
        params.put("objectId", userCollectVo.getObjectId());
        params.put("openId", userCollectVo.getOpenId());
        List<UserCollect> collects = this.dao.findByLimit(params);
        int count = 1;//0 失败 1成功
        if(userCollectVo.getCollectType() == 1){//收藏
            if(!collects.isEmpty()){//已收藏，不必再新增数据
                return count;
            }
            UserCollect userCollect = new UserCollect();
            BeanUtils.copyProperties(userCollectVo,userCollect);
            count = this.dao.insert(userCollect);
        }else {//取消收藏
            if(collects.isEmpty()){//没有收藏的数据，不必再删除
                return count;
            }
            count = this.dao.deleteByParams(params);
        }
        return count;
    }
}