package com.juan.adx.api.converter.youdao;

import com.alibaba.fastjson2.JSON;
import com.juan.adx.api.converter.AbstractDspResponseParamConvert;
import com.juan.adx.common.alg.MD5Util;
import com.juan.adx.common.utils.LogPrintCheckUtils;
import com.juan.adx.model.dsp.oppo.response.*;
import com.juan.adx.model.dsp.youdao.enums.YouDaoYdAdType;
import com.juan.adx.model.dsp.youdao.response.*;
import com.juan.adx.model.dsp.yueke.enums.YueKeOsType;
import com.juan.adx.model.dsp.yueke.response.YueKeBid;
import com.juan.adx.model.entity.api.ConvertDspParam;
import com.juan.adx.model.enums.*;
import com.juan.adx.model.ssp.common.response.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import java.util.*;
import java.util.concurrent.atomic.LongAdder;

/**
 * 有道返回参数转换
 *
 * @author： HeWen Zhou
 * @date： 2024/5/15 16:53
 */
@Slf4j
public class YouDaoDspResponseParamConvert extends AbstractDspResponseParamConvert {

    private final String DSP_NAME = this.dsp.getDesc();
    private final Dsp DSP = this.dsp;

    private final static String MACRO_PRICE = "_YD_WIN_PRICE_ ";
    public static LongAdder longAdder = new LongAdder();
    public YouDaoDspResponseParamConvert(ConvertDspParam convertDspParam) {
        super(convertDspParam);
    }

    public List<SspRespAdInfo> convert() {

        if (StringUtils.isBlank(this.dspRespData)) {
            if (LogPrintCheckUtils.needPrintLog(longAdder, 100)) {
                log.warn("longAdder：{}，{} 响应数据为空，响应数据：{} ",longAdder.sum(), DSP_NAME, this.dspRespData);
            }
            return null;
        }

        YouDaoDspResponseParam dspResponseParam = JSON.parseObject(this.dspRespData, YouDaoDspResponseParam.class);
        if (dspResponseParam == null) {
            if (LogPrintCheckUtils.needPrintLog(longAdder, 100)) {
                log.warn("longAdder：{}，{} 响应的JSON数据转换为JAVA对象为空，响应数据：{} ",longAdder.sum(), DSP_NAME, this.dspRespData);
            }
            return null;
        }


        SspRespApp sspRespApp = this.getSspRespApp(dspResponseParam);
        SspRespVideo sspRespVideo = this.getSspRespVideo(dspResponseParam);
        List<SspRespImage> sspRespImages = this.getSspRespImages(dspResponseParam);
        SspRespTrack sspRespTrack = this.getSspRespTrack(dspResponseParam);
        SspRespMiniProgram sspRespMiniProgram = this.getSspRespMiniProgram(dspResponseParam);

        SspRespAdInfo respAdInfo = new SspRespAdInfo();
        respAdInfo.setRequestId(this.sspRequestId);
        respAdInfo.setAdType(this.adSlotBudgetWrap.getBudgetType());
        respAdInfo.setMaterialType(null);

        InteractionType dspInteractionType = InteractionType.NON_INREACTION;
        if (StringUtils.isNotBlank(dspResponseParam.getDeeplink())) {
            dspInteractionType = InteractionType.DEEPLINK;
        } else if (dspResponseParam.getYdAdType().equals(YouDaoYdAdType.WEBVIEW.getType())) {
            dspInteractionType = InteractionType.WEBVIEW;
        } else if (dspResponseParam.getWxMiniProgram() != null) {
            dspInteractionType = InteractionType.WECHAT_MINI_PROGRAM;
        }else if(dspResponseParam.getYdAdType().equals(YouDaoYdAdType.DOWNLOAD.getType())){
            dspInteractionType = InteractionType.DOWNLOAD;
        }

        respAdInfo.setInteractionType(dspInteractionType.getType());
        respAdInfo.setTitle(dspResponseParam.getTitle());
        respAdInfo.setDesc(dspResponseParam.getText());
        if (dspResponseParam.getIconimage() != null) {
            respAdInfo.setAdIcons(Collections.singletonList(dspResponseParam.getIconimage()));
        }
        respAdInfo.setDeeplinkType(DeeplinkType.DEEPLINK.getType());
        respAdInfo.setDeeplink(dspResponseParam.getDeeplink());
        respAdInfo.setLandingPageUrl(dspResponseParam.getClk());
        respAdInfo.setDownloadUrl(dspResponseParam.getClk()); //XXX 确认下载地址
        respAdInfo.setBidPrice(Optional.ofNullable(dspResponseParam.getEcpm()).map(Double::intValue).orElse(null));
        respAdInfo.setWinNoticeUrl(null);
        respAdInfo.setLossNoticeUrl(null);

        respAdInfo.setApp(sspRespApp);
        respAdInfo.setVideo(sspRespVideo);
        respAdInfo.setImages(sspRespImages);
        respAdInfo.setMiniProgram(sspRespMiniProgram);
        respAdInfo.setTrack(sspRespTrack);

        return Collections.singletonList(respAdInfo);

    }

    /**
     * 展示上报链接价格宏替换
     *
     * @param price
     * @param showUrls
     * @return
     */
    private List<String> showUrlReplace(Double price, List<String> showUrls) {
        String priceEncryptKey = this.adSlotBudgetWrap.getPriceEncryptKey();
        if (StringUtils.isBlank(priceEncryptKey) || priceEncryptKey.split(",").length != 2) {
            log.error("展示上报链接价格宏替换失败，网易有道dsp加密密钥格式错误:{}", priceEncryptKey);
            return showUrls;
        }
        String encryptionKey = priceEncryptKey.split(",")[0];
        String integrityKey = priceEncryptKey.split(",")[1];

        String priceEncryptStr = YouDaoParamsEncrypt.encryptWinningPrice(price, encryptionKey, integrityKey);
        if (StringUtils.isBlank(priceEncryptStr)) {
            return showUrls;
        }
        List<String> resultWinUrls = new ArrayList<>();
        for (String url : showUrls) {
            String replaceWinUrl = StringUtils.replace(url, MACRO_PRICE, priceEncryptStr);
            resultWinUrls.add(replaceWinUrl);
        }
        return resultWinUrls;
    }

    private SspRespMiniProgram getSspRespMiniProgram(YouDaoDspResponseParam dspResponseParam) {
        if (dspResponseParam.getWxMiniProgram() == null) {
            return null;
        }
        SspRespMiniProgram sspRespMiniProgram = new SspRespMiniProgram();
        sspRespMiniProgram.setMiniProgramId(dspResponseParam.getWxMiniProgram().getOriginid());
        sspRespMiniProgram.setMiniProgramPath(dspResponseParam.getWxMiniProgram().getPath());
        return sspRespMiniProgram;
    }

    private SspRespApp getSspRespApp(YouDaoDspResponseParam dspResponseParam) {
        SspRespApp sspRespApp = new SspRespApp();
        sspRespApp.setPkgName(getPackageName());
        sspRespApp.setPkgMd5(getPackageName() != null ? MD5Util.getMD5String(getPackageName()) : null);
        YouDaoDownloadAppInfo youDaoDownloadAppInfo = dspResponseParam.getDownloadAppInfo();
        if (youDaoDownloadAppInfo != null) {
            sspRespApp.setName(youDaoDownloadAppInfo.getAppTitle());
            sspRespApp.setVersion(youDaoDownloadAppInfo.getAppVersion());
            sspRespApp.setIconUrl(youDaoDownloadAppInfo.getAppIconImage());
            sspRespApp.setSize(null);
            sspRespApp.setCorporate(youDaoDownloadAppInfo.getDeveloperName());
            sspRespApp.setPrivacyPolicyUrl(youDaoDownloadAppInfo.getPrivacyPolicy());
            sspRespApp.setPermissionUrl(youDaoDownloadAppInfo.getAppPermission());
        }
        return sspRespApp;
    }


    private SspRespVideo getSspRespVideo(YouDaoDspResponseParam dspResponseParam) {
        SspRespVideo sspRespVideo = new SspRespVideo();
        sspRespVideo.setUrl(dspResponseParam.getVideourl());
        sspRespVideo.setCoverUrl(null);
        sspRespVideo.setDuration(convertToSeconds(dspResponseParam.getVideoduration()));
        sspRespVideo.setForceDuration(null);
        if (dspResponseParam.getVideosize() != null) {
            sspRespVideo.setSize(Math.toIntExact(dspResponseParam.getVideosize()));
        }
        sspRespVideo.setWidth(dspResponseParam.getVideowidth());
        sspRespVideo.setHeight(dspResponseParam.getVideoheight());
        if (dspResponseParam.getEndcardhtml() != null) {
            sspRespVideo.setEndUrlType(3);
            sspRespVideo.setEndUrl(dspResponseParam.getEndcardhtml());
        }
        return sspRespVideo;
    }

    /**
     * 将有道返回的视频时长HH:mm:ss.SSS格式，转为秒
     *
     * @param videoDuration
     * @return
     */
    public static Integer convertToSeconds(String videoDuration) {
        if (videoDuration == null) {
            return null;
        }
        // 切分时、分、秒
        String[] parts = videoDuration.split(":");
        // 将时、分、秒转换为整数
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        double seconds = Double.parseDouble(parts[2]);
        // 计算总秒数
        return hours * 3600 + minutes * 60 + (int) Math.round(seconds);
    }

    private List<SspRespImage> getSspRespImages(YouDaoDspResponseParam dspResponseParam) {
        List<SspRespImage> sspRespImageList = new ArrayList<>();
        if(dspResponseParam.getMainimage()!=null){
            SspRespImage sspRespImage = new SspRespImage();
            sspRespImage.setUrl(dspResponseParam.getMainimage());
            sspRespImageList.add(sspRespImage);
        }
        if(dspResponseParam.getMainimage1()!=null){
            SspRespImage sspRespImage = new SspRespImage();
            sspRespImage.setUrl(dspResponseParam.getMainimage1());
            sspRespImageList.add(sspRespImage);
        }
        if(dspResponseParam.getMainimage2()!=null){
            SspRespImage sspRespImage = new SspRespImage();
            sspRespImage.setUrl(dspResponseParam.getMainimage2());
            sspRespImageList.add(sspRespImage);
        }
        if(dspResponseParam.getMainimage3()!=null){
            SspRespImage sspRespImage = new SspRespImage();
            sspRespImage.setUrl(dspResponseParam.getMainimage3());
            sspRespImageList.add(sspRespImage);
        }
        if(dspResponseParam.getMainimage4()!=null){
            SspRespImage sspRespImage = new SspRespImage();
            sspRespImage.setUrl(dspResponseParam.getMainimage4());
            sspRespImageList.add(sspRespImage);
        }
        if (dspResponseParam.getImages() != null) {
            for (YouDaoWidthHeightInfo youDaoWidthHeightInfo : dspResponseParam.getImages()) {
                SspRespImage sspRespImage = new SspRespImage();
                sspRespImage.setHeight(youDaoWidthHeightInfo.getHeight());
                sspRespImage.setWidth(youDaoWidthHeightInfo.getWidth());
                sspRespImage.setUrl(youDaoWidthHeightInfo.getUrl());
                sspRespImageList.add(sspRespImage);
            }
        }
        return sspRespImageList;
    }


    private SspRespTrack getSspRespTrack(YouDaoDspResponseParam dspResponseParam) {

        //解析DSP返回的监控链接，并将链接中的宏替换为平台宏
        List<String> showUrls = null;
        List<String> clickUrls = null;
        List<String> adLoadUrls = null;
        List<String> adSkipUrls = null;
        List<String> adCloseUrls = null;
        List<String> wechatOpenUrls = null;

        List<String> startDownloadUrls = null;
        List<String> finishDownloadUrls = null;
        List<String> startInstallUrls = null;
        List<String> finishInstallUrls = null;
        List<String> activeAppUrls = null;
        List<String> deeplinkTryUrls = null;
        List<String> deeplinkSuccessUrls = null;
        List<String> deeplinkFailureUrls = null;
        List<String> deeplinkClickUrls = null;
        List<String> deeplinkInstalledkUrls = null;
        List<String> deeplinkUninstallkUrls = null;

        List<String> videoStartUrls = null;
        List<String> videoClickUrls = null;
        List<String> videoCompleteUrls = null;
        List<String> videoFailUrls = null;
        List<String> videoCloseUrls = null;
        List<String> videoSkipUrls = null;
        List<String> videoPauseUrls = null;
        List<String> videoResumeUrls = null;
        List<String> videoReplayUrls = null;
        List<String> videoMuteUrls = null;
        List<String> videoUnmuteUrls = null;
        List<String> videoFullscreenUrls = null;
        List<String> videoExitFullscreenUrls = null;
        List<String> videoUpscrollUrls = null;
        List<String> videoDownscrollUrls = null;
        List<String> videoQuartileUrls = null;
        List<String> videoHalfUrls = null;
        List<String> videoThreeQuartileUrls = null;
        List<String> winTrackUrls = null;

        showUrls = MacroParameters.macroParametersReplace(DSP, dspResponseParam.getImptracker());
        clickUrls = MacroParameters.macroParametersReplace(DSP, dspResponseParam.getClktrackers());
        deeplinkTryUrls = MacroParameters.macroParametersReplace(DSP, dspResponseParam.getDptrackers());
        deeplinkInstalledkUrls = MacroParameters.macroParametersReplace(DSP, dspResponseParam.getDpInstallTrackers());
        deeplinkUninstallkUrls = MacroParameters.macroParametersReplace(DSP, dspResponseParam.getDpNotInstallTrackers());
        deeplinkSuccessUrls = MacroParameters.macroParametersReplace(DSP, dspResponseParam.getDpSuccessTrackers());
        deeplinkFailureUrls = MacroParameters.macroParametersReplace(DSP, dspResponseParam.getDpFailedTrackers());
        startDownloadUrls = MacroParameters.macroParametersReplace(DSP, dspResponseParam.getApkStartDownloadTrackers());
        finishDownloadUrls = MacroParameters.macroParametersReplace(DSP, dspResponseParam.getApkDownloadTrackers());
        startInstallUrls = MacroParameters.macroParametersReplace(DSP, dspResponseParam.getApkStartInstallTrackers());
        finishInstallUrls = MacroParameters.macroParametersReplace(DSP, dspResponseParam.getApkInstallTrackers());
        wechatOpenUrls = MacroParameters.macroParametersReplace(DSP, dspResponseParam.getWxTrackers());
        videoStartUrls = MacroParameters.macroParametersReplace(DSP, dspResponseParam.getVideoloaded());
        videoFailUrls = MacroParameters.macroParametersReplace(DSP, dspResponseParam.getError());
        YouDaoVideoPlaytrackers youDaoVideoPlaytrackers = dspResponseParam.getPlaytrackers();
        if (youDaoVideoPlaytrackers != null) {
            videoMuteUrls = MacroParameters.macroParametersReplace(DSP, youDaoVideoPlaytrackers.getMute());
            videoUnmuteUrls = MacroParameters.macroParametersReplace(DSP, youDaoVideoPlaytrackers.getUnmute());
            videoCloseUrls = MacroParameters.macroParametersReplace(DSP, youDaoVideoPlaytrackers.getVideoclose());

            videoStartUrls = MacroParameters.macroParametersReplace(DSP, youDaoVideoPlaytrackers.getPlay());
            videoPauseUrls = MacroParameters.macroParametersReplace(DSP, youDaoVideoPlaytrackers.getPause());
            videoReplayUrls = MacroParameters.macroParametersReplace(DSP, youDaoVideoPlaytrackers.getReplay());
            videoFullscreenUrls = MacroParameters.macroParametersReplace(DSP, youDaoVideoPlaytrackers.getFullscreen());
            videoExitFullscreenUrls = MacroParameters.macroParametersReplace(DSP, youDaoVideoPlaytrackers.getUnfullscreen());
            videoUpscrollUrls = MacroParameters.macroParametersReplace(DSP, youDaoVideoPlaytrackers.getUpscroll());
            videoDownscrollUrls = MacroParameters.macroParametersReplace(DSP, youDaoVideoPlaytrackers.getDownscroll());
            videoThreeQuartileUrls = MacroParameters.macroParametersReplace(DSP, youDaoVideoPlaytrackers.getPlaypercent());
            //组装激励视频播放进度追踪URLs
            getPlaypercentageUrls(videoQuartileUrls, videoHalfUrls, videoThreeQuartileUrls, youDaoVideoPlaytrackers);
        }

        //组装平台自己的事件上报链接
        showUrls = Optional.ofNullable(showUrls).orElseGet(ArrayList::new);
        if (StringUtils.isNotBlank(dspResponseParam.getWinNotice())) {
            showUrls.add(dspResponseParam.getWinNotice());
        }
        //展示上报链接价格宏替换
        if (dspResponseParam.getEcpm() != null) {
            showUrls = this.showUrlReplace(dspResponseParam.getEcpm(), showUrls);
        }

        clickUrls = Optional.ofNullable(clickUrls).orElseGet(ArrayList::new);
        List<String> ctatrackersUrls = MacroParameters.macroParametersReplace(DSP, dspResponseParam.getCtatrackers());
        if (ctatrackersUrls != null) {
            clickUrls.addAll(ctatrackersUrls);
        }
        finishDownloadUrls = Optional.ofNullable(finishDownloadUrls).orElseGet(ArrayList::new);
        finishInstallUrls = Optional.ofNullable(finishInstallUrls).orElseGet(ArrayList::new);
        deeplinkSuccessUrls = Optional.ofNullable(deeplinkSuccessUrls).orElseGet(ArrayList::new);
        this.fillReportUrls(showUrls, clickUrls, finishDownloadUrls, finishInstallUrls, deeplinkSuccessUrls);

        SspRespTrack sspRespTrack = new SspRespTrack();
        sspRespTrack.setShowUrls(showUrls);
        sspRespTrack.setClickUrls(clickUrls);
        sspRespTrack.setAdLoadUrls(adLoadUrls);
        sspRespTrack.setAdSkipUrls(adSkipUrls);
        sspRespTrack.setAdCloseUrls(adCloseUrls);
        sspRespTrack.setWechatOpenUrls(wechatOpenUrls);

        sspRespTrack.setStartDownloadUrls(startDownloadUrls);
        sspRespTrack.setFinishDownloadUrls(finishDownloadUrls);
        sspRespTrack.setStartInstallUrls(startInstallUrls);
        sspRespTrack.setFinishInstallUrls(finishInstallUrls);
        sspRespTrack.setActiveAppUrls(activeAppUrls);
        sspRespTrack.setDeeplinkTryUrls(deeplinkTryUrls);
        sspRespTrack.setDeeplinkSuccessUrls(deeplinkSuccessUrls);
        sspRespTrack.setDeeplinkFailureUrls(deeplinkFailureUrls);
        sspRespTrack.setDeeplinkClickUrls(deeplinkClickUrls);
        sspRespTrack.setDeeplinkInstalledkUrls(deeplinkInstalledkUrls);
        sspRespTrack.setDeeplinkUninstallkUrls(deeplinkUninstallkUrls);

        sspRespTrack.setVideoStartUrls(videoStartUrls);
        sspRespTrack.setVideoClickUrls(videoClickUrls);
        sspRespTrack.setVideoCompleteUrls(videoCompleteUrls);
        sspRespTrack.setVideoFailUrls(videoFailUrls);
        sspRespTrack.setVideoCloseUrls(videoCloseUrls);
        sspRespTrack.setVideoSkipUrls(videoSkipUrls);
        sspRespTrack.setVideoPauseUrls(videoPauseUrls);
        sspRespTrack.setVideoResumeUrls(videoResumeUrls);
        sspRespTrack.setVideoReplayUrls(videoReplayUrls);
        sspRespTrack.setVideoMuteUrls(videoMuteUrls);
        sspRespTrack.setVideoUnmuteUrls(videoUnmuteUrls);
        sspRespTrack.setVideoFullscreenUrls(videoFullscreenUrls);
        sspRespTrack.setVideoExitFullscreenUrls(videoExitFullscreenUrls);
        sspRespTrack.setVideoUpscrollUrls(videoUpscrollUrls);
        sspRespTrack.setVideoDownscrollUrls(videoDownscrollUrls);
        sspRespTrack.setVideoQuartileUrls(videoQuartileUrls);
        sspRespTrack.setVideoHalfUrls(videoHalfUrls);
        sspRespTrack.setVideoThreeQuartileUrls(videoThreeQuartileUrls);
        return sspRespTrack;
    }

    /**
     * 组装激励视频播放进度追踪URLs
     *
     * @param videoQuartileUrls
     * @param videoHalfUrls
     * @param videoThreeQuartileUrls
     * @param youDaoVideoPlaytrackers
     */
    private void getPlaypercentageUrls(List<String> videoQuartileUrls, List<String> videoHalfUrls, List<String> videoThreeQuartileUrls, YouDaoVideoPlaytrackers youDaoVideoPlaytrackers) {
        List<YouDaoRewardedVideoplaypercentage> youDaoRewardedVideoplaypercentageList = youDaoVideoPlaytrackers.getPlaypercentage();
        if (youDaoVideoPlaytrackers.getPlaypercentage() == null) {
            return;
        }
        for (YouDaoRewardedVideoplaypercentage youDaoRewardedVideoplaypercentage : youDaoRewardedVideoplaypercentageList) {
            if (youDaoRewardedVideoplaypercentage.getUrls().isEmpty()) {
                continue;
            }
            if (youDaoRewardedVideoplaypercentage.getCheckpoint().equals(0.25)) {
                videoQuartileUrls = Optional.ofNullable(videoQuartileUrls).orElseGet(ArrayList::new);
                videoQuartileUrls.addAll(youDaoRewardedVideoplaypercentage.getUrls());
            }
            if (youDaoRewardedVideoplaypercentage.getCheckpoint().equals(0.50)) {
                videoHalfUrls = Optional.ofNullable(videoHalfUrls).orElseGet(ArrayList::new);
                videoHalfUrls.addAll(youDaoRewardedVideoplaypercentage.getUrls());
            }
            if (youDaoRewardedVideoplaypercentage.getCheckpoint().equals(0.75)) {
                videoThreeQuartileUrls = Optional.ofNullable(videoThreeQuartileUrls).orElseGet(ArrayList::new);
                videoThreeQuartileUrls.addAll(youDaoRewardedVideoplaypercentage.getUrls());
            }
        }
    }
}
