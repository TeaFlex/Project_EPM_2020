package be.heh.std.epm.persistence.access;

import be.heh.std.epm.application.port.out.OutPersistence;

import java.sql.*;
import java.util.Properties;

public abstract class DBPersistence implements OutPersistence {

    private Connection connection;
    private String url, username, password;

    public DBPersistence(String type, String path, String username, String password) {
        connection = null;
        url = String.format("jdbc:%s:%s", type, path);
        this.username = username;
        this.password = password;
    }

    protected void connect() throws Exception {
        if(connection == null) {
            connection = DriverManager.getConnection(this.url, this.username, this.password);
            System.out.printf("Connected to %s as %s.%n", url, username);
        }
    }

    protected void disconnect() throws Exception{
        if(connection != null){
            connection.close();
            connection = null;
            System.out.printf("Disconnected from %s.%n", url);
        }
    }

    protected Connection getConnection() {
        return connection;
    }

    public boolean isConnected() {
        return connection != null;
    }

}
