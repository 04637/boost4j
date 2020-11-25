package dev.aid.boost4j.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * 解析器及跨域配置
 *
 * @author 04637@163.com
 * @date 2020/11/25
 */
@Configuration
// @PropertySource("classpath:system.properties")
public class WebMvcConfig extends WebMvcConfigurationSupport {

    // todo 可通过配置文件注入跨域配置
    // @Value("${cross.origins}")
    private String origins = "localhost:8080, localhost:8088";

    /**
     * 跨域配置
     */
    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins(origins.split(","))
                .allowedHeaders("*");
        super.addCorsMappings(registry);
    }

    /**
     * 参数解析器配置
     */
    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new Token2UserResolver());
        argumentResolvers.add(new CurrentIPResolver());
    }
}
