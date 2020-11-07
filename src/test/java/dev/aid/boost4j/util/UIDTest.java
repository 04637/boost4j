package dev.aid.boost4j.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 唯一ID测试
 *
 * @author 04637@163.com
 * @date 2020/11/7
 */
public class UIDTest {

    @Test
    @DisplayName("正常ID生成")
    public void testNext() {
        String id = UID.next();
        Assertions.assertEquals(18, id.length());
    }
}
