package org.orhuntokdemir.generation;

import java.sql.SQLException;

public interface DataInserter {

    void setRecordCount(int count);

    void dropComprehensiveTable() throws SQLException;

    void createComprehensiveTable() throws SQLException;

    void insertComprehensiveData() throws SQLException;

    void createAndInsertComprehensiveData(boolean dropFirst) throws SQLException;
}
