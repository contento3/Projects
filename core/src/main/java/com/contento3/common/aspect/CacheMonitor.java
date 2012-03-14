package com.contento3.common.aspect;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;

@Aspect
public class CacheMonitor {
	
	private final static Logger LOG = Logger.getLogger(CacheMonitor.class);
	
	private final static NumberFormat NF = new DecimalFormat("0.0###");
	  
	private SessionFactory sessionFactory;
	   
	@Around("execution(* com.contento3.cms..**.dao.*.*(..))")
	public Object log(ProceedingJoinPoint pjp) throws Throwable {
//	      if (!LOG.isDebugEnabled()) {
//	         return pjp.proceed();
//	      }
	      
		Statistics statistics = sessionFactory.getStatistics();
		statistics.setStatisticsEnabled(true);
		      
		long hit0 = statistics.getQueryCacheHitCount();
		long miss0 = statistics.getSecondLevelCacheMissCount();
		      
		Object result = pjp.proceed();
		      
		long hit1 = statistics.getQueryCacheHitCount();
		long miss1 = statistics.getQueryCacheMissCount();
		      
		double ratio = (double) hit1 / (hit1 + miss1);

	    if (hit1 > hit0) {
	    	LOG.info(String.format("CACHE HIT; Ratio=%s; Signature=%s#%s()", NF.format(ratio), pjp.getTarget().getClass().getName(), pjp.getSignature().toShortString()));
	    }
	    else if (miss1 > miss0){
	    	LOG.info(String.format("CACHE MISS; Ratio=%s; Signature=%s#%s()", NF.format(ratio), pjp.getTarget().getClass().getName(), pjp.getSignature().toShortString()));
	    }
	    else {
	    	LOG.info("query cache not used");
	    }
	      
      return result;
   }
	
	public void setSessionFactory(final SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}
}

