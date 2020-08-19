package com.osd.sync.entity.gas;

    import java.math.BigDecimal;
    import com.baomidou.mybatisplus.annotation.TableName;
    import com.baomidou.mybatisplus.annotation.TableId;
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
    @TableName("gas_user")
    public class GasUserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 用户账号
            */
            @TableId("accountNumber")
    private String accountNumber;

            /**
            * 系统编号
            */
        @TableField("sysNumber")
    private String sysNumber;

            /**
            * 老系统用户账号
            */
        @TableField("oldAccountNumber")
    private String oldAccountNumber;

            /**
            * 小区
            */
    private String community;

            /**
            * 本号
            */
        @TableField("bookNo")
    private String bookNo;

            /**
            * 户号
            */
        @TableField("accountNo")
    private Integer accountNo;

            /**
            * 用户名
            */
        @TableField("accountName")
    private String accountName;

            /**
            * 用气类型
            */
        @TableField("useType")
    private String useType;

            /**
            * 手机号
            */
    private String mobile;

            /**
            * 身份证号
            */
        @TableField("idCard")
    private String idCard;

            /**
            * 人口数
            */
    private Integer population;

            /**
            * 地址
            */
    private String address;

            /**
            * 用气设备
            */
        @TableField("gasEquipment")
    private String gasEquipment;

            /**
            * 特殊用户类型
            */
        @TableField("specialUserType")
    private Integer specialUserType;

            /**
            * 用户类型
            */
        @TableField("userType")
    private Integer userType;

            /**
            * 表id
            */
        @TableField("meterId")
    private String meterId;

            /**
            * 开户时间
            */
        @TableField("openTime")
    private Long openTime;

            /**
            * 保险到期时间
            */
        @TableField("insuranceExpireTime")
    private Long insuranceExpireTime;

            /**
            * 补加补减量
            */
        @TableField("addGas")
    private BigDecimal addGas;

            /**
            * 是否公用户
            */
  /*  private Boolean publicUser;*/

            /**
            * 用户状态
            */
    private Integer status;

            /**
            * 是否可删除
            */
        @TableField("canDelete")
    private Boolean canDelete;

            /**
            * 操作人名
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
            * 上次抄表指数
            */
        @TableField("lastTimeMeterReadingIndex")
    private BigDecimal lastTimeMeterReadingIndex;

            /**
            * 上次抄表时间
            */
        @TableField("lastTimeMeterReading")
    private Long lastTimeMeterReading;

            /**
            * 账户余额,单位:厘
            */
    private Long balance;

            /**
            * 欠费金额,单位:厘
            */
    private Long arrears;

            /**
            * 滞纳金,单位:厘
            */
        @TableField("lateFee")
    private Long lateFee;

            /**
            * 是否有电热带
            */
        @TableField("heatingBand")
    private Integer heatingBand;

            /**
            * 上次用量
            */
        @TableField("lastUse")
    private BigDecimal lastUse;

            /**
            * 是否是批量开户
            */
        @TableField("batchOpen")
    private Integer batchOpen;

            /**
            * IC卡表/NB物联网表上次充值时间
            */
        @TableField("lastRechargeTime")
    private Long lastRechargeTime;

            /**
            * IC卡表/NB物联网表上次充值金额,单位:厘
            */
        @TableField("lastRechargeMoney")
    private Long lastRechargeMoney;

            /**
            * 调压阀开关
            */
        @TableField("regulatingValve")
    private Integer regulatingValve;

            /**
            * 上次欠费时间
            */
        @TableField("lastArrearsTime")
    private Integer lastArrearsTime;

            /**
            * 是否开卡
            */
        @TableField("openCard")
    private Integer openCard;

            /**
            * 总购气量
            */
        @TableField("totalDosage")
    private BigDecimal totalDosage;

            /**
            * 总购金额
            */
        @TableField("totalBuyGasMoney")
    private Long totalBuyGasMoney;

            /**
            * 收费模式
            */
        @TableField("chargeMode")
    private Integer chargeMode;

            /**
            * 报警金额
            */
        @TableField("alarmAmount")
    private Long alarmAmount;


}
