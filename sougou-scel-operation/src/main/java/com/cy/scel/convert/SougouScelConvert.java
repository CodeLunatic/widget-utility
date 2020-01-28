package com.cy.scel.convert;

import com.cy.scel.parse.SougouScelFileParser;
import com.cy.scel.parse.SougouThesaurus;
import com.cy.scel.parse.SougouWord;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 搜狗词库解析到Txt文件中
 */
public class SougouScelConvert {

    /**
     * 主方法
     */
    public static void main(String[] args) throws FileNotFoundException {
        startParse();
    }

    /**
     * 开始解析
     *
     * @throws FileNotFoundException
     */
    @SuppressWarnings("all")
    private static void startParse() throws FileNotFoundException {
        /*
        转换后输出的位置
         */
        new File("词库汇总").mkdirs();
        PrintStream printStream = new PrintStream("词库汇总" + File.separator + "搜狗官方推荐词库.txt");
        PrintStream printStream2 = new PrintStream("词库汇总" + File.separator + "没有转换成功的文件.txt");

        /*
        遍历文件夹
         */
        File file = new File("词库");
        ArrayList<File> lists = new ArrayList<>();
        listAllFiles(file, lists);

        /*
        开始解析
         */
        for (File list : lists) {
            try {
                System.out.println("正在解析：" + list.getAbsolutePath());
                SougouThesaurus sougouThesaurus = new SougouScelFileParser().readScelFile(list.getAbsolutePath());
                List<SougouWord> swList = sougouThesaurus.getSwList();
                for (SougouWord sougouWord : swList) {
                    String pinYinString = sougouWord.getPinYinString();
                    Integer termFrequency = sougouWord.getTermFrequency();
                    String wordString = sougouWord.getWordString();
                    printStream.print(wordString + "\t" + pinYinString + "\t" + termFrequency + "\r\n");
                }
            } catch (Exception e) {
                System.err.println("解析失败：" + list.getAbsoluteFile());
                printStream2.println(list.getAbsolutePath());
            }
        }
    }

    /**
     * 过滤所有下载好的文件里面的官方推荐词库
     *
     * @param file  文件
     * @param lists 列表
     */
    private static void listAllFiles(File file, List<File> lists) {
        if (file.isDirectory()) { //如果传入的file是一个文件路径
            File[] list = file.listFiles(); //列出该路径下的所有的文件和文件夹
            if (list != null) for (File file2 : list) listAllFiles(file2, lists); //递归继续列出，直到不是一个文件夹为止
        }
        if (file.getName().endsWith("scel") && file.getName().contains("官方推荐")) lists.add(file);
    }

}
