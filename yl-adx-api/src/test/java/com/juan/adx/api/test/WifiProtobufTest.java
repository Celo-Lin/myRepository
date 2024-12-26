package com.juan.adx.api.test;



import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.protobuf.util.JsonFormat;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.model.ssp.wifi.WifiRtbProtobuf;


public class WifiProtobufTest {

    public static void main(String[] args) {


        /*MeituRtaRequestProto.RtaRequest.Builder rtaRequest = MeituRtaRequestProto.RtaRequest.newBuilder();

        MeituRtaResponseProto.RtaResponse.Builder rtaResponse = MeituRtaResponseProto.RtaResponse.newBuilder();

        rtaRequest.setId("dbdfe4fa-288a-4304-a584-849fad3f989c");
        rtaRequest.setTagid("44");
        rtaRequest.setSitesetId(0);


        MeituRtaRequestProto.AdInfo  adInfo = MeituRtaRequestProto.RtaRequest.AdInfo.newBuilder();
        rtaRequest.setAdInfos(0,adInfo);*/
        
        List<WifiRtbProtobuf.MaterialType> materialTypes = new ArrayList<WifiRtbProtobuf.MaterialType>();
        materialTypes.add(WifiRtbProtobuf.MaterialType.UNRECOGNIZED);
        
        WifiRtbProtobuf.Slot.Builder wifiSlot = WifiRtbProtobuf.Slot.newBuilder();
        wifiSlot.setId("10001409");
        wifiSlot.setType(WifiRtbProtobuf.SlotType.SLOT_TYPE_BANNER);
        wifiSlot.setAdCount(1);
//        wifiSlot.addAllMaterialTypes(materialTypes)
        
        WifiRtbProtobuf.DeviceID.Builder wifiDeviceId = WifiRtbProtobuf.DeviceID.newBuilder();
        wifiDeviceId.setImei("357268050000321");
        wifiDeviceId.setMac("00:1A:2B:3C:4D:5E");
        wifiDeviceId.setAndroidId("c7d45f6a8c9d0123");
        wifiDeviceId.setOaid("d2d3c4d5-e6e7-f8f9-1a1b-2c2d3e4f5a6");
        wifiDeviceId.setIdfa("");
        
        WifiRtbProtobuf.Device.Builder wifiDevice = WifiRtbProtobuf.Device.newBuilder();
        wifiDevice.setType(WifiRtbProtobuf.DeviceType.DEVICE_TYPE_PHONE);
        wifiDevice.setOs(WifiRtbProtobuf.OS.OS_ANDROID);
        wifiDevice.setOsVersion("11");
        wifiDevice.setApiLevel(23);
        wifiDevice.setVendor("OPPO");
        wifiDevice.setModel("OPPOA33m");
        wifiDevice.setUserAgent("Mozilla/5.0 (Linux; Android 11; Pixel 4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.75 Mobile Safari/537.36");
        wifiDevice.setDeviceId(wifiDeviceId.build());
        wifiDevice.setScreenWidth(2048);
        wifiDevice.setScreenHeight(1024);
        wifiDevice.setScreenDpi(888);
        wifiDevice.setScreenPpi(0);
        wifiDevice.setBootMark("ec7f4f33-411a-47bc-8067-744a 4e7e0723");
        wifiDevice.setUpdateMark("1004697.709999999");
        wifiDevice.setBootTime("1697990400000");
        wifiDevice.setSysUpdateTime("1697990400000");
        wifiDevice.setHwHmsVerCode("6.3.0.301");
        wifiDevice.setHwAgVerCode("6.3.0.301");
        
        WifiRtbProtobuf.SourceApp.Builder wifiApp = WifiRtbProtobuf.SourceApp.newBuilder();
        wifiApp.setPkgName("com.qq.wechat");
        
        WifiRtbProtobuf.Network.Builder wifiNetwork = WifiRtbProtobuf.Network.newBuilder();
        wifiNetwork.setType(WifiRtbProtobuf.NetType.NET_TYPE_CELLULAR_5G);
        wifiNetwork.setCarrier(WifiRtbProtobuf.Carrier.CARRIER_TELECOM);
        wifiNetwork.setIpv4("120.25.101.0");
        
        WifiRtbProtobuf.Geo.Builder wifiGeo = WifiRtbProtobuf.Geo.newBuilder();
        wifiGeo.setLatitude(37.7749);
        wifiGeo.setLongitude(-122.4194);
        
        WifiRtbProtobuf.UserData.Builder wifiUser = WifiRtbProtobuf.UserData.newBuilder();
        
        WifiRtbProtobuf.BidRequest.Builder wifiRequestParam = WifiRtbProtobuf.BidRequest.newBuilder();
        String requestId = String.valueOf(LocalDateUtils.getNowMilliseconds());
        System.out.println(requestId);
        wifiRequestParam.setRequestId(requestId);
        wifiRequestParam.setTimeout(10000);
        wifiRequestParam.setSlot(wifiSlot.build());
        wifiRequestParam.setDevice(wifiDevice.build());
        wifiRequestParam.setSourceApp(wifiApp.build());
        wifiRequestParam.setNetwork(wifiNetwork.build());
        wifiRequestParam.setGeo(wifiGeo.build());
        wifiRequestParam.setUser(wifiUser.build());
        
        try {
        	CloseableHttpClient httpClient = HttpClients.createDefault();
        	HttpPost post = new HttpPost("http://127.0.0.1:9101/marketflow/ssp/wifi");
        	post.setEntity(new ByteArrayEntity(wifiRequestParam.build().toByteArray()));
        	post.setHeader("Content-Type", "application/octet-stream");
        	
        	HttpResponse response = httpClient.execute(post);
        	
        	if (response.getStatusLine().getStatusCode() == 200) {
        		
        		WifiRtbProtobuf.BidResponse resp = WifiRtbProtobuf.BidResponse.parseFrom(response.getEntity().getContent());
        		
        		String requestParamJson = JsonFormat.printer().print(resp);
        		
        		System.out.println("result: " + requestParamJson);
        	} else {
        		System.out.println(response.getStatusLine().getStatusCode());
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		

    }

}
