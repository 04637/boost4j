package dev.aid.boost4j.util;

import com.alibaba.fastjson.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ResourceUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

/**
 * 热更新配置, 监听文件更改, 不用重启即可更新属性
 * 参考 https://www.baeldung.com/java-nio2-watchservice
 * TODO 没写完, 考虑是否可以不依赖配置文件且易于使用, 考虑通过代码配置
 *
 * @author 04637@163.com
 * @date 2020/11/10
 */
public class HotConfig {

    private static WatchService watchService;
    // boost4j resource下的配置相对路径
    private static final String BOOST4J_YML = "classpath:boost4j.yml";

    static {
        try {
            JSONObject boost4j = loadBoost4jConfig();
            if (boost4j != null) {
                JSONObject hotConfig = boost4j.getJSONObject("hotConfig");
                Boolean enable = hotConfig.getBoolean("enable");
                String watchDir = hotConfig.getString("watchDir");
                // hotConfig.enable=true 且 监控目录不为空时, 开启监听
                if (enable != null && enable) {
                    if (StringUtils.isEmpty(watchDir)) {
                        System.out.println("hotConfig.watchDir为空, 无法监听");
                    } else {
                        System.out.printf("开启热更新配置监控: %s\n", watchDir);
                        watchService = FileSystems.getDefault()
                                .newWatchService();
                        watch(watchDir);
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void watch(String dir) throws IOException {
        Path path = Paths.get(URI.create(dir));
        WatchKey watchKey = path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
        System.out.println(watchKey);
    }

    private static JSONObject loadBoost4jConfig() {
        try (FileInputStream fis = new FileInputStream(ResourceUtils.getFile(BOOST4J_YML))) {
            Yaml boost4jYml = new Yaml();
            return boost4jYml.loadAs(fis, JSONObject.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws IOException {

    }

}
