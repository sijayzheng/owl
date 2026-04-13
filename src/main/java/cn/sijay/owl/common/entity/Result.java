package cn.sijay.owl.common.entity;


import com.mybatisflex.core.paginate.Page;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * 通用响应结果类
 * <p>
 * 用于封装 API 响应数据，包含状态码、消息、数据和总数
 *
 * @param <T> 响应数据类型
 * @author sijay
 * @since 2026-04-08
 */
public record Result<T>(
        /**
         * 状态码
         * <p>
         * 200 表示成功，非 200 表示失败
         */
        int code,
        /**
         * 响应消息
         * <p>
         * 成功或失败的提示信息
         */
        String message,
        /**
         * 响应数据
         * <p>
         * 具体的业务数据
         */
        T data,
        /**
         * 总数
         * <p>
         * 用于分页场景，表示总记录数
         */
        long total
) {
    /**
     * 创建成功响应
     * <p>
     * 无数据，无消息
     *
     * @param <T> 响应数据类型
     * @return 成功响应结果
     */
    public static <T> Result<T> success() {
        return new Result<>(HttpStatus.OK.value(), null, null, 0);
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
        return new Result<>(HttpStatus.OK.value(), "", data, 0);
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
        return new Result<>(HttpStatus.OK.value(), null, rows, CollectionUtils.isEmpty(rows) ? rows.size() : 0);
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
        return new Result<>(HttpStatus.OK.value(), null, page.getRecords(), page.getTotalRow());
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
        return new Result<>(HttpStatus.OK.value(), message, data, 0);
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
        return new Result<>(code, message, null, 0);
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
        return new Result<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null, 0);
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
        return new Result<>(status, message, body, 0);
    }
}
