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
    @TableName("gas_mend_gas")
    public class GasMendGasEntity implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * ID
            */
    private String id;

            /**
            * 用户账号
            */
        @TableField("accountNumber")
    private String accountNumber;

            /**
            * 用户表ID
            */
        @TableField("meterId")
    private String meterId;

            /**
            * 用气类型
            */
        @TableField("useType")
    private String useType;

            /**
            * 用户类型
            */
        @TableField("userType")
    private Integer userType;

            /**
            * 补气气量
            */
        @TableField("mendGas")
    private BigDecimal mendGas;

            /**
            * 补气金额
            */
        @TableField("mendGasAmount")
    private Long mendGasAmount;

            /**
            * 成本金额
            */
        @TableField("costMoney")
    private Long costMoney;

            /**
            * 购气次数
            */
        @TableField("buyGasTimes")
    private Integer buyGasTimes;

            /**
            * 计量方式
            */
        @TableField("chargeType")
    private Integer chargeType;

            /**
            * 费用计算明细
            */
        @TableField("feeDetail")
    private String feeDetail;

            /**
            * 补气时间
            */
        @TableField("creatAt")
    private Long creatAt;

            /**
            * 状态
            */
    private Integer status;

            /**
            * 补气原因
            */
    private String reason;

            /**
            * 操作员姓名
            */
        @TableField("opByName")
    private String opByName;

            /**
            * 操作员编号
            */
        @TableField("opByNo")
    private String opByNo;

            /**
            * 所属营业厅编号
            */
        @TableField("serviceHallId")
    private String serviceHallId;

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


}
