package com.herocheer.instructor.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.exception.CommonException;
import com.herocheer.common.utils.StringUtils;
import com.herocheer.instructor.dao.SysDeptDao;
import com.herocheer.instructor.domain.entity.SysDept;
import com.herocheer.instructor.domain.vo.SysDeptVO;
import com.herocheer.instructor.service.SysDeptService;
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
 * @desc 机构表、部门表、部门架构表(SysDept)表服务实现类
 * @date 2021-01-07 17:44:30
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
@Slf4j
public class SysDeptServiceImpl extends BaseServiceImpl<SysDeptDao, SysDept, Long> implements SysDeptService {

    /**
     * 添加机构
     *
     * @param sysDeptVO VO
     * @return {@link SysDept}
     */
    @Override
    public SysDept addDept(SysDeptVO sysDeptVO) {
        if(sysDeptVO.getPid() != 0L){
            // 0.*.*的结构 其中，0是顶级结构，第一个*是顶级结构下的结构，第二个*是顶级结构下的结构下的结构
            sysDeptVO.setLevel(this.get(sysDeptVO.getPid()).getLevel()+ "." + sysDeptVO.getPid());
        }
        SysDept sysDept = SysDept.builder().build();
        BeanCopier.create(sysDeptVO.getClass(),sysDept.getClass(),false).copy(sysDeptVO,sysDept,null);
        this.insert(sysDept);
        return sysDept;
    }

    /**
     * 删除机构
     *
     * @param id id
     */
    @Override
    public void removeDeptById(Long id) {
        // 物理删除
        this.delete(id);
    }

    /**
     * 发现部门通过id
     *
     * @param id id
     * @return {@link SysDept}
     */
    @Override
    public SysDept findDeptById(Long id) {
        return this.get(id);
    }

    /**
     * 修改机构
     *
     * @param sysDeptVO VO
     * @return {@link SysDept}
     */
    @Override
    public SysDept modifyDept(SysDeptVO sysDeptVO) {
        if(sysDeptVO.getId() == null || StringUtils.isBlank(sysDeptVO.getId().toString())){
            throw new CommonException("编辑ID不能为空");
        }
        SysDept sysDept = SysDept.builder().build();
        BeanCopier.create(sysDeptVO.getClass(),sysDept.getClass(),false).copy(sysDeptVO,sysDept,null);
        this.update(sysDept);
        return sysDept;
    }

    /**
     * 机构列表
     *
     * @param sysDeptVO VO
     * @return {@link Page <SysDept>}
     */
    @Override
    public Page<SysDept> findDeptByPage(SysDeptVO sysDeptVO) {
        Page page = Page.startPage(sysDeptVO.getPageNo(), sysDeptVO.getPageSize());
        sysDeptVO.setPid(null);
        List<SysDept> sysUserList = this.dao.selectDeptByPage(sysDeptVO);
        page.setDataList(sysUserList);
        return page;
    }

    /**
     * 部门下拉框
     *
     * @return {@link List <SysDept>}
     */
    @Override
    public List<SysDept> findDept() {
        return this.dao.selectDeptByPage(new SysDeptVO());
    }

    /**
     * 发现部门树
     *
     * @return {@link List<Tree<Long>>}
     */
    @Override
    public List<Tree<Long>> findDeptTree() {
        // 构建node列表
        List<TreeNode<Long>> nodeList = CollUtil.newArrayList();
        List<SysDept> sysDepts = this.dao.selectDeptByPage(SysDeptVO.builder().pid(null).build());
        sysDepts.stream().forEach(sysDept -> {
            Map<String, Object> hashMap = new HashMap();
            hashMap.put("label",sysDept.getDeptName());
            TreeNode<Long> treeNode = new TreeNode<Long>(sysDept.getId(), sysDept.getPid(), sysDept.getDeptName(), 5).setExtra(hashMap);
            nodeList.add(treeNode);
        });
        // 0表示最顶层的id是0
        List<Tree<Long>> treeList = TreeUtil.build(nodeList, 0L);
        return treeList;
    }

    /**
     * 通过id查询部门
     *
     * @param sysDeptVO 系统部门签证官
     * @return {@link Page<SysDept>}
     */
    @Override
    public Page<SysDept> findDeptById(SysDeptVO sysDeptVO) {
        Page page = Page.startPage(sysDeptVO.getPageNo(), sysDeptVO.getPageSize());
        sysDeptVO.setPid(sysDeptVO.getId());
        sysDeptVO.setId(null);
        List<SysDept> sysUserList = this.dao.selectDeptByPage(sysDeptVO);
        page.setDataList(sysUserList);
        return page;
    }
}