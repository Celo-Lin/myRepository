package com.juan.adx.api.service.xunfei;

import com.juan.adx.api.service.AdxApiService;
import com.juan.adx.common.exception.ExceptionEnum;
import com.juan.adx.common.exception.ServiceRuntimeException;
import com.juan.adx.common.validator.ParamAssert;
import com.juan.adx.model.entity.api.AdSlotWrap;
import com.juan.adx.model.enums.CooperationMode;
import com.juan.adx.model.ssp.common.request.SspReqDeal;
import com.juan.adx.model.ssp.common.request.SspRequestParam;
import com.juan.adx.model.ssp.common.response.SspRespAdInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/28 13:48
 */
@Slf4j
@Service
public class XunfeiAdxApiService extends AdxApiService {

    /**
     * 重写该方法，无广告时返回204
     * @param sspRequestParam
     * @param slotWrap
     * @return
     */
    @Override
    public List<SspRespAdInfo> getDspAdvert(SspRequestParam sspRequestParam, AdSlotWrap slotWrap) {

        CooperationMode cooperationMode = CooperationMode.get(slotWrap.getSlotCooperationMode());
        if (cooperationMode == null) {
            throw new ServiceRuntimeException(ExceptionEnum.UNCLASSIFIED);
        }
        List<SspRespAdInfo> sspRespBids = null;
        switch (cooperationMode) {
            case PD:
                sspRespBids = this.pdMode(sspRequestParam, slotWrap);
                break;
            case RTB_AUTO:
                if (Objects.isNull(sspRequestParam.getDeal())
                        || Objects.isNull(sspRequestParam.getDeal().getBidfloor())
                        || sspRequestParam.getDeal().getBidfloor().intValue() <= 0) {
                    SspReqDeal deal = new SspReqDeal();
                    deal.setBidfloor(slotWrap.getSspBidPrice());
                    sspRequestParam.setDeal(deal);
                }
                //RTB-自动模式下，校验价格参数必填
                //ParamAssert.isTrue(sspRequestParam.getDeal() == null, "交易相关参数不能为空");
                ParamAssert.isTrue(sspRequestParam.getDeal().getBidfloor() == null || sspRequestParam.getDeal().getBidfloor().intValue() <= 0, "交易底价参数不能为空");
                sspRespBids = this.rtbMod(sspRequestParam, slotWrap, CooperationMode.RTB_AUTO);
                break;
            case RTB_MANUAL:
                sspRespBids = this.rtbMod(sspRequestParam, slotWrap, CooperationMode.RTB_MANUAL);
                break;
            default:
                break;
        }
        if (CollectionUtils.isEmpty(sspRespBids)) {
            throw new ServiceRuntimeException(ExceptionEnum.XUNFEI_ADVERT_NOT_FILL);
        }
        return sspRespBids;
    }

}
