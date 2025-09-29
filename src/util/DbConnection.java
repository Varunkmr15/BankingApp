package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static DbConnection instance;
    private Connection conn;

    // Singleton Pattern
    private DbConnection() throws SQLException {
        String url = "jdbc:postgresql://ep-autumn-mud-absyu43h-pooler.eu-west-2.aws.neon.tech:5432/neondb?sslmode=require";
        String user = "neondb_owner";
        String pass = "npg_7PcLtkOHG3lT";
        conn = DriverManager.getConnection(url, user, pass);
    }

    public static synchronized Connection getConnection() throws SQLException {
        if (instance == null) {
            instance = new DbConnection();
        }
        return instance.conn;
    }
}
