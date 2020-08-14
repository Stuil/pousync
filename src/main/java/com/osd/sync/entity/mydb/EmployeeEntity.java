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
    @TableName("employee")
    public class EmployeeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

            @TableId(value = "employee_id", type = IdType.AUTO)
    private Integer employeeId;

        @TableField("Ep_PWD")
    private String epPwd;

        @TableField("Ep_Name")
    private String epName;

        @TableField("Ep_Tel")
    private String epTel;

        @TableField("Ep_Addr")
    private String epAddr;

        @TableField("Ep_IsAvaiable")
    private Integer epIsavaiable;

        @TableField("Ep_Remark")
    private String epRemark;

        @TableField("Ep_Addemployeeid")
    private Integer epAddemployeeid;

        @TableField("Ep_CreateTime")
    private LocalDateTime epCreatetime;

        @TableField("Ep_UpdateTime")
    private LocalDateTime epUpdatetime;

        @TableField("Ep_Res1")
    private String epRes1;

        @TableField("Ep_Res2")
    private String epRes2;

        @TableField("Ep_Res3")
    private String epRes3;

        @TableField("Ep_IsAllAuthority")
    private Integer epIsallauthority;

        @TableField("Ep_IsInitset")
    private Integer epIsinitset;

        @TableField("Ep_IsSyset")
    private Integer epIssyset;

        @TableField("Ep_IsEmpset")
    private Integer epIsempset;

        @TableField("Ep_IsRecordInf")
    private Integer epIsrecordinf;

        @TableField("Ep_IsManageCommunity")
    private Integer epIsmanagecommunity;

        @TableField("Ep_IsManageConsumer")
    private Integer epIsmanageconsumer;

        @TableField("Ep_IsManageWatmeter")
    private Integer epIsmanagewatmeter;

        @TableField("Ep_IsManageWatprice")
    private Integer epIsmanagewatprice;

        @TableField("Ep_IsManageSurcharge")
    private Integer epIsmanagesurcharge;

        @TableField("Ep_IsBusCharge")
    private Integer epIsbuscharge;

        @TableField("Ep_IsCardOperator")
    private Integer epIscardoperator;

        @TableField("Ep_IsOpenAccount")
    private Integer epIsopenaccount;

        @TableField("Ep_IsBuyWater")
    private Integer epIsbuywater;

        @TableField("Ep_IsRelatedBus")
    private Integer epIsrelatedbus;

        @TableField("Ep_IsIntegQuery")
    private Integer epIsintegquery;

        @TableField("Ep_IsConsumerQuery")
    private Integer epIsconsumerquery;

        @TableField("Ep_IsBuyRecordQuery")
    private Integer epIsbuyrecordquery;

        @TableField("Ep_IsIntegInfQuery")
    private Integer epIsinteginfquery;

        @TableField("Ep_IsDateRpeortQuery")
    private Integer epIsdaterpeortquery;

        @TableField("Ep_IsEquipOverhaul")
    private Integer epIsequipoverhaul;

        @TableField("Ep_CommAuthority")
    private String epCommauthority;

        @TableField("Ep_canFreeWater")
    private Integer epCanfreewater;

        @TableField("Ep_PrintPreview")
    private Integer epPrintpreview;

        @TableField("Ep_W_StandCard")
    private Integer epWStandcard;

        @TableField("Ep_W_ParaCard")
    private Integer epWParacard;

        @TableField("Ep_W_MeterCodeCard")
    private Integer epWMetercodecard;

        @TableField("Ep_W_FaultClearCard")
    private Integer epWFaultclearcard;

        @TableField("Ep_W_ClearCard")
    private Integer epWClearcard;

        @TableField("Ep_W_MeterChangeCard")
    private Integer epWMeterchangecard;

        @TableField("Ep_W_BackWaterCard")
    private Integer epWBackwatercard;

        @TableField("Ep_W_TestCard")
    private Integer epWTestcard;

        @TableField("Ep_W_WaterCheckCard")
    private Integer epWWatercheckcard;

        @TableField("Ep_W_TimeCheckCard")
    private Integer epWTimecheckcard;

        @TableField("Ep_W_TmpChargeCard")
    private Integer epWTmpchargecard;

        @TableField("Ep_W_ValveCard")
    private Integer epWValvecard;

        @TableField("Ep_W_SuperValveCard")
    private Integer epWSupervalvecard;

        @TableField("Ep_GPrintInvoice")
    private Integer epGprintinvoice;

        @TableField("Ep_IsWriteCard")
    private Integer epIswritecard;

        @TableField("Ep_IsReadCard")
    private Integer epIsreadcard;

        @TableField("Ep_IsMakeupCard")
    private Integer epIsmakeupcard;

        @TableField("Ep_IsUserControl")
    private Integer epIsusercontrol;

        @TableField("Ep_IsTransferUser")
    private Integer epIstransferuser;

        @TableField("Ep_IsBlacklistQuery")
    private Integer epIsblacklistquery;

        @TableField("Ep_IsDeleteUser")
    private Integer epIsdeleteuser;

        @TableField("Ep_IsPrintInvoice")
    private Integer epIsprintinvoice;

        @TableField("Ep_IsBackWater")
    private Integer epIsbackwater;

        @TableField("Ep_IsUserScan")
    private Integer epIsuserscan;

        @TableField("Ep_IsInvoiceDesign")
    private Integer epIsinvoicedesign;

        @TableField("Ep_IsOpenQuery")
    private Integer epIsopenquery;


}
