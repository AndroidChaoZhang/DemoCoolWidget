package com.zc.democoolwidget.common.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

public class PermissionReqUtil {

    /**定位和手机相关权限*/
    public static boolean requestLocationPhonePermission(Activity activity) {
        boolean result = PermissionUtils.checkPermission(activity, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.READ_PHONE_STATE}, PermissionUtils.PERMISSION_LOCATION_PHONE);
        return result;
    }

    /**打电话相关权限*/
    public static boolean requestCallPermission(Activity activity) {
        boolean result = PermissionUtils.checkPermission(activity, new String[]{
                Manifest.permission.CALL_PHONE}, PermissionUtils.PERMISSION_CALL_PHONE);
        return result;
    }
    /**百度语音相关权限*/
    public static boolean requestAudioPermission(Activity activity) {
        boolean result = PermissionUtils.checkPermission(activity, new String[]{
                Manifest.permission.RECORD_AUDIO}, PermissionUtils.PERMISSION_GRANTED_RECORD_AUDIO);
        return result;
    }

    /**语音操作相关权限*/
    public static boolean requestAudioWriteExternalS(Activity activity) {
        boolean result = PermissionUtils.checkPermission(activity, new String[]{
                Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE}, PermissionUtils.PERMISSION_GRANTED_RECORD_AUDIO);
        return result;
    }

    /**读写存储权限*/
    public static boolean requestWriteExternalS (Activity activity) {
        boolean result = PermissionUtils.checkPermission(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PermissionUtils.PERMISSION_WRITE_EXTERNAL_STORAGE);
        return result;
    }
    /**读写联系人权限*/
    public static boolean requestWriteContacts (Activity activity) {
        boolean result = PermissionUtils.checkPermission(activity, new String[]{Manifest.permission.WRITE_CONTACTS,Manifest.permission.READ_CONTACTS}, PermissionUtils.PERMISSION_WRITE_CONTACTS);
        return result;
    }

    /**照相操作相关权限*/
    public static boolean requestCameraWriteExternalS(Activity activity) {
        boolean result = PermissionUtils.checkPermission(activity, new String[]{
                Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, PermissionUtils.PERMISSION_CAMERA_WRITE_EXTERNAL);
        return result;
    }
    public static boolean requestCameraWriteExternalS(Activity activity,int requestCode) {
        boolean result = PermissionUtils.checkPermission(activity, new String[]{
                Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
        return result;
    }


    /**申请android.permission.WRITE_SETTINGS权限的方式*/
    public static void reuqestWriteSetting (Activity activity) {
        //申请android.permission.WRITE_SETTINGS权限的方式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //如果当前平台版本大于23平台
            if (!Settings.System.canWrite(activity)) {
                //如果没有修改系统的权限这请求修改系统的权限
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + activity.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
            }
        }
    }
}
