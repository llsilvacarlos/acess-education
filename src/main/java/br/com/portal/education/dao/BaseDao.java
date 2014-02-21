package br.com.portal.education.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.portal.education.entity.BaseEntity;
import br.com.portal.education.exception.PortalEducationApplicationException;

@Stateless
@Named("baseDAO")
public class BaseDao<T extends BaseEntity, ID> implements Serializable {

	@PersistenceContext(unitName = "portal_education")
	private EntityManager entityManager;

	private Class entityClass;

	protected EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityClass(Class entityClass) {
		this.entityClass = entityClass;
	}

	public Class getEntityClass() {
		if (entityClass == null) {
			// only works if one extends BaseDao, we will take care of it with
			// CDI
			entityClass = (Class) ((ParameterizedType) getClass()
					.getGenericSuperclass()).getActualTypeArguments()[0];
		}
		return entityClass;
	}

	// utility database methods
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public T find(ID id) {
		return (T) this.entityManager.find(getEntityClass(), id);
	}

    public void delete(Object primaryKey) {
		Object ref = this.entityManager.getReference(getEntityClass(), primaryKey);
		this.entityManager.remove(ref);
	}

	public T update(T t) throws PortalEducationApplicationException {
	    try{
		return (T) this.entityManager.merge(t);
	    }catch(Exception e){
		throw new PortalEducationApplicationException(e);
	    }
	    
	}

	public void insert(T t) throws PortalEducationApplicationException {
	    try{
		this.entityManager.persist(t);
	    }catch(Exception e){
		throw new PortalEducationApplicationException(e);
	    }
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List findAll() {
	    return entityManager.createQuery(
			"Select entity FROM " + getEntityClass().getSimpleName()
					+ " entity").getResultList();
	}

}