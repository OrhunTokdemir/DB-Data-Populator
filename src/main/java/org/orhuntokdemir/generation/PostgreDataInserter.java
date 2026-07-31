package org.orhuntokdemir.generation;

import org.orhuntokdemir.DB.DbManager;
import java.sql.SQLException;

public class PostgreDataInserter implements DataInserter {
    private final DbManager postgresManager;
    private final RandomDataGenerator generator;
    private int recordCount;

    public PostgreDataInserter(DbManager postgresManager) {
        this.postgresManager = postgresManager;
        this.generator = new RandomDataGenerator();
        this.recordCount = 100; // default value
    }

    @Override
    public void setRecordCount(int count) {
        this.recordCount = count;
    }

    /**
     * Drops the comprehensive test table and its associated types
     */
    @Override
    public void dropComprehensiveTable() throws SQLException {
        String dropTableSql = "DROP TABLE IF EXISTS test.test_data CASCADE;";
        postgresManager.createTable(dropTableSql);

        // Check if the table exists in the public schema as well and drop it if it does
        String dropPublicTableSql = "DROP TABLE IF EXISTS test_data CASCADE;";
        postgresManager.createTable(dropPublicTableSql);

        String dropTypeSql = "DROP TYPE IF EXISTS test.gender CASCADE;";
        try {
            postgresManager.createTable(dropTypeSql);
        } catch (SQLException e) {
            // Ignore if type doesn't exist
        }

        // Also drop public gender type if it exists
        String dropPublicTypeSql = "DROP TYPE IF EXISTS gender CASCADE;";
        try {
            postgresManager.createTable(dropPublicTypeSql);
        } catch (SQLException e) {
            // Ignore if type doesn't exist
        }
    }

    @Override
    public void dropComprehensiveTable(String tableName) throws SQLException {
        String dropTableSql = "DROP TABLE IF EXISTS " + tableName + " CASCADE;";
        postgresManager.createTable(dropTableSql);

        // Also drop associated type if it exists
        String dropTypeSql = "DROP TYPE IF EXISTS test.gender CASCADE;";
        try {
            postgresManager.createTable(dropTypeSql);
        } catch (SQLException e) {
            // Ignore if type doesn't exist
        }
    }

    /**
     * Creates a comprehensive test table with all common PostgreSQL data types if it doesn't exist
     */
    @Override
    public void createComprehensiveTable() throws SQLException {
        /* Ensure test schema and gender type exist */
        String createSchemaSql = "CREATE SCHEMA IF NOT EXISTS test;";
        postgresManager.createTable(createSchemaSql);

        String createTypeSql = "DO $$ BEGIN " +
                "IF NOT EXISTS (SELECT 1 FROM pg_type t JOIN pg_namespace n ON n.oid = t.typnamespace WHERE t.typname = 'gender' AND n.nspname = 'test') THEN " +
                "CREATE TYPE test.gender AS ENUM ('male', 'female', 'other'); " +
                "END IF; END $$;";
        postgresManager.createTable(createTypeSql);

        String createTableSql = "CREATE TABLE IF NOT EXISTS test.test_data (" +
                "id SERIAL PRIMARY KEY, " +
                "col_name VARCHAR(100), " +
                "col_smallint SMALLINT, " +
                "tc_kimlik_no CHAR(11) NOT NULL CHECK (tc_kimlik_no ~ '^[0-9]{11}$'), " +
                "col_integer INTEGER, " +
                "col_bigint BIGINT, " +
                "col_decimal DECIMAL(10, 2), " +
                "col_numeric NUMERIC(10, 2), " +
                "col_real REAL, " +
                "col_double_precision DOUBLE PRECISION, " +
                "col_money MONEY, " +
                "col_varchar VARCHAR(255), " +
                "col_char CHAR(10), " +
                "col_text TEXT, " +
                "col_bpchar BPCHAR(10), " +
                "col_bytea BYTEA, " +
                "col_timestamp TIMESTAMP, " +
                "col_timestamptz TIMESTAMP WITH TIME ZONE, " +
                "col_date DATE, " +
                "col_time TIME, " +
                "col_timetz TIME WITH TIME ZONE, " +
                "col_interval INTERVAL, " +
                "col_boolean BOOLEAN, " +
                "col_enum test.gender, " +
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
                ");";

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
     * Creates the table (optionally dropping it first) and inserts comprehensive random data
     *
     * @param dropFirst if true, drops the table before creating/inserting
     * @throws SQLException if database operation fails
     */
    @Override
    public void createAndInsertComprehensiveData(boolean dropFirst) throws SQLException {
        if (dropFirst) {
            dropComprehensiveTable();
        }
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
        String insertSql = "INSERT INTO test.test_data (" +
                "col_name, col_smallint, tc_kimlik_no, col_integer, col_bigint, col_decimal, col_numeric, " +
                "col_real, col_double_precision, col_money, col_varchar, col_char, col_text, col_bpchar, " +
                "col_bytea, col_timestamp, col_timestamptz, col_date, col_time, col_timetz, col_interval, " +
                "col_boolean, col_enum, col_point, col_line, col_lseg, col_box, col_path, col_polygon, " +
                "col_circle, col_cidr, col_inet, col_macaddr, col_bit, col_varbit, col_uuid, col_xml, " +
                "col_json, col_jsonb, col_array" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?::money, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?::interval, ?, ?::test.gender, ?::point, ?::line, ?::lseg, ?::box, ?::path, ?::polygon, ?::circle, ?::cidr, ?::inet, ?::macaddr, ?::bit(10), ?::varbit(10), ?::uuid, ?::xml, ?::json, ?::jsonb, ?::integer[])";

        for (int i = 0; i < count; i++) {
            postgresManager.insert(
                    insertSql,
                    generator.generateName(),                  // col_name
                    generator.generateSmallint(),              // col_smallint
                    generator.generateFakeTCKN(),                  // tc_kimlik_no
                    generator.generateInt(),                   // col_integer
                    generator.generateBigint(),                // col_bigint
                    generator.generateNumeric(10, 2),          // col_decimal
                    generator.generateNumeric(10, 2),          // col_numeric
                    generator.generateReal(),                  // col_real
                    generator.generateDouble(),                // col_double_precision
                    generator.generateMoney(),                 // col_money
                    generator.generateVarchar(255),            // col_varchar
                    generator.generateChar(10),                // col_char
                    generator.generateText(),                  // col_text
                    generator.generateChar(10),                // col_bpchar
                    generator.generateBytea(),                 // col_bytea
                    java.sql.Timestamp.valueOf(generator.generateTimestamp()),           // col_timestamp
                    java.sql.Timestamp.valueOf(generator.generateTimestampTz()),         // col_timestamptz
                    generator.generateDate(),                  // col_date
                    generator.generateTime(),                  // col_time
                    generator.generateTimeTz(),                // col_timetz
                    generator.generateInterval(),              // col_interval
                    generator.generateBoolean(),               // col_boolean
                    generator.generateGender(),                // col_enum
                    generator.generatePoint(),                 // col_point
                    generator.generateLine(),                  // col_line
                    generator.generateLseg(),                  // col_lseg
                    generator.generateBox(),                   // col_box
                    generator.generatePath(),                  // col_path
                    generator.generatePolygon(),               // col_polygon
                    generator.generateCircle(),                // col_circle
                    generator.generateCidr(),                  // col_cidr
                    generator.generateInet(),                  // col_inet
                    generator.generateMacaddr(),               // col_macaddr
                    generator.generateBit(10),                 // col_bit
                    generator.generateVarbit(10),              // col_varbit
                    generator.generateUUID(),                  // col_uuid
                    generator.generateXml(),                   // col_xml
                    generator.generateJson(),                  // col_json
                    generator.generateJsonb(),                 // col_jsonb
                    generator.generateIntArray(5)              // col_array
            );
        }

        System.out.println("Successfully inserted " + count + " rows with comprehensive random data");
    }
}
