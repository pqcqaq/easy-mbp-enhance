package online.zust.qcqcqc.easymbpenhance.module.demo.listener;

import lombok.extern.slf4j.Slf4j;
import online.zust.qcqcqc.services.module.redis.listener.interfaces.KeyDeleteObserver;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

/**
 * @author qcqcqc
 * Date: 2024/4/13
 * Time: 21:03
 * Redis中某一个Key被删除的监听器
 */
@Component
@Slf4j
public class Delete2 implements KeyDeleteObserver {
    @Override
    public String listenerKey() {
        // 匹配以ahh开头的key
        return "ahh.*";
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("监听到开头为ahh的key被删除了： {}，事件： {}", message.toString(), new String(pattern));
    }
}
