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
    * 补卡记录表
    * </p>
*
* @author stuil
* @since 2020-08-14
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("makeupcard")
    public class MakeupcardEntity implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * id
            */
            @TableId(value = "MUC_id", type = IdType.AUTO)
    private Integer mucId;

            /**
            * 操作类型
            */
        @TableField("MUC_OPType")
    private Integer mucOptype;

            /**
            * 刷卡标识
            */
        @TableField("MUC_SwipFlag")
    private Integer mucSwipflag;

            /**
            * 卡片价格
            */
        @TableField("MUC_CardPrice")
    private Float mucCardprice;

            /**
            * 是否换表
            */
        @TableField("MUC_IsChangeMeter")
    private Integer mucIschangemeter;

            /**
            * 用户id
            */
        @TableField("MUC_ConsumerId")
    private Integer mucConsumerid;

            /**
            * 员工id
            */
        @TableField("MUC_EmployeeId")
    private Integer mucEmployeeid;

            /**
            * 卡上剩余气量
            */
        @TableField("MUC_RemainWater")
    private String mucRemainwater;

            /**
            * 备注
            */
        @TableField("MUC_Remark")
    private String mucRemark;

            /**
            * 创建时间
            */
        @TableField("MUC_CreateTime")
    private LocalDateTime mucCreatetime;

            /**
            * 更新时间
            */
        @TableField("MUC_UpdateTime")
    private LocalDateTime mucUpdatetime;

            /**
            * 备用字段1
            */
        @TableField("MUC_Res1")
    private String mucRes1;

            /**
            * 备用字段2
            */
        @TableField("MUC_Res2")
    private String mucRes2;

            /**
            * 备用字段3
            */
        @TableField("MUC_Res3")
    private String mucRes3;


}
