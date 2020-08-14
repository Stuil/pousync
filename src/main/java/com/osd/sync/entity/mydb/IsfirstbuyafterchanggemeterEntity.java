package com.osd.sync.entity.mydb;

    import com.baomidou.mybatisplus.annotation.TableName;
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
    @TableName("isfirstbuyafterchanggemeter")
    public class IsfirstbuyafterchanggemeterEntity implements Serializable {

    private static final long serialVersionUID = 1L;

            @TableId("FBACM_id")
    private Integer fbacmId;

        @TableField("FBACM_isValid")
    private Integer fbacmIsvalid;

        @TableField("FBACM_OPTime")
    private LocalDateTime fbacmOptime;


}
