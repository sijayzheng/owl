package cn.sijay.owl.common.config;

import cn.sijay.owl.common.utils.XssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.ext.javatime.deser.LocalDateTimeDeserializer;
import tools.jackson.databind.ext.javatime.deser.LocalTimeDeserializer;
import tools.jackson.databind.ext.javatime.ser.LocalDateTimeSerializer;
import tools.jackson.databind.ext.javatime.ser.LocalTimeSerializer;
import tools.jackson.databind.module.SimpleModule;
import tools.jackson.databind.ser.jdk.NumberSerializer;
import tools.jackson.databind.ser.std.ToStringSerializer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * JacksonConfig
 *
 * @author sijay
 * @since 2026-04-08
 */
@Slf4j
@Configuration
public class JacksonConfig {

    @Bean
    JsonMapperBuilderCustomizer jacksonCustomizer() {
        return builder -> {
            builder.enable(SerializationFeature.INDENT_OUTPUT);
            // 全局配置序列化返回 JSON 处理
            // 1. 创建自定义模块
            SimpleModule customModule = new SimpleModule();
            // 2. 将之前的 serializerByType 配置，改为向模块添加序列化器
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // 为特定类型注册序列化器
            customModule.addSerializer(Long.class, NumberSerializer.instance);
            customModule.addSerializer(Long.TYPE, NumberSerializer.instance);
            customModule.addSerializer(BigInteger.class, NumberSerializer.instance);
            customModule.addSerializer(BigDecimal.class, ToStringSerializer.instance);
            customModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
            customModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ISO_TIME));

            // 为特定类型注册反序列化器
            customModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
            customModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ISO_TIME));

            // 3. 注册您之前自定义的 XssStringDeserializer
            customModule.addDeserializer(String.class, new ValueDeserializer<>() {
                @Override
                public String deserialize(JsonParser p, DeserializationContext ctxt) {
                    String value = p.getValueAsString();
                    return XssUtil.clean(value);
                }
            });

            // 4. 将整个模块添加到 builder 中
            builder.addModule(customModule);

            log.info("初始化Jackson配置完成");
        };
    }

}

