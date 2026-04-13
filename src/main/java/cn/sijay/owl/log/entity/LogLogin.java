package cn.sijay.owl.log.entity;


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
 * 登录日志实体类
 *
 * @author sijay
 * @since 2026-04-09
 */
@ExcelIgnoreUnannotated
@Data
@Table(value = "log_login", comment = "登录日志表")
public class LogLogin implements Serializable {

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
    @ExcelProperty(value = "用户id")
    private Long userId;

    /**
     * 用户账号
     */
    @Column(value = "username", comment = "用户账号")
    @ExcelProperty(value = "用户账号")
    private String username;

    /**
     * 登录ip地址
     */
    @Column(value = "login_ip", comment = "登录ip地址")
    @ExcelProperty(value = "登录ip地址")
    private String loginIp;

    /**
     * 浏览器类型
     */
    @Column(value = "browser", comment = "浏览器类型")
    @ExcelProperty(value = "浏览器类型")
    private String browser;

    /**
     * 操作系统
     */
    @Column(value = "os", comment = "操作系统")
    @ExcelProperty(value = "操作系统")
    private String os;

    /**
     * 登录状态
     */
    @Column(value = "succeeded", comment = "登录状态")
    @ExcelProperty(value = "登录状态")
    private Boolean succeeded;

    /**
     * 提示消息
     */
    @Column(value = "message", comment = "提示消息")
    @ExcelProperty(value = "提示消息")
    private String message;

    /**
     * 登录时间
     */
    @Column(value = "login_time", comment = "登录时间")
    @ExcelProperty(value = "登录时间")
    private LocalDateTime loginTime;

        private LocalDateTime[] loginTimeRange;

}
