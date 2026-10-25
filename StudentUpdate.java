
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class StudentUpdate {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/smart_placement";
        String user = "root";
        String password = System.getenv("DB_PASSWORD");

        String sql = "UPDATE students SET skills = ? WHERE student_id = ?";

        try {
            Connection con = DriverManager.getConnection(url, user, password);

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, "Java, SQL, HTML, CSS");
            ps.setInt(2, 1);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Student data updated successfully!");
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

