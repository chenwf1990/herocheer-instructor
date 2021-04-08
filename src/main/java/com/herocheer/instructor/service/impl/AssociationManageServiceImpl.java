package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.constants.ResponseCode;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.domain.entity.AssociationManage;
import com.herocheer.instructor.dao.AssociationManageDao;
import com.herocheer.instructor.domain.vo.ApplicationListVo;
import com.herocheer.instructor.domain.vo.AssociationDictVo;
import com.herocheer.instructor.domain.vo.AssociationManageQueryVo;
import com.herocheer.instructor.domain.vo.AssociationManageVo;
import com.herocheer.instructor.service.AssociationManageService;
import com.herocheer.instructor.service.AssociationMemberService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author makejava
 * @desc  协会管理(AssociationManage)表服务实现类
 * @date 2021-04-07 09:31:30
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class AssociationManageServiceImpl extends BaseServiceImpl<AssociationManageDao, AssociationManage,Long> implements AssociationManageService {

    @Resource
    private AssociationMemberService associationMemberService;

    @Override
    public Page<AssociationManageVo> queryPage(AssociationManageQueryVo queryVo) {
        Page page = Page.startPage(queryVo.getPageNo(),queryVo.getPageSize());
        List<AssociationManageVo> list=this.dao.findList(queryVo);
        page.setDataList(list);
        return page;
    }

    @Override
    public AssociationManage getAssociation(Long id) {
        AssociationManage associationManage=this.dao.get(id);
        return associationManage;
    }

    @Override
    public Integer addAssociation(AssociationManage associationManage) {
        return this.dao.insert(associationManage);
    }

    @Override
    public Integer updateAssociation(AssociationManage associationManage) {
        return this.dao.update(associationManage);
    }

    @Override
    public Integer delAssociation(Long id) {
        Map<String,Object> map=new HashedMap();
        map.put("associationId",id);
        //判断改协会下 是否还有成员
        Integer membersNumber=associationMemberService.count(map);
        if(membersNumber>0){
            throw new CommonException(ResponseCode.SERVER_ERROR, "该协会下还存在成员,无法删除!");
        }
        return this.dao.delete(id);
    }

    @Override
    public List<AssociationDictVo> getAssociationDict() {
        return this.dao.getAssociationDict();
    }
}