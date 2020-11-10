package dev.aid.boost4j.util;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import dev.aid.boost4j.common.Resp;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Http工具类
 * 为什么选择okhttp: https://github.com/square/okhttp/issues/3472
 *
 * @author 04637@163.com
 * @date 2020/11/5
 */
public class HttpUtil {

    // 连接超时 单位 [秒]
    private static final int CONNECT_TIMEOUT = 10;
    // 读取超时 单位 [秒]
    private static final int READ_TIMEOUT = 10;

    // default timeout is 10 seconds:  https://www.baeldung.com/okhttp-timeouts#:~:text=A%20connect%20timeout%20defines%20a,its%20value%20using%20the%20OkHttpClient.
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS).build();


    /**
     * get请求
     *
     * @param url     请求地址, 注意以协议开头
     * @param params  {@link Params}
     * @param headers {@link Params}
     */
    public static Resp get(String url, Params params, Params headers) {
        if (isIllegalUrl(url)) {
            return Resp.unProcess("不合法的URL, 请填写完整的url: http://xxxx or https://xxxx");
        }
        Request request = new Request.Builder()
                .get()
                .url(url + params.build())
                .headers(Headers.of(headers.get()))
                .build();
        try (Response response = client.newCall(request).execute()) {
            Resp result = new Resp();
            result.setCode(response.code());
            result.setMsg(response.message());
            ResponseBody body = response.body();
            if (body != null) {
                result.setData(body.string());
            }
            result.setSucceed(true);
            return result;
        } catch (IOException e) {
            return Resp.fail(e);
        }
    }


    /**
     * post请求
     *
     * @param url      请求连接
     * @param params   拼接参数
     * @param postData post发送数据
     */
    public static Resp post(String url, Params params, JSONObject postData, Params headers) {
        if (isIllegalUrl(url)) {
            return Resp.unProcess("不合法的URL, 请填写完整的url: http://xxxx or https://xxxx");
        }
        RequestBody body = RequestBody.create(postData.toJSONString(),
                MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url + params.build())
                .post(body)
                .headers(Headers.of(headers.get()))
                .build();
        Resp result = new Resp();
        try (Response response = client.newCall(request).execute()) {
            result.setCode(response.code());
            result.setMsg(response.message());
            ResponseBody respBody = response.body();
            if (respBody != null) {
                result.setData(respBody.string());
            }
            result.setSucceed(true);
            return result;
        } catch (IOException e) {
            return Resp.fail(e);
        }
    }


    public static Resp get(String url) {
        return get(url, Params.n(), Params.n());
    }

    public static Resp get(String url, Params params) {
        return get(url, params, Params.n());
    }

    public static Resp post(String url) {
        return post(url, Params.n(), new JSONObject(), Params.n());
    }

    public static Resp post(String url, Params params) {
        return post(url, params, new JSONObject(), Params.n());
    }

    public static Resp post(String url, JSONObject postData) {
        return post(url, Params.n(), postData, Params.n());
    }

    public static Resp post(String url, Params params, JSONObject postData) {
        return post(url, params, postData, Params.n());
    }

    private static boolean isIllegalUrl(String url) {
        return !url.startsWith("http://") && !url.startsWith("https://");
    }

    private HttpUtil() {
    }

}
