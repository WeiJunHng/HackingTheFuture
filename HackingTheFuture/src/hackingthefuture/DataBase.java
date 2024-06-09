/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package hackingthefuture;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.DriverManager;

/**
 *
 * @author jze20
 */
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;
import javax.swing.JOptionPane;
// A comment

public class DataBase {

    /**
     * @param args the command line arguments
     */
    private static final String PATH = "jdbc:mysql://localhost:3306/futurehacking?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    private Connection conn;

    private final HikariConfig config = new HikariConfig();
    
    private HikariDataSource ds;

    public DataBase() {
        config.setJdbcUrl(PATH);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.addDataSourceProperty("connectionTimeout", "1000");
        config.addDataSourceProperty("idleTimeout", "60000");
        config.addDataSourceProperty("maximumPoolSize", "20");
        
        ds = new HikariDataSource(config);
    }

    public void connectDB() {
        conn = null;
        try {
            conn = ds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exitDB() {
        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return conn;
    }

}
