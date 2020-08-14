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
    * 
    * </p>
*
* @author stuil
* @since 2020-08-13
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("deletedconsumer")
    public class DeletedconsumerEntity implements Serializable {

    private static final long serialVersionUID = 1L;

            @TableId(value = "DC_id", type = IdType.AUTO)
    private Integer dcId;

        @TableField("DC_ConsumerId")
    private Integer dcConsumerid;

        @TableField("DC_EmployeeId")
    private Integer dcEmployeeid;

        @TableField("DC_BackWaterCount")
    private Float dcBackwatercount;

        @TableField("DC_BackMoney")
    private Float dcBackmoney;

        @TableField("DC_CreateTime")
    private LocalDateTime dcCreatetime;

        @TableField("DC_UpdateTime")
    private LocalDateTime dcUpdatetime;

        @TableField("DC_Remark")
    private String dcRemark;

        @TableField("DC_BackWaterfee")
    private Float dcBackwaterfee;

        @TableField("DC_BackSurcharge")
    private Float dcBacksurcharge;

        @TableField("DC_ThisRemainMoney")
    private Float dcThisremainmoney;


}
