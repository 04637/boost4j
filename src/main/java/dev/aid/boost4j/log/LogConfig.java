package dev.aid.boost4j.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 取代自定义log, 使log可注入
 *
 * @author 04637@163.com
 * @date 2020/11/26
 */
@Configuration
public class LogConfig {

    @Bean
    public Logger classLogger(InjectionPoint injectionPoint) {
        Class<?> classOnWired = injectionPoint.getMember().getDeclaringClass();
        return LoggerFactory.getLogger(classOnWired);
    }
}
