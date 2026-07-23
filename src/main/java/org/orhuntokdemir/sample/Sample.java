package org.orhuntokdemir.sample;
import org.orhuntokdemir.DB.PostgreManager;
import java.sql.SQLException;

public class Sample {
    public static void main(String[] args) {
        Sample sample = new Sample();
        try {
            sample.testTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void testTable() throws SQLException {
        try (PostgreManager postgreManager = new PostgreManager(
                "jdbc:postgresql://localhost:8004/postgres", "postgres", "postgres")) {
            postgreManager.createTable(
                    "CREATE TABLE IF NOT EXISTS users " +
                            "(id SERIAL PRIMARY KEY, " +
                            "name VARCHAR(255)," +
                            " email VARCHAR(255))");
            postgreManager.insert("INSERT INTO users (name, email) VALUES ('John Doe', 'john.doe@example.com')");
        }
    }
}
