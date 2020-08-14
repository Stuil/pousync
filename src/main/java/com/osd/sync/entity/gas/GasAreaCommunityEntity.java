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
@TableName("gas_area_community")
public class GasAreaCommunityEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 名称
     */
    private String address;

    /**
     * 关联旧关键字
     */
    @TableField("oldKeywords")
    private String oldKeywords;

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
     * 地区code
     */
    @TableField("areaCode")
    private String areaCode;

    /**
     * 经度
     */
    private String lng;

    /**
     * 纬度
     */
    private String lat;


}
