package dev.aid.boost4j.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 添加该注解后, 将解析当前请求IP至参数
 *
 * @author 04637@163.com
 * @date 2020/11/25
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentIP {
    String value() default "";
}