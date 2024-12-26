package com.juan.adx.api.converter.vivo;

import com.juan.adx.api.converter.AbstractDspRequestParamConvert;
import com.juan.adx.common.utils.ParamUtils;
import com.juan.adx.model.dsp.vivo.enums.VivoCarrierType;
import com.juan.adx.model.dsp.vivo.enums.VivoGender;
import com.juan.adx.model.dsp.vivo.enums.VivoNetworkType;
import com.juan.adx.model.dsp.vivo.enums.VivoSlotType;
import com.juan.adx.model.dsp.vivo.request.*;
import com.juan.adx.model.entity.api.ConvertSspParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/25 16:44
 */
@Slf4j
public class VivoDspRequestParamConvert extends AbstractDspRequestParamConvert {

    public VivoDspRequestParamConvert(ConvertSspParam convertSspParam) {
        super(convertSspParam);
    }

    public VivoDspRequestParam convert() {
        VivoDspRequestParam requestParam = new VivoDspRequestParam();
        requestParam.setApiVersion("1.0");
        requestParam.setSysVersion("unknow");
        if (StringUtils.isNotBlank(this.app.getAppStoreVersion()) && ParamUtils.isLong(this.app.getAppStoreVersion())) {
            requestParam.setAppstoreVersion(Long.parseLong(this.app.getAppStoreVersion()));
        }
        requestParam.setTimeout(2000);
        requestParam.setUser(getUser());
        requestParam.setMedia(getMedia());
        requestParam.setPosition(getPostion());
        requestParam.setDevice(getDevice());
        requestParam.setNetwork(getNetwork());
        requestParam.setGeo(getGeo());
        requestParam.setPmp(null);
        requestParam.setAdCount(1);
        if(this.ext != null && this.ext.getSupportWechat() != null){
            requestParam.setIsSupportMiniProgram(this.ext.getSupportWechat()?1:0);
        }
        requestParam.setBrowserVersion(null);
        return requestParam;
    }


    private VivoDevice getDevice() {
        VivoDevice device = new VivoDevice();
        // 设置属性值为空
        device.setMac(this.network.getMac());
        device.setImei(this.deviceId.getImei());
        device.setDidMd5(this.deviceId.getImeiMd5());
        device.setOaid(this.deviceId.getOaid());
        device.setVaid(null);
        device.setAndroidId(this.deviceId.getAndroidId());
        device.setAn(this.device.getOsVersion());
        device.setAv(this.device.getAndroidApiLevel());
        device.setUa(this.network.getUserAgent());
        device.setIp(this.network.getIp());
        device.setMake(this.device.getMake());
        device.setModel(this.device.getModel());
        device.setLanguage(this.device.getLanguage());
        device.setScreenWidth(this.device.getWidth());
        device.setScreenHeight(this.device.getHeight());
        device.setPpi(this.device.getPpi());
        device.setElapseTime(null);
        device.setPersonalizedState(null);
        return device;
    }

    private VivoGeo getGeo() {
        VivoGeo geo = new VivoGeo();
        geo.setLat(this.geo.getLatitude().floatValue());
        geo.setLng(this.geo.getLongitude().floatValue());
        geo.setCoordTime(this.geo.getTimestamp());
        return geo;
    }

    private VivoMedia getMedia() {
        VivoMedia media = new VivoMedia();
        media.setMediaId(this.adSlotBudgetWrap.getDspAppId());
        media.setAppPackage(getPackageName());
        media.setAppVersion(1);//默认填写1
        return media;
    }

    private VivoNetwork getNetwork() {
        VivoNetwork network = new VivoNetwork();
        network.setConnectType(VivoNetworkType.get(this.network.getNetworkType()).getDspType());
        network.setCarrier(VivoCarrierType.get(this.network.getCarrier()).getDspType());
        network.setMccmnc(this.network.getMcc());
        return network;
    }

    private VivoPmp getPmp() {
        VivoPmp vivoPmp = new VivoPmp();
        VivoDeal vivoDeal = new VivoDeal();
        vivoDeal.setId(null);
        vivoDeal.setBidFloor(null);
        vivoPmp.setDeals(null);
        return vivoPmp;
    }

    private VivoPostion getPostion() {
        VivoPostion position = new VivoPostion();
        position.setPositionId(this.adSlotBudgetWrap.getDspSlotId());
        position.setDisplayType(VivoSlotType.get(this.slot.getType()).getDspType());
        position.setWidth(this.slot.getWidth());
        position.setHeight(this.slot.getHeight());
        if (this.deal != null && this.deal.getBidfloor() != null) {
            position.setBidFloor(this.deal.getBidfloor().longValue());
        }
        return position;
    }

    private VivoUser getUser() {
        if (this.user == null) {
            return null;
        }
        VivoUser user = new VivoUser();
        user.setAppList(this.user.getInstalled());
        user.setGender(VivoGender.get(this.user.getGender()).getDspType());
        user.setAge(this.user.getAge());
        user.setInterest(this.user.getInterest());
        return user;
    }
}
