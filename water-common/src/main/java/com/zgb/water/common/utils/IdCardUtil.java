package com.zgb.water.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IdCardUtil {
    // 18位身份证号码正则
    private static String IDCard18_REGEX = "^[1-9](\\d{5})(19|20)(\\d{2})((0[1-9])|10|11|12)(([0-2][1-9])|10|20|30|31)(\\d{3})(\\d|X|x)$";
    // 15位身份证号码正则
    private static String IDCard15_REGEX = "^[1-9](\\d{5})(\\d{2})((0[1-9])|10|11|12)(([0-2][1-9])|10|20|30|31)(\\d{3})$";
    // 平年日期正则
    private static String OrdinaryYear_REGEX = "(((0[13578])|10|12)(([0-2][1-9])|10|20|30|31)|(((0[469])|11)(([0-2][1-9])|10|20|30))|(02(([0-2][1-8])|09|19|10|20)))";
    // 闰年日期正则
    private static String LeapYear_REGEX = "(((0[13578])|10|12)(([0-2][1-9])|10|20|30|31)|(((0[469])|11)(([0-2][1-9])|10|20|30))|(02(([0-2][1-9])|10|20)))";
    // 1-17位相乘因子数组
    private final static int[] FACTOR = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    // 18位随机码数组
    private final static char[] RANDOM = "10X98765432".toCharArray();
    //出生证明校验规则
    private static String BIRTH_REGEX = "[A-Z]{1}\\d{9}";

    private static Map<Integer, String> zoneNum = new HashMap<>();

    static {
        zoneNum.put(11, "北京");
        zoneNum.put(12, "天津");
        zoneNum.put(13, "河北");
        zoneNum.put(14, "山西");
        zoneNum.put(15, "内蒙古");
        zoneNum.put(21, "辽宁");
        zoneNum.put(22, "吉林");
        zoneNum.put(23, "黑龙江");
        zoneNum.put(31, "上海");
        zoneNum.put(32, "江苏");
        zoneNum.put(33, "浙江");
        zoneNum.put(34, "安徽");
        zoneNum.put(35, "福建");
        zoneNum.put(36, "江西");
        zoneNum.put(37, "山东");
        zoneNum.put(41, "河南");
        zoneNum.put(42, "湖北");
        zoneNum.put(43, "湖南");
        zoneNum.put(44, "广东");
        zoneNum.put(45, "广西");
        zoneNum.put(46, "海南");
        zoneNum.put(50, "重庆");
        zoneNum.put(51, "四川");
        zoneNum.put(52, "贵州");
        zoneNum.put(53, "云南");
        zoneNum.put(54, "西藏");
        zoneNum.put(61, "陕西");
        zoneNum.put(62, "甘肃");
        zoneNum.put(63, "青海");
        zoneNum.put(64, "宁夏");
        zoneNum.put(65, "新疆");
        zoneNum.put(71, "台湾");
        zoneNum.put(81, "香港");
        zoneNum.put(82, "澳门");
        zoneNum.put(91, "外国");
        zoneNum.put(83, "台湾");
    }


    static int nowYear = Calendar.getInstance().get(Calendar.YEAR);
    static int nowMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
    static int nowDay = Calendar.getInstance().get(Calendar.DATE);

    public static int getAgeByIdCard(String idCard) {
        int age = 0;
        try {
            if (StringUtils.isEmpty(idCard)) {
                return age;
            }
            if (idCard.length() == 15) {
                age = nowYear - (getSubStringInt(idCard, 6, 8) + 1900) - 1;
                if (nowMonth > getSubStringInt(idCard, 8, 10)) {
                    age++;
                }
                if (nowMonth == getSubStringInt(idCard, 8, 10) && nowDay >= getSubStringInt(idCard, 10, 12)) {
                    age++;
                }
            }
            if (idCard.length() == 18) {
                age = nowYear - getSubStringInt(idCard, 6, 10) - 1;
                if (nowMonth > getSubStringInt(idCard, 10, 12)) {
                    age++;
                }
                if (nowMonth == getSubStringInt(idCard, 10, 12) && nowDay >= getSubStringInt(idCard, 12, 14)) {
                    age++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return age;
    }

    /**
     * @param idCard
     * @return EnumGender
     */
    public static int getGenderByIdCard(String idCard) {
        int resGender = 9;
        if (idCard.length() == 15) {
            if ((Integer.parseInt(idCard.substring(14, 15))) % 2 == 0) {
                resGender = 1;
            } else {
                resGender =2;
            }
        }
        if (idCard.length() == 18) {
            if ((Integer.parseInt(idCard.substring(16, 17))) % 2 == 0) {
                resGender = 1;
            } else {
                resGender = 2;
            }
        }
        return resGender;
    }

    private static int getSubStringInt(String source, int start, int end) {
        return Integer.parseInt(source.substring(start, end));
    }


    /**
     * 用户身份证号码的打码隐藏加星号加*
     * <p>18位和非18位身份证处理均可成功处理</p>
     * <p>参数异常直接返回null</p>
     *
     * @param idCardNum 身份证号码
     * @return 处理完成的身份证
     */
    public static String idMask(String idCardNum) {
        if (StringUtils.isEmpty(idCardNum)) {
            return null;
        }
        int type = checkCardType(idCardNum);
        int end = 1;
        int length = 0;
        int middle = 0;
        if (type == 1) {
            length = 10;
            middle = 6;
        } else if (type == 2) {
            length = 15;
            middle = 4;
        } else if (type == 3) {
            length = 18;
            middle = 5;
        }
        if (length != 0) {
            int front = length - middle - end;
            //计算*的数量
            int asteriskCount = idCardNum.length() - (front + end);
            StringBuffer asteriskStr = new StringBuffer();
            for (int i = 0; i < asteriskCount; i++) {
                asteriskStr.append("*");
            }
            String regex = "(\\w{" + String.valueOf(front) + "})(\\w+)(\\w{" + String.valueOf(end) + "})";
            idCardNum = idCardNum.replaceAll(regex, "$1" + asteriskStr + "$3");
        }
        return idCardNum;
    }


    /**
     * 校验证件号码类型
     * 1：出生证
     * 2：15位身份证
     * 3：18位身份证
     * 0：其它
     *
     * @param idCard
     * @return
     */
    public static int checkCardType(String idCard) {
        try {
            int type = 0;
            boolean flag1 = false;
            boolean flag2 = false;
            boolean flag3 = false;

            // 判断第18位校验值
            int length = idCard.length();
            if (length == 10) {
                if (regexValidate(BIRTH_REGEX, idCard)) {
                    type = 1;
                }
            } else if (length == 15) {
                //验证区域码
                flag1 = regexValidate(IDCard15_REGEX, idCard);
                flag2 = regexValidate(OrdinaryYear_REGEX, idCard.substring(8, 12));
                if (flag1 && flag2) {
                    type = 2;
                    if (!zoneNum.containsKey(Integer.valueOf(idCard.substring(0, 2)))) {
                        return 0;
                    }
                }
            } else if (length == 18) {
                //验证区域码

                flag1 = regexValidate(IDCard18_REGEX, idCard);
                // 判断是不是闰年，并匹配日期是否规范
                int year = Integer.parseInt(idCard.substring(6, 10));
                if (year % 4 == 0) {
                    flag2 = regexValidate(LeapYear_REGEX, idCard.substring(10, 14));
                } else {
                    flag2 = regexValidate(OrdinaryYear_REGEX, idCard.substring(10, 14));
                }
                char[] charArray = idCard.toCharArray();
                char idCardLast = charArray[17];
                // 计算1-17位与相应因子乘积之和
                int total = 0;
                for (int i = 0; i < 17; i++) {
                    total += Character.getNumericValue(charArray[i]) * FACTOR[i];
                }
                // 判断随机码是否相等
                char ch = RANDOM[total % 11];
                if (String.valueOf(ch).toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
                    flag3 = true;
                }
                if (flag1 && flag2 && flag3) {
                    type = 3;
                    if (!zoneNum.containsKey(Integer.valueOf(idCard.substring(0, 2)))) {
                        return 0;
                    }
                }
            }
            return type;
        } catch (Exception e) {
            return 0;
        }
    }

    private static boolean regexValidate(String regex, String value) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }


    /**
     * 根据身份证号得到出生日期
     *
     * @param idCard String
     * @return String
     */
    public static String getStringBirthdayFromIdCard(String idCard) {
        if (idCard.length() < 15) {
            return "";
        }
        String birthStr;
        if (idCard.length() == 15) {
            birthStr = "19" + idCard.substring(6, 12);
        } else {
            birthStr = idCard.substring(6, 14);
        }
        StringBuilder sb = new StringBuilder(birthStr).insert(4, "-").insert(7, "-");
        return sb.toString();
    }

    /**
     * 根据身份证号得到出生日期
     *
     * @param idCard String
     * @return Date
     */
    public static Date getBirthdayFromIdCard(String idCard) {
        if (idCard.length() < 15) {
            return null;
        }
        String birthStr;
        if (idCard.length() == 15) {
            birthStr = "19" + idCard.substring(6, 12);
        } else {
            birthStr = idCard.substring(6, 14);
        }
        StringBuilder sb = new StringBuilder(birthStr).insert(4, "-").insert(7, "-");
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(sb.toString());
        } catch (Exception ignored) {
        }
        return null;
    }
}
