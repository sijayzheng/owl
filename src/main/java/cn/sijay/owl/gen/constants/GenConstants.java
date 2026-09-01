package cn.sijay.owl.gen.constants;

import cn.sijay.owl.gen.enums.HtmlType;

import java.util.List;

/**
 * GenConstants
 *
 * @author sijay
 * @since 2026-04-09
 */
public interface GenConstants {

    List<String> NEEDLESS = List.of("create_by", "create_time", "update_by", "update_time", "deleted", "version");
    List<String> BASE_FIELD = List.of("create_by", "create_time", "update_by", "update_time");
    List<String> TEMPLATES = List.of(
        "controller.java",
        "entity.java",
        "query.java",
        "mapper.java",
        "service.java",
        "api.ts",
        "types.ts",
        "sql"
    );
    List<HtmlType> NEED_QUERY = List.of(HtmlType.INPUT, HtmlType.NUMBER, HtmlType.SELECT, HtmlType.RADIO, HtmlType.DATETIME, HtmlType.DATE, HtmlType.TIME);
}
