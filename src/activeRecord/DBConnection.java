package activeRecord;

import java.util.ArrayList;

public class DBConnection {

    // variables de connection
    String userName = "root";
    String password = "root";
    String serverName = "127.0.0.1";
    //String portNumber = "3306";
    String portNumber = "8889"; // Port par défaut sur MAMP
    String tableName = "personne";

    static String dbName = "testpersonne";

    public static void getConnection() {
        //TODO
    }

    /**
     * Change le nom de la base d'accès.
     * @param db_name Nouveau nom de la base à laquelle on souhaite accéder
     */
    public static void setNomDB(String db_name) {
        dbName = db_name;
    }

}