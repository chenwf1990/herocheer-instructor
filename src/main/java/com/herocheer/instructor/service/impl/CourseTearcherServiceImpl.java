package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.exception.CommonException;
import com.herocheer.common.utils.StringUtils;
import com.herocheer.instructor.dao.CourseTearcherDao;
import com.herocheer.instructor.domain.entity.CourseTearcher;
import com.herocheer.instructor.domain.vo.CourseTearcherVO;
import com.herocheer.instructor.domain.vo.TearcherVO;
import com.herocheer.instructor.service.CourseTearcherService;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gaorh
 * @desc 授课老师(CourseTearcher)表服务实现类
 * @date 2021-03-29 17:12:09
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class CourseTearcherServiceImpl extends BaseServiceImpl<CourseTearcherDao, CourseTearcher, Long> implements CourseTearcherService {

    /**
     * 添加授课老师
     *
     * @param courseTearcherVO tearcher签证官
     * @return {@link CourseTearcher}
     */
    @Override
    public CourseTearcher addCourseTearcher(CourseTearcherVO courseTearcherVO) {
        CourseTearcher courseTearcher = CourseTearcher.builder().build();
        BeanCopier.create(courseTearcherVO.getClass(),courseTearcher.getClass(),false).copy(courseTearcherVO,courseTearcher,null);
        this.insert(courseTearcher);
        return courseTearcher;
    }

    /**
     * 修改授课老师
     *
     * @param courseTearcherVO tearcher签证官
     * @return {@link CourseTearcher}
     */
    @Override
    public CourseTearcher modifyCourseTearcher(CourseTearcherVO courseTearcherVO) {
        if(courseTearcherVO.getId() == null || StringUtils.isBlank(courseTearcherVO.getId().toString())){
            throw new CommonException("编辑ID不能为空");
        }
        CourseTearcher courseTearcher = CourseTearcher.builder().build();
        BeanCopier.create(courseTearcherVO.getClass(),courseTearcher.getClass(),false).copy(courseTearcherVO,courseTearcher,null);
        this.update(courseTearcher);
        return courseTearcher;
    }

    /**
     * 分页授课老师列表
     *
     * @param courseTearcherVO tearcher签证官
     * @return {@link Page <CourseTearcher>}
     */
    @Override
    public Page<CourseTearcher> findCourseTearcherByPage(CourseTearcherVO courseTearcherVO) {
        Page page = Page.startPage(courseTearcherVO.getPageNo(), courseTearcherVO.getPageSize());
        List<CourseTearcher> CourseTearcherList = this.dao.selectTearcherByPage(courseTearcherVO);
        page.setDataList(CourseTearcherList);
        return page;
    }

    /**
     * 查询授课老师
     *
     * @return {@link List<CourseTearcher>}
     */
    @Override
    public List<TearcherVO> findCourseTearcher() {
        Map<String, Object> paramMap = new HashMap();
        return this.dao.selectCourseTearcher(paramMap);
    }

    /**
     * 根据手机号查询授课老师
     *
     * @param phone 电话
     * @return {@link List<TearcherVO>}
     */
    @Override
    public List<TearcherVO> findCourseTearcherByPhone(String phone) {
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("phone",phone);
        return this.dao.selectCourseTearcher(paramMap);
    }
}