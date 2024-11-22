package activeRecordTests;

import activeRecord.DBConnection;
import activeRecord.Personne;
import org.junit.Before;
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
        Personne.createTable();
        DBConnection.setNomDB("testpersonne");
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
    public void testNewPersonne_Personne_value) throws Exception {
        Personne p = new Personne("Michel", "Dupont");
        p.save();

        int id = p.getId();
        assertEquals(1, id);
    }

    @Test
    public void testDeletePersonne) throws Exception {
        Personne p1 = new Personne("Michel", "Dupont");
        Personne p2 = new Personne("Michel2", "Dupont2");
        p1.save();
        p2.save();

        p1.delete();
        ArrayList<Personne> liste = Personne.findAll();
        assertEquals(1, liste.size());
    }

    @AfterEach
    public void tearDown() throws SQLException {
        Personne.deleteTable();
        DBConnection.getConnection().close();
    }

}

