package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.exception.CommonException;
import com.herocheer.common.utils.StringUtils;
import com.herocheer.instructor.dao.SysDictDao;
import com.herocheer.instructor.domain.entity.SysDict;
import com.herocheer.instructor.domain.vo.SysDictVO;
import com.herocheer.instructor.service.SysDictService;
import com.herocheer.instructor.utils.PinYinUtil;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author gaorh
 * @desc 系统字典表(SysDict)表服务实现类
 * @date 2021-01-07 17:18:16
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Slf4j
public class SysDictServiceImpl extends BaseServiceImpl<SysDictDao, SysDict, Long> implements SysDictService {

    /**
     * 添加字典
     *
     * @param sysDictVO VO
     * @return {@link SysDict}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysDict addDict(SysDictVO sysDictVO) {
        SysDict sysDict = SysDict.builder().build();
        BeanCopier.create(sysDictVO.getClass(),sysDict.getClass(),false).copy(sysDictVO,sysDict,null);
        if(StringUtils.isBlank(sysDictVO.getDictCode())){
            sysDict.setDictCode(PinYinUtil.toFirstChar(sysDictVO.getDictName()).toUpperCase());
        }
        this.insert(sysDict);
        return sysDict;
    }

    /**
     * 通过id删除dict
     *
     * @param id id
     */
    @Override
    public void removeDictById(Long id) {
        this.delete(id);
    }

    /**
     * 通过id查询dict
     *
     * @param id id
     * @return {@link SysDict}
     */
    @Override
    public SysDict findDictById(Long id) {
        return this.get(id);
    }

    /**
     * 修改字典
     *
     * @param sysDictVO sys dict签证官
     * @return {@link SysDict}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysDict modifyDict(SysDictVO sysDictVO) {
        if(sysDictVO.getId() == null || StringUtils.isBlank(sysDictVO.getId().toString())){
            throw new CommonException("编辑ID不能为空");
        }
        SysDict sysDict =  SysDict.builder().build();
        BeanCopier.create(sysDictVO.getClass(),sysDict.getClass(),false).copy(sysDictVO,sysDict,null);

        this.update(sysDict);
        return sysDict;
    }

    /**
     * 分页查询字典列表
     *
     * @param sysDictVO sys dict签证官
     * @return {@link Page <SysDict>}
     */
    @Override
    public Page<SysDict> findDictByPage(SysDictVO sysDictVO) {
        Page page = Page.startPage(sysDictVO.getPageNo(), sysDictVO.getPageSize());
        sysDictVO.setStatus(null);
        List<SysDict> sysDicts = this.dao.selectDictByPage(sysDictVO);
        page.setDataList(sysDicts);
        return page;
    }

    /**
     * 查询字典名
     *
     * @return {@link List <SysDict>}
     */
    @Override
    public List<SysDict> findDictByPid(String type) {
        return this.dao.selectDictByPage( SysDictVO.builder().status(true).pid(type.toUpperCase()).build());
    }

    /**
     * 模糊查询字典
     *
     * @param dictName dict类型名称
     * @return {@link List<SysDict>}
     */
    @Override
    public List<SysDict> findDictLikeDictName(String pid,String dictName) {
        return this.dao.selectDictByPage( SysDictVO.builder().status(true).pid(pid).dictName(dictName).build());
    }
}