package activeRecord;

import java.sql.*;
import java.util.ArrayList;

public class Film {

    private String titre;
    private int id;
    private int id_real;


    public Film(String t, Personne r) throws RealisateurAbsentException {
        this.titre = t;
        this.id_real = r.getId();
        if(id_real == -1) throw new RealisateurAbsentException();

        this.id = -1;
    }

    private Film(String t, int id_real, int id) throws RealisateurAbsentException {
        if(id_real == -1) throw new RealisateurAbsentException();

        this.titre = t;
        this.id = id;
        this.id_real = id_real;
    }


    /**
     * @param id l'id du film a chercher
     * @return le film de la base de donnees ayant l'id donné
     * @throws SQLException
     */
    public static Film findById(int id) throws SQLException, RealisateurAbsentException {
        String request = "SELECT * FROM Film WHERE id = ?;";

        Connection co = DBConnection.getConnection();
        PreparedStatement prep = co.prepareStatement(request);
        prep.setInt(1, id);
        prep.execute();

        ResultSet rs = prep.getResultSet();
        if (rs.next()) {
            String titre = rs.getString("titre");
            int id_real = rs.getInt("id_rea"); //oui en DB c'est id_rea et pas id_real

            return new Film(titre, id_real, id);
        }
        return null;
    }


    /**
     * @param p Le réalisateur du film
     * @return une liste de tous les films de la base de donnees ayant le réalisateur p
     * @throws SQLException
     */
    public static ArrayList<Film> findByReal(Personne p) throws SQLException, RealisateurAbsentException {
        int p_id = p.getId();
        if(p_id == -1) throw new RealisateurAbsentException();

        String request = "SELECT * FROM Film WHERE id = ?;";

        ArrayList<Film> res = new ArrayList<>();

        Connection co = DBConnection.getConnection();
        PreparedStatement prep = co.prepareStatement(request);
        prep.setInt(1, p_id);
        prep.execute();

        ResultSet rs = prep.getResultSet();
        while (rs.next()) {
            String titre = rs.getString("titre");
            int id = rs.getInt("id");

            Film f = new Film(titre, p.getId(), id);
            res.add(f);
        }

        return res;
    }


    public Personne getRealisateur() throws SQLException, RealisateurAbsentException {
        if(id_real == -1) throw new RealisateurAbsentException();
        return Personne.findById(this.id_real);
    }


    public void save() throws SQLException {
        String request = "INSERT INTO Film (titre, id_rea) VALUES (?, ?);";

        Connection co = DBConnection.getConnection();
        PreparedStatement prep = co.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
        prep.setString(1, this.titre);
        prep.setInt(2, this.id_real);
        prep.execute();

        ResultSet rs = prep.getGeneratedKeys();
        if (rs.next()) {
            this.id = rs.getInt(1);
        }
    }


    public void delete() throws SQLException {
        String request = "DELETE FROM Film WHERE id = ?;";

        Connection co = DBConnection.getConnection();
        PreparedStatement prep = co.prepareStatement(request);
        prep.setInt(1, this.id);
        prep.execute();
    }


    public static void createTable() throws SQLException {
        String createString = "CREATE TABLE film ( "
                + "id INTEGER AUTO_INCREMENT, "
                + "titre VARCHAR(40) NOT NULL, "
                + "id_rea INTEGER DEFAULT NULL, "
                + "PRIMARY KEY (id), "
                + "KEY `id_rea` (`id_rea`), "
                + "CONSTRAINT `film_ibfk_1` FOREIGN KEY (`id_rea`) REFERENCES `personne` (`id`) "
                + ");";
        Statement stmt = DBConnection.getConnection().createStatement();
        stmt.executeUpdate(createString);
    }


    public static void dropTable() throws SQLException {
        try {
            String request = "DROP TABLE film;";

            Connection co = DBConnection.getConnection();
            PreparedStatement prep = co.prepareStatement(request);
            prep.execute();
        } catch (SQLException e) {
            System.out.println("Une erreur inattendue est survenue : " + e.getMessage());
        }
    }

    public int getId() {
        return this.id;
    }

    public String getTitre() {
        return this.titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
}
