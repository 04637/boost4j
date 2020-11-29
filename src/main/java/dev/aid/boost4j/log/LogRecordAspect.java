package dev.aid.boost4j.log;

import com.alibaba.fastjson.JSON;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import dev.aid.boost4j.common.Resp;
import dev.aid.boost4j.util.UID;

/**
 * 接口日志打印切面
 * 默认配置打印 {@link Resp} 作为返回值的接口
 * todo 需要配置spring扫描包 dev.aid.boost4j,  例如springboot
 * 启动类添加注解 @ComponentScan(basePackages = {"dev.aid.boost4j", "com.fh.xx"})
 *
 * @author 04637@163.com
 * @date 2020/11/25
 */
@Aspect
@Configuration
public class LogRecordAspect {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LogRecordAspect.class);

    // 定义切点Pointcut
    @Pointcut("execution(dev.aid.boost4j.common.Resp *(..)) && " +
            "(@annotation(org.springframework.web.bind.annotation.RequestMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.GetMapping) ||" +
            "@annotation(org.springframework.web.bind.annotation.PostMapping))")
    public void excludeService() {
    }

    @Around("excludeService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String queryString = request.getQueryString();
        Object[] args = pjp.getArgs();
        String params = "";
        //获取请求参数集合并进行遍历拼接
        if (args.length > 0) {
            if ("POST".equals(method)) {
                Object object = args[0];
                Map map = getKeyAndValue(object);
                params = JSON.toJSONString(map);
            } else if ("GET".equals(method)) {
                params = queryString;
            }
        }
        String requestId = UID.next();
        long start = System.currentTimeMillis();
        log.info("{} => {}: {}, params: {}", requestId, method, url, params);

        // result的值就是被拦截方法的返回值
        Resp result = (Resp) pjp.proceed();
        long time = System.currentTimeMillis() - start;
        if (time > 1000) {
            log.info("{} <= {}s: {}", requestId, ((time / 100 * 100) / 1000.0), JSON.toJSONString(result));
        } else {
            log.info("{} <= {}ms: {}", requestId, time, JSON.toJSONString(result));
        }
        return result;
    }

    public static Map<String, Object> getKeyAndValue(Object obj) {
        Map<String, Object> map = new HashMap<>();
        if (obj == null) {
            return map;
        }
        // 得到类对象
        Class userCla = obj.getClass();
        /* 得到类中的所有属性集合 */
        Field[] fs = userCla.getDeclaredFields();
        for (Field f : fs) {
            f.setAccessible(true); // 设置些属性是可以访问的
            Object val;
            try {
                val = f.get(obj);
                // 得到此属性的值
                map.put(f.getName(), val);// 设置键值
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        return map;
    }
}
