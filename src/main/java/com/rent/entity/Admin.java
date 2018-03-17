package com.rent.entity;

import com.rent.base.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户实体类
 */
@Data
@Entity
@Table(name = "t_admin")
public class Admin extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String username;

    private String password;

    private String nickName;

    private String telPhone;

    private String email;

    private String avatar;

    private Integer type;

}
