package com.nmpc.kindergarten.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

	private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	@Around("execution(* com.nmpc.kindergarten.service.*.*(..))")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

		String methodName = joinPoint.getSignature().getName();

		logger.info("Before method: {}", methodName);

		long start = System.currentTimeMillis();

		Object result = null;

		try {
			result = joinPoint.proceed();
		} catch (Exception e) {
			logger.error("Exception in method: {}", methodName);
			throw e;
		}

		long end = System.currentTimeMillis();

		logger.info("After method: {} and Execution Time: {} ms", methodName, (end - start));

		return result;

	}

}
