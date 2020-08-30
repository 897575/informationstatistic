package com.java.informationstatistic.tools;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.util.UUID;

/**
 * 工具类
 *
 * @author luyu
 * @since  2020729
 * @version v1.0
 *
 * copyright 18994139782@163.com
 */
public class ToolsUtil {

    /**
     * 数据源初始化类
     * @param url  地址
     * @param userName  用户名
     * @param className  驱动
     * @param password  密码
     * @return 数据源信息
     */
    public static DataSource initDataSource(String url, String userName, String className, String password,
                                            boolean testWhileidle,String validationQuery,int minEIT,int tberm,
                                            boolean tob){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(className);
        dataSource.setUsername(userName);
        dataSource.setUrl(url);
        dataSource.setPassword(password);
        dataSource.setTestWhileIdle(testWhileidle);
        dataSource.setTestOnBorrow(tob);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setMinEvictableIdleTimeMillis(minEIT);
        dataSource.setTimeBetweenEvictionRunsMillis(tberm);
        dataSource.setRemoveAbandoned(true);
        dataSource.setRemoveAbandonedTimeout(100);
        return dataSource;
    }
}
