package com.lixs.charts;

import android.content.Context;

/**
 * @author XinSheng
 * @description
 * @date 2018/4/28
 */
public class Utils {
    public static int dp2px(Context context, int dpValue) {
        return (int) context.getResources().getDisplayMetrics().density * dpValue;
    }
}
