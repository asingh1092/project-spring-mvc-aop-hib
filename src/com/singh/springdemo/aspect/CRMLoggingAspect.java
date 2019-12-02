package com.singh.springdemo.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class CRMLoggingAspect {
	
	//Setup Logger
	private Logger logger = Logger.getLogger(getClass().getName());
	
	//Setup pointcut declarations
	@Pointcut("execution(* com.singh.springdemo.controller.*.*(..))")
	private void forControllerPackage() {}
	
	@Pointcut("execution(* com.singh.springdemo.service.*.*(..))")
	private void forServicePackage() {}
	
	@Pointcut("execution(* com.singh.springdemo.dao.*.*(..))")
	private void forDAOPackage() {}
	
	@Pointcut("forControllerPackage() || forServicePackage() || forDAOPackage()")
	private void forAppFlow() {}
	
	// add @Before advice
	@Before("forAppFlow()")
	public void before(JoinPoint joinPoint) {
		// display method we are calling
		String method = joinPoint.getSignature().toShortString();
		logger.info("=======> @Before advice: Calling method: " + method);
		
		// display the arguments to the method
		
		// get arguments
		Object[] args = joinPoint.getArgs();
		
		// loop through and display args
		for (Object tempArg : args) {
			logger.info("=======> Displaying arguments: " + tempArg);
		}
		
	}
	// add @AfterReturning advice
	@AfterReturning(pointcut = "forAppFlow()",
					returning = "result")
	public void afterReturning(JoinPoint joinPoint, Object result) {
		 // display method we are returning from
		String method = joinPoint.getSignature().toShortString();
		logger.info("=======> @AfterReturning advice: Calling method: " + method);
		
		// display data returned
		logger.info("=======> Result: " + result);
		
	}
}
