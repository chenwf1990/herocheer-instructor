package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.instructor.dao.FitnessVideoDao;
import com.herocheer.instructor.domain.entity.FitnessVideo;
import com.herocheer.instructor.domain.vo.FitnessVideoVo;
import com.herocheer.instructor.domain.vo.VideoQueryVo;
import com.herocheer.instructor.service.FitnessVideoService;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chenwf
 * @desc  健身视频管理(FitnessVideo)表服务实现类
 * @date 2021-03-17 09:46:10
 * @company 厦门熙重电子科技有限公司
 */
@Service
public class FitnessVideoServiceImpl extends BaseServiceImpl<FitnessVideoDao, FitnessVideo,Long> implements FitnessVideoService {

    /**
     * @param videoQueryVo
     * @desc 健身视频列表查询
     * @author chenwf
     * @create 2021/3/17
     * @company 厦门熙重电子科技有限公司
     */
    @Override
    public Page<FitnessVideoVo> queryPageList(VideoQueryVo videoQueryVo) {
        Page page = Page.startPage(videoQueryVo.getPageNo(),videoQueryVo.getPageSize());
        List<FitnessVideoVo> fitnessVideoVos = this.dao.queryPageList(videoQueryVo);
        page.setDataList(fitnessVideoVos);
        return page;
    }

    /**
     * 增加浏览数量
     *
     * @param id
     * @return
     */
    @Override
    public int addBrowseNum(Long id) {
        return this.dao.addBrowseNum(id);
    }
}