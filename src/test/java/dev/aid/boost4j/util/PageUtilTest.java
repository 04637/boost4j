package dev.aid.boost4j.util;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 分页转换测试
 *
 * @author 04637@163.com
 * @date 2020/11/10
 */
public class PageUtilTest {

    @Test
    @DisplayName("limit测试")
    public void testLimit() {
        int limit = PageUtil.limit(1);
        assertEquals(1, limit);
        limit = PageUtil.limit(0);
        assertEquals(0, limit);
        limit = PageUtil.limit(-1);
        assertEquals(0, limit);
    }

    @Test
    @DisplayName("offset测试")
    public void testOffset() {
        int pageNo = 3;
        int pageSize = 10;
        int limit = PageUtil.limit(pageSize);
        int offset = PageUtil.offset(pageNo, pageSize);
        assertEquals(10, limit);
        assertEquals(20, offset);

        pageNo = -3;
        offset = PageUtil.offset(pageNo, pageSize);
        assertEquals(0, offset);
    }
}
