package com.cy.index.controller;

import com.cy.index.data.AppData;
import com.cy.index.service.BaiduIndex2ExcelService;
import com.cy.index.util.DateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * 指数控制器
 */
@Controller
public class IndexController {

    @Resource
    private BaiduIndex2ExcelService baiduIndex2ExcelService;

    @GetMapping("downloadExcel")
    public void downloadExcel(String word, Date startDate, Date endDate, HttpServletResponse response) throws IOException {
        response.setContentType("multipart/form-data");
        response.setCharacterEncoding("utf-8");
        String fileName = new String(("百度指数-" + word + "-" + DateUtil.getDateString(startDate) + "-" + DateUtil.getDateString(endDate))
                .getBytes(), StandardCharsets.ISO_8859_1);
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        ServletOutputStream out = response.getOutputStream();
        baiduIndex2ExcelService.downloadIndexExcel(word, startDate, endDate, out);
    }

    @GetMapping("bduss")
    public String bduss(String bduss) {
        AppData.bduss = bduss;
        return "downloadIndex";
    }

    @GetMapping("/index.html")
    public String toIndex() {
        return "index";
    }
}
