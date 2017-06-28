package com.wosai.bright.common.utils;

/**
 * Created by Administrator on 2017/3/27 0027.
 */
public class RandomUtils {

    /**
     * 获取指定位数的随机字符串(包含小写字母、大写字母、数字)
     * @param length
     * @return
     */
    public static String getRandomStringALL(int length) {
        //随机字符串的随机字符库
        String KeyString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuffer sb = new StringBuffer();
        int len = KeyString.length();
        for (int i = 0; i < length; i++) {
            sb.append(KeyString.charAt((int) Math.round(Math.random() * len)));
        }
        return sb.toString();
    }

    /**
     * 获取指定位数的随机字符串(包含小写字母、数字)
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        //随机字符串的随机字符库
        String KeyString = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuffer sb = new StringBuffer();
        int len = KeyString.length();
        for (int i = 0; i < length; i++) {
            sb.append(KeyString.charAt((int) Math.round(Math.random() * len)));
        }
        return sb.toString();
    }


}
