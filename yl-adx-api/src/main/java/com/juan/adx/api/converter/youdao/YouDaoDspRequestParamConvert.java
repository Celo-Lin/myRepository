package com.juan.adx.api.converter.youdao;

import com.juan.adx.api.converter.AbstractDspRequestParamConvert;
import com.juan.adx.common.alg.MD5Util;
import com.juan.adx.common.utils.NumberUtils;
import com.juan.adx.common.utils.ParamUtils;
import com.juan.adx.model.dsp.oppo.enums.OppoNetworkType;
import com.juan.adx.model.dsp.youdao.enums.YouDaoLlp;
import com.juan.adx.model.dsp.youdao.enums.YouDaoMnc;
import com.juan.adx.model.dsp.youdao.enums.YouDaoNetworkType;
import com.juan.adx.model.dsp.youdao.enums.YouDaoOrientation;
import com.juan.adx.model.dsp.youdao.request.YouDaoDspRequestParam;
import com.juan.adx.model.dsp.yueke.request.YueKeDspRequestParam;
import com.juan.adx.model.entity.api.ConvertSspParam;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.common.returnsreceiver.qual.This;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.stream.Collectors;

/**
 * 有道请求参数转换
 *
 * @author： HeWen Zhou
 * @date： 2024/5/15 16:52
 */
@Slf4j
public class YouDaoDspRequestParamConvert extends AbstractDspRequestParamConvert {


    public YouDaoDspRequestParamConvert(ConvertSspParam convertSspParam) {
        super(convertSspParam);
    }

    public YouDaoDspRequestParam convert() {
        if (!this.slot.getType().equals(this.adSlotBudgetWrap.getBudgetType())) {
            log.warn("{} 广告类型不匹配,传参广告类型为{}，adx配置广告类型为{}", this.adSlotBudgetWrap.getBudgetName(),this.slot.getType(),this.adSlotBudgetWrap.getBudgetType());
            return null;
        }
        YouDaoDspRequestParam youDaoDspRequestParam = new YouDaoDspRequestParam();
        getRequiredParams(youDaoDspRequestParam);
        getOptionalParams(youDaoDspRequestParam);
        return youDaoDspRequestParam;
    }

    /**
     * 必填参数
     *
     * @param youDaoDspRequestParam
     */
    private void getRequiredParams(YouDaoDspRequestParam youDaoDspRequestParam) {
        YouDaoNetworkType networkType = YouDaoNetworkType.get(this.network.getNetworkType());

        youDaoDspRequestParam.setId(this.adSlotBudgetWrap.getDspSlotId());
        youDaoDspRequestParam.setAv(this.app.getVerName());
        youDaoDspRequestParam.setCt(networkType.getDspType());
        youDaoDspRequestParam.setDct(networkType.getSubDspType());

        youDaoDspRequestParam.setUdid(this.deviceId.getIdfa() == null ? this.deviceId.getAndroidId() : this.deviceId.getIdfa());
        if (this.deviceId.getIdfaMd5() != null) {
            youDaoDspRequestParam.setIdfa_md5(this.deviceId.getIdfaMd5().toUpperCase());
        }
        if (this.deviceId.getAndroidId() != null) {
            youDaoDspRequestParam.setAuidmd5(MD5Util.getMD5UpperString(this.deviceId.getAndroidId().toUpperCase()));
        }

        youDaoDspRequestParam.setImei(this.deviceId.getImei());
        if (this.deviceId.getImeiMd5() != null) {
            youDaoDspRequestParam.setImeimd5(this.deviceId.getImeiMd5().toUpperCase());
        }
        youDaoDspRequestParam.setAaid(null);
        youDaoDspRequestParam.setOaid(this.deviceId.getOaid());
        if (this.deviceId.getOaidMd5() != null) {
            youDaoDspRequestParam.setOaid_md5(this.deviceId.getOaidMd5().toUpperCase());
        }
        youDaoDspRequestParam.setIdfv(this.deviceId.getIdfv());
        if (this.caId.getCaid() != null) {
            youDaoDspRequestParam.setCaid(this.caId.getCaid() + "_" + (this.caId.getVersion() == null ? "0" : this.caId.getVersion()));
            youDaoDspRequestParam.setCaid_md5(MD5Util.getMD5UpperString(this.caId.getCaid().toUpperCase()) + "_" + (this.caId.getVersion() == null ? "0" : this.caId.getVersion()));
        }
        youDaoDspRequestParam.setRip(this.network.getIp() == null ? this.network.getIpv6() : this.network.getIp());

        if (this.geo.getLatitude() != null && this.geo.getLongitude() != null) {
            youDaoDspRequestParam.setLl(this.geo.getLatitude() + "," + this.geo.getLongitude());
            String lla = ParamUtils.calculateAccuracy(this.geo.getLatitude()) + "米/" + ParamUtils.calculateAccuracy(this.geo.getLongitude()) + "米";
            youDaoDspRequestParam.setLla(lla);
            if (this.geo.getTimestamp() != null) {
                String llt = String.valueOf((System.currentTimeMillis() - this.geo.getTimestamp()) / 60000);
                youDaoDspRequestParam.setLlt(llt);
            }
        }
        if (this.geo.getCoordinateType() != null) {
            YouDaoLlp youDaoLlp = YouDaoLlp.get(this.geo.getCoordinateType());
            youDaoDspRequestParam.setLlp(youDaoLlp.getDspType());
        }

        youDaoDspRequestParam.setWifi((this.network.getBssid() == null ? "" : this.network.getBssid()) + "," + (this.network.getSsid() == null ? "" : this.network.getSsid()));

    }

    /**
     * 可选参数
     *
     * @param youDaoDspRequestParam
     */
    private void getOptionalParams(YouDaoDspRequestParam youDaoDspRequestParam) {

        youDaoDspRequestParam.setS(null);//加密
        youDaoDspRequestParam.setYdet(0);
        youDaoDspRequestParam.setDn(this.device.getMake() + "," + this.device.getModel());
        youDaoDspRequestParam.setZ(this.device.getTimeZone());
        youDaoDspRequestParam.setIsSecure(null);
        youDaoDspRequestParam.setOsv(this.device.getOsVersion());

        YouDaoOrientation orientation = YouDaoOrientation.get(this.device.getOrientation());
        youDaoDspRequestParam.setO(orientation.getDspType());

        youDaoDspRequestParam.setMcc(this.network.getMcc());

        YouDaoMnc mnc = YouDaoMnc.get(this.network.getCarrier());
        youDaoDspRequestParam.setMnc(mnc.getDspType());
        youDaoDspRequestParam.setIso(this.network.getCountry());
        youDaoDspRequestParam.setCn(mnc.getDspName());
        youDaoDspRequestParam.setLac(null);
        youDaoDspRequestParam.setCid(null);
        youDaoDspRequestParam.setRan(1);
        youDaoDspRequestParam.setCids(null);
        youDaoDspRequestParam.setIsrd(null);
        youDaoDspRequestParam.setSc_h(this.device.getHeight());
        youDaoDspRequestParam.setSc_w(this.device.getWidth());
        youDaoDspRequestParam.setSc_a(null);
        youDaoDspRequestParam.setSc_ppi(this.device.getPpi());
        youDaoDspRequestParam.setUser_id(null);
        youDaoDspRequestParam.setM_age(this.user.getAge());
        youDaoDspRequestParam.setM_gender(this.user.getGender());

        if (this.user.getInterest() != null && !this.user.getInterest().isEmpty()) {
            youDaoDspRequestParam.setPor_words(String.join(",", this.user.getInterest()));
        }
        youDaoDspRequestParam.setPhone_name(this.device.getDeviceName());
        if (this.device.getSysStartupTime() != null) {
            long sysStartupTime = Long.parseLong(this.device.getSysStartupTime());
            youDaoDspRequestParam.setPwot(System.currentTimeMillis() - sysStartupTime);
        }
        youDaoDspRequestParam.setImsi(this.device.getImsi());
        youDaoDspRequestParam.setRomv(this.device.getRomVersion());
        youDaoDspRequestParam.setSysct(getYouDaoStringTime(this.device.getSysCompilingTime()));
        youDaoDspRequestParam.setDlg(this.device.getLanguage());
        youDaoDspRequestParam.setDst(getYouDaoStringTime(this.device.getSysStartupTime()));
        youDaoDspRequestParam.setDut(getYouDaoStringTime(this.device.getSysUpdateTime()));
        youDaoDspRequestParam.setCpum(this.device.getCpuNum() == null ? null : this.device.getCpuNum().toString());
        youDaoDspRequestParam.setRom_t(this.device.getSysDiskSize() == null ? null : this.device.getSysDiskSize().toString());
        youDaoDspRequestParam.setRam_t(this.device.getSysMemorySize() == null ? null : this.device.getSysMemorySize().toString());
        youDaoDspRequestParam.setIosauth(null);
        youDaoDspRequestParam.setDevice_type(this.device.getHardwareModel());
        youDaoDspRequestParam.setDsid(null);
        youDaoDspRequestParam.setSpt_mkt("1");
        youDaoDspRequestParam.setBidFloor(getSspFloorPrice());

        youDaoDspRequestParam.setIsu(this.device.getSupportUniversal().toString());
        youDaoDspRequestParam.setHwidv(this.device.getHmsVersion());
        youDaoDspRequestParam.setRstyleID(null);
        youDaoDspRequestParam.setMktv(null);
    }


}
