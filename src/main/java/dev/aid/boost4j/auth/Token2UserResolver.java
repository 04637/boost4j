package dev.aid.boost4j.auth;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Arrays;

import dev.aid.boost4j.common.Code;
import dev.aid.boost4j.exp.AuthExp;

/**
 * token解析器
 * todo 需要配置spring扫描包 dev.aid.boost4j,  例如springboot
 * 启动类添加注解 @ComponentScan(basePackages = {"dev.aid.boost4j", "com.fh.xx"})
 *
 * @author 04637@163.com
 * @date 2020/11/23
 */
public class Token2UserResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterAnnotation(Auth.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) {
        Auth auth = methodParameter.getParameterAnnotation(Auth.class);
        assert auth != null;
        String token = nativeWebRequest.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            throw new AuthExp("Resolver 未从 Header 中获取到 Authorization").setExpCode(Code.UNAUTHORIZED);
        }
        AuthUser authUser = JwtUtil.parseToken(token);
        if (authUser == null) {
            throw new AuthExp("token 已失效").setExpCode(Code.UNAUTHORIZED);
        } else {
            checkRoles(auth, authUser);
        }
        return authUser;
    }


    /**
     * TODO 在此处自定义你的校验方式
     *
     * @param auth     指定待校验信息
     * @param authUser token中的信息
     */
    private void checkRoles(Auth auth, AuthUser authUser) {
        // 校验多角色组
        if (auth.userRoles().length > 0) {
            for (UserRole authRole : auth.userRoles()) {
                if (ArrayUtils.contains(authUser.getUserRoles(), authRole)) {
                    throw new AuthExp("需要: " + Arrays.toString(auth.userRoles()) + ", " +
                            "现有权限为: " + Arrays.toString(authUser.getUserRoles()))
                            .setExpCode(Code.PERMISSION_DENIED);
                }
            }
        }
        // 校验单一角色
        if (auth.userRole() != UserRole.ANY) {
            if (!ArrayUtils.contains(authUser.getUserRoles(), auth.userRole())) {
                throw new AuthExp("需要: " + auth.userRole() + ", " +
                        "现有权限为: " + Arrays.toString(authUser.getUserRoles()))
                        .setExpCode(Code.PERMISSION_DENIED);
            }
        }
    }
}
