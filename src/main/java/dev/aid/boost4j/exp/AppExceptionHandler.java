package dev.aid.boost4j.exp;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import dev.aid.boost4j.common.Code;
import dev.aid.boost4j.common.Resp;

/**
 * 统一异常处理
 *
 * @author 04637@163.com
 * @date 2020/11/9
 */
@RestControllerAdvice
public class AppExceptionHandler {

    //############ 参数相关异常 => start ###########

    /**
     * 统一处理参数校验异常
     *
     * @return BindingResult中的信息, code: 400, msg: 参数校验失败
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Resp handleArgumentValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> map = new HashMap<>();
        List<FieldError> list = bindingResult.getFieldErrors();
        for (FieldError err : list) {
            map.put(err.getField(), err.getDefaultMessage());
        }
        return Resp.unProcess(map);
    }

    /**
     * 当使用@RequestBody类型转换失败时会报该异常
     *
     * @param e json转换失败时异常
     * @return msg: HttpMessageNotReadableException的信息, code: 400
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Resp handleHttpNotReadable(HttpMessageNotReadableException e) {
        return Resp.unProcess(e.getMessage());
    }

    /**
     * 统一处理参数绑定异常
     *
     * @return BindingResult中的信息, code: 400, msg: 参数校验失败
     */
    @ExceptionHandler(BindException.class)
    public Resp handleBindException(BindException e) {
        String data = e.getBindingResult().getAllErrors()
                .stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        return Resp.unProcess(data);
    }

    /**
     * 统一处理约束违反异常
     *
     * @return 违反约束信息, code: 400, msg: 参数校验异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Resp handleConstraintViolationException(ConstraintViolationException e) {
        String data =
                e.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining());
        return Resp.unProcess(data);
    }

    //############ 参数相关异常 => end ###########

    /**
     * 业务层异常统一处理, 根据 expCode 细分   {@link dev.aid.boost4j.common.Code}
     *
     * @param e 业务层异常
     */
    @ExceptionHandler(ServiceExp.class)
    public Resp handleServiceException(ServiceExp e) {
        switch (e.getExpCode()) {
            case PERMISSION_DENIED:
                return Resp.denied();
            case UNAUTHORIZED:
                return Resp.noAuth();
            case NOT_FOUND:
                return Resp.notFound(e.getMessage());
            case UNPROCESSABLE_ENTITY:
                return Resp.unProcess(e.getMessage());
            case OK:
                return Resp.ok(e.getMessage());
            default:
                return Resp.fail(e);
        }
    }

    /**
     * 数据层异常处理
     *
     * @param e sql处理异常
     */
    @ExceptionHandler(DaoExp.class)
    public Resp handleDaoException(DaoExp e) {
        return new Resp().setCode(Code.SYSTEM_EXCEPTION.getVal())
                .setMsg(e.getMessage())
                .setExpStack(e.getStackTrace());
    }

    /**
     * 其他异常处理
     *
     * @param e 其他异常
     */
    @ExceptionHandler(Exception.class)
    public Resp handleException(Exception e) {
        String msg = "未知异常: " + e.getClass().getName() + ", 请向开发人员反馈 expStack";
        try {
            if (!e.getMessage().isEmpty()) {
                msg = e.getMessage();
            }
        } catch (Exception ignored) {
        }
        return new Resp().setCode(Code.SYSTEM_EXCEPTION.getVal())
                .setMsg(msg)
                .setExpStack(e.getStackTrace());
    }

}
