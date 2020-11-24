package dev.aid.boost4j.mbp;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * 代码生成器
 *
 * @author 04637@163.com
 * @date 2020/11/18
 */
public class CodeGenerator {

    // ################# 生成器配置 #################
    // 数据源配置
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/dsim";
    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    private static final String JDBC_USERNAME = "postgres";
    private static final String JDBC_PASSWORD = "123456";
    // 父级包名配置
    private static final String PACKAGE_NAME = "dev.aid.dsim";
    // 生成代码的 @author 值
    private static final String AUTHOR = "04637@163.com";
    // 要生成代码的表名配置
    private static final String[] TABLES = {
            // "data_interface",
            // "project_info",
            // "project_user",
            // "table_code"
            "user_info"
    };
    // ################# 生成器配置 end ##############

    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor(AUTHOR);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        // 生成完毕后是否打开输出目录
        gc.setOpen(false);
        // 为true时生成entity将继承Model类，单类即可完成基于单表的业务逻辑操作，按需开启
        gc.setActiveRecord(false);
        gc.setServiceName("%sService");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(JDBC_URL);
        dsc.setDriverName(JDBC_DRIVER);
        dsc.setUsername(JDBC_USERNAME);
        dsc.setPassword(JDBC_PASSWORD);
        dsc.setTypeConvert(new MyTypeConvert());
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        // 父级包名，按需修改
        pc.setParent(PACKAGE_NAME);
        // 设置模块名, 会在parent包下生成一个指定的模块包
        pc.setModuleName(null);
        pc.setMapper("dao");
        pc.setXml("dao/mapper");
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setRestControllerStyle(true);
        strategy.setSkipView(true);
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setInclude(TABLES);
        strategy.setSuperEntityColumns("id");
        // Controller驼峰连字符，如开启，则requestMapping由 helloWorld 变为 hello-world 默认false
        strategy.setControllerMappingHyphenStyle(false);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        // 开启后将使用lombok注解代替set-get方法，false则生成set-get方法
        strategy.setEntityLombokModel(true);
        // 在实体类中移除is前缀
        strategy.setEntityBooleanColumnRemoveIsPrefix(false);
        strategy.setSkipView(true);
        mpg.setStrategy(strategy);
        mpg.execute();
    }
}
