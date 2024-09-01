package ru.dariayo.starter.audit;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditAspect {

    private static final Logger logger = LoggerFactory.getLogger(AuditAspect.class);

    @AfterReturning(pointcut = "execution(* ru.dariayo..*(..))", returning = "result")
    public void auditMethod(Object result) {
        logger.info("Method executed successfully with result: " + result);
    }
}
