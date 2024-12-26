package com.juan.adx.api.converter;

import java.util.List;
import java.util.Objects;

import com.alibaba.fastjson2.JSON;
import com.juan.adx.api.config.ApiParameterConfig;
import com.juan.adx.api.context.TraceContext;
import com.juan.adx.common.alg.base64.Base64Util;
import com.juan.adx.model.entity.api.AdSlotBudgetWrap;
import com.juan.adx.model.entity.api.ConvertDspParam;
import com.juan.adx.model.entity.api.ReportLinkParam;
import com.juan.adx.model.enums.CooperationMode;
import com.juan.adx.model.enums.CooperationModeSimple;
import com.juan.adx.model.enums.Dsp;
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
import org.apache.commons.lang3.StringUtils;

public abstract class AbstractDspResponseParamConvert {
	
	public String dspRespData;

	public AdSlotBudgetWrap adSlotBudgetWrap;

	public String sspRequestId;

	public CooperationMode mode;

	public CooperationModeSimple modeSimple;
	
	public Dsp dsp;
    
    
    public SspRequestParam sspRequestParam;
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
    
    
	
	public AbstractDspResponseParamConvert(ConvertDspParam convertDspParam) {

		this.dspRespData = convertDspParam.getDspRespData();
        this.adSlotBudgetWrap = convertDspParam.getAdSlotBudgetWrap();
        this.sspRequestId = convertDspParam.getSspRequestId();
        this.mode = convertDspParam.getMode();
        this.modeSimple = convertDspParam.getModeSimple();
        this.dsp = convertDspParam.getDsp();
        
        this.sspRequestParam = convertDspParam.getSspRequestParam();
        this.slot = Objects.nonNull(sspRequestParam) && Objects.nonNull(sspRequestParam.getSlot()) ? sspRequestParam.getSlot() : new SspReqSlot();
        this.deal = Objects.nonNull(sspRequestParam) && Objects.nonNull(sspRequestParam.getDeal()) ? sspRequestParam.getDeal() : new SspReqDeal();
        this.app = Objects.nonNull(sspRequestParam) && Objects.nonNull(sspRequestParam.getApp()) ? sspRequestParam.getApp() : new SspReqApp();
        this.user = Objects.nonNull(sspRequestParam) && Objects.nonNull(sspRequestParam.getUser()) ? sspRequestParam.getUser() : null;
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


    protected void fillReportUrls(List<String > showUrls, List<String> clickUrls, List<String> finishDownloadUrls,
			List<String> finishInstallUrls, List<String> deeplinkSuccessUrls) {
		String traceId = TraceContext.getTraceIdByContext();
        ReportLinkParam reportLinkParam = new ReportLinkParam();
        reportLinkParam.setTraceId(Long.parseLong(traceId));
        reportLinkParam.setSlotId(this.adSlotBudgetWrap.getSlotId());
        reportLinkParam.setBudgetId(this.adSlotBudgetWrap.getBudgetId());
        reportLinkParam.setMod(this.modeSimple.getMode());
        reportLinkParam.setWinUrl(null);
        String baseData = JSON.toJSONString(reportLinkParam);
        String reportParamStr = Base64Util.encodeWithKeyUrlSafe(baseData, ApiParameterConfig.base64EncryptKey);
        
        String adxShowUrl = String.format(ApiParameterConfig.adxReportDisplayUrl, reportParamStr, this.adSlotBudgetWrap.getSlotId());
        String adxClickUrl = String.format(ApiParameterConfig.adxReportClickUrl, reportParamStr, this.adSlotBudgetWrap.getSlotId());
        String adxDownloadUrl = String.format(ApiParameterConfig.adxReportDownloadUrl, reportParamStr, this.adSlotBudgetWrap.getSlotId());
        String adxInstallUrl = String.format(ApiParameterConfig.adxReportInstallUrl, reportParamStr, this.adSlotBudgetWrap.getSlotId());
        String adxDeeplinkUrl = String.format(ApiParameterConfig.adxReportDpUrl, reportParamStr, this.adSlotBudgetWrap.getSlotId());
        
        
        showUrls.add(adxShowUrl);
        clickUrls.add(adxClickUrl);
        finishDownloadUrls.add(adxDownloadUrl);
        finishInstallUrls.add(adxInstallUrl);
        deeplinkSuccessUrls.add(adxDeeplinkUrl);
	}

}
