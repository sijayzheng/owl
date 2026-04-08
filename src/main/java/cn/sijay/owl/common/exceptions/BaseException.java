package cn.sijay.owl.common.exceptions;


import cn.sijay.owl.common.utils.StringUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * BaseException
 *
 * @author sijay
 * @since 2026/4/8
 */
@Getter
@Setter
public class BaseException extends RuntimeException {
    /**
     * 错误码
     */
    private Integer code = HttpStatus.INTERNAL_SERVER_ERROR.value();

    /**
     * 错误信息
     */
    private String message;

    /**
     * 根本原因异常
     */
    private Throwable cause;

    /**
     * 无参构造方法
     */
    public BaseException() {
        super();
    }

    /**
     * 带有错误信息的构造方法
     *
     * @param message 错误信息
     */
    public BaseException(String message) {
        super(message);
        this.message = message;
    }

    /**
     * 带格式化参数的构造方法
     *
     * @param message 错误信息模板
     * @param args    格式化参数
     */
    public BaseException(String message, Object... args) {
        super(formatMessage(message, args));
        this.message = formatMessage(message, args);
    }

    private static String formatMessage(String message, Object[] args) {
        return StringUtil.format(message, args);
    }

    /**
     * 带有错误信息和根本原因的构造方法
     *
     * @param message 错误信息
     * @param cause   根本原因异常
     */
    public BaseException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.cause = cause;
    }

    /**
     * 带有错误码、错误信息和根本原因的构造方法
     *
     * @param code    错误码
     * @param message 错误信息
     * @param cause   根本原因异常
     */
    public BaseException(Integer code, String message, Throwable cause) {
        super(code + ": " + message, cause);
        this.code = code;
        this.message = message;
        this.cause = cause;
    }

    /**
     * 带有错误信息和错误码的构造方法
     *
     * @param code    错误码
     * @param message 错误信息
     */
    public BaseException(Integer code, String message) {
        super(code + ": " + message);
        this.code = code;
        this.message = message;
    }

    /**
     * 重写toString方法，提供详细的异常信息输出
     *
     * @return 包含类名、错误码、错误信息和根本原因的字符串表示
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName());
        if (code != null) {
            sb.append(" [Code: ").append(code).append("]");
        }
        if (message != null) {
            sb.append(": ").append(message);
        }
        if (cause != null) {
            sb.append("; Root Cause: ").append(cause);
        }
        return sb.toString();
    }
}
