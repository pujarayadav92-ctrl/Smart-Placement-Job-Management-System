
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class StudentView {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/smart_placement";
        String user = "root";
        String password = System.getenv("DB_PASSWORD");

        String sql = "SELECT * FROM students";

        try {
            Connection con = DriverManager.getConnection(url, user, password);

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                System.out.println("Student ID: " + rs.getInt("student_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Course: " + rs.getString("course"));
                System.out.println("Skills: " + rs.getString("skills"));
                System.out.println("-------------------------");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

