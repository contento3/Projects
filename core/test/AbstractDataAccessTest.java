package com.contento3.cms;

import org.hibernate.SessionFactory;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

 

@SuppressWarnings("deprecation")
public abstract class AbstractDataAccessTest extends
		AbstractTransactionalDataSourceSpringContextTests {

	private SessionFactory sessionFactory = null;

	/**
     * Reference the Spring configuration file for the test case.
     */
     protected String[] getConfigLocations()
     {
        // return new String[]{ "spring-service-context.xml" };
    	 return new String[]{ "applicationContext.xml" };
     }

    /**
     * Spring will automatically inject the SessionFactory instance on startup.
     * Only necessary for Hibernate-backed DAO testing
     */
     public void setSessionFactory(SessionFactory factory)
     {
         this.sessionFactory = factory;
     }
}
