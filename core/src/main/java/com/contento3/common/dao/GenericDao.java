package com.contento3.common.dao;

import java.io.Serializable;
import java.util.Collection;

public interface GenericDao <T, PK extends Serializable> {

	  /**
	     * persists the newInstance object into database
	     * 
	     * @param newInstance new instance to persist
	     * @return Primary Key of the newly persisted instance
	     */
	    PK persist(T newInstance);

	   /**
	     * saves changes made to a persistent object.
	     * 
	     * @param transientObject the object to update
	     */
	   void update(T transientObject);

	   /**
	     * removes an object from persistent storage in the database
	     * 
	     * @param persistentObject the object to delete
	     */
	   void delete(T persistentObject);
	   
	   /**
	     * deletes all persistent objects
	     * 
	     */
	   void deleteAll();

	   /**
	     * finds a persistent object by its primary key
	     * 
	     * @param id Primary Key of the object to find
	     * @return Found object or <code>null</code> if not found
	     */
	    T findById(PK id);

	   
	   /**
	     * finds a persistent object by its primary key with lock
	     * 
	     * 
	     * @param id Primary Key of the object to find
	     * @param lock if lock is true, then the UPGRADE LockMode is used
	     * @return Found object or <code>null</code> if not found
	     */
	    T findById(PK id, boolean lock);

	   /**
	     * finds a persistent object by its unique primary key
	     * 
	     * 
	     * @param id Primary Key of the object to find
	     * @return Found object or <code>null</code> if not found
	     */
	    T findByIdUnique(PK id);
	   
	   /**
	     * finds all persistent objects
	     * 
	     * @return List of found objects
	     */
	    Collection<T> findAll();
	   
	   /**
	     * finds persistent objects using an example instance
	     * 
	     * @param exampleInstance the valued instance example
	     * @return List of found objects
	     */
	    Collection<T> findByExample(T exampleInstance);
	   
	   /**
	     * flushes the persistence session
	     * 
	      */
	   void flush();
	   
	   /**
	     * clears the persistence session
	     * 
	     */
	   void clear();
	}

