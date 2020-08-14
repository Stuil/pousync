package com.osd.sync.entity.mydb;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author stuil
 * @since 2020-08-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("consumer")
public class ConsumerEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "CSM_id", type = IdType.AUTO)
    private Integer csmId;

    /**
     * 用户名称
     */
    @TableField("CSM_Name")
    private String csmName;

    /**
     * 联系方式1
     */
    @TableField("CSM_TEl1")
    private String csmTel1;

    /**
     * 联系方式2
     */
    @TableField("CSM_TEL2")
    private String csmTel2;

    /**
     * 地址
     */
    @TableField("CSM_Addr")
    private String csmAddr;

    /**
     * 小区id
     */
    @TableField("Community_Comm_id")
    private Integer communityCommId;

    /**
     * 是否在黑名单
     */
    @TableField("CSM_IsBlacklist")
    private Integer csmIsblacklist;

    /**
     * 是否开卡
     */
    @TableField("CSM_IsOpened")
    private Integer csmIsopened;

    /**
     * 员工id
     */
    @TableField("CSM_Employeeid")
    private Integer csmEmployeeid;

    /**
     * 是否删除
     */
    @TableField("CSM_IsDeleted")
    private Integer csmIsdeleted;

    /**
     * 备注说明
     */
    @TableField("CSM_Remark")
    private String csmRemark;

    /**
     * 创建时间
     */
    @TableField("CSM_CreateTime")
    private LocalDateTime csmCreatetime;

    /**
     * 更新时间
     */
    @TableField("CSM_UpdateTime")
    private LocalDateTime csmUpdatetime;

    /**
     * 开卡时间
     */
    @TableField("CSM_OpenTime")
    private LocalDateTime csmOpentime;

    /**
     * 备用字段1
     */
    @TableField("CSM_Res1")
    private String csmRes1;

    /**
     * 备用字段2
     */
    @TableField("CSM_Res2")
    private String csmRes2;

    /**
     * 备用字段3
     */
    @TableField("CSM_Res3")
    private String csmRes3;


}
