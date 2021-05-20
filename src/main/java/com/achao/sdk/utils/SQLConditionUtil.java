package com.achao.sdk.utils;

import com.achao.sdk.pojo.constant.ToConstant;
import com.achao.sdk.pojo.dto.QueryPageDTO;
import com.achao.sdk.pojo.dto.SearchCriteriaPO;
import com.achao.sdk.pojo.po.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author achao
 */
public class SQLConditionUtil {
    public static Map toTransferMap(Map conditions, BasePO to) throws IllegalAccessException {
        if (to == null) {
            return null;
        }
        to = judgeTo(to);
        Class<? extends BasePO> aClass = to.getClass();
        Field[] files = aClass.getDeclaredFields();
        for (Field field : files) {
            field.setAccessible(true);
            if (field.getType().getName() == "byte") {
                if ((byte) field.get(to) == 0) {
                    continue;
                }
            }
            if (field.get(to) == null) {
                continue;
            }
            conditions.put(field.getName(), field.get(to));
        }
        String key = null;
        String value = null;
        return conditions;
    }

    public static BasePO judgeTo(BasePO to) {
        String name = to.getClass().getSimpleName();
        switch (name) {
            case ToConstant.ADMINSTO:
                return (AdminPO) to;
            case ToConstant.COMMENTSTO:
                return (CommentPO) to;
            case ToConstant.CUSTOMERTO:
                return (CustomerPO) to;
            case ToConstant.DELIVERERTO:
                return (DelivererPO) to;
            case ToConstant.DISHESTO:
                return (DishesPO) to;
            case ToConstant.ORDERSTO:
                return (OrderPO) to;
            case ToConstant.STORETO:
                return (StorePO) to;
            default:
                return to;
        }
    }

    public static void transferToWrapper(QueryWrapper wrapper, QueryPageDTO queryPageDTO) throws SQLException {
        List<SearchCriteriaPO> toes = queryPageDTO.getConditions();
        if (toes == null || toes.size() == 0 && (queryPageDTO.getOrders() == null || queryPageDTO.getOrders().size() == 0)) {
            wrapper = null;
            return;
        }
        // 构造查询条件
        if (toes != null && toes.size() > 0) {
            for (SearchCriteriaPO to : toes) {
                if ("=".equals(to.getSearchPattern())) {
                    wrapper.eq(to.getKey(), to.getValue());
                }else if ("<>".equals(to.getSearchPattern())) {
                    wrapper.ne(to.getKey(), to.getValue());
                }else if ("!=".equals(to.getSearchPattern())) {
                    wrapper.ne(to.getKey(), to.getValue());
                } else if (">".equals(to.getSearchPattern())) {
                    wrapper.gt(to.getKey(), to.getValue());
                } else if (">=".equals(to.getSearchPattern())) {
                    wrapper.ge(to.getKey(), to.getValue());
                } else if ("<".equals(to.getSearchPattern())) {
                    wrapper.lt(to.getKey(), to.getValue());
                } else if ("<=".equals(to.getSearchPattern())) {
                    wrapper.le(to.getKey(), to.getValue());
                } else if ("like".equals(to.getSearchPattern())) {
                    wrapper.like(to.getKey(), to.getValue().toString());
                } else if ("not in".equals(to.getSearchPattern())) {
                    wrapper.notIn(to.getKey(), (List) to.getValue());
                } else if ("is null".equals(to.getSearchPattern())) {
                    wrapper.isNull(to.getKey());
                } else if ("is not null".equals(to.getSearchPattern())) {
                    wrapper.isNotNull(to.getKey());
                } else if ("in".equals(to.getSearchPattern())) {
                    wrapper.in(to.getKey(), (List) to.getValue());
                } else if ("not like".equals(to.getSearchPattern())) {
                    wrapper.notLike(to.getKey(), to.getValue().toString());
                } else {//The default is equal to
                    wrapper.eq(to.getKey(), to.getValue());
                }
            }
        }
        // 构造排序条件
        Map<String, Boolean> orders = queryPageDTO.getOrders();
        if (orders != null && orders.size() > 0) {
            Set<String> keySet = orders.keySet();
            for (String key : keySet) {
                if (orders.get(key)) {
                    wrapper.orderByAsc(key);
                } else {
                    wrapper.orderByDesc(key);
                }
            }
        }
//
//        // 构造分组条件
//        List<String> groups = queryPageDTO.getGroups();
//        if (groups != null && groups.size() > 0) {
//            groups.forEach(group -> {
//                wrapper.groupBy(group);
//            });
//        }
    }

    /**
     * 将传递的分页查询对象转换成mybatis-plus支持的分页查询参数
     *
     */
    public static void transferToIPage(IPage page, QueryPageDTO queryPageDTO) {
        page.setSize(queryPageDTO.getSize());
        page.setCurrent(queryPageDTO.getCurrent());
    }
}
