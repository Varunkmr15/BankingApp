package dao;

import model.TransactionRecord;
import util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class TransactionDao {

    public static int createTransaction(int accountId, String type, BigDecimal amount, String remarks, String relatedAccount) throws SQLException {
        String sql = "INSERT INTO transactions (account_id, type, amount, remarks, related_account) VALUES (?, ?, ?, ?, ?)";
        try (Connection c = DbConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, accountId);
            ps.setString(2, type);
            ps.setBigDecimal(3, amount);
            ps.setString(4, remarks);
            ps.setString(5, relatedAccount);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    public static List<TransactionRecord> getTransactionsForAccount(int accountId) throws SQLException {
        String sql = "SELECT id, account_id, type, amount, timestamp, remarks, related_account FROM transactions WHERE account_id = ? ORDER BY timestamp DESC";
        List<TransactionRecord> res = new ArrayList<>();
        try (Connection c = DbConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TransactionRecord t = new TransactionRecord();
                    t.setId(rs.getInt("id"));
                    t.setAccountId(rs.getInt("account_id"));
                    t.setType(rs.getString("type"));
                    t.setAmount(rs.getBigDecimal("amount"));
                    t.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
                    t.setRemarks(rs.getString("remarks"));
                    t.setRelatedAccount(rs.getString("related_account"));
                    res.add(t);
                }
            }
        }
        return res;
    }
}
