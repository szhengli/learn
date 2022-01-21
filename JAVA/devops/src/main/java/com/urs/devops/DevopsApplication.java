package com.urs.devops;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@ImportResource("classpath:application-beans.xml")

public class DevopsApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(DevopsApplication.class);
        springApplication.setWebApplicationType(WebApplicationType.SERVLET);

        springApplication.run(args);
    }

}
