package matchingtrade.persistence.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import matchingtrade.persistence.entity.Entity;

import javax.transaction.Transactional;

/**
 * Generic Dao class to handle the most common operations performed by every Dao.
 * @author rafael.santos.bra@gmail.com
 */
public abstract class Dao<T extends Entity> {

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
	@Transactional
    public T get(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return (T) session.get(getEntityClass(), id);
    }

    @Transactional
    Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * When extending this class, you need to implement <i>getEntityClass()</i>
     * and return the class used as generic for Dao.java
     *
     * @return Entity used as generic for this class
     */
    protected abstract Class<T> getEntityClass();

    @Transactional
    public void save(T entity) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(entity);
    }
}
