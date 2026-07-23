package org.orhuntokdemir.sample;

import org.orhuntokdemir.DB.PostgreManager;
import org.orhuntokdemir.generation.PostgreDataInserter;
import java.sql.SQLException;

/**
 * Sample class demonstrating how to use PostgreDataInserter to create and populate
 * a comprehensive PostgreSQL table with all supported data types
 */
public class SampleInsert {

    public static void main(String[] args) {
        SampleInsert sample = new SampleInsert();
        try {
            sample.insertComprehensiveData();
        } catch (SQLException e) {
            System.err.println("Error during sample insert: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Demonstrates creating a comprehensive test table and inserting random data
     */
    public void insertComprehensiveData() throws SQLException {
        try (PostgreManager postgresManager = new PostgreManager(
                "jdbc:postgresql://localhost:8004/postgres", "postgres", "postgres")) {

            // Create PostgreDataInserter instance
            PostgreDataInserter inserter = new PostgreDataInserter(postgresManager);

            // Optional: Set number of records to insert (default is 100)
            inserter.setRecordCount(50);

            // Option 1: Create table and insert data in one operation (dropping table if exists)
            System.out.println("Creating comprehensive test table and inserting 50 rows...");
            inserter.createAndInsertComprehensiveData(true);

            System.out.println("✓ Sample insert completed successfully!");
            System.out.println("  - Table: test_data");
            System.out.println("  - Rows inserted: 50");
            System.out.println("  - Columns: 40 (all PostgreSQL data types)");
        }
    }

    /**
     * Alternative method: Create table separately, then insert data
     */
    public void insertComprehensiveDataSeparately() throws SQLException {
        try (PostgreManager postgresManager = new PostgreManager(
                "jdbc:postgresql://localhost:8004/postgres", "postgres", "postgres")) {

            PostgreDataInserter inserter = new PostgreDataInserter(postgresManager);

            // Step 1: Create the table with all data types
            System.out.println("Step 1: Creating comprehensive table...");
            inserter.createComprehensiveTable();

            // Step 2: Insert 100 rows of random data
            System.out.println("Step 2: Inserting 100 rows of random data...");
            inserter.setRecordCount(100);
            inserter.insertComprehensiveData();

            System.out.println("✓ Sample insert (separate steps) completed successfully!");
        }
    }
}
