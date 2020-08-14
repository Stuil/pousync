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
    * 小区表
    * </p>
*
* @author stuil
* @since 2020-08-13
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("community")
    public class CommunityEntity implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 区域id
            */
            @TableId(value = "Comm_id", type = IdType.AUTO)
    private Integer commId;

            /**
            * 区域名称
            */
        @TableField("Comm_Name")
    private String commName;

            /**
            * 区域管理员
            */
        @TableField("Comm_Admin")
    private String commAdmin;

            /**
            * 区域备注说明
            */
        @TableField("Comm_Remark")
    private String commRemark;

            /**
            * 地区
            */
        @TableField("Comm_District")
    private String commDistrict;

            /**
            * 城市
            */
        @TableField("Comm_City")
    private String commCity;

            /**
            * 通信序列号
            */
        @TableField("Comm_SerialNum")
    private String commSerialnum;

            /**
            * 父级id
            */
        @TableField("Comm_ParentId")
    private Integer commParentid;

            /**
            * 员工id
            */
        @TableField("Comm_Employeeid")
    private Integer commEmployeeid;

            /**
            * 是否可用
            */
        @TableField("Comm_IsAvailable")
    private Integer commIsavailable;

            /**
            * 备用字段1
            */
        @TableField("Comm_Res1")
    private String commRes1;

            /**
            * 备用字段2
            */
        @TableField("Comm_Res2")
    private String commRes2;

            /**
            * 备用字段3
            */
        @TableField("Comm_res3")
    private String commRes3;

            /**
            * 创建时间
            */
        @TableField("Comm_CreateTime")
    private LocalDateTime commCreatetime;

            /**
            * 更新时间
            */
        @TableField("Comm_UpdateTime")
    private LocalDateTime commUpdatetime;


}
