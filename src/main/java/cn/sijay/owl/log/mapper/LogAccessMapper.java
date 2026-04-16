package cn.sijay.owl.log.mapper;

import cn.sijay.owl.log.entity.LogAccess;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 访问日志Mapper
 *
 * @author sijay
 * @since 2026-04-09
 */
@Mapper
public interface LogAccessMapper extends BaseMapper<LogAccess> {
}
