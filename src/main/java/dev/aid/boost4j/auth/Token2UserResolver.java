package dev.aid.boost4j.auth;

import org.apache.commons.lang3.ArrayUtils;
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
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        Auth auth = methodParameter.getParameterAnnotation(Auth.class);
        assert auth != null;
        String token = nativeWebRequest.getHeader("Authorization");
        User user = JwtUtil.parseToken(token);
        if (user == null) {
            throw new AuthExp("Resolver 未从 Header 中获取到 Authorization").setExpCode(Code.UNAUTHORIZED);
        } else {
            checkRoles(auth, user);
        }
        return user;
    }


    /**
     * TODO 在此处自定义你的校验方式
     *
     * @param auth 指定待校验信息
     * @param user token中的信息
     */
    private void checkRoles(Auth auth, User user) {
        // 校验多角色组
        if (auth.userRoles().length > 0) {
            for (UserRole authRole : auth.userRoles()) {
                if (ArrayUtils.contains(user.getUserRoles(), authRole)) {
                    throw new AuthExp("需要: " + Arrays.toString(auth.userRoles()) + ", " +
                            "现有权限为: " + Arrays.toString(user.getUserRoles()))
                            .setExpCode(Code.PERMISSION_DENIED);
                }
            }
        }
        // 校验单一角色
        if (auth.userRole() != UserRole.ANY) {
            if (!ArrayUtils.contains(user.getUserRoles(), auth.userRole())) {
                throw new AuthExp("需要: " + auth.userRole() + ", " +
                        "现有权限为: " + Arrays.toString(user.getUserRoles()))
                        .setExpCode(Code.PERMISSION_DENIED);
            }
        }
    }
}
