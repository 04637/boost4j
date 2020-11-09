package dev.aid.boost4j.exp;

import dev.aid.boost4j.common.Code;

/**
 * 业务层异常
 *
 * @author 04637@163.com
 * @date 2020/11/9
 */
public class ServiceExp extends BaseException {

    // 异常代码, 默认系统异常 500
    private Code expCode = Code.SYSTEM_EXCEPTION;

    public ServiceExp(String msg) {
        super(msg);
    }

    public ServiceExp(Throwable throwable) {
        super(throwable);
    }

    public ServiceExp() {
        super("业务层异常, 请向开发人员反馈 expStack");
    }

    public void setExpCode(Code expCode) {
        this.expCode = expCode;
    }

    public Code getExpCode() {
        return expCode;
    }
}
