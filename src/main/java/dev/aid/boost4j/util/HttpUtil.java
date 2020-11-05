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

    private static String get(String url, Params params, long timeout) {
        return null;
    }

    public static String get(String url, Params params) {
        String urlParams = params.build();
        StringBuilder response = new StringBuilder();
        try {
            URL realUrl = new URL(url + urlParams);
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setConnectTimeout(TIME_OUT);
            connection.setReadTimeout(TIME_OUT);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)
            );
            response = new StringBuilder();
            String responseLine;
            while ((responseLine = reader.readLine()) != null) {
                response.append(responseLine);
            }
            // log.info("请求完成: url: {}, response: {}", realUrl.getPath(), response.toString());
        } catch (MalformedURLException e) {
            // log.error("请求拼接出错: {}", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            // log.error("请求IO出错: {}", e.getMessage());
            e.printStackTrace();
        }
        return response.toString();
    }


    /**
     * post请求
     *
     * @param url      请求连接
     * @param params   拼接参数
     * @param postData post发送数据
     */
    public static String post(String url, Params params, JSONObject postData) {
        String urlParams = params.build();
        StringBuilder response = new StringBuilder();
        // log.info("发起请求: url: {}, params: {}, data: {}",
        // url, JSON.toJSONString(params), postData.toJSONString());
        try {
            URL realUrl = new URL(url + urlParams);
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setConnectTimeout(TIME_OUT);
            connection.setReadTimeout(TIME_OUT);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream()) {
                byte[] data = postData.toJSONString().getBytes(StandardCharsets.UTF_8);
                os.write(data, 0, data.length);
            }
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)
            );
            response = new StringBuilder();
            String responseLine;
            while ((responseLine = reader.readLine()) != null) {
                response.append(responseLine);
            }
            // log.info("请求完成: url: {}, response: {}", realUrl.getPath(), response.toString());
        } catch (MalformedURLException e) {
            // log.error("请求拼接出错: {}", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            // log.error("请求IO出错: {}", e.getMessage());
            e.printStackTrace();
        }
        return response.toString();
    }

}
