package com.weclubs.util;

import org.apache.log4j.Logger;

/**
 * 常见的工具类
 *
 * Created by fangzanpan on 2017/4/17.
 */
public class WCCommonUtil {

    private static Logger log = Logger.getLogger(WCCommonUtil.class);

    /**
     * 获取 long 型
     *
     * @param object    需要转换的类型
     *
     * @return  long 类型的结果
     */
    public static long getLongData(Object object) {
        if (object == null) {
            return 0;
        }

        if (object instanceof Long) {
            return (Long) object;
        } else if (object instanceof Integer) {
            return (Integer) object;
        } else if (object instanceof String) {
            try {
                return Long.parseLong((String) object);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return 0;
            }
        } else {
            try {
                // 尝试直接转换成字符类型直接强转
                return Long.parseLong((String) object);
            } catch (NumberFormatException e) {
                log.error("getLongData：无法转换成long类型，object = 【" + object.toString() + "】");
                e.printStackTrace();
                return 0;
            }
        }
    }
}
