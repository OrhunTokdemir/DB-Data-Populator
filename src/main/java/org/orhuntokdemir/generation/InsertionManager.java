package org.orhuntokdemir.generation;

import java.sql.SQLException;

public class InsertionManager {
    private final DataInserter inserter;

    public InsertionManager(DataInserter inserter) {
        this.inserter = inserter;
    }

    /**
     * Orchestrates the insertion process.
     * @param recordCount The number of records to insert.
     */
    public void run(int recordCount) {
        run(recordCount, false);
    }

    /**
     * Orchestrates the insertion process with option to drop existing table.
     * @param recordCount The number of records to insert.
     * @param dropFirst Whether to drop the table before starting.
     */
    public void run(int recordCount, boolean dropFirst) {
        try {
            System.out.println("Initializing insertion process...");
            inserter.setRecordCount(recordCount);

            if (dropFirst) {
                inserter.dropComprehensiveTable();
                System.out.println("Dropping existing table as requested...");
            }

            System.out.println("Starting managed insertion of " + recordCount + " records...");
            inserter.createAndInsertComprehensiveData(dropFirst);
            System.out.println("✓ Successfully managed and completed insertions.");

        } catch (SQLException e) {
            System.err.println("Process failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
