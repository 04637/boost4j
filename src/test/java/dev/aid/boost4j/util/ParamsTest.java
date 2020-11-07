package dev.aid.boost4j.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 参数组装器测试
 *
 * @author: 04637@163.com
 * @date: 2020/11/5
 */

public class ParamsTest {

    @Test
    @DisplayName("正常拼接")
    public void testNormal() {
        String result = Params.n().add("name", "daming")
                .add("age", "1").build();
        assertEquals("?name=daming&age=1", result);
    }

    @Test
    @DisplayName("空参数")
    public void testEmpty() {
        String result = Params.n().build();
        assertEquals("", result);
    }
}
