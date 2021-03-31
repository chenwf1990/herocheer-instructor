
package com.herocheer.instructor.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.exception.CommonException;
import com.herocheer.common.utils.StringUtils;
import com.herocheer.instructor.aspect.SysLog;
import com.herocheer.instructor.dao.SysRoleDao;
import com.herocheer.instructor.domain.entity.SysRole;
import com.herocheer.instructor.domain.entity.SysRoleArea;
import com.herocheer.instructor.domain.entity.SysRoleMenu;
import com.herocheer.instructor.domain.entity.User;
import com.herocheer.instructor.domain.vo.SysRoleVO;
import com.herocheer.instructor.enums.AuditUnitEnums;
import com.herocheer.instructor.enums.OperationConst;
import com.herocheer.instructor.enums.UserTypeEnums;
import com.herocheer.instructor.service.SysRoleService;
import com.herocheer.instructor.service.UserService;
import com.herocheer.instructor.utils.PinYinUtil;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.Valid;
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
@Slf4j
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleDao, SysRole, Long> implements SysRoleService {

    @Autowired
    private SysRoleService sysRoleService;
    @Resource
    private UserService userService;
    /**
     * 添加角色
     *
     * @param sysRoleVO VO
     * @return {@link SysRole}
     */
    @SysLog(module = "系统管理",bizType = OperationConst.INSERT,bizDesc = "添加角色")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysRole addRole(@Valid SysRoleVO sysRoleVO) {
        Map<String, Object> roleMap = new HashMap();
        roleMap.put("roleName",sysRoleVO.getRoleName());
        if(this.dao.selectSysRoleOne(roleMap) >= 1){
            throw new CommonException("角色名已存在");
        }

        SysRole sysRole = SysRole.builder().build();
        BeanCopier.create(sysRoleVO.getClass(),sysRole.getClass(),false).copy(sysRoleVO,sysRole,null);

        // 处理角色编码重复
        Map<String, Object> codeMap = new HashMap();
        // 角色编码取用角色名的拼首字母
        String  oldCode  = PinYinUtil.toFirstChar(sysRole.getRoleName()).toLowerCase();
        codeMap.put("code",oldCode);
        int sum = 1;
        String newCode = null;
        if(this.dao.selectSysRoleOne(codeMap) >= 1){

            do{
                newCode = oldCode +"0" + sum++;
                codeMap.put("code",newCode);
            }while (this.dao.selectSysRoleOne(codeMap) >= 1);

            sysRole.setCode(newCode);
        }else {
            sysRole.setCode(oldCode);
        }

        this.insert(sysRole);

        // 批量插入中间表
        // sysRoleService.settingMenuToRole(sysRoleVO.getMenuId(), sysRole.getId());
        return sysRole;
    }

    /**
     * 设置角色菜单关联表
     *
     * @param menuIds 菜单id
     * @param roleId  角色id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void settingMenuToRole(String menuIds, Long roleId) {
        // 删除关联
        this.dao.deleteMenuById(roleId);

        // 批量插入
        if(CharSequenceUtil.isNotBlank(menuIds)){
            String[] arr = menuIds.split(",");
            List<SysRoleMenu> list = new ArrayList<>();

            SysRoleMenu sysRoleMenu = null;
            for (int i = 0; i < arr.length; i++) {
                /**
                 *
                 * 业务场景：1、当角色分配的菜单里有包含：培训课程信息查看、驿站招募查询和赛事活动招募查看时，需默认新增课程详情和驿站招募详情
                 * @Date 2021/3/30 15:27
                 **/
                if( "76".equals(arr[i])|| "14".equals(arr[i])){
                    sysRoleMenu = new SysRoleMenu();
                    sysRoleMenu.setRoleId(roleId);
                    sysRoleMenu.setMenuId(Long.parseLong("78"));
                    list.add(sysRoleMenu);
                }
                if("64".equals(arr[i])){
                    sysRoleMenu = new SysRoleMenu();
                    sysRoleMenu.setRoleId(roleId);
                    sysRoleMenu.setMenuId(Long.parseLong("79"));
                    list.add(sysRoleMenu);
                }

                // 正常情况
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
        // 删除关联
        this.dao.deleteAreaById(roleId);

        if(CharSequenceUtil.isNotBlank(areaIds)){
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
    @Transactional(rollbackFor = Exception.class)
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
    @Transactional(rollbackFor = Exception.class)
    public SysRole modifyRole(@Valid SysRoleVO sysRoleVO) {
        if(sysRoleVO.getId() == null || CharSequenceUtil.isBlank(sysRoleVO.getId().toString())){
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
        Page<SysRole> page = Page.startPage(sysRoleVO.getPageNo(), sysRoleVO.getPageSize());
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

    /**
     * 检测是否有审批的权限
     *
     * @param curUserId     审批人
     * @param auditUnitType 审批类型
     * @return
     */
    @Override
    public boolean checkIsAuditAuth(Long curUserId, String auditUnitType) {
        String roleCode = AuditUnitEnums.getRoleCode(auditUnitType);
        if(StringUtils.isEmpty(roleCode)){
            throw new CommonException("审批编码异常");
        }
        User user = userService.get(curUserId);
        if(user.getUserType().equals(UserTypeEnums.sysAdmin.getCode())){
            return true;
        }
        int count = this.dao.checkIsAuditAuth(curUserId,roleCode);
        if(count > 0){
            return true;
        }
        return false;
    }
}