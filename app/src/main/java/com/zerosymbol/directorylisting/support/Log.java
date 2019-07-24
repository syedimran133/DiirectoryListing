package com.zerosymbol.directorylisting.support;

/**
 * Created by root on 23-12-2016.
 */

public class Log {

    private static boolean ENABLE_DEBUG = true;

    public static void e(String tag, String message) {
        if (ENABLE_DEBUG)
            android.util.Log.e(tag, message);
    }

    public static void d(String tag, String message) {
        if (ENABLE_DEBUG)
            android.util.Log.d(tag, message);
    }

    public static void i(String tag, String message) {
        if (ENABLE_DEBUG)
            android.util.Log.i(tag, message);
    }

    public static void v(String tag, String message) {
        if (ENABLE_DEBUG)
            android.util.Log.v(tag, message);
    }

    public static void w(String tag, String message) {
        if (ENABLE_DEBUG)
            android.util.Log.w(tag, message);
    }
}
