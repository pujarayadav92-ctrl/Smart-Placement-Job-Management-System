
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class StudentManagement {

    static final String URL = "jdbc:mysql://localhost:3306/smart_placement";
    static final String USER = "root";
    static final String PASSWORD = System.getenv("DB_PASSWORD");
    public static void run(Scanner sc) {

        int choice = 0;

        do {
            System.out.println("\n===== Student Management =====");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student Skills");
            System.out.println("4. Delete Student");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            if (!sc.hasNextInt()) {
                System.out.println("Please enter a valid number!");
                sc.nextLine();
                continue;
            }

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addStudent(sc);
                    break;

                case 2:
                    viewStudents();
                    break;

                case 3:
                    updateStudent(sc);
                    break;

                case 4:
                    deleteStudent(sc);
                    break;

                case 5:
                    System.out.println("Returning to Main Menu...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 5);
    }

    static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    static void addStudent(Scanner sc) {

        String sql = "INSERT INTO students (name, email, course, skills) VALUES (?, ?, ?, ?)";

        try (Connection con = getConnection()) {

            System.out.print("Enter name: ");
            String name = sc.nextLine();

            System.out.print("Enter email: ");
            String email = sc.nextLine();

            System.out.print("Enter course: ");
            String course = sc.nextLine();

            System.out.print("Enter skills: ");
            String skills = sc.nextLine();

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, course);
                ps.setString(4, skills);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    System.out.println("Student added successfully!");
                }
            }

        } catch (Exception e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }

    static void viewStudents() {

        String sql = "SELECT * FROM students";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            boolean found = false;

            while (rs.next()) {
                found = true;

                System.out.println("\nStudent ID: " + rs.getInt("student_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Course: " + rs.getString("course"));
                System.out.println("Skills: " + rs.getString("skills"));
                System.out.println("--------------------------");
            }

            if (!found) {
                System.out.println("No students found.");
            }

        } catch (Exception e) {
            System.out.println("Error viewing students: " + e.getMessage());
        }
    }

    static void updateStudent(Scanner sc) {

        String sql = "UPDATE students SET skills = ? WHERE student_id = ?";

        try (Connection con = getConnection()) {

            System.out.print("Enter student ID: ");

            if (!sc.hasNextInt()) {
                System.out.println("Invalid student ID!");
                sc.nextLine();
                return;
            }

            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter new skills: ");
            String skills = sc.nextLine();

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, skills);
                ps.setInt(2, id);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    System.out.println("Student updated successfully!");
                } else {
                    System.out.println("Student not found!");
                }
            }

        } catch (Exception e) {
            System.out.println("Error updating student: " + e.getMessage());
        }
    }

    static void deleteStudent(Scanner sc) {

        String sql = "DELETE FROM students WHERE student_id = ?";

        try (Connection con = getConnection()) {

            System.out.print("Enter student ID to delete: ");

            if (!sc.hasNextInt()) {
                System.out.println("Invalid student ID!");
                sc.nextLine();
                return;
            }

            int id = sc.nextInt();
            sc.nextLine();

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    System.out.println("Student deleted successfully!");
                } else {
                    System.out.println("Student not found!");
                }
            }

        } catch (Exception e) {
            System.out.println("Error deleting student: " + e.getMessage());
        }
    }
}


