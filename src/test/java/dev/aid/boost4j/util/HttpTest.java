package dev.aid.boost4j.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dev.aid.boost4j.common.Resp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Http请求测试
 *
 * @author 04637@163.com
 * @date 2020/11/6
 */
public class HttpTest {

    private static final String BASE_URL = "http://httpbin.org/";

    @Test
    @DisplayName("get请求")
    public void testGet() {
        Resp resp = Http.get(BASE_URL + "get",
                Params.n().add("hello", "world"),
                Params.n().add("Authorization", "eyJraWQiOiJjb2xsZWFn"));
        assertEquals("world", getArg(resp, "hello"));
        assertEquals("eyJraWQiOiJjb2xsZWFn", getHeader(resp, "Authorization"));
        assertEquals(200, resp.getCode());
    }

    @Test
    @DisplayName("超时get请求")
    public void testGetTimeout() {
        Resp resp = Http.get(BASE_URL + "delay/10");
        assertEquals(500, resp.getCode());
        assertFalse(resp.isSucceed());
        assertEquals("timeout", resp.getMsg());
    }

    @Test
    @DisplayName("post请求")
    public void testPost() {
        JSONObject jo = new JSONObject();
        jo.put("hello", "world");
        Resp resp = Http.post("http://httpbin.org/post", jo);
        assertEquals(200, resp.getCode());
        assertTrue(resp.isSucceed());
        assertEquals("{\"hello\":\"world\"}", getData(resp));
    }

    @Test
    @DisplayName("超时post请求")
    public void testPostTimeout() {
        Resp resp = Http.post(BASE_URL + "delay/1000");
        assertEquals(500, resp.getCode());
        assertFalse(resp.isSucceed());
        assertEquals("timeout", resp.getMsg());
    }

    private String getHeader(Resp resp, String name) {
        JSONObject headers = JSON.parseObject(resp.getData().toString()).getJSONObject("headers");
        return headers.getString(name);
    }

    private String getArg(Resp resp, String name) {
        JSONObject args = JSON.parseObject(resp.getData().toString()).getJSONObject("args");
        return args.getString(name);
    }

    private String getData(Resp resp) {
        return JSON.parseObject(resp.getData().toString()).getString("data");
    }

}
