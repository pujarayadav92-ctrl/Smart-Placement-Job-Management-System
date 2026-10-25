
import java.sql.*;
import java.util.Scanner;

public class JobManagement {

    static final String URL =
            "jdbc:mysql://localhost:3306/smart_placement";
    static final String USER = "root";
    static final String PASSWORD = System.getenv("DB_PASSWORD");
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void run(Scanner sc) {
        int choice = 0;

        do {
            System.out.println("\n===== Job Management =====");
            System.out.println("1. Add Job");
            System.out.println("2. View Jobs");
            System.out.println("3. Update Job Salary");
            System.out.println("4. Delete Job");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
                choice = 0;
                continue;
            }

            switch (choice) {
                case 1:
                    addJob(sc);
                    break;
                case 2:
                    viewJobs();
                    break;
                case 3:
                    updateJob(sc);
                    break;
                case 4:
                    deleteJob(sc);
                    break;
                case 5:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 5);
    }

    public static void addJob(Scanner sc) {
        String sql = "INSERT INTO jobs "
                + "(job_title, company_name, location, salary, "
                + "required_skills, job_description) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.print("Enter Job Title: ");
            ps.setString(1, sc.nextLine());

            System.out.print("Enter Company Name: ");
            ps.setString(2, sc.nextLine());

            System.out.print("Enter Location: ");
            ps.setString(3, sc.nextLine());

            System.out.print("Enter Salary: ");
            ps.setString(4, sc.nextLine());

            System.out.print("Enter Required Skills: ");
            ps.setString(5, sc.nextLine());

            System.out.print("Enter Job Description: ");
            ps.setString(6, sc.nextLine());

            ps.executeUpdate();
            System.out.println("Job added successfully!");

        } catch (SQLException e) {
            System.out.println("Error adding job: " + e.getMessage());
        }
    }

    public static void viewJobs() {
        String sql = "SELECT * FROM jobs";

        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println("\nJob ID: " + rs.getInt("job_id"));
                System.out.println("Title: " + rs.getString("job_title"));
                System.out.println("Company: " + rs.getString("company_name"));
                System.out.println("Location: " + rs.getString("location"));
                System.out.println("Salary: " + rs.getString("salary"));
                System.out.println("Skills: " + rs.getString("required_skills"));
                System.out.println("Description: "
                        + rs.getString("job_description"));
                System.out.println("--------------------------");
            }

            if (!found) {
                System.out.println("No jobs found.");
            }

        } catch (SQLException e) {
            System.out.println("Error viewing jobs: " + e.getMessage());
        }
    }

    public static void updateJob(Scanner sc) {
        String sql = "UPDATE jobs SET salary = ? WHERE job_id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.print("Enter Job ID: ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.print("Enter New Salary: ");
            String salary = sc.nextLine();

            ps.setString(1, salary);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Job updated successfully!");
            } else {
                System.out.println("Job ID not found.");
            }

        } catch (SQLException | NumberFormatException e) {
            System.out.println("Error updating job: " + e.getMessage());
        }
    }

    public static void deleteJob(Scanner sc) {
        String sql = "DELETE FROM jobs WHERE job_id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.print("Enter Job ID to delete: ");
            int id = Integer.parseInt(sc.nextLine());

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Job deleted successfully!");
            } else {
                System.out.println("Job ID not found.");
            }

        } catch (SQLException | NumberFormatException e) {
            System.out.println("Error deleting job: " + e.getMessage());
        }
    }
}

