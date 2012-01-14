package com.olive.hibernate;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.olive.util.UniqueIdentifier;


/**
 * This class is used to generate UUID using the 
 * ptv.utilities.UniqueIdentifier class
 * @author Hammad.Afridi
 *
 */
	public class UUIDGenerator implements IdentifierGenerator {

	/**
	* This method will generate a random number and return it, hibernate can
	* use this id as it generator class id.
	*/
	@Override
	public Serializable generate(final SessionImplementor sessionImplemetor,
	final Object object) throws HibernateException {
		return UniqueIdentifier.getUniqueIdentifier().generateUniqueId(null);
	}

}
