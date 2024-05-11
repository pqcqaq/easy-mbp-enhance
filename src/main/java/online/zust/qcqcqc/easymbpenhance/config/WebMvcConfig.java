package online.zust.qcqcqc.easymbpenhance.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.extern.slf4j.Slf4j;
import online.zust.qcqcqc.utils.config.ConventConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author pqcmm
 */
@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 注入框架已经配置好的转换器配置，用于添加自定义转换器
     */
    @Autowired
    private ConventConfig conventConfig;

    /**
     * 添加自定义转换器
     *
     * @param converters 转换器列表
     *                   * 序列换成json时,将所有的long变成string
     *                   * 因为js中得数字类型不能包含所有的java long值
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule conventionModule = conventConfig.getConventionModule();
        log.info("加载转换模块: {}", conventionModule);
        objectMapper.registerModule(conventionModule);
        // 允许null
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
        converters.add(0, jackson2HttpMessageConverter);
        // 时间格式化
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
        converters.add(0, jackson2HttpMessageConverter);
    }
}
