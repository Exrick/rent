package com.rent.common.constant;


/**
 * 常量
 * @author Exrickx
 */
public interface CommonConstant {

    /**
     * 用户正常状态
     */
    Integer STATUS_USER_NORMAL=0;

    /**
     * 用户禁用状态
     */
    Integer STATUS_USER_LOCK=-1;

    /**
     * 普通用户
     */
    Integer TYPE_USER_NORMAL=0;

    /**
     * 管理员
     */
    Integer TYPE_USER_ADMIN=1;

    /**
     * 性别男
     */
    Integer SEX_MAN=0;

    /**
     * 性别女
     */
    Integer SEX_WOMAN=1;

    /**
     * 性别保密
     */
    Integer SEX_SECRET=2;

    /**
     * 发布状态 默认待审核
     */
    Integer STATUS_RENT_DEFAULT=0;

    /**
     * 发布状态 发布
     */
    Integer STATUS_RENT_POST=1;

    /**
     * 发布状态 驳回
     */
    Integer STATUS_RENT_BACK=2;

    /**
     * 发布状态 下架
     */
    Integer STATUS_RENT_CANCEL=3;

    /**
     * 成交状态
     */
    Integer STATUS_RENT_DEAL=1;

    /**
     * 正常状态
     */
    Integer STATUS_NORMAL=0;
}
