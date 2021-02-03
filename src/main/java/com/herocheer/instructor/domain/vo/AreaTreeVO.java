package com.herocheer.instructor.domain.vo;

import cn.hutool.core.lang.tree.Tree;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 区域树
 *
 * @author gaorh
 * @create 2021-02-03
 */
@ApiModel(description = "区域树VO")
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AreaTreeVO {
    @ApiModelProperty("区域树节点")
    private List<Tree<Long>> treeNode;
    @ApiModelProperty("选中的节点")
    private String selectedNode;
}
