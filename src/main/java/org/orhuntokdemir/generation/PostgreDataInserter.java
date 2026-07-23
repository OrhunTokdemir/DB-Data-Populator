package org.orhuntokdemir.generation;

import org.orhuntokdemir.DB.PostgreManager;
import java.sql.SQLException;
import java.sql.Timestamp;

public class PostgreDataInserter {
    private final PostgreManager postgresManager;
    private final RandomDataGenerator generator;

    public PostgreDataInserter(PostgreManager postgresManager) {
        this.postgresManager = postgresManager;
        this.generator = new RandomDataGenerator();
    }

    /**
     * Creates a comprehensive test table with all common PostgreSQL data types
     */
    public void createComprehensiveTable() throws SQLException {
        String createTableSql = "CREATE TABLE IF NOT EXIST test_data (" +
        "id SERIAL PRIMARY KEY, "+
        "col_smallint SMALLINT, " +
        "tc_kimlik_no CHAR(11) NOT NULL CHECK (tc_kimlik_no ~ '^[0-9]{11}$'), "+
        "col_integer INTEGER, " +
        "col_bigint BIGINT, " +
        "col_decimal DECIMAL(10, 2), " +
        "col_numeric NUMERIC(10, 2), " +
        "col_real REAL, " +
        "col_double_precision DOUBLE PRECISION, " +
        "col_serial SERIAL, " +
        "col_bigserial BIGSERIAL, " +
        "col_money MONEY, " +
        "col_varchar VARCHAR(255), " +
        "col_char CHAR(10), " +
        "col_text TEXT, " +
        "col_bpchar BPCHAR(10), " +
        "col_bytea BYTEA, " +
        "col_timestamp TIMESTAMP, " +
        "col_timestamptz TIMESTAMPTZ, " +
    }
    public void createComprehensiveTable1() throws SQLException {
        String createTableSql = "CREATE TABLE IF NOT EXISTS test_data (" +
                "id SERIAL PRIMARY KEY, " +
                "varchar_col VARCHAR(255), " +
                "char_col CHAR(20), " +
                "text_col TEXT, " +
                "int_col INT, " +
                "smallint_col SMALLINT, " +
                "bigint_col BIGINT, " +
                "real_col REAL, " +
                "double_col DOUBLE PRECISION, " +
                "numeric_col NUMERIC(10, 2), " +
                "boolean_col BOOLEAN, " +
                "date_col DATE, " +
                "time_col TIME, " +
                "timestamp_col TIMESTAMP, " +
                "name_col VARCHAR(255), " +
                "email_col VARCHAR(255), " +
                "phone_col VARCHAR(20), " +
                "json_col TEXT" +
                ")";
        postgresManager.createTable(createTableSql);
    }

    /**
     * Inserts random data into the test_data table a specified number of times
     *
     * @param count number of rows to insert
     * @throws SQLException if database operation fails
     */
    public void insertRandomData(int count) throws SQLException {
        String insertSql = "INSERT INTO test_data (" +
                "varchar_col, char_col, text_col, int_col, smallint_col, bigint_col, " +
                "real_col, double_col, numeric_col, boolean_col, date_col, time_col, " +
                "timestamp_col, name_col, email_col, phone_col, json_col" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        for (int i = 0; i < count; i++) {
            postgresManager.insert(
                    insertSql,
                    generator.generateVarchar(255),
                    generator.generateChar(20),
                    generator.generateText(),
                    generator.generateInt(),
                    generator.generateSmallint(),
                    generator.generateBigint(),
                    generator.generateReal(),
                    generator.generateDouble(),
                    generator.generateNumeric(10, 2),
                    generator.generateBoolean(),
                    generator.generateDate(),
                    generator.generateTime(),
                    Timestamp.valueOf(generator.generateTimestamp()),
                    generator.generateName(),
                    generator.generateEmail(),
                    generator.generatePhone(),
                    generator.generateJson()
            );
        }

        System.out.println("Successfully inserted " + count + " rows with random data");
    }

    /**
     * Creates the table and inserts random data in one operation
     *
     * @param count number of rows to insert
     * @throws SQLException if database operation fails
     */
    public void createAndInsert(int count) throws SQLException {
        createComprehensiveTable();
        insertRandomData(count);
    }
}

