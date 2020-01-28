package com.cy.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HanyuPinyinHelper {

    /**
     * 汉语拼音的默认输出格式【已测试】
     */
    private static HanyuPinyinOutputFormat defaultFormat;

    /*
     * 默认拼音输出格式符合书写形式【已测试】
     */
    static {
        defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 输出拼音全部小写
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 不带声调
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V); // 设置例如lv显示成lu还是lv
    }

    private HanyuPinyinHelper() {
    }

    /**
     * 设置汉语拼音大小写
     *
     * @param hanyuPinyinCaseType 汉语拼音大小写
     */
    public static void setCaseType(HanyuPinyinCaseType hanyuPinyinCaseType) {
        defaultFormat.setCaseType(hanyuPinyinCaseType);
    }

    /**
     * 设置汉语拼音声调
     *
     * @param hanyuPinyinToneType 汉语拼音声调
     */
    public static void setToneType(HanyuPinyinToneType hanyuPinyinToneType) {
        defaultFormat.setToneType(hanyuPinyinToneType);
    }

    /**
     * 设置汉语拼音u和v类型
     *
     * @param hanyuPinyinVCharType 汉语拼音u和v类型
     */
    public static void setToneType(HanyuPinyinVCharType hanyuPinyinVCharType) {
        defaultFormat.setVCharType(hanyuPinyinVCharType);
    }

    /**
     * 将文字转为汉语拼音【已测试】
     *
     * @param chineseLanguage 要转成拼音的中文
     */
    public static String getHanyuPinyin(String chineseLanguage, String separate) {
        try {
            return PinyinHelper.toHanYuPinyinString(chineseLanguage, defaultFormat, separate, false);
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将文字转为汉语拼音的数组【已测试】
     *
     * @param chineseLanguage 要转成拼音的中文
     */
    public static String[] getHanyuPinyinArray(String chineseLanguage) {
        String separate = ",";
        String hanyuPinyin = getHanyuPinyin(chineseLanguage, separate);
        assert hanyuPinyin != null;
        return hanyuPinyin.split(separate);
    }

    /**
     * 将汉语拼音拆分开【测试并未通过】
     * 拼音规则过于复杂，所以需要很多词库的支持，当然基础的拼音拆分这样还是可以实现的
     * 2019年2月24日 19点22分【等待优化】
     *
     * @param hanyuPinyin 要拆分开的汉语拼音，想象中的样子例如：zhonghuarenmingongheguo
     * @return 想象中的样子例如：zhong hua ren min gong he guo
     */
    public static String[] splitHanyuPinyin(String hanyuPinyin) {
        List<String> tokenResult = new ArrayList<>();
        Pattern pat = Pattern.compile("[^aoeiuv]?h?[iuv]?(ai|ei|ao|ou|er|ang?|eng?|ong|a|o|e|i|u|ng|n)?");
        for (int i = hanyuPinyin.length(), tag = 0; i > 0; i = i - tag) {
            Matcher matcher = pat.matcher(hanyuPinyin);
            boolean rs = matcher.find();
            if (rs) {
                tokenResult.add(matcher.group());
                tag = matcher.end() - matcher.start();
                hanyuPinyin = hanyuPinyin.substring(tag);
            }
        }
        String[] strings = new String[tokenResult.size()];
        tokenResult.toArray(strings);
        return strings;
    }

    /**
     * 获取中文字符串中所有的中文字符的第一个首字母【已测试】
     *
     * @param chineseLanguage 中文
     * @return 第一个首字母集合例如：zhrmghg
     */
    public static String getFirstLetters(String chineseLanguage) {
        String[] hanyuPinyinArray = getHanyuPinyinArray(chineseLanguage);
        StringBuilder sb = new StringBuilder();
        for (String s : hanyuPinyinArray) {
            sb.append(s.charAt(0));
        }
        return sb.toString();
    }
}