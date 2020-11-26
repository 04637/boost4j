package dev.aid.boost4j.util;

import org.apache.commons.lang3.StringUtils;

/**
 * sql相关工具
 *
 * @author 04637@163.com * @date 2020/11/26
 */
public class SqlUtil {

    /**
     * 过滤搜索关键字中的通配符, 并拼装 %
     *
     * @param keywords 搜索关键字
     */
    public static String likeSearch(String keywords) {
        if (StringUtils.isEmpty(keywords)) {
            return "%";
        }
        return '%' + keywords.replaceAll("%", "")
                .replaceAll("_", "")
                .replaceAll("\\[", "")
                .replaceAll("]", "") + '%';

    }
}
