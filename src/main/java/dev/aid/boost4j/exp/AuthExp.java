package dev.aid.boost4j.exp;

import dev.aid.boost4j.common.Code;

/**
 * 鉴权异常
 *
 * @author 04637@163.com
 * @date 2020/11/24
 */
public class AuthExp extends BaseException {

    private Code expCode = Code.PERMISSION_DENIED;

    public AuthExp(String msg) {
        super(msg);
    }

    public AuthExp(Throwable originalExp) {
        super(originalExp);
    }

    public AuthExp setExpCode(Code expCode) {
        this.expCode = expCode;
        return this;
    }

    public Code getExpCode() {
        return expCode;
    }
}
