package dev.aid.boost4j.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 统一响应体测试
 *
 * @author 04637@163.com
 * @date 2020/11/6
 */
public class RespTest {

    @Test
    @DisplayName("正常响应")
    public void testOk() {
        String data = "mydata";
        Resp result = Resp.ok(data);
        assertEquals(200, result.getCode());
        assertEquals("mydata", result.getData());
    }

    @Test
    @DisplayName("失败响应")
    public void testDefaultFailed() {
        // 默认失败响应
        String msg = "this is default errMsg";
        Resp defaultR = Resp.fail(msg);
        assertEquals(msg, defaultR.getMsg());
        assertEquals(500, defaultR.getCode());

        // 异常失败
        msg = "exception fail";
        IllegalStateException exp = new IllegalStateException(msg);
        Resp withExp = Resp.fail(exp);
        assertEquals(msg, withExp.getMsg());
        assertEquals(500, withExp.getCode());
        assertEquals(Arrays.toString(exp.getStackTrace()), Arrays.toString(withExp.getExpStack()));

        // msg+code失败
        msg = "code fail";
        Resp failCode = Resp.fail(msg, RespCode.NOT_FOUND);
        assertEquals(404, failCode.getCode());
        assertEquals(msg, failCode.getMsg());

        // 参数校验失败
        msg = "over max legnth";
        Resp unProcess = Resp.unProcess(msg);
        assertEquals(msg, unProcess.getMsg());
        assertEquals(400, unProcess.getCode());

        // 未找到资源
        msg = "not found";
        Resp notFound = Resp.notFound(msg);
        assertEquals(msg, notFound.getMsg());
        assertEquals(404, notFound.getCode());

        // 为携带验证信息失败
        Resp noAuth = Resp.noAuth();
        assertEquals("需要认证信息", noAuth.getMsg());
        assertEquals(401, noAuth.getCode());

        // 权限不足
        Resp denied = Resp.denied();
        assertEquals("权限不足", denied.getMsg());
        assertEquals(403, denied.getCode());
    }

}
