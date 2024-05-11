package online.zust.qcqcqc.easymbpenhance.config;

import jakarta.servlet.http.HttpServletRequest;
import online.zust.qcqcqc.services.module.user.entity.User;
import online.zust.qcqcqc.utils.config.LimiterConfig;
import online.zust.qcqcqc.easymbpenhance.utils.ContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author qcqcqc
 * Date: 2024/4/11
 * Time: 19:37
 */
@Component
public class MyLimiterConfig implements LimiterConfig {

    @Value("${limiter.remote-info.user-key:X-Forwarded-For}")
    private String headerKey;
    @Value("${limiter.remote-info.use-proxy:false}")
    private Boolean useProxy;

    /**
     * 请求上下文
     */
    private HttpServletRequest httpServletRequest;

    @Autowired
    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public String getUserKey() {
        /*
         * 从请求上下文中获取用户的唯一标识
         */
        User currentUser = ContextUtil.getUserInfo();
        if (currentUser != null) {
            return currentUser.getUsername();
        }
        if (useProxy) {
            return httpServletRequest.getHeader(headerKey);
        } else {
            return httpServletRequest.getRemoteAddr();
        }
    }
}
