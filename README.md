# boost4j
An out-of-the-box library for java web.

# 需求
## 小
1. http工具类(考虑连接池) 为什么选择okhttp: https://github.com/square/okhttp/issues/3472
2. Resp统一响应体
3. 加解密
4. 雪花ID
6. 参数校验 (附带断言式判断)
8. 统一异常
10. 时间戳转Timestamp(XKKT)
11. 文件上传
12. 日期工具类获取当前数据
13. 分页转换
## 大
5. 轻量缓存
7. 接口文档生成
9. 配置热更新 WatchService

# 贡献规范
1. 约定优于配置 (尽可能地提供默认配置, 减少调用方的配置)
2. 让调用方足够方便
    如非必要
    a). 禁止外抛异常