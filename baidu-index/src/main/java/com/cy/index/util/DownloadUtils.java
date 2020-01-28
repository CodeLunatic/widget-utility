package com.cy.index.util;

import java.io.*;

/**
 * 下载工具类
 */
public class DownloadUtils {

    /**
     * 文件的结尾
     */
    private static final int EOF = -1;

    /**
     * 默认buffer尺寸
     */
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    /**
     * 读取所有的字节数组
     *
     * @param input 输入流
     * @return 所有的字节数组
     */
    private static byte[] readAllBytes(final InputStream input) {
        BufferedInputStream bufferedInput = new BufferedInputStream(input);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = bufferedInput.read(buffer)) != EOF) {
                output.write(buffer, 0, length);
            }
            return output.toByteArray();
        } catch (IOException ignored) {
        } finally {
            try {
                output.close();
                bufferedInput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new byte[0];
    }

    /**
     * 读取所有的输入流到字符串中
     *
     * @param inputStream 输入流
     * @return 字符串
     */
    public static String readString(InputStream inputStream) throws UnsupportedEncodingException {
        return new String(readAllBytes(inputStream), "UTF-8");
    }
}
