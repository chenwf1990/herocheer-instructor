package com.herocheer.instructor.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.exception.CommonException;
import com.herocheer.common.utils.StringUtils;
import com.herocheer.instructor.dao.SysMenuDao;
import com.herocheer.instructor.domain.entity.SysMenu;
import com.herocheer.instructor.domain.entity.User;
import com.herocheer.instructor.domain.vo.MetaVO;
import com.herocheer.instructor.domain.vo.OptionTreeVO;
import com.herocheer.instructor.domain.vo.SysMenuVO;
import com.herocheer.instructor.service.SysMenuService;
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
 * @desc 菜单表(SysMenu)表服务实现类
 * @date 2021-01-07 17:07:30
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
@Slf4j
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuDao, SysMenu, Long> implements SysMenuService {

    /**
     * 查找菜单权限树 (封装菜单权限树)(hutool-treeUtil)
     *
     * @param user 系统用户
     * @return {@link List < Tree <Long>>}
     */
    @Override
    public List<Tree<Long>> findMenuTreeToUser(User user) {
        // 构建node列表
        List<TreeNode<Long>> nodeList = CollUtil.newArrayList();

        List<SysMenu> sysMenus = this.dao.selectMenuTreeToRole(new HashMap<>());
        Map<String, Object> hashMap = null;
        for (SysMenu sysMenu:sysMenus){
            hashMap = new HashMap();
            hashMap.put("path",sysMenu.getUrl());
            hashMap.put("component","layout/publics");
            hashMap.put("meta", MetaVO.builder().hidden(sysMenu.getStatus()).icon(sysMenu.getIcon()).title(sysMenu.getMenuName()).build());
            TreeNode<Long> treeNode = new TreeNode<Long>(sysMenu.getId(), sysMenu.getPid(), sysMenu.getMenuName(), 5).setExtra(hashMap);
            nodeList.add(treeNode);
        }
        // 最外面一层
        Map<String, Object> rootMap = new HashMap();
        rootMap.put("path","/");
        rootMap.put("component","layout/index");
        rootMap.put("redirect","/sportPolitical/sportPoliticalManage");
        nodeList.add(new TreeNode<Long>(0L, -1L, "社会体育指导员平台", 5).setExtra(rootMap));

        List<Tree<Long>> treeList = TreeUtil.build(nodeList, -1L);
        return treeList;
    }

    /**
     *  菜单树
     *
     * @return {@link List<Tree<Long>>}
     */
    @Override
    public OptionTreeVO findMenuTreeToRole() {
        // 构建node列表
        List<TreeNode<Long>> nodeList = CollUtil.newArrayList();

        List<OptionTreeVO> optionTreeList = this.dao.selectMenuTreeToUser(new HashMap<>());
        Map<String, Object> hashMap = null;
        for (OptionTreeVO optionTree:optionTreeList){
            hashMap = new HashMap();
            hashMap.put("label",optionTree.getName());
            TreeNode<Long> treeNode = new TreeNode<Long>(optionTree.getId(), optionTree.getPid(), optionTree.getName(), 5).setExtra(hashMap);
            nodeList.add(treeNode);
        }
        // 0表示最顶层的id是0
        List<Tree<Long>> treeList = TreeUtil.build(nodeList, 0L);

        OptionTreeVO optionTree = OptionTreeVO.builder().build();
        optionTree.setTreeList(treeList);
        // TODO 选中节点
        optionTree.setSelectedNode("1,2,3,4");
        return optionTree;
    }

    /**
     * 添加菜单
     *
     * @param sysMenuVO VO
     * @return {@link SysMenu}
     */
    @Override
    public SysMenu addMenu(SysMenuVO sysMenuVO) {
        SysMenu sysMenu = SysMenu.builder().build();
        BeanCopier.create(sysMenuVO.getClass(),sysMenu.getClass(),false).copy(sysMenuVO,sysMenu,null);
        //  上级为空事pid默认为0
        this.insert(sysMenu);
        return sysMenu;
    }

    /**
     * 通过id找到菜单
     *
     * @param id id
     * @return {@link SysMenu}
     */
    @Override
    public SysMenu findMenuById(Long id) {
        return this.get(id);
    }

    /**
     * 通过id修改菜单
     *
     * @param sysMenuVO 系统菜单签证官
     * @return {@link SysMenu}
     */
    @Override
    public SysMenu modifyMenuById(SysMenuVO sysMenuVO) {
        if(sysMenuVO.getId() == null || StringUtils.isBlank(sysMenuVO.getId().toString())){
            throw new CommonException("编辑ID不能为空");
        }
        SysMenu sysMenu = SysMenu.builder().build();
        BeanCopier.create(sysMenuVO.getClass(),sysMenu.getClass(),false).copy(sysMenuVO,sysMenu,null);
        this.update(sysMenu);
        return sysMenu;
    }

    /**
     * 通过id删除菜单
     *
     * @param id id
     */
    @Override
    public void removeMenuById(Long id) {
        // 物理删除
        this.delete(id);
    }

    /**
     * 分页查询菜单列表
     *
     * @param sysMenuVO VO
     * @return {@link Page <SysMenu>}
     */
    @Override
    public Page<SysMenu> findMenuByPage(SysMenuVO sysMenuVO) {
        Page page = Page.startPage(sysMenuVO.getPageNo(), sysMenuVO.getPageSize());
        List<SysMenu> sysUserList = this.dao.selectMenuByPage(sysMenuVO);
        page.setDataList(sysUserList);
        return page;
    }
}