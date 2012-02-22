package com.contento3.common.spring.dao;

import java.io.Serializable;
import java.util.List;
import org.hibernate.LockMode;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.contento3.common.dao.GenericDao;

public class GenericDaoSpringHibernateTemplate<T, PK extends Serializable>  
												extends HibernateDaoSupport
												implements GenericDao<T, PK>
{
      private Class<T> type;

      public GenericDaoSpringHibernateTemplate(Class<T> type) {
            this.type = type;
      }

      public void clear() {
    	  getHibernateTemplate().clear();
      }

      public void delete(T persistentObject) {
    	  getHibernateTemplate().delete(persistentObject);
      }

      public void deleteAll() {
    	  getHibernateTemplate().bulkUpdate(
                  "delete from "+type.getSimpleName());
      }

      @SuppressWarnings("unchecked")
      public List<T> findAll() {
            return getHibernateTemplate().find("from "+type.getName());
      }

      @SuppressWarnings("unchecked")
      public List<T> findByExample(T exampleInstance) {
            return getHibernateTemplate().findByExample(exampleInstance);
      }

      @SuppressWarnings("unchecked")
      public T findById(PK id) {
            return (T) getHibernateTemplate().get(type, id);
      }

      @SuppressWarnings("unchecked")
      public T findByIdUnique(PK id) {
            return (T) getHibernateTemplate().get(type, id);
      }

      @SuppressWarnings("unchecked")
      public T findById(PK id, boolean lock) {
            if(lock)
                  return (T) getHibernateTemplate().get(type, id, LockMode.UPGRADE);
            else return (T) getHibernateTemplate().get(type, id);
      }
 
      public void flush() {
    	  getHibernateTemplate().flush();
      }

      @SuppressWarnings("unchecked")
      public PK persist(T newInstance) {
            return (PK)getHibernateTemplate().save(newInstance);
      }

      public void update(T transientObject) {
    	  getHibernateTemplate().update(transientObject);
      }

      public Class<T> getType() {
            return type;
      }

      public void setType(Class<T> type) {
            this.type = type;
      }
}

