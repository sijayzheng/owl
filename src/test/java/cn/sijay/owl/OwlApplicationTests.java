package cn.sijay.owl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

@Slf4j
@SpringBootTest
class OwlApplicationTests {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void test() throws SQLException {
    }

}
