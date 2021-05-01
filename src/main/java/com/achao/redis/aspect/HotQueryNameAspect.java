package com.achao.redis.aspect;

import com.achao.controller.DishesController;
import com.achao.controller.StoreController;
import com.achao.pojo.constant.Constant;
import com.achao.pojo.dto.QueryPageDTO;
import com.achao.pojo.po.SearchCriteriaPO;
import com.achao.redis.RedisService;
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
@Aspect
@Component
public class HotQueryNameAspect {

    @Autowired
    private RedisService redisService;

    @Pointcut("execution(public * com.achao.controller.*.queryPage(..))")
    public void tag() {
    }

    @Before("tag()")
    public void addHotName(JoinPoint joinPoint) {
        QueryPageDTO queryPageDTO = (QueryPageDTO) joinPoint.getArgs()[0];
        List<SearchCriteriaPO> conditions = queryPageDTO.getConditions();
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
