package com.juan.adx.api.service.dsp;


import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @Author: Kevin.赵伟凯
 * @CreateTime: 2024-12-16 11:02
 * @Description: 向 DSP  Proxy发起请求服务
 * @Version: 1.0
 */
@Getter
@ToString
public class PriceContext {
    private Integer mediaBidFloor = 0;
    private Integer channelBidFloor = 0;
    private Integer mediaBidPrice = 0;
    private Integer channelBidPrice = 0;
    private Integer mediaSettlePrice = 0;
    private Integer channelSettlePrice = 0;

    public void setMediaBidFloor(Integer mediaBidFloor, BigDecimal bidFloorFactor) {
        this.mediaBidFloor = mediaBidFloor == null ? Integer.valueOf("0") : mediaBidFloor;

        // 底价抬升计算
        bidFloorFactor = bidFloorFactor == null ? new BigDecimal("0") : bidFloorFactor;
        BigDecimal channelBidFloor = new BigDecimal(this.mediaBidFloor).add(new BigDecimal(this.mediaBidFloor).multiply(bidFloorFactor)).setScale(0, RoundingMode.HALF_UP);
        this.channelBidFloor = channelBidFloor.intValue();

        // 系数有可能是-1, 这时候出现<=0的底价, 需要兜底操作
        if (this.channelBidFloor <= 0) {
            this.channelBidFloor = 1;
        }
    }

    public void setBidFloor(Integer mediaBidFloor, Integer channelBidFloor) {
        this.mediaBidFloor = mediaBidPrice == null ? Integer.valueOf("0") : mediaBidFloor;
        this.channelBidFloor = channelBidFloor == null ? Integer.valueOf("0") : channelBidFloor;
    }

    public void setChannelBidPrice(Integer channelBidPrice, BigDecimal bidPriceFactor) {
        this.channelBidPrice = channelBidPrice == null ? Integer.valueOf("0") : channelBidPrice;

        // 给媒体出价压低
        bidPriceFactor = bidPriceFactor == null ? new BigDecimal("0") : bidPriceFactor;
        BigDecimal temp = (new BigDecimal(this.channelBidPrice).subtract(new BigDecimal(this.mediaBidFloor))).multiply((new BigDecimal("1").subtract(bidPriceFactor)));
        this.mediaBidPrice = new BigDecimal(this.channelBidPrice).subtract(temp).setScale(0, RoundingMode.HALF_UP).intValue();
        if (this.mediaBidPrice < this.mediaBidFloor) {
            this.mediaBidPrice = this.mediaBidFloor + 1;
        }
    }

    public void setBidPrice(Integer mediaBidPrice, Integer channelBidPrice) {
        this.mediaBidPrice = mediaBidPrice == null ? Integer.valueOf("0") : mediaBidPrice;
        this.channelBidPrice = channelBidPrice == null ? Integer.valueOf("0") : channelBidPrice;
    }

    public void setMediaSettlePrice(Integer mediaSettlePrice, BigDecimal settlePriceFactor) {
        this.mediaSettlePrice = mediaSettlePrice == null ? Integer.valueOf("0") : mediaSettlePrice;

        // 给渠道结算价抬升
        settlePriceFactor = settlePriceFactor == null ? new BigDecimal("0") : settlePriceFactor;
        BigDecimal temp = (new BigDecimal(this.channelBidPrice).subtract(new BigDecimal(this.mediaSettlePrice))).multiply(settlePriceFactor);
        this.channelSettlePrice = new BigDecimal(this.mediaSettlePrice).add(temp).setScale(0, RoundingMode.HALF_UP).intValue();
        // 如果渠道结算价大于渠道出价, 则以渠道出价作为媒体结算价
        if (this.channelSettlePrice.compareTo(this.channelBidPrice) >= 0) {
            this.channelSettlePrice = this.channelBidPrice - 1;
            if (this.channelSettlePrice.compareTo(this.mediaSettlePrice) < 0) {
                this.channelSettlePrice = this.channelBidPrice;
            }
        }
    }

    public void setSettlePrice(Integer mediaSettlePrice, Integer channelSettlePrice) {
        this.mediaSettlePrice = mediaSettlePrice == null ? Integer.valueOf("0") : mediaSettlePrice;
        this.channelSettlePrice = channelSettlePrice == null ? Integer.valueOf("0") : channelSettlePrice;
    }

    public static void main(String[] args) {
        PriceContext priceContext = new PriceContext();
        priceContext.setMediaBidFloor(10, new BigDecimal("0"));
        priceContext.setChannelBidPrice(20, new BigDecimal("0"));
        priceContext.setMediaSettlePrice(11, new BigDecimal("1"));
        System.err.println(priceContext);
    }

}
