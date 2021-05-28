package com.achao.aspect;

import com.achao.controller.DishesController;
import com.achao.controller.StoreController;
import com.achao.redis.RedisService;
import com.achao.sdk.pojo.constant.Constant;
import com.achao.sdk.pojo.dto.QueryPageDTO;
import com.achao.sdk.pojo.dto.SearchCriteriaPO;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 热点餐品名称缓存处理
 * @author achao
 */
@Slf4j
@Aspect
@Component
public class HotQueryNameAspect {

    @Autowired
    private RedisService redisService;

    @Pointcut("execution(public * com.achao.controller.DishesController.queryPage(..)) || execution(public * com.achao.controller" +
            ".StoreController.queryPage(..))")
    public void tag() {
    }

    @Before("tag()")
    public void addHotName(JoinPoint joinPoint) {
        QueryPageDTO queryPageDTO = (QueryPageDTO) joinPoint.getArgs()[0];
        List<SearchCriteriaPO> conditions = queryPageDTO.getConditions();
        if (CollectionUtils.isEmpty(conditions)) {
            return;
        }
        String redisHostKey = getRedisHostKey(joinPoint.getThis());
        for (SearchCriteriaPO condition : conditions) {
            // 如果搜索的条件中包含名称，则将其所有前缀，作为key，缓存到redis
            if (condition.getKey().equals("name")) {
                String value = (String) condition.getValue();
                int len = value.length();
                if (len <= 1) {
                    return;
                }
                for (int i = 0; i < len; ) {
                    String key = redisHostKey + value.substring(0, ++i);
                    redisService.addHotDishName(key, value);
                }
            }
        }
    }

    private String getRedisHostKey(Object instance) {
        if (instance instanceof StoreController) {
            return Constant.HOST_STORE_NAME;
        }
        if (instance instanceof DishesController) {
            return Constant.HOST_DISH_NAME;
        }
        return null;
    }

}
