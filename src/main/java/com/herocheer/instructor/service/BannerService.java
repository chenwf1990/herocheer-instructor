package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.instructor.domain.entity.Banner;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.vo.BannerQueryVo;

/**
 * @author chenwf
 * @desc  banner管理(Banner)表服务接口
 * @date 2021-03-17 09:43:08
 * @company 厦门熙重电子科技有限公司
 */
public interface BannerService extends BaseService<Banner,Long> {

    Page<Banner> queryPageList(BannerQueryVo bannerQueryVo);
}