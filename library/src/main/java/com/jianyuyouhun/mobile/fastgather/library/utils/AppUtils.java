package com.jianyuyouhun.mobile.fastgather.library.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.RequiresPermission;

/**
 * app通用处理工具类
 * Created by wangyu on 2018/6/25.
 */
@SuppressWarnings("ALL")
public class AppUtils {
    /**
     * 获取meta
     *
     * @param context      上下文
     * @param metaName     metaName
     * @param defaultValue 默认值
     * @return
     */
    public static String getAppMetaDataString(Context context, String metaName, String defaultValue) {
        try {
            //application标签下用getApplicationinfo，如果是activity下的用getActivityInfo
            //Sting类型的用getString，Boolean类型的getBoolean，其他具体看api
            String value = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA)
                    .metaData.getString(metaName, defaultValue);
            return value;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    /**
     * 获得进程名字
     */
    public static String getUIPName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (mActivityManager != null) {
            for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
                if (appProcess.pid == pid) {
                    return appProcess.processName;
                }
            }
        }
        return "";
    }

    /**
     * 获取版本号
     */
    public static String getStringDeviceVersion() {
        return android.os.Build.VERSION.RELEASE;
    }


    /**
     * 获得数字版本号
     */
    public static float getIntDeviceVersion() {
        float m_iDeviceVersion = 0;
        String m_sDeviceVersion = android.os.Build.VERSION.RELEASE;
        if (m_sDeviceVersion != null && m_sDeviceVersion.length() >= 3) {
            String spiltString = m_sDeviceVersion.substring(0, 3);
            String regex = "^\\d+([\\.]?\\d+)?$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(spiltString);
            boolean result = matcher.matches();
            if (result) {
                m_iDeviceVersion = Float.valueOf(spiltString);
            } else {
                m_iDeviceVersion = 0;
            }
        }
        return m_iDeviceVersion;
    }


    public static String getPhoneVersion() {
        return android.os.Build.VERSION.CODENAME;
    }

    /**
     * 获取设备android api版本
     *
     * @return
     */
    public static int getDeviceSdk() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * 获取imei串号（需要权限）
     *
     * @param context
     * @return
     */
    @SuppressLint("HardwareIds")
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String getIMEI(Context context) {
        String IMEI = "";
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
        if (tm != null && tm.getDeviceId() != null && !tm.getDeviceId().equals("")) {
            IMEI = null == tm.getDeviceId() ? "" : tm.getDeviceId();
        }
        return IMEI;
    }


    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getPhoneStyle() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取国际移动用户识别码
     *
     * @param context 上下文
     * @return 手机号码，取不到时返回空字符串
     */
    @SuppressLint("HardwareIds")
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String getIMSI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = null;
        if (tm != null) {
            imsi = tm.getSubscriberId();
        }
        if (null == imsi) {
            imsi = "";
        }
        return imsi;
    }

    /**
     * 获取手机ip地址
     *
     * @return ip地址，没有获取到时返回空字符串
     */
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    /**
     * 获取屏幕dpi
     *
     * @param context
     * @return
     */
    public static int getScreenDPI(Context context) {
        int m_screenDPI = -1;
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager wndMgr = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wndMgr != null) {
            wndMgr.getDefaultDisplay().getMetrics(metric);
        }
        m_screenDPI = metric.densityDpi;
        return m_screenDPI;
    }

    /**
     * 获取文字高度和行高
     *
     * @param fontSize
     * @return
     */
    public static int getFontHeight(float fontSize) {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.ascent);
    }

    /**
     * 将dip转换为px
     *
     * @param dip
     * @return
     */
    public static int dipToPx(Context context, float dip) {
        float s = context.getResources().getDisplayMetrics().density;
        return (int) (dip * s + 0.5f);
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        int m_screenWidth;
        DisplayMetrics m_displayMetrics = new DisplayMetrics();
        WindowManager wndMgr = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wndMgr != null) {
            wndMgr.getDefaultDisplay().getMetrics(m_displayMetrics);
        }
        m_screenWidth = m_displayMetrics.widthPixels;
        return m_screenWidth;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        int m_screenHeight;
        DisplayMetrics m_displayMetrics = new DisplayMetrics();
        WindowManager wndMgr = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wndMgr != null) {
            wndMgr.getDefaultDisplay().getMetrics(m_displayMetrics);
        }
        m_screenHeight = m_displayMetrics.heightPixels;
        return m_screenHeight;
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusHeight(Context context) {
        int m_statusBarHeight = 0;
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            m_statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return m_statusBarHeight;
    }

    /**
     * 隐藏软键盘
     *
     * @param activity
     * @return
     */
    public static boolean hideSoftPad(Activity activity) {
        if (activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                return inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return false;
    }

    /**
     * 打开软键盘
     *
     * @param activity
     * @param view
     */
    public static void openSoftPad(Activity activity, View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, 0);
        }
    }

    /**
     * 判断应用是否处于后台
     *
     * @param context 上下文
     * @return 是否处于后台，true ：后台， false：前台
     */
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses;
        if (activityManager != null) {
            appProcesses = activityManager.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                if (appProcess.processName.equals(context.getPackageName())) {
                    return appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND;
                }
            }
        }
        return false;
    }

    /**
     * 判断程序是否在前台运行
     */
    public static boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    /**
     * 跳到系统首页
     *
     * @param context
     */
    public static void startLauncher(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(intent);
    }

    /**
     * 启动设置页面
     *
     * @param activity            activity
     * @param settingsRequestCode 请求码
     */
    public static void startSystemSettingActivity(Activity activity, int settingsRequestCode) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, settingsRequestCode);
    }

    /**
     * 根据包名判断应用是否安装
     *
     * @param context
     * @param packageName
     */
    public static boolean isTargetAppInstall(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> infos = packageManager.getInstalledPackages(0);
        if (infos != null) {
            for (PackageInfo info : infos) {
                if (packageName.equals(info.packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * double转string
     *
     * @param digits       保留几位小数
     * @param sourceDouble 源数据
     * @return doubleString
     */
    public static String getDoubleWithDigit(int digits, double sourceDouble) {
        if (digits <= 0) {
            digits = 0;
        }
        return String.format(Locale.getDefault(), "%." + digits + "f", sourceDouble);
    }

}
