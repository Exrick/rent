package com.rent.entity;

import com.rent.base.BaseEntity;
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

    private String username;

    private String password;

    private String nickName;

    private String telPhone;

    private String email;

    private String address;

    /**
     * 0女 1男 2保密
     */
    private Integer sex;

    private String avatar;

    /**
     * 0普通用户 1管理员
     */
    private Integer type = 0;

}
