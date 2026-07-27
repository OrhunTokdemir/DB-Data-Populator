package org.orhuntokdemir.sample;

import io.github.cdimascio.dotenv.Dotenv;
import org.orhuntokdemir.DB.DbManager;
import org.orhuntokdemir.DB.PostgreManager;
import org.orhuntokdemir.generation.InsertionManager;
import org.orhuntokdemir.generation.OracleDbInserter;
import java.sql.SQLException;

public class OracleDbSample
{
    static void main(){
        String url = "jdbc:oracle:thin:@//localhost:1521/FREEPDB1";//Change this to your database URL if needed
        Dotenv dotenv = Dotenv.load();
        String user = dotenv.get("ORACLE_USER") != null ? dotenv.get("ORACLE_USER") : "system";
        String password = dotenv.get("ORACLE_PASSWORD") != null ? dotenv.get("ORACLE_PASSWORD") : "oracle";
        //how many records to enter
        int recordCount = 1000;
        try (
                DbManager dbManager = new PostgreManager(url, user, password)) {
            OracleDbInserter inserter = new OracleDbInserter(dbManager);
            InsertionManager manager = new InsertionManager(inserter);

            manager.run(recordCount, false);
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }
}
