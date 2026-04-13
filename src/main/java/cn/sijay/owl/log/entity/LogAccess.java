package cn.sijay.owl.log.entity;


import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import org.apache.fesod.sheet.annotation.ExcelIgnoreUnannotated;
import org.apache.fesod.sheet.annotation.ExcelProperty;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 访问日志实体类
 *
 * @author sijay
 * @since 2026-04-09
 */
@ExcelIgnoreUnannotated
@Data
@Table(value = "log_access", comment = "访问日志表")
public class LogAccess implements Serializable {

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
     * 模块标题
     */
    @Column(value = "title", comment = "模块标题")
    @ExcelProperty(value = "模块标题")
    private String title;

    /**
     * 业务类型
     */
    @Column(value = "operate_type", comment = "业务类型")
    @ExcelProperty(value = "业务类型")
    private String operateType;

    /**
     * 方法名称
     */
    @Column(value = "method", comment = "方法名称")
    @ExcelProperty(value = "方法名称")
    private String method;

    /**
     * 请求方式
     */
    @Column(value = "request_method", comment = "请求方式")
    @ExcelProperty(value = "请求方式")
    private String requestMethod;

    /**
     * 访问人员
     */
    @Column(value = "access_username", comment = "访问人员")
    @ExcelProperty(value = "访问人员")
    private String accessUsername;

    /**
     * 请求url
     */
    @Column(value = "access_url", comment = "请求url")
    @ExcelProperty(value = "请求url")
    private String accessUrl;

    /**
     * 主机地址
     */
    @Column(value = "access_ip", comment = "主机地址")
    @ExcelProperty(value = "主机地址")
    private String accessIp;

    /**
     * 访问地点
     */
    @Column(value = "access_location", comment = "访问地点")
    @ExcelProperty(value = "访问地点")
    private String accessLocation;

    /**
     * 请求参数
     */
    @Column(value = "access_param", comment = "请求参数")
    @ExcelProperty(value = "请求参数")
    private String accessParam;

    /**
     * 返回参数
     */
    @Column(value = "json_result", comment = "返回参数")
    @ExcelProperty(value = "返回参数")
    private String jsonResult;

    /**
     * 访问状态
     */
    @Column(value = "status", comment = "访问状态")
    @ExcelProperty(value = "访问状态")
    private Integer status;

    /**
     * 错误消息
     */
    @Column(value = "error_msg", comment = "错误消息")
    @ExcelProperty(value = "错误消息")
    private String errorMsg;

    /**
     * 访问时间
     */
    @Column(value = "access_time", comment = "访问时间")
    @ExcelProperty(value = "访问时间")
    private LocalDateTime accessTime;

    /**
     * 消耗时间
     */
    @Column(value = "cost_time", comment = "消耗时间")
    @ExcelProperty(value = "消耗时间")
    private Long costTime;

}
