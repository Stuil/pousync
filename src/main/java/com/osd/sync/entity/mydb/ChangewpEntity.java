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
    @TableName("changewp")
    public class ChangewpEntity implements Serializable {

    private static final long serialVersionUID = 1L;

            @TableId(value = "CW_id", type = IdType.AUTO)
    private Integer cwId;

        @TableField("CW_ConsumerId")
    private Integer cwConsumerid;

        @TableField("CW_OldWP_id")
    private Integer cwOldwpId;

        @TableField("CW_NewWP_id")
    private Integer cwNewwpId;

        @TableField("CW_CreateTime")
    private LocalDateTime cwCreatetime;

        @TableField("CW_Remark")
    private String cwRemark;

        @TableField("CW_EmployeeId")
    private Integer cwEmployeeid;


}
