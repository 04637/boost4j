package dev.aid.boost4j.util;

/**
 * 数据库分页 limit,
 *
 * @author 04637@163.com
 * @date 2020/11/10
 */
public class PageUtil {

    /**
     * 获取数据库使用的 limit
     *
     * @param pageSize 分页size
     * @return limit
     */
    public static int limit(int pageSize) {
        if (pageSize < 0) {
            pageSize = 0;
        }
        return pageSize;
    }

    /**
     * 获取数据库使用的 offset
     *
     * @param pageNo   第几页
     * @param pageSize 每页条数
     * @return offset
     */
    public static int offset(int pageNo, int pageSize) {
        if (pageNo < 1) {
            pageNo = 1;
        }
        return (pageNo - 1) * pageSize;
    }

    private PageUtil() {
    }
}
