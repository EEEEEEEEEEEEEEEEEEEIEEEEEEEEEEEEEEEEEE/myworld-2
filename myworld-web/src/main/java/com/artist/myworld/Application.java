package com.artist.myworld;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.autoconfigure.velocity.VelocityAutoConfiguration;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by asiamastor on 2016/12/21.
 */

@SpringBootApplication
@MapperScan("com.artist.myworld.dao")
@EnableAutoConfiguration(exclude = {ThymeleafAutoConfiguration.class, VelocityAutoConfiguration.class})
@ImportResource(locations = "classpath:applicationContext.xml")
@ComponentScan(basePackages={"com.dili.utils","com.artist.myworld"})
public class Application extends SpringBootServletInitializer {

        public static void main(String[] args) {
            SpringApplication.run(Application.class, args);
        }


}
