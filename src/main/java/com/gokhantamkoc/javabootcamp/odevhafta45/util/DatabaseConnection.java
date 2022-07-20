package com.gokhantamkoc.javabootcamp.odevhafta45.util;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component 
public class DatabaseConnection { 

    // VERITABANI AYARLARINI KENDI KURULUMUNUZA GORE GUNCELLEME YAPINIZ.
    private final String URL = "jdbc:mysql://localhost:3306/swapper";
    private final String USER = "root";
    private final String PASSWORD = "Hatice.0";

    private Connection connection = null;

    public DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to Database!");
        } catch (SQLException ex) {
        	
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
