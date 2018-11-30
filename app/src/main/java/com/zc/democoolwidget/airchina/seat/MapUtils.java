package com.zc.democoolwidget.airchina.seat;

import android.text.TextUtils;

/**
 * Created by NEU on 2016/11/17.
 */

public class MapUtils {
    /**object的空处理*/
    public static  String getObject (Object object) {
        return null==object?"":object.toString();
    }


    /**object的空处理成0字符串*/
    public static  String getObjectToZero (Object object) {
        String string = getObject(object);
        if (TextUtils.isEmpty(string)) {
            string = "0";
        }
        return string;
    }


}
