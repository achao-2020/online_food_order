package com.achao.utils;

import com.achao.annoation.NotReflect;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用转换类工具
 */
public class GeneralConv {
    public static List convert2List(List resources, Class targetClass) {
        List list = new ArrayList();
        resources.forEach(resource -> {
            Object target = null;
            try {
                target = targetClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                return;
            }
            BeanUtils.copyProperties(resource, target);
            list.add(target);
        });
        return list;
    }

    /**
     * 安装dto中的条件装换成为wrapper
     */
    public static <T> QueryWrapper<T> convQueryWrapper(T entity) throws IllegalAccessException {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        Field[] fields = entity.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            if (field.get(entity) != null && field.getAnnotation(NotReflect.class) == null) {
                if (field.get(entity) instanceof String && ((String) field.get(entity)).length() == 0) {
                    continue;
                }
                String column = changeToJavaFiled(field.getName());
                wrapper.eq(column, field.get(entity));
            }
            ;
        }
        return wrapper;
    }

    public static String changeToJavaFiled(String field) {
        String[] fields = field.toLowerCase().split("_");
        StringBuilder sbuilder = new StringBuilder(fields[0]);
        for (int i = 1; i < fields.length; i++) {
            char[] cs = fields[i].toCharArray();
            cs[0] -= 32;
            sbuilder.append(String.valueOf(cs));
        }
        return sbuilder.toString();
    }
}
