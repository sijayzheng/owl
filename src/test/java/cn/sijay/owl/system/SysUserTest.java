package cn.sijay.owl.system;

import cn.sijay.owl.common.utils.JsonUtil;
import cn.sijay.owl.system.entity.SysUser;
import cn.sijay.owl.system.service.SysUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * SysUserTest
 *
 * @author sijay
 * @since 2026-04-16
 */
@SpringBootTest
public class SysUserTest {
    @Autowired
    private SysUserService sysUserService;

    @Test
    public void test() {
        SysUser user = sysUserService.getWithRelations(2L);
        System.out.println(JsonUtil.toJson(user));
    }
}
