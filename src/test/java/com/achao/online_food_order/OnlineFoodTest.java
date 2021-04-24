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
        String a = "ab";
        String b = a + new String("c");
        String c = b;
        System.out.println("b=" + b);
        System.out.println("c=" + c);
        c = "aaa";
        System.out.println("b=" + b);
        System.out.println("c=" + c);
    }
}
