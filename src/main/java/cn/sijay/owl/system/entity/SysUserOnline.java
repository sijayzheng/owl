package cn.sijay.owl.system.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.apache.fesod.sheet.annotation.ExcelIgnoreUnannotated;
import org.apache.fesod.sheet.annotation.ExcelProperty;

import java.time.LocalDateTime;
import java.io.Serial;
import java.io.Serializable;
/**
 * 在线用户实体类
 *
 * @author sijay
 * @since 2026-04-09
 */
@ExcelIgnoreUnannotated
@Data
@Table(value = "sys_user_online", comment = "在线用户表")
public class SysUserOnline implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id(keyType = KeyType.Auto, comment = "主键")
    @Column(value = "id", comment = "主键")
    private Long id;

    /**
     * 用户id
     */
    @Column(value = "user_id", comment = "用户id")
    @NotNull(message = "用户id不能为空")
    private Long userId;

    /**
     * 用户账号
     */
    @Column(value = "username", comment = "用户账号")
    private String username;

    /**
     * 部门名称
     */
    @Column(value = "dept_name", comment = "部门名称")
    private String deptName;

    /**
     * 登录ip
     */
    @Column(value = "login_ip", comment = "登录ip")
    private String loginIp;

    /**
     * 登录地点
     */
    @Column(value = "login_location", comment = "登录地点")
    private String loginLocation;

    /**
     * 浏览器
     */
    @Column(value = "browser", comment = "浏览器")
    private String browser;

    /**
     * 操作系统
     */
    @Column(value = "os", comment = "操作系统")
    private String os;

    /**
     * 登录时间
     */
    @Column(value = "login_time", comment = "登录时间")
    @NotNull(message = "登录时间不能为空")
    private LocalDateTime loginTime;

    /**
     * 最后访问时间
     */
    @Column(value = "last_access_time", comment = "最后访问时间")
    @NotNull(message = "最后访问时间不能为空")
    private LocalDateTime lastAccessTime;

    /**
     * 过期时间
     */
    @Column(value = "expire_time", comment = "过期时间")
    @NotNull(message = "过期时间不能为空")
    private LocalDateTime expireTime;

}
