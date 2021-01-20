package com.herocheer.instructor.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.herocheer.cache.bean.RedisClient;
import com.herocheer.common.base.Page.Page;
import com.herocheer.instructor.dao.SysAreaDao;
import com.herocheer.instructor.domain.entity.SysArea;
import com.herocheer.instructor.domain.vo.AreaQueryVo;
import com.herocheer.instructor.service.SysAreaService;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
@Transactional
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
     * @param type 1无权限 2有数据权限
     * @return
     * @author chenwf
     * @desc 获取所有区域，树形态
     * @date 2021-01-07 09:50:58
     */
    @Override
    public List<Tree<Long>> getAllArea(int type) {
        List<SysArea> sysAreas = this.dao.findByLimit(new HashMap<>());
        if(type == 2){
            sysAreas = filterDataPermission(sysAreas);
        }
        //组装成树结构
        List<Tree<Long>> treeNodes = getTreeNode(sysAreas);
        return treeNodes;
    }

    private List<SysArea> filterDataPermission(List<SysArea> sysAreas) {
        //TODO 数据权限过滤
        return sysAreas;
    }

    //组装成树结构
    private List<Tree<Long>> getTreeNode(List<SysArea> sysAreas) {
        List<TreeNode<Long>> nodeList = sysAreas.stream().map(area -> {
            Map<String,Object> extra=new HashMap<>();
            extra.put("level",area.getLevel());
            extra.put("chinaCode",area.getChinaCode());
            extra.put("areaCode",area.getAreaCode());
            extra.put("label",area.getAreaName());
            TreeNode<Long> treeNode = new TreeNode<Long>(area.getId(),area.getPid(),area.getAreaName(),0)
                    .setExtra(extra);
            return treeNode;
        }).collect(Collectors.toList());
        List<Tree<Long>> trees = TreeUtil.build(nodeList,0L);
        return trees;
    }

}