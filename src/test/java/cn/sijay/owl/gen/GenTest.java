package cn.sijay.owl.gen;

import cn.sijay.owl.gen.service.GenService;
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


    @Test
    public void importTable() {
        genService.importTable(Arrays.stream("""
                                         user
                                         """.split("\n"))
                                     .map(StringUtils::trim)
                                     .filter(StringUtils::isNotBlank)
                                     .toList());
    }

    @Test
    public void genCode() {
        genService.generateCode(29L);
    }
}
