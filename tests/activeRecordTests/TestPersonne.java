package activeRecordTests;

import activeRecord.DBConnection;
import activeRecord.Personne;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
public class TestPersonne {

    @BeforeEach
    public void setUp() throws SQLException {
        try {
            Personne.dropTable();
            Personne.createTable();
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
    public void testNewPersonne_in_BDD() throws Exception {
        Personne p = new Personne("Michel", "Dupont");
        p.save();

        ArrayList<Personne> liste = Personne.findAll();
        assertEquals(1, liste.size());
    }

    @Test
    public void testNewPersonne_Personne_value() throws Exception {
        Personne p = new Personne("Michel", "Dupont");
        p.save();

        int id = p.getId();
        assertEquals(1, id);
    }

    @Test
    public void testDeletePersonne() throws Exception {
        Personne p1 = new Personne("Michel", "Dupont");
        Personne p2 = new Personne("Michel2", "Dupont2");
        p1.save();
        p2.save();

        p1.delete();
        ArrayList<Personne> liste = Personne.findAll();
        assertEquals(1, liste.size());
    }

    @Test
    public void testGetPrenom() throws Exception {
        Personne p1 = new Personne("Michel", "Dupont");
        p1.save();

        assertEquals("Dupont", p1.getPrenom());
    }

    @Test
    public void testGetNom() throws Exception {
        Personne p1 = new Personne("Michel", "Dupont");
        p1.save();

        assertEquals("Michel", p1.getNom());
    }

    @Test
    public void testGetId() throws Exception {
        Personne p1 = new Personne("Michel", "Dupont");
        p1.save();

        assertEquals(1, p1.getId());
    }

    @Test
    public void testSetNom() throws Exception {
        Personne p1 = new Personne("Michel", "Dupont");
        p1.save();
        p1.setNom("Michel2");

        assertEquals("Michel2", p1.getNom());
    }

    @Test
    public void testSetPrenom() throws Exception {
        Personne p1 = new Personne("Michel", "Dupont");
        p1.save();
        p1.setPrenom("Dupont2");

        assertEquals("Dupont2", p1.getPrenom());
    }

    @Test
    public void testFindById() throws Exception {
        Personne p1 = new Personne("Michel", "Dupont");
        p1.save();
        Personne p2 = new Personne("Michel2", "Dupont2");
        p2.save();

        Personne p3 = Personne.findById(2);
        assertEquals("Michel2", p3.getNom());
    }

    @Test
    public void testFindByName() throws Exception {
        Personne p1 = new Personne("Michel", "Dupont");
        p1.save();
        Personne p2 = new Personne("Michel2", "Dupont2");
        p2.save();

        ArrayList<Personne> liste = Personne.findByName("Michel");
        assertEquals(1, liste.size());
    }

    @Test
    public void testSave() throws Exception {
        Personne p1 = new Personne("Michel", "Dupont");
        p1.setNom("Michel2");  // id = 1
        p1.save(); // id = 1
        p1.setNom("Michel");  // id = 2
        p1.save();  // id = 2

        ArrayList<Personne> liste = Personne.findAll();
        assertEquals("Michel2", liste.get(0).getNom());
    }

    @Test
    public void testSave2() throws Exception {
        Personne p1 = new Personne("Michel", "Dupont");
        p1.setNom("Michel2");  // id = 1
        p1.save(); // id = 1
        p1.setNom("Michel");  // id = 2
        p1.save();  // id = 2

        ArrayList<Personne> liste = Personne.findAll();
        assertEquals("Michel2", liste.get(0).getNom());
    }






}

