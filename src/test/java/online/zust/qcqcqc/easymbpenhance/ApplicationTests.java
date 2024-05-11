package online.zust.qcqcqc.easymbpenhance;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@SpringBootTest
class ApplicationTests {

    @Test
    void contextLoads() {
        //生成 密码测试
         System.out.println(new BCryptPasswordEncoder().encode("987654321"));
    }
}
