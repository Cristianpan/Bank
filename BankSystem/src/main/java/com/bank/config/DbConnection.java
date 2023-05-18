package com.bank.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {
    private static Connection connection;
    
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/banco";
                connection = DriverManager.getConnection(url, "root", "Gatosinbotas1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }
}
