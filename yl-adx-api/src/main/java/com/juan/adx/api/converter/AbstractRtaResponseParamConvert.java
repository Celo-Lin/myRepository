package com.juan.adx.api.converter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson2.JSON;
import com.juan.adx.api.config.ApiParameterConfig;
import com.juan.adx.api.context.TraceContext;
import com.juan.adx.common.alg.base64.Base64Util;
import com.juan.adx.model.entity.api.AdSlotBudgetWrap;
import com.juan.adx.model.entity.api.ConvertDspRtaParam;
import com.juan.adx.model.entity.api.ReportLinkParam;
import com.juan.adx.model.enums.CooperationModeSimple;
import com.juan.adx.model.ssp.common.response.SspRespAdInfo;
import com.juan.adx.model.ssp.common.response.SspRespImage;
import com.juan.adx.model.ssp.common.response.SspRespTrack;
import com.juan.adx.model.ssp.common.response.SspRespVideo;

public abstract class AbstractRtaResponseParamConvert {
	

    protected AdSlotBudgetWrap adSlotBudgetWrap;
	
    protected CooperationModeSimple modeSimple;
    
    
    
	
	public AbstractRtaResponseParamConvert(ConvertDspRtaParam convertDspRtaParam) {
        this.adSlotBudgetWrap = convertDspRtaParam.getAdSlotBudgetWrap();
        
        this.modeSimple = convertDspRtaParam.getModeSimple();
	}

	
	public void setRtaTrack(SspRespAdInfo sspRespAdInfo){

        String traceId = TraceContext.getTraceIdByContext();
        ReportLinkParam reportLinkParam = new ReportLinkParam();
        reportLinkParam.setTraceId(Long.parseLong(traceId));
        reportLinkParam.setSlotId(this.adSlotBudgetWrap.getSlotId());
        reportLinkParam.setBudgetId(this.adSlotBudgetWrap.getBudgetId());
        reportLinkParam.setMod(this.modeSimple.getMode());
        String baseData = JSON.toJSONString(reportLinkParam);
        String reportParamStr = Base64Util.encodeWithKeyUrlSafe(baseData, ApiParameterConfig.base64EncryptKey);


        SspRespTrack respTrack = new SspRespTrack();

        List<String> showUrls = new ArrayList<>();
        String metooShowUrl = String.format(ApiParameterConfig.adxReportDisplayUrl, reportParamStr, this.adSlotBudgetWrap.getSlotId());
        showUrls.add(metooShowUrl);
        respTrack.setShowUrls(showUrls);

        List<String> clickUrls = new ArrayList<>();
        String metooClickUrl = String.format(ApiParameterConfig.adxReportClickUrl, reportParamStr, this.adSlotBudgetWrap.getSlotId());
        clickUrls.add(metooClickUrl);
        respTrack.setClickUrls(clickUrls);

        List<String> elEndUrls = new ArrayList<>();
        String metooDownloadUrl = String.format(ApiParameterConfig.adxReportDownloadUrl, reportParamStr, this.adSlotBudgetWrap.getSlotId());
        elEndUrls.add(metooDownloadUrl);
        respTrack.setFinishDownloadUrls(elEndUrls);

        List<String> finishInstallUrls = new ArrayList<>();
        String metooInstallUrl = String.format(ApiParameterConfig.adxReportInstallUrl, reportParamStr, this.adSlotBudgetWrap.getSlotId());
        finishInstallUrls.add(metooInstallUrl);
        respTrack.setFinishInstallUrls(finishInstallUrls);


        List<String> deeplinkSuccessUrls = new ArrayList<>();
        String metooDpUrl = String.format(ApiParameterConfig.adxReportDpUrl, reportParamStr, this.adSlotBudgetWrap.getSlotId());
        deeplinkSuccessUrls.add(metooDpUrl);
        respTrack.setDeeplinkSuccessUrls(deeplinkSuccessUrls);

        sspRespAdInfo.setTrack(respTrack);

    }

	public void paddingRtaAdInfo(SspRespAdInfo sspRespAdInfo,int resultAd){

        List<SspRespImage> sspRespImages = new ArrayList<SspRespImage>();

        // 只有单个图片
        SspRespImage sspRespImage = new SspRespImage();
        sspRespImage.setUrl(adSlotBudgetWrap.getPictureUrl());
        sspRespImages.add(sspRespImage);
        sspRespAdInfo.setImages(sspRespImages);

        sspRespAdInfo.setTitle(adSlotBudgetWrap.getBudgetTitle());
        sspRespAdInfo.setLandingPageUrl(adSlotBudgetWrap.getH5link());

        //
        if(1==resultAd){ //resultAd ！= 0 是快手rta
            // ---只发下载
            if(StringUtils.isNotBlank(adSlotBudgetWrap.getDownloadUrl())){
            	sspRespAdInfo.setInteractionType(1);
            }
            sspRespAdInfo.setDownloadUrl(adSlotBudgetWrap.getDownloadUrl());

        }else if(2==resultAd){ // ---只发deeplink
            if(StringUtils.isNotBlank(adSlotBudgetWrap.getDeeplink())){
            	sspRespAdInfo.setInteractionType(2);
            }
            sspRespAdInfo.setDeeplink(adSlotBudgetWrap.getDeeplink());

        }else { //百度rta  下载和deep可以都下发
            if(StringUtils.isNotBlank(adSlotBudgetWrap.getDeeplink())){
            	sspRespAdInfo.setInteractionType(2);
            }
            sspRespAdInfo.setDeeplink(adSlotBudgetWrap.getDeeplink());
            sspRespAdInfo.setDownloadUrl(adSlotBudgetWrap.getDownloadUrl());
        }
        //添加视频链接
        SspRespVideo video = new SspRespVideo();
        video.setUrl(adSlotBudgetWrap.getVideoUrl());
        sspRespAdInfo.setVideo(video);
    }
}
