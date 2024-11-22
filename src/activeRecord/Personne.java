package activeRecord;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Personne {
    private String prenom;
    private String nom;

    public Personne(String n, String p){
        prenom = n;
        nom = n;
    }

    public void createTable() throws SQLException {
        String createString = "CREATE TABLE Personne ( "
                + "ID INTEGER  AUTO_INCREMENT, " + "NOM varchar(40) NOT NULL, "
                + "PRENOM varchar(40) NOT NULL, " + "PRIMARY KEY (ID))";
        Statement stmt = DBConnection.getConnection().createStatement();
        stmt.executeUpdate(createString);
    }

    public void save() throws SQLException {
        Statement stat = DBConnection.getConnection().createStatement();
        ResultSet res = stat.executeQuery("INSERT INTO personne('nom', 'prenom') VALUES (nom, prenom)");

        res.close();
        stat.close();
    }
}
