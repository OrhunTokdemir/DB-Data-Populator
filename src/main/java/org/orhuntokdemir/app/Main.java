package org.orhuntokdemir.app;
//import net.datafaker.Faker;
import org.orhuntokdemir.DB.PostgreManager;

import java.sql.SQLException;


public class Main {

    static void main(String[] args) {
        Main main = new Main();
        try {
            main.testTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void testTable() throws SQLException {
        PostgreManager postgreManager = new PostgreManager(
                "jdbc:postgresql://localhost:8004/postgres", "postgres", "postgres");
        postgreManager.createTable(
                "CREATE TABLE IF NOT EXISTS users " +
                        "(id SERIAL PRIMARY KEY, " +
                        "name VARCHAR(255)," +
                        " email VARCHAR(255))");
        postgreManager.insert("INSERT INTO users (name, email) VALUES ('John Doe', 'john.doe@example.com')");

    }
}
