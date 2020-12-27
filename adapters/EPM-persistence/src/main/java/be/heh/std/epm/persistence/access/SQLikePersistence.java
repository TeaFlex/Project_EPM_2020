package be.heh.std.epm.persistence.access;

import be.heh.std.epm.application.port.out.OutPersistence;

import java.sql.*;
import java.util.Properties;

public abstract class SQLikePersistence implements OutPersistence {

    private Connection connection;
    private String url, username, password;

    public SQLikePersistence(String type, String path, String username, String password) {
        connection = null;
        url = String.format("jdbc:%s:%s", type, path);
        this.username = username;
        this.password = password;
    }

    protected void connect() throws Exception {
        if (!isConnected()) {
            connection = DriverManager.getConnection(this.url, this.username, this.password);
            //System.out.printf("Connected to %s as %s.%n", url, username);
        }
    }

    public void disconnect() throws Exception {
        if (isConnected()) {
            connection.close();
            connection = null;
            //System.out.printf("Disconnected from %s.%n", url);
        }
    }

    protected Connection getConnection() throws Exception {
        connect();
        return connection;
    }

    public boolean isConnected() {
        return connection != null;
    }

}
