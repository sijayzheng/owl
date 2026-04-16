package cn.sijay.owl.common.excel;

import org.apache.fesod.sheet.read.listener.ReadListener;

/**
 * ExcelListener
 *
 * @author sijay
 * @since 2026-04-15
 */
public interface ExcelListener<T> extends ReadListener<T> {
    ExcelResult<T> getExcelResult();
}
