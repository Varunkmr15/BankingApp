package service;

import dao.UserDao;
import dao.AccountDao;

public class AdminService {
    public static java.util.List<model.User> listUsers() throws java.sql.SQLException {
        return UserDao.listAllUsers();
    }
}
