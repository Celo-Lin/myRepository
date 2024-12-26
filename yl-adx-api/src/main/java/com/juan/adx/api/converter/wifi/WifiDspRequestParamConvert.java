package com.juan.adx.api.converter.wifi;

import com.alibaba.fastjson2.JSON;
import com.google.protobuf.ByteString;
import com.juan.adx.api.converter.AbstractDspRequestParamConvert;
import com.juan.adx.model.dsp.oppo.response.OppoDspResponseParam;
import com.juan.adx.model.dsp.oppo.response.OppoUnionApiAdInfo;
import com.juan.adx.model.dsp.wifi.WifiDspProtobuf;
import com.juan.adx.model.dsp.wifi.enums.*;
import com.juan.adx.model.dsp.youdao.request.YouDaoDspRequestParam;
import com.juan.adx.model.entity.api.ConvertSspParam;
import com.juan.adx.model.ssp.wifi.WifiRtbProtobuf;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/28 19:24
 */
@Slf4j
public class WifiDspRequestParamConvert extends AbstractDspRequestParamConvert {

    public WifiDspRequestParamConvert(ConvertSspParam convertSspParam) {
        super(convertSspParam);
    }

    public WifiDspProtobuf.FlyingShuttleBidRequest convert() {

        WifiDspProtobuf.FlyingShuttleBidRequest.Builder flyingShuttleBidRequest = WifiDspProtobuf.FlyingShuttleBidRequest.newBuilder();
        flyingShuttleBidRequest.setToken(this.adSlotBudgetWrap.getPriceEncryptKey());
        flyingShuttleBidRequest.setRequestId(sspRequestParam.getRequestId());
        flyingShuttleBidRequest.setTimestamp(System.currentTimeMillis());
        flyingShuttleBidRequest.setUser(getUser());
        flyingShuttleBidRequest.setSlot(getSlot());
        flyingShuttleBidRequest.setDevice(getDevice());
        flyingShuttleBidRequest.setApp(getApp());
        flyingShuttleBidRequest.setNetwork(getNetwork());
        flyingShuttleBidRequest.setGeo(getGeo());
        return flyingShuttleBidRequest.build();
    }

    public static WifiDspProtobuf.Gender getGenderByType(Integer type) {
        if (type == null) {
            return WifiDspProtobuf.Gender.GENDER_UNSPECIFIED;
        }
        for (WifiDspProtobuf.Gender genderType : WifiDspProtobuf.Gender.values()) {
            if (genderType.getNumber() == type) {
                return genderType;
            }
        }
        return WifiDspProtobuf.Gender.GENDER_UNSPECIFIED;
    }

    public WifiDspProtobuf.User getUser() {
        WifiDspProtobuf.User.Builder user = WifiDspProtobuf.User.newBuilder();
        if (this.user.getAge() != null) {
            user.setAge(this.user.getAge());
        }
        user.setGender(getGenderByType(this.user.getGender()));
        if (this.user.getInstalled() != null) {
            user.addAllInstalledApps(getInstallAppList(this.user.getInstalled()));
        }
        return user.build();
    }

    /**
     * 获取安装程序对应的id
     *
     * @param installAppList
     * @return
     */
    public List<Integer> getInstallAppList(List<String> installAppList) {
        if (installAppList == null || installAppList.isEmpty()) {
            return null;
        }
        List<Integer> returnList = new ArrayList<>();
        for (String installApp : installAppList) {
            WIFIInstallApp wifiInstallApp = WIFIInstallApp.getByPkgName(installApp);
            if (wifiInstallApp == null) {
                continue;
            }
            returnList.add(wifiInstallApp.getId());
        }
        return returnList;
    }

    public WifiDspProtobuf.Slot getSlot() {
        WifiDspProtobuf.Slot.Builder slot = WifiDspProtobuf.Slot.newBuilder();
        slot.setId(this.adSlotBudgetWrap.getDspSlotId());
        slot.setType(WifiSloatType.get(this.slot.getType()).getDspType());
        slot.setAdCount(1);
        // slot.setMaterialTypes();
        // slot.setAdTypes();
        slot.setFloorCpm(getSspFloorPrice());
        return slot.build();
    }

    public WifiDspProtobuf.Device getDevice() {
        WifiDspProtobuf.Device.Builder device = WifiDspProtobuf.Device.newBuilder();
        device.setType(WifiDeviceType.get(this.device.getType()).getDspType());
        device.setOs(WifiOsType.get(this.device.getOsType()).getDspType());
        device.setOsVersion(this.device.getOsVersion());
        Optional.ofNullable(this.device.getAndroidApiLevel()).ifPresent(device::setApiLevel);
        device.setVendor(this.device.getMake());
        device.setModel(this.device.getModel());
        device.setUserAgent(this.network.getUserAgent());
        device.setDeviceId(getDeviceID());
        device.setScreenWidth(this.device.getWidth());
        device.setScreenHeight(this.device.getHeight());
        device.setScreenDpi(this.device.getDpi());
        Optional.ofNullable(this.device.getPpi()).ifPresent(device::setScreenPpi);
        Optional.ofNullable(this.device.getBootMark()).ifPresent(device::setBootMark);
        Optional.ofNullable(this.device.getUpdateMark()).ifPresent(device::setUpdateMark);
        device.setBootTime(this.device.getSysStartupTime());
        device.setSysUpdateTime(this.device.getSysUpdateTime());
        Optional.ofNullable(this.device.getHmsVersion()).ifPresent(device::setHwHmsVerCode);
        Optional.ofNullable(this.device.getHagVersion()).ifPresent(device::setHwAgVerCode);
        return device.build();
    }

    public WifiDspProtobuf.SourceApp getApp() {
        WifiDspProtobuf.SourceApp.Builder app = WifiDspProtobuf.SourceApp.newBuilder();
        app.setId(this.adSlotBudgetWrap.getDspAppId());
        app.setName(this.app.getName());
        app.setVersion(0);//待确认
        app.setVersionName(this.app.getVerName());
        app.setMarket("");//待确认
        app.setPkgName(this.app.getPkgName());
        return app.build();
    }

    public static WifiDspProtobuf.Carrier getCarrierByType(Integer type) {
        if (type == null) {
            return WifiDspProtobuf.Carrier.CARRIER_UNSPECIFIED;
        }
        for (WifiDspProtobuf.Carrier carrierType : WifiDspProtobuf.Carrier.values()) {
            if (carrierType.getNumber() == type) {
                return carrierType;
            }
        }
        return WifiDspProtobuf.Carrier.CARRIER_UNSPECIFIED;
    }

    public WifiDspProtobuf.Network getNetwork() {
        WifiDspProtobuf.Network.Builder network = WifiDspProtobuf.Network.newBuilder();
        network.setType(WifiNetType.get(this.network.getNetworkType()).getDspType());
        network.setCarrier(getCarrierByType(this.network.getCarrier()));
        Optional.ofNullable(this.network.getIp()).ifPresent(network::setIpv4);
        return network.build();
    }

    public WifiDspProtobuf.Geo getGeo() {
        WifiDspProtobuf.Geo.Builder geo = WifiDspProtobuf.Geo.newBuilder();
        if (this.geo == null) {
            return geo.build();
        }
        Optional.ofNullable(this.geo.getLatitude()).ifPresent(geo::setLatitude);
        Optional.ofNullable(this.geo.getLongitude()).ifPresent(geo::setLongitude);
        return geo.build();
    }

    public WifiDspProtobuf.DeviceID getDeviceID() {
        WifiDspProtobuf.DeviceID.Builder deviceID = WifiDspProtobuf.DeviceID.newBuilder();
        Optional.ofNullable(this.deviceId.getImei()).ifPresent(deviceID::setImei);
        deviceID.setMac(this.network.getMac());
        Optional.ofNullable(this.deviceId.getAndroidId()).ifPresent(deviceID::setAndroidId);
        Optional.ofNullable(this.deviceId.getOaid()).ifPresent(deviceID::setOaid);
        Optional.ofNullable(this.deviceId.getIdfa()).ifPresent(deviceID::setIdfa);
        return deviceID.build();
    }


}
