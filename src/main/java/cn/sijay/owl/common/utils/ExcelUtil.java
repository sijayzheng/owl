package cn.sijay.owl.common.utils;

import cn.sijay.owl.common.constants.ErrorConstants;
import cn.sijay.owl.common.exceptions.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.apache.fesod.sheet.FesodSheet;
import org.apache.fesod.sheet.annotation.ExcelProperty;
import org.apache.fesod.sheet.write.style.column.LongestMatchColumnWidthStyleStrategy;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ExcelUtil
 *
 * @author sijay
 * @since 2026-04-08
 */
@Slf4j
public class ExcelUtil {

    public static <T> ResponseEntity<Resource> exportExcel(List<T> list, String sheetName, Class<T> clazz) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            // 使用 FastExcel 写入缓冲区
            FesodSheet.write(bos, clazz)
                      .autoCloseStream(true)
                      .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                      .sheet(sheetName)
                      .doWrite(list);
            // 生成文件名
            String filename = sheetName + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")) + ".xlsx";
            String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8);
            String percentEncodedFileName = encodedFilename.replaceAll("\\+", "%20");
            return ResponseEntity.ok()
                                 .contentType(MediaType.APPLICATION_OCTET_STREAM)
                                 .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + percentEncodedFileName + "\"")
                                 .body(new ByteArrayResource(bos.toByteArray()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> importExcel(InputStream inputStream, Class<T> clazz) {
        try (Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            HashMap<Integer, String> titleMap = getTitle(sheet);
            List<HashMap<String, String>> list = getData(sheet, titleMap);
            if (CollectionUtils.isEmpty(list)) {
                throw new BaseException("导入数据为空");
            }
            return convertList(list, clazz);
        } catch (IOException e) {
            throw new BaseException(ErrorConstants.EXCEL_READ_ERROR);
        }
    }

    private static HashMap<Integer, String> getTitle(Sheet sheet) {
        HashMap<Integer, String> titleMap = new HashMap<>();
        for (Cell cell : sheet.getRow(0)) {
            titleMap.put(cell.getColumnIndex(), getCellValue(cell));
        }
        return titleMap;
    }

    private static String getCellValue(Cell cell) {
        return switch (cell.getCellType()) {
            case NUMERIC -> org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell) ?
                    cell.getLocalDateTimeCellValue().toString() :
                    BigDecimal.valueOf(cell.getNumericCellValue()).toString();
            case STRING -> cell.getRichStringCellValue().getString();
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            case ERROR -> String.valueOf(cell.getErrorCellValue());
            default -> cell.getRichStringCellValue().toString();
        };
    }

    private static List<HashMap<String, String>> getData(Sheet sheet, HashMap<Integer, String> titleMap) {
        List<HashMap<String, String>> list = new ArrayList<>();
        for (int j = 1; j <= sheet.getLastRowNum(); j++) {
            HashMap<String, String> map = new HashMap<>();
            for (Cell cell : sheet.getRow(j)) {
                map.put(titleMap.get(cell.getColumnIndex()), getCellValue(cell));
            }
            list.add(map);
        }
        return list;
    }

    private static <T> List<T> convertList(List<HashMap<String, String>> list, Class<T> clazz) {
        return list.stream().map(map -> {
            try {
                T t = clazz.getDeclaredConstructor().newInstance();
                for (Field field : clazz.getDeclaredFields()) {
                    ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
                    if (annotation != null) {
                        field.setAccessible(true);
                        field.set(t, map.get(annotation.value()[0]));
                    }
                }
                return t;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                log.error("{} 实例化失败", clazz.getName());
                return null;
            }
        }).filter(ObjectUtils::isNotEmpty).collect(Collectors.toList());
    }

    /**
     * 反向解析值 男=0,女=1,未知=2
     *
     * @param propertyValue 参数值
     * @param converterExp  翻译注解
     * @param separator     分隔符
     * @return 解析后值
     */
    public static String reverseByExp(String propertyValue, String converterExp, String separator) {
        StringBuilder propertyString = new StringBuilder();
        String[] convertSource = converterExp.split(StringUtil.SEPARATOR);
        for (String item : convertSource) {
            String[] itemArray = item.split("-");
            if (Strings.CS.containsAny(propertyValue, separator)) {
                for (String value : propertyValue.split(separator)) {
                    if (itemArray[1].equals(value)) {
                        propertyString.append(itemArray[0]).append(separator);
                        break;
                    }
                }
            } else {
                if (itemArray[1].equals(propertyValue)) {
                    return itemArray[0];
                }
            }
        }
        return StringUtils.stripEnd(propertyString.toString(), separator);
    }

    /**
     * 解析导出值 0=男,1=女,2=未知
     *
     * @param propertyValue 参数值
     * @param converterExp  翻译注解
     * @param separator     分隔符
     * @return 解析后值
     */
    public static String convertByExp(String propertyValue, String converterExp, String separator) {
        StringBuilder propertyString = new StringBuilder();
        String[] convertSource = converterExp.split(StringUtil.SEPARATOR);
        for (String item : convertSource) {
            String[] itemArray = item.split("=");
            if (Strings.CS.containsAny(propertyValue, separator)) {
                for (String value : propertyValue.split(separator)) {
                    if (itemArray[0].equals(value)) {
                        propertyString.append(itemArray[1]).append(separator);
                        break;
                    }
                }
            } else {
                if (itemArray[0].equals(propertyValue)) {
                    return itemArray[1];
                }
            }
        }
        return StringUtils.stripEnd(propertyString.toString(), separator);
    }
}
