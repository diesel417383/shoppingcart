package com.chemin.backend.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Around("@annotation(com.chemin.backend.annotation.LogExecution)")
    public Object logService(ProceedingJoinPoint joinPoint) throws Throwable {

        // 計算方法執行時長
        long start = System.currentTimeMillis();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        try {
            Object result = joinPoint.proceed();

            long cost = System.currentTimeMillis() - start;

            // 慢查詢紀錄
            if (cost > 1000) {
                log.warn("慢方法 {}.{}, cost={}ms", className, methodName, cost);
            }

            return result;

        } catch (Exception e) {
            long cost = System.currentTimeMillis() - start;
            log.error("例外 {}.{}, cost={}ms", className, methodName, cost, e);
            throw e;
        }
    }
}
