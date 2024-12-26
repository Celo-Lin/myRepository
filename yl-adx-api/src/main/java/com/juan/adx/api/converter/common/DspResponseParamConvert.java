package com.juan.adx.api.converter.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.LongAdder;

import com.juan.adx.common.utils.LogPrintCheckUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson2.JSON;
import com.juan.adx.api.converter.AbstractDspResponseParamConvert;
import com.juan.adx.model.entity.api.ConvertDspParam;
import com.juan.adx.model.ssp.common.response.SspRespAdInfo;
import com.juan.adx.model.ssp.common.response.SspRespTrack;
import com.juan.adx.model.ssp.common.response.SspResponseParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DspResponseParamConvert extends AbstractDspResponseParamConvert {


    public DspResponseParamConvert(ConvertDspParam convertDspParam) {
        super(convertDspParam);
    }

    public static LongAdder longAdder = new LongAdder();

    public List<SspRespAdInfo> convert() {
        if (StringUtils.isBlank(this.dspRespData)) {
            if (LogPrintCheckUtils.needPrintLog(longAdder, 100)) {
                log.warn("longAdder：{}，{} 响应数据为空，响应数据：{} ",longAdder.sum(), this.dsp.getDesc(), this.dspRespData);
            }
            return null;
        }

        SspResponseParam dspResponseParam = JSON.parseObject(this.dspRespData, SspResponseParam.class);
        if (dspResponseParam == null) {
            if (LogPrintCheckUtils.needPrintLog(longAdder, 100)) {
                log.warn("longAdder：{}，{} 响应的JSON数据转换为JAVA对象为空，响应数据：{} ",longAdder.sum(), this.dsp.getDesc(), this.dspRespData);
            }
            return null;
        }

        if (!Objects.equals(200, dspResponseParam.getCode())) {
            if (LogPrintCheckUtils.needPrintLog(longAdder, 100)) {
                log.warn("longAdder：{}，{} 响应异常状态码，响应数据：{} ",longAdder.sum(), this.dsp.getDesc(), this.dspRespData);
            }
            return null;
        }

        List<SspRespAdInfo> sspRespAdInfos = dspResponseParam.getData();

        if (CollectionUtils.isEmpty(sspRespAdInfos)) {
            if (LogPrintCheckUtils.needPrintLog(longAdder, 100)) {
                log.warn("{} 响应json结构中data为空，响应数据：{} ", this.dsp.getDesc(), this.dspRespData);
            }
            return null;
        }

        for (SspRespAdInfo sspRespAdInfo : sspRespAdInfos) {

            SspRespTrack sspRespTrack = this.getSspRespTrack(sspRespAdInfo);

            sspRespAdInfo.setTrack(sspRespTrack);
        }

        return sspRespAdInfos;
    }

    private SspRespTrack getSspRespTrack(SspRespAdInfo sspRespAdInfo) {

        SspRespTrack dspRespTrack = Optional.ofNullable(sspRespAdInfo.getTrack()).orElseGet(SspRespTrack::new);

        //解析DSP返回的监控链接，并将链接中的宏替换为平台宏
        List<String> showUrls = dspRespTrack.getShowUrls();
        List<String> clickUrls = dspRespTrack.getClickUrls();
        List<String> finishDownloadUrls = dspRespTrack.getFinishDownloadUrls();
        List<String> finishInstallUrls = dspRespTrack.getFinishInstallUrls();
        List<String> deeplinkSuccessUrls = dspRespTrack.getDeeplinkSuccessUrls();


        //组装平台自己的事件上报链接
        showUrls = Optional.ofNullable(showUrls).orElseGet(ArrayList::new);
        clickUrls = Optional.ofNullable(clickUrls).orElseGet(ArrayList::new);
        finishDownloadUrls = Optional.ofNullable(finishDownloadUrls).orElseGet(ArrayList::new);
        finishInstallUrls = Optional.ofNullable(finishInstallUrls).orElseGet(ArrayList::new);
        deeplinkSuccessUrls = Optional.ofNullable(deeplinkSuccessUrls).orElseGet(ArrayList::new);
        this.fillReportUrls(showUrls, clickUrls, finishDownloadUrls, finishInstallUrls, deeplinkSuccessUrls);


        List<String> winUrls = sspRespAdInfo.getWinNoticeUrl();
        if (CollectionUtils.isNotEmpty(winUrls)) {
            showUrls.addAll(winUrls);
        }


        dspRespTrack.setShowUrls(showUrls);
        dspRespTrack.setClickUrls(clickUrls);
        dspRespTrack.setFinishDownloadUrls(finishDownloadUrls);
        dspRespTrack.setFinishInstallUrls(finishInstallUrls);
        dspRespTrack.setDeeplinkSuccessUrls(deeplinkSuccessUrls);

        SspRespTrack sspRespTrack = new SspRespTrack();
        sspRespTrack.setShowUrls(showUrls);
        sspRespTrack.setClickUrls(clickUrls);
        sspRespTrack.setAdLoadUrls(dspRespTrack.getAdLoadUrls());
        sspRespTrack.setAdSkipUrls(dspRespTrack.getAdSkipUrls());
        sspRespTrack.setAdCloseUrls(dspRespTrack.getAdCloseUrls());
        sspRespTrack.setWechatOpenUrls(dspRespTrack.getWechatOpenUrls());

        sspRespTrack.setStartDownloadUrls(dspRespTrack.getStartDownloadUrls());
        sspRespTrack.setFinishDownloadUrls(finishDownloadUrls);
        sspRespTrack.setStartInstallUrls(dspRespTrack.getStartInstallUrls());
        sspRespTrack.setFinishInstallUrls(finishInstallUrls);
        sspRespTrack.setActiveAppUrls(dspRespTrack.getActiveAppUrls());
        sspRespTrack.setDeeplinkTryUrls(dspRespTrack.getDeeplinkTryUrls());
        sspRespTrack.setDeeplinkSuccessUrls(deeplinkSuccessUrls);
        sspRespTrack.setDeeplinkFailureUrls(dspRespTrack.getDeeplinkFailureUrls());
        sspRespTrack.setDeeplinkClickUrls(dspRespTrack.getDeeplinkClickUrls());
        sspRespTrack.setDeeplinkInstalledkUrls(dspRespTrack.getDeeplinkInstalledkUrls());
        sspRespTrack.setDeeplinkUninstallkUrls(dspRespTrack.getDeeplinkUninstallkUrls());

        sspRespTrack.setVideoStartUrls(dspRespTrack.getVideoStartUrls());
        sspRespTrack.setVideoClickUrls(dspRespTrack.getVideoClickUrls());
        sspRespTrack.setVideoCompleteUrls(dspRespTrack.getVideoCompleteUrls());
        sspRespTrack.setVideoFailUrls(dspRespTrack.getVideoFailUrls());
        sspRespTrack.setVideoCloseUrls(dspRespTrack.getVideoCloseUrls());
        sspRespTrack.setVideoSkipUrls(dspRespTrack.getVideoSkipUrls());
        sspRespTrack.setVideoPauseUrls(dspRespTrack.getVideoPauseUrls());
        sspRespTrack.setVideoResumeUrls(dspRespTrack.getVideoResumeUrls());
        sspRespTrack.setVideoReplayUrls(dspRespTrack.getVideoReplayUrls());
        sspRespTrack.setVideoMuteUrls(dspRespTrack.getVideoMuteUrls());
        sspRespTrack.setVideoUnmuteUrls(dspRespTrack.getVideoUnmuteUrls());
        sspRespTrack.setVideoFullscreenUrls(dspRespTrack.getVideoFullscreenUrls());
        sspRespTrack.setVideoExitFullscreenUrls(dspRespTrack.getVideoExitFullscreenUrls());
        sspRespTrack.setVideoUpscrollUrls(dspRespTrack.getVideoUpscrollUrls());
        sspRespTrack.setVideoDownscrollUrls(dspRespTrack.getVideoDownscrollUrls());
        sspRespTrack.setVideoQuartileUrls(dspRespTrack.getVideoQuartileUrls());
        sspRespTrack.setVideoHalfUrls(dspRespTrack.getVideoHalfUrls());
        sspRespTrack.setVideoThreeQuartileUrls(dspRespTrack.getVideoThreeQuartileUrls());
        return sspRespTrack;
    }

}
