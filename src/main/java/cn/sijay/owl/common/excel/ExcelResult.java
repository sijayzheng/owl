package cn.sijay.owl.common.excel;

import java.util.List;

/**
 * ExcelResult
 *
 * @author sijay
 * @since 2026-04-15
 */
public interface ExcelResult<T> {

    /**
     * 对象列表
     */
    List<T> getList();

    /**
     * 错误列表
     */
    List<String> getErrorList();

    /**
     * 导入回执
     */
    String getAnalysis();
}

