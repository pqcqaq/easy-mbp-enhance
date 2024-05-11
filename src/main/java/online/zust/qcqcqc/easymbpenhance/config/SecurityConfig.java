package online.zust.qcqcqc.easymbpenhance.config;

import jakarta.annotation.Resource;
import online.zust.qcqcqc.services.module.user.filter.JwtAuthenticationFilter;
import online.zust.qcqcqc.services.module.user.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * SpringSecurity 5.4.x以上新用法配置
 * 为避免循环依赖，仅用于配置HttpSecurity
 *
 * @author qcqcqc
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Resource
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    /**
     * 角色继承
     *
     * @return RoleHierarchy
     */
    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_ADMIN > ROLE_SERVER";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }


    /**
     * 配置过滤
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 添加自定义的jwt过滤器，把jwt过滤器添加在用户名密码过滤器之前
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        //关闭csrf
        return http.csrf(AbstractHttpConfigurer::disable)
                //关闭session
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 配置RememberMe
                .rememberMe(rememberMe -> rememberMe.key("remember"))
                .exceptionHandling(
                        exceptionHandling -> exceptionHandling
                                .authenticationEntryPoint(authenticationEntryPoint)
                                .accessDeniedHandler(accessDeniedHandler)
                )
                // 配置路由授权，除了下面几个显式放行的，其他请求都需要认证
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/user/register").permitAll()
                                .requestMatchers("/user/open").permitAll()
                                .requestMatchers("/user/close").permitAll()
                                .requestMatchers("/user/login").permitAll()
                                .requestMatchers("/user/user").permitAll()
                                .requestMatchers("/user/logout").permitAll()
                                .requestMatchers("/druid/**").permitAll()
                                .requestMatchers("/demo/**").permitAll()
                                .requestMatchers("/user/resetPassword").permitAll()
                                .requestMatchers("/common/**").permitAll()
                                .anyRequest().authenticated()
                )
                .cors(
                        cors -> cors.configurationSource(corsConfigurationSource())
                )
                .userDetailsService(userDetailsService)
                .build();
    }

    /**
     * 配置跨源访问(CORS)
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // 允许跨域的域名，可以用*表示允许任何域名使用，当证书为true时，不能设置为*，否则会报错
        configuration.setAllowedOrigins(List.of("*"));
        // 允许的方法
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        // 允许任何头
        configuration.setAllowedHeaders(List.of("*"));
        // 是否支持安全证书
        configuration.setAllowCredentials(false);
        // 允许的header
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type", "Content-Disposition"));
        // 跨域允许时间
        configuration.setMaxAge(3600L);

        // 配置源对象
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 对所有URL应用CORS配置
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
