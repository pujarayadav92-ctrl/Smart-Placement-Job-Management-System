
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ApplicationManagement {

    static final String URL = "jdbc:mysql://localhost:3306/smart_placement";
    static final String USER = "root";
    static final String PASSWORD = System.getenv("DB_PASSWORD");

    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void run(Scanner sc) {
        int choice = 0;

        do {
            System.out.println("\n===== Application Management =====");
            System.out.println("1. Add Application");
            System.out.println("2. View Applications");
            System.out.println("3. Update Application Status");
            System.out.println("4. Delete Application");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        addApplication(sc);
                        break;
                    case 2:
                        viewApplications();
                        break;
                    case 3:
                        updateStatus(sc);
                        break;
                    case 4:
                        deleteApplication(sc);
                        break;
                    case 5:
                        System.out.println("Returning to Main Menu...");
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
                choice = 0;
            }
        } while (choice != 5);
    }

    static void addApplication(Scanner sc) {
        String checkSql = "SELECT application_id FROM applications "
                + "WHERE student_id = ? AND job_id = ?";
        String insertSql = "INSERT INTO applications (student_id, job_id) VALUES (?, ?)";

        try (Connection con = getConnection()) {
            System.out.print("Enter Student ID: ");
            int studentId = Integer.parseInt(sc.nextLine());

            System.out.print("Enter Job ID: ");
            int jobId = Integer.parseInt(sc.nextLine());

            if (studentId <= 0 || jobId <= 0) {
                System.out.println("Student ID and Job ID must be greater than 0.");
                return;
            }

            try (PreparedStatement check = con.prepareStatement(checkSql)) {
                check.setInt(1, studentId);
                check.setInt(2, jobId);

                try (ResultSet rs = check.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("Application already exists for this student and job.");
                        return;
                    }
                }
            }

            try (PreparedStatement insert = con.prepareStatement(insertSql)) {
                insert.setInt(1, studentId);
                insert.setInt(2, jobId);

                if (insert.executeUpdate() > 0) {
                    System.out.println("Application added successfully!");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter valid numeric IDs.");
        } catch (SQLException e) {
            System.out.println("Error adding application: " + e.getMessage());
        }
    }

    static void viewApplications() {
        String sql = "SELECT * FROM applications ORDER BY application_id";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println("\nApplication ID: " + rs.getInt("application_id"));
                System.out.println("Student ID: " + rs.getInt("student_id"));
                System.out.println("Job ID: " + rs.getInt("job_id"));
                System.out.println("Date: " + rs.getDate("application_date"));
                System.out.println("Status: " + rs.getString("status"));
                System.out.println("--------------------------");
            }

            if (!found) {
                System.out.println("No applications found.");
            }
        } catch (SQLException e) {
            System.out.println("Error viewing applications: " + e.getMessage());
        }
    }

    static void updateStatus(Scanner sc) {
        String sql = "UPDATE applications SET status = ? WHERE application_id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.print("Enter Application ID: ");
            int applicationId = Integer.parseInt(sc.nextLine());

            System.out.print("Enter Status (Pending/Selected/Rejected): ");
            String status = sc.nextLine().trim();

            if (!status.equalsIgnoreCase("Pending")
                    && !status.equalsIgnoreCase("Selected")
                    && !status.equalsIgnoreCase("Rejected")) {
                System.out.println("Invalid status!");
                return;
            }

            ps.setString(1, status.substring(0, 1).toUpperCase()
                    + status.substring(1).toLowerCase());
            ps.setInt(2, applicationId);

            if (ps.executeUpdate() > 0) {
                System.out.println("Application status updated!");
            } else {
                System.out.println("Application ID not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid Application ID.");
        } catch (SQLException e) {
            System.out.println("Error updating status: " + e.getMessage());
        }
    }

    static void deleteApplication(Scanner sc) {
        String sql = "DELETE FROM applications WHERE application_id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.print("Enter Application ID to delete: ");
            int applicationId = Integer.parseInt(sc.nextLine());

            ps.setInt(1, applicationId);

            if (ps.executeUpdate() > 0) {
                System.out.println("Application deleted successfully!");
            } else {
                System.out.println("Application ID not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid Application ID.");
        } catch (SQLException e) {
            System.out.println("Error deleting application: " + e.getMessage());
        }
    }
}

