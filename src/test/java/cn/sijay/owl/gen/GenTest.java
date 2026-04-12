package cn.sijay.owl.gen;


import cn.sijay.owl.gen.service.GenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * GenTest
 *
 * @author sijay
 * @since 2026/4/9
 */
@SpringBootTest
public class GenTest {
    @Autowired
    private GenService genService;


    @Test
    public void importTable() {
        genService.importTable(List.of(
                "sys_user",
                "sys_dept"
        ));
    }

    @Test
    public void genCode() {
        for (long i = 1; i < 18; i++) {
            genService.generateCode(i);
        }
    }
}
