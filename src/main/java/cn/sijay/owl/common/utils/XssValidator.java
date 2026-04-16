package cn.sijay.owl.common.utils;

import cn.sijay.owl.common.annotations.Xss;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * XssValidator
 *
 * @author sijay
 * @since 2026-04-16
 */
public class XssValidator implements ConstraintValidator<Xss, String> {
    private static final Pattern PATTERN = Pattern.compile("(<[^<]*?>)|(<\\s*?/[^<]*?>)|(<[^<]*?/\\s*?>)");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return StringUtils.isNotBlank(value) && PATTERN.matcher(value).find();
    }

}
