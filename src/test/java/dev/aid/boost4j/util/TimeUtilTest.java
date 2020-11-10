package dev.aid.boost4j.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * 时间工具类测试
 *
 * @author 04637@163.com
 * @date 2020/11/7
 */
public class TimeUtilTest {

    @Test
    @DisplayName("sql时间转换")
    public void TestToSqlTime() {
        long cur13 = System.currentTimeMillis();
        String cur10 = cur13 / 1000 + "";
        Timestamp cur13T = TimeUtil.toSqlTime(cur13);
        Timestamp cur10T = TimeUtil.toSqlTime(cur10);
        assert cur10T != null;
        assertEquals(cur13T.toString().substring(0, cur13T.toString()
                        .lastIndexOf(".") + 1) + "0",
                cur10T.toString());
    }

    @Test
    @DisplayName("当前时间")
    public void TestCurTime() {
        String curTime = TimeUtil.toSqlTime(System.currentTimeMillis()).toString();
        assertEquals(curTime.substring(0, curTime.indexOf("-")), TimeUtil.curYear() + "");
        assertEquals(curTime.substring(curTime.indexOf("-") + 1, curTime.lastIndexOf("-")),
                TimeUtil.curMonth() + "");
        assertEquals((TimeUtil.curMonth() - 1) / 3 + 1, TimeUtil.curQuarter());
    }
}
