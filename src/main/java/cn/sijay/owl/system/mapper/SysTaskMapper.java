package cn.sijay.owl.system.mapper;

import cn.sijay.owl.system.entity.SysTask;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务配置Mapper
 *
 * @author sijay
 * @since 2026-04-09
 */
@Mapper
public interface SysTaskMapper extends BaseMapper<SysTask> {
}