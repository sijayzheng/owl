package cn.sijay.owl.common.filter;

import cn.sijay.owl.common.wrapper.XssHttpServletRequestWrapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * XssFilter
 * XSS 过滤器，对请求参数和 Header 进行清洗
 *
 * @author sijay
 * @since 2026-04-14
 */
public class XssFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    FilterChain filterChain)
        throws ServletException, IOException {
        // 使用包装后的请求替换原始请求
        XssHttpServletRequestWrapper wrappedRequest = new XssHttpServletRequestWrapper(request);
        filterChain.doFilter(wrappedRequest, response);
    }
}
