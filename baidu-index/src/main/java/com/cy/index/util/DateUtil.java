package com.cy.index.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    /**
     * 获取日期字符串
     *
     * @param date 日期
     * @return 日期获取的字符串
     */
    public static String getDateString(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
}
