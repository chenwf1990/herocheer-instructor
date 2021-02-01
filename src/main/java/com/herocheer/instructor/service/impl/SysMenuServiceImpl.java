package com.herocheer.instructor.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.entity.UserEntity;
import com.herocheer.common.exception.CommonException;
import com.herocheer.common.utils.StringUtils;
import com.herocheer.instructor.dao.SysMenuDao;
import com.herocheer.instructor.domain.entity.SysMenu;
import com.herocheer.instructor.domain.vo.MetaVO;
import com.herocheer.instructor.domain.vo.OptionTreeVO;
import com.herocheer.instructor.domain.vo.SysMenuVO;
import com.herocheer.instructor.enums.UserTypeEnums;
import com.herocheer.instructor.service.SysMenuService;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
     * @param currentUser 当前用户
     * @return {@link List<Tree<Long>>}
     */
    @Override
    public List<Tree<Long>> findMenuTreeToUser(UserEntity currentUser) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("status", false);

        List<SysMenu> sysMenus = null;
        Set<Long> longSet = null;
        // 非超级管理员
        if(!UserTypeEnums.sysAdmin.getCode().equals(currentUser.getUserType())){
            paramMap.put("userId", currentUser.getId());
            // 获取用户的角色
            Long [] roleArray01 = {2L,5L};
            // Long [] roleArray =
            paramMap.put("roleArray", roleArray01);
            sysMenus = this.dao.selectMenuTreeToRole(paramMap);
            longSet =  sysMenus.stream().map(menu -> menu.getPid()).collect(Collectors.toSet());
        }else {
            sysMenus = this.dao.selectMenuByPage(SysMenuVO.builder().status(false).build());
            longSet =  sysMenus.stream().map(menu -> menu.getPid()).collect(Collectors.toSet());
        }

        // 构建node列表
        List<TreeNode<Long>> nodeList = CollUtil.newArrayList();
        Map<String, Object> hashMap = null;
        for (SysMenu sysMenu:sysMenus){
            hashMap = new HashMap();
            hashMap.put("path",sysMenu.getUrl());
            // 无子节点的节点
            if (sysMenu.getPid().equals(0L) && longSet.contains(sysMenu.getId())) {
                hashMap.put("component", "layout/publics");
            } else {
                hashMap.put("component", sysMenu.getUrl().substring(1));
            }
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
    public OptionTreeVO findMenuTreeToRole(Long id) {
        // 构建node列表
        List<TreeNode<Long>> nodeList = CollUtil.newArrayList();

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("status", false);
        List<OptionTreeVO> optionTreeList = this.dao.selectMenuTreeToUser(paramMap);
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
        //  选中节点
        List<String> roleMenus = this.dao.selectedMenuNode(id);
        if (CollectionUtils.isEmpty(roleMenus)) {
            optionTree.setSelectedNode(null);
        } else {
            String str = String.join(",", roleMenus);
            optionTree.setSelectedNode(str);
        }

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