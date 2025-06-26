package qa.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import qa.utils.DatabaseManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataBaseTest {

    @Test
    public void Test() {
        // Expected list of student names
        List<String> expectedStudents = Arrays.asList("Ali", "Sara", "Usman");
        List<String> actualStudents = new ArrayList<>();

        try {
            Connection conn = DatabaseManager.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM students");

            while (rs.next()) {
                String name = rs.getString("first_name");
                System.out.println("Student: " + name);
                actualStudents.add(name);
            }

            // Assertion: Check actual list contains all expected values
            Assert.assertEqualsNoOrder(
                    actualStudents.toArray(),
                    expectedStudents.toArray(),
                    "Student names do not match!"
            );

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception occurred: " + e.getMessage());
        } finally {
            DatabaseManager.closeConnection();
        }
    }
}
