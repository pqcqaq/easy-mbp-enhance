package online.zust.qcqcqc.easymbpenhance.module.demo.listener;

import lombok.extern.slf4j.Slf4j;
import online.zust.qcqcqc.services.module.redis.listener.interfaces.KeyUpdateObserver;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

/**
 * @author qcqcqc
 * Date: 2024/4/13
 * Time: 20:48
 * Redis中某一个Key被更新的监听器
 */
@Component
@Slf4j
public class Update implements KeyUpdateObserver {
    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("Redis中的Key：{}被更新了，事件： {}", message.toString(), new String(pattern));
    }
}
