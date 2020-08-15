package com.osd.sync.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.osd.sync.entity.gas.*;
import com.osd.sync.entity.mydb.CardoprecordEntity;
import com.osd.sync.entity.mydb.ConsumerEntity;
import com.osd.sync.entity.mydb.MakeupcardEntity;
import com.osd.sync.entity.mydb.UsercardinfoEntity;
import com.osd.sync.model.UserCardModel;
import com.osd.sync.service.UserCardService;
import com.osd.sync.service.gas.*;
import com.osd.sync.service.mydb.*;
import com.osd.sync.utils.MoneyUtil;
import freemarker.template.utility.NumberUtil;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.nutz.lang.Lang;
import org.nutz.lang.Times;
import org.nutz.lang.util.NutMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @title: IndexController
 * @description: hh
 * @date: 2020/8/13
 * @author: zwh
 * @copyright: Copyright (c) 2020
 * @version: 1.0
 */
@RestController
@Slf4j
public class IndexController {

    static final String PATH_FORMAT1="yyyy-MM-dd HH:mm:ss";
    static final String PATH_FORMAT2="yyyyMMddHHmmss";

    @Autowired
    GasAreaCommunityService gasAreaCommunityService;
    @Autowired
    ConsumerService consumerService;
    @Autowired
    GasBookNoService gasBookNoService;
    @Autowired
    UserCardService userCardService;
    @Autowired
    CommunityService communityService;
    @Autowired
    UsercardinfoService usercardinfoService;
    @Autowired
    CardoprecordService cardoprecordService;
    @Autowired
    MakeupcardService makeupcardService;
    @Autowired
    GasUserService gasUserService;
    @Autowired
    GasMendGasService gasMendGasService;
    @Autowired
    GasMeterService gasMeterService;
    @Autowired
    GasUserChargeRecordService gasUserChargeRecordService;
    @Autowired
    GasRefundGasService gasRefundGasService;

    @RequestMapping("/index2")
    public String index2(){
        List<CardoprecordEntity> userCardModels = cardoprecordService.list();
        return String.valueOf(userCardModels.get(0).getCoCreatetime()).replace("T"," ");
    }

    public void index(){
        // 系统内部小区
        Map<String, Object> gasComm = gasAreaCommunityService.getListAreaToMap();
        // 金凤小区
        Map<Integer, String> jfComm = communityService.getOneCommunity();
        // 获取金凤库中人员信息
        List<ConsumerEntity> consumerEntities=consumerService.list();
        consumerEntities.forEach(item->{
            UsercardinfoEntity cardInfo = usercardinfoService.getById(item.getCsmId());
            // 金凤库购气+补气次数
            /*List<CardoprecordEntity> buyJf = cardoprecordService.list(new QueryWrapper<CardoprecordEntity>()
                    .eq("CO_ConsumerId", item.getCsmId()).gt("CO_WaterCount", 0).gt("CO_Freewater", 0));*/
            // 购气、退气、补气全记录
            List<CardoprecordEntity> jfAcc = cardoprecordService.list(new QueryWrapper<CardoprecordEntity>()
                    .eq("CO_ConsumerId", item.getCsmId()).ne("CO_WaterCount", 0).gt("CO_Freewater", 0));
            // 金凤库充值记录
            List<CardoprecordEntity> cardCord = cardoprecordService.list(new QueryWrapper<CardoprecordEntity>()
                    .eq("CO_ConsumerId", item.getCsmId()).gt("CO_WaterCount", 0).orderByAsc("CO_id"));
            // 金凤补气记录
            List<CardoprecordEntity> mendCord = cardoprecordService.list(new QueryWrapper<CardoprecordEntity>()
                    .eq("CO_ConsumerId", item.getCsmId()).gt("CO_Freewater", 0).orderByAsc("CO_id"));
            // 退气记录
            List<CardoprecordEntity> reFundCord = cardoprecordService.list(new QueryWrapper<CardoprecordEntity>()
                    .eq("CO_ConsumerId", item.getCsmId()).lt("CO_WaterCount", 0).orderByAsc("CO_id"));
            // 金凤 库补卡
            int mendCount = makeupcardService.count(new QueryWrapper<MakeupcardEntity>()
                    .eq("MUC_ConsumerId", item.getCsmId()).eq("MUC_OPType", 3));
            // 上次充值时间金额
            long lastRecTime=0L;
            long lastRecMoney=0L;
            if(cardCord!=null && cardCord.size()>0){
                lastRecTime=cardCord.get(cardCord.size()-1).getCoCreatetime().toEpochSecond(ZoneOffset.of("+8"));
                lastRecMoney=MoneyUtil.yuanToLi(String.valueOf(cardCord.get(cardCord.size()-1).getCoTotalnormalfee()));
            }
            // fixme 累计购气量  购气+退气
            int buyGasJfSum = cardCord.stream().mapToInt(CardoprecordEntity::getCoWatercount).sum();
            int fefundJfSum= reFundCord.stream().mapToInt(CardoprecordEntity::getCoWatercount).sum();
            int jfSumCount=buyGasJfSum+fefundJfSum;
            // fixme 累计购气金额 购气+退气
            long buyMoney=MoneyUtil.yuanToLi(String.valueOf(cardCord.stream().mapToDouble(CardoprecordEntity::getCoTotalnormalfee).sum()));
            long refundMoney=MoneyUtil.yuanToLi(String.valueOf(reFundCord.stream().mapToDouble(CardoprecordEntity::getCoTotalnormalfee).sum()));
            long jfSumMoney=buyMoney+refundMoney;
            // 购气次数
            /*int jfCount=0;
            if(buyJf!=null){
                int refund=reFundCord==null?0:reFundCord.size();
                jfCount=buyJf.size()-refund;
            }*/
            GasUserEntity gasUser=new GasUserEntity();
            gasUser.setAccountNumber("JF"+item.getCsmId());
            gasUser.setSysNumber("03");
            gasUser.setOldAccountNumber("");
            // 小区名称
            if(keyAndValue(jfComm.get(item.getCsmId()),gasComm)){
                gasUser.setCommunity(getKey(gasComm,jfComm.get(item.getCsmId())).get(0));
                Integer bookNo= Lang.isEmpty(gasBookNoService.getBooks(gasUser.getCommunity()))?1:gasBookNoService.getBooks(gasUser.getCommunity()).getId();
                gasUser.setBookNo(String.valueOf(bookNo));
            }
            gasUser.setAccountNo(0);//补户号
            gasUser.setAccountName(item.getCsmName());
            gasUser.setBalance(MoneyUtil.yuanToLi(String.valueOf(cardInfo.getCardTodayRemainMoney())));
            gasUser.setArrears(0L);
            gasUser.setLateFee(0L);
            gasUser.setUseType(cnbt_price_map.get(cardInfo.getWaterpriceWpId()));//用气类型
            gasUser.setMobile(item.getCsmTel1());
            gasUser.setAddress(item.getCsmAddr());
            gasUser.setUserType(1);
            gasUser.setMeterId(UUID.randomUUID().toString().replaceAll("-",""));
            gasUser.setOpenTime(item.getCsmOpentime().toEpochSecond(ZoneOffset.of("+8")));
            gasUser.setAddGas(BigDecimal.valueOf(0));
            // fixme 用户状态
            gasUser.setStatus(item.getCsmIsdeleted());
            gasUser.setCanDelete(false);
            gasUser.setHeatingBand(0);
            gasUser.setBatchOpen(0);
            gasUser.setLastRechargeTime(lastRecTime);//上次充值时间
            gasUser.setLastRechargeMoney(lastRecMoney);//上次充值金额
            gasUser.setOpenCard(item.getCsmIsopened());
            gasUser.setTotalDosage(BigDecimal.valueOf(jfSumCount));
            gasUser.setTotalBuyGasMoney(jfSumMoney);
            //    meter
            GasMeterEntity gasMeter = new GasMeterEntity();
            gasMeter.setId((gasUser.getMeterId()));
            gasMeter.setMeterNo("");
            gasMeter.setSupplier("JF");
            // fixme  平台创建
            gasMeter.setModelId("46f720fcb30c4b78a6e6fef19a04bc74");
            gasMeter.setDigit(0);
            gasMeter.setCardNo("JF"+cardInfo.getCardId());
            gasMeter.setInstallTime(cardInfo.getCardCreatetime().toEpochSecond(ZoneOffset.of("+8")));
            gasMeter.setOpenCard(item.getCsmIsopened());
            gasMeter.setOpenCardTime(item.getCsmOpentime().toEpochSecond(ZoneOffset.of("+8")));
            gasMeter.setBuyGasCount(cardInfo.getCardBuycount());
            gasMeter.setMendCardCount(mendCount);
            // 购气记录
            List<GasUserChargeRecordEntity> chargeRecordList = new ArrayList<>();
            List<GasMendGasEntity> mendGasList = new ArrayList<>();
            List<GasRefundGasEntity> refundGasList = new ArrayList<>();
           /* cardCord.forEach(buyGas->{
                GasUserChargeRecordEntity gasUserCharge=new GasUserChargeRecordEntity();
                gasUserCharge.setId("JF"+buyGas.getCoId());
                gasUserCharge.setAccountNumber(String.valueOf(buyGas.getCoConsumerid()));
                gasUserCharge.setMeterId(gasUser.getMeterId());
                gasUserCharge.setPaidAmount(MoneyUtil.yuanToLi(String.valueOf(buyGas.getCoTotalnormalfee())));
                gasUserCharge.setChangeMoney(0L);
                gasUserCharge.setArrears(0L);
                gasUserCharge.setBalance(MoneyUtil.yuanToLi(String.valueOf(buyGas.getCoLastremainmoney())));
                gasUserCharge.setUserNewMoney(MoneyUtil.yuanToLi(String.valueOf(buyGas.getCoTodayremainmoney())));
                gasUserCharge.setPreStored(MoneyUtil.yuanToLi(String.valueOf(buyGas.getCoTodayremainmoney())));
                gasUserCharge.setPayMethod(0);
                gasUserCharge.setPaySource(2);
                gasUserCharge.setUseType(gasUser.getUseType());
                gasUserCharge.setUserType(1);
                gasUserCharge.setBuyGas(BigDecimal.valueOf(buyGas.getCoWatercount()));
                gasUserCharge.setBuyGasAmount(MoneyUtil.yuanToLi(String.valueOf(buyGas.getCoTotalnormalfee())));
                // 购气次数
                gasUserCharge.setBuyGasTimes(Long.valueOf(gasMeter.getBuyGasCount()));
                gasUserCharge.setTotalBuyGas(BigDecimal.valueOf(buyGasJfSum));
                gasUserCharge.setTotalBuyGasAmount(buyMoney);
                // 费用计算明细
                long price = MoneyUtil.yuanToLi(String.valueOf(buyGas.getCoUnitnormalfee()));//单价
                NutMap feeDetailMap = NutMap.NEW().setv("ladder", false);
                List<NutMap> ladderConfig = new ArrayList<>();
                ladderConfig.add(NutMap.NEW().setv("price", price).setv("dosage", ""));
                ladderConfig.add(NutMap.NEW());
                ladderConfig.add(NutMap.NEW());
                ladderConfig.add(NutMap.NEW());
                ladderConfig.add(NutMap.NEW());
                feeDetailMap.setv("ladderConfig", ladderConfig);
                feeDetailMap.setv("gasValue", gasUserCharge.getBuyGas());
                List<NutMap> detail = new ArrayList<>();
                detail.add(NutMap.NEW().setv("price", price).setv("cur", 0).setv("dosage", gasUserCharge.getBuyGas()).setv("fee", gasUserCharge.getBuyGasAmount()));
                feeDetailMap.setv("detail", detail);
                feeDetailMap.setv("fee", gasUserCharge.getBuyGasAmount());
                gasUserCharge.setFeeDetail(JSON.toJSONString(feeDetailMap));
                gasUserCharge.setPayStatus(2);
                gasUserCharge.setPayTime(buyGas.getCoCreatetime().toEpochSecond(ZoneOffset.of("+8")));
                gasUserCharge.setStatus(0);
                gasUserCharge.setSettleStatus(1);
                gasUserCharge.setSettleTime(buyGas.getCoCreatetime().toEpochSecond(ZoneOffset.of("+8")));
                gasUserCharge.setSettleByName("系统");
                gasUserCharge.setOpByName("系统");
                gasUserCharge.setNote(buyGas.getCoRemark());
                chargeRecordList.add(gasUserCharge);
            });*/
            jfAcc.forEach(accGas->{
                // 购气、补气
                if(accGas.getCoOptype()==1){
                    // 购气
                    if(accGas.getCoWatercount()>0){
                        GasUserChargeRecordEntity gasUserCharge=new GasUserChargeRecordEntity();
                        // 流水号
                        gasUserCharge.setId(accNo(accGas.getCoCreatetime(),accGas.getCoId()));
                        gasUserCharge.setAccountNumber(String.valueOf(accGas.getCoConsumerid()));
                        gasUserCharge.setMeterId(gasUser.getMeterId());
                        gasUserCharge.setPaidAmount(MoneyUtil.yuanToLi(String.valueOf(accGas.getCoTotalnormalfee())));
                        gasUserCharge.setChangeMoney(0L);
                        gasUserCharge.setArrears(0L);
                        gasUserCharge.setBalance(MoneyUtil.yuanToLi(String.valueOf(accGas.getCoLastremainmoney())));
                        gasUserCharge.setUserNewMoney(MoneyUtil.yuanToLi(String.valueOf(accGas.getCoTodayremainmoney())));
                        gasUserCharge.setPreStored(MoneyUtil.yuanToLi(String.valueOf(accGas.getCoTodayremainmoney())));
                        gasUserCharge.setPayMethod(0);
                        gasUserCharge.setPaySource(2);
                        gasUserCharge.setUseType(gasUser.getUseType());
                        gasUserCharge.setUserType(1);
                        gasUserCharge.setBuyGas(BigDecimal.valueOf(accGas.getCoWatercount()));
                        gasUserCharge.setBuyGasAmount(MoneyUtil.yuanToLi(String.valueOf(accGas.getCoTotalnormalfee())));
                        // 购气次数
                        gasUserCharge.setBuyGasTimes(Long.valueOf(gasMeter.getBuyGasCount()));
                        gasUserCharge.setTotalBuyGas(BigDecimal.valueOf(buyGasJfSum));
                        gasUserCharge.setTotalBuyGasAmount(buyMoney);
                        // 费用计算明细
                        long price = MoneyUtil.yuanToLi(String.valueOf(accGas.getCoUnitnormalfee()));//单价
                        NutMap feeDetailMap = NutMap.NEW().setv("ladder", false);
                        List<NutMap> ladderConfig = new ArrayList<>();
                        ladderConfig.add(NutMap.NEW().setv("price", price).setv("dosage", ""));
                        ladderConfig.add(NutMap.NEW());
                        ladderConfig.add(NutMap.NEW());
                        ladderConfig.add(NutMap.NEW());
                        ladderConfig.add(NutMap.NEW());
                        feeDetailMap.setv("ladderConfig", ladderConfig);
                        feeDetailMap.setv("gasValue", gasUserCharge.getBuyGas());
                        List<NutMap> detail = new ArrayList<>();
                        detail.add(NutMap.NEW().setv("price", price).setv("cur", 0).setv("dosage", gasUserCharge.getBuyGas()).setv("fee", gasUserCharge.getBuyGasAmount()));
                        feeDetailMap.setv("detail", detail);
                        feeDetailMap.setv("fee", gasUserCharge.getBuyGasAmount());
                        gasUserCharge.setFeeDetail(JSON.toJSONString(feeDetailMap));
                        gasUserCharge.setPayStatus(2);
                        gasUserCharge.setPayTime(accGas.getCoCreatetime().toEpochSecond(ZoneOffset.of("+8")));
                        gasUserCharge.setStatus(0);
                        gasUserCharge.setSettleStatus(1);
                        gasUserCharge.setSettleTime(accGas.getCoCreatetime().toEpochSecond(ZoneOffset.of("+8")));
                        gasUserCharge.setSettleByName("系统");
                        gasUserCharge.setOpByName("系统");
                        gasUserCharge.setNote(accGas.getCoRemark());
                        chargeRecordList.add(gasUserCharge);
                    }
                    //补气
                    if(accGas.getCoFreewater()>0){
                        GasMendGasEntity gasMendGasEntity=new GasMendGasEntity();
                        gasMendGasEntity.setId(accNo(accGas.getCoCreatetime(),accGas.getCoId()));
                        gasMendGasEntity.setAccountNumber(gasUser.getAccountNumber());
                        gasMendGasEntity.setMeterId(gasUser.getMeterId());
                        gasMendGasEntity.setUseType(gasUser.getUseType());
                        gasMendGasEntity.setUserType(gasUser.getUserType());
                        gasMendGasEntity.setMendGas(BigDecimal.valueOf(accGas.getCoFreewater()));
                        gasMendGasEntity.setMendGasAmount(0L);
                        long price = MoneyUtil.yuanToLi(String.valueOf(accGas.getCoUnitnormalfee()));//单价
                        // fixme  成本价
                        gasMendGasEntity.setCostMoney(price*accGas.getCoFreewater());
                        gasMendGasEntity.setBuyGasTimes(gasMeter.getBuyGasCount());
                        // fixme 计量方式
                        gasMendGasEntity.setChargeType(0);
                        gasMendGasEntity.setFeeDetail("");
                        gasMendGasEntity.setCreatAt(accGas.getCoCreatetime().toEpochSecond(ZoneOffset.of("+8")));
                        // fixme 状态
                        gasMendGasEntity.setStatus(0);
                        gasMendGasEntity.setReason(accGas.getCoRemark());
                        gasMendGasEntity.setOpByName("系统");
                        mendGasList.add(gasMendGasEntity);
                    }
                }else if(accGas.getCoOptype()==2){
                    // 退气
                    GasRefundGasEntity gasRefundGasEntity=new GasRefundGasEntity();
                    gasRefundGasEntity.setId(accNo(accGas.getCoCreatetime(),accGas.getCoId()));
                    gasRefundGasEntity.setAccountNumber(gasUser.getAccountNumber());
                    gasRefundGasEntity.setMeterId(gasUser.getMeterId());
                    gasRefundGasEntity.setUseType(gasUser.getUseType());
                    gasRefundGasEntity.setUserType(gasUser.getUserType());
                    gasRefundGasEntity.setRefundGas(BigDecimal.valueOf(Math.abs(accGas.getCoWatercount())));
                    gasRefundGasEntity.setRefundGasMoney(0L);
                    gasRefundGasEntity.setRefundMoney(Math.abs(MoneyUtil.yuanToLi(String.valueOf(accGas.getCoTotalnormalfee()))));//气费金额
                    gasRefundGasEntity.setChargeType(0);
                    gasRefundGasEntity.setChargeRecordId("");
                    gasRefundGasEntity.setStatus(0);
                    gasRefundGasEntity.setCreatAt(accGas.getCoCreatetime().toEpochSecond(ZoneOffset.of("+8")));
                    gasRefundGasEntity.setReason(accGas.getCoRemark());
                    gasRefundGasEntity.setOpByName("系统");
                    refundGasList.add(gasRefundGasEntity);
                }

            });
            //  用户入库
            boolean user=gasUserService.insetUser(gasUser);
            if(!user){
                log.error("失败:{}",gasUser.getAccountNumber());
            }
            // 表具入库
            boolean meter=gasMeterService.insertMeter(gasMeter);
            if(!meter){
                log.error("表具：{}",gasMeter.getId());
            }
            // fixme 购气入库
            gasUserChargeRecordService.insertCharge(chargeRecordList);
            // 补气
            gasMendGasService.insertMendGas(mendGasList);
            // 退气
            gasRefundGasService.insetRefund(refundGasList);
        });
    }
    // 金凤 用气类型
    private static Map<Integer, String> cnbt_price_map = new HashMap() {
        {
            put(3, "F_M_0");//居民煤气0.8
            put(4, "F_M_1");//居民天然气2.06
            put(5,"F_G_4");// add  商业天然气 2.3
            put(6, "F_G_1");//公用煤气1.2
        }
    };
    // 金凤 单价
    private static Map<String, Long> jf_price_map = new HashMap() {
        {
            put("F_M_0", 800);//居民煤气0.8
            put("F_M_1",2060 );//居民天然气2.06
            put("F_G_4",2300);// add  商业天然气 2.3
            put("F_G_1", 1200);//公用煤气1.2
        }
    };
    // Map比较
    public boolean keyAndValue(String val,Map<String,Object> map2){
        return map2.containsValue(val);
    }
    // val -> key
    public static List<String> getKey(Map<String,Object> map, Object value){
        List<String> keyList = new ArrayList<>();
        for(String key: map.keySet()){
            if(map.get(key).equals(value)){
                keyList.add(key);
            }
        }
        return keyList;
    }
 // 时间
 private long parseDate(String dateStr) {
     long openTime = 0;
     try {
         Date openDate = Times.parse("yyyy-MM-dd HH:mm:ss", dateStr);
         openTime = openDate.getTime() / 1000;
     } catch (Exception e) {
         try {
             Date openDate = Times.D(dateStr.substring(0, 10));
             openTime = openDate.getTime() / 1000;
         } catch (Exception exception) {
             openTime = 1451606400L;//保底，交易记录中最早的一条都是2016，2016-01-01 08:00:00
         }
     }
     return openTime;
 }

     // 时间格式 转换
     public static String getTimeToString(String str)  {
         SimpleDateFormat dateFormat = new SimpleDateFormat(PATH_FORMAT1);
         SimpleDateFormat df = new SimpleDateFormat(PATH_FORMAT2);
         try {
             return df.format(dateFormat.parse(str));
         } catch (ParseException e) {
             return df.format(new Date());
         }
     }
    // 左补0
    public static String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        StringBuffer sb = null;
        while (strLen < strLength) {
            sb = new StringBuffer();
            sb.append("0").append(str);
            str = sb.toString();
            strLen = str.length();
        }
        return str;
    }
     // 购气、补气、退气单号
     public static String accNo(LocalDateTime localDateTime, Integer accId)  {
        String str=String.valueOf(localDateTime).replace("T"," ");
        return "JF"+getTimeToString(str)+addZeroForNum(String.valueOf(accId),5);
     }
}
