package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Query;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoImp.class.getName());

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        try {
            sessionFactory.getCurrentSession().save(user);
            logger.log(Level.INFO, "Добавление пользователя: {}", user);
        } catch (HibernateException e) {
            throw new RuntimeException("Не удалось добавить пользователя", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        try {
            TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
            logger.log(Level.INFO, "Получение всех пользователей с их автомобилями");
            return query.getResultList();
        } catch (HibernateException e) {
            throw new RuntimeException("Не удалось получить пользователей", e);
        }
    }

    @Override
    public User getUserByCarModelAndSeries(String model, int series) {

        try {
            logger.log(Level.INFO, "Получение пользователя по модели и серии автомобиля");
            Query query = sessionFactory.getCurrentSession().createQuery(
                    "from User where car.model=:model and car.series=:series");
            query.setParameter("model", model);
            query.setParameter("series", series);
            User user = (User) query.getSingleResult();
            return user;
        } catch (HibernateException e) {
            throw new RuntimeException("Не удалось получить пользователя по модели и серии автомобиля", e);
        }
    }

    @Override
    public void setCar(Car car) {
        try {
            sessionFactory.getCurrentSession().save(car);
            logger.log(Level.INFO, "Установка автомобиля");
        } catch (HibernateException e) {
            throw new RuntimeException("Не удалось установить автомобиль", e);
        }
    }

    @Override
    public Car getCar(String name) {
        try {
            String hql = "select car from User where name=:paramName";
            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setParameter("paramName", name);
            logger.log(Level.INFO, "Получение автомобля");
            return (Car) query.getSingleResult();
        } catch (HibernateException e) {
            throw new RuntimeException("Не удалось получить автомобиль", e);
        }
    }

    @Override
    public void clearUsers() {
        try {
            sessionFactory.getCurrentSession().createQuery("DELETE FROM User").executeUpdate();
            logger.log(Level.INFO, "Очистка таблицы пользователей завершена.");
        } catch (HibernateException e) {
            throw new RuntimeException("Не удалось очистить таблицу пользователей", e);
        }
    }
}
