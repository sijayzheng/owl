package cn.sijay.owl.system.mapper;

import cn.sijay.owl.system.entity.SysAccessLog;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 访问日志Mapper
 *
 * @author sijay
 * @since 2026-04-09
 */
@Mapper
public interface SysAccessLogMapper extends BaseMapper<SysAccessLog> {
}
