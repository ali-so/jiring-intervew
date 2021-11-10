package ir.jiring.accounting.dao.impl;

import ir.jiring.accounting.dao.GeneralRepository;
import ir.jiring.accounting.dao.interfaces.IUserRepository;
import ir.jiring.accounting.dto.UserDto;
import ir.jiring.accounting.model.User;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository extends GeneralRepository<User> implements IUserRepository {

    @Override
    protected Class<User> getDomainClass() {
        return User.class;
    }

    @Override
    public User loadUserByUsername(String username) {
        String hql = " from " + domainClass.getName() + " e " +
                " where e.username = :username ";
        Query<User> query = getCurrentSession().createQuery(hql, domainClass);
        query.setParameter("username", username);
        return (User)query.uniqueResult();
    }

    @Override
    public List<UserDto> getAllUsers() {
        String hql = " select e.id, e.firstName, e.lastName, e.username " +
                "from " + domainClass.getName() + " e ";
        Query<UserDto> query = getCurrentSession().createQuery(hql);
        query.setCacheable(true);
        return query.getResultList();
    }
}
