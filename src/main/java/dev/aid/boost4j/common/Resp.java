package dev.aid.boost4j.common;

/**
 * 统一响应体
 *
 * @author 04637@163.com
 * @date 2020/11/6
 */
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
     * 处理完成, 响应处理完成消息
     *
     * @param msg 填充响应消息
     * @return Resp msg=> ${msg}; code=> "200"
     */
    public static Resp ok(String msg) {
        return new Resp().setCode(Code.OK.getVal()).setMsg(msg).setSucceed(true);
    }

    /**
     * 处理完成, 正常响应结果
     *
     * @param data 填充响应数据
     * @return Resp msg=> "ok"; code=> "200"
     */
    public static Resp ok(Object data) {
        return new Resp().setData(data).setCode(Code.OK.getVal()).setMsg("ok").setSucceed(true);
    }

    /**
     * 处理失败, 返回指定错误提示, 指定异常代码
     *
     * @param errMsg  错误提示信息
     * @param expCode 错误代码  {@link Code}
     * @return Resp msg=> ${errMsg}; code=> ${expCode}
     */
    public static Resp fail(String errMsg, Code expCode) {
        return new Resp().setMsg(errMsg).setCode(expCode.getVal());
    }

    /**
     * 处理失败, 返回自定义错误消息
     *
     * @param errMsg 错误提示信息
     * @return Resp msg=> ${errMsg}; code=> "500"
     */
    public static Resp fail(String errMsg) {
        return new Resp().setCode(Code.SYSTEM_EXCEPTION.getVal()).setMsg(errMsg);
    }

    /**
     * 因发生异常导致的失败
     *
     * @param exception 异常
     * @return Resp msg=> ${Exception}.msg; code=> "500"
     */
    public static Resp fail(Exception exception) {
        return new Resp().setCode(Code.SYSTEM_EXCEPTION.getVal())
                .setMsg(exception.getMessage()).setExpStack(exception.getStackTrace());
    }

    /**
     * 因参数未经过校验导致处理失败时, 使用该方法
     *
     * @param errMsg 参数校验失败的提示
     * @return Resp msg=> ${errMsg}; code=> "400"
     */
    public static Resp unProcess(String errMsg) {
        return new Resp().setCode(Code.UNPROCESSABLE_ENTITY.getVal()).setMsg(errMsg);
    }

    /**
     * 参数校验失败调用该响应
     *
     * @param data 包含失败的参数及提示
     * @return Resp code=> "400"; data=> ${data}
     */
    public static Resp unProcess(Object data) {
        return new Resp().setCode(Code.UNPROCESSABLE_ENTITY.getVal()).setData(data)
                .setMsg("参数校验失败");
    }

    /**
     * 因资源未找到导致的处理失败
     *
     * @param errMsg 提示信息
     * @return Resp msg=> ${errMsg}; code=> "404"
     */
    public static Resp notFound(String errMsg) {
        return new Resp().setCode(Code.NOT_FOUND.getVal()).setMsg(errMsg);
    }

    /**
     * 在需要鉴权而用户未携带权限校验信息时
     *
     * @return Resp code=> "401"; msg=> "需要认证信息"
     */
    public static Resp noAuth() {
        return new Resp().setCode(Code.UNAUTHORIZED.getVal()).setMsg("需要认证信息");
    }

    /**
     * 权限不足
     *
     * @return Resp code=> "403"; msg=> "权限不足"
     */
    public static Resp denied() {
        return new Resp().setCode(Code.PERMISSION_DENIED.getVal()).setMsg("权限不足");
    }

    public boolean isSucceed() {
        return succeed;
    }

    public Resp setSucceed(boolean succeed) {
        this.succeed = succeed;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Resp setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Resp setData(Object data) {
        this.data = data;
        return this;
    }

    public StackTraceElement[] getExpStack() {
        return expStack;
    }

    public Resp setExpStack(StackTraceElement[] expStack) {
        this.expStack = expStack;
        return this;
    }

    public int getCode() {
        return code;
    }

    public Resp setCode(int code) {
        this.code = code;
        return this;
    }
}
