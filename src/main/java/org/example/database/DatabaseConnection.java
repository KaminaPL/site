package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public Connection connection;

    public Connection getConnection() {
        return connection;
    }
    public void connect(String filepath){
        try {
            String url = "jdbc:sqlite:" + filepath;
            connection = DriverManager.getConnection(url);
            if(connection != null){System.out.println("Connected");}
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void disconnect(int index) throws SQLException {
        if(connection != null && !connection.isClosed()){
            connection.close();
        }
    }

}
