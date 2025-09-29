// ----------------- service/AuthService.java -----------------
package service;

import dao.UserDao;
import model.User;
import util.PasswordUtils;

public class AuthService {
    public static User register(String username, char[] password, String fullName, String email, String phone, boolean isAdmin) throws Exception {
        byte[] salt = PasswordUtils.generateSalt();
        byte[] hash = PasswordUtils.hashPassword(password, salt);
        int userId = UserDao.createUser(username, hash, salt, fullName, email, phone, isAdmin);
        if (userId > 0) {
            return UserDao.findByUsername(username);
        }
        return null;
    }

    public static User login(String username, char[] password) throws Exception {
        byte[] hash = UserDao.getPasswordHashByUsername(username);
        byte[] salt = UserDao.getSaltByUsername(username);
        if (hash == null || salt == null) return null;
        boolean ok = PasswordUtils.verifyPassword(password, salt, hash);
        if (ok) return UserDao.findByUsername(username);
        return null;
    }
}

