package cn.sijay.owl.common.utils;

import com.password4j.BcryptFunction;
import com.password4j.Hash;
import com.password4j.Password;

/**
 * BCrypt
 *
 * @author sijay
 * @since 2026-04-14
 */
public class PasswordUtil {
    static final BcryptFunction bcrypt = BcryptFunction.getInstance(12);

    public static String hash(String password) {
        Hash hash = Password.hash(password).with(bcrypt);
        return hash.getResult();
    }

    public static boolean check(String password, String hashedPassword) {
        return Password.check(password, hashedPassword).with(bcrypt);
    }

}
