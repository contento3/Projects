package com.olive.common.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * This class provides the logging aspect for all the classes in the application.
 * Applied to all classes.Class/Module specific logging can be implemented in 
 * a separate aspect.
 * 
 * @author HAMMAD AFRIDI
 */
@Aspect
public class ApplicationLogger {
	private static final Logger logger = Logger.getLogger(ApplicationLogger.class);

	
	@Before("within(com.olive.cms..*)")
	public void logMethodEntry(JoinPoint joinPoint)
	{
		logger.info("Now trying to enter into: ");
		logger.info(String.format("[Class: %s%s" , joinPoint.getTarget(),"]"));
		logger.info(String.format("[Method: %s%s", joinPoint.getSignature().getName(),"()]"));
		logger.info("Logging test");
	}

	@AfterReturning("within(com.olive.cms..*)")
	public void logMethodExit(JoinPoint joinPoint)
	{
		logger.info("Exiting from: ");
		logger.info(String.format("[Class: %s%s" , joinPoint.getTarget(),"]"));
		logger.info(String.format("[Method: %s%s", joinPoint.getSignature().getName(),"()]"));
	}

	@AfterThrowing("within(com.olive.cms..*)")
	public void logMethodThrow(JoinPoint joinPoint)
	{
		logger.error("Exception Occured: ");
		logger.error(String.format("[Class: %s%s" , joinPoint.getTarget(),"]"));
		logger.error(String.format("[Method: %s%s", joinPoint.getSignature().getName(),"()]"));
	}
}
