package online.zust.qcqcqc.easymbpenhance.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import online.zust.qcqcqc.utils.conventer.serializer.MyBigDecimalTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

/**
 * @author qcqcqc
 * @date 2024/03
 * @time 19-47-35
 */
@Configuration
public class MybatisConfig {
    /**
     * 配置分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //数据库类型是MySql，因此参数填写DbType.MYSQL
        PaginationInnerInterceptor innerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        interceptor.addInnerInterceptor(innerInterceptor);
        return interceptor;
    }

    /**
     * 代码增强，覆盖mybatis-plus原始BigDecimalTypeHandler改为自定义MyBigDecimalTypeHandler
     *
     * @return : com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer
     */
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            //代码增强，实现插入数据库和返回数据的时候bigDecimal末尾0去除
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            typeHandlerRegistry.register(BigDecimal.class, new MyBigDecimalTypeHandler());
            typeHandlerRegistry.register(JdbcType.NUMERIC, new MyBigDecimalTypeHandler());
            typeHandlerRegistry.register(JdbcType.DECIMAL, new MyBigDecimalTypeHandler());
        };
    }
}
