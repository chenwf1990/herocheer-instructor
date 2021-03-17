package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.instructor.domain.entity.FitnessVideo;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.vo.FitnessVideoVo;
import com.herocheer.instructor.domain.vo.VideoQueryVo;

/**
 * @author chenwf
 * @desc  健身视频管理(FitnessVideo)表服务接口
 * @date 2021-03-17 09:46:10
 * @company 厦门熙重电子科技有限公司
 */
public interface FitnessVideoService extends BaseService<FitnessVideo,Long> {
    /**
     * @desc 健身视频列表查询
     * @author chenwf
     * @create 2021/3/17
     * @company 厦门熙重电子科技有限公司
     */
    Page<FitnessVideoVo> queryPageList(VideoQueryVo videoQueryVo);
}