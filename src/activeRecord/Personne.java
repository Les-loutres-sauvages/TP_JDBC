package activeRecord;

import java.sql.*;
import java.util.ArrayList;

public class Personne {
    private String prenom;
    private String nom;
    private int id;

    public Personne(String n, String p) {
        this.nom = n;
        this.prenom = p;
        this.id = -1;
    }

    public Personne(String n, String p, int id) {
        this.nom = n;
        this.prenom = p;
        this.id = id;
    }

    public static ArrayList<Personne> FindAll() throws SQLException {
        String request = "SELECT * FROM Personne;";

        ArrayList<Personne> res = new ArrayList<>();

        Connection co = DBConnection.getConnection();
        PreparedStatement prep = co.prepareStatement(request);
        prep.execute();

        ResultSet rs = prep.getResultSet();
        while (rs.next()) {
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            int id = rs.getInt("id");

            Personne p = new Personne(nom, prenom, id);
            res.add(p);
        }

        return res;
    }

    public static Personne FindById(int id) throws SQLException {
        String request = "SELECT * FROM Personne WHERE id = ?;";

        Connection co = DBConnection.getConnection();
        PreparedStatement prep = co.prepareStatement(request);
        prep.setInt(1, id);
        prep.execute();

        ResultSet rs = prep.getResultSet();
        if (rs.next()) {
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");

            return new Personne(nom, prenom, id);
        }
        return null;
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

    public static Personne FindByNom(String nom) throws SQLException {
        String request = "SELECT * FROM Personne WHERE nom = ?;";

        Connection co = DBConnection.getConnection();
        PreparedStatement prep = co.prepareStatement(request);
        prep.setString(1, nom);
        prep.execute();

        ResultSet rs = prep.getResultSet();
        if (rs.next()) {
            String prenom = rs.getString("prenom");
            int id = rs.getInt("id");

            return new Personne(nom, prenom, id);
        }
        return null;
    }


}
