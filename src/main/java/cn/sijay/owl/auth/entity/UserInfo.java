package cn.sijay.owl.auth.entity;

import cn.sijay.owl.system.entity.SysUser;
import lombok.Data;

/**
 * UserInfo
 *
 * @author sijay
 * @since 2026-04-14
 */
@Data
public class UserInfo {
    private SysUser user;
    private String[] permissions;
    private String[] roles;
}

