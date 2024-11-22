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
        String request = "SELECT * FROM Personne ORDER BY id ASC;";

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
        Connection co = DBConnection.getConnection();
        assert co != null;

        String query = "INSERT INTO personne (nom, prenom) VALUES (?, ?)";
        PreparedStatement ps = co.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, this.nom);
        ps.setString(2, this.prenom);
        if (ps.executeUpdate() == 1) {
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        } else {
            throw new SQLException("Insertion failed");
        }
    }

    public void delete() {
        try {
            String request = "DELETE FROM Personne WHERE id = ?;";

            Connection co = DBConnection.getConnection();
            PreparedStatement prep = co.prepareStatement(request);
            prep.setInt(1, this.id);
            prep.execute();
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {
                System.out.println("Impossible de supprimer cette personne : elle est référencée dans une autre table.");
            } else {
                System.out.println("Une erreur inattendue est survenue : " + e.getMessage());
            }
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
            if (e.getSQLState().equals("23000")) {
                System.out.println("Impossible de supprimer cette table : des personnes sont référencées dans une autre table.");
            } else {
                System.out.println("Une erreur inattendue est survenue : " + e.getMessage());
            }
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
