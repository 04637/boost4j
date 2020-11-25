package dev.aid.boost4j.mbp;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

import dev.aid.boost4j.util.UID;

/**
 * mybatis plus
 * todo 需要配置spring扫描包 dev.aid.boost4j,  例如springboot
 * 启动类添加注解 @ComponentScan(basePackages = {"dev.aid.boost4j", "com.fh.xx"})
 * 将扫描配置为你的dao目录, 例如: @MapperScan("dev.aid.dsim.dao")
 *
 * @author 04637@163.com
 * @date 2020/11/18
 */
@Configuration
@MapperScan("dev.aid.dsim.dao")
public class MybatisPlusConfig {

    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.POSTGRE_SQL));
        return interceptor;
    }

    /**
     * 使用雪花算法代替ID生成, 为空时自动插入
     */
    @Bean
    public IdentifierGenerator identifierGenerator() {
        return new MyIdGenerator();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public MybatisSqlSessionFactoryBean sqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:/mapper/*Mapper.xml"));
        return sqlSessionFactoryBean;
    }
}

class MyIdGenerator implements IdentifierGenerator {

    @Override
    public Number nextId(Object entity) {
        return null;
    }

    @Override
    public String nextUUID(Object entity) {
        return UID.next();
    }
}