package cn.sijay.owl.common.exceptions;

import lombok.Getter;
import lombok.Setter;

/**
 * ServiceException
 *
 * @author sijay
 * @since 2026-04-08
 */
@Getter
@Setter
public class ServiceException extends BaseException {
    private Class<?> clazz;

    public ServiceException(Class<?> clazz, String message) {
        super(message);
        this.clazz = clazz;
    }

    public ServiceException(Class<?> clazz, String message, Object... args) {
        super(message, args);
        this.clazz = clazz;
    }

    public ServiceException(Class<?> clazz, String message, Throwable cause) {
        super(message, cause);
        this.clazz = clazz;
    }

    public ServiceException(Class<?> clazz, Integer errorCode, String errorMessage) {
        super(errorCode, errorMessage);
        this.clazz = clazz;
    }

    public ServiceException(Class<?> clazz, Integer errorCode, String errorMessage, Throwable cause) {
        super(errorCode, errorMessage, cause);
        this.clazz = clazz;
    }

    @Override
    public String toString() {
        String baseString = super.toString();
        if (clazz != null) {
            return " [类: " + clazz.getName() + "]" + baseString;
        }
        return baseString;
    }
}
