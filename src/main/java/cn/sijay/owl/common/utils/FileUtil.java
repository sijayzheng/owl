package cn.sijay.owl.common.utils;


import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * FileUtil
 *
 * @author sijay
 * @since 2026/4/9
 */
public class FileUtil {
    static final String DATE_FILE_NAME_FORMAT = "yyyyMMdd";
    static final String DATE_TIME_FILE_NAME_FORMAT = "yyyyMMddHHmmss";
    static final String CODE_UTF8 = "UTF-8";
    static final String CODE_UTF8_BOM = "UTF-8_BOM";
    static final String CODE_GBK = "GBK";
    static final int BYTE_SIZE = 8;

    public static String joinPath(String... path) {
        return StringUtils.join(path, File.separator);
    }

    public static void writeToFile(String path, String content) {
        System.out.println(path);
        writeToFile(new File(path), content);
    }

    public static void writeToFile(String path, String content, Charset charset) {
        writeToFile(new File(path), content, charset);
    }

    public static void writeToFile(File file, String content) {
        writeToFile(file, content, StandardCharsets.UTF_8);
    }

    public static void writeToFile(File file, String content, Charset charset) {
        try {
            // 获取父目录
            Path path = file.toPath();
            Path parentDir = path.getParent();
            // 如果父目录不为空且不存在，则创建父目录
            if (parentDir != null && !Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }
            Files.writeString(path, content, charset, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException("写入文件失败", e);
        }
    }
}
