package online.zust.qcqcqc.easymbpenhance.module.demo.listener;

import lombok.extern.slf4j.Slf4j;
import online.zust.qcqcqc.services.module.redis.listener.interfaces.KeyDeleteObserver;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

/**
 * @author qcqcqc
 * Date: 2024/4/13
 * Time: 20:47
 * Redis中某一个Key被删除的监听器
 */
@Component
@Slf4j
public class Delete implements KeyDeleteObserver {
    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("Redis中Key {}被删除了，事件： {}", message.toString(), new String(pattern));
    }
}
