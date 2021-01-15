package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.exception.CommonException;
import com.herocheer.common.utils.StringUtils;
import com.herocheer.instructor.dao.SysRoleDao;
import com.herocheer.instructor.domain.entity.SysRole;
import com.herocheer.instructor.domain.vo.SysRoleVO;
import com.herocheer.instructor.service.SysRoleService;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    /**
     * 添加角色
     *
     * @param sysRoleVO VO
     * @return {@link SysRole}
     */
    @Override
    public SysRole addRole(SysRoleVO sysRoleVO) {
        SysRole sysRole = SysRole.builder().build();
        BeanCopier.create(sysRoleVO.getClass(),sysRole.getClass(),false).copy(sysRoleVO,sysRole,null);
        // TODO 角色编码
        this.insert(sysRole);
        // TODO 是否需要直接和菜单建立关联还是独立功能单独授权
        return sysRole;
    }

    /**
     * 删除角色
     *
     * @param id id
     */
    @Override
    public void removeRoleById(Long id) {
        // 物理删除和级联删除
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