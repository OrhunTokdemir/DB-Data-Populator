package org.orhuntokdemir.generation;

import org.orhuntokdemir.DB.PostgreManager;
import java.sql.SQLException;
import java.sql.Timestamp;

public class PostgreDataInserter implements DataInserter {
    private final PostgreManager postgresManager;
    private final RandomDataGenerator generator;
    private int recordCount;

    public PostgreDataInserter(PostgreManager postgresManager) {
        this.postgresManager = postgresManager;
        this.generator = new RandomDataGenerator();
        this.recordCount = 100; // default value
    }

    public void setRecordCount(int count) {
        this.recordCount = count;
    }

    /**
     * Creates a comprehensive test table with all common PostgreSQL data types
     */
    @Override
    public void createComprehensiveTable() throws SQLException {
        String createTableSql ="CREATE TYPE gender AS ENUM ('male', 'female'); "+
                "CREATE TABLE IF NOT EXIST test_data (" +
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
                "col_timestamptz TIMESTAMPT WITH TIME ZONE, " +
                "col_date DATE, " +
                "col_time TIME, " +
                "col_timetz TIME WITH TIME ZONE, " +
                "col_interval INTERVAL, " +
                "col_boolean BOOLEAN, " +
                "col_enum gender, " +
                "col_point POINT, " +
                "col_line LINE, " +
                "col_lseg LSEG, " +
                "col_box BOX, " +
                "col_path PATH, " +
                "col_polygon POLYGON, " +
                "col_circle CIRCLE, " +
                "col_cidr CIDR, " +
                "col_inet INET, " +
                "col_macaddr MACADDR, " +
                "col_bit BIT(10), " +
                "col_varbit VARBIT(10), " +
                "col_uuid UUID, " +
                "col_xml XML, " +
                "col_json JSON, " +
                "col_jsonb JSONB, " +
                "col_array INTEGER[] " +
                ")";

        postgresManager.createTable(createTableSql);
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
     * Inserts comprehensive random data into the test_data table
     *
     * @throws SQLException if database operation fails
     */
    @Override
    public void insertComprehensiveData() throws SQLException {
        insertRandomData(recordCount);
    }

    /**
     * Creates the table and inserts comprehensive random data in one operation
     *
     * @throws SQLException if database operation fails
     */
    @Override
    public void createAndInsertComprehensiveData() throws SQLException {
        createComprehensiveTable();
        insertComprehensiveData();
    }

    /**
     * Inserts random data into the test_data table a specified number of times
     *
     * @param count number of rows to insert
     * @throws SQLException if database operation fails
     */
    private void insertRandomData(int count) throws SQLException {
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
}

