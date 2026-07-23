package org.orhuntokdemir.generation;

import org.orhuntokdemir.DB.PostgreManager;

import java.sql.SQLException;

public interface DataInserter {


    void createComprehensiveTable() throws SQLException;

    void insertComprehensiveData() throws SQLException;

    void createAndInsertComprehensiveData() throws SQLException;
}
