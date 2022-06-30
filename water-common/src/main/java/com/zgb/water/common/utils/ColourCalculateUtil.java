package com.zgb.water.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 颜色计算
 */
public class ColourCalculateUtil {
    /**
     * 颜色值计算
     * @param colours
     * @return
     */
    public static int assessColourCalculate(String colours) {
        int colour = 0;
        if (StringUtils.isEmpty(colours)) {
            return colour;
        }
        String[] arr = colours.split(",");
        for (int i = 0; i < arr.length; i++) {
            colour = colour | Integer.parseInt(arr[i]);
        }
        return colour;
    }

    /**
     * 颜色值计算
     * @param colours
     * @return
     */
    public static int getColourCalculate(String[] colours) {
        int colour = 0;
        if (null==colours) {
            return colour;
        }
        for (int i = 0; i < colours.length; i++) {
            colour = colour | Integer.parseInt(colours[i]);
        }
        return colour;
    }

}
