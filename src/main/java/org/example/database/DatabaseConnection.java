package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class DatabaseConnection {

    public Map<Integer, Connection> connections;

    public Connection getConnection(int connectionId) {
        return connections.get(connectionId);
    }
    public void connect(String filepath, int connectionId){
        try {
            String url = "jdbc:sqlite:" + filepath;
            Connection connection = DriverManager.getConnection(url);
            connections.put(connectionId, connection);
            if(connections.get(connectionId) != null){System.out.println("Connected");}
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void disconnect(int connectionID) throws SQLException {
        if(connections.get(connectionID) != null && !connections.get(connectionID).isClosed()){
            connections.get(connectionID).close();
        }
    }

}
