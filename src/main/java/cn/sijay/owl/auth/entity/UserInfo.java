package cn.sijay.owl.auth.entity;

import cn.sijay.owl.system.entity.SysUser;

import java.util.Set;

/**
 * UserInfo
 *
 * @author sijay
 * @since 2026-04-14
 */
public record UserInfo(
    Long userId,
    SysUser user,
    Set<String> permissions,
    Set<String> roles
) {

}
