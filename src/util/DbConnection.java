package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static DbConnection instance;
    private Connection conn;

    private DbConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/bankapp?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String pass = "pass@word1";
        conn = DriverManager.getConnection(url, user, pass);
    }

    public static synchronized Connection getConnection() throws SQLException {
        if (instance == null) {
            instance = new DbConnection();
        }
        return instance.conn;
    }

}
