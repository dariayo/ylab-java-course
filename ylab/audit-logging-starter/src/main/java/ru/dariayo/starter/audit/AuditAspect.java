package ru.dariayo.starter.audit;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditAspect {

    private final AuditLogRepository auditLogRepository;

    public AuditAspect(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    /**
     * Определяет точку среза для всех методов CarContoller
     */
    @Pointcut("execution(* ru.dariayo.controllers.CarController.*(..))")
    public void carControllerMethods() {
    }

    /**
     * Аудит всех успешных операций, возвращающих результат
     * 
     * @param joinPoint
     * @param result
     */
    @AfterReturning(pointcut = "carControllerMethods()", returning = "result")
    public void logAfterAction(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String username = "";
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof String) {
                username = (String) arg;
                break;
            }
        }

        auditLogRepository.logAction(username, methodName,
                "User performed action: " + methodName + " with result: " + result);
    }
}