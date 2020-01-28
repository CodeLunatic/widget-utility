package com.cy.service;

import com.cy.utils.HanyuPinyinHelper;
import com.cy.utils.UDPNType;
import com.cy.utils.UDPNUtil;
import org.springframework.stereotype.Service;

/**
 * 一个拼音的服务类
 */
@Service
public class UDPNService {

    /**
     * 汉字转换成双拼
     *
     * @param hanzi 汉字
     * @return 双拼
     */
    public String hanzi2UDPN(String hanzi, String udpnType) {
        hanzi = hanzi.replaceAll(" ", "");
        String[] hanyuPinyinArray = HanyuPinyinHelper.getHanyuPinyinArray(hanzi);
        StringBuilder sb = new StringBuilder();
        for (String s : hanyuPinyinArray) {
            try {
                sb.append(UDPNUtil.pinyin2UDPN(s, getUdpnType(udpnType))).append(" ");
            } catch (Exception e) {
                String[] strings = HanyuPinyinHelper.splitHanyuPinyin(s);
                for (String string : strings) {
                    sb.append(UDPNUtil.pinyin2UDPN(string, getUdpnType(udpnType))).append(" ");
                }
            }
        }
        return sb.toString();
    }

    /**
     * 双拼转拼音
     *
     * @param udpn 双拼
     * @return 拼音
     */
    public String udpn2Pinyin(String udpn, String udpnType) {
        udpn = udpn.replaceAll(" ", "");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < udpn.length(); i += 2) {
            String udpnString = udpn.substring(i, i + 2);
            sb.append(UDPNUtil.udpn2Pinyin(udpnString, getUdpnType(udpnType))).append(" ");
        }
        return sb.toString();
    }

    /**
     * 拼音到双拼
     *
     * @param pinyin 拼音
     * @return 双拼
     */
    public String pinyin2UDPN(String pinyin, String udpnType) {
        pinyin = pinyin.replaceAll(" ", "");
        StringBuilder sb = new StringBuilder();
        String[] strings = HanyuPinyinHelper.splitHanyuPinyin(pinyin);
        for (String string : strings) {
            sb.append(UDPNUtil.pinyin2UDPN(string, getUdpnType(udpnType))).append(" ");
        }
        return sb.toString();
    }

    /**
     * 根据字符串得到双拼的类型
     *
     * @param udpnType 双拼类型的字符串
     * @return 双拼类型
     */
    private UDPNType getUdpnType(String udpnType) {

        switch (udpnType) {
            case "wzrr":
                return UDPNType.MS_UDPN;
            case "gobc":
                return UDPNType.GB_UDPN;
            case "jwjw":
                return UDPNType.PYJJ_UDPN;
            case "sbgb":
                return UDPNType.SOUGOU_UDPN;
            case "xche":
                return UDPNType.XIAOHE_UDPN;
            case "ving":
                return UDPNType.ZHINENGABC_UDPN;
            case "zigd":
                return UDPNType.ZIGUANG_UDPN;
            case "zirj":
                return UDPNType.ZIRANMA_UDPN;
            default:
                return UDPNType.MS_UDPN;
        }
    }
}
