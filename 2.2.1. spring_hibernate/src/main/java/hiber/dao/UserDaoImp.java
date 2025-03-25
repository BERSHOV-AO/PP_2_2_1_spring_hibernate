package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

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
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public User getUserByCarModelAndSeries(String model, int series) {
        Query query = sessionFactory.getCurrentSession().createQuery(
                "from User where car.model=:model and car.series=:series");
        query.setParameter("model", model);
        query.setParameter("series", series);
        User user = (User) query.getSingleResult();
        return user;
    }

    @Override
    public void setCar(Car car) {
        sessionFactory.getCurrentSession().save(car);
    }

    @Override
    public Car getCar(String name) {
        String hql = "select car from User where name=:paramName";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("paramName", name);
        return (Car) query.getSingleResult();
    }

    @Override
    public void clearUsers() {
        try {
            sessionFactory.getCurrentSession().createQuery("DELETE FROM User").executeUpdate();
          //  sessionFactory.getCurrentSession().createQuery("DELETE FROM Car").executeUpdate();
        } catch (HibernateException e) {
            throw new RuntimeException("Не удалось очистить пользователей и автомобили", e);
        }
    }

}
