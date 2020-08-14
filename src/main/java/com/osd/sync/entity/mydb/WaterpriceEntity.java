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
    * 用气类型表
    * </p>
*
* @author stuil
* @since 2020-08-13
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("waterprice")
    public class WaterpriceEntity implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 用气类型id
            */
            @TableId(value = "WP_id", type = IdType.AUTO)
    private Integer wpId;

            /**
            * 序列号
            */
        @TableField("WP_SerialNum")
    private String wpSerialnum;

            /**
            * 用气类型名称
            */
        @TableField("WP_WaterName")
    private String wpWatername;

            /**
            * 单价
            */
        @TableField("WP_Price")
    private Float wpPrice;

            /**
            * 附加费表id
            */
        @TableField("surcharge_SC_id")
    private Integer surchargeScId;

            /**
            * 是否可用
            */
        @TableField("WP_IsAvailable")
    private Integer wpIsavailable;

            /**
            * 更新时间
            */
        @TableField("WP_UpdateTime")
    private LocalDateTime wpUpdatetime;

            /**
            * 创建时间
            */
        @TableField("WP_CreateTime")
    private LocalDateTime wpCreatetime;

            /**
            * 备用字段1
            */
        @TableField("WP_Res1")
    private String wpRes1;

            /**
            * 备用字段2
            */
        @TableField("WP_Res2")
    private String wpRes2;

            /**
            * 备用字段3
            */
        @TableField("WP_Res3")
    private String wpRes3;


}
