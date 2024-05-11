package online.zust.qcqcqc.easymbpenhance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

/**
 * @author qcqcqc
 */
@SpringBootApplication
public class EasyMbpEnhanceApplication {
    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SpringApplication.run(EasyMbpEnhanceApplication.class, args);
    }
}
