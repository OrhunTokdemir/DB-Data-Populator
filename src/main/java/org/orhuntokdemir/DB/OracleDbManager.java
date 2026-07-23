package org.orhuntokdemir.DB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class OracleDbManager implements DbManager {
    private final String url;
    private final String username;
    private final String password;
    private Connection connection;

    public OracleDbManager(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Connection connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, username, password);
        }
        return connection;
    }

    public void createTable(String createTableSql) throws SQLException {
        try (Statement statement = connect().createStatement()) {
            statement.execute(createTableSql);
        }
    }

    public int insert(String insertSql, Object... params) throws SQLException {
        try (PreparedStatement statement = connect().prepareStatement(insertSql)) {
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            return statement.executeUpdate();
        }
    }

    @Override
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}