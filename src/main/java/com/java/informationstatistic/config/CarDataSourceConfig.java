package com.java.informationstatistic.config;


import com.java.informationstatistic.tools.ToolsUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * car库连接配置
 *
 * @author luyu
 * @version v1.0
 * <p>
 * copyright 18994139782@163.com
 * @since 2020726
 */
@Configuration
@MapperScan(basePackages = CarDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "carSqlSessionFactory")
public class CarDataSourceConfig {
    static final String PACKAGE = "com.java.informationstatistic.dao.car";
    private static final String MAPPER_LOCATION = "classpath:mapper/car/*.xml";

    @Value("${car.spring.datasource.url}")
    private String url;

    @Value("${car.spring.datasource.username}")
    private String user;

    @Value("${car.spring.datasource.password}")
    private String password;

    @Value("${car.spring.datasource.driver.class.name}")
    private String driverClass;

    @Value("${car.spring.datasource.test-while-idle}")
    private boolean testWhileidle;

    @Value("${car.spring.datasource.validation-query}")
    private String validationQuery;

    @Value("${car.spring.datasource.min-evictable-idle-time-millis}")
    private int minEIT;
    @Value("${car.spring.datasource.time-between-eviction-runs-millis}")
    private int tberm;
    @Value("${car.spring.datasource.test-on-borrow}")
    private boolean tob;


    @Bean(name = "carDataSource")
    @Primary
    public DataSource carDataSource() {
        return ToolsUtil.initDataSource(url, user, driverClass, password,testWhileidle
                ,validationQuery,minEIT,tberm,tob);
    }

    @Bean(name = "carSqlSessionFactory")
    @Primary
    public SqlSessionFactory carSqlSessionFactory(@Qualifier("carDataSource") DataSource carDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(carDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(CarDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();

    }
}

