package dev.aid.boost4j.exp;


import com.alibaba.fastjson.JSONObject;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.naming.NoPermissionException;

import dev.aid.boost4j.common.Resp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * 异常处理器测试
 * 测试参考=> http://tengj.top/2017/12/28/springboot12/
 *
 * @author 04637@163.com
 * @date 2020/11/9
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@SpringBootApplication
public class ExpHandlerTest {

    @Autowired
    private TestRestTemplate testTemplate;

    private static final String BASE = "http://localhost:8080/expTest/";

    @Test
    @DisplayName("业务层异常")
    public void testServiceExp() {
        Resp resp = testTemplate.getForObject(BASE + "service", Resp.class);
        assertEquals(500, resp.getCode());
        assertFalse(resp.isSucceed());
        assertEquals("业务层异常, 请向开发人员反馈 expStack", resp.getMsg());

        // 带信息的业务层异常
        Resp withMsg = testTemplate.getForObject(BASE + "serviceWithMsg?msg=出错啦", Resp.class);
        assertEquals(500, withMsg.getCode());
        assertFalse(withMsg.isSucceed());
        assertEquals("出错啦", withMsg.getMsg());

        // 因异常抛出的异常
        Resp withExp = testTemplate.getForObject(BASE + "serviceExp", Resp.class);
        assertEquals(403, withExp.getCode());
        assertFalse(withExp.isSucceed());
        assertEquals("权限不足", withExp.getMsg());

        // 其他异常, 测试保底异常处理
        Resp otherExp = testTemplate.getForObject(BASE + "otherExp", Resp.class);
        assertFalse(otherExp.isSucceed());
        assertEquals(500, otherExp.getCode());
        assertEquals("未知异常: javax.naming.NoPermissionException, 请向开发人员反馈 expStack",
                otherExp.getMsg());
    }

    @Test
    @DisplayName("数据处理层异常")
    public void testDaoExp() {
        Resp resp = testTemplate.getForObject(BASE + "dao", Resp.class);
        assertEquals(500, resp.getCode());
        assertFalse(resp.isSucceed());
        assertEquals("数据层处理异常, 请向开发人员反馈 expStack", resp.getMsg());
        // 待信息的数据层异常
        Resp withMsg = testTemplate.getForObject(BASE + "daoWithMsg?msg=数据层出错", Resp.class);
        assertEquals(500, withMsg.getCode());
        assertFalse(withMsg.isSucceed());
        assertEquals("数据层出错", withMsg.getMsg());
    }

    @Test
    @DisplayName("包装异常")
    public void testPackExp() {
        try {
            throw new ServiceExp().setOriginalExp(new NoPermissionException("权限异常0"));
        } catch (ServiceExp e) {
            assertNotNull(e.getOriginalExp());
            assertEquals("javax.naming.NoPermissionException",
                    e.getOriginalExp().getClass().getName());
            assertEquals("业务层异常, 请向开发人员反馈 expStack", e.getMessage());
        }
    }

    @Test
    @DisplayName("参数异常")
    public void testArgExp() throws Exception {
        // 转换失败异常  HttpMessageNotReadableException
        JSONObject arg = new JSONObject();
        arg.put("bindFailed", "abc");
        arg.put("maxLength", "12345678");
        arg.put("notEmpty", "");
        Resp resp = testTemplate.postForObject(BASE + "arg", arg, Resp.class);
        assertEquals("JSON parse error: Cannot deserialize value of type `int` from String \"abc\": not a valid Integer value; nested exception is com.fasterxml.jackson.databind.exc.InvalidFormatException: Cannot deserialize value of type `int` from String \"abc\": not a valid Integer value\n" +
                " at [Source: (PushbackInputStream); line: 1, column: 15] (through reference chain: dev.aid.boost4j.exp.ArgTest[\"bindFailed\"])", resp.getMsg());
        assertFalse(resp.isSucceed());
        assertEquals(400, resp.getCode());
        // 校验异常
        arg.put("bindFailed", "8");
        resp = testTemplate.postForObject(BASE + "arg", arg, Resp.class);
        assertFalse(resp.isSucceed());
        assertEquals("参数校验失败", resp.getMsg());
        assertEquals("{notEmpty=该参数不能为空哦, maxLength=长度超长了}", resp.getData().toString());
        assertEquals(400, resp.getCode());
    }


    @Test
    @DisplayName("断言出错异常")
    public void testAssertExp() {
        Resp resp = testTemplate.getForObject(BASE + "paramExp", Resp.class);
        assertFalse(resp.isSucceed());
        assertEquals("mama 不能为空", resp.getMsg());
        assertEquals(400, resp.getCode());
    }
}
