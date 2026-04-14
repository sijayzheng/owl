package cn.sijay.owl.common.utils;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * FileUtil
 *
 * @author sijay
 * @since 2026-04-09
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
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

    /**
     * 获取文件扩展名
     *
     * @param file 文件
     * @return 扩展名
     */
    public static String getExtension(File file) {
        return getExtension(file.getName());
    }

    /**
     * 获取文件扩展名
     *
     * @param filename 文件名
     * @return 扩展名
     */
    public static String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf("."));
    }

    /**
     * 写入文件
     *
     * @param file    文件
     * @param content 内容
     */
    public static void writeFile(File file, String content) {
        try {
            if (!file.exists()) {
                try {
                    if (!file.getParentFile().exists()) {
                        Files.createDirectories(file.getParentFile().toPath());
                    }
                    Files.createFile(file.toPath());
                } catch (Exception e) {
                    log.error("create file error", e);
                }
            }
            Files.writeString(file.toPath(), content);
        } catch (Exception e) {
            log.error("write file error", e);
        }
    }

    /**
     * 写入文件
     *
     * @param path    文件路径
     * @param content 内容
     */
    public static void writeFile(String path, String content) {
        writeFile(new File(path), content);
    }

    /**
     * 拼接路径
     *
     * @param path 路径
     * @return 拼接后的路径
     */
    public static String concatPath(String... path) {
        return String.join(File.separator, path);
    }

    public static List<File> listFile(String path) {
        List<File> list = new ArrayList<>();
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    list.addAll(listFile(f.getPath()));
                }
            }
        } else {
            list.add(file);
        }
        return list;
    }

    public static String readFile(String path) {
        return readFile(new File(path));
    }

    public static String readFile(File file) {
        try {
            if (!file.exists()) {
                log.error("file not exists");
                return "";
            }
            return String.join("\n", Files.readAllLines(file.toPath()));
        } catch (Exception e) {
            log.error("write file error", e);
            return "";
        }
    }

    /**
     * 百分号编码工具方法
     *
     * @param s 需要百分号编码的字符串
     * @return 百分号编码后的字符串
     */
    public static String percentEncode(String s) {
        String encode = URLEncoder.encode(s, StandardCharsets.UTF_8);
        return encode.replaceAll("\\+", "%20");
    }

    public static List<File> listFiles(String path) {
        return listFiles(new File(path));
    }

    public static List<File> listFiles(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null) {
                return List.of();
            } else {
                List<File> list = new ArrayList<>();
                for (File f : files) {
                    if (f.isDirectory()) {
                        list.addAll(listFiles(f));
                    } else {
                        list.add(f);
                    }
                }
                return list;
            }
        } else {
            return List.of(file);
        }
    }
}
