package com.juan.adx.api.converter;

import java.time.Instant;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.juan.adx.model.entity.api.AdSlotBudgetWrap;
import com.juan.adx.model.entity.api.ConvertSspParam;
import com.juan.adx.model.enums.CooperationMode;
import com.juan.adx.model.ssp.common.request.SspReqApp;
import com.juan.adx.model.ssp.common.request.SspReqDeal;
import com.juan.adx.model.ssp.common.request.SspReqDevice;
import com.juan.adx.model.ssp.common.request.SspReqDeviceCaid;
import com.juan.adx.model.ssp.common.request.SspReqDeviceId;
import com.juan.adx.model.ssp.common.request.SspReqExt;
import com.juan.adx.model.ssp.common.request.SspReqGeo;
import com.juan.adx.model.ssp.common.request.SspReqNetwork;
import com.juan.adx.model.ssp.common.request.SspReqSlot;
import com.juan.adx.model.ssp.common.request.SspReqUser;
import com.juan.adx.model.ssp.common.request.SspRequestParam;

public abstract class AbstractDspRequestParamConvert {


    public SspRequestParam sspRequestParam;
    public AdSlotBudgetWrap adSlotBudgetWrap;
    public CooperationMode mode;

    public SspReqSlot slot;
    public SspReqDeal deal;
    public SspReqApp app;
    public SspReqUser user;
    public SspReqDevice device;
    public SspReqDeviceId deviceId;
    public SspReqDeviceCaid caId;
    public SspReqNetwork network;
    public SspReqGeo geo;
    public SspReqExt ext;

    public AbstractDspRequestParamConvert(ConvertSspParam convertSspParam) {
        this.sspRequestParam = convertSspParam.getSspRequestParam();
        this.adSlotBudgetWrap = convertSspParam.getAdSlotBudgetWrap();
        this.mode = convertSspParam.getMode();

        this.slot = Objects.nonNull(sspRequestParam) && Objects.nonNull(sspRequestParam.getSlot()) ? sspRequestParam.getSlot() : new SspReqSlot();
        this.deal = Objects.nonNull(sspRequestParam) && Objects.nonNull(sspRequestParam.getDeal()) ? sspRequestParam.getDeal() : new SspReqDeal();
        this.app = Objects.nonNull(sspRequestParam) && Objects.nonNull(sspRequestParam.getApp()) ? sspRequestParam.getApp() : new SspReqApp();
        this.user = Objects.nonNull(sspRequestParam) && Objects.nonNull(sspRequestParam.getUser()) ? sspRequestParam.getUser() : new SspReqUser();
        this.device = Objects.nonNull(sspRequestParam) && Objects.nonNull(sspRequestParam.getDevice()) ? sspRequestParam.getDevice() : new SspReqDevice();
        this.deviceId = Objects.nonNull(sspRequestParam) && Objects.nonNull(sspRequestParam.getDeviceId()) ? sspRequestParam.getDeviceId() : new SspReqDeviceId();
        this.caId = Objects.nonNull(sspRequestParam) && Objects.nonNull(sspRequestParam.getCaId()) ? sspRequestParam.getCaId() : new SspReqDeviceCaid();
        this.network = Objects.nonNull(sspRequestParam) && Objects.nonNull(sspRequestParam.getNetwork()) ? sspRequestParam.getNetwork() : new SspReqNetwork();
        this.geo = Objects.nonNull(sspRequestParam) && Objects.nonNull(sspRequestParam.getGeo()) ? sspRequestParam.getGeo() : new SspReqGeo();
        this.ext = Objects.nonNull(sspRequestParam) && Objects.nonNull(sspRequestParam.getExt()) ? sspRequestParam.getExt() : new SspReqExt();
    }

    public String getPackageName() {
        String pkgName = StringUtils.isNotBlank(this.adSlotBudgetWrap.getPackageName())
                ? this.adSlotBudgetWrap.getPackageName()
                : this.app.getPkgName();
        return pkgName;
    }

    public Integer getSspFloorPrice() {
        if (this.deal != null && this.deal.getBidfloor() != null) {
            return this.deal.getBidfloor();
        }
        return this.adSlotBudgetWrap.getSspFloorPrice();
    }


    /**
     * 将时间戳（毫秒）格式化为有道文档时间格式
     * 如:”1596270702.486691”, 整数部分精确到 s, 小数部分精确到 ns
     *
     * @param timeMillis
     * @return
     */
    public String getYouDaoStringTime(String timeMillis) {
        if(StringUtils.isBlank(timeMillis)){
            return null;
        }
        String youDaoFormatTime = null;
        try {
            long milliseconds = Long.parseLong(timeMillis);
            // 将毫秒转换为秒和纳秒
            long seconds = milliseconds / 1000;
            int nanos = (int) ((milliseconds % 1000) * 1_000_000);
            Instant instant = Instant.ofEpochSecond(seconds, nanos);
            // 格式化时间为字符串
            youDaoFormatTime = String.format("%d.%09d", instant.getEpochSecond(), instant.getNano());
        } catch (NumberFormatException e) {
            return null;
        }
        return youDaoFormatTime;
    }
    
}
