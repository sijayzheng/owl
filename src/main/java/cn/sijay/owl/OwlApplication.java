package cn.sijay.owl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy // 开启 AOP 支持
@SpringBootApplication
public class OwlApplication {

    public static void main(String[] args) {
        SpringApplication.run(OwlApplication.class, args);
    }

}
