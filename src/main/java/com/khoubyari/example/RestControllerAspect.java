package com.khoubyari.example;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RestControllerAspect {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Before("execution(public * com.khoubyari.example.api.rest.*Controller.*(..))")
    public void logBeforeRestCall(JoinPoint pjp) {
        log.info(":::::AOP Before REST call:::::" + pjp);
    }
}
