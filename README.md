# boost4j
An out-of-the-box library for java web.
公共通用代码库
目的:
1. 消除团队间技术壁垒, 共同贡献代码, 让好的代码及工具在团队间流通, 共享资源及技术
2. 降低新建项目时的搜索, 剥离代码成本
3. 统一维护升级, 共用一套通用代码库, 有效减少通用代码库的bug
3. 提供极简的调用方式, 详尽的使用示例, 积累部门技术资源

# 需求
## 小
1. http工具类 √    //封装okHttp, 默认连接池, 支持http2, 极简调用
2. Resp统一响应体 √  //封装各类常用响应, 应对各类返回场景并可携带异常信息供快速排错
3. 加解密
4. 雪花ID √   //解决雪花时钟回拨问题, 隐藏构造, 生成效率较UUID提升60%+
5. 参数校验 (附带断言式判断)
6. 统一异常
7. 时间戳转Timestamp(XKKT) √
8. 文件上传
9. 日期工具类获取当前数据
10. 分页转换
11. fhIM通知
12. sonar通知
13. 异步执行
14. 类似mbp的类生成
## 大
1. 轻量缓存
2. 接口文档生成
## 不考虑
1. xss过滤 (不再需要, 如果后台处理对性能影响较大, 前端有更专业更具针对性的解决方案)
2. sql注入过滤 (不再需要, 目前大家都已使用mybatis预编译, 无需再做处理, 影响性能)
## 文档需求
1. 使用demo编写整理 (尽量详细)
2. 开发


# 贡献规范
1. 约定优于配置 (尽可能地提供默认配置, 减少调用方的配置)
2. 让调用方足够方便, 如非必要
    1). 禁止外抛异常, 需要外抛的异常使用 Resp.fail(exp) 返回给调用方
3. 必须编写单元测试代码并通过测试