package com.achao.sdk.utils;


import com.achao.sdk.pojo.constant.HttpStatus;
import com.achao.sdk.pojo.vo.BaseVO;
import com.achao.sdk.pojo.vo.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 请求返回数据工具
 */
public class ResponseUtil<T extends BaseVO> {
    public static <T> Result<T> simpleSuccessInfo(T t) {
        Result<T> result = new Result<>();
        result.setCode(HttpStatus.SUCCESS);
        result.setMessage("成功！");
        result.setSuccess(true);
        result.setInfo(t);
        return result;
    }

    public static <T> Result<T> simpleFail(String code, String message) {
        Result<T> result = new Result<>();
        List<Map<String, String>> list = new ArrayList<>();
        result.setCode(code);
        result.setSuccess(false);
        result.setMessage(message);
        return result;

    }
}
