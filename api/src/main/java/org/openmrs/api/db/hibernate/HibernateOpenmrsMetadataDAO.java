package org.openmrs.api.db.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.api.db.OpenmrsMetadataDAO;

/**
 * Abstract class implementing basic data access methods for BaseOpenmrsMetadata persistents
 *
 * @since 1.10
 *
 * @param <T>
 */
public class HibernateOpenmrsMetadataDAO<T extends BaseOpenmrsMetadata> extends HibernateOpenmrsObjectDAO<T> implements OpenmrsMetadataDAO<T> {
	
	public HibernateOpenmrsMetadataDAO(Class<T> mappedClass) {
		super();
		this.mappedClass = mappedClass;
	}

	/**
	 * @see org.openmrs.api.db.OpenmrsMetadataDAO#getAll(boolean)
	 */
	public List<T> getAll(boolean includeRetired) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		
		if (!includeRetired) {
			crit.add(Restrictions.eq("retired", false));
		}
		
		return crit.list();
	}
	
	/**
	 * @see org.openmrs.api.db.OpenmrsMetadataDAO#getAll(boolean, java.lang.Integer, java.lang.Integer)
	 */
	public List<T> getAll(boolean includeRetired, Integer firstResult, Integer maxResults) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(mappedClass);
		
		if (!includeRetired) {
			crit.add(Restrictions.eq("retired", false));
		}
		crit.setFirstResult(firstResult);
		crit.setMaxResults(maxResults);
		
		return crit.list();

	}

	/**
	 * @see org.openmrs.api.db.OpenmrsMetadataDAO#getAllCount(boolean)
	 */
	public int getAllCount(boolean includeRetired) {

		String hql = "select count(*)" + " from " + mappedClass;

		if (!includeRetired) {
			hql += " where retired = false";
		}
		Query query = sessionFactory.getCurrentSession().createQuery(hql);

		Number count = (Number) query.uniqueResult();

		return count == null ? 0 : count.intValue();
	}
}
