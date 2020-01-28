package com.cy.controller;

import com.cy.service.UDPNService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UDPNController {

    @Resource
    private UDPNService udpnService;

    /**
     * 汉字转换成双拼
     *
     * @param text 汉字
     * @return 双拼
     */
    @RequestMapping("hanzi2UDPN")
    public Map hanzi2UDPN(String text, String udpnType) {
        try {
            String s = udpnService.hanzi2UDPN(text, udpnType);
            return toMap(true, s);
        } catch (Exception e) {
            e.printStackTrace();
            return toMap(false, e.getMessage());
        }
    }

    /**
     * 双拼转拼音
     *
     * @param text 双拼
     * @return 拼音
     */
    @RequestMapping("udpn2Pinyin")
    public Map<String, Object> udpn2Pinyin(String text, String udpnType) {
        try {
            String s = udpnService.udpn2Pinyin(text, udpnType);
            return toMap(true, s);
        } catch (Exception e) {
            e.printStackTrace();
            return toMap(false, e.getMessage());
        }
    }

    /**
     * 拼音到双拼
     *
     * @param text 拼音
     * @return 双拼
     */
    @RequestMapping("pinyin2UDPN")
    public Map<String, Object> pinyin2UDPN(String text, String udpnType) {
        try {
            String s = udpnService.pinyin2UDPN(text, udpnType);
            return toMap(true, s);
        } catch (Exception e) {
            e.printStackTrace();
            return toMap(false, e.getMessage());
        }
    }

    /**
     * 返回前台的信息
     *
     * @param success 成功与否
     * @param message 返回前台的信息
     * @return 返回的MAP
     */
    private Map<String, Object> toMap(Boolean success, String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", success);
        map.put("message", message);
        return map;
    }
}
