package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        UserService user = new UserServiceImpl();
        user.createUsersTable();
        user.saveUser("Kirill", "Yunitsin", (byte) 23);
        user.saveUser("Regina", "Yunitsina", (byte) 24);
        user.saveUser("Ramzes", "Yunitsin", (byte) 8);
        user.saveUser("Markiz", "Yunitsin", (byte) 7);
        user.saveUser("Foxford", "Yunitsin", (byte) 2);
        user.getAllUsers();
        user.removeUserById(1);
        user.cleanUsersTable();
        user.dropUsersTable();
    }
}
