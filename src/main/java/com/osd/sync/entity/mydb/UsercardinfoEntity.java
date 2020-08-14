package com.osd.sync.entity.mydb;

    import com.baomidou.mybatisplus.annotation.TableName;
    import com.baomidou.mybatisplus.annotation.TableId;
    import java.time.LocalDateTime;
    import com.baomidou.mybatisplus.annotation.TableField;
    import java.io.Serializable;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 用户卡信息
    * </p>
*
* @author stuil
* @since 2020-08-13
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("usercardinfo")
    public class UsercardinfoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 卡号，与用户id一致
            */
            @TableId("Card_id")
    private Integer cardId;

            /**
            * 购气次数
            */
        @TableField("Card_BuyCount")
    private Integer cardBuycount;

            /**
            * 表类型
            */
        @TableField("Card_WmType")
    private String cardWmtype;

            /**
            * 表版本
            */
        @TableField("Card_WmVersion")
    private String cardWmversion;

            /**
            * 表编号
            */
        @TableField("Card_WmPos")
    private String cardWmpos;

            /**
            * 表初始值
            */
        @TableField("Card_WmInitValue")
    private String cardWminitvalue;

            /**
            * 员工id
            */
        @TableField("Card_Employeeid")
    private Integer cardEmployeeid;

            /**
            * 备注说明
            */
        @TableField("Card_Remark")
    private String cardRemark;

            /**
            * 是否可用
            */
        @TableField("Card_IsAvailable")
    private Integer cardIsavailable;

            /**
            * 备用字段1
            */
        @TableField("Card_Res1")
    private String cardRes1;

            /**
            * 备用字段2
            */
        @TableField("Card_Res2")
    private String cardRes2;

            /**
            * 备用字段3
            */
        @TableField("Card_Res3")
    private String cardRes3;

            /**
            * 创建时间
            */
        @TableField("Card_CreateTime")
    private LocalDateTime cardCreatetime;

            /**
            * 更新时间
            */
        @TableField("Card_UpdateTime")
    private LocalDateTime cardUpdatetime;

            /**
            * 用气类型id
            */
        @TableField("waterprice_WP_id")
    private Integer waterpriceWpId;

            /**
            * 累计用量
            */
        @TableField("Card_WmAccumulatedWater")
    private Integer cardWmaccumulatedwater;

            /**
            * 上次剩余金额
            */
        @TableField("Card_last_remain_money")
    private Double cardLastRemainMoney;

            /**
            * 本次剩余金额
            */
        @TableField("Card_today_remain_money")
    private Double cardTodayRemainMoney;


}
