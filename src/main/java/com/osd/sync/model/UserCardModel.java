package com.osd.sync.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @title: UserCardModel
 * @description: 金凤用户和卡信息Model
 * @date: 2020/8/13
 * @author: zwh
 * @copyright: Copyright (c) 2020
 * @version: 1.0
 */
@Component
@Data
public class UserCardModel implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Integer csmId;

    /**
     * 用户名称
     */
    private String csmName;

    /**
     * 联系方式1
     */
    private String csmTel1;

    /**
     * 联系方式2
     */
    private String csmTel2;

    /**
     * 地址
     */
    private String csmAddr;

    /**
     * 小区id
     */
    private Integer communityCommId;

    /**
     * 是否在黑名单
     */
    private Integer csmIsblacklist;

    /**
     * 是否开卡
     */
    private Integer csmIsopened;

    /**
     * 员工id
     */
    private Integer csmEmployeeid;

    /**
     * 是否删除
     */
    private Integer csmIsdeleted;

    /**
     * 备注说明
     */
    private String csmRemark;

    /**
     * 创建时间
     */
    private LocalDateTime csmCreatetime;

    /**
     * 更新时间
     */
    private LocalDateTime csmUpdatetime;

    /**
     * 开卡时间
     */
    private LocalDateTime csmOpentime;

    /**
     * 备用字段1
     */
    private String csmRes1;

    /**
     * 备用字段2
     */
    private String csmRes2;

    /**
     * 备用字段3
     */
    private String csmRes3;

    /**
     * 卡号，与用户id一致
     */
    private Integer cardId;

    /**
     * 购气次数
     */
    private Integer cardBuycount;

    /**
     * 表类型
     */
    private String cardWmtype;

    /**
     * 表版本
     */
    private String cardWmversion;

    /**
     * 表编号
     */
    private String cardWmpos;

    /**
     * 表初始值
     */
    private String cardWminitvalue;

    /**
     * 员工id
     */
    private Integer cardEmployeeid;

    /**
     * 备注说明
     */
    private String cardRemark;

    /**
     * 是否可用
     */
    private Integer cardIsavailable;

    /**
     * 备用字段1
     */
    private String cardRes1;

    /**
     * 备用字段2
     */
    private String cardRes2;

    /**
     * 备用字段3
     */
    private String cardRes3;

    /**
     * 创建时间
     */
    private LocalDateTime cardCreatetime;

    /**
     * 更新时间
     */
    private LocalDateTime cardUpdatetime;

    /**
     * 用气类型id
     */
    private Integer waterpriceWpId;

    /**
     * 累计用量
     */
    private Integer cardWmaccumulatedwater;

    /**
     * 上次剩余金额
     */
    private Double cardLastRemainMoney;

    /**
     * 本次剩余金额
     */
    private Double cardTodayRemainMoney;

}
