package com.java.informationstatistic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * spring启动器
 *
 * @author luyu
 * @since 20200802
 * @version v1.0
 *
 * copyright 18994139782@163.com
 */
@SpringBootApplication
@EnableScheduling
public class InformationstatisticApplication {

    public static void main(String[] args) {
        SpringApplication.run(InformationstatisticApplication.class, args);
    }

}
