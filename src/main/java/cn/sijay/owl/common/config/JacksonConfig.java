package cn.sijay.owl.common.config;


import cn.sijay.owl.common.utils.XssUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.NumberSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
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
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        // 全局配置序列化返回 JSON 处理
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        builder.serializerByType(Long.class, BigNumberSerializer.instance);
        builder.serializerByType(Long.TYPE, BigNumberSerializer.instance);
        builder.serializerByType(BigInteger.class, BigNumberSerializer.instance);
        builder.serializerByType(BigDecimal.class, ToStringSerializer.instance);
        builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
        builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
        SimpleModule module = new SimpleModule();
        module.addDeserializer(String.class, new XssStringDeserializer());
        builder.modules(module);
        log.info("初始化 jackson 配置");
        return builder;
    }

    /**
     * 自定义字符串反序列化器，对 JSON 字符串值进行 XSS 过滤
     */
    static class XssStringDeserializer extends JsonDeserializer<String> {
        @Override
        public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            String value = p.getValueAsString();
            return XssUtil.clean(value);
        }
    }

    @JacksonStdImpl
    static
    class BigNumberSerializer extends NumberSerializer {
        /**
         * 提供实例
         */
        public static final BigNumberSerializer instance = new BigNumberSerializer(Number.class);
        /**
         * 根据 JS Number.MAX_SAFE_INTEGER 与 Number.MIN_SAFE_INTEGER 得来
         */
        private static final long MAX_SAFE_INTEGER = 9007199254740991L;
        private static final long MIN_SAFE_INTEGER = -9007199254740991L;

        public BigNumberSerializer(Class<? extends Number> rawType) {
            super(rawType);
        }

        @Override
        public void serialize(Number value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            // 超出范围 序列化为字符串
            if (value.longValue() > MIN_SAFE_INTEGER && value.longValue() < MAX_SAFE_INTEGER) {
                super.serialize(value, gen, provider);
            } else {
                gen.writeString(value.toString());
            }
        }
    }
}

