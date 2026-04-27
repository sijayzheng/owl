package cn.sijay.owl;


import java.io.IOException;
import java.time.LocalDate;

/**
 * MainTest
 *
 * @author sijay
 * @since 2026-04-13
 */
public class MainTest {

    public static void main(String[] args) throws IOException {

        String[] split = "2026-04-22,2026-06-06".split(",");
        LocalDate now = LocalDate.now();
        System.out.println(now.isAfter(LocalDate.parse(split[0])) && now.isBefore(LocalDate.parse(split[1])));
    }

     
}
