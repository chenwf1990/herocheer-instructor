package com.herocheer.instructor.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.herocheer.cache.bean.RedisClient;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.exception.CommonException;
import com.herocheer.common.utils.StringUtils;
import com.herocheer.instructor.dao.SysAreaDao;
import com.herocheer.instructor.domain.entity.SysArea;
import com.herocheer.instructor.domain.vo.AreaQueryVo;
import com.herocheer.instructor.domain.vo.AreaVO;
import com.herocheer.instructor.service.SysAreaService;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.geom.Area;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author chenwf
 * @desc  区域管理（省市区街道社区）(SysArea)表服务实现类
 * @date 2021-01-07 09:50:58
 * @company 厦门熙重电子科技有限公司
 */
@Service
public class SysAreaServiceImpl extends BaseServiceImpl<SysAreaDao, SysArea,Long> implements SysAreaService {
    @Resource
    private RedisClient redisClient;

    /**
     * @param areaQueryVo
     * @return
     * @author chenwf
     * @desc 区域列表查询
     * @date 2021-01-07 09:50:58
     */
    @Override
    public Page<SysArea> queryPageList(AreaQueryVo areaQueryVo) {
        Page page = Page.startPage(areaQueryVo.getPageNo(),areaQueryVo.getPageSize());
        List<SysArea> areas = this.dao.queryPageList(areaQueryVo);
        page.setDataList(areas);
        return page;
    }

    /**
     * 通过id查找子区域
     *
     * @param areaQueryVo 区域查询签证官
     * @return {@link Page<SysArea>}
     */
    @Override
    public Page<SysArea> findAreaById(AreaQueryVo areaQueryVo) {
        if(areaQueryVo.getId() == null || StringUtils.isBlank(areaQueryVo.getId().toString())){
            throw new CommonException("ID不能为空");
        }
        Page page = Page.startPage(areaQueryVo.getPageNo(),areaQueryVo.getPageSize());
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("pid",areaQueryVo.getId());
        List<SysArea> areas = this.dao.selectAreaById(paramMap);
        page.setDataList(areas);
        return page;
    }

    /**
     * 找到区域节点
     *
     * @param id id
     * @return {@link List<String>}
     */
    @Override
    public List<String> findAreaNode(Long id) {
        return this.dao.selectedAreaNode(id);
    }

    /**
     * 添加区域
     *
     * @param areaVO VO
     * @return {@link Area}
     */
    @Override
    public SysArea addArea(AreaVO areaVO) {
        SysArea area = SysArea.builder().build();
        BeanCopier.create(areaVO.getClass(),area.getClass(),false).copy(areaVO,area,null);
        this.insert(area);
        return area;
    }

    /**
     * @param type 1无权限 2有数据权限
     * @return
     * @author chenwf
     * @desc 获取所有区域，树形态
     * @date 2021-01-07 09:50:58
     */
    @Override
    public List<Tree<Long>> getAllArea(int type) {
        return getAllArea(type,0L);
    }

    /**
     * 过滤数据权限
     *
     * @param sysAreas 系统领域
     * @return {@link List<SysArea>}
     */
    private List<SysArea> filterDataPermission(List<SysArea> sysAreas) {

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("status", true);
        paramMap.put("userId", 8L);
//        paramMap.put("userId", user.getId());
        Long [] roleArray01 = {2L,5L};
//        Long [] roleArray =
        paramMap.put("roleArray", roleArray01);

        return this.dao.selectAreaTreeToRole(paramMap);
    }

    //组装成树结构
    private List<Tree<Long>> getTreeNode(List<SysArea> sysAreas, long parentId) {
        List<TreeNode<Long>> nodeList = sysAreas.stream().map((SysArea area) -> {
            Map<String,Object> extra=new HashMap<>();
            extra.put("level",area.getLevel());
            extra.put("chinaCode",area.getChinaCode());
            extra.put("areaCode",area.getAreaCode());
            extra.put("label",area.getAreaName());
            TreeNode<Long> treeNode = new TreeNode<Long>(area.getId(),area.getPid(),area.getAreaName(),0)
                    .setExtra(extra);
            return treeNode;
        }).collect(Collectors.toList());
        List<Tree<Long>> trees = TreeUtil.build(nodeList,parentId);
        return trees;
    }

    /**
     * @param type     1无权限 2有数据权限
     * @param parentId
     * @return
     * @author chenwf
     * @desc 获取所有区域，树形态
     * @date 2021-01-07 09:50:58
     */
    @Override
    public List<Tree<Long>> getAllArea(int type, Long parentId) {
        List<SysArea> sysAreas = this.dao.findByLimit(new HashMap<>());
        if(type == 2){
//            sysAreas = filterDataPermission(sysAreas);
        }
        //组装成树结构
        if(parentId == null){
            parentId = 2L;//厦门市下所有数据(不包含厦门市顶层)
        }
        List<Tree<Long>> treeNodes = getTreeNode(sysAreas,parentId);
        return treeNodes;
    }
}