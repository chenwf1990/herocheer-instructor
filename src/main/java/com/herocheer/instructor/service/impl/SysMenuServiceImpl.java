package com.herocheer.instructor.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.herocheer.cache.bean.RedisClient;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.entity.UserEntity;
import com.herocheer.common.exception.CommonException;
import com.herocheer.common.utils.StringUtils;
import com.herocheer.instructor.dao.SysMenuDao;
import com.herocheer.instructor.domain.entity.SysMenu;
import com.herocheer.instructor.domain.vo.MetaVO;
import com.herocheer.instructor.domain.vo.OptionTreeVO;
import com.herocheer.instructor.domain.vo.SysMenuVO;
import com.herocheer.instructor.enums.CacheKeyConst;
import com.herocheer.instructor.enums.UserMarkEnums;
import com.herocheer.instructor.enums.UserTypeEnums;
import com.herocheer.instructor.service.SysMenuService;
import com.herocheer.instructor.service.SysOperationService;
import com.herocheer.instructor.service.UserService;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.HashSet;
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
@Slf4j
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuDao, SysMenu, Long> implements SysMenuService {

    @Autowired
    private SysOperationService sysOperationService;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisClient redisClient;
    /**
     * 查找菜单权限树 (封装菜单权限树)(hutool-treeUtil)
     *
     * @param currentUser 当前用户
     * @return {@link List<Tree<Long>>}
     */
    @Override
    public List<Tree<Long>> findMenuTreeToUser(UserEntity currentUser) {
        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("status", false);

        List<SysMenu> sysMenus = null;
        // 非超级管理员
        if(!UserTypeEnums.sysAdmin.getCode().equals(currentUser.getUserType())){
            paramMap.put("userId", currentUser.getId());

            // 获取用户的角色
            String key = StrUtil.format(CacheKeyConst.ROLEID, currentUser.getPhone(), currentUser.getId());
            String roleStr = redisClient.get(key);

            // Redis中未取到，就到DB中取
            if(StrUtil.isNotBlank(roleStr)){
                String [] roleArray = roleStr.split(",");
                paramMap.put("roleArray", roleArray);
            }else {
                List<String> stringList= userService.findRoleId(currentUser.getId());
                if(CollectionUtils.isEmpty(stringList)){
                    throw new CommonException("请联系管理员，分配用户角色给您");
                }
                paramMap.put("roleArray", stringList);
            }

            sysMenus = this.dao.selectMenuTreeToRole(paramMap);
            if(CollectionUtils.isEmpty(sysMenus)){
                throw new CommonException("请联系管理员，分配菜单权限给您");
            }
        }else {
            sysMenus = this.dao.selectMenuByPage(SysMenuVO.builder().mark(UserMarkEnums.INSTRUCTOR.getCode()).build());
        }

        // 获取子节点的pid
        Set<Long> longSet = new HashSet<>();
        sysMenus.stream().map(menu -> {
                if(!menu.getStatus()){
                    longSet.add(menu.getPid());
                }
                    return longSet;
            }
        ).collect(Collectors.toSet());

        // 封装node树
        List<TreeNode<Long>> nodeList = CollUtil.newArrayList();
        Map<String, Object> hashMap = null;
        for (SysMenu sysMenu:sysMenus){
            hashMap = new HashMap();
            hashMap.put("path",sysMenu.getUrl());
            // 无子节点的节点
            if (sysMenu.getPid().equals(9999L) && longSet.contains(sysMenu.getId())) {
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
        rootMap.put("redirect","");
        nodeList.add(new TreeNode<Long>(9999L, -1L, "社会体育指导员平台", 5).setExtra(rootMap));

        List<Tree<Long>> treeList = TreeUtil.build(nodeList, -1L);
        return treeList;
    }

    /**
     * 菜单树
     *
     * @param id   id
     * @param mark 马克
     * @return {@link OptionTreeVO}
     */
    @Override
    public OptionTreeVO findMenuTreeToRole(Long id,Integer mark) {
        // 构建node列表
        List<TreeNode<Long>> nodeList = CollUtil.newArrayList();

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("status", false);
        if(mark != null){
            paramMap.put("mark", mark);
        }else {
            paramMap.put("mark", 1);
        }
        List<OptionTreeVO> optionTreeList = this.dao.selectMenuTreeToUser(paramMap);
        Map<String, Object> hashMap = null;
        for (OptionTreeVO optionTree:optionTreeList){
            hashMap = new HashMap();
            hashMap.put("label",optionTree.getName());
            TreeNode<Long> treeNode = new TreeNode<Long>(optionTree.getId(), optionTree.getPid(), optionTree.getName(), 5).setExtra(hashMap);
            nodeList.add(treeNode);
        }
        // 0表示最顶层的id是0
        List<Tree<Long>> treeList = TreeUtil.build(nodeList, 9999L);

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
    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysMenu addMenu(SysMenuVO sysMenuVO) {
        SysMenu sysMenu = SysMenu.builder().build();
        BeanCopier.create(sysMenuVO.getClass(),sysMenu.getClass(),false).copy(sysMenuVO,sysMenu,null);
        //  上级为空事pid默认为0
        this.insert(sysMenu);

        // 菜单的操作功能
        if(!CollectionUtils.isEmpty(sysMenuVO.getSysOperationList())){
            sysMenuVO.getSysOperationList().stream().forEach(sysOperation -> {
                // 批量插入
                sysOperation.setPid(sysMenu.getId());
                sysOperation.setId(IdUtil.getSnowflake(1, 1).nextId());
                sysOperationService.insert(sysOperation);
            });
        }
        return sysMenu;
    }

    /**
     * 通过id找到菜单
     *
     * @param id id
     * @return {@link SysMenuVO}
     */
    @Override
    public SysMenuVO findMenuById(Long id) {
        SysMenu sysMenu = this.get(id);
        if(ObjectUtils.isEmpty(sysMenu)){
            throw new CommonException("查无数据");
        }
        SysMenuVO sysMenuVO = SysMenuVO.builder().build();
        BeanCopier.create(sysMenu.getClass(),sysMenuVO.getClass(),false).copy(sysMenu,sysMenuVO,null);
        // 回显菜单操作
        sysMenuVO.setSysOperationList(sysOperationService.findOperationByPid(id));
        return sysMenuVO;
    }

    /**
     * 通过id修改菜单
     *
     * @param sysMenuVO VO
     * @return {@link SysMenu}
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysMenu modifyMenuById(SysMenuVO sysMenuVO) {
        if(sysMenuVO.getId() == null || StringUtils.isBlank(sysMenuVO.getId().toString())){
            throw new CommonException("编辑ID不能为空");
        }
        SysMenu sysMenu = SysMenu.builder().build();
        BeanCopier.create(sysMenuVO.getClass(),sysMenu.getClass(),false).copy(sysMenuVO,sysMenu,null);
        this.update(sysMenu);

        // 菜单的操作功能
        sysOperationService.delete(sysMenuVO.getId());

        if(!CollectionUtils.isEmpty(sysMenuVO.getSysOperationList())){
            sysMenuVO.getSysOperationList().stream().forEach(sysOperation -> {
                // 更新 = 删除 + 插入
                sysOperationService.insert(sysOperation);
            });
        }
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
        if(sysMenuVO.getMark() == null){
            sysMenuVO.setMark(1);
        }
        Page page = Page.startPage(sysMenuVO.getPageNo(), sysMenuVO.getPageSize());
        List<SysMenu> sysUserList = this.dao.selectMenuByPage(sysMenuVO);
        page.setDataList(sysUserList);
        return page;
    }

    /**
     * 根据用户角色获取权限
     *
     * @param map 地图
     * @return {@link List<SysMenu>}
     */
    @Override
    public List<SysMenu> findMenuTreeToRole(Map<String, Object> map) {
        return this.dao.selectMenuTreeToRole(map);
    }
}