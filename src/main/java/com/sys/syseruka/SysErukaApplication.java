package com.sys.syseruka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


/**
 * @author zq
 */
@EnableEurekaServer
@SpringBootApplication
public class SysErukaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SysErukaApplication.class, args);
    }

}
