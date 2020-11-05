package dev.aid.boost4j.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求参数拼接器
 *
 * @author: 04637@163.com
 * @date: 2020/11/5
 */
public class Params {
    private final Map<String, String> map = new HashMap<>();

    /**
     * 代替 new Params();
     */
    public static Params n() {
        return new Params();
    }

    /**
     * 添加参数
     *
     * @param name  参数名名
     * @param value 参数值
     */
    public Params add(String name, String value) {
        map.put(name, value);
        return this;
    }

    /**
     * 构建请求参数
     * 例: 将 {"name": "1", "age", "12"}
     *
     * @return "?name=1&value=2"
     */
    public String build() {
        if (map.isEmpty()) {
            return "";
        }
        StringBuilder paramUrl = new StringBuilder("?");
        for (Map.Entry<String, String> e : map.entrySet()) {
            paramUrl.append(e.getKey()).append("=").append(e.getValue()).append("&");
        }
        return paramUrl.substring(0, paramUrl.length() - 1);
    }
}
