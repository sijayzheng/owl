package cn.sijay.owl.file.entity;

import cn.sijay.owl.common.base.BaseEntity;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.apache.fesod.sheet.annotation.ExcelIgnoreUnannotated;
import org.apache.fesod.sheet.annotation.ExcelProperty;

/**
 * OSS实体类
 *
 * @author sijay
 * @since 2026-04-09
 */
@ExcelIgnoreUnannotated
@Data
@Table(value = "file_oss_storage", comment = "OSS对象存储表")
public class FileOssStorage extends BaseEntity {

    /**
     * 主键
     */
    @Id(keyType = KeyType.Auto, comment = "主键")
    @Column(value = "id", comment = "主键")
    private Long id;

    /**
     * 文件名
     */
    @Column(value = "file_name", comment = "文件名")
    @NotBlank(message = "文件名不能为空")
    @ExcelProperty(value = "文件名")
    private String fileName;

    /**
     * 原名
     */
    @Column(value = "original_name", comment = "原名")
    @NotBlank(message = "原名不能为空")
    @ExcelProperty(value = "原名")
    private String originalName;

    /**
     * 文件后缀名
     */
    @Column(value = "file_suffix", comment = "文件后缀名")
    @NotBlank(message = "文件后缀名不能为空")
    @ExcelProperty(value = "文件后缀名")
    private String fileSuffix;

    /**
     * 文件大小(字节)
     */
    @Column(value = "file_size", comment = "文件大小")
    @ExcelProperty(value = "文件大小")
    private Long fileSize;

    /**
     * MIME类型
     */
    @Column(value = "content_type", comment = "MIME类型")
    @ExcelProperty(value = "MIME类型")
    private String contentType;

    /**
     * url地址
     */
    @Column(value = "url", comment = "url地址")
    @NotBlank(message = "url地址不能为空")
    @ExcelProperty(value = "url地址")
    private String url;

    /**
     * 服务商
     */
    @Column(value = "service", comment = "服务商")
    @NotBlank(message = "服务商不能为空")
    @ExcelProperty(value = "服务商")
    private String service;

    /**
     * 删除
     */
    @Column(value = "deleted", comment = "删除")
    private Boolean deleted;

}
