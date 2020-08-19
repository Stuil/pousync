package com.osd.sync.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.osd.sync.entity.gas.*;
import com.osd.sync.entity.mydb.*;
import com.osd.sync.service.gas.*;
import com.osd.sync.service.mydb.*;
import com.osd.sync.utils.MoneyUtil;
import com.osd.sync.utils.SyncUtil;
import lombok.extern.slf4j.Slf4j;
import org.nutz.lang.Lang;
import org.nutz.lang.Times;
import org.nutz.lang.util.NutMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @title: SyncDataInfoServer
 * @description: 同步数据信息
 * @date: 2020/8/18
 * @author: zwh
 * @copyright: Copyright (c) 2020
 * @version: 1.0
 */
@Service
@Slf4j
public class SyncServer {


    @Autowired
    GasAreaCommunityService gasAreaCommunityService;
    @Autowired
    ConsumerService consumerService;
    @Autowired
    GasBookNoService gasBookNoService;
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
    @Autowired
    GasUserChargeRecord2019Service userChargeRecord2019Service;
    @Autowired
    GasUserChargeRecord2020Service userChargeRecord2020Service;

    /**
     * @description: 小区+信息
     * @date: 2020/8/18
     * @author: zwh
     */
    // @Transactional(rollbackFor = Exception.class)
    public String syncCommAndConsumer(Integer id) {
        AtomicInteger areaCount = new AtomicInteger();
        AtomicInteger bookCount = new AtomicInteger();
        AtomicInteger count = new AtomicInteger();
        AtomicInteger meterCount = new AtomicInteger();
        AtomicInteger buyGasCount = new AtomicInteger();
        AtomicInteger mendGasCount = new AtomicInteger();
        AtomicInteger refundGasCount = new AtomicInteger();
        List<GasUserChargeRecordEntity> chargeRecordList = new ArrayList<>();
        List<GasMendGasEntity> mendGasList = new ArrayList<>();
        List<GasRefundGasEntity> refundGasList = new ArrayList<>();
        List<CommunityEntity> list = new ArrayList<>();
        List<ConsumerEntity> consumer1 = consumerService.list();
        if (id != null) {
            list = communityService.list(new QueryWrapper<CommunityEntity>().eq("Comm_id", id).orderByAsc("Comm_id"));
        } else {
            list = communityService.list(new QueryWrapper<CommunityEntity>().orderByAsc("Comm_id"));
            // list=list.subList(0,500);
        }
        // 小区
        try {
            list.forEach(area -> {
                // 查询小区下面的人数
                List<ConsumerEntity> consumer = consumerService.list(new QueryWrapper<ConsumerEntity>().eq("Community_Comm_id", area.getCommId()));
                GasAreaCommunityEntity areaCommunity = new GasAreaCommunityEntity();
                areaCommunity.setId(getUUID());
                areaCommunity.setName(area.getCommName() + "(金凤)");
                areaCommunity.setAddress(areaCommunity.getName());
                areaCommunity.setOldKeywords(areaCommunity.getName());
                areaCommunity.setOpBy("system import");
                areaCommunity.setOpAt(Times.getTS());
                areaCommunity.setDelFlag(false);
                areaCommunity.setAreaCode("150203");
                // 是否存在小区
                GasAreaCommunityEntity commNames = gasAreaCommunityService.getOnes(areaCommunity.getName());
                if (Lang.isEmpty(commNames)) {
                    boolean areaComm = gasAreaCommunityService.saves(areaCommunity);
                    if (!areaComm) {
                        log.error("新增失败:{}", areaCommunity.getId());
                    } else {
                        areaCount.getAndIncrement();
                    }
                } else {
                    boolean areaComm = gasAreaCommunityService.updates(areaCommunity);
                    if (!areaComm) {
                        log.error("更新失败:{}", areaCommunity.getId());
                    } else {
                        areaCount.getAndIncrement();
                    }
                }
                // 查询户号 按地址查 只适合 金凤
                GasBookNoEntity bookOne = gasBookNoService.getOnes(new QueryWrapper<GasBookNoEntity>()
                        .eq("address", areaCommunity.getName()));
                GasBookNoEntity bookNoEntity = new GasBookNoEntity();
                if (Lang.isEmpty(bookOne)) {
                    GasBookNoEntity books = gasBookNoService.getOnes(new QueryWrapper<GasBookNoEntity>().lt("id", 7000).orderByDesc("id").last("limit 1"));
                    bookNoEntity.setId(books.getId() + 1);
                    bookNoEntity.setAddress(areaCommunity.getName());
                    bookNoEntity.setCommunityId(areaCommunity.getId());
                    bookNoEntity.setDistributed(true);
                    bookNoEntity.setOpBy("system import");
                    bookNoEntity.setOpAt(Times.getTS());
                    bookNoEntity.setDelFlag(false);
                    bookNoEntity.setUserCount(consumer.size());
                    // 户号
                    boolean book = gasBookNoService.saves(bookNoEntity);
                    if (!book) {
                        log.error("失败:{}", bookNoEntity.getId());
                    } else {
                        bookCount.getAndIncrement();
                    }
                } else {
                    SyncUtil.copyProperties(bookOne, bookNoEntity);
                    bookNoEntity.setCommunityId(areaCommunity.getId());
                    bookNoEntity.setUserCount(consumer.size());
                    // 户号
                    boolean book = gasBookNoService.updates(bookNoEntity);
                    if (!book) {
                        log.error("失败:{}", bookNoEntity.getId());
                    } else {
                        bookCount.getAndIncrement();
                    }
                }
                //   人员
                // 用户循环
                consumer.forEach(item -> {
                    UsercardinfoEntity cardInfo = usercardinfoService.getById(item.getCsmId());
                    // 购气、退气、补气全记录
                    List<CardoprecordEntity> jfAcc = cardoprecordService.list(new QueryWrapper<CardoprecordEntity>()
                            .eq("CO_ConsumerId", item.getCsmId()).and(i -> i.ne("CO_WaterCount", 0).or().ne("CO_Freewater", 0)).orderByAsc("CO_CreateTime"));
                    // 金凤库充值记录
                    List<CardoprecordEntity> cardCord = cardoprecordService.list(new QueryWrapper<CardoprecordEntity>()
                            .eq("CO_ConsumerId", item.getCsmId()).gt("CO_WaterCount", 0).orderByAsc("CO_id").orderByAsc("CO_CreateTime"));
                    // 退气记录
                    List<CardoprecordEntity> reFundCord = cardoprecordService.list(new QueryWrapper<CardoprecordEntity>()
                            .eq("CO_ConsumerId", item.getCsmId()).lt("CO_WaterCount", 0).orderByAsc("CO_id").orderByAsc("CO_CreateTime"));
                    // 补气记录
                    List<CardoprecordEntity> mendCord = cardoprecordService.list(new QueryWrapper<CardoprecordEntity>()
                            .eq("CO_ConsumerId", item.getCsmId()).ge("CO_WaterCount", 0).gt("CO_WaterCount", 0).orderByAsc("CO_id").orderByAsc("CO_CreateTime"));
                    // 金凤 库补卡
                    int mendCount = makeupcardService.count(new QueryWrapper<MakeupcardEntity>()
                            .eq("MUC_ConsumerId", item.getCsmId()).eq("MUC_OPType", 3));
                    // 上次充值时间金额
                    long lastRecTime = 0L;
                    long lastRecMoney = 0L;
                    if (cardCord != null && cardCord.size() > 0) {
                        lastRecTime = cardCord.get(cardCord.size() - 1).getCoCreatetime().toEpochSecond(ZoneOffset.of("+8"));
                        lastRecMoney = MoneyUtil.yuanToLi(String.valueOf(cardCord.get(cardCord.size() - 1).getCoTotalnormalfee()));
                    }
                    // 累计购气量  购气+退气
                    int buyGasJfSum = cardCord.stream().mapToInt(CardoprecordEntity::getCoWatercount).sum();
                    int fefundJfSum = reFundCord.stream().mapToInt(CardoprecordEntity::getCoWatercount).sum();
                    int jfSumCount = buyGasJfSum + fefundJfSum;
                    // 累计购气金额 购气+退气
                    long buyMoney = MoneyUtil.yuanToLi(String.valueOf(cardCord.stream().mapToDouble(CardoprecordEntity::getCoTotalnormalfee).sum()));
                    long refundMoney = MoneyUtil.yuanToLi(String.valueOf(reFundCord.stream().mapToDouble(CardoprecordEntity::getCoTotalnormalfee).sum()));
                    long jfSumMoney = buyMoney + refundMoney;
                    // 用户是否存在
                    GasUserEntity gasUser = new GasUserEntity();
                    gasUser.setAccountNumber("JF" + SyncUtil.addZeroForNum(String.valueOf(item.getCsmId()), 8));
                    gasUser.setSysNumber("03");
                    gasUser.setOldAccountNumber("");
                    // 小区名称
                    gasUser.setCommunity(areaCommunity.getId());
                    gasUser.setBookNo(String.valueOf(bookNoEntity.getId()));
                    gasUser.setAccountNo(0);//补户号
                    gasUser.setAccountName(item.getCsmName());
                    gasUser.setBalance(MoneyUtil.yuanToLi(String.valueOf(cardInfo.getCardTodayRemainMoney())));
                    gasUser.setArrears(0L);
                    gasUser.setLateFee(0L);
                    gasUser.setUseType(jf_use_type_map.get(cardInfo.getWaterpriceWpId()));//用气类型
                    gasUser.setMobile(item.getCsmTel1());
                    gasUser.setAddress(item.getCsmAddr());
                    gasUser.setUserType(1);
                    gasUser.setMeterId(getUUID());
                    gasUser.setOpenTime(item.getCsmOpentime().toEpochSecond(ZoneOffset.of("+8")));
                    gasUser.setAddGas(BigDecimal.valueOf(0));
                    //   status 停用
                    gasUser.setStatus(item.getCsmIsdeleted() == 0 ? 1 : 4);
                    gasUser.setDelFlag(false);
                    // 是否公用
                    gasUser.setCanDelete(false);
                    gasUser.setHeatingBand(0);
                    gasUser.setBatchOpen(0);
                    gasUser.setLastRechargeTime(lastRecTime);//上次充值时间
                    gasUser.setLastRechargeMoney(lastRecMoney);//上次充值金额
                    gasUser.setOpenCard(item.getCsmIsopened());
                    gasUser.setTotalDosage(BigDecimal.valueOf(jfSumCount));
                    gasUser.setTotalBuyGasMoney(jfSumMoney);
                    gasUser.setOpAt(Times.getTS());
                    gasUser.setOpByName("系统");
                    gasUser.setOpBy("system import");
                    GasUserEntity gasUserEntity = gasUserService.getByAcc(gasUser.getAccountNumber());
                    if (Lang.isEmpty(gasUserEntity)) {
                        //  用户入库
                        boolean user = gasUserService.insetUser(gasUser);
                        if (!user) {
                            log.error("失败:{}", gasUser.getAccountNumber());
                        } else {
                            count.getAndIncrement();
                        }
                    } else {
                        //  用户更新
                        gasUser.setMeterId(gasUserEntity.getMeterId());
                        boolean user = gasUserService.updateUser(gasUser);
                        if (!user) {
                            log.error("失败:{}", gasUser.getAccountNumber());
                        } else {
                            count.getAndIncrement();
                        }
                    }
                    //  meter
                    GasMeterEntity gasMeter = new GasMeterEntity();
                    gasMeter.setId((gasUser.getMeterId()));
                    gasMeter.setMeterNo("");
                    gasMeter.setSupplier("JF");
                    // 表具型号
                    gasMeter.setModelId("de22710d9c0e46ceb5433aac577a07e0");
                    gasMeter.setDigit(0);
                    gasMeter.setDelFlag(false);
                    gasMeter.setCardNo(String.valueOf(cardInfo.getCardId()));
                    gasMeter.setInstallTime(cardInfo.getCardCreatetime().toEpochSecond(ZoneOffset.of("+8")));
                    gasMeter.setOpenCard(item.getCsmIsopened());
                    gasMeter.setOpenCardTime(item.getCsmOpentime().toEpochSecond(ZoneOffset.of("+8")));
                    gasMeter.setBuyGasCount(cardInfo.getCardBuycount());
                    gasMeter.setMendCardCount(mendCount);
                    gasMeter.setOpAt(Times.getTS());
                    gasMeter.setOpByName("系统");
                    gasMeter.setOpBy("system import");
                    // 表具入库
                    boolean meter = gasMeterService.insertMeter(gasMeter);
                    if (!meter) {
                        log.error("表具：{}", gasMeter.getId());
                    } else {
                        meterCount.getAndIncrement();
                    }
                    //  购气
                    for(int i=0;i<cardCord.size();i++){
                        CardoprecordEntity buys=cardCord.get(i);
                        // 购气次数
                        GasUserChargeRecordEntity gasUserCharge = new GasUserChargeRecordEntity();
                        // 流水号
                        gasUserCharge.setId(SyncUtil.accNo(buys.getCoCreatetime(), buys.getCoId()));
                        gasUserCharge.setAccountNumber(gasUser.getAccountNumber());
                        gasUserCharge.setMeterId(gasUser.getMeterId());
                        gasUserCharge.setPaidAmount(MoneyUtil.yuanToLi(String.valueOf(buys.getCoTotalnormalfee())));
                        gasUserCharge.setChangeMoney(0L);
                        gasUserCharge.setArrears(0L);
                        gasUserCharge.setBalance(MoneyUtil.yuanToLi(String.valueOf(buys.getCoLastremainmoney())));
                        gasUserCharge.setUserNewMoney(MoneyUtil.yuanToLi(String.valueOf(buys.getCoTodayremainmoney())));
                        gasUserCharge.setPreStored(0L);
                        gasUserCharge.setPayMethod(0);
                        gasUserCharge.setPaySource(2);
                        gasUserCharge.setUseType(gasUser.getUseType());
                        gasUserCharge.setDelFlag(false);
                        gasUserCharge.setUserType(1);
                        gasUserCharge.setBuyGas(BigDecimal.valueOf(buys.getCoWatercount()));
                        gasUserCharge.setBuyGasAmount(MoneyUtil.yuanToLi(String.valueOf(buys.getCoTotalnormalfee())));
                        // 购气次数
                        // gasUserCharge.setBuyGasTimes(Long.valueOf(gasMeter.getBuyGasCount()));
                        if(i==0){
                            gasUserCharge.setTotalBuyGas(BigDecimal.valueOf(buys.getCoWatercount()));
                            gasUserCharge.setTotalBuyGasAmount(MoneyUtil.yuanToLi(String.valueOf(buys.getCoTotalnormalfee())));
                        }else {
                            gasUserCharge.setTotalBuyGas(BigDecimal.valueOf(buys.getCoWatercount()+cardCord.get(i-1).getCoWatercount()));
                            gasUserCharge.setTotalBuyGasAmount(MoneyUtil.yuanToLi(String.valueOf(buys.getCoTotalnormalfee()+cardCord.get(i-1).getCoTotalnormalfee())));
                        }
                        gasUserCharge.setBuyGasTimes(Long.valueOf(i+1));
                        // 费用计算明细
                        long price = MoneyUtil.yuanToLi(String.valueOf(buys.getCoUnitnormalfee()));//单价
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
                        gasUserCharge.setPayTime(buys.getCoCreatetime().toEpochSecond(ZoneOffset.of("+8")));
                        gasUserCharge.setStatus(0);
                        gasUserCharge.setDelFlag(false);
                        gasUserCharge.setSettleStatus(1);
                        gasUserCharge.setSettleTime(buys.getCoCreatetime().toEpochSecond(ZoneOffset.of("+8")));
                        gasUserCharge.setSettleByName("系统");
                        gasUserCharge.setOpByName("系统");
                        gasUserCharge.setOpAt(Times.getTS());
                        gasUserCharge.setOpBy("system import");
                        gasUserCharge.setNote(buys.getCoRemark());
                        //购气入库
                        if (("2019").equals(buys.getCoCreatetime().toString().substring(0, 4))) {
                            GasUserChargeRecord2019Entity uc2019 = new GasUserChargeRecord2019Entity();
                            SyncUtil.copyProperties(gasUserCharge, uc2019);
                            boolean uc1 = userChargeRecord2019Service.saveOrupdates(uc2019);
                            if (!uc1) {
                                log.error("表具：{}", JSONObject.toJSONString(uc2019));
                            } else {
                                buyGasCount.getAndIncrement();
                            }
                        } else if (("2020").equals(buys.getCoCreatetime().toString().substring(0, 4))) {
                            GasUserChargeRecord2020Entity uc2020 = new GasUserChargeRecord2020Entity();
                            SyncUtil.copyProperties(gasUserCharge, uc2020);
                            boolean uc2 = userChargeRecord2020Service.saveOrUpdates(uc2020);
                            if (!uc2) {
                                log.error("表具：{}", JSONObject.toJSONString(uc2020));
                            } else {
                                buyGasCount.getAndIncrement();
                            }
                        }
                        chargeRecordList.add(gasUserCharge);
                    }
                    // 补气
                    for(int i=0;i<mendCord.size();i++){
                        CardoprecordEntity mends=cardCord.get(i);
                        GasMendGasEntity gasMendGasEntity = new GasMendGasEntity();
                        gasMendGasEntity.setId(SyncUtil.accNo(mends.getCoCreatetime(), mends.getCoId()));
                        gasMendGasEntity.setAccountNumber(gasUser.getAccountNumber());
                        gasMendGasEntity.setMeterId(gasUser.getMeterId());
                        gasMendGasEntity.setUseType(gasUser.getUseType());
                        gasMendGasEntity.setUserType(gasUser.getUserType());
                        gasMendGasEntity.setMendGas(BigDecimal.valueOf(mends.getCoFreewater()));
                        gasMendGasEntity.setMendGasAmount(0L);
                        long price = MoneyUtil.yuanToLi(String.valueOf(mends.getCoUnitnormalfee()));//单价
                        gasMendGasEntity.setCostMoney(price * mends.getCoFreewater());
                        // fixme 补气记录中的购气次数
                        gasMendGasEntity.setBuyGasTimes(gasMeter.getBuyGasCount());
                        gasMendGasEntity.setChargeType(0);
                        gasMendGasEntity.setFeeDetail("");
                        gasMendGasEntity.setDelFlag(false);
                        gasMendGasEntity.setCreatAt(mends.getCoCreatetime().toEpochSecond(ZoneOffset.of("+8")));
                        gasMendGasEntity.setStatus(2);
                        gasMendGasEntity.setReason(mends.getCoRemark());
                        gasMendGasEntity.setOpByName("系统");
                        gasMendGasEntity.setOpAt(Times.getTS());
                        gasMendGasEntity.setOpBy("system import");
                        // 补气入库
                        boolean insertMendGas = gasMendGasService.saveOrUpdates(gasMendGasEntity);
                        if (!insertMendGas) {
                            log.error("表具：{}", JSONObject.toJSONString(gasMendGasEntity));
                        } else {
                            mendGasCount.getAndIncrement();
                        }
                        mendGasList.add(gasMendGasEntity);
                    }

                    // 退气
                    for(int i=0;i<reFundCord.size();i++){
                        CardoprecordEntity refunds=cardCord.get(i);
                        GasRefundGasEntity gasRefundGasEntity = new GasRefundGasEntity();
                        gasRefundGasEntity.setId(SyncUtil.accNo(refunds.getCoCreatetime(), refunds.getCoId()));
                        gasRefundGasEntity.setAccountNumber(gasUser.getAccountNumber());
                        gasRefundGasEntity.setMeterId(gasUser.getMeterId());
                        gasRefundGasEntity.setUseType(gasUser.getUseType());
                        gasRefundGasEntity.setUserType(gasUser.getUserType());
                        gasRefundGasEntity.setRefundGas(BigDecimal.valueOf(Math.abs(refunds.getCoWatercount()) + refunds.getCoFreewater()));
                        gasRefundGasEntity.setRefundGasMoney(0L);
                        gasRefundGasEntity.setRefundMoney(Math.abs(MoneyUtil.yuanToLi(String.valueOf(refunds.getCoTotalnormalfee()))));//气费金额
                        gasRefundGasEntity.setChargeType(0);
                        gasRefundGasEntity.setChargeRecordId("");
                        gasRefundGasEntity.setStatus(2);
                        gasRefundGasEntity.setDelFlag(false);
                        gasRefundGasEntity.setCreatAt(refunds.getCoCreatetime().toEpochSecond(ZoneOffset.of("+8")));
                        gasRefundGasEntity.setReason(refunds.getCoRemark());
                        gasRefundGasEntity.setOpByName("系统");
                        gasRefundGasEntity.setOpAt(Times.getTS());
                        gasRefundGasEntity.setOpBy("system import");
                        // 退气入库
                        boolean refund1 = gasRefundGasService.saveOrUpdates(gasRefundGasEntity);
                        if (!refund1) {
                            log.error("表具：{}", JSONObject.toJSONString(gasRefundGasEntity));
                        } else {
                            refundGasCount.getAndIncrement();
                        }
                        refundGasList.add(gasRefundGasEntity);
                    }
                });

            });
            String res = "小区成功:" + areaCount + "本号成功:" + bookCount;
            String counta = "小区总数:" + list.size() + "本号总数:" + list.size();
            log.info(res);
            log.info(counta);
            log.info("用户成功{}条,水表成功{}条,购气成功{}条,补气成功{}条，退气成功{}条",
                    count, meterCount, buyGasCount, mendGasCount, refundGasCount);
            log.info("用户总{}条,水表总{}条,购气总{}条,补气总{}条，退气总{}条",
                    consumer1.size(), consumer1.size(), chargeRecordList.size(),
                    mendGasList.size(), refundGasList.size());
            return res + "=====" + counta;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("异常:{}", e.getMessage());
            //  TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return e.getMessage();
        }
    }

    /**
     * @description: UUID
     * @date: 2020/8/18
     * @author: zwh
     */
    public String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    // 金凤 用气类型
    private static Map<Integer, String> jf_use_type_map = new HashMap() {
        {
            put(3, "F_M_0");//居民煤气0.8
            put(4, "F_M_1");//居民天然气2.06
            put(5, "F_G_4");// add  商业天然气 2.3
            put(6, "F_G_1");//公用煤气1.2
        }
    };
    // 金凤 单价
    private static Map<String, Long> jf_price_map = new HashMap() {
        {
            put("F_M_0", 800);//居民煤气0.8
            put("F_M_1", 2060);//居民天然气2.06
            put("F_G_4", 2300);// add  商业天然气 2.3
            put("F_G_1", 1200);//公用煤气1.2
        }
    };
    // 是否公用户
    private static Map<String, Boolean> jf_public_user = new HashMap() {
        {
            put("F_M_0", false);//居民煤气0.8
            put("F_M_1", false);//居民天然气2.06
            put("F_G_4", true);// add  商业天然气 2.3
            put("F_G_1", true);//公用煤气1.2
        }
    };
}
