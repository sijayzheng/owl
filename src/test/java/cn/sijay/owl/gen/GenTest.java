package cn.sijay.owl.gen;

import cn.sijay.owl.gen.service.GenService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

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
        List<String> list = Arrays.stream("""
                                      """.split("\n"))
                                  .map(StringUtils::trim)
                                  .filter(StringUtils::isNotBlank)
                                  .toList();

        genService.importTable(list);
    }

    @Test
    public void genCode() {
        for (long i = 1; i < 18; i++) {
            genService.generateCode(i);
        }
    }
}
