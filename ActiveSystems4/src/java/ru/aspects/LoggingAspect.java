/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.aspects;

import java.util.logging.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 *
 * @author Vitaly_Petrov
 */
@Aspect
@Component
public class LoggingAspect {
    
    private final Logger LOGGER = Logger.getLogger("InfoLogging");
    
    @Before("execution(* ru.web.DeviationController.create(..))")
    public void logBefore(JoinPoint joinPoint) {
        LOGGER.info("Add record with method " + joinPoint.getSignature().getName());
    }
    
}
