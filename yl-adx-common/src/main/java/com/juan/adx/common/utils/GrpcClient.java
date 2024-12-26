package com.juan.adx.common.utils;

import com.juan.adx.model.ssp.common.protobuf.YlAdxDataReqConvert;
import com.juan.adx.model.ssp.common.request.*;
import com.yl.ad.protobuf.AdxDataProtobuf;
import com.yl.ad.protobuf.DomobGrpc;
import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Kevin.赵伟凯
 * @CreateTime: 2024-12-23 12:33
 * @Description:
 * @Version: 1.0
 */
@Slf4j
public class GrpcClient {


    public static void main(String[] args) throws InterruptedException {

        AdxDataProtobuf.AdReq.Builder ad = AdxDataProtobuf.AdReq.newBuilder();

        YlAdxDataReqConvert reqConvert = new YlAdxDataReqConvert();
        SspRequestParam reqParameter = new SspRequestParam();
        reqParameter.setRequestId("1223232323");
        SspReqSlot slot = new SspReqSlot();
        reqParameter.setSlot(slot);
        slot.setMaterialType(3);
        slot.setType(3);
        slot.setSlotId("7823");
        slot.setWidth(0);
        slot.setHeight(0);
        slot.setAdSlotId(70100);

        SspReqDevice device = new SspReqDevice();
        reqParameter.setDevice(device);
        device.setDeviceName("Test");
        device.setType(3);
        device.setOsType(2);
        device.setModel("M2012K11C");
        device.setOsVersion("M2012K11C");
        device.setDeviceName("Test");
        device.setBrand("OPPO");
        device.setMake("OPPO");
        device.setImsi("123");

        SspReqDeviceId deviceId = new SspReqDeviceId();
        deviceId.setIdfv("1212");
        deviceId.setAndroidId("kds323");
        deviceId.setImei("Test");
        reqParameter.setDeviceId(deviceId);

        SspReqApp app = new SspReqApp();
        app.setAppId("2345666");
        app.setName("TestApp");
        app.setPkgName("com.pkg.com");
        app.setVerName("12.323");
        app.setAppStoreVersion("4232.232");
        reqParameter.setApp(app);

        SspReqNetwork network = new SspReqNetwork();
        network.setIp("123.57.24.133");
        network.setCarrier(2);
        network.setMac("ksdkalsdkfdd");
        network.setCountry("CN");
        network.setNetworkType(4);
        network.setUserAgent("Mozilla/5.0 (Linux; Android 12; M2012K11C Build/SKQ1.211006.001;");
        reqParameter.setNetwork(network);

        GrpcPool connectionPool = new GrpcPool("123.57.24.133", 8088, 5);
        ManagedChannel channel = connectionPool.borrowChannel();
        channel.awaitTermination(20000L, TimeUnit.MILLISECONDS);

//        ManagedChannel  channel = ManagedChannelBuilder.forAddress("123.57.24.133", 8088).usePlaintext().build();
        AdxDataProtobuf.AdReq adReqProtobuf = reqConvert.convert(reqParameter);

//        // 使用 channel 进行 RPC 调用
//        DomobGrpc.DomobBlockingStub stubBlocking = DomobGrpc.newBlockingStub(channel);
//        AdxDataProtobuf.AdResp resp = stubBlocking.adGet(adReqProtobuf);
//        log.info(">>>>>>>>:" + resp);

        DomobGrpc.DomobStub stub = DomobGrpc.newStub(channel);

        stub.adGet(adReqProtobuf, new StreamObserver<AdxDataProtobuf.AdResp>() {
            @Override
            public void onNext(AdxDataProtobuf.AdResp adResp) {
                log.info("1111111111111111");
                System.out.println("NNNNNNNNNNN");
            }

            @Override
            public void onError(Throwable throwable) {

                log.info("222222222");
                System.out.println("EEEEEEEEEEEEE:" + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println(">>>>>>>>>>>>>>>");
                log.info("333333333333333333");
            }
        });

//        ListenableFuture<YlAdxDataProtobuf.AdResp> future  = stub.adGet(reqConvert.convert(reqParameter));
        connectionPool.returnChannel(channel);
    }

}
