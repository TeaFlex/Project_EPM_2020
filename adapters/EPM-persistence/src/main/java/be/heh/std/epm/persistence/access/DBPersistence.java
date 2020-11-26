package be.heh.std.epm.persistence.access;

import be.heh.std.epm.application.port.out.OutPersistence;

import java.sql.*;
import java.util.Properties;

public abstract class DBPersistence implements OutPersistence {

    protected Connection connection;
    private String url, path, type, username, password;
    private Properties connectionProps;

    public DBPersistence(String type, String path, String username, String password) {
        connection = null;
        url = String.format("jdbc:%s:%s", type, path);
        connectionProps = new Properties();
        this.path = path;
        this.type = type;
        this.username = username;
        this.password = password;
    }

    @Override
    public void connect() throws SQLException {
        if(connection == null) {
            connectionProps.put("user", username);
            connectionProps.put("password", password);
            connection = DriverManager.getConnection(this.url, this.connectionProps);
            System.out.printf("Connected to %s (%s) as %s.%n", path, type, username);
        }
        else
            throw new SQLException("You are already connected.");
    }

    @Override
    public void disconnect() throws SQLException{
        if(connection != null){
            connection.close();
            connection = null;
            System.out.printf("Disconnected from %s (%s).%n", path, type);
        }
        else
            throw new SQLException("The database is already closed.");
    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }
}
