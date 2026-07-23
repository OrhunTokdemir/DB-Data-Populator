package org.orhuntokdemir.app;
//import net.datafaker.Faker;
import org.orhuntokdemir.DB.DbManager;
import org.orhuntokdemir.DB.PostgreManager;
import org.orhuntokdemir.generation.InsertionManager;
import org.orhuntokdemir.generation.PostgreDataInserter;

import java.sql.SQLException;


public class Main {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:8004/postgres";
        String user = "postgres";
        String password = "postgres";

        try (DbManager dbManager = new PostgreManager(url, user, password)) {
            PostgreDataInserter inserter = new PostgreDataInserter(dbManager);
            InsertionManager manager = new InsertionManager(inserter);

            manager.run(1000, false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
