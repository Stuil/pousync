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
    @TableName("gas_change_meter_record")
    public class GasChangeMeterRecordEntity implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * ID
            */
    private String id;

            /**
            * 户号
            */
        @TableField("accountNumber")
    private String accountNumber;

            /**
            * 旧表id
            */
        @TableField("oldMeterId")
    private String oldMeterId;

            /**
            * 换表性质
            */
        @TableField("changeType")
    private Integer changeType;

            /**
            * 原表号
            */
        @TableField("oldMeterNo")
    private String oldMeterNo;

            /**
            * 新表号
            */
        @TableField("newMeterNo")
    private String newMeterNo;

            /**
            * 旧表上次指针
            */
        @TableField("oldLastPointer")
    private BigDecimal oldLastPointer;

            /**
            * 旧表本次指针
            */
        @TableField("oldPointer")
    private BigDecimal oldPointer;

            /**
            * 旧表用气量
            */
        @TableField("oldDosage")
    private BigDecimal oldDosage;

            /**
            * 补加补减量
            */
        @TableField("addGas")
    private BigDecimal addGas;

            /**
            * 旧表起始指针
            */
        @TableField("oldStartPointer")
    private BigDecimal oldStartPointer;

            /**
            * 新表起始指针
            */
        @TableField("newStartPointer")
    private BigDecimal newStartPointer;

            /**
            * 换表时间
            */
        @TableField("changeTime")
    private Long changeTime;

            /**
            * 换表人
            */
        @TableField("changeBy")
    private String changeBy;

            /**
            * 换表原因
            */
        @TableField("changeReason")
    private String changeReason;

            /**
            * 厂家
            */
    private String supplier;

            /**
            * 表型
            */
        @TableField("meterModel")
    private String meterModel;

            /**
            * 位数
            */
    private Integer digit;

            /**
            * 旧厂家
            */
        @TableField("oldSupplier")
    private String oldSupplier;

            /**
            * 旧表型
            */
        @TableField("oldMeterModel")
    private String oldMeterModel;

            /**
            * 旧位数
            */
        @TableField("oldDigit")
    private Integer oldDigit;

            /**
            * 旧防盗卡环
            */
        @TableField("oldAntiTheftRing")
    private String oldAntiTheftRing;

            /**
            * 新防盗卡环
            */
        @TableField("newAntiTheftRing")
    private String newAntiTheftRing;

            /**
            * 是否已取消
            */
    private Boolean cancelled;

            /**
            * 取消时间
            */
        @TableField("cancelTime")
    private Long cancelTime;

            /**
            * 取消人id
            */
        @TableField("cancelBy")
    private String cancelBy;

            /**
            * 取消人名
            */
        @TableField("cancelByName")
    private String cancelByName;

            /**
            * 操作人
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
            * 换表时间
            */
        @TableField("changeAt")
    private Long changeAt;

            /**
            * 换表类型
            */
    private Integer type;


}
