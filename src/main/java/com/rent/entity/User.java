package com.rent.entity;

import com.rent.base.BaseEntity;
import com.rent.common.constant.CommonConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

/**
 * 用户实体类
 * @author Exrickx
 */
@Data
@Entity
@Table(name = "t_user")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "手机")
    private String telPhone;

    @ApiModelProperty(value = "邮件")
    private String email;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "0女 1男 2保密")
    private Integer sex;

    @ApiModelProperty(value = "用户默认头像")
    private String avatar = "http://ow2h3ee9w.bkt.clouddn.com/%E4%B8%8B%E8%BD%BD.png";

    @ApiModelProperty(value = "0普通用户 1管理员")
    private Integer type = CommonConstant.TYPE_USER_NORMAL;

    @ApiModelProperty(value = "状态 默认0正常 -1拉黑")
    private Integer status = CommonConstant.STATUS_USER_NORMAL;

    @ApiModelProperty(value = "描述/详情/备注")
    private String description;

    @Transient
    @ApiModelProperty(value = "token")
    private String token;

    @Transient
    @ApiModelProperty(value = "新密码")
    private String newPass;

}
