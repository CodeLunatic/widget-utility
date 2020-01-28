package com.cy.index.service;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.fastjson.JSON;
import com.cy.index.data.AppData;
import com.cy.index.model.index.All;
import com.cy.index.model.index.GeneralRatioItem;
import com.cy.index.model.index.Index;
import com.cy.index.model.index.UserIndexesItem;
import com.cy.index.model.output.ExcelOutput;
import com.cy.index.model.ptbk.Ptbk;
import com.cy.index.util.DateUtil;
import com.cy.index.util.DownloadUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.*;

/**
 * 百度指数到Excel
 */
@Service
public class BaiduIndex2ExcelService {

    /**
     * 默认指数
     */
    private final String INDEX_URL = "http://index.baidu.com/api/SearchApi/index?word=${word}&area=0&startDate=${startDate}&endDate=${endDate}";

    /**
     * 资讯指数
     */
    private final String FEED_INDEX_URL = "http://index.baidu.com/api/FeedSearchApi/getFeedIndex?word=${word}&area=0&startDate=${startDate}&endDate=${endDate}";

    /**
     * XXX
     */
    private final String THUMBNAIL_URL = "http://index.baidu.com/api/SearchApi/thumbnail?area=0&word=${word}&startDate=${startDate}&endDate=${endDate}";

    /**
     * 获取密钥链接
     */
    private final String PTBK_URL = "http://index.baidu.com/Interface/ptbk?uniqid=";

    /**
     * 获取指数的Excel的输出流
     *
     * @param word      关键词
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @throws IOException #
     */
    public void downloadIndexExcel(String word, Date startDate, Date endDate, OutputStream outputStream) throws IOException {
        List<ExcelOutput> list = getDatas(word, startDate, endDate);
        ExcelWriter writer = EasyExcelFactory.getWriter(outputStream);
        Sheet sheet1 = new Sheet(1, 0);
        sheet1.setClazz(ExcelOutput.class);
        sheet1.setSheetName("百度指数");
        sheet1.setAutoWidth(true);
        writer.write(list, sheet1);
        writer.finish();
        outputStream.flush();
    }

    /**
     * 得到要导出的数据
     *
     * @param word      搜索词
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 数据
     * @throws IOException #
     */
    private List<ExcelOutput> getDatas(String word, Date startDate, Date endDate) throws IOException {
        Index index = getIndex(word, startDate, endDate);
        List<ExcelOutput> list = new ArrayList<>();
        if (index != null) {
            if (index.getData() != null && index.getData().getGeneralRatio() != null && index.getData().getGeneralRatio().size() > 0) {
                GeneralRatioItem generalRatioItem = index.getData().getGeneralRatio().get(0);
                list.add(new ExcelOutput("搜索词", generalRatioItem.getWord()));
                list.add(new ExcelOutput("整体日均值", generalRatioItem.getAll().getAvg()));
                list.add(new ExcelOutput("移动日均值", generalRatioItem.getWise().getAvg()));
            }
            if (index.getData() != null && index.getData().getUserIndexes() != null && index.getData().getUserIndexes().size() > 0) {
                UserIndexesItem userIndexesItem = index.getData().getUserIndexes().get(0);
                All all = userIndexesItem.getAll();
                String data = all.getData();
                String ptbk = getPtbk(index.getData().getUniqid());
                String decrypt = decrypt(ptbk, data);
                String[] indexs = decrypt.split(",");
                for (int i = 0; i < indexs.length; i++) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(startDate); // 开始时间
                    calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + i);
                    list.add(new ExcelOutput(DateUtil.getDateString(calendar.getTime()), indexs[i]));
                }
            }
            if (list.size() == 0) {
                if ("not login".equals(index.getMessage())) {
                    list.add(new ExcelOutput("您未登陆", "请在程序主页输入正确的BDUSS"));
                } else if ("bad request".equals(index.getMessage())) {
                    list.add(new ExcelOutput("未检索到当前词语", "你的词语" + word + "为创新词，应百度要求需要购买创新词权限!"));
                }
            }
        }
        return list;
    }


    /**
     * 得到密钥
     *
     * @param uniqid 唯一ID
     * @return 密钥
     * @throws IOException #
     */
    private String getPtbk(String uniqid) throws IOException {
        Ptbk ptbkObj = getObjectByURL(PTBK_URL + uniqid, Ptbk.class);
        return ptbkObj.getData();
    }

    /**
     * 得到基本的指数
     *
     * @param word      搜索词
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 基本指数
     * @throws IOException #
     */
    private Index getIndex(String word, Date startDate, Date endDate) throws IOException {
        String url = INDEX_URL.replace("${word}", URLEncoder.encode(word, "UTF-8")).replace("${startDate}", DateUtil.getDateString(startDate)).replace("${endDate}", DateUtil.getDateString(endDate));
        return getObjectByURL(url, Index.class);
    }

    /**
     * 通过URL获取封装好的对象
     *
     * @param urlStr 链接字符串
     * @param t      返回结果的类型
     * @param <T>    操作的实体类的类型
     * @return 返回封装好的数据
     * @throws IOException #
     */
    private <T> T getObjectByURL(String urlStr, Class<T> t) throws IOException {
        URL url = new URL(urlStr);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("Cookie", "BDUSS=" + AppData.bduss);
        InputStream inputStream = urlConnection.getInputStream();
        String s = DownloadUtils.readString(inputStream);
        return JSON.parseObject(s, t);
    }

    /**
     * 百度指数的解密算法
     *
     * @param ptbk       解密密钥
     * @param encryptStr 加密的字符串
     * @return 解密后的字符串
     */
    private String decrypt(String ptbk, String encryptStr) {
        if (ptbk == null || encryptStr == null) throw new IllegalArgumentException("非法的参数");
        HashMap<Character, Character> map = new HashMap<>();
        char[] chars = new char[encryptStr.length()];
        for (int o = 0; o < ptbk.length() / 2; o++) {
            map.put(ptbk.charAt(o), ptbk.charAt(ptbk.length() / 2 + o));
        }
        for (int s = 0; s < encryptStr.length(); s++) {
            chars[s] = map.get(encryptStr.charAt(s));
        }
        return new String(chars);
    }
}
