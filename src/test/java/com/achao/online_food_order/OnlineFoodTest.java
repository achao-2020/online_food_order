package com.achao.online_food_order;

import java.util.HashMap;
import java.util.Map;

public class OnlineFoodTest {
    private static final Map<String, String> FINAL_MAP = new HashMap(){{
        put("achao", "12");
        put("zzd", "18");
    }};

    public static void main(String[] args) {
        System.out.println(FINAL_MAP);
    }
}
