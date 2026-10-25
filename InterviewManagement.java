

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class InterviewManagement {

    static final String URL = "jdbc:mysql://localhost:3306/smart_placement";
    static final String USER = "root";
    static final String PASSWORD = System.getenv("DB_PASSWORD");

    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void run(Scanner sc) {
        int choice = 0;

        do {
            System.out.println("\n===== Interview Management =====");
            System.out.println("1. Add Interview");
            System.out.println("2. View Interviews");
            System.out.println("3. Update Interview Status");
            System.out.println("4. Delete Interview");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        addInterview(sc);
                        break;
                    case 2:
                        viewInterviews();
                        break;
                    case 3:
                        updateStatus(sc);
                        break;
                    case 4:
                        deleteInterview(sc);
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

    static void addInterview(Scanner sc) {
        String sql = "INSERT INTO interviews "
                + "(application_id, interview_date, interview_mode, status) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection con = getConnection()) {
            System.out.print("Enter Application ID: ");
            int applicationId = Integer.parseInt(sc.nextLine());

            System.out.print("Enter Interview Date (YYYY-MM-DD): ");
            String date = sc.nextLine().trim();

            System.out.print("Enter Interview Mode (Online/Offline): ");
            String mode = sc.nextLine().trim();

            System.out.print("Enter Status (Scheduled/Completed/Cancelled): ");
            String status = sc.nextLine().trim();

            if (applicationId <= 0 || date.isEmpty()
                    || mode.isEmpty() || status.isEmpty()) {
                System.out.println("Please enter valid details.");
                return;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, applicationId);
                ps.setDate(2, java.sql.Date.valueOf(date));
                ps.setString(3, mode);
                ps.setString(4, status);

                int rows = ps.executeUpdate();
                if (rows > 0) {
                    System.out.println("Interview added successfully!");
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid date. Use YYYY-MM-DD format.");
        } catch (SQLException e) {
            System.out.println("Could not add interview: " + e.getMessage());
        }
    }

    static void viewInterviews() {
        String sql = "SELECT interview_id, application_id, interview_date, "
                + "interview_mode, status FROM interviews ORDER BY interview_id";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println("\nInterview ID: " + rs.getInt("interview_id"));
                System.out.println("Application ID: " + rs.getInt("application_id"));
                System.out.println("Date: " + rs.getDate("interview_date"));
                System.out.println("Mode: " + rs.getString("interview_mode"));
                System.out.println("Status: " + rs.getString("status"));
            }

            if (!found) {
                System.out.println("No interviews found.");
            }
        } catch (SQLException e) {
            System.out.println("Could not view interviews: " + e.getMessage());
        }
    }

    static void updateStatus(Scanner sc) {
        String sql = "UPDATE interviews SET status = ? WHERE interview_id = ?";

        try (Connection con = getConnection()) {
            System.out.print("Enter Interview ID: ");
            int interviewId = Integer.parseInt(sc.nextLine());

            System.out.print("Enter new status (Scheduled/Completed/Cancelled): ");
            String status = sc.nextLine().trim();

            if (interviewId <= 0 || status.isEmpty()) {
                System.out.println("Please enter valid details.");
                return;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, status);
                ps.setInt(2, interviewId);

                int rows = ps.executeUpdate();
                if (rows > 0) {
                    System.out.println("Interview status updated successfully!");
                } else {
                    System.out.println("Interview ID not found.");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Interview ID must be a number.");
        } catch (SQLException e) {
            System.out.println("Could not update interview: " + e.getMessage());
        }
    }

    static void deleteInterview(Scanner sc) {
        String sql = "DELETE FROM interviews WHERE interview_id = ?";

        try (Connection con = getConnection()) {
            System.out.print("Enter Interview ID to delete: ");
            int interviewId = Integer.parseInt(sc.nextLine());

            if (interviewId <= 0) {
                System.out.println("Interview ID must be greater than 0.");
                return;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, interviewId);

                int rows = ps.executeUpdate();
                if (rows > 0) {
                    System.out.println("Interview deleted successfully!");
                } else {
                    System.out.println("Interview ID not found.");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Interview ID must be a number.");
        } catch (SQLException e) {
            System.out.println("Could not delete interview: " + e.getMessage());
        }
    }
}
