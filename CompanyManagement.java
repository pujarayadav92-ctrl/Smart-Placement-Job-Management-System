

import java.sql.*;
import java.util.Scanner;

public class CompanyManagement {

    static final String URL = "jdbc:mysql://localhost:3306/smart_placement";
    static final String USER = "root";
    static final String PASSWORD = System.getenv("DB_PASSWORD");

    static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void run(Scanner sc) {
        int choice=0;

        do {
            System.out.println("\n===== Company Management =====");
            System.out.println("1. Add Company");
            System.out.println("2. View Companies");
            System.out.println("3. Update Company");
            System.out.println("4. Delete Company");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            String input = sc.nextLine();

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
                continue;
            }

            switch (choice) {
                case 1:
                    addCompany(sc);
                    break;
                case 2:
                    viewCompanies();
                    break;
                case 3:
                    updateCompany(sc);
                    break;
                case 4:
                    deleteCompany(sc);
                    break;
                case 5:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 5);
    }

    static void addCompany(Scanner sc) {
        String sql = "INSERT INTO companies (company_name, email, location) VALUES (?, ?, ?)";

        try (Connection con = getConnection()) {
            System.out.print("Enter company name: ");
            String name = sc.nextLine();

            System.out.print("Enter company email: ");
            String email = sc.nextLine();

            System.out.print("Enter company location: ");
            String location = sc.nextLine();

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, location);

                ps.executeUpdate();
                System.out.println("Company added successfully!");
            }

        } catch (Exception e) {
            System.out.println("Error adding company: " + e.getMessage());
        }
    }

    static void viewCompanies() {
        String sql = "SELECT * FROM companies";

        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            boolean found = false;

            while (rs.next()) {
                found = true;

                System.out.println("\nCompany ID: " + rs.getInt("company_id"));
                System.out.println("Name: " + rs.getString("company_name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Location: " + rs.getString("location"));
                System.out.println("--------------------------");
            }

            if (!found) {
                System.out.println("No companies found.");
            }

        } catch (Exception e) {
            System.out.println("Error viewing companies: " + e.getMessage());
        }
    }

    static void updateCompany(Scanner sc) {
        String sql = "UPDATE companies SET company_name=?, email=?, location=? WHERE company_id=?";

        try (Connection con = getConnection()) {
            System.out.print("Enter company ID: ");
            int id;

            try {
                id = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid company ID!");
                return;
            }

            System.out.print("Enter new company name: ");
            String name = sc.nextLine();

            System.out.print("Enter new email: ");
            String email = sc.nextLine();

            System.out.print("Enter new location: ");
            String location = sc.nextLine();

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, location);
                ps.setInt(4, id);

                if (ps.executeUpdate() > 0) {
                    System.out.println("Company updated successfully!");
                } else {
                    System.out.println("Company not found!");
                }
            }

        } catch (Exception e) {
            System.out.println("Error updating company: " + e.getMessage());
        }
    }

    static void deleteCompany(Scanner sc) {
        String sql = "DELETE FROM companies WHERE company_id = ?";

        try (Connection con = getConnection()) {
            System.out.print("Enter company ID to delete: ");
            int id;

            try {
                id = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid company ID!");
                return;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);

                if (ps.executeUpdate() > 0) {
                    System.out.println("Company deleted successfully!");
                } else {
                    System.out.println("Company not found!");
                }
            }

        } catch (Exception e) {
            System.out.println("Error deleting company: " + e.getMessage());
        }
    }
}
