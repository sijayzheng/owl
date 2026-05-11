package cn.sijay.owl.demo.mapper;

import cn.sijay.owl.demo.entity.User;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 人员demoMapper
 *
 * @author sijay
 * @since 2026-04-09
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
