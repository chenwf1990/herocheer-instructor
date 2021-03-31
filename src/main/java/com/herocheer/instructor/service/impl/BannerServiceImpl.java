package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.instructor.dao.BannerDao;
import com.herocheer.instructor.domain.entity.Banner;
import com.herocheer.instructor.domain.vo.BannerQueryVo;
import com.herocheer.instructor.service.BannerService;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chenwf
 * @desc  banner管理(Banner)表服务实现类
 * @date 2021-03-17 09:43:08
 * @company 厦门熙重电子科技有限公司
 */
@Service
public class BannerServiceImpl extends BaseServiceImpl<BannerDao, Banner,Long> implements BannerService {

    @Override
    public Page<Banner> queryPageList(BannerQueryVo bannerQueryVo) {
        Page page = Page.startPage(bannerQueryVo.getPageNo(),bannerQueryVo.getPageSize());
        List<Banner> banners = this.dao.queryPageList(bannerQueryVo);
        page.setDataList(banners);
        return page;
    }
}