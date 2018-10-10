package com.example.service_andriodthreadtest;

import android.util.Log;

public class LogUtil {
    /*
    level的数值设置为 NOTHING 就不进行任何的日志输出
            相应的，数值越大就向下兼容输出日志
             level == ERR时可以进行所有类型日志输出
             level == VERBOSE 只能进行VERBOSE输出日志
     */
    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERR = 5;
    public static final int NOTHING = 6;
    public static int level = VERBOSE;

    public static void v(String tag,String msg){
        if(level <= VERBOSE){
            Log.v(tag,msg);
        }
    }

    public static void d(String tag,String msg){
        if(level <= DEBUG){
            Log.v(tag,msg);
        }
    }
    public static void i(String tag,String msg){
        if(level <= INFO){
            Log.v(tag,msg);
        }
    }
    public static void w(String tag,String msg){
        if(level <= WARN){
            Log.v(tag,msg);
        }
    }
    public static void e(String tag,String msg){
        if(level <= ERR){
            Log.v(tag,msg);
        }
    }

}
