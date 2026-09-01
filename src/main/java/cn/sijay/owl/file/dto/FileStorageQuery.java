package cn.sijay.owl.file.dto;

import java.time.LocalDateTime;
/**
 * 文件存储实体类
 *
 * @author sijay
 * @since 2026-04-09
 */
public record FileStorageQuery (
    String originalName,
    String fileSuffix,
    String contentType
){
}
