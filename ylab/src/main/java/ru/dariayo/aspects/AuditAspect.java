package ru.dariayo.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.dariayo.log.AuditLogRepository;

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
