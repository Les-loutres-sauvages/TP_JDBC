package activeRecordTests;

import activeRecord.DBConnection;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
public class TestConnection {

    @Test
    public void test_connection() throws Exception {
        Connection co1 = DBConnection.getConnection();
        Connection co2 = DBConnection.getConnection();

        assertSame(co1, co2, "Les deux objets devraient être la même instance");
    }

    @Test
    public void test_connection_type() throws Exception {
        Connection co1 = DBConnection.getConnection();

        assertInstanceOf(java.sql.Connection.class, co1, "L'objet devrait être une instance de java.sql.Connection");
    }
}

