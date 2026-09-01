package cn.sijay.owl.system.dto;

import java.time.LocalDateTime;
/**
 * 字典类型实体类
 *
 * @author sijay
 * @since 2026-04-09
 */
public record SysDictTypeQuery (
    String typeName,
    String typeCode
){
}
