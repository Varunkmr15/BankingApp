package dao;

import model.User;
import util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    public static int createUser(String username, byte[] hash, byte[] salt, String fullName, String email, String phone, boolean isAdmin) throws SQLException {
        String sql = "INSERT INTO users (username, password_hash, salt, full_name, email, phone, is_admin) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection c = DbConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, username);
            ps.setBytes(2, hash);
            ps.setBytes(3, salt);
            ps.setString(4, fullName);
            ps.setString(5, email);
            ps.setString(6, phone);
            ps.setBoolean(7, isAdmin);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    public static User findByUsername(String username) throws SQLException {
        String sql = "SELECT id, username, full_name, email, phone, is_admin, is_frozen FROM users WHERE username = ?";
        try (Connection c = DbConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getBoolean("is_admin"),
                        rs.getBoolean("is_frozen")
                    );
                }
            }
        }
        return null;
    }

    public static byte[] getPasswordHashByUsername(String username) throws SQLException {
        String sql = "SELECT password_hash FROM users WHERE username = ?";
        try (Connection c = DbConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getBytes("password_hash");
            }
        }
        return null;
    }

    public static byte[] getSaltByUsername(String username) throws SQLException {
        String sql = "SELECT salt FROM users WHERE username = ?";
        try (Connection c = DbConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getBytes("salt");
            }
        }
        return null;
    }

    public static List<User> listAllUsers() throws SQLException {
        String sql = "SELECT id, username, full_name, email, phone, is_admin, is_frozen FROM users";
        List<User> res = new ArrayList<>();
        try (Connection c = DbConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                res.add(new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getBoolean("is_admin"),
                    rs.getBoolean("is_frozen")
                ));
            }
        }
        return res;
    }
}