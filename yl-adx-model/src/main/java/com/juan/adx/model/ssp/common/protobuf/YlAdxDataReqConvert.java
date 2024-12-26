package com.juan.adx.model.ssp.common.protobuf;

import com.juan.adx.model.ssp.common.request.SspRequestParam;
import com.yl.ad.protobuf.AdxDataProtobuf;
import org.springframework.beans.BeanUtils;

import java.util.Optional;

/**
 * @author： Kevin.zhao
 * @date： 2024/12/22 19:24
 * 将Bean对象转换为Protobuf对象, 不使用BeanUtil等方法,提高转换的效率
 */
public class YlAdxDataReqConvert {

    public AdxDataProtobuf.AdReq convert(SspRequestParam sspRequestParam) {
        AdxDataProtobuf.AdReq.Builder b = AdxDataProtobuf.AdReq.newBuilder();
        b.setRequestId(sspRequestParam.getRequestId());
        b.setDeal(convertDeal(sspRequestParam));
        b.setUser(convertUser(sspRequestParam));
        b.setSlot(convertSlot(sspRequestParam));
        b.setDevice(convertDevice(sspRequestParam));
        b.setDeviceId(convertDeviceId(sspRequestParam));
        b.setApp(convertApp(sspRequestParam));
        b.setNetwork(convertNetwork(sspRequestParam));
        b.setGeo(convertGeo(sspRequestParam));
        b.setExt(convertExt(sspRequestParam));
        return b.build();
    }

    private AdxDataProtobuf.Ext convertExt(SspRequestParam sspRequestParam) {
        AdxDataProtobuf.Ext.Builder g = AdxDataProtobuf.Ext.newBuilder();
        if (sspRequestParam.getExt() == null) {
            return g.build();
        }
        Optional.ofNullable(sspRequestParam.getExt().getPaid() ).ifPresent(g::setPaid);
        Optional.ofNullable(sspRequestParam.getExt().getSupportWechat() ).ifPresent(g::setSupportWechat);
        return  g.build();
    }

    private AdxDataProtobuf.Deal convertDeal(SspRequestParam sspRequestParam) {
        AdxDataProtobuf.Deal.Builder g = AdxDataProtobuf.Deal.newBuilder();
        if (sspRequestParam.getDeal() == null) {
            return g.build();
        }
        Optional.ofNullable(sspRequestParam.getDeal().getBidfloor()).ifPresent(g::setBidfloor);
        return  g.build();
    }

    private AdxDataProtobuf.Geo convertGeo(SspRequestParam sspRequestParam) {
        AdxDataProtobuf.Geo.Builder g = AdxDataProtobuf.Geo.newBuilder();
        if (sspRequestParam.getGeo() == null) {
            return g.build();
        }
        Optional.ofNullable(sspRequestParam.getGeo().getCoordinateType()).ifPresent(g::setCoordinateType);
        Optional.ofNullable(sspRequestParam.getGeo().getTimestamp()).ifPresent(g::setTimestamp);
        if (sspRequestParam.getGeo().getLatitude() != null) {
            g.setLatitude(sspRequestParam.getGeo().getLatitude().floatValue());
        }
        if (sspRequestParam.getGeo().getLongitude() != null) {
            g.setLongitude(sspRequestParam.getGeo().getLongitude().floatValue());
        }
        return g.build();
    }

    public AdxDataProtobuf.App convertApp(SspRequestParam s) {
        AdxDataProtobuf.App.Builder a = AdxDataProtobuf.App.newBuilder();
        Optional.ofNullable(s.getApp().getAppStoreVersion()).ifPresent(a::setAppStoreVersion);
        Optional.ofNullable(s.getApp().getName()).ifPresent(a::setName);
        Optional.ofNullable(s.getApp().getVerName()).ifPresent(a::setVerName);
        Optional.ofNullable(s.getApp().getPkgName()).ifPresent(a::setPkgName);

        //TODO  appId
        return a.build();
    }

    public AdxDataProtobuf.Device convertDevice(SspRequestParam s) {
        AdxDataProtobuf.Device.Builder d = AdxDataProtobuf.Device.newBuilder();
        Optional.ofNullable(s.getDevice().getBrand()).ifPresent(d::setBrand);
        Optional.ofNullable(s.getDevice().getType()).ifPresent(d::setType);
        Optional.ofNullable(s.getDevice().getMake()).ifPresent(d::setMake);
        Optional.ofNullable(s.getDevice().getBirthMark()).ifPresent(d::setBirthMark);
        Optional.ofNullable(s.getDevice().getAndroidApiLevel()).ifPresent(d::setAndroidApiLevel);
        Optional.ofNullable(s.getDevice().getDpi()).ifPresent(d::setDpi);
        Optional.ofNullable(s.getDevice().getCpuNum()).ifPresent(d::setCpuNum);
        Optional.ofNullable(s.getDevice().getPpi()).ifPresent(d::setPpi);
        Optional.ofNullable(s.getDevice().getWidth()).ifPresent(d::setWidth);
        Optional.ofNullable(s.getDevice().getHeight()).ifPresent(d::setHeight);
        Optional.ofNullable(s.getDevice().getBootMark()).ifPresent(d::setBootMark);
        Optional.ofNullable(s.getDevice().getDensity()).ifPresent(d::setDensity);
        Optional.ofNullable(s.getDevice().getDeviceName()).ifPresent(d::setDeviceName);
        Optional.ofNullable(s.getDevice().getDeviceNameMd5()).ifPresent(d::setDeviceNameMd5);
        Optional.ofNullable(s.getDevice().getHagVersion()).ifPresent(d::setHagVersion);
        Optional.ofNullable(s.getDevice().getHmsVersion()).ifPresent(d::setHmsVersion);
        Optional.ofNullable(s.getDevice().getRomVersion()).ifPresent(d::setRomVersion);
        Optional.ofNullable(s.getDevice().getOsVersion()).ifPresent(d::setOsVersion);
        Optional.ofNullable(s.getDevice().getOsUiVersion()).ifPresent(d::setOsUiVersion);
        Optional.ofNullable(s.getDevice().getOsType()).ifPresent(d::setOsType);
        Optional.ofNullable(s.getDevice().getUpdateMark()).ifPresent(d::setUpdateMark);
        Optional.ofNullable(s.getDevice().getTimeZone()).ifPresent(d::setTimeZone);
        Optional.ofNullable(s.getDevice().getSysMemorySize()).ifPresent(d::setSysMemorySize);
        Optional.ofNullable(s.getDevice().getSysDiskSize()).ifPresent(d::setSysDiskSize);
        Optional.ofNullable(s.getDevice().getHardwareModel()).ifPresent(d::setHardwareModel);
        Optional.ofNullable(s.getDevice().getHarmonyOsVersion()).ifPresent(d::setHarmonyOsVersion);
        Optional.ofNullable(s.getDevice().getSupportDeeplink()).ifPresent(d::setSupportDeeplink);
        Optional.ofNullable(s.getDevice().getSupportUniversal()).ifPresent(d::setSupportUniversal);
        Optional.ofNullable(s.getDevice().getSerialno()).ifPresent(d::setSerialno);
        Optional.ofNullable(s.getDevice().getOrientation()).ifPresent(d::setOrientation);
        Optional.ofNullable(s.getDevice().getScreenSize()).ifPresent(d::setScreenSize);
        Optional.ofNullable(s.getDevice().getSysCompilingTime()).ifPresent(d::setSysCompilingTime);
        Optional.ofNullable(s.getDevice().getSysStartupTime()).ifPresent(d::setSysStartupTime);
        Optional.ofNullable(s.getDevice().getSysUpdateTime()).ifPresent(d::setSysUpdateTime);
        Optional.ofNullable(s.getDevice().getImsi()).ifPresent(d::setImsi);
        Optional.ofNullable(s.getDevice().getModel()).ifPresent(d::setModel);
        Optional.ofNullable(s.getDevice().getLanguage()).ifPresent(d::setLanguage);

        return d.build();
    }

    public AdxDataProtobuf.DeviceId convertDeviceId(SspRequestParam s) {
        AdxDataProtobuf.DeviceId.Builder d = AdxDataProtobuf.DeviceId.newBuilder();
        Optional.ofNullable(s.getDeviceId().getAndroidId()).ifPresent(d::setAndroidId);
        Optional.ofNullable(s.getDeviceId().getAndroidIdMd5()).ifPresent(d::setAndroidIdMd5);
        Optional.ofNullable(s.getDeviceId().getAndroidIdSha1()).ifPresent(d::setAndroidIdSha1);
        Optional.ofNullable(s.getDeviceId().getIdfa()).ifPresent(d::setIdfa);
        Optional.ofNullable(s.getDeviceId().getIdfaMd5()).ifPresent(d::setIdfaMd5);
        Optional.ofNullable(s.getDeviceId().getIdfv()).ifPresent(d::setIdfv);
        Optional.ofNullable(s.getDeviceId().getOaid()).ifPresent(d::setOaid);
        Optional.ofNullable(s.getDeviceId().getOaidMd5()).ifPresent(d::setOaidMd5);
        Optional.ofNullable(s.getDeviceId().getImei()).ifPresent(d::setImei);
        Optional.ofNullable(s.getDeviceId().getImeiMd5()).ifPresent(d::setImeiMd5);
        Optional.ofNullable(s.getDeviceId().getOpenUdid()).ifPresent(d::setOpenUdid);

        return d.build();
    }

    public AdxDataProtobuf.Slot convertSlot(SspRequestParam sspRequestParam) {
        AdxDataProtobuf.Slot.Builder slot = AdxDataProtobuf.Slot.newBuilder();
        slot.setAdSlotId(sspRequestParam.getSlot().getAdSlotId().toString());
        slot.setType(sspRequestParam.getSlot().getType());
        slot.setHeight(sspRequestParam.getSlot().getHeight());
        slot.setWidth(sspRequestParam.getSlot().getWidth());
        slot.setMaterialType(sspRequestParam.getSlot().getMaterialType());
        return slot.build();
    }

    public AdxDataProtobuf.User convertUser(SspRequestParam sspRequestParam) {
        AdxDataProtobuf.User.Builder user = AdxDataProtobuf.User.newBuilder();
        if (sspRequestParam.getUser() == null) {
            return user.build();
        }
        Optional.ofNullable(sspRequestParam.getUser().getAge()).ifPresent(user::setAge);
        Optional.ofNullable(sspRequestParam.getUser().getGender()).ifPresent(user::setGender);
        Optional.ofNullable(sspRequestParam.getUser().getInstalled()).ifPresent(user::addAllInstalled);
        Optional.ofNullable(sspRequestParam.getUser().getInterest()).ifPresent(user::addAllInterest);
        return user.build();
    }

    public AdxDataProtobuf.Network convertNetwork(SspRequestParam s) {
        AdxDataProtobuf.Network.Builder network = AdxDataProtobuf.Network.newBuilder();
        Optional.ofNullable(s.getNetwork().getBssid()).ifPresent(network::setBssid);
        Optional.ofNullable(s.getNetwork().getSsid()).ifPresent(network::setSsid);
        Optional.ofNullable(s.getNetwork().getIp()).ifPresent(network::setIp);
        Optional.ofNullable(s.getNetwork().getCountry()).ifPresent(network::setCountry);
        Optional.ofNullable(s.getNetwork().getCarrier()).ifPresent(network::setCarrier);
        Optional.ofNullable(s.getNetwork().getIpv6()).ifPresent(network::setIpv6);
        Optional.ofNullable(s.getNetwork().getMac()).ifPresent(network::setMac);
        Optional.ofNullable(s.getNetwork().getMacMd5()).ifPresent(network::setMacMd5);
        Optional.ofNullable(s.getNetwork().getMacSha1()).ifPresent(network::setMacSha1);
        Optional.ofNullable(s.getNetwork().getUserAgent()).ifPresent(network::setUserAgent);
        Optional.ofNullable(s.getNetwork().getNetworkType()).ifPresent(network::setNetworkType);
        Optional.ofNullable(s.getNetwork().getMnc()).ifPresent(network::setMnc);
        Optional.ofNullable(s.getNetwork().getMcc()).ifPresent(network::setMcc);
        return network.build();
    }
}


