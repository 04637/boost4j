package dev.aid.boost4j.util;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import okhttp3.OkHttpClient;

/**
 * Http工具类
 *
 * @author 04637@163.com
 * @date 2020/11/5
 */
public class HttpUtil {

    private static final OkHttpClient httpClient = new OkHttpClient();

    private static final int TIME_OUT = 30000;


    public static String get(String url, Params params) {
        return null;
    }


    /**
     * post请求
     *
     * @param url      请求连接
     * @param params   拼接参数
     * @param postData post发送数据
     */
    public static String post(String url, Params params, JSONObject postData) {
        return null;
    }

    private HttpUtil(){}

}
