package cn.sijay.owl.system.dto;

import java.time.LocalDateTime;
/**
 * 字典数据实体类
 *
 * @author sijay
 * @since 2026-04-09
 */
public record SysDictDataQuery (
    Long dictTypeId,
    String dictLabel
){
}
