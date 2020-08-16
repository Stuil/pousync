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
    @TableName("gas_use_type")
    public class GasUseTypeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 编码
            */
    private String code;

            /**
            * 用气类型名称
            */
    private String name;

            /**
            * 燃气类型
            */
        @TableField("gasType")
    private String gasType;

            /**
            * 最小储气量
            */
        @TableField("minGasStorage")
    private BigDecimal minGasStorage;

            /**
            * 最大储气量
            */
        @TableField("maxGasStorage")
    private BigDecimal maxGasStorage;

            /**
            * IC最大储气量
            */
        @TableField("maxGasStorageIC")
    private BigDecimal maxGasStorageIC;

            /**
            * 报警气量
            */
        @TableField("warnGas")
    private BigDecimal warnGas;

            /**
            * 是否启用
            */
    private Boolean enabled;

            /**
            * 备注
            */
    private String note;

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
            * 是否公用户
            */
        @TableField("publicUser")
    private Boolean publicUser;


}
