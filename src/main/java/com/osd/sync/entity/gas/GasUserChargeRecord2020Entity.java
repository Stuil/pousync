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
* @since 2020-08-16
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("gas_user_charge_record_2020")
    public class GasUserChargeRecord2020Entity implements Serializable {

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
            * 实收金额
            */
        @TableField("paidAmount")
    private Long paidAmount;

            /**
            * 找零金额
            */
        @TableField("changeMoney")
    private Long changeMoney;

            /**
            * 欠费金额
            */
    private Long arrears;

            /**
            * 余额
            */
    private Long balance;

            /**
            * 用户新余额
            */
        @TableField("userNewMoney")
    private Long userNewMoney;

            /**
            * 用户新欠费
            */
        @TableField("userNewArrears")
    private Long userNewArrears;

            /**
            * 已付违约金
            */
        @TableField("paidLateFee")
    private Long paidLateFee;

            /**
            * 减免违约金
            */
        @TableField("reduceLateFee")
    private Long reduceLateFee;

            /**
            * 缴费减免违约金
            */
        @TableField("rechargeReduceLateFee")
    private Long rechargeReduceLateFee;

            /**
            * 违约金减免备注
            */
        @TableField("reduceLateFeeNote")
    private String reduceLateFeeNote;

            /**
            * 找零预存
            */
        @TableField("preStored")
    private Long preStored;

            /**
            * 收费方式
            */
        @TableField("payMethod")
    private Integer payMethod;

            /**
            * 来源
            */
        @TableField("paySource")
    private Integer paySource;

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
            * 购气气量
            */
        @TableField("buyGas")
    private BigDecimal buyGas;

            /**
            * 购气金额
            */
        @TableField("buyGasAmount")
    private Long buyGasAmount;

            /**
            * 购气次数
            */
        @TableField("buyGasTimes")
    private Integer buyGasTimes;

            /**
            * 累计购气量
            */
        @TableField("totalBuyGas")
    private BigDecimal totalBuyGas;

            /**
            * 累计购气金额
            */
        @TableField("totalBuyGasAmount")
    private Long totalBuyGasAmount;

            /**
            * 费用计算明细
            */
        @TableField("feeDetail")
    private String feeDetail;

            /**
            * 是否已退气
            */
        @TableField("refundGas")
    private Boolean refundGas;

            /**
            * 支付状态
            */
        @TableField("payStatus")
    private Integer payStatus;

            /**
            * 支付时间
            */
        @TableField("payTime")
    private Long payTime;

            /**
            * 第三方支付账号
            */
        @TableField("payAccount")
    private String payAccount;

            /**
            * 第三方流水号
            */
        @TableField("paySerialNo")
    private String paySerialNo;

            /**
            * 状态
            */
    private Integer status;

            /**
            * 撤销原因
            */
        @TableField("revokeReason")
    private String revokeReason;

            /**
            * 撤销附件
            */
        @TableField("revokeImage")
    private String revokeImage;

            /**
            * 撤销人
            */
        @TableField("revokeBy")
    private String revokeBy;

            /**
            * 撤销人名
            */
        @TableField("revokeByName")
    private String revokeByName;

            /**
            * 撤销时间
            */
        @TableField("revokeTime")
    private Long revokeTime;

            /**
            * 扎帐状态
            */
        @TableField("settleStatus")
    private Integer settleStatus;

            /**
            * 扎帐时间
            */
        @TableField("settleTime")
    private Long settleTime;

            /**
            * 扎帐人
            */
        @TableField("settleBy")
    private String settleBy;

            /**
            * 扎帐人名
            */
        @TableField("settleByName")
    private String settleByName;

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
            * 仅缴燃气费不缴违约金
            */
        @TableField("onlyPayGasFee")
    private Boolean onlyPayGasFee;

            /**
            * POS设备编号
            */
        @TableField("devCode")
    private String devCode;

            /**
            * 缴费备注
            */
    private String note;

            /**
            * 充值指令状态
            */
        @TableField("commandStatus")
    private Integer commandStatus;

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
            * 写卡失败
            */
        @TableField("writeICFail")
    private Boolean writeICFail;


}
