package com.osd.sync.entity.gas;

    import com.baomidou.mybatisplus.annotation.TableName;
    import com.baomidou.mybatisplus.annotation.TableField;
    import java.io.Serializable;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 
    * </p>
*
* @author stuil
* @since 2020-08-13
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("gas_meter")
    public class GasMeterEntity implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * ID
            */
    private String id;

            /**
            * 表具编号/气表编号
            */
        @TableField("meterNo")
    private String meterNo;

            /**
            * 厂家
            */
    private String supplier;

            /**
            * 表型号
            */
        @TableField("modelId")
    private String modelId;

            /**
            * 表最大指数
            */
        @TableField("maxPointer")
    private Integer maxPointer;

            /**
            * 表位数
            */
    private Integer digit;

            /**
            * 防盗卡环
            */
        @TableField("antiTheftRing")
    private String antiTheftRing;

            /**
            * 安装日期
            */
        @TableField("installTime")
    private Long installTime;

            /**
            * 是否开卡
            */
        @TableField("openCard")
    private Integer openCard;

            /**
            * 办卡日期
            */
        @TableField("openCardTime")
    private Long openCardTime;

            /**
            * 卡更新号
            */
        @TableField("cardNewNo")
    private String cardNewNo;

            /**
            * 操作人姓名
            */
        @TableField("opByName")
    private String opByName;

            /**
            * 操作人
            */
        @TableField("opBy")
    private String opBy;

            /**
            * 操作时间
            */
        @TableField("opAt")
    private Long opAt;

            /**
            * 删除标记
            */
        @TableField("delFlag")
    private Boolean delFlag;

            /**
            * 卡更新日期
            */
        @TableField("lastCardUpdateTime")
    private Long lastCardUpdateTime;

            /**
            * 卡号
            */
        @TableField("cardNo")
    private String cardNo;

            /**
            * 购气次数
            */
        @TableField("buyGasCount")
    private Integer buyGasCount;

            /**
            * 补卡次数
            */
        @TableField("mendCardCount")
    private Integer mendCardCount;

            /**
            * 设备编号
            */
        @TableField("deviceNumber")
    private String deviceNumber;

            /**
            * 设备型号
            */
        @TableField("deviceModel")
    private String deviceModel;

            /**
            * 起始读数
            */
        @TableField("initialReading")
    private String initialReading;

            /**
            * 注册信息
            */
    private String register;

            /**
            * 区域代码
            */
        @TableField("areaCode")
    private String areaCode;


}
