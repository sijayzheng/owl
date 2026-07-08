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
//                                         FILE_OSS_STORAGE
//                                         SYS_TASK
//                                         LOG_TASK
        Arrays.stream("""
                  FILE_STORAGE
                  LOG_ACCESS
                  LOG_LOGIN
                  SYS_DICT_DATA
                  SYS_DICT_TYPE
                  SYS_MESSAGE
                  SYS_NOTICE
                  SYS_POST
                  SYS_USER_MFA_RECOVERY_CODES
                  SYS_USER_ONLINE
                  SYS_USER_POST
                  """.split("\n"))
              .map(StringUtils::trim)
              .filter(StringUtils::isNotBlank)
              .forEach(genService::importTable);
    }

    @Test
    public void genCode() {
        for (GenTable genTable : genTableService.list()) {
            genService.generateCode(genTable.getId());
        }

    }
}
