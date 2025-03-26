package hiber.dao;

import hiber.model.Car;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class CarDaoImpl implements CarDao {

    private static final Logger logger = Logger.getLogger(UserDaoImp.class.getName());


    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(Car car) {
        try {
            sessionFactory.getCurrentSession().save(car);
            logger.log(Level.INFO, "Добавление автомобиля: {}", car);
        } catch (HibernateException e) {
            throw new RuntimeException("Не удалось добавить автомобиль", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Car> listCars() {
        try {
            TypedQuery<Car> query = sessionFactory.getCurrentSession().createQuery("from Car");
            logger.log(Level.INFO, "Получение всех автомобилей");
            return query.getResultList();
        } catch (HibernateException e) {
            throw new RuntimeException("Не удалось получить автомобили", e);
        }
    }

    @Override
    public void clearCars() {
        try {
            sessionFactory.getCurrentSession().createQuery("DELETE FROM Car").executeUpdate();
            logger.log(Level.INFO, "Очистка таблицы автомобилей завершена.");

        } catch (HibernateException e) {
            throw new RuntimeException("Не удалось очистить таблицу автомобилей", e);
        }
    }
}
