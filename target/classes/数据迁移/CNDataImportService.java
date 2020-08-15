package cn.osdiot.app.data.modules.services.impl;

import cn.osdiot.app.data.commons.utils.V2DaoUtil;
import cn.osdiot.app.gas.modules.commons.enums.*;
import cn.osdiot.app.gas.modules.models.*;
import cn.osdiot.app.utils.MoneyUtil;
import cn.osdiot.app.utils.NumberUtil;
import com.alibaba.fastjson.JSON;
import org.nutz.aop.interceptor.ioc.TransAop;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.integration.jedis.RedisService;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.Times;
import org.nutz.lang.random.R;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Pattern;

/**
 * @Author zhangyi
 * @Date 2020/5/15 10:54.
 * @Description
 */
@IocBean
public class CNDataImportService {
    private static final Log log = Logs.get();
    private final static long CYCLE_NUMBER = 1000000L;

    private static final ExecutorService workExs = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    //苍南包头价格配置
    private static Map<Integer, String> cnbt_price_map = new HashMap() {
        {
            put(101, "F_G_1");//公用煤气1.2
            put(102, "F_G_4");//公用天然气2.3
            put(104, "F_M_0");//民用煤气0.8
            put(105, "F_M_1");//民用天然气2.06
            put(106, "");//特殊价格用气
        }
    };

    @Inject
    private Dao cnDao;

    @Inject
    private Dao v2Dao;

    @Inject
    private V2DaoUtil v2DaoUtil;

    @Inject
    private RedisService redisService;

    public void migrationDo() {
        /**
         * 先从查询用户，处理每个用户数据
         *
         */
        long start = Times.getTS();

        int queryTotal = 0;//查询用户总数量
        int successTotal = 0;//成功查询数据
        int pageNumber = 0;
        int pageSize = 200;
        int listSize = 0;
        List<String> errList = new ArrayList<>();

        do {
            pageNumber++;
            List<Record> recordList = cnDao.query("TB_USER", Cnd.NEW(), new Pager(pageNumber, pageSize));
            listSize = recordList.size();
            queryTotal += recordList.size();

            for (Record record : recordList) {
                try {
                    handleCNBTOneUSer(record);
                    successTotal++;
                } catch (Exception e) {
                    errList.add(String.format("处理数据时出错,用户信息：%s,%s", JSON.toJSONString(record), e.getMessage()));
                }
            }

        } while (listSize >= pageSize);








//        do {
//            pageNumber++;
//            List<Record> recordList = cnDao.query("TB_USER", Cnd.NEW(), new Pager(pageNumber, pageSize));
//            listSize = recordList.size();
//            queryTotal += recordList.size();
//
//            /***********线程跑****************/
//            CompletionService<Boolean> completionService = new ExecutorCompletionService<Boolean>(workExs);
//            for (Record record : recordList) {
//                completionService.submit(new Callable<Boolean>() {
//                    @Override
//                    public Boolean call() {
//                        try {
//                            handleCNBTOneUSer(record);
//                            return true;
//                        } catch (Exception e) {
//                            log.errorf("处理数据时出错,用户信息：%s,%s", JSON.toJSONString(record), e);
//                            return false;
//                        }
//                    }
//                });
//            }
//            for (int i = 0; i < recordList.size(); i++) {
//                try {
//                    Boolean aBoolean = completionService.take().get();
//                    if (aBoolean) {
//                        successTotal++;
//                    }
//                } catch (Exception e) {
//                    log.errorf("多线程处理获取返回值发生的严重错误%s", e);
//                }
//            }
//            /***********线程跑****************/
//
//
//        } while (listSize >= pageSize);

        log.debugf("本次迁移查询出用户:%s个，成功导入%s", queryTotal, successTotal);
        long end = Times.getTS();
        log.debugf("迁移耗时:%s秒", end - start);
        for (String s : errList) {
            log.error(s);
        }
    }

    /**
     * 处理苍南包头系统数据
     *
     * @param user
     */
    @Aop(TransAop.READ_COMMITTED)
    public void handleCNBTOneUSer(Record user) {
        Record userMeter = cnDao.fetch("tb_usermeter", Cnd.where("userid", "=", user.getString("userid")).desc("editdate"));
        if (Lang.isEmpty(userMeter)) {
            throw Lang.makeThrow("未查询到用户表具关系");
        }
        Record meter = cnDao.fetch("g_meter", Cnd.where("meterid", "=", userMeter.getString("meterid")));
        if (Lang.isEmpty(meter)) {
            throw Lang.makeThrow("未查询到表具信息");
        }
        List<Record> tb_record_list = cnDao.query("tb_record", Cnd.where("meterid", "=", userMeter.getString("meterid")).asc("writedate"));
        String ictype = meter.getString("ictype");//流量计：CNMCPU20、CNMCPU30
        /**
         * CNBT（苍南包头）
         * 如需保留10位长度，打算前四位设置为字符（因为要和中浩白云分公司的区分开来），如CNBT（苍南包头）、CNBY（苍南白云）之后的6位为正常数字排序
         */
        String accountNumber = "CNBT" + user.getString("userno").substring(6);
        int count = v2Dao.count("gas_user", Cnd.where("accountNumber", "=", accountNumber));
        if (count > 0) {
            throw Lang.makeThrow("户号重复");
        }

        //用户
        Gas_user gasUser = new Gas_user();
        gasUser.setAccountNumber(accountNumber);
        gasUser.setSysNumber(GasSysNumberEnum.CN);
        gasUser.setOldAccountNumber("");
        Integer bookNo = getBookNoCNBT(user.getString("username"), user.getString("useraddr"));
        if (Lang.isNotEmpty(bookNo)) {
            Gas_book_no gasBookNo = v2Dao.fetch(Gas_book_no.class, Cnd.where("id", "=", bookNo));
            if (Lang.isNotEmpty(gasBookNo)) {
                gasUser.setCommunity(gasBookNo.getCommunityId());//补小区
                gasUser.setBookNo(bookNo);//补本号
            }
        }
        gasUser.setAccountNo(0);//补户号
        gasUser.setAccountName(user.getString("username"));
        gasUser.setBalance(MoneyUtil.yuanToLi(userMeter.getString("remainmoney")));
        gasUser.setArrears(0L);
        gasUser.setLateFee(0L);
        gasUser.setUseType(cnbt_price_map.get(userMeter.getInt("priceid")));//用气类型
        gasUser.setMobile(user.getString("mobile"));
        gasUser.setAddress(user.getString("useraddr"));
        gasUser.setUserType(GasUserTypeEnum.IC);
        gasUser.setMeterId(R.UU32());
        gasUser.setOpenTime(parseDate(user.getString("editdate")));
        gasUser.setAddGas(0D);
        gasUser.setStatus(GasUserStatusEnum.OPENING);
        gasUser.setCanDelete(false);
        gasUser.setHeatingBand(false);
        gasUser.setBatchOpen(false);
        gasUser.setLastRechargeTime(parseDate(userMeter.getString("writedate")));//上次充值时间
        gasUser.setLastRechargeMoney(MoneyUtil.yuanToLi(userMeter.getString("salemoney")));//上次充值金额
        gasUser.setOpenCard(true);
        double totalDosage = 0D;//总购气量
        long totalBuyGasMoney = 0L;//总购金额(厘)

        //气表
        Gas_meter gasMeter = new Gas_meter();
        gasMeter.setId(gasUser.getMeterId());
        gasMeter.setMeterNo("");
        if ("CNMCPU20".equalsIgnoreCase(ictype)) {
            gasMeter.setSupplier("CN_INDUSTRY");//苍南工商流量计
            gasMeter.setModelId("e15d0253522e4ac98296bdcae5c79c94");//苍南工商流量计 02
            gasMeter.setDigit(8);
            gasMeter.setCardNo(Strings.alignRight(userMeter.getString("meterid"), 10, '0'));
        } else if ("CNMCPU30".equalsIgnoreCase(ictype)) {
            gasMeter.setSupplier("CN_INDUSTRY");//苍南工商流量计
            gasMeter.setModelId("4845f5f66f0e4f5ca3bbcb01fd5d3e85");//苍南工商流量计 04
            gasMeter.setDigit(8);
            gasMeter.setCardNo(Strings.alignRight(userMeter.getString("meterid"), 16, '0'));
        } else {
            gasMeter.setSupplier("CN_PERSON");//苍南普卡
            gasMeter.setModelId("e7eb39f724554e1d9d9aef59e68777f4");//苍南普卡
            gasMeter.setDigit(5);
            gasMeter.setCardNo(Strings.alignRight(userMeter.getString("meterid"), 10, '0'));
        }
        gasMeter.setInstallTime(gasUser.getOpenTime());
        gasMeter.setOpenCard(true);
        gasMeter.setOpenCardTime(gasUser.getOpenTime());
        gasMeter.setBuyGasCount(userMeter.getInt("writenumber", 0));
        gasMeter.setMendCardCount(userMeter.getInt("fillcardno", 0));
        gasMeter.setAreaCode(user.getString("areacode"));

        //交易记录
        List<Gas_user_charge_record> chargeRecordList = new ArrayList<>();
        List<Gas_mend_gas> mendGasList = new ArrayList<>();
        List<Gas_refund_gas> refundGasList = new ArrayList<>();
        for (Record tb_record : tb_record_list) {
            int optype = tb_record.getInt("optype");
            long opAt = parseDate(tb_record.getString("editdate"));
            switch (optype) {
                case 1:
                case 2:
                    //制卡、充值
                    if (tb_record.getDouble("gasvalue") >= 1) {
                        //有购气量都是购气的
                        Gas_user_charge_record chargeRecord = new Gas_user_charge_record();
                        chargeRecord.setId(nextId(opAt));
                        chargeRecord.setAccountNumber(gasUser.getAccountNumber());
                        chargeRecord.setMeterId(gasUser.getMeterId());
                        chargeRecord.setPaidAmount(MoneyUtil.yuanToLi(tb_record.getString("salemoney")));//实收金额
                        chargeRecord.setChangeMoney(0L);
                        chargeRecord.setArrears(0L);
                        chargeRecord.setUserNewMoney(MoneyUtil.yuanToLi(tb_record.getString("remainmoney")));//本次余额
                        chargeRecord.setUserNewArrears(0L);
                        chargeRecord.setPayMethod(GasPayMethodEnum.CASH);
                        chargeRecord.setPaySource(GasPaySourceEnum.PC);
                        chargeRecord.setUseType(gasUser.getUseType());
                        chargeRecord.setUserType(GasUserTypeEnum.IC);
                        chargeRecord.setBuyGas(tb_record.getDouble("gasvalue", 0D));
                        chargeRecord.setBuyGasAmount(MoneyUtil.yuanToLi(tb_record.getString("writemoney")));//气费金额
                        chargeRecord.setBalance(chargeRecord.getUserNewMoney() - chargeRecord.getPaidAmount() + chargeRecord.getBuyGasAmount());
                        chargeRecord.setBuyGasTimes(tb_record.getInt("writenumber", 0));
                        long price = MoneyUtil.yuanToLi(tb_record.getString("gasprice"));//单价
                        NutMap feeDetailMap = NutMap.NEW().setv("ladder", false);
                        List<NutMap> ladderConfig = new ArrayList<>();
                        ladderConfig.add(NutMap.NEW().setv("price", price).setv("dosage", ""));
                        ladderConfig.add(NutMap.NEW());
                        ladderConfig.add(NutMap.NEW());
                        ladderConfig.add(NutMap.NEW());
                        ladderConfig.add(NutMap.NEW());
                        feeDetailMap.setv("ladderConfig", ladderConfig);
                        feeDetailMap.setv("gasValue", chargeRecord.getBuyGas());
                        List<NutMap> detail = new ArrayList<>();
                        detail.add(NutMap.NEW().setv("price", price).setv("cur", 0).setv("dosage", chargeRecord.getBuyGas()).setv("fee", chargeRecord.getBuyGasAmount()));
                        feeDetailMap.setv("detail", detail);
                        feeDetailMap.setv("fee", chargeRecord.getBuyGasAmount());
                        chargeRecord.setFeeDetail(JSON.toJSONString(feeDetailMap));
                        chargeRecord.setPayStatus(GasPayStatusEnum.PAID);
                        chargeRecord.setPayTime(opAt);
                        chargeRecord.setStatus(GasChargeStatusEnum.NORMAL);
                        chargeRecord.setSettleStatus(GasSettleStatusEnum.SETTLEED);
                        chargeRecord.setSettleTime(opAt);
                        chargeRecord.setSettleByName("系统");
                        chargeRecord.setOpByName("系统");
                        chargeRecord.setNote(tb_record.getString("remarks"));
                        chargeRecordList.add(chargeRecord);
                        totalDosage += tb_record.getDouble("gasvalue");
                        totalBuyGasMoney += MoneyUtil.yuanToLi(tb_record.getString("salemoney"));
                    }
                    break;
                case 3:
                case 5:
                    //补气、补卡
                    Gas_mend_gas mendGas = new Gas_mend_gas();
                    if (tb_record.getDouble("gasvalue") >= 1) {
                        mendGas.setId(nextId(opAt));
                        mendGas.setAccountNumber(gasUser.getAccountNumber());
                        mendGas.setMeterId(gasUser.getMeterId());
                        mendGas.setUseType(gasUser.getUseType());
                        mendGas.setUserType(gasUser.getUserType());
                        mendGas.setMendGas(tb_record.getDouble("gasvalue"));
                        mendGas.setMendGasAmount(0L);
                        long price = MoneyUtil.yuanToLi(tb_record.getString("gasprice"));//单价
                        mendGas.setCostMoney(NumberUtil.mul(Long.toString(price), tb_record.getString("gasvalue")).longValue());
                        mendGas.setBuyGasTimes(tb_record.getInt("writenumber", 0));
                        mendGas.setChargeType(GasMeterChargeTypeEnum.GAS);
                        mendGas.setFeeDetail("");
                        mendGas.setCreatAt(opAt);
                        mendGas.setStatus(GasMendStatusEnum.SUCCESS);
                        mendGas.setReason(tb_record.getString("remarks"));
                        mendGas.setOpByName("系统");
                        mendGasList.add(mendGas);
                    }
                    break;
                case 4:
                    //退购
                    Gas_refund_gas refundGas = new Gas_refund_gas();
                    refundGas.setId(nextId(opAt));
                    refundGas.setAccountNumber(gasUser.getAccountNumber());
                    refundGas.setMeterId(gasUser.getMeterId());
                    refundGas.setUseType(gasUser.getUseType());
                    refundGas.setUserType(gasUser.getUserType());
                    refundGas.setRefundGas(Math.abs(tb_record.getDouble("gasvalue")));
                    refundGas.setRefundGasMoney(0L);
                    refundGas.setRefundMoney(Math.abs(MoneyUtil.yuanToLi(tb_record.getString("writemoney"))));//气费金额
                    refundGas.setChargeType(GasMeterChargeTypeEnum.GAS);
                    refundGas.setChargeRecordId("");
                    refundGas.setStatus(GasRefundStatusEnum.SUCCESS);
                    refundGas.setCreatAt(opAt);
                    refundGas.setReason(tb_record.getString("remarks"));
                    refundGas.setOpByName("系统");
                    refundGasList.add(refundGas);
                    totalDosage += tb_record.getDouble("gasvalue");
                    totalBuyGasMoney += MoneyUtil.yuanToLi(tb_record.getString("salemoney"));
                    break;
                case 6:
                    break;
                case 21:
                    break;
                case 41:
                    break;
                default:
            }
        }
        gasUser.setTotalDosage(totalDosage);//总购气量
        gasUser.setTotalBuyGasMoney(totalBuyGasMoney);//总购金额

        v2Dao.insert(gasUser);
        v2Dao.fastInsert(gasMeter);
        chargeRecordList.forEach(v-> v2DaoUtil.getDao(v.getId().substring(0, 4), Gas_user_charge_record.class).insert(v));
        mendGasList.forEach(v->v2Dao.insert(v));
        refundGasList.forEach(v->v2Dao.insert(v));
        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    private static Map<String, Integer> cnbt_bookNo_map = new HashMap() {
        {
            put("滨江国际三期15", 3044);
            put("滨江国际三期16", 3045);
            put("滨江国际三期17", 3046);
            put("滨江国际三期18", 3047);
            put("昆河二期1栋", 3048);
            put("昆河二期2栋", 3049);
            put("昆河二期3栋", 3050);
            put("昆河二期4栋", 3051);
            put("昆河二期5栋", 3052);
            put("昆河二期6栋", 3053);
            put("昆河二期7栋", 3054);
            put("昆河二期8栋", 3055);
            put("昆河二期9栋", 3056);
            put("团结10八职高", 3057);
            put("团结10药业楼", 3058);
            put("青年12#-5栋", 3059);
            put("友谊23#区委", 3060);
            put("青年8#25栋3号楼", 3061);
            put("美洋付14栋", 3062);
            put("哈林格尔丽景苑一期9栋", 3063);
            put("哈林格尔丽景苑一期16栋", 3064);
            put("哈林格尔丽景苑一期14栋", 3065);
            put("哈林格尔丽景苑一期15栋", 3066);
            put("哈林格尔丽景苑一期18栋", 3067);
            put("森茂华庭1栋", 3068);
            put("森茂华庭2栋", 3069);
            put("森茂华庭3栋", 3070);
            put("森茂华庭5栋", 3071);
            put("森茂华庭6栋", 3072);
            put("华天云居1", 3073);
            put("华天云居2", 3074);
            put("华天云居3", 3075);
            put("华天云居4", 3076);
            put("瀚星华府1号楼", 3077);
            put("瀚星华府2号楼", 3078);
            put("瀚星华府3号楼", 3079);
            put("瀚星华府4号楼", 3080);
            put("瀚星华府5号楼", 3081);
            put("瀚星华府6号楼", 3082);
            put("龙昱华府-1", 3083);
            put("龙昱华府-2", 3084);
            put("龙昱华府-3", 3085);
            put("龙昱华府4", 3086);
            put("龙昱华府5", 3087);
            put("龙昱华府6", 3088);
            put("龙昱华府7", 3089);
            put("龙昱华府8|龙昱华府-8", 3090);
            put("馥室成双1栋", 3091);
            put("馥室成双3栋", 3092);
            put("馥室成双4栋", 3093);
            put("馥室成双7栋", 3094);
            put("馥室成双9栋", 3095);
            put("馥室成双11栋", 3096);
            put("馥室成双二期18栋", 3097);
            put("馥室成双二期22栋", 3098);
            put("馥室成双二期26栋", 3099);
            put("天诚名筑", 3100);
            put("团14-公安楼", 3101);
            put("青年8#25栋", 3103);
            put("钢甲24#|钢24#", 3104);
            put("青年16", 3105);
        }
    };

    //苍南包头区域数据
    private static Map<Integer, String> cnbt_community_map = new HashMap() {
        {
            put(1507, "");//馥室成双，2900户          公33户
            put(1508, "");//昆河壹号二期，688户       公2户
            put(1509, "");//昆区，141户              公22户
            put(1510, "");//滨江国际三期，384户
            put(1511, "");//瀚星华府楼盘，827户       公5户
            put(1512, "");//茂业，1户
            put(1513, "");//馥室成双二期，187户
            put(1517, "");//河西工业区，1户           公1户
            put(1518, "");//少先23#，1户
            put(1519, "");//哈林格尔丽景苑，357户      公16户
            put(1520, "");//默认区域，1户             公1户
            put(1521, "");//友谊19，2户
            put(1522, "");//青年12，46户              公1户
            put(1523, "");//团8#，446户
            put(1524, "");//钢苑学府，1户             公1户
            put(1525, "");//天诚名筑，166户
            put(1526, "");//团结14#，66户
            put(1527, "");//青年16#，47户
        }
    };

    private static Integer getBookNoCNBT(String userName, String userAddr) {
        List<String> list = new ArrayList<>();
        list.add(Strings.sNull(userAddr));
        list.add(Strings.sNull(userName));
        for (String s : list) {
            for (String regex : cnbt_bookNo_map.keySet()) {
                if (Pattern.matches(".*(" + regex + ").*", s)) {
                    return cnbt_bookNo_map.get(regex);
                }
            }
            if (s.contains("哈林格尔丽景苑") && !s.contains("哈林格尔丽景苑一期")) {
                return 3102;
            }
        }
        return null;
    }

    private String nextId(long at) {
        String timestr = Times.ts2S(at, "yyyyMMddHHmmss");
        String key = "cn_wk_no_generator_" + timestr.substring(0, 8);
        Long serialNumber = redisService.incr(key);
        if (serialNumber == 1) {
            redisService.expire(key, 86400);//按天
        }
        return timestr + (1100000L + (serialNumber % CYCLE_NUMBER));
    }

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
}
