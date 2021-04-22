package com.achao.online_food_order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@MapperScan(basePackages = {"com.achao.service.mapper"})
@ComponentScan(basePackages = {"com.achao.*"})
public class OnlineFoodOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnlineFoodOrderApplication.class, args);
        System.out.println("start success");
    }

}
