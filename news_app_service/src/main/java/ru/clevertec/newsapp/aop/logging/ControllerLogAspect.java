package ru.clevertec.newsapp.aop.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Aspect to centralize logging for Controllers
 */
@Slf4j
@Aspect
@Component
public class ControllerLogAspect extends BaseAspect {
    /**
     * Pointcut that matches all Spring beans in the specified packages.
     */
    @Pointcut("execution(* ru.clevertec.newsapp.controllers..*(..)) " +
            "&& !@annotation(ru.clevertec.newsapp.aop.logging.ExcludeLog))")
    public void isControllerLayer() {
    }

    /**
     * Advice that logs when a method is entered.
     * @param joinPoint join point for advice
     */
    @Before("isControllerLayer()")
    public void logControllersBefore(JoinPoint joinPoint) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.info(BEFORE_CONTROLLER_PATTERN,
                request.getMethod(),
                joinPoint.getSignature().toShortString(),
                request.getRequestURI(),
                getArgsWithName(joinPoint));
    }

    /**
     * Advice that logs when a method is exited.
     * @param joinPoint join point for advice
     * @param result the desired result after successful method
     */
    @AfterReturning(pointcut = "isControllerLayer()", returning = "result")
    public void logControllerAfter(JoinPoint joinPoint, Object result) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.info(AFTER_CONTROLLER_PATTERN,
                request.getMethod(),
                joinPoint.getSignature().toShortString(),
                request.getRequestURI(),
                getStringInstanceOf(Optional.ofNullable(result).orElse("")),
                getArgsWithName(joinPoint));
    }
}
