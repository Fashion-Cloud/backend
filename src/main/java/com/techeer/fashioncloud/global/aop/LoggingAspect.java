package com.techeer.fashioncloud.global.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public final class LoggingAspect {

    @Before("execution(* com.techeer.fashioncloud..*Controller.*(..))")
    public void controllerLog(JoinPoint joinPoint) {
        log.debug("CONTROLLER : {}", joinPoint.getSignature());
    }

    @Before("execution(* com.techeer.fashioncloud..*Service.*(..))")
    public void serviceLog(JoinPoint joinPoint) {
        log.debug("SERVICE : {}", joinPoint.getSignature());
    }

}