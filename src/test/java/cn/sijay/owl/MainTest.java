package cn.sijay.owl;


import cn.sijay.owl.common.utils.PasswordUtil;

import java.io.IOException;

/**
 * MainTest
 *
 * @author sijay
 * @since 2026-04-13
 */
public class MainTest {

    public static void main(String[] args) throws IOException {
        System.out.println(PasswordUtil.hash("123456"));
    }


}
