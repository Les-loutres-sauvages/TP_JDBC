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

    private Personne(String n, String p, int id) {
        this.nom = n;
        this.prenom = p;
        this.id = id;
    }

    /**
     * @return une liste de toutes les personnes de la base de donnees
     * @throws SQLException
     */
    public static ArrayList<Personne> findAll() throws SQLException {
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

    /**
     * @param name le nom de la personne a chercher
     * @return une liste de toutes les personnes de la base de donnees ayant le nom name
     * @throws SQLException
     */
    public static ArrayList<Personne> findByName(String name) throws SQLException {
        String request = "SELECT * FROM Personne WHERE nom = ?;";

        ArrayList<Personne> res = new ArrayList<>();

        Connection co = DBConnection.getConnection();
        PreparedStatement prep = co.prepareStatement(request);
        prep.setString(1, name);
        prep.execute();

        ResultSet rs = prep.getResultSet();
        while (rs.next()) {
            String prenom = rs.getString("prenom");
            int id = rs.getInt("id");

            Personne p = new Personne(name, prenom, id);
            res.add(p);
        }

        return res;
    }

    /**
     * @param id l'id de la personne a chercher
     * @return la personne correspondant a l'id
     * @throws SQLException
     */
    public static Personne findById(int id) throws SQLException {
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

    public static void createTable() throws SQLException {
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

    public void delete() {
        try {
            String request = "DELETE FROM Personne WHERE id = ?;";

            Connection co = DBConnection.getConnection();
            PreparedStatement prep = co.prepareStatement(request);
            prep.setInt(1, this.id);
            prep.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * /!\ DANGER ZONE /!\
     * Drop the table Personne
     */
    public static void dropTable() {
        try {
            String request = "DROP TABLE Personne;";

            Connection co = DBConnection.getConnection();
            PreparedStatement prep = co.prepareStatement(request);
            prep.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void setNom(String n) {
        this.nom = n;
    }

    public void setPrenom(String p) {
        this.prenom = p;
    }

    public int getId() {
        return this.id;
    }

    public String getNom() {
        return this.nom;
    }

    public String getPrenom() {
        return this.prenom;
    }


}
