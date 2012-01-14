package com.contento3.common.aspect;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class CacheAspect {
	private Cache cache;
	private static Logger LOGGER = Logger.getLogger(CacheAspect.class);

	@Around("execution(* com.contento3.cms.content.dao.impl.ContentTypeDAOHibernateImpl.find*(..))")
	public Object cacheObject(ProceedingJoinPoint pjp) throws Throwable {

		Object result;
		String cacheKey = getCacheKey(pjp);
		
		LOGGER.error(new StringBuilder("First looking in the CACHE to get an object "));
		Element element = (Element) cache.get(cacheKey);
		LOGGER.error(new StringBuilder("CacheAspect invoke:").append("n get:").append(cacheKey).append(" value:").append(element).toString());
	
		if (element == null) {
			LOGGER.error(new StringBuilder("Unable to find the object in cache.Lets get this object from db and store this into cache"));
			result = pjp.proceed();
			element = new Element(cacheKey, result);
		
			LOGGER.error(new StringBuilder("Storing it now"));
			cache.put(element);
		
			LOGGER.error(new StringBuilder("n put:").append(cacheKey).append(" value:").append(result).toString());
			LOGGER.error(new StringBuilder("Stored in cache successfully"));
		}
	return element.getValue();
	}

	public void flush() {
		cache.flush();
	}

	private String getCacheKey(ProceedingJoinPoint pjp) {
	
		String targetName = pjp.getTarget().getClass().getSimpleName();
		String methodName = pjp.getSignature().getName();
		Object[] arguments = pjp.getArgs();
	
		StringBuilder sb = new StringBuilder();
		sb.append(targetName).append(".").append(methodName);
		if ((arguments != null) && (arguments.length != 0)) {
			for (int i = 0; i < arguments.length; i++) {
				sb.append(".").append(arguments[i]);
		}
	}
	return sb.toString();
	}

	public void setCache(Cache cache) {
	this.cache = cache;
	}

}

