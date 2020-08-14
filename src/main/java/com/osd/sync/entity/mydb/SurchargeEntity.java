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
    @TableName("surcharge")
    public class SurchargeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

            @TableId(value = "SC_id", type = IdType.AUTO)
    private Integer scId;

        @TableField("SC_SerialNum")
    private String scSerialnum;

        @TableField("SC_Name")
    private String scName;

        @TableField("SC_Price")
    private Float scPrice;

        @TableField("SC_Remark")
    private String scRemark;

        @TableField("SC_CreateTime")
    private LocalDateTime scCreatetime;

        @TableField("SC_UpdateTime")
    private LocalDateTime scUpdatetime;

        @TableField("SC_Res1")
    private String scRes1;

        @TableField("SC_Res2")
    private String scRes2;

        @TableField("SC_Res3")
    private String scRes3;


}
