package online.zust.qcqcqc.easymbpenhance.module.demo.runner;

import lombok.extern.slf4j.Slf4j;
import online.zust.qcqcqc.services.module.redis.service.RedisServiceImpl;
import online.zust.qcqcqc.services.module.redis.utils.RedisHashMap;
import online.zust.qcqcqc.services.module.redis.utils.RedisString;
import online.zust.qcqcqc.utils.utils.ProxyUtil;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author qcqcqc
 * Date: 2024/5/12
 * Time: 下午11:04
 */
@Component
@Slf4j
public class TestRedisOnBoot implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.warn("这是Redis服务的使用Demo...............");
        testRedisMap();
        testRedisString();
    }

    private static void testRedisMap() throws InterruptedException {
        RedisHashMap<String> stringRedisHashMap = new RedisHashMap<>("testMap", String.class);
        stringRedisHashMap.put("test", "test");
        stringRedisHashMap.put("test1", "test1");
        stringRedisHashMap.put("test2", "test2");
        stringRedisHashMap.put("test3", "test3");
        // remove
        stringRedisHashMap.remove("test");
        // get
        stringRedisHashMap.setTimeout(10000);
        Thread.sleep(1000);
        RedisHashMap<String> stringRedisHashMap1 = new RedisHashMap<>("test2", String.class);
        stringRedisHashMap1.put("test", "test");
        ProxyUtil.getBean(RedisServiceImpl.class).delete("test2");
        ProxyUtil.getBean(RedisServiceImpl.class).set("test3", "test3");
        ProxyUtil.getBean(RedisServiceImpl.class).set("test3", "test4");
        ProxyUtil.getBean(RedisServiceImpl.class).delete("test2");
        ProxyUtil.getBean(RedisServiceImpl.class).set("ahhahaha", "test4");
        ProxyUtil.getBean(RedisServiceImpl.class).delete("ahhahaha");
    }

    private static void testRedisString() throws Exception {
        RedisString<String> stringRedisString = new RedisString<>("testString", String.class);
        stringRedisString.set("test");
        stringRedisString.set("test1");
        stringRedisString.set("test2");
        stringRedisString.set("test3");
        stringRedisString.setTimeout(1500L);
        Thread.sleep(1000);
        log.info(stringRedisString.get());
        RedisString<String> stringRedisString1 = new RedisString<>("testString", String.class);
        log.info(stringRedisString1.get());
    }
}
