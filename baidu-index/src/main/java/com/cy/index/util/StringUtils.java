package com.cy.index.util;

/**
 * 字符串工具类
 */
public class StringUtils {

    /**
     * 判断字符串是否不为Empty
     *
     * @param value 字符串
     * @return false : 如果字符串为null或者为空串
     * true : 如果字符串不为null或者空串
     */
    public static boolean isNotEmpty(String value) {
        return value != null && !"".equals(value.trim());
    }
}
