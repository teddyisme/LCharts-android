package com.lixs.charts;

import android.content.Context;
import android.util.AttributeSet;

import com.lixs.charts.Base.FramBase;

/**
 * Created by XinSheng on 2016/12/21.
 */

public class KlineChartView extends FramBase {
    public KlineChartView(Context context) {
        this(context, null);
    }

    public KlineChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KlineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        initPaint();
    }

    private void initPaint() {

    }



}
