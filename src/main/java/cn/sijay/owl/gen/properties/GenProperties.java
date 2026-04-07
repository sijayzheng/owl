package cn.sijay.owl.gen.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * GenProperties
 *
 * @author sijay
 * @since 2026/4/7
 */
@Data
@Component
@ConfigurationProperties(prefix = "gen")
public class GenProperties {
    private String author;
    private String packageName;
}
