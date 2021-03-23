package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.FitnessVideo;
import com.herocheer.instructor.domain.vo.FitnessVideoVo;
import com.herocheer.instructor.domain.vo.VideoQueryVo;
import com.herocheer.mybatis.base.dao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chenwf
 * @desc  健身视频管理(FitnessVideo)表数据库访问层
 * @date 2021-03-17 09:46:10
 * @company 厦门熙重电子科技有限公司
 */
public interface FitnessVideoDao extends BaseDao<FitnessVideo,Long>{

    List<FitnessVideoVo> queryPageList(VideoQueryVo videoQueryVo);

    /**
     * 增加浏览数量
     * @param id
     * @return
     */
    int addBrowseNum(@Param("id") Long id);
}