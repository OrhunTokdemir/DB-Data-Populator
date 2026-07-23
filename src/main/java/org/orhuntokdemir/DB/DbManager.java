package org.orhuntokdemir.DB;

import java.sql.Connection;
import java.sql.SQLException;

public interface DbManager extends AutoCloseable {
    Connection connect() throws SQLException;
    void createTable(String createTableSql) throws SQLException;
    int insert(String insertSql, Object... params) throws SQLException;
    @Override
    void close() throws SQLException;
}
