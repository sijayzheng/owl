package cn.sijay.owl.common.excel;

import cn.sijay.owl.common.annotations.CellMerge;
import cn.sijay.owl.common.constants.ErrorConstants;
import cn.sijay.owl.common.exceptions.BaseException;
import cn.sijay.owl.common.utils.DateTimeUtil;
import cn.sijay.owl.common.utils.FileUtil;
import cn.sijay.owl.common.utils.ReflectUtil;
import cn.sijay.owl.common.utils.StringUtil;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.apache.fesod.sheet.FesodSheet;
import org.apache.fesod.sheet.annotation.ExcelProperty;
import org.apache.fesod.sheet.metadata.Head;
import org.apache.fesod.sheet.write.builder.ExcelWriterSheetBuilder;
import org.apache.fesod.sheet.write.merge.AbstractMergeStrategy;
import org.apache.fesod.sheet.write.style.column.LongestMatchColumnWidthStyleStrategy;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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

    /**
     * 导出excel
     *
     * @param list      导出数据集合
     * @param sheetName 工作表的名称
     * @param clazz     实体类
     * @param response  响应体
     */
    public static <T> void export(List<T> list, String sheetName, Class<T> clazz, HttpServletResponse response) {
        try {
            resetResponse(sheetName, response);
            ServletOutputStream os = response.getOutputStream();
            exportExcel(list, sheetName, clazz, false, os);
        } catch (IOException e) {
            throw new RuntimeException("导出Excel异常");
        }
    }

    /**
     * 导出excel
     *
     * @param list      导出数据集合
     * @param sheetName 工作表的名称
     * @param clazz     实体类
     * @param merge     是否合并单元格
     * @param response  响应体
     */
    public static <T> void export(List<T> list, String sheetName, Class<T> clazz, boolean merge, HttpServletResponse response) {
        try {
            resetResponse(sheetName, response);
            ServletOutputStream os = response.getOutputStream();
            exportExcel(list, sheetName, clazz, merge, os);
        } catch (IOException e) {
            throw new RuntimeException("导出Excel异常");
        }
    }

    /**
     * 导出excel
     *
     * @param list      导出数据集合
     * @param sheetName 工作表的名称
     * @param clazz     实体类
     * @param os        输出流
     */
    public static <T> void export(List<T> list, String sheetName, Class<T> clazz, OutputStream os) {
        exportExcel(list, sheetName, clazz, false, os);
    }

    /**
     * 导出excel
     *
     * @param list      导出数据集合
     * @param sheetName 工作表的名称
     * @param clazz     实体类
     * @param merge     是否合并单元格
     * @param os        输出流
     */
    public static <T> void exportExcel(List<T> list, String sheetName, Class<T> clazz, boolean merge, OutputStream os) {
        ExcelWriterSheetBuilder builder = FesodSheet.write(os, clazz)
                                                    .autoCloseStream(false)
                                                    // 自动适配
                                                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                                                    // 大数值自动转换 防止失真
//                                                   .registerConverter(new ExcelBigNumberConvert())
                                                    .sheet(sheetName);
        if (merge) {
            // 合并处理器
//            builder.registerWriteHandler(new CellMergeStrategy(list, true));
        }
        // 添加下拉框操作
        builder.doWrite(list);
    }

    public static <T> List<T> read(MultipartFile file, Class<T> clazz) {
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            HashMap<Integer, String> titleMap = getTitle(sheet);
            List<HashMap<String, String>> list = getData(sheet, titleMap);
            return convertList(list, clazz);
        } catch (IOException e) {
            throw new BaseException(ErrorConstants.EXCEL_READ_ERROR);
        }
    }

    /**
     * 下载文件名重新编码
     *
     * @param response     响应对象
     * @param realFileName 真实文件名
     */
    public static void setAttachmentResponseHeader(HttpServletResponse response, String realFileName) {
        String percentEncodedFileName = FileUtil.percentEncode(realFileName);
        String contentDispositionValue = "attachment; filename=" + percentEncodedFileName + ";" + "filename*=" + "utf-8''" + percentEncodedFileName;
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition,filename");
        response.setHeader("Content-disposition", contentDispositionValue);
        response.setHeader("filename", percentEncodedFileName);
    }

    /**
     * 重置响应体
     */
    private static void resetResponse(String sheetName, HttpServletResponse response) {
        String filename = sheetName + DateTimeUtil.nowFile() + ".xlsx";
        String encode = URLEncoder.encode(filename, StandardCharsets.UTF_8);
        String percentEncodedFileName = encode.replaceAll("\\+", "%20");
        String contentDispositionValue = "attachment; filename=%s;filename*=utf-8''%s".formatted(percentEncodedFileName, percentEncodedFileName);
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition,download-filename");
        response.setHeader("Content-disposition", contentDispositionValue);
        response.setHeader("download-filename", percentEncodedFileName);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
    }

    public static <T> void exportTemplate(String name, Class<T> clazz, HttpServletResponse response) {
        try {
            FesodSheet.write(response.getOutputStream(), clazz)
                      .autoCloseStream(false)
                      // 自动适配
                      .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                      .sheet(name).doWrite(new ArrayList<>());
        } catch (IOException e) {
            throw new BaseException(ErrorConstants.EXCEL_TEMPLATE_EXPORT_ERROR);
        }
    }

    static class CellMergeStrategy extends AbstractMergeStrategy {

        private final List<CellRangeAddress> cellList;
        private final boolean hasTitle;
        private int rowIndex;

        public CellMergeStrategy(List<?> list, boolean hasTitle) {
            this.hasTitle = hasTitle;
            // 行合并开始下标
            this.rowIndex = hasTitle ? 1 : 0;
            this.cellList = handle(list, hasTitle);
        }

        public CellMergeStrategy(List<?> list, boolean hasTitle, int rowIndex) {
            this.hasTitle = hasTitle;
            // 行合并开始下标
            this.rowIndex = rowIndex;
            this.cellList = handle(list, hasTitle);
        }

        @Override
        protected void merge(Sheet sheet, Cell cell, Head head, Integer relativeRowIndex) {
            // judge the list is not null
            if (CollectionUtils.isNotEmpty(cellList)) {
                // the judge is necessary
                if (cell.getRowIndex() == rowIndex && cell.getColumnIndex() == 0) {
                    for (CellRangeAddress item : cellList) {
                        sheet.addMergedRegion(item);
                    }
                }
            }
        }

        @SneakyThrows
        private List<CellRangeAddress> handle(List<?> list, boolean hasTitle) {
            List<CellRangeAddress> cellList = new ArrayList<>();
            if (CollectionUtils.isEmpty(list)) {
                return cellList;
            }
            Class<?> clazz = list.getFirst().getClass();
            List<Field> fields = Arrays.stream(clazz.getDeclaredFields()).filter(field -> !"serialVersionUID".equals(field.getName())).toList();
            // 有注解的字段
            List<Field> mergeFields = new ArrayList<>();
            Field mainField = null;
            int mainFieldIndex = 0;
            List<Integer> mergeFieldsIndex = new ArrayList<>();
            for (int i = 0; i < fields.size(); i++) {
                Field field = fields.get(i);
                if (field.isAnnotationPresent(CellMerge.class)) {
                    CellMerge cm = field.getAnnotation(CellMerge.class);
                    if (cm.main()) {
                        mainField = field;
                        mainFieldIndex = cm.index() == -1 ? i : cm.index();
                    } else {
                        mergeFields.add(field);
                        mergeFieldsIndex.add(cm.index() == -1 ? i : cm.index());
                    }
                    if (hasTitle) {
                        ExcelProperty property = field.getAnnotation(ExcelProperty.class);
                        rowIndex = Math.max(rowIndex, property.value().length);
                    }
                }
            }

            Map<Field, RepeatCell> map = new HashMap<>();
            // 生成两两合并单元格
            if (mainField == null) {
                for (int i = 0; i < list.size(); i++) {
                    for (int j = 0; j < mergeFields.size(); j++) {
                        Field field = mergeFields.get(j);
                        Object val = ReflectUtil.invokeGetter(clazz, list.get(i), field.getName());
                        int colNum = mergeFieldsIndex.get(j);
                        if (!map.containsKey(field)) {
                            map.put(field, new RepeatCell(val, i));
                        } else {
                            RepeatCell repeatCell = map.get(field);
                            Object cellValue = repeatCell.getValue();
                            if (cellValue == null || "".equals(cellValue) || val == null || "".equals(val)) {
                                // 空值跳过不合并
                                if (cellValue != null && !"".equals(cellValue) && repeatCell.getCurrent() + rowIndex != i + rowIndex - 1) {
                                    cellList.add(new CellRangeAddress(repeatCell.getCurrent() + rowIndex, i + rowIndex - 1, colNum, colNum));
                                }
                                map.put(field, new RepeatCell(val, i));
                            } else {
                                if (!Objects.equals(cellValue, val)) {
                                    if (i - repeatCell.getCurrent() > 1) {
                                        cellList.add(new CellRangeAddress(repeatCell.getCurrent() + rowIndex, i + rowIndex - 1, colNum, colNum));
                                    }
                                    map.put(field, new RepeatCell(val, i));
                                } else if (i == list.size() - 1) {
                                    if (i > repeatCell.getCurrent()) {
                                        cellList.add(new CellRangeAddress(repeatCell.getCurrent() + rowIndex, i + rowIndex, colNum, colNum));
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                for (int i = 0; i < list.size(); i++) {
                    Object val = ReflectUtil.invokeGetter(clazz, list.get(i), mainField.getName());
                    if (!map.containsKey(mainField)) {
                        map.put(mainField, new RepeatCell(val, i));
                    } else {
                        RepeatCell repeatCell = map.get(mainField);
                        Object cellValue = repeatCell.getValue();
                        if (cellValue == null || "".equals(cellValue) || val == null || "".equals(val)) {
                            // 空值跳过不合并
                            if (cellValue != null && !"".equals(cellValue) && repeatCell.getCurrent() + rowIndex != i + rowIndex - 1) {
                                cellList.add(new CellRangeAddress(repeatCell.getCurrent() + rowIndex, i + rowIndex - 1, mainFieldIndex, mainFieldIndex));
                                for (int j = 0; j < mergeFields.size(); j++) {
                                    cellList.add(new CellRangeAddress(repeatCell.getCurrent() + rowIndex, i + rowIndex - 1, mergeFieldsIndex.get(j), mergeFieldsIndex.get(j)));
                                }
                            }
                            map.put(mainField, new RepeatCell(val, i));
                        } else {
                            if (!Objects.equals(cellValue, val)) {
                                if (i - repeatCell.getCurrent() > 1) {
                                    cellList.add(new CellRangeAddress(repeatCell.getCurrent() + rowIndex, i + rowIndex - 1, mainFieldIndex, mainFieldIndex));
                                    for (int j = 0; j < mergeFields.size(); j++) {
                                        cellList.add(new CellRangeAddress(repeatCell.getCurrent() + rowIndex, i + rowIndex - 1, mergeFieldsIndex.get(j), mergeFieldsIndex.get(j)));
                                    }
                                }
                                map.put(mainField, new RepeatCell(val, i));
                            } else if (i == list.size() - 1) {
                                if (i > repeatCell.getCurrent()) {
                                    cellList.add(new CellRangeAddress(repeatCell.getCurrent() + rowIndex, i + rowIndex, mainFieldIndex, mainFieldIndex));
                                    for (int j = 0; j < mergeFields.size(); j++) {
                                        cellList.add(new CellRangeAddress(repeatCell.getCurrent() + rowIndex, i + rowIndex - 1, mergeFieldsIndex.get(j), mergeFieldsIndex.get(j)));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return cellList;
        }

        @Data
        @AllArgsConstructor
        static class RepeatCell {

            private Object value;

            private int current;

        }
    }

    /**
     * 同步导入(适用于小数据量)
     *
     * @param is 输入流
     * @return 转换后集合
     */
    public static <T> List<T> importExcel(InputStream is, Class<T> clazz) {
        return FesodSheet.read(is).head(clazz).autoCloseStream(false).sheet().doReadSync();
    }

    /**
     * 使用自定义监听器 异步导入 自定义返回
     *
     * @param is       输入流
     * @param clazz    对象类型
     * @param listener 自定义监听器
     * @return 转换后集合
     */
    public static <T> ExcelResult<T> importExcel(InputStream is, Class<T> clazz, ExcelListener<T> listener) {
        FesodSheet.read(is, clazz, listener).sheet().doRead();
        return listener.getExcelResult();
    }

    /**
     * 导出excel
     *
     * @param list      导出数据集合
     * @param sheetName 工作表的名称
     * @param clazz     实体类
     * @param response  响应体
     */
    public static <T> void exportExcel(List<T> list, String sheetName, Class<T> clazz, HttpServletResponse response) {
        exportExcel(list, sheetName, clazz, false, response);
    }

    /**
     * 导出excel
     *
     * @param list      导出数据集合
     * @param sheetName 工作表的名称
     * @param clazz     实体类
     * @param merge     是否合并单元格
     * @param response  响应体
     */
    public static <T> void exportExcel(List<T> list, String sheetName, Class<T> clazz, boolean merge, HttpServletResponse response) {
        try {
            resetResponse(sheetName, response);
            ServletOutputStream os = response.getOutputStream();
            exportExcel(list, sheetName, clazz, merge, os);
        } catch (IOException e) {
            throw new RuntimeException("导出Excel异常");
        }
    }

    /**
     * 导出excel
     *
     * @param list      导出数据集合
     * @param sheetName 工作表的名称
     * @param clazz     实体类
     * @param os        输出流
     */
    public static <T> void exportExcel(List<T> list, String sheetName, Class<T> clazz, OutputStream os) {
        exportExcel(list, sheetName, clazz, false, os);
    }


    /**
     * 使用校验监听器 异步导入 同步返回
     *
     * @param is         输入流
     * @param clazz      对象类型
     * @param isValidate 是否 Validator 检验 默认为是
     * @return 转换后集合
     */
    public static <T> ExcelResult<T> importExcel(InputStream is, Class<T> clazz, boolean isValidate) {
        DefaultExcelListener<T> listener = new DefaultExcelListener<>(isValidate);
        FesodSheet.read(is, clazz, listener).sheet().doRead();
        return listener.getExcelResult();
    }

}
