
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class StudentInsert {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/smart_placement";
        String user = "root";
        String password = System.getenv("DB_PASSWORD");

        String sql = "INSERT INTO students (name, email, course, skills) VALUES (?, ?, ?, ?)";

        try {
            Connection con = DriverManager.getConnection(url, user, password);

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, "Pujari");
            ps.setString(2, "pujari@example.com");
            ps.setString(3, "MCA");
            ps.setString(4, "Java, SQL, HTML");

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Student data inserted successfully!");
            }

            ps.close();
            con.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}




