package org.orhuntokdemir.generation;

import java.sql.SQLException;

public interface DataInserter {
    //set how many records to insert
    void setRecordCount(int count);

    //drop specified table if exists
    void dropComprehensiveTable() throws SQLException;

    //drop specified table if exists
    void dropComprehensiveTable(String tableName) throws SQLException;

    //create comprehensive table with the respective columns and data types
    void createComprehensiveTable() throws SQLException;

    //insert randomized data into the comprehensive table
    void insertComprehensiveData() throws SQLException;

    //act as a wrapper to drop, create and insert data into the comprehensive table
    void createAndInsertComprehensiveData(boolean dropFirst) throws SQLException;
}
