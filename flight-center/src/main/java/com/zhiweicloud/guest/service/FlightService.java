package com.zhiweicloud.guest.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.wyun.thrift.client.utils.ClientUtil;
import com.wyun.thrift.server.MyService;
import com.wyun.thrift.server.Response;
import com.wyun.utils.SpringBeanUtil;
import com.zhiweicloud.guest.common.model.FlightCenterResult;
import com.zhiweicloud.guest.common.model.FlightCenterStatus;
import com.zhiweicloud.guest.common.util.DateUtils;
import com.zhiweicloud.guest.exception.FlightCenterException;
import com.zhiweicloud.guest.mapper.CustomFlightPoMapper;
import com.zhiweicloud.guest.mapper.CustomerPoMapper;
import com.zhiweicloud.guest.mapper.FlightPoMapper;
import com.zhiweicloud.guest.po.CustomFlightPo;
import com.zhiweicloud.guest.po.CustomerPo;
import com.zhiweicloud.guest.po.FlightPo;
import com.zhiweicloud.guest.source.ibe.service.IbeService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Transient;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * FlightService.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * <p>
 * 2017/5/31 14:00
 *
 * @author tiecheng
 */
@Service
public class FlightService {

    private static final Log log = LogFactory.getLog(FlightService.class);

    @Autowired
    private FlightPoMapper flightPoMapper;

    @Autowired
    private CustomFlightPoMapper customFlightPoMapper;

    @Autowired
    private CustomerPoMapper customerPoMapper;

    @Autowired
    private IbeService ibeService;

    private static MyService.Iface baseInfoClient = SpringBeanUtil.getBean("baseInfoClient");

    /**
     * 查询航班信息 -- 时刻表
     *
     * @param request 参数
     * @return 航班中心结果集字符串
     * @version 2017-06-20
     */
    @Transactional
    public String queryFlightInfo(JSONObject request) {
        FlightCenterResult<List<FlightPo>> re = new FlightCenterResult<>();
        String flightNo = request.getString("flightNo");
        String depDate = request.getString("depDate");// 出发日期 即航班号
        String depAirportCode = request.getString("depAirportCode");
        String arrAirportCode = request.getString("arrAirportCode");
        if (log.isInfoEnabled()) {
            log.info("【参数   flightNo: " + flightNo + " depDate: " + depDate + " depAirportCode: " + depAirportCode + " arrAirportCode: " + arrAirportCode + " 】");
        }
        try {
            // 确定模式
            if (StringUtils.isNoneBlank(depAirportCode) && StringUtils.isNoneBlank(arrAirportCode)) {
                if (flightNo != null && !"".equals(flightNo)) {
                    re = queryFlightInfo(flightNo, depDate, depAirportCode, arrAirportCode);
                } else {
                    re = queryFlightInfo(depDate, depAirportCode, arrAirportCode);
                }
            } else {
                if (flightNo != null && !"".equals(flightNo)) {
                    re = queryFlightInfo(flightNo, depDate);
                } else {
                    re.setMessage(FlightCenterStatus.NONE_FLIGHT_NO.display());
                    re.setState(FlightCenterStatus.NONE_FLIGHT_NO.value());
                    re.setData(null);
                    return JSON.toJSONString(re);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            re.setMessage(FlightCenterStatus.ERROR.display());
            re.setState(FlightCenterStatus.ERROR.value());
            re.setData(null);
        }
        return JSON.toJSONString(re);
    }

    /**
     * 查询航班信息 -- 动态表
     *
     * @param request 参数
     * @return 航班中心结果集字符串
     */
    @Transactional
    public String queryDynamicFlightInfo(JSONObject request) {
        FlightCenterResult<List<FlightPo>> re = new FlightCenterResult<>();
        String flightNo = request.getString("flightNo");
        String depDate = request.getString("depDate");
        String depAirportCode = request.getString("depAirportCode");
        String arrAirportCode = request.getString("arrAirportCode");
        if (log.isInfoEnabled()) {
            log.info("【参数   flightNo: " + flightNo + " depDate: " + depDate + " depAirportCode: " + depAirportCode + " arrAirportCode: " + arrAirportCode + " 】");
        }
        try {
            // 确定模式
            if (StringUtils.isNoneBlank(depAirportCode) && StringUtils.isNoneBlank(arrAirportCode)) {
                if (flightNo != null && !"".equals(flightNo)) {
                    re = queryDynamicFlightInfo(flightNo, depDate, depAirportCode, arrAirportCode);
                } else {
                    re = queryDynamicFlightInfo(depDate, depAirportCode, arrAirportCode);
                }
            } else {
                if (flightNo != null && !"".equals(flightNo)) {
                    re = queryDynamicFlightInfo(flightNo, depDate);
                } else {
                    re.setMessage(FlightCenterStatus.NONE_FLIGHT_NO.display());
                    re.setState(FlightCenterStatus.NONE_FLIGHT_NO.value());
                    re.setData(null);
                    return JSON.toJSONString(re);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            re.setMessage(FlightCenterStatus.ERROR.display());
            re.setState(FlightCenterStatus.ERROR.value());
            re.setData(null);
        }
        return JSON.toJSONString(re);
    }

    /**
     * 定制航班
     *
     * @param request 参数
     * @return 航班中心结果集
     */
    @Transactional
    public String customFlight(JSONObject request) {
        FlightCenterResult<FlightPo> re = new FlightCenterResult();
        try {
            String flightNo = request.getString("flightNo");
            String depDate = request.getString("depDate");// 出发日期 即航班号
            String depAirportCode = request.getString("depAirportCode");
            String arrAirportCode = request.getString("arrAirportCode");

            if (log.isInfoEnabled()) {
                log.info("【 参数   flightNo: " + flightNo + " depDate: " + depDate + " depAirportCode: " + depAirportCode + " arrAirportCode: " + arrAirportCode + " 】");
            }

            String s = verifyArgs(flightNo, depAirportCode, arrAirportCode);

            if (!"OK".equals(s)) {
                return s;
            }

            String sysCode = request.getString("sysCode");
            CustomerPo customerPo = customerPoMapper.selectBySysCode(sysCode);

            FlightPo flightPo = new FlightPo();
            flightPo.setFlightNo(flightNo);
            flightPo.setDepDate(DateUtils.stringToDate(depDate, "yyyy-MM-dd"));

            List<FlightPo> flightPos = flightPoMapper.selectByDateAndNo(flightPo);

            flightPo.setDepAirportCode(depAirportCode);
            flightPo.setArrAirportCode(arrAirportCode);

            FlightPo localFlight = null;

            for (FlightPo flight : flightPos) {
                if (depAirportCode.equals(flight.getDepAirportCode()) && arrAirportCode.equals(flight.getArrAirportCode())) {
                    localFlight = flight;
                }
            }

            if (localFlight != null) {
                // TODO 根据实际数据需要酌情添加条件
                if ("到达".equals(localFlight.getFlightState())) {
                    re.setState(FlightCenterStatus.UNABLE_CUSTOM.value());
                    re.setMessage(FlightCenterStatus.UNABLE_CUSTOM.display());
                    re.setData(null);
                    return JSON.toJSONString(re);
                }
                // 本地存在 未定制 -- 定制
                if (localFlight.getIsCustom() != 1) {
                    flightPoMapper.updateIsCustom(flightPo);
                    ibeService.executeCustomFlight(flightNo, DateUtils.stringToDate(depDate, "yyyy-MM-dd"));
                    for (FlightPo flight : flightPos) {
                        flightPoMapper.updateIsCustom(flight);
                    }
                }
                CustomFlightPo localCustomFlight = customFlightPoMapper.selectByCustomerIdAndFlightId(customerPo.getCustomerId(), localFlight.getFlightId());
                if (localCustomFlight == null) {
                    // 插入定制信息
                    CustomFlightPo customFlightPo = new CustomFlightPo();
                    customFlightPo.setCustomUrl(customerPo.getCustomUrl());
                    customFlightPo.setCustomerId(customerPo.getCustomerId());
                    customFlightPo.setFlightId(localFlight.getFlightId());
                    customFlightPoMapper.insert(customFlightPo);
                }
                if(localCustomFlight !=null && localCustomFlight.getIsDeleted()==1){
                    customFlightPoMapper.resumeCustom(customerPo.getCustomerId(), localFlight.getFlightId());
                }
                re.setState(FlightCenterStatus.SUCCESS.value());
                re.setMessage(FlightCenterStatus.SUCCESS.display());
                re.setData(null);
            } else {
                re = getFlightPoCustomByIbeSource(customerPo, DateUtils.stringToDate(depDate, "yyyy-MM-dd"), flightNo, depAirportCode, arrAirportCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            re.setState(FlightCenterStatus.ERROR.value());
            re.setMessage(FlightCenterStatus.ERROR.display());
            re.setData(null);
        }
        return JSON.toJSONString(re);
    }

    /**
     * 取消航班定制
     * 航段 + 日期 + 航班号
     *
     * @param request
     * @return
     */
    public String cancelCustomFlight(JSONObject request) {
        FlightCenterResult<FlightPo> re = new FlightCenterResult();
        try {
            String flightNo = request.getString("flightNo");
            String depDate = request.getString("depDate");// 出发日期 即航班号
            String depAirportCode = request.getString("depAirportCode");
            String arrAirportCode = request.getString("arrAirportCode");

            if (log.isInfoEnabled()) {
                log.info("【 参数   flightNo: " + flightNo + " depDate: " + depDate + " depAirportCode: " + depAirportCode + " arrAirportCode: " + arrAirportCode + " 】");
            }

            String s = verifyArgs(flightNo, depAirportCode, arrAirportCode);

            if (!"OK".equals(s)) {
                return s;
            }

            String sysCode = request.getString("sysCode");
            CustomerPo customerPo = customerPoMapper.selectBySysCode(sysCode);
            FlightPo flightPo = new FlightPo();
            flightPo.setFlightNo(flightNo);
            flightPo.setDepDate(DateUtils.stringToDate(depDate,"yyyy-MM-dd"));
            flightPo.setDepAirportCode(depAirportCode);
            flightPo.setArrAirportCode(arrAirportCode);
            List<FlightPo> flightPos = flightPoMapper.selects(flightPo);
            if(flightPos == null || flightPos.size() >1){  // 没有找到指定的航班
                re.setState(FlightCenterStatus.NONE_FLIGHT.value());
                re.setMessage(FlightCenterStatus.NONE_FLIGHT.display());
                re.setData(null);
            }else {
                customFlightPoMapper.deleteByIdBogus(customerPo.getCustomerId(),flightPos.get(0).getFlightId());
                re.setState(FlightCenterStatus.SUCCESS.value());
                re.setMessage(FlightCenterStatus.SUCCESS.display());
                re.setData(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            re.setState(FlightCenterStatus.ERROR.value());
            re.setMessage(FlightCenterStatus.ERROR.display());
            re.setData(null);
        }
        return JSON.toJSONString(re);
    }
    /**
     * 航班号 + 航班日期 -- 时刻表
     *
     * @param flightNo 航班号
     * @param depDate  航班日期
     * @return 航班中心结果集
     */
    private FlightCenterResult<List<FlightPo>> queryFlightInfo(String flightNo, String depDate) {
        FlightCenterResult<List<FlightPo>> re = new FlightCenterResult();

        FlightPo flightPo = new FlightPo();

        // 日期已经在aspect过滤
        try {
            flightPo.setFlightNo(flightNo);
            flightPo.setDepDate(DateUtils.stringToDate(depDate, "yyyy-MM-dd"));
            List<FlightPo> selects = flightPoMapper.selects(flightPo);

            boolean isToday = isTodayFlight(DateUtils.stringToDate(depDate, "yyyy-MM-dd"));

            // 本地是否存在
            if (isExistInFlightCenter(selects) && !isToday) {
                re.setMessage(FlightCenterStatus.SUCCESS.display());
                re.setState(FlightCenterStatus.SUCCESS.value());
                re.setData(selects);
            } else {
                re = isToday ? ibeService.queryFlightNo(flightNo) : ibeService.queryFlightNoByDate(flightNo, depDate);
                if (!verifyFlightCenterResult(re, true)) {
                    re.setState(FlightCenterStatus.NONE_FLIGHT.value());
                    re.setMessage(FlightCenterStatus.NONE_FLIGHT.display());
                    re.setData(null);
                    return re;
                }
                selects = flightPoMapper.selects(flightPo);
                re.setMessage(FlightCenterStatus.SUCCESS.display());
                re.setState(FlightCenterStatus.SUCCESS.value());
                re.setData(selects);
            }
        } catch (Exception e) {
            e.printStackTrace();
            re.setMessage(FlightCenterStatus.ERROR.display());
            re.setState(FlightCenterStatus.ERROR.value());
            re.setData(null);
        }
        return re;
    }

    /**
     * 航班日期 + 航段 -- 时刻表
     *
     * @param depDate        航班日期
     * @param depAirportCode 出发地三字码
     * @param arrAirportCode 目的地三字码
     * @return 航班中心结果集
     */
    private FlightCenterResult<List<FlightPo>> queryFlightInfo(String depDate, String depAirportCode, String arrAirportCode) {
        return modelLegDate(depDate, depAirportCode, arrAirportCode);
    }

    /**
     * 航班号 + 航班日期 + 航段 -- 时刻表
     *
     * @param flightNo       航班号
     * @param depDate        航班日期
     * @param depAirportCode 出发地三字码
     * @param arrAirportCode 目的地三字码
     * @return 航班中心结果集
     */
    private FlightCenterResult queryFlightInfo(String flightNo, String depDate, String depAirportCode, String arrAirportCode) {
        FlightCenterResult<List<FlightPo>> re = new FlightCenterResult();

        FlightPo flightPo = new FlightPo();

        // 日期已经在aspect过滤
        try {
            flightPo.setFlightNo(flightNo);
            flightPo.setDepDate(DateUtils.stringToDate(depDate, "yyyy-MM-dd"));
            flightPo.setDepAirportCode(depAirportCode);
            flightPo.setArrAirportCode(arrAirportCode);
            List<FlightPo> selects = flightPoMapper.selects(flightPo);

            boolean isToday = isTodayFlight(DateUtils.stringToDate(depDate, "yyyy-MM-dd"));

            // 本地是否存在
            if (isExistInFlightCenter(selects) && !isToday) {
                re.setMessage(FlightCenterStatus.SUCCESS.display());
                re.setState(FlightCenterStatus.SUCCESS.value());
                re.setData(selects);
            } else {
                re = isToday ? ibeService.queryFlightNo(flightNo) : ibeService.queryFlightNoByDate(flightNo, depDate);
                if (!verifyFlightCenterResult(re, true)) {
                    re.setState(FlightCenterStatus.NONE_FLIGHT.value());
                    re.setMessage(FlightCenterStatus.NONE_FLIGHT.display());
                    re.setData(null);
                    return re;
                }
                selects = flightPoMapper.selects(flightPo);
                re.setMessage(FlightCenterStatus.SUCCESS.display());
                re.setState(FlightCenterStatus.SUCCESS.value());
                re.setData(selects);
            }
        } catch (Exception e) {
            e.printStackTrace();
            re.setMessage(FlightCenterStatus.ERROR.display());
            re.setState(FlightCenterStatus.ERROR.value());
            re.setData(null);
        }
        return re;
    }

    /**
     * 航班中心是否存在航班记录
     *
     * @param flightPos 查询的航班信息
     * @return true 存在 false 不存在
     */
    private boolean isExistInFlightCenter(List<FlightPo> flightPos) {
        if (flightPos != null && flightPos.size() != 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否是本日航班
     *
     * @param depDate 航班日期
     * @return true 本日航班 false 未来航班（过去的日期已经在aspect过滤）
     * @throws ParseException
     */
    private boolean isTodayFlight(Date depDate) throws ParseException {
        if (depDate.equals(DateUtils.getDate("yyyy-MM-dd"))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 航班号 + 航班日期 -- 动态表
     *
     * @param flightNo 航班号
     * @param depDate  航班日期
     * @return 航班中心结果集
     */
    private FlightCenterResult<List<FlightPo>> queryDynamicFlightInfo(String flightNo, String depDate) {
        FlightCenterResult<List<FlightPo>> re = new FlightCenterResult();

        FlightPo flightPo = new FlightPo();

        boolean isCustom = true;
        try {
            flightPo.setFlightNo(flightNo);
            flightPo.setDepDate(DateUtils.stringToDate(depDate, "yyyy-MM-dd"));
            List<FlightPo> selects = flightPoMapper.selects(flightPo);

            boolean isToday = isTodayFlight(DateUtils.stringToDate(depDate, "yyyy-MM-dd"));

            if (isExistInFlightCenter(selects)) {
                for (FlightPo po : selects) {
                    if (po.getIsCustom() != 1) {
                        isCustom = false;
                        break;
                    }
                }
                if (isCustom) {
                    re.setMessage(FlightCenterStatus.SUCCESS.display());
                    re.setState(FlightCenterStatus.SUCCESS.value());
                    re.setData(selects);
                } else {
                    re = isToday ? ibeService.queryFlightNo(flightNo) : ibeService.queryFlightNoByDate(flightNo, depDate);
                    if (!verifyFlightCenterResult(re, true)) {
                        re.setState(FlightCenterStatus.NONE_FLIGHT.value());
                        re.setMessage(FlightCenterStatus.NONE_FLIGHT.display());
                        re.setData(null);
                        return re;
                    }
                    selects = flightPoMapper.selects(flightPo);
                    re.setMessage(FlightCenterStatus.SUCCESS.display());
                    re.setState(FlightCenterStatus.SUCCESS.value());
                    re.setData(selects);
                }
            } else {
                re = isToday ? ibeService.queryFlightNo(flightNo) : ibeService.queryFlightNoByDate(flightNo, depDate);
                if (!verifyFlightCenterResult(re, false)) {
                    re.setState(FlightCenterStatus.NONE_FLIGHT.value());
                    re.setMessage(FlightCenterStatus.NONE_FLIGHT.display());
                    re.setData(null);
                    return re;
                }
                selects = flightPoMapper.selects(flightPo);
                re.setMessage(FlightCenterStatus.SUCCESS.display());
                re.setState(FlightCenterStatus.SUCCESS.value());
                re.setData(selects);
            }
        } catch (Exception e) {
            e.printStackTrace();
            re.setMessage(FlightCenterStatus.ERROR.display());
            re.setState(FlightCenterStatus.ERROR.value());
            re.setData(null);
        }
        return re;
    }

    /**
     * 航班日期 + 航段 -- 动态表
     *
     * @param depDate        航班日期
     * @param depAirportCode 出发地三字码
     * @param arrAirportCode 目的地三字码
     * @return 航班中心结果集
     */
    private FlightCenterResult<List<FlightPo>> queryDynamicFlightInfo(String depDate, String depAirportCode, String arrAirportCode) {
        return modelLegDate(depDate, depAirportCode, arrAirportCode);
    }

    /**
     * 航班号 + 航班日期 + 航段 -- 动态表
     *
     * @param flightNo       航班号
     * @param depDate        航班日期
     * @param depAirportCode 出发地三字码
     * @param arrAirportCode 目的地三字码
     * @return 航班中心结果集
     */
    private FlightCenterResult queryDynamicFlightInfo(String flightNo, String depDate, String depAirportCode, String arrAirportCode) {
        FlightCenterResult<List<FlightPo>> re = new FlightCenterResult();

        FlightPo flightPo = new FlightPo();

        boolean isCustom = true;
        try {
            flightPo.setFlightNo(flightNo);
            flightPo.setDepDate(DateUtils.stringToDate(depDate, "yyyy-MM-dd"));
            flightPo.setDepAirportCode(depAirportCode);
            flightPo.setArrAirportCode(arrAirportCode);
            List<FlightPo> selects = flightPoMapper.selects(flightPo);

            boolean isToday = isTodayFlight(DateUtils.stringToDate(depDate, "yyyy-MM-dd"));

            if (isExistInFlightCenter(selects)) {
                for (FlightPo po : selects) {   // 此处就一条数据
                    if (po.getIsCustom() != 1) {
                        isCustom = false;
                        break;
                    }
                }
                if (isCustom) {
                    re.setMessage(FlightCenterStatus.SUCCESS.display());
                    re.setState(FlightCenterStatus.SUCCESS.value());
                    re.setData(selects);
                } else {
                    re = isToday ? ibeService.queryFlightNo(flightNo) : ibeService.queryFlightNoByDate(flightNo, depDate);
                    if (!verifyFlightCenterResult(re, true)) {
                        re.setState(FlightCenterStatus.NONE_FLIGHT.value());
                        re.setMessage(FlightCenterStatus.NONE_FLIGHT.display());
                        re.setData(null);
                        return re;
                    }
                    selects = flightPoMapper.selects(flightPo);
                    re.setMessage(FlightCenterStatus.SUCCESS.display());
                    re.setState(FlightCenterStatus.SUCCESS.value());
                    re.setData(selects);
                }
            } else {
                re = isToday ? ibeService.queryFlightNo(flightNo) : ibeService.queryFlightNoByDate(flightNo, depDate);
                if (!verifyFlightCenterResult(re, false)) {
                    re.setState(FlightCenterStatus.NONE_FLIGHT.value());
                    re.setMessage(FlightCenterStatus.NONE_FLIGHT.display());
                    re.setData(null);
                    return re;
                }
                selects = flightPoMapper.selects(flightPo);
                re.setMessage(FlightCenterStatus.SUCCESS.display());
                re.setState(FlightCenterStatus.SUCCESS.value());
                re.setData(selects);
            }
        } catch (Exception e) {
            e.printStackTrace();
            re.setMessage(FlightCenterStatus.ERROR.display());
            re.setState(FlightCenterStatus.ERROR.value());
            re.setData(null);
        }
        return re;
    }

    /**
     * 航段 + 日期 模式 时刻和动态 都是调用这个方法
     *
     * @param depDate        航班日期
     * @param depAirportCode 出发地三字码
     * @param arrAirportCode 目的地三字码
     * @return 航班中心结果集
     */
    private FlightCenterResult<List<FlightPo>> modelLegDate(String depDate, String depAirportCode, String arrAirportCode) {
        FlightCenterResult<List<FlightPo>> re = new FlightCenterResult();

        FlightPo flightPo = new FlightPo();
        try {
            flightPo.setDepDate(DateUtils.stringToDate(depDate, "yyyy-MM-dd"));
            flightPo.setDepAirportCode(depAirportCode);
            flightPo.setArrAirportCode(arrAirportCode);
            if (isTodayFlight(DateUtils.stringToDate(depDate, "yyyy-MM-dd"))) {
                re = ibeService.queryFlightCode(depAirportCode, arrAirportCode);
            } else {
                re = ibeService.queryFlightCodeAndDate(depAirportCode, arrAirportCode, DateUtils.stringToDate(depDate, "yyyy-MM-dd"));
            }
            if (!verifyFlightCenterResult(re, true)) {
                re.setState(FlightCenterStatus.NONE_FLIGHT.value());
                re.setMessage(FlightCenterStatus.NONE_FLIGHT.display());
                re.setData(null);
                return re;
            }
        } catch (Exception e) {
            e.printStackTrace();
            re.setMessage(FlightCenterStatus.ERROR.display());
            re.setState(FlightCenterStatus.ERROR.value());
            re.setData(null);
        }
        return re;
    }

    /**
     * 校验从第三方数据源查询数据的结果
     * @param re 航班中心的结果集
     * @param verifyLocal 校验本地是否存在
     * @return true 第三方返回航班信息 false 第三方不返回航班信息
     */
    private boolean verifyFlightCenterResult(FlightCenterResult<List<FlightPo>> re, boolean verifyLocal) throws FlightCenterException {
        try {
            if (re.getData() == null) {
                return false;
            } else {
                if (verifyLocal) {
                    for (FlightPo po : re.getData()) {

                        if (flightPoMapper.select(po) == null) {
//                        flightPoMapper.insert(po);
                            flightPoMapper.insert(perfectParams(po));
                        } else {
                            flightPoMapper.updateByCondition(po);
                        }
                    }
                } else {
                    for (FlightPo po : re.getData()) {
//                    flightPoMapper.insert(po);
                        flightPoMapper.insert(perfectParams(po));
                    }
                }
            }
        } catch (FlightCenterException e) {
            throw e;
        }
        return true;
    }

    private FlightPo perfectParams(FlightPo flightPo) throws FlightCenterException{
        try{
            JSONObject airline = queryAirline(flightPo.getFlightNo());
            JSONObject depAirport = queryAirport(flightPo.getDepAirportCode());
            JSONObject arrAirport = queryAirport(flightPo.getArrAirportCode());
            if (airline != null) {
                flightPo.setAirlineCode(airline.getJSONObject("data").getString("IATA"));
                flightPo.setAirlineName(airline.getJSONObject("data").getString("airlineName"));
            }
            if(depAirport !=null){
                flightPo.setDepAirport(depAirport.getJSONObject("data").getString("shortTitle"));
                flightPo.setDepAirportName(depAirport.getJSONObject("data").getString("name"));
                flightPo.setDepCity(depAirport.getJSONObject("data").getString("city"));
            }
            if(arrAirport !=null){
                flightPo.setArrAirport(arrAirport.getJSONObject("data").getString("shortTitle"));
                flightPo.setArrAirportName(arrAirport.getJSONObject("data").getString("name"));
                flightPo.setArrCity(arrAirport.getJSONObject("data").getString("city"));
            }
        }catch (FlightCenterException e){
            throw e;
        }
        return flightPo;
    }

    private JSONObject queryAirline(String flightNo) throws FlightCenterException{
        JSONObject object = null;
        try {
            JSONObject params = new JSONObject();
            params.put("iata", flightNo.substring(0, 2));
            Response re = ClientUtil.clientSendData(baseInfoClient, "businessService", "queryAirlineByIata",params);
            if (re != null && re.getResponeCode().getValue() == 200) {
                object = JSON.parseObject(new String(re.getResponseJSON()), Feature.OrderedField);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new FlightCenterException("服务间调用异常");
        }
        return object;
    }

    private JSONObject queryAirport(String airportCode) throws FlightCenterException{
        JSONObject object = null;
        try {
            JSONObject params = new JSONObject();
            params.put("airportCode", airportCode);
            Response re = ClientUtil.clientSendData(baseInfoClient, "businessService","queryAirportByAirportCode", params);
            if (re != null && re.getResponeCode().getValue() == 200) {
                object = JSON.parseObject(new String(re.getResponseJSON()), Feature.OrderedField);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new FlightCenterException("服务间调用异常");
        }
        return object;
    }

    /**
     * 从IBE查询航班信息集合
     *
     * @param depDate  起飞时间
     * @param flightNo 航班日期
     * @return FlightCenterResult 航班中心结果集
     * @throws Exception
     */
    private FlightCenterResult<List<FlightPo>> getFlightPosByIbeSource(Date depDate, String flightNo) throws Exception {
        Date today = DateUtils.getDate("yyyy-MM-dd");
        FlightCenterResult<List<FlightPo>> result = new FlightCenterResult<>();
        if (depDate == null || depDate.before(today)) {
            result.setState(FlightCenterStatus.FLIGHT_DATE_INVALID.value());
            result.setMessage(FlightCenterStatus.FLIGHT_DATE_INVALID.display());
            result.setData(null);
        }
        if (depDate.after(today)) {
            result = ibeService.queryFlightNoByDate(flightNo, DateUtils.dateToString(depDate, "yyyy-MM-dd"));
        }
        if (depDate.equals(today)) {
            result = ibeService.queryFlightNo(flightNo);
        }
        return result;
    }

    private FlightCenterResult<FlightPo> getFlightPoCustomByIbeSource(CustomerPo customerPo, Date depDate, String flightNo, String depAirportCode, String arrAirportCode) throws Exception {
        FlightCenterResult<List<FlightPo>> re = getFlightPosByIbeSource(depDate, flightNo);
        FlightCenterResult<FlightPo> result = new FlightCenterResult<>();
        boolean isExist = false;
        for (FlightPo flightPo : re.getData()) {
            // 存在航班信息
            if (depAirportCode.equals(flightPo.getDepAirportCode()) && arrAirportCode.equals(flightPo.getArrAirportCode())) {
                isExist = true;
                flightPo.setIsCustom((short) 1);
                flightPoMapper.insert(flightPo);
                CustomFlightPo customFlightPo = new CustomFlightPo();
                customFlightPo.setCustomUrl(customerPo.getCustomUrl());
                customFlightPo.setCustomerId(customerPo.getCustomerId());
                customFlightPo.setFlightId(flightPo.getFlightId());
                customFlightPoMapper.insert(customFlightPo);
                ibeService.executeCustomFlight(flightNo, depDate);
                result.setState(FlightCenterStatus.SUCCESS.value());
                result.setMessage(FlightCenterStatus.SUCCESS.display());
                result.setData(flightPo);
            } else {
                flightPoMapper.insert(flightPo);
            }
            if (!isExist) {
                result.setState(FlightCenterStatus.NONE_FLIGHT.value());
                result.setMessage(FlightCenterStatus.NONE_FLIGHT.display());
                result.setData(null);
            }
        }
        return result;
    }

    private String verifyArgs(String flightNo, String depAirportCode, String arrAirportCode) {
        String result;

        result = verifyFlightNo(flightNo);

        result = result.equals("OK") ? "OK" : result;

        if (!"OK".equals(result)) {
            return result;
        }

        result = verifyDepAirportCode(depAirportCode);

        result = result.equals("OK") ? "OK" : result;

        if (!"OK".equals(result)) {
            return result;
        }

        result = verifyArrAirportCode(arrAirportCode);

        result = result.equals("OK") ? "OK" : result;

        if (!"OK".equals(result)) {
            return result;
        }

        return result;
    }

    private String verifyFlightNo(String flightNo) {
        FlightCenterResult re = new FlightCenterResult();
        if (StringUtils.isBlank(flightNo)) {
            re.setMessage(FlightCenterStatus.NONE_FLIGHT_NO.display());
            re.setState(FlightCenterStatus.NONE_FLIGHT_NO.value());
            re.setData(null);
            return JSON.toJSONString(re);
        } else {
            return "OK";
        }
    }

    private String verifyDepAirportCode(String depAirportCode) {
        FlightCenterResult re = new FlightCenterResult();
        if (StringUtils.isBlank(depAirportCode)) {
            re.setMessage(FlightCenterStatus.NONE_DEP_AIRPORT_CODE.display());
            re.setState(FlightCenterStatus.NONE_DEP_AIRPORT_CODE.value());
            re.setData(null);
            return JSON.toJSONString(re);
        } else {
            return "OK";
        }
    }

    private String verifyArrAirportCode(String arrAirportCode) {
        FlightCenterResult re = new FlightCenterResult();
        if (StringUtils.isBlank(arrAirportCode)) {
            re.setMessage(FlightCenterStatus.NONE_ARR_AIRPORT_CODE.display());
            re.setState(FlightCenterStatus.NONE_ARR_AIRPORT_CODE.value());
            re.setData(null);
            return JSON.toJSONString(re);
        } else {
            return "OK";
        }
    }

}
