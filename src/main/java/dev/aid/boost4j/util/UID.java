package dev.aid.boost4j.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 使用雪花算法, 生成速度比UUID快60%+, 且位数较短, 时间有序
 * 对雪花算法的时钟回拨问题做处理
 *
 * @author 04637@163.com
 * @date 2020/11/7
 */
public class UID {
    /**
     * 开始时间截 (2015-01-01)
     */
    private static final long START_TIME = 1420041600000L;

    /**
     * 机器id所占的位数
     */
    private static final long WORKER_ID_BITS = 5L;

    /**
     * 数据标识id所占的位数
     */
    private static final long DATA_CENTER_ID_BITS = 5L;

    /**
     * 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);

    /**
     * 支持的最大数据标识id，结果是31
     */
    private static final long MAX_DATA_CENTER_ID = ~(-1L << DATA_CENTER_ID_BITS);

    /**
     * 序列在id中占的位数
     */
    private static final long SEQUENCE_BITS = 12L;

    /**
     * 机器ID向左移12位
     */
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;

    /**
     * 数据标识id向左移17位(12+5)
     */
    private static final long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

    /**
     * 时间截向左移22位(5+5+12)
     */
    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;

    /**
     * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
     */
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

    /**
     * 工作机器ID(0~31)
     */
    private static long workerId;

    /**
     * 数据中心ID(0~31)
     */
    private static long dataCenterId;

    /**
     * 毫秒内序列(0~4095)
     */
    private static long sequence = 0L;

    /**
     * 上次生成ID的时间截
     */
    private static long lastTimestamp = -1L;

    //==============================Constructors=====================================
    static {
        init();
    }

    /**
     * 初始化
     */
    private static void init() {
        long workerId = 0;
        long dataCenterId = 0;
        try {
            InetAddress addr = InetAddress.getLocalHost();
            String hostAddr = addr.getHostAddress();
            String endIp = hostAddr.substring(hostAddr.lastIndexOf(".") + 1);
            int id = Integer.parseInt(endIp);
            workerId = id % MAX_WORKER_ID;
            dataCenterId = id % MAX_DATA_CENTER_ID;
        } catch (UnknownHostException ignored) {
        }

        UID.workerId = workerId;
        UID.dataCenterId = dataCenterId;
    }


    // ==============================Methods==========================================

    /**
     * 生成18位纯数字ID, 时间有序
     */
    public static String next() {
        return next(workerId, dataCenterId);
    }

    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    private static synchronized String next(long workerId, long dataCenterId) {
        long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            // throw new RuntimeException(
            //         String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
            // 处理时钟回拨问题
            lastTimestamp = timestamp;
            // 临时使用一次备用id, 因为上面取模运算, 不会使用到该 ID
            return next(workerId + 1, dataCenterId + 1);
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        return (((timestamp - START_TIME) << TIMESTAMP_LEFT_SHIFT) //
                | (dataCenterId << DATA_CENTER_ID_SHIFT) //
                | (workerId << WORKER_ID_SHIFT) //
                | sequence) + "";
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    private static long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    private static long timeGen() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            System.out.println(UID.next());
            Thread.sleep(1000);
        }
    }
}
