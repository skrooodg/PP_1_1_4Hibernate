package jm.task.core.jdbc.dao;

import jakarta.transaction.Transactional;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory = Util.getSessionFactory();
    public UserDaoHibernateImpl() {
    }
    @Override
    public void createUsersTable() {

        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS firstbase.Users (Id bigint PRIMARY KEY AUTO_INCREMENT, " +
                "Name VARCHAR(200), LastName VARCHAR(200)," +
                " Age tinyint)").executeUpdate();
            transaction.commit();
            System.out.println("Таблица создана");
        } catch (HibernateException e) {
            System.out.println("Ошибка при создании таблицы");
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("TRUNCATE firstBase.users").executeUpdate();
            System.out.println("Таблица удалена");
            transaction.commit();
        } catch (HibernateException e) {
            System.out.println("Ошибка при удалении");
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (HibernateException e) {
            System.out.println("При добавлении пользователя произошла ошибка");
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.remove(session.get(User.class, id));
            transaction.commit();
            System.out.println("Удаление прошло успешно");
        } catch (HibernateException e) {
            System.out.println("Ошибка при удалении");
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Transaction transaction = null;

        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            users = session.createQuery("select u from User u", User.class).getResultList();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println("ERRRRROOOOORRRRR!!!!!!!!!!");
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return users;
    }
    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE firstbase.users").executeUpdate();
            System.out.println("Таблица очищена");
            transaction.commit();
        } catch (HibernateException e) {
            System.out.println("Ошибка при очистке таблицы");
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
