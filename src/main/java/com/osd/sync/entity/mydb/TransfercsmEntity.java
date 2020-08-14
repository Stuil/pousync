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
    * 用户过户记录表
    * </p>
*
* @author stuil
* @since 2020-08-13
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("transfercsm")
    public class TransfercsmEntity implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * id
            */
            @TableId(value = "TC_id", type = IdType.AUTO)
    private Integer tcId;

            /**
            * 用户id
            */
        @TableField("TC_CSM_id")
    private Integer tcCsmId;

            /**
            * 用户旧名称
            */
        @TableField("TC_CSM_OldName")
    private String tcCsmOldname;

            /**
            * 用户新名称
            */
        @TableField("TC_CSM_NewName")
    private String tcCsmNewname;

            /**
            * 开户时间
            */
        @TableField("TC_CSM_OpenedDate")
    private LocalDateTime tcCsmOpeneddate;

            /**
            * 过户时间
            */
        @TableField("TC_CSM_TransferDate")
    private LocalDateTime tcCsmTransferdate;

            /**
            * 员工ID
            */
        @TableField("TC_EmployeeId")
    private Integer tcEmployeeid;

            /**
            * 备注
            */
        @TableField("TC_Remark")
    private String tcRemark;

            /**
            * 地址
            */
        @TableField("TC_CSM_Addr")
    private String tcCsmAddr;


}
