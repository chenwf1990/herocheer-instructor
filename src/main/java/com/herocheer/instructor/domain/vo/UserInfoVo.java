package com.herocheer.instructor.domain.vo;

import com.herocheer.common.base.entity.UserEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chenwf
 * @desc
 * @date 2021/2/2
 * @company 厦门熙重电子科技有限公司
 */
@Data
@ApiModel("用户信息")
public class UserInfoVo extends UserEntity {
    @ApiModelProperty("性别：0-未知、1-男、2-女")
    private Integer sex;
    @ApiModelProperty("头像")
    private String imgUrl;
    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("是否是指导员 false不是 true是")
    private Boolean instructorFlag;

    @ApiModelProperty("头像")
    private String headPic;

    @ApiModelProperty("tokenId")
    private String tokenId;

    @ApiModelProperty("登入状态")
    private Boolean ixmLoginStatus;

}
