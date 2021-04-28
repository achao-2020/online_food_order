package com.achao.online_food_order;

import com.achao.pojo.constant.Constant;
import com.achao.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class RedisServiceTest {
    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void redisConnect() {
        redisUtils.hset("hash1", "account", "achao");
    }

    @Test
    public void geoSetTest() {
        String key = "geoTest";
        String id = "111222333";
        Circle circle = new Circle(new Point(90.01000, 20.01000), new Distance(100, Metrics.KILOMETERS));
//        redisUtils.geoSet(key, id, 99.00, 22.00);
        GeoResults geoTest = redisTemplate.opsForGeo().radius(Constant.LOCATION_STORE, circle);
        return;
    }
}
