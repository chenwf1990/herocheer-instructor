package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.exception.CommonException;
import com.herocheer.common.utils.StringUtils;
import com.herocheer.instructor.aspect.SysLog;
import com.herocheer.instructor.dao.SysRoleDao;
import com.herocheer.instructor.domain.entity.SysRole;
import com.herocheer.instructor.domain.entity.SysRoleArea;
import com.herocheer.instructor.domain.entity.SysRoleMenu;
import com.herocheer.instructor.domain.vo.SysRoleVO;
import com.herocheer.instructor.enums.OperationConst;
import com.herocheer.instructor.service.SysRoleService;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gaorh
 * @desc 后台角色表(SysRole)表服务实现类
 * @date 2021-01-07 17:48:15
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
@Slf4j
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleDao, SysRole, Long> implements SysRoleService {

    @Autowired
    private SysRoleService sysRoleService;
    /**
     * 添加角色
     *
     * @param sysRoleVO VO
     * @return {@link SysRole}
     */
    @Transactional
    @Override
    public SysRole addRole(SysRoleVO sysRoleVO) {
        // TODO 角色编码如何处理
        Map<String, Object> map = new HashMap();
        map.put("roleName",sysRoleVO.getRoleName());
        if(this.dao.selectSysRoleOne(map) == 1){
            throw new CommonException("角色名已存在");
        }

        SysRole sysRole = SysRole.builder().build();
        BeanCopier.create(sysRoleVO.getClass(),sysRole.getClass(),false).copy(sysRoleVO,sysRole,null);
        this.insert(sysRole);

        // 批量插入中间表
        sysRoleService.settingMenuToRole(sysRoleVO.getMenuId(), sysRole.getId());
        return sysRole;
    }

    /**
     * 设置角色菜单关联表
     *
     * @param menuIds 菜单id
     * @param roleId  角色id
     */
    @Override
    public void settingMenuToRole(String menuIds, Long roleId) {
        if(StringUtils.isNotBlank(menuIds)){
            String[] arr = menuIds.split(",");
            List<SysRoleMenu> list = new ArrayList<>();
            SysRoleMenu sysRoleMenu = null;
            for (int i = 0; i < arr.length; i++) {
                sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setRoleId(roleId);
                sysRoleMenu.setMenuId(Long.parseLong(arr[i]));
                list.add(sysRoleMenu);
            }
            this.dao.insertBatchSysRoleMenu(list);
        }
    }

    /**
     * 设置角色区域关联表
     *
     * @param areaIds 区域id
     * @param roleId  角色id
     */
    @Override
    public void settingAreaToRole(String areaIds, Long roleId) {
        if(StringUtils.isNotBlank(areaIds)){
            String[] arr = areaIds.split(",");
            List<SysRoleArea> list = new ArrayList<>();
            SysRoleArea sysRoleArea = null;
            for (int i = 0; i < arr.length; i++) {
                sysRoleArea = SysRoleArea.builder().build();
                sysRoleArea.setRoleId(roleId);
                sysRoleArea.setAreaId(Long.parseLong(arr[i]));
                list.add(sysRoleArea);
            }
            this.dao.insertBatchSysRoleArea(list);
        }

    }

    /**
     * 删除角色
     *
     * @param id id
     */
    @SysLog(module = "系统管理",bizType =  OperationConst.DELETE,bizDesc = "删除角色")
    @Override
    public void removeRoleById(Long id) {
        // 物理删除
        this.delete(id);
    }

    /**
     * 根据id获取角色信息
     *
     * @param id id
     * @return {@link SysRole}
     */
    @Override
    public SysRole findRoleById(Long id) {
        return this.get(id);
    }

    /**
     * 修改角色
     *
     * @param sysRoleVO 系统角色签证官
     * @return {@link SysRole}
     */
    @Override
    public SysRole modifyRole(SysRoleVO sysRoleVO) {
        if(sysRoleVO.getId() == null || StringUtils.isBlank(sysRoleVO.getId().toString())){
            throw new CommonException("编辑ID不能为空");
        }
        SysRole sysRole = SysRole.builder().build();
        BeanCopier.create(sysRoleVO.getClass(),sysRole.getClass(),false).copy(sysRoleVO,sysRole,null);
        this.update(sysRole);
        return sysRole;
    }

    /**
     * 角色列表
     *
     * @param sysRoleVO VO
     * @return {@link Page <SysRole>}
     */
    @Override
    public Page<SysRole> findRoleByPage(SysRoleVO sysRoleVO) {
        Page page = Page.startPage(sysRoleVO.getPageNo(), sysRoleVO.getPageSize());
        List<SysRole> sysUserList = this.dao.selectRoleByPage(sysRoleVO);
        page.setDataList(sysUserList);
        return page;
    }

    /**
     * 下拉框角色名
     *
     * @return {@link List <SysRole>}
     */
    @Override
    public List<SysRole> findRole() {
        return this.dao.selectRoleByPage(new SysRoleVO());
    }
}