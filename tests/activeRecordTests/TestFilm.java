package activeRecordTests;

import activeRecord.DBConnection;
import activeRecord.Film;
import activeRecord.Personne;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
public class TestFilm {

    @BeforeEach
    public void setUp() throws SQLException {
        try {
            Film.dropTable();

            Personne.dropTable();
            Personne.createTable();

            Film.createTable();
        } catch (Exception e) {
            System.out.println(".");
        }

    }

    @Test
    public void test_connection() throws Exception {
        Connection co1 = DBConnection.getConnection();
        Connection co2 = DBConnection.getConnection();

        assertSame(co1, co2, "Les deux objets devraient être la même instance");
    }

    @Test
    public void testNewFILM_in_BDD() throws Exception {
        Personne p = new Personne("Michel", "Dupont");
        p.save();

        Film f = new Film("Titanic", p);
        f.save();

        Film f2 = Film.findById(1);
        assertEquals("Titanic", f2.getTitre());
    }

    @Test
    public void testNewPersonne_Personne_value() throws Exception {
        Personne p = new Personne("Michel", "Dupont");
        p.save();

        Film f = new Film("Titanic", p);
        f.save();

        int id = f.getId();
        assertEquals(1, id);
    }

    @Test
    public void testDeleteFilm() throws Exception {
        Personne p1 = new Personne("Michel", "Dupont");
        p1.save();

        Film f1 = new Film("Titanic", p1);
        f1.save();

        f1.delete();

        Film f = Film.findById(1);
        assertNull(f);
    }

    @Test
    public void testGetTitre() throws Exception {
        Personne p = new Personne("Michel", "Dupont");
        p.save();

        Film f = new Film("Titanic", p);
        f.save();

        assertEquals("Titanic", f.getTitre());
    }

    @Test
    public void testGetId() throws Exception {
        Personne p = new Personne("Michel", "Dupont");
        p.save();

        Film f = new Film("Titanic", p);
        f.save();

        assertEquals(1, f.getId());
    }

    @Test
    public void testSetTitre() throws Exception {
        Personne p = new Personne("Michel", "Dupont");
        p.save();

        Film f = new Film("Titanic", p);
        f.save();
        f.setTitre("Tarzan");

        assertEquals("Tarzan", f.getTitre());
    }

    @Test
    public void testFindById() throws Exception {
        Personne p1 = new Personne("Michel", "Dupont");
        p1.save();
        Film f1 = new Film("Titanic", p1);
        f1.save();

        Film f = Film.findById(1);
        assertEquals("Titanic", f.getTitre());
    }

    @Test
    public void testFindByReal() throws Exception {
        Personne p1 = new Personne("Michel", "Dupont");
        p1.save();
        Film f1 = new Film("Titanic", p1);
        f1.save();

        ArrayList<Film> liste = Film.findByReal(p1);
        assertEquals("Titanic", liste.get(0).getTitre());
    }

    @Test
    public void testSave() throws Exception {
        Personne p = new Personne("Michel", "Dupont");
        p.save();

        Film f = new Film("Titanic", p);
        f.save();

        Film f2 = Film.findById(1);
        assertEquals(f.getId(), f2.getId());
    }
}

