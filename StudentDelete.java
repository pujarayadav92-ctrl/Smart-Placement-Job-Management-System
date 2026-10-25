
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class StudentDelete {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/smart_placement";
        String user = "root";
        String password = System.getenv("DB_PASSWORD");

        String sql = "DELETE FROM students WHERE student_id = ?";

        try {
            Connection con = DriverManager.getConnection(url, user, password);

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, 1);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("Student not found!");
            }

            ps.close();
            con.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

