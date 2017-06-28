package com.wosai.bright.common.utils;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/24 0024.
 */
public class GodUtils {


    /**
     * @param list
     * @return
     */
    public static boolean CheckNull(List<?> list) {
        if (list == null || list.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean CheckNull(Map<?, ?> map) {
        if (map == null || map.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean CheckNull(String str) {
        if (str == null) {
            return true;
        } else if ("".equals(str.trim())) {
            return true;
        }
        return false;
    }

    public static boolean CheckNull(Integer it) {
        if (it == null || it == 0) {
            return true;
        }
        return false;
    }

    public static boolean CheckNull(Object[] array) {
        if (array == null || array.length <= 0) {
            return true;
        }
        return false;
    }

    public static boolean CheckIsNumber(String number) {

        if (number == null || number == "") {

            return false;
        }

        for (int i = 0; i < number.length(); i++) {

            if (Character.isDigit(number.charAt(i))) {

                return true;
            }

        }
        return false;
    }
}
