package com.cy.utils;

/**
 * 双拼的类型
 *
 * @author CY
 */
public enum UDPNType {

    /**
     * 微软双拼
     */
    MS_UDPN("weiruan", "微软双拼"),

    /**
     * 国标双拼
     */
    GB_UDPN("guobiao", "国标双拼"),

    /**
     * 拼音加加双拼
     */
    PYJJ_UDPN("pinyinjiajia", "拼音加加双拼"),

    /**
     * 搜狗双拼
     */
    SOUGOU_UDPN("sougou", "搜狗双拼"),

    /**
     * 小鹤双拼
     */
    XIAOHE_UDPN("xiaohe", "小鹤双拼"),

    /**
     * 智能ABC双拼
     */
    ZHINENGABC_UDPN("zhinengabc", "智能ABC双拼"),

    /**
     * 紫光双拼
     */
    ZIGUANG_UDPN("ziguang", "紫光双拼"),

    /**
     * 自然码双拼
     */
    ZIRANMA_UDPN("ziranma", "自然码双拼");

    private String key;

    private String value;

    private UDPNType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
