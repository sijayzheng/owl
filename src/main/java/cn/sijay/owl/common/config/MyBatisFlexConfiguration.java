package cn.sijay.owl.common.config;

import cn.sijay.owl.common.base.BaseEntity;
import cn.sijay.owl.common.utils.LoginHelper;
import com.mybatisflex.core.FlexGlobalConfig;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * MyBatisFlexConfiguration
 *
 * @author sijay
 * @since 2026-04-14
 */
@Configuration
public class MyBatisFlexConfiguration {
    public MyBatisFlexConfiguration() {
        FlexGlobalConfig config = FlexGlobalConfig.getDefaultConfig();
        //设置BaseEntity类启用
        config.registerInsertListener(object -> {
            Long userId = LoginHelper.getUserId();
            if (object instanceof BaseEntity entity) {
                entity.setCreateBy(userId);
                entity.setCreateDept(userId);
                entity.setCreateTime(LocalDateTime.now());
            }
        }, BaseEntity.class);
        config.registerUpdateListener(object -> {
            Long userId = LoginHelper.getUserId();
            if (object instanceof BaseEntity entity) {
                entity.setUpdateBy(userId);
                entity.setUpdateTime(LocalDateTime.now());
            }
        }, BaseEntity.class);
    }
}
