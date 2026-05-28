package cn.sijay.owl.gen;

import cn.sijay.owl.gen.entity.GenTable;
import cn.sijay.owl.gen.service.GenService;
import cn.sijay.owl.gen.service.GenTableService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

/**
 * GenTest
 *
 * @author sijay
 * @since 2026-04-09
 */
@SpringBootTest
public class GenTest {
    @Autowired
    private GenService genService;
    @Autowired
    private GenTableService genTableService;


    @Test
    public void importTable() {
        genService.importTable(Arrays.stream("""
                                         sys_message
                                         sys_role
                                         sys_task
                                         sys_post
                                         sys_user_post
                                         sys_menu
                                         sys_dict_type
                                         sys_config
                                         sys_dept
                                         sys_notice
                                         sys_role_menu
                                         sys_dict_data
                                         sys_user
                                         sys_user_role
                                         sys_user_mfa_recovery_codes
                                         """.split("\n"))
                                     .map(StringUtils::trim)
                                     .filter(StringUtils::isNotBlank)
                                     .toList());
    }

    @Test
    public void genCode() {
        for (GenTable genTable : genTableService.list()) {
            genService.generateCode(genTable.getId());
        }

    }
}
