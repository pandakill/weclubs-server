package com.weclubs.util;

import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常见的工具类
 *
 * Created by fangzanpan on 2017/4/17.
 */
public class WCCommonUtil {

    private static Logger log = Logger.getLogger(WCCommonUtil.class);

    private static final char UNDERLINE='_';

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

    /**
     * 获取 int 型数据
     *
     * @param object    需要转换的数据
     *
     * @return  int 类型的结果
     */
    public static int getIntegerData(Object object) {
        if (object == null) {
            return 0;
        }

        if (object instanceof Integer) {
            return (Integer) object;
        } else if (object instanceof String) {
            try {
                return Integer.parseInt((String) object);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return 0;
            }
        } else {
            try {
                // 尝试直接转换成字符类型直接强转
                return Integer.parseInt((String) object);
            } catch (NumberFormatException e) {
                log.error("getIntegerData：无法转换成 int 类型，object = 【" + object.toString() + "】");
                e.printStackTrace();
                return 0;
            }
        }
    }

    /**
     * 判断传入的时间与当前时间比较是否已经过期
     *
     * @param date  需要判断的时间戳
     *
     * @return  {@code true} 已经过期； {@code false} 仍未过期
     */
    public static boolean isExpire(long date) {
        return System.currentTimeMillis() >= date;
    }

    /**
     * 获取当前年份
     *
     * @return  当前年份
     */
    public static int getCurrentYear() {
        Calendar cal = Calendar.getInstance();

        return cal.get(Calendar.YEAR);
    }

    /**
     * 驼峰转下划线
     *
     * @param param 参数名
     * @return  返回转换后结果
     */
    public static String camelToUnderline(String param){
        if (param==null||"".equals(param.trim())){
            return "";
        }
        int len=param.length();
        StringBuilder sb=new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c=param.charAt(i);
            if (Character.isUpperCase(c)){
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String underlineToCamel(String param){
        if (param==null||"".equals(param.trim())){
            return "";
        }
        int len=param.length();
        StringBuilder sb=new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c=param.charAt(i);
            if (c==UNDERLINE){
                if (++i<len){
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String underlineToCamel2(String param){
        if (param==null||"".equals(param.trim())){
            return "";
        }
        StringBuilder sb=new StringBuilder(param);
        Matcher mc= Pattern.compile("_").matcher(param);
        int i=0;
        while (mc.find()){
            int position=mc.end()-(i++);
            //String.valueOf(Character.toUpperCase(sb.charAt(position)));
            sb.replace(position-1,position+1,sb.substring(position,position+1).toUpperCase());
        }
        return sb.toString();
    }
}
