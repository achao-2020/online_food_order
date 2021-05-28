package com.achao.aspect;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author aChao
 * @create: 2021-05-24 23:31
 */
@Slf4j
@Aspect
@Component
public class LoggerAspect {

    /**
     * 这里针对ocom.achao.controller的所有类和方法
     * */
    @Pointcut("execution(public * com.achao.controller.*.*(*))")
    public void allMethod(){

    }
    @Before("allMethod()")
    public void before(){
        log.info("进入log切面");
    }
    @After("allMethod()")
    public void afterLog(JoinPoint point){
        log.info("--------------afterLog:--------------最终通知");
        log.info("--------------afterLog:=========目标方法为：" + point.getSignature().getDeclaringTypeName() + point.getSignature().getName());
        log.info("--------------afterLog:--------------参数为："+ JSONObject.toJSONString(point.getArgs()));
        log.info("--------------afterLog:--------------被织入的对象为："+point.getTarget());
    }

    @AfterReturning(value="allMethod()",returning="returnValue")
    public void afterRunningLog(JoinPoint point,Object returnValue ){
        log.info("--------------afterRunningLog--------------返回值后通知");
        log.info("--------------afterRunningLog----------------目标方法："+point.getSignature().getDeclaringTypeName()+"."+point.getSignature().getName());
        log.info("--------------afterRunningLog----------------参数为："+ JSONObject.toJSONString(point.getArgs()));
        log.info("--------------afterRunningLog----------------返回值："+returnValue);
    }

    @AfterThrowing(value="allMethod()",throwing="ex")
    public void AfterThrowingLog(Throwable ex){
        log.info("--------------AfterThrowingLog--------------进入异常通知");
        log.info("--------------AfterThrowingLog--------------异常信息："+ex.getMessage());
    }


    @Around("allMethod()")
    public Object doAround(ProceedingJoinPoint p) throws Throwable{
        log.info("--------------Around--------------进入环绕通知");
        long startTime = System.currentTimeMillis();
        Object obj = p.proceed();
        long endTime = System.currentTimeMillis();
        MethodSignature signature = (MethodSignature) p.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();

        log.info("目标方法："+methodName);
        log.info("运行耗时："+(endTime-startTime)+"ms");
        log.info("--------------Around--------------结束方法调用");
        return obj;

    }
}
