package dao;

import model.Account;
import util.DbConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {
    public static int createAccount(int userId, String accountNumber) throws SQLException {
        String sql = "INSERT INTO accounts (user_id, account_number, balance) VALUES (?, ?, 0)";
        try (Connection c = DbConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            ps.setString(2, accountNumber);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    public static Account findByAccountNumber(String accNum) throws SQLException {
        String sql = "SELECT id, user_id, account_number, balance FROM accounts WHERE account_number = ?";
        try (Connection c = DbConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, accNum);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Account a = new Account();
                    a.setId(rs.getInt("id"));
                    a.setUserId(rs.getInt("user_id"));
                    a.setAccountNumber(rs.getString("account_number"));
                    a.setBalance(rs.getBigDecimal("balance"));
                    return a;
                }
            }
        }
        return null;
    }

    public static boolean updateBalance(int accountId, BigDecimal newBalance) throws SQLException {
        String sql = "UPDATE accounts SET balance = ? WHERE id = ?";
        try (Connection c = DbConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setBigDecimal(1, newBalance);
            ps.setInt(2, accountId);
            return ps.executeUpdate() == 1;
        }
    }

    public static List<Account> listAccountsByUser(int userId) throws SQLException {
        String sql = "SELECT id, user_id, account_number, balance FROM accounts WHERE user_id = ?";
        List<Account> res = new ArrayList<>();
        try (Connection c = DbConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Account a = new Account();
                    a.setId(rs.getInt("id"));
                    a.setUserId(rs.getInt("user_id"));
                    a.setAccountNumber(rs.getString("account_number"));
                    a.setBalance(rs.getBigDecimal("balance"));
                    res.add(a);
                }
            }
        }
        return res;
    }
}
