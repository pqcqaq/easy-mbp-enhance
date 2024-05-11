package online.zust.qcqcqc.easymbpenhance.module.log.metadata;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import online.zust.qcqcqc.services.module.log.common.MetadataAppender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author qcqcqc
 * Date: 2024/4/12
 * Time: 0:05
 * 实现MetadataAppender接口，用于日志的元数据生成，会根据key和value生成元数据中的key-value对
 */
@Component
@RequiredArgsConstructor
public class RemoteAddrMetadata implements MetadataAppender {

    @Value("${limiter.remote-info.user-key:X-Forwarded-For}")
    private String headerKey;
    @Value("${limiter.remote-info.use-proxy:false}")
    private Boolean useProxy;

    @Override
    public String getKey() {
        return "remote-addr";
    }

    /**
     * 请求上下文
     */
    private final HttpServletRequest httpServletRequest;

    @Override
    public String appendMetadata() {
        try {
            if (useProxy) {
                return httpServletRequest.getHeader(headerKey);
            } else {
                return httpServletRequest.getRemoteAddr();
            }
        } catch (Exception e) {
            return "unknown";
        }
    }
}
