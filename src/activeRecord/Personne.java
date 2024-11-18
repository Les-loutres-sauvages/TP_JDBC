package activeRecord;

import java.sql.*;
import java.util.ArrayList;

public class Personne {

    private int id;
    private String nom, prenom;

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
}
