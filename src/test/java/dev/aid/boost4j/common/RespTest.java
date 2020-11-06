package dev.aid.boost4j.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * 统一响应体测试
 *
 * @author 04637@163.com
 * @date 2020/11/6
 */
public class RespTest {

    @Test
    @DisplayName("正常响应")
    public void TestOk() {
        String data = "mydata";
        Resp result = Resp.ok(data);
        assertEquals(200, result.getCode());
        assertEquals("mydata", result.getData());
    }

    @Test
    @DisplayName("失败响应")
    public void TestDefaultFailed() {
        // 默认失败响应
        Resp defaultR = Resp.fail("this is default errMsg");
        assertEquals("this is default errMsg", defaultR.getMsg());
        assertEquals(500, defaultR.getCode());

        // 异常失败
        Resp withExp = Resp.fail(new IllegalAccessException("exception fail"));
        assertEquals("exception fail", withExp.getMsg());
        assertEquals(500, withExp.getCode());

        // msg+code失败
        Resp failCode = Resp.fail("errMsg", RespCode.NOT_FOUND);
        assertEquals(404, failCode.getCode());
        assertEquals("errMsg", failCode.getMsg());
    }

}
