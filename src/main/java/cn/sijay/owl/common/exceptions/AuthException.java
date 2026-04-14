package cn.sijay.owl.common.exceptions;


/**
 * AuthException
 *
 * @author sijay
 * @since 2026-04-14
 */
public class AuthException extends BaseException {
    public AuthException(String message) {
        super(403, message);
    }
}
