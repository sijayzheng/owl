package cn.sijay.owl.log.mapper;

import cn.sijay.owl.log.entity.LogTask;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 任务日志Mapper
 *
 * @author sijay
 * @since 2026-04-09
 */
@Mapper
public interface LogTaskMapper extends BaseMapper<LogTask> {
}