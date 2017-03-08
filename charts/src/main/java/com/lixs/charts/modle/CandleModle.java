package com.lixs.charts.modle;

/**
 * Created by XinSheng on 2016/12/21.
 */

public class CandleModle {
    private float closePrice;//收盘价
    private float openPrice;//开盘价
    private float topPrice;//最高价
    private float bottomPrice;//最低价

    public float getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(float closePrice) {
        this.closePrice = closePrice;
    }

    public float getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(float openPrice) {
        this.openPrice = openPrice;
    }

    public float getTopPrice() {
        return topPrice;
    }

    public void setTopPrice(float topPrice) {
        this.topPrice = topPrice;
    }

    public float getBottomPrice() {
        return bottomPrice;
    }

    public void setBottomPrice(float bottomPrice) {
        this.bottomPrice = bottomPrice;
    }
}
