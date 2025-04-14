package com.example.vegetabledragon;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect // AOP를 처리하는 클래스임을 알려줌.
@Component // 스프링 빈으로 등록.
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Before, After -> 메소드가 언제 실행되고 종료되는지를 알기 위함.
    // 메서드 실행 전 로그 남기기
    @Before("execution(* com.example.vegetabledragon.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Entering method: " + joinPoint.getSignature().toShortString());
    }

    // 메서드 실행 후 로그 남기기
    @After("execution(* com.example.vegetabledragon.service.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("Exiting method: " + joinPoint.getSignature().toShortString());
    }

    // 실행 결과 확인
    @Around("execution(* com.example.vegetabledragon.service.*.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed(); // 메소드 실행

        logger.info("Method execution finished: " + joinPoint.getSignature().toShortString());
        logger.info("Result: " + result);

        return result;
    }
}
