package com.osd.sync.entity.gas;

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
    @TableName("gas_book_no")
    public class GasBookNoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * id
            */
    private Integer id;

            /**
            * 地址
            */
    private String address;

            /**
            * 所属小区
            */
        @TableField("communityId")
    private String communityId;

            /**
            * 是否已分配
            */
    private Boolean distributed;

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
            * 用户数
            */
        @TableField("userCount")
    private Integer userCount;


}
