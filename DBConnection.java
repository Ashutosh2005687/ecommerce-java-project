package com.ecommerce.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Simple singleton-like helper for JDBC connection.
 * Update URL, USER and PASS to match your local MySQL setup before running.
 */
public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/ecommerce?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "password";
    private static Connection conn;

    private DBConnection() {}

    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(URL, USER, PASS);
        }
        return conn;
    }
}
