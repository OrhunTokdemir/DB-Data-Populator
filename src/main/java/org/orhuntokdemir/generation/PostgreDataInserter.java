package org.orhuntokdemir.generation;

import org.orhuntokdemir.DB.PostgreManager;
import java.sql.SQLException;

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
        /* First drop type if exists to avoid conflicts */
        /*String dropTypeSql = "DROP TYPE IF EXISTS gender CASCADE;";
        try {
            postgresManager.createTable(dropTypeSql);
        } catch (SQLException e) {
            // Ignore if type doesn't exist
        }*/

        String createTableSql = "--CREATE TYPE IF NOT EXISTS gender AS ENUM ('male', 'female'); " +
                "CREATE TABLE IF NOT EXISTS test_data (" +
                "id SERIAL PRIMARY KEY, " +
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
                "col_smallint, tc_kimlik_no, col_integer, col_bigint, col_decimal, col_numeric, " +
                "col_real, col_double_precision, col_money, col_varchar, col_char, col_text, col_bpchar, " +
                "col_bytea, col_timestamp, col_timestamptz, col_date, col_time, col_timetz, col_interval, " +
                "col_boolean, col_enum, col_point, col_line, col_lseg, col_box, col_path, col_polygon, " +
                "col_circle, col_cidr, col_inet, col_macaddr, col_bit, col_varbit, col_uuid, col_xml, " +
                "col_json, col_jsonb, col_array" +
               ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?::money, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        for (int i = 0; i < count; i++) {
            postgresManager.insert(
                    insertSql,
                    generator.generateSmallint(),              // col_smallint
                    generator.generateTCKN(),                  // tc_kimlik_no
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

