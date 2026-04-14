package cn.sijay.owl.common.wrapper;


import cn.sijay.owl.common.utils.XssUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.Arrays;

/**
 * XssHttpServletRequestWrapper
 * XSS 请求包装器，过滤参数和 Header 中的恶意脚本
 *
 * @author sijay
 * @since 2026-04-14
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        return XssUtil.clean(value);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values == null) {
            return null;
        }
        return Arrays.stream(values)
                     .map(XssUtil::clean)
                     .toArray(String[]::new);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return XssUtil.clean(value);
    }
}
