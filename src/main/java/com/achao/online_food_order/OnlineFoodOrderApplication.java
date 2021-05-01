package com.achao.online_food_order;

import com.achao.sdk.pojo.constant.AchaoTest;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @author achao
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.achao.service.mapper"})
@ComponentScan(basePackages = {"com.achao.*"})
@EnableCaching
public class OnlineFoodOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnlineFoodOrderApplication.class, args);
        System.out.println("start success");
    }

}
