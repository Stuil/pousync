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
    * 卡片记录，购气，补气、退气表
    * </p>
*
* @author stuil
* @since 2020-08-13
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("cardoprecord")
    public class CardoprecordEntity implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 记录id
            */
            @TableId(value = "CO_id", type = IdType.AUTO)
    private Integer coId;

            /**
            * 操作类型
            */
        @TableField("CO_OPType")
    private Integer coOptype;

            /**
            * 用户id
            */
        @TableField("CO_ConsumerId")
    private Integer coConsumerid;

            /**
            * 操作员id
            */
        @TableField("CO_EmployeeId")
    private Integer coEmployeeid;

            /**
            * 用气类型
            */
        @TableField("CO_WaterType")
    private String coWatertype;

            /**
            * 购气量：负数为退气
            */
        @TableField("CO_WaterCount")
    private Integer coWatercount;

            /**
            * 用气类型说明
            */
        @TableField("CO_WaterPrice")
    private String coWaterprice;

            /**
            * 单价
            */
        @TableField("CO_unitNormalFee")
    private Float coUnitnormalfee;

            /**
            * 附加费
            */
        @TableField("CO_unitSurcharge")
    private Float coUnitsurcharge;

            /**
            * 总费用
            */
        @TableField("CO_TotalNormalFee")
    private Float coTotalnormalfee;

            /**
            * 总附加费
            */
        @TableField("CO_TotalSurcharge")
    private Float coTotalsurcharge;

            /**
            * 备注
            */
        @TableField("CO_Remark")
    private String coRemark;

            /**
            * 创建时间
            */
        @TableField("CO_CreateTime")
    private LocalDateTime coCreatetime;

            /**
            * 更新时间
            */
        @TableField("CO_UpdateTime")
    private LocalDateTime coUpdatetime;

            /**
            * 是否打印
            */
        @TableField("CO_IsPrint")
    private Integer coIsprint;

            /**
            * 免费（补气）气量
            */
        @TableField("CO_Freewater")
    private Integer coFreewater;

            /**
            * 上次剩余金额
            */
        @TableField("CO_LastRemainMoney")
    private Float coLastremainmoney;

            /**
            * 本次剩余金额
            */
        @TableField("CO_TodayRemainMoney")
    private Float coTodayremainmoney;

            /**
            * 用户名
            */
        @TableField("CO_ConsumerName")
    private String coConsumername;


}
