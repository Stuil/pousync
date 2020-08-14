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
    @TableName("gas_refund_gas")
    public class GasRefundGasEntity implements Serializable {

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
            * 退气卡气量
            */
        @TableField("refundGas")
    private BigDecimal refundGas;

            /**
            * 退气卡金额
            */
        @TableField("refundGasMoney")
    private Long refundGasMoney;

            /**
            * 应退金额
            */
        @TableField("refundMoney")
    private Long refundMoney;

            /**
            * 计量方式
            */
        @TableField("chargeType")
    private Integer chargeType;

            /**
            * 购气记录ID
            */
        @TableField("chargeRecordId")
    private String chargeRecordId;

            /**
            * 状态
            */
    private Integer status;

            /**
            * 退气时间
            */
        @TableField("creatAt")
    private Long creatAt;

            /**
            * 退气原因
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
