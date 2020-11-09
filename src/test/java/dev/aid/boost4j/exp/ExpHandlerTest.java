package dev.aid.boost4j.exp;


import com.alibaba.fastjson.JSONObject;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import dev.aid.boost4j.common.Resp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;

    private static final String BASE = "http://localhost:8080/expTest/";

    @Before
    public void setupMockMvc() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

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
    @DisplayName("参数异常")
    public void testArgExp() throws Exception {
        // 转换失败异常  HttpMessageNotReadableException
        JSONObject arg = new JSONObject();
        arg.put("bindFailed", "123");
        arg.put("maxLength", "12345678");
        arg.put("notEmpty", "");
        Resp resp = testTemplate.postForObject(BASE + "arg", arg, Resp.class);
        mvc.perform(MockMvcRequestBuilders.post(BASE + "arg")
                .contentType(MediaType.APPLICATION_JSON)
                .content(arg.toJSONString()));
        // Resp resp1 = testTemplate.getForObject(BASE+"arg?bindFailed=123&maxLength=123456", Resp.class);
        System.out.println(resp);
        // assertFalse(resp.isSucceed());
        // assertEquals(400, resp.getCode());
        // assertEquals("JSON parse error: Cannot deserialize value of type `int` from String \"abc\": not a valid Integer value; nested exception is com.fasterxml.jackson.databind.exc.InvalidFormatException: Cannot deserialize value of type `int` from String \"abc\": not a valid Integer value\n" +
        //                 " at [Source: (PushbackInputStream); line: 1, column: 15] (through reference chain: dev.aid.boost4j.exp.ArgTest[\"bindFailed\"])",
        //         resp.getMsg());
        // // 长度超出校验失败异常
        // arg.put("bindFailed", "123");
        // arg.put("maxLength", "12345678");
        // resp = testTemplate.postForObject(BASE + "arg", arg, Resp.class);
        // System.out.println(resp);
    }
}
