package com.juan.adx.common.utils;

import java.util.concurrent.atomic.LongAdder;

/**
 * @author： HeWen Zhou
 * @date： 2024/6/7 15:28
 */
public class LogPrintCheckUtils {

    public static boolean needPrintLog(LongAdder longAdder,int rate) {
        if (longAdder.sum() >= (Integer.MAX_VALUE - 8000)) {
            longAdder.reset();
        }
        longAdder.increment();
        if (longAdder.intValue() >= rate && longAdder.intValue() % rate == 0) {
            return true;
        }
        return false;
    }

}
