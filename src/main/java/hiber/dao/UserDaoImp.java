package hiber.dao;


import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        String hql = "SELECT DISTINCT u FROM User u JOIN FETCH u.car";
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(hql, User.class);
        return query.getResultList();
    }

    @Override
    public User findUserByCar(String model, int serial) {
        String hql = "SELECT u FROM User u JOIN FETCH u.car " +
                "c WHERE c.model = :model AND c.serial = :serial";
        TypedQuery <User> query = sessionFactory.getCurrentSession().createQuery(hql, User.class);
        query.setParameter("model", model);
        query.setParameter("serial", serial);
        query.setMaxResults(1);
        return query.getSingleResult();
    }

}
