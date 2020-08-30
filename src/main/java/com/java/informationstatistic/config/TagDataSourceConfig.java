package com.java.informationstatistic.config;

import com.java.informationstatistic.tools.ToolsUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * tag库数据连接
 *
 * @author luyu
 * @version v1.0
 * <p>
 * copyright 18994139782@163.com
 * @since 2020726
 */
@Configuration
@MapperScan(basePackages = TagDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "tagSqlSessionFactory")
public class TagDataSourceConfig {

    static final String PACKAGE = "com.java.informationstatistic.dao.tag";
    private static final String MAPPER_LOCATION = "classpath:mapper/tag/*.xml";

    @Value("${tag.spring.datasource.url}")
    private String url;

    @Value("${tag.spring.datasource.username}")
    private String user;

    @Value("${tag.spring.datasource.password}")
    private String password;

    @Value("${tag.spring.datasource.driver.class.name}")
    private String driverClass;

    @Value("${tag.spring.datasource.test-while-idle}")
    private boolean testWhileidle;

    @Value("${tag.spring.datasource.validation-query}")
    private String validationQuery;

    @Value("${tag.spring.datasource.min-evictable-idle-time-millis}")
    private int minEIT;
    @Value("${tag.spring.datasource.time-between-eviction-runs-millis}")
    private int tberm;
    @Value("${tag.spring.datasource.test-on-borrow}")
    private boolean tob;

    @Bean(name = "tagDataSource")
    public DataSource tagDataSource() {
        return ToolsUtil.initDataSource(url, user, driverClass, password,testWhileidle
                ,validationQuery,minEIT,tberm,tob);
    }

    @Bean(name = "tagSqlSessionFactory")
    public SqlSessionFactory tagSqlSessionFactory(@Qualifier("tagDataSource") DataSource tagDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(tagDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(TagDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}
