package dev.aid.boost4j.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 鉴权注解
 *
 * @author 04637@163.com
 * @date 2020/11/23
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
    String value() default "";

    // 校验多角色时则指定此项
    UserRole[] userRoles() default {};

    // 校验单一角色, 当多角色组 userRoles 不为空时, 即使该项为 ANY, 也需要通过多角色组校验
    UserRole userRole() default UserRole.ANY;
}
