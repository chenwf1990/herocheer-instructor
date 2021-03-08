package com.herocheer.instructor.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        // 如果pid为空，设置默认值：0
        if(StringUtils.isBlank(sysDictVO.getPid())){
            sysDict.setPid("0");
        }

        // 如果code为空，设置默认值：中文拼音的首字母
        if(StringUtils.isBlank(sysDictVO.getDictCode())){
            // 处理角色编码重复
            Map<String, Object> codeMap = new HashMap();
            // 角色编码取用角色名的拼首字母
            String  oldCode  = PinYinUtil.toFirstChar(sysDictVO.getDictName()).toUpperCase();
            codeMap.put("dictCode",oldCode);
            int sum = 1;
            String newCode = null;
            if(this.dao.selectSysDictOne(codeMap) >= 1){
                do{
                    newCode = oldCode +"0" + sum++;
                    codeMap.put("dictCode",newCode);
                }while (this.dao.selectSysDictOne(codeMap) >= 1);
                sysDict.setDictCode(newCode);
            }else {
                sysDict.setDictCode(oldCode);
            }
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

    /**
     * 通过pid找到dict树
     *
     * @return {@link List< Tree <Long>>}
     */
    @Override
    public List<Tree<Long>> findDictTreeByPid() {
        List<SysDict>  sysDictList = findDictByPid("0");
        List<TreeNode<Long>> nodeList = CollUtil.newArrayList();
        sysDictList.forEach((SysDict dict)->{
            Map<String, Object> hashMap = new HashMap();
            hashMap.put("label",dict.getDictName());
            hashMap.put("code",dict.getDictCode());
            TreeNode<Long> treeNode = new TreeNode<Long>(dict.getId(), Long.valueOf(dict.getPid()), dict.getDictName(), 5).setExtra(hashMap);
            nodeList.add(treeNode);
        });
        List<Tree<Long>> treeList = TreeUtil.build(nodeList, 0L);
        return treeList;
    }
}