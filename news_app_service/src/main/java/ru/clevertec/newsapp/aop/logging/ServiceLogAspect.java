package ru.clevertec.newsapp.aop.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Aspect to centralize logging for Service
 */
@Slf4j
@Aspect
@Component
public class ServiceLogAspect extends BaseAspect {
    /**
     * Pointcut that matches all Spring beans in the specified packages.
     */
    @Pointcut("execution(* ru.clevertec.newsapp.services..*(..)) " +
            "&& !@annotation(ru.clevertec.newsapp.aop.logging.ExcludeLog))")
    public void isServiceLayer() {
    }

    /**
     * Advice that logs when a method is entered.
     * @param joinPoint
     */
    @Before("isServiceLayer()")
    public void logControllersBefore(JoinPoint joinPoint) {
        log.info(BEFORE_SERVICE_PATTERN,
                joinPoint.getSignature().toShortString(),
                getArgsWithName(joinPoint));
    }

    /**
     * Advice that logs when a method is exited.
     * @param joinPoint join point for advice
     * @param result the desired result after successful method
     */
    @AfterReturning(pointcut = "isServiceLayer()", returning = "result")
    public void logControllerAfter(JoinPoint joinPoint, Object result) {
        log.info(AFTER_SERVICE_PATTERN,
                joinPoint.getSignature().toShortString(),
                getStringInstanceOf(Optional.ofNullable(result).orElse("")),
                getArgsWithName(joinPoint));
    }

    /**
     * Advice that logs methods throwing exceptions.
     * @param joinPoint join point for advice
     * @param e The class of the exception which method throws
     */
    @AfterThrowing(pointcut = "isServiceLayer()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error(AFTER_THROWING_PATTERN,
                joinPoint.getSignature().getDeclaringType(),
                joinPoint.getSignature().getName(),
                e.getCause() != null? e.getCause() : "NULL",
                e.getMessage());
    }
}
