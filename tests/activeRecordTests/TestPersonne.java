package activeRecordTests;

import activeRecord.DBConnection;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
public class TestPersonne {

    @Test
    public void test_connection() throws Exception {
        Connection co1 = DBConnection.getConnection();
        Connection co2 = DBConnection.getConnection();

        assertSame(co1, co2, "Les deux objets devraient être la même instance");
    }

}

