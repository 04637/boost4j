package dev.aid.boost4j.common;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 统一响应体
 *
 * @author 04637@163.com
 * @date 2020/11/6
 */
@Data
@Accessors(chain = true)
public class Resp {

    // 标志请求是否处理成功
    private boolean succeed;

    // 需要携带给前端的备注信息
    private String msg;

    // 成功时为响应数据
    private Object data;

    // 如发生异常, 填充异常堆栈信息, 方便排查问题
    private StackTraceElement[] expStack;

    // 响应代码
    private int code;

    /**
     * 处理完成, 正常响应结果
     *
     * @param data 填充响应数据
     * @return Resp msg=> "ok"; code=> "200"
     */
    public static Resp ok(Object data) {
        return new Resp().setData(data).setCode(RespCode.OK.getVal()).setMsg("ok");
    }

    /**
     * 处理失败, 返回指定错误提示, 指定异常代码
     *
     * @param errMsg  错误提示信息
     * @param expCode 错误代码  {@link RespCode}
     * @return Resp msg=> ${errMsg}; code=> ${expCode}
     */
    public static Resp fail(String errMsg, RespCode expCode) {
        return new Resp().setMsg(errMsg).setCode(expCode.getVal());
    }

    /**
     * 处理失败, 返回自定义错误消息
     *
     * @param errMsg 错误提示信息
     * @return Resp msg=> ${errMsg}; code=> "500"
     */
    public static Resp fail(String errMsg) {
        return new Resp().setCode(RespCode.SYSTEM_EXCEPTION.getVal()).setMsg(errMsg);
    }

    /**
     * 因发生异常导致的失败
     *
     * @param exception 异常
     * @return Resp msg=> ${Exception}.msg; code=> "500"
     */
    public static Resp fail(Exception exception) {
        return new Resp().setCode(RespCode.SYSTEM_EXCEPTION.getVal())
                .setMsg(exception.getMessage()).setExpStack(exception.getStackTrace());
    }

    /**
     * 因参数未经过校验导致处理失败时, 使用该方法
     *
     * @param errMsg 参数校验失败的提示
     * @return Resp msg=> ${errMsg}; code=> "400"
     */
    public static Resp unProcess(String errMsg) {
        return new Resp().setCode(RespCode.UNPROCESSABLE_ENTITY.getVal()).setMsg(errMsg);
    }

    /**
     * 因资源未找到导致的处理失败
     *
     * @param errMsg 提示信息
     * @return Resp msg=> ${errMsg}; code=> "404"
     */
    public static Resp notFound(String errMsg) {
        return new Resp().setCode(RespCode.NOT_FOUND.getVal()).setMsg(errMsg);
    }

    /**
     * 在需要鉴权而用户未携带权限校验信息时
     *
     * @return Resp code=> "401"; msg=> "需要认证信息"
     */
    public static Resp noAuth() {
        return new Resp().setCode(RespCode.UNAUTHORIZED.getVal()).setMsg("需要认证信息");
    }

    /**
     * 权限不足
     *
     * @return Resp code=> "403"; msg=> "权限不足"
     */
    public static Resp denied() {
        return new Resp().setCode(RespCode.PERMISSION_DENIED.getVal()).setMsg("权限不足");
    }
}
