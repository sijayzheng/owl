package cn.sijay.owl.common.base;


import cn.sijay.owl.common.entity.Result;
import cn.sijay.owl.common.enums.OperateType;
import com.mybatisflex.core.paginate.Page;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * BaseController
 *
 * @author sijay
 * @since 2026/4/8
 */
public class BaseController {
    /**
     * 创建成功响应
     * <p>
     * 无数据，无消息
     *
     * @param <T> 响应数据类型
     * @return 成功响应结果
     */
    public static <T> Result<T> success() {
        return Result.success();
    }

    /**
     * 创建成功响应
     * <p>
     * 带数据，无消息
     *
     * @param data 响应数据
     * @return 成功响应结果
     */
    public static <T> Result<T> success(T data) {
        return Result.success(data);
    }

    /**
     * 创建成功响应
     * <p>
     * 带列表数据
     *
     * @param rows 数据列表
     * @param <T>  数据类型
     * @return 成功响应结果
     */
    public static <T> Result<List<T>> success(List<T> rows) {
        return Result.success(rows);
    }

    /**
     * 创建成功响应
     * <p>
     * 带分页数据
     *
     * @param page 分页对象
     * @param <T>  数据类型
     * @return 成功响应结果
     */
    public static <T> Result<List<T>> success(Page<T> page) {
        return Result.success(page);
    }

    /**
     * 创建成功响应
     * <p>
     * 带消息和数据
     *
     * @param message 响应消息
     * @param data    响应数据
     * @param <T>     数据类型
     * @return 成功响应结果
     */
    public static <T> Result<T> success(String message, T data) {
        return Result.success(message, data);
    }

    /**
     * 创建失败响应
     * <p>
     * 带状态码和消息
     *
     * @param code    状态码
     * @param message 错误消息
     * @param <T>     数据类型
     * @return 失败响应结果
     */
    public static <T> Result<T> fail(int code, String message) {
        return Result.fail(code, message);
    }

    /**
     * 创建失败响应
     * <p>
     * 带消息，使用默认错误状态码
     *
     * @param message 错误消息
     * @param <T>     数据类型
     * @return 失败响应结果
     */
    public static <T> Result<T> fail(String message) {
        return Result.fail(message);
    }

    /**
     * 创建响应
     * <p>
     * 自定义状态码、消息和数据
     *
     * @param status  状态码
     * @param message 响应消息
     * @param body    响应数据
     * @param <T>     数据类型
     * @return 响应结果
     */
    public static <T> Result<T> of(int status, String message, T body) {
        return Result.of(status, message, body);
    }

    /**
     * 创建响应
     * <p>
     * 自定义状态码、消息和数据
     *
     * @param flag    状态码
     * @param operate 响应消息
     * @return 响应结果
     */
    public static Result<Boolean> result(boolean flag, OperateType operate) {
        int status = flag ? HttpStatus.OK.value() : HttpStatus.INTERNAL_SERVER_ERROR.value();
        return Result.of(status, operate.getDescription() + (flag ? "成功" : "失败"), flag);
    }

    /**
     * 创建响应
     * <p>
     * 自定义状态码、消息和数据
     *
     * @param operate 响应消息
     * @return 响应结果
     */
    public static Result<Boolean> success(OperateType operate) {
        return Result.of(HttpStatus.OK.value(), operate.getDescription() + "成功", true);
    }
}
