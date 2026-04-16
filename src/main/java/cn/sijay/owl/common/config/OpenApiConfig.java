package cn.sijay.owl.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenApiConfig
 *
 * @author sijay
 * @since 2026-04-08
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("你的API接口文档标题")
                .description("这里是详细的接口文档描述信息")
                .version("v1.0.0")
                .contact(new Contact()
                    .name("你的名字或团队")
                    .email("your-email@example.com")
                    .url("https://your-website.com")
                )
            );
    }
}
