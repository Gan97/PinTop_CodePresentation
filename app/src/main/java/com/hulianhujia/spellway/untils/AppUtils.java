package com.hulianhujia.spellway.untils;

/**
 * Created by Administrator on 2017\6\14 0014.
 */

public class AppUtils {
    /**
     * 第一次和第二次的退出间隔时间基准
     */
    private static final long EXIT_TWICE_INTERVAL = 2000;
    private static long mExitTime = 0;

    /**
     * 第二次按退出则返回true,否则返回false
     */
    public static boolean exitTwice() {
        long newExitTime = System.currentTimeMillis();
        if (newExitTime - mExitTime > EXIT_TWICE_INTERVAL) {
            mExitTime = newExitTime;
            return false;
        } else {
            return true;
        }
    }


}
