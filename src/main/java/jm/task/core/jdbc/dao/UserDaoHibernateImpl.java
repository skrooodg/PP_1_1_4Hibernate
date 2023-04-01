package jm.task.core.jdbc.dao;

import jakarta.transaction.Transactional;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory = Util.getConnection();
    public UserDaoHibernateImpl() {

    }
    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS firstbase.Users (Id bigint PRIMARY KEY AUTO_INCREMENT, " +
                "Name VARCHAR(200), LastName VARCHAR(200)," +
                " Age tinyint)").executeUpdate();
            transaction.commit();
            System.out.println("Таблица создана");
        } catch (Exception e) {
            System.out.println("Ошибка при создании таблицы");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery("TRUNCATE firstBase.users").executeUpdate();
            System.out.println("Таблица удалена");
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Ошибка при удалении");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (Exception e) {
            System.out.println("При добавлении пользователя произошла ошибка");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.remove(session.get(User.class, id));
            transaction.commit();
            System.out.println("Удаление прошло успешно");
        } catch (Exception e) {
            System.out.println("Ошибка при удалении");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        return (List<User>) session.createQuery("select u from User u", User.class).getResultList();
    }
    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE firstbase.users").executeUpdate();
            System.out.println("Таблица очищена");
            transaction.commit();
        } catch (HibernateException e) {
            System.out.println("Ошибка при очистке таблицы");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
