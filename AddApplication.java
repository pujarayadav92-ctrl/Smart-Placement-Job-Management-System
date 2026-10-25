
import java.sql.*;
import java.util.Scanner;

public class AddApplication {

    static final String URL = "jdbc:mysql://localhost:3306/smart_placement";
    static final String USER = "root";
    static final String PASSWORD = System.getenv("DB_PASSWORD");

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String sql = "INSERT INTO applications (student_id, job_id) VALUES (?, ?)";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.print("Enter Student ID: ");
            int studentId = Integer.parseInt(sc.nextLine());

            System.out.print("Enter Job ID: ");
            int jobId = Integer.parseInt(sc.nextLine());

            ps.setInt(1, studentId);
            ps.setInt(2, jobId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Application added successfully!");
            }

        } catch (SQLException | NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
        }

        sc.close();
    }
}

