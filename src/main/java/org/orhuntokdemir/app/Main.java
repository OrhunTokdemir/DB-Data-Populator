package org.orhuntokdemir.app;
import io.github.cdimascio.dotenv.Dotenv;
import org.orhuntokdemir.DB.DbManager;
import org.orhuntokdemir.DB.PostgreManager;
import org.orhuntokdemir.generation.InsertionManager;
import org.orhuntokdemir.generation.PostgreDataInserter;


import java.sql.SQLException;


public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:8003/postgres";//Change this to your database URL if needed
        Dotenv dotenv = Dotenv.load();
        String user = dotenv.get("POSTGRES_USER") != null ? dotenv.get("POSTGRES_USER") : "postgres";
        String password = dotenv.get("POSTGRES_PASSWORD") != null ? dotenv.get("POSTGRES_PASSWORD") : "postgres";
        //how many records to enter
        int recordCount = 100;
        try (DbManager dbManager = new PostgreManager(url, user, password)) {
            PostgreDataInserter inserter = new PostgreDataInserter(dbManager);
            InsertionManager manager = new InsertionManager(inserter);

            manager.run(recordCount, false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
