package ir.jiring.accounting.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public abstract class GeneralRepository<T> implements IGeneralRepository<T> {
    protected Class<T> domainClass = getDomainClass();

    protected abstract Class<T> getDomainClass();

    @Autowired
    protected EntityManager entityManager;

    protected Session getCurrentSession() {
        return entityManager.unwrap(Session.class);
    }

    @Override
    public void add(T entity) {
        Session session = getCurrentSession();
        /*Statistics statistics = session.getSessionFactory().getStatistics();
        CacheRegionStatistics secondLevelCacheStatistics =
                statistics.getDomainDataRegionStatistics( "query.cache.person" );
        long hitCount = secondLevelCacheStatistics.getHitCount();
        long missCount = secondLevelCacheStatistics.getMissCount();
        double hitRatio = (double) hitCount / ( hitCount + missCount );*/
        session.save(entity);
    }

    @Override
    public void flushSession() {
        getCurrentSession().flush();
    }

    @Override
    public void clearSession() {
        getCurrentSession().clear();
    }

    @Override
    public List<T> getAll() {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(domainClass.getName());
        return criteria.list();
    }

    @Override
    public void deleteById(Long entityId) {
        Session session = getCurrentSession();
        T obj = (T) session.load(domainClass, entityId);
        session.delete(obj);
    }

    @Override
    public T loadById(Long entityId) {
        Session session = getCurrentSession();

        Query query = session.createQuery("from " + domainClass.getName() + " e where e.id = :id");
        query.setParameter("id", entityId);
        return (T) query.uniqueResult();
    }
}
