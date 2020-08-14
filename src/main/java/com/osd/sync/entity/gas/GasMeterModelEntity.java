package com.osd.sync.entity.gas;

    import java.math.BigDecimal;
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
    @TableName("gas_meter_model")
    public class GasMeterModelEntity implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * ID
            */
    private String id;

            /**
            * 生产厂家
            */
        @TableField("productFactory")
    private String productFactory;

            /**
            * 型号名称
            */
    private String name;

            /**
            * 型号规格
            */
    private String specification;

            /**
            * 最大流量
            */
        @TableField("maxFlow")
    private BigDecimal maxFlow;

            /**
            * 最小流量
            */
        @TableField("minFlow")
    private BigDecimal minFlow;

            /**
            * 最大压力
            */
        @TableField("maxPressure")
    private BigDecimal maxPressure;

            /**
            * 字轮最大值
            */
        @TableField("wheelMaximum")
    private BigDecimal wheelMaximum;

            /**
            * 回转体积
            */
        @TableField("rotaryVolume")
    private BigDecimal rotaryVolume;

            /**
            * 气表类型型号（ic卡型号）
            */
        @TableField("ammeterSort")
    private String ammeterSort;

            /**
            * 是否大表
            */
        @TableField("bigMeter")
    private Boolean bigMeter;

            /**
            * 表最大允许存储气量
            */
        @TableField("ammeterMax")
    private Integer ammeterMax;

            /**
            * 报警气量
            */
        @TableField("warnGas")
    private Integer warnGas;

            /**
            * 是否启用
            */
    private Boolean enabled;

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
            * 表位数
            */
    private Integer digit;

            /**
            * 用户类型
            */
        @TableField("userType")
    private Integer userType;

            /**
            * 计量方式
            */
        @TableField("chargeType")
    private Integer chargeType;

            /**
            * 报警气量
            */
        @TableField("alarmValue")
    private Integer alarmValue;

            /**
            * 购气上限量
            */
        @TableField("inputMaxValue")
    private Integer inputMaxValue;

            /**
            * 气表类型
            */
        @TableField("meterType")
    private String meterType;


}
