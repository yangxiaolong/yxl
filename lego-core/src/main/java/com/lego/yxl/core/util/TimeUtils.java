package com.lego.yxl.core.util;

import java.util.concurrent.TimeUnit;

/**
 */
public class TimeUtils {
    public static void sleepAsMS(long time){
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
