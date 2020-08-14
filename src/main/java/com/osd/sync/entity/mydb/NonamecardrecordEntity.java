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
    @TableName("nonamecardrecord")
    public class NonamecardrecordEntity implements Serializable {

    private static final long serialVersionUID = 1L;

            @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

        @TableField("NNC_type")
    private String nncType;

        @TableField("NNC_watercount")
    private Integer nncWatercount;

        @TableField("NNC_comment")
    private String nncComment;

        @TableField("NNC_employeeId")
    private Integer nncEmployeeid;

        @TableField("NNC_CreateTime")
    private LocalDateTime nncCreatetime;


}
