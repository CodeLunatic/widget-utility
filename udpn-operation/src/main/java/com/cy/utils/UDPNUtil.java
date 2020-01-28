package com.cy.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.List;

/**
 * 双拼工具类
 *
 * @author CY
 */
public class UDPNUtil {

    private static Document document;

    static {
        try {
            document = new SAXReader().read(UDPNUtil.class.getClassLoader().getResourceAsStream("pinyin_table.xml"));
        } catch (DocumentException e) {
            System.err.println("请将pinyin_table.xml文件放到classpath下");
            e.printStackTrace();
        }
    }

    /**
     * 单个小写拼音转换为双拼（注意这里的拼音要求必须是：一个拼音，小写，不能为null，拼音在现实中真正存在）
     *
     * @return 双拼
     */
    public static String pinyin2UDPN(String pinyin, UDPNType udpnType) {
        if (pinyin == null)
            throw new IllegalArgumentException("拼音参数不能为空");
        Element element = document.elementByID(pinyin);
        if (element == null) {
            throw new IllegalArgumentException("请确保拼音参数格式正确并且是单个拼音");
        }
        return element.element(udpnType.getKey()).getText();
    }

    /**
     * 单个双拼转换为拼音 （这里的双拼不区分大小写，但是只能为1个，且不能为null，现实中真实存在）
     *
     * @return 拼音
     */
    public static String udpn2Pinyin(String udpn, UDPNType udpnType) {
        if (udpn == null)
            throw new IllegalArgumentException("双拼参数不能为空");
        Element rootElement = document.getRootElement();
        List<Element> elements = rootElement.elements();
        for (Element element : elements) {
            String text = element.element(udpnType.getKey()).getText();
            if (udpn.equalsIgnoreCase(text))
                return element.attributeValue("ID");
        }
        throw new IllegalArgumentException("请检查你的双拼参数是否正确");
    }
}
