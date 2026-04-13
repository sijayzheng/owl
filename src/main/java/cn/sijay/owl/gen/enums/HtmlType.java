package cn.sijay.owl.gen.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * HtmlType
 *
 * @author sijay
 * @since 2026-04-08
 */
@Getter
@AllArgsConstructor
public enum HtmlType {
    INPUT("输入框"),
    TEXTAREA("文本域"),
    NUMBER("数字框"),
    SELECT("下拉框"),
    RADIO("单选框"),
    DATETIME("日期选择"),
    DATE("日期"),
    TIME("时间"),
    IMAGE("图片上传"),
    FILE("文件上传"),
    EDITOR("富文本");

    private final String description;
}