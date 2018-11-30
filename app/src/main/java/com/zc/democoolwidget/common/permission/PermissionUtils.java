package com.zc.democoolwidget.common.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/7.
 */
public class PermissionUtils {

    public static final int PERMISSION_CALL_PHONE = 1;
    public static final int PERMISSION_LOCATION_PHONE = 2;
    public static final int PERMISSION_GRANTED_RECORD_AUDIO = 4;
    public static final int REQUEST_CODE_ASK_WRITE_SETTINGS = 5;
    public static final int PERMISSION_WRITE_EXTERNAL_STORAGE = 6;
    public static final int PERMISSION_CAMERA_WRITE_EXTERNAL = 7;
    public static final int PERMISSION_WRITE_CONTACTS = 8;

    /**
     * 申请没有的权限,已申请的会被过滤,
     * 如果没有过滤,会导致已申请的权限再次申请
     * 返回申请失败,不执行相应的方法
     * @param activity
     * @param permission
     * @param code
     * @return
     */
    public static boolean  checkPermission(Activity activity,String[] permission,int code){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions=new ArrayList<>();
            for (String per :permission){
                if (ContextCompat.checkSelfPermission(activity, per) != PackageManager.PERMISSION_GRANTED) {
                    permissions.add(per);
                }
            }
            if(permissions.size()>0){
                activity.requestPermissions(permissions.toArray(new String[permissions.size()]),code);
                return false;
            }
        }
        return  true;
    }

    /**校验申请的权限是否全部申请了 true都申请了 false否*/
    public static boolean checkAllGranted (int[] grantResults) {
        boolean isAllGranted = true;//是否所有权限都申请了
        if (grantResults.length >= 1) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }
        }
        return isAllGranted;
    }

    /**判断某个权限是否granted */
    public static boolean checkPermissionGranted (Context activity, String per) {
        return ContextCompat.checkSelfPermission(activity, per) == PackageManager.PERMISSION_GRANTED;
    }

    /**判断是否有某个权限 */
    public static boolean checkHavePermission (String[] permissions,String permission) {
        boolean isHave = false;
        for (int i = 0; i < permissions.length; i++){
            String per = permissions[i];
            if (permission.equals(per)) {
                isHave = true;
                break;
            }
        }
        return isHave;
    }

}
