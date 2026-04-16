package cn.sijay.owl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableScheduling
@EnableCaching
@EnableTransactionManagement(proxyTargetClass = true)
@EnableAspectJAutoProxy // 开启 AOP 支持
@SpringBootApplication
public class OwlApplication {

    public static void main(String[] args) {
        SpringApplication.run(OwlApplication.class, args);
    }

}
