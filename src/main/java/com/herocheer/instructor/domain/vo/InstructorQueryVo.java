package com.herocheer.instructor.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * @author chenwf
 * @desc 驿站列表查询请求参数
 * @date 2021/1/4
 * @company 厦门熙重电子科技有限公司
 */
@NoArgsConstructor
@Data
public class InstructorQueryVo implements Serializable {
    @ApiModelProperty("审核状态 0待审核1审核通过2审核驳回")
    private Integer auditState;
    @ApiModelProperty("常驻区域")
    private String areaCode;
    @ApiModelProperty("姓名(支持前后模糊查询)")
    private String name;
    @ApiModelProperty("手机号码(支持前后模糊查询)")
    private String phone;
    @ApiModelProperty("指导项目(支持前后模糊查询)")
    private String guideProject;
    @ApiModelProperty("证书等级")
    private String certificateGrade;
    @ApiModelProperty("创建时间(开始时间)")
    private Long beginTime;
    @ApiModelProperty("创建时间(结束时间)")
    private Long endTime;
    @ApiModelProperty("审核状态集合0待审核1审核通过2审核驳回")
    private List<Integer> auditStates;
    @ApiModelProperty("数据来源 0pc端 1i健身 2小程序 3ios 4安卓 5导入")
    private Integer channel;
    @ApiModelProperty("页码")
    private int pageNo;
    @ApiModelProperty("页数")
    private int pageSize;
}
