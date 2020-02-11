package com.project.webflux.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
@Slf4j
public class UserServiceAop {

    @Before(value = "execution(* com.project.webflux.service.UserService.*(..))")
    public void beforeAdvice(JoinPoint joinPoint) {
        log.info(("Before method:" + joinPoint.getSignature()));
    }

    @After(value = "execution(* com.project.webflux.service.UserService.*(..))")
    public void afterAdvice(JoinPoint joinPoint) {
        log.info("After method:" + joinPoint.getSignature());
    }
}