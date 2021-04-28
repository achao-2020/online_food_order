package com.achao.redis;

import com.achao.pojo.constant.Constant;
import com.achao.pojo.vo.BaseVO;
import com.achao.pojo.vo.StoreVO;
import com.achao.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author achao
 */
@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 将结果添加到redis位置数据当中
     *
     * @param results
     * @return
     */
    public boolean addResultToGeo(List<BaseVO> results) {
        if (!CollectionUtils.isEmpty(results)) {
            if (results.get(0) instanceof StoreVO) {
                results.forEach(result -> {
                    StoreVO storeVO = (StoreVO) result;
                    redisUtils.hset(Constant.STORE_QUERY_RESULT, storeVO.getId(), storeVO);
                    redisUtils.geoSet(Constant.LOCATION_STORE, storeVO.getId(), storeVO.getLongitude(),
                            storeVO.getLatitude());
                });
            }
        }
        return true;
    }

    public List<StoreVO> queryNearbyStores(Point point, double radius) {
        List<StoreVO> rst = new ArrayList<>();
        List<GeoResult<RedisGeoCommands.GeoLocation<Object>>> geoResults = redisUtils.geoGetNear(point, null, radius);
        for (GeoResult<RedisGeoCommands.GeoLocation<Object>> result : geoResults) {
            String storeId = (String) result.getContent().getName();
            StoreVO storeVO = (StoreVO) redisUtils.hget(Constant.STORE_QUERY_RESULT, storeId);
            if (storeVO != null) {
                rst.add(storeVO);
            }
        }
        return rst;
    }
}
