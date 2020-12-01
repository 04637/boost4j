package dev.aid.boost4j.util;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;

/**
 * 时间工具类
 *
 * @author 04637@163.com
 * @date 2020/11/7
 */
public class TimeUtil {

    /**
     * 将长整形时间戳转为sql中的时间戳 {@link Timestamp}
     *
     * @param time 支持13位/10位时间戳
     * @return Timestamp
     */
    public static Timestamp toSqlTime(long time) {
        return toSqlTime(time + "");
    }


    /**
     * 获取数据库当前时间戳
     */
    public static Timestamp now() {
        return Timestamp.from(Instant.now());
    }

    /**
     * 将字符串时间戳转为sql中的时间戳 {@link Timestamp}
     *
     * @param time 支持13位/10位时间戳
     * @return Timestamp
     */
    public static Timestamp toSqlTime(String time) {
        if (time.length() == 10) {
            return new Timestamp(Long.parseLong(time + "000"));
        } else if (time.length() == 13) {
            return new Timestamp(Long.parseLong(time));
        }
        return null;
    }

    /**
     * 获取当前年
     */
    public static int curYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取当前月份, 屏蔽需要加1的操作
     */
    public static int curMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前季度
     */
    public static int curQuarter() {
        return (curMonth() - 1) / 3 + 1;
    }
}
