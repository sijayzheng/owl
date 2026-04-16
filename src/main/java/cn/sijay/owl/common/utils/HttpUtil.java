package cn.sijay.owl.common.utils;

import cn.sijay.owl.common.service.Ip2RegionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import ua_parser.Client;
import ua_parser.Parser;

/**
 * HttpUtil
 *
 * @author sijay
 * @since 2026-04-14
 */
public class HttpUtil {
    private static final Parser UA_PARSER = new Parser();
    static Ip2RegionService service = SpringUtil.getBean(Ip2RegionService.class);

    /**
     * 获取浏览器信息
     */
    public static String getBrowser(HttpServletRequest request) {
        Client client = UA_PARSER.parse(request.getHeader(HttpHeaders.USER_AGENT));
        return client.userAgent.family + " " + client.userAgent.major;
    }

    /**
     * 获取操作系统信息
     */
    public static String getOs(HttpServletRequest request) {
        Client client = UA_PARSER.parse(request.getHeader(HttpHeaders.USER_AGENT));
        return client.os.family + " " + client.os.major;
    }

    /**
     * 获取客户端真实 IP 地址
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 处理多级代理后的逗号分隔 IP（取第一个）
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }

    public static String getRegion(String ip) {
        return service.getIpLocation(ip);
    }
}
