package com.zc.democoolwidget.recycleview.excelcalendar;

import android.content.Context;

/**
 * Created by NEU on 2018/2/5.
 */

public class DpPxSpUtil {

    /**
     * dip转px函数。
     *
     * @param context
     *            程序上下文
     * @param dpValue
     *            dip值
     * @return px值
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
