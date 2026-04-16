package cn.sijay.owl.common.utils;

import cn.sijay.owl.common.enums.NamingCase;
import com.fasterxml.jackson.databind.util.NamingStrategyImpls;
import com.google.common.base.CaseFormat;

/**
 * NamingUtil
 *
 * @author sijay
 * @since 2026-04-15
 */
public class NamingUtil {
    public static String caseConvert(String string, NamingCase namingCase) {
        string = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, NamingStrategyImpls.SNAKE_CASE.translate(string.replace("-", "_")));
        return switch (namingCase) {
            case LOWER_CAMEL_CASE -> NamingStrategyImpls.LOWER_CAMEL_CASE.translate(string);
            case UPPER_CAMEL_CASE -> NamingStrategyImpls.UPPER_CAMEL_CASE.translate(string);
            case LOWER_SNAKE_CASE -> NamingStrategyImpls.SNAKE_CASE.translate(string);
            case UPPER_SNAKE_CASE -> NamingStrategyImpls.UPPER_SNAKE_CASE.translate(string);
            case KEBAB_CASE -> NamingStrategyImpls.KEBAB_CASE.translate(string);
            case LOWER_DOT_CASE -> NamingStrategyImpls.LOWER_DOT_CASE.translate(string);
            case LOWER_CASE -> NamingStrategyImpls.LOWER_CASE.translate(string);
        };
    }
}
