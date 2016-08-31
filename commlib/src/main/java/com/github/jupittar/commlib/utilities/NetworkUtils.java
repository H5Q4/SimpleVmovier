package com.github.jupittar.commlib.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * 网络状态工具类 需要权限：ACCESS_NETWORK_STATE,ACCESS_WIFI_STATE,INTERNET
 */
public class NetworkUtils {

    /**
     * Unknown network class.
     */
    protected static final int NETWORK_CLASS_UNKNOWN = 0;
    /**
     * Class of broadly defined "2G" networks.
     */
    protected static final int NETWORK_CLASS_2_G = 1;
    /**
     * Class of broadly defined "3G" networks.
     */
    protected static final int NETWORK_CLASS_3_G = 2;

    /**
     * Class of broadly defined "4G" networks.
     */
    protected static final int NETWORK_CLASS_4_G = 3;

    /**
     * 是否连接上了网络（包括2G 3G WIFI）
     */
    public static Boolean isNetworkConnected(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    /**
     * 是否连接上了WIFI网络
     */
    public static Boolean isWIFIConnected(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        return !(info == null || info.getType() != ConnectivityManager.TYPE_WIFI) && info.isConnected();
    }

    /**
     * 是否连接上了手机网络（包括2G 3G）
     */
    public static Boolean isMobileConnected(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        return !(info == null || info.getType() != ConnectivityManager.TYPE_MOBILE) && info.isConnected();
    }

    /**
     * 是否连接上了2G
     */
    public static Boolean is2GConnected(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        return !(info == null || getNetworkClass(info.getSubtype()) != NETWORK_CLASS_2_G) && info.isConnected();
    }

    /**
     * 是否连接上了3G
     */
    public static Boolean is3GConnected(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        return !(info == null || getNetworkClass(info.getSubtype()) != NETWORK_CLASS_3_G) && info.isConnected();
    }

    /**
     * 是否连接上了4G
     */
    public static Boolean is4GConnected(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        return !(info == null || getNetworkClass(info.getSubtype()) != NETWORK_CLASS_4_G) && info.isConnected();
    }

    protected static int getNetworkClass(int networkType) {
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return NETWORK_CLASS_2_G;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return NETWORK_CLASS_3_G;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return NETWORK_CLASS_4_G;
            default:
                return NETWORK_CLASS_UNKNOWN;
        }
    }
}
