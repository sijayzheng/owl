package cn.sijay.owl.system;


import cn.sijay.owl.system.service.SysMenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * SysMenuTest
 *
 * @author sijay
 * @since 2026-05-09
 */
@SpringBootTest
public class SysMenuTest {
    @Autowired
    private SysMenuService sysMenuService;

    @Test
    public void test() {
        sysMenuService.list()
                      .stream()
                      .forEach(System.out::println);

    }


}
