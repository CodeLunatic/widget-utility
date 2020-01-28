package com.cy.scel.download;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;

/**
 * 搜狗的全网Scel词库下载，2019-02-20可用
 * 运行此类可以进行搜狗输入法官方词库的批量下载，几乎涵盖了所有的官方词库
 * 大概2.36GB
 */
public class SougouScelDownLoad {

    /**
     * 搜狗官方词库可用的类别ID列表
     */
    private final static int[] types = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21,
            22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48,
            49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75,
            76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101,
            102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122,
            123, 124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143,
            144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164,
            165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 178, 180, 181, 182, 183, 184, 185, 186,
            187, 188, 189, 190, 191, 192, 193, 194, 195, 197, 198, 199, 200, 201, 202, 203, 204, 205, 206, 207, 208,
            209, 210, 211, 212, 213, 214, 215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229,
            230, 231, 233, 234, 235, 236, 237, 239, 240, 241, 242, 243, 244, 245, 246, 247, 248, 249, 250, 251, 252,
            253, 254, 255, 257, 258, 259, 260, 261, 262, 263, 264, 265, 266, 267, 269, 270, 271, 272, 273, 275, 276,
            277, 278, 279, 281, 282, 283, 284, 287, 288, 289, 290, 291, 292, 293, 294, 295, 296, 297, 298, 299, 300,
            301, 302, 303, 305, 306, 307, 308, 309, 310, 311, 312, 313, 314, 315, 316, 317, 318, 319, 320, 321, 323,
            324, 325, 326, 327, 329, 330, 331, 332, 333, 335, 336, 337, 338, 339, 341, 342, 343, 344, 345, 346, 347,
            348, 349, 350, 351, 352, 353, 354, 355, 356, 357, 358, 359, 360, 361, 362, 363, 364, 365, 366, 367, 368,
            369, 370, 371, 372, 373, 374, 375, 376, 377, 378, 379, 380, 381, 382, 383, 384, 385, 386, 387, 388, 389,
            390, 391, 392, 393, 394, 395, 396, 397, 398, 399, 400, 401, 402, 403, 404, 405, 406, 407, 408, 409, 410,
            411, 412, 413, 414, 415, 416, 417, 418, 419, 420, 421, 422, 425, 426, 427, 428, 429, 430, 431, 432, 433,
            434, 435, 436, 437, 460, 461, 548, 549, 550, 551, 552, 553, 554, 555, 556, 557, 559, 561, 564, 568, 569,
            570, 571, 572, 573, 574, 575, 576, 577, 578, 579, 580, 581, 582, 583, 584, 585, 586, 587, 588, 589, 590,
            591, 592, 593, 594, 595, 596, 597, 598, 599, 600, 602, 603, 604, 605, 606, 607, 608, 609, 610, 611, 612,
            613, 614, 615, 616, 617, 618, 619, 620, 621, 622, 623, 624, 625, 626, 627, 628, 629, 630, 636, 638, 644,
            646, 648, 650, 652, 656, 666};

    /**
     * 主函数
     *
     * @param args 运行参数
     * @throws Exception 如果异常程序立刻停止
     */
    public static void main(String[] args) throws Exception {
        startDownLoad();
    }

    /**
     * 开始下载
     *
     * @throws IOException           如果发生特殊的未预料的IO异常，会在这里抛出
     * @throws FileNotFoundException 文件未找到，或者盘符，文件名不合法
     */
    private static void startDownLoad() throws IOException, FileNotFoundException {
        int count = types.length;
        for (int type : types) {
            Document document = getPageByType(type);
            int pages = getPageCount(document);
            String typeName = getTypeName(document);
            System.err.println("正在下载：" + typeName + "，类型编号为：" + type + "，共计：" + pages + "页数据，还剩" + (count--) + "个分类");
            loopEveryPage(type, pages, typeName);
        }
    }

    /**
     * 循环每一页的数据
     *
     * @param type     类型的Id
     * @param pages    页码
     * @param typeName 类型名称
     * @throws IOException 如果连接错误
     */
    private static void loopEveryPage(int type, int pages, String typeName) throws IOException {
        for (int i = 1; i <= pages; i++) {
            Document document = getPageByPageNum(type, i);
            Elements downloadBtns = document.select(".dict_dl_btn a");
            for (Element element : downloadBtns) {
                String attr = element.attr("href"); //下载地址
                downloadOne(typeName, i, attr);
            }
        }
    }

    /**
     * 下载一个文件
     *
     * @param typeName 类型的名称（用于显示）
     * @param i        页数（用于显示）
     * @param attr     下载链接
     * @throws IOException 链接错误
     */
    private static void downloadOne(String typeName, int i, String attr) throws IOException {
        Response execute = Jsoup.connect(attr).ignoreContentType(true).maxBodySize(1024 * 1024 * 100)
                .timeout(1000 * 60 * 60).ignoreHttpErrors(true).execute(); //执行下载
        BufferedInputStream bodyStream = execute.bodyStream(); // 获取相应流
        String header = execute.header("Content-Disposition"); // 得到下载信息的头
        String fileName = header.subSequence(header.indexOf('"') + 1, header.lastIndexOf('"')).toString(); // 得到下载的文件名
        System.out.println("\t\t|-正在下载：第" + i + "页" + fileName); // 输出下载开始信息
        File file = new File("词库" + File.separator + typeName + File.separator + getPrettyFileName(fileName).replaceAll("\u000E", ""));
        createDir(file); // 创建文件夹
        bodyStream.transferTo(new FileOutputStream(file)); // 开始下载
    }

    /**
     * 创建文件夹
     *
     * @param file 文件
     */
    @SuppressWarnings("all")
    private static void createDir(File file) {
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
    }

    /**
     * 得到可用的文件名（通用性一般不包含特殊的字符，因为范围广，并且太复杂 比如说\U000E字符）
     *
     * @param fileName 文件名
     * @return 可用的文件名
     */
    private static String getPrettyFileName(String fileName) {
        if (fileName == null) return "临时文件名"; // 无文件名
        if (fileName.length() > 255) return fileName.substring(0, 10); // 裁剪一部分文件名
        if (!fileName.matches("[^\\s\\\\/:*?\"<>|](\\x20|[^\\s\\\\/:*?\"<>|])*[^\\s\\\\/:*?\"<>|.]$")) // 文件名合法验证
            return fileName.replaceAll("[\\s\\\\/:*?\"<>|]", ""); // 替换文件名中非法字符
        return fileName; // 原文件名
    }


    /**
     * 根据页码得到该页的数据
     *
     * @param type 类型Id
     * @param i    页码数
     * @return HTML页面对象
     * @throws IOException 获取错误
     */
    private static Document getPageByPageNum(int type, int i) throws IOException {
        Connection connect = Jsoup.connect("https://pinyin.sogou.com/dict/cate/index/" + type + "/default/" + i);
        return connect.get();
    }

    /**
     * 根据类型的Id获取该类型的第一页数据
     *
     * @param type 类型ID
     * @return 第一页Document
     * @throws IOException 如果获取失败
     */
    private static Document getPageByType(int type) throws IOException {
        Connection connect2 = Jsoup.connect("https://pinyin.sogou.com/dict/cate/index/" + type);
        return connect2.get();
    }

    /**
     * 得到类型的名称
     *
     * @param document 每一类别第一页的数据
     * @return 类型名称
     */
    private static String getTypeName(Document document) {
        Elements select = document.select(".cate_title");
        String text = select.text();
        return text.substring(text.indexOf('“') + 1, text.indexOf("”"));
    }

    /**
     * 得到每一类词库一共又多少页的数据（每页十条）
     *
     * @param document 每一类别第一页的数据
     * @return 解析后的页码数
     */
    private static int getPageCount(Document document) {
        Elements select2 = document.select("#dict_page_list li");
        if (select2.size() != 0) {
            Element element2 = select2.get(select2.size() - 2);
            return Integer.parseInt(element2.text());
        }
        return 1;
    }
}