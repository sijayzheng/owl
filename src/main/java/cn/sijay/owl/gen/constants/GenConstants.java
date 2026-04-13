package cn.sijay.owl.gen.constants;


import java.util.List;

/**
 * GenConstants
 *
 * @author sijay
 * @since 2026-04-09
 */
public interface GenConstants {

    List<String> NEEDLESS = List.of("create_dept", "create_by", "create_time", "update_by", "update_time", "deleted", "version");
    List<String> BASE_FIELD = List.of("create_dept", "create_by", "create_time", "update_by", "update_time");
    List<String> TEMPLATES = List.of(
            "controller.java",
            "entity.java",
            "mapper.java",
//                "req.java",
//                "resp.java",
            "service.java"
//                "api.ts",
//                "types.ts",
//                "dialog.vue",
//               "sql"
    );
}
