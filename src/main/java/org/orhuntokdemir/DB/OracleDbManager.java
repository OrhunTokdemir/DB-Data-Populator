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
        String currentSchema = conn.getSchema();

        // 1. Try with current schema first (most common case)
        if (currentSchema != null && !currentSchema.isBlank()) {
            try (ResultSet rs = metaData.getTables(null, currentSchema.toUpperCase(), tableName.toUpperCase(), new String[]{"TABLE", "VIEW", "SYNONYM"})) {
                if (rs.next()) return true;
            }
        }

        // 2. Try with username as schema
        String userSchema = username;
        if (userSchema != null && !userSchema.equalsIgnoreCase(currentSchema)) {
            try (ResultSet rs = metaData.getTables(null, userSchema.toUpperCase(), tableName.toUpperCase(), new String[]{"TABLE", "VIEW", "SYNONYM"})) {
                if (rs.next()) return true;
            }
        }

        // 3. Fallback: search all accessible schemas
        try (ResultSet rs = metaData.getTables(null, null, tableName.toUpperCase(), new String[]{"TABLE", "VIEW", "SYNONYM"})) {
            return rs.next();
        }
    }

    @Override
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}