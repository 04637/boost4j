package dev.aid.boost4j.common;

/**
 * 响应码枚举
 *
 * @author 04637@163.com
 * @date 2020/11/6
 */
public enum RespCode {
    // 默认系统异常
    SYSTEM_EXCEPTION(500),

    // 权限不足异常
    PERMISSION_DENIED(403),

    // 未携带鉴权信息
    UNAUTHORIZED(401),

    // 表单参数校验失败
    UNPROCESSABLE_ENTITY(400),

    // 未找到资源
    NOT_FOUND(404),

    // 无异常
    OK(200);

    private final int val;

    RespCode(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
