package org.orhuntokdemir.DB;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    @SuppressWarnings("resource")
    public void createTable(String createTableSql) throws SQLException {
        try (Statement statement = connect().createStatement()) {
            statement.execute(createTableSql);
        }
    }

    @SuppressWarnings("resource")
    public int insert(String insertSql, Object... params) throws SQLException {
        try (PreparedStatement statement = connect().prepareStatement(insertSql)) {
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            return statement.executeUpdate();
        }
    }

    @SuppressWarnings("resource")
    @Override
    public boolean checkIfTableExists(String tableName) throws SQLException {
        Connection conn = connect();
        DatabaseMetaData metaData = conn.getMetaData();
        String schema = conn.getSchema();
        if (schema == null || schema.isBlank()) {
            schema = username;
        }

        try (ResultSet resultSet = metaData.getTables(null, schema.toUpperCase(), tableName.toUpperCase(), new String[]{"TABLE", "VIEW"})) {
            return resultSet.next();
        }
    }

    @Override
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}