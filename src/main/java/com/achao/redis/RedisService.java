package com.achao.redis;

import com.achao.pojo.vo.BaseVO;
import com.achao.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author achao
 */
@Service("redisService")
public class RedisService {

    @Autowired
    private RedisUtils redisUtils;

    public boolean addLoginCache(String key, BaseVO baseVO) {
        return redisUtils.set(key, baseVO);
    }
}
