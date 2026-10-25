
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int choice = 0;

        do {
            System.out.println("\n==================================");
            System.out.println(" SMART PLACEMENT MANAGEMENT SYSTEM");
            System.out.println("==================================");
            System.out.println("1. Student Management");
            System.out.println("2. Company Management");
            System.out.println("3. Job Management");
            System.out.println("4. Application Management");
            System.out.println("5. Interview Management");
            System.out.println("6. Exit");
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
                    StudentManagement.run(sc);
                    break;

                case 2:
                    CompanyManagement.run(sc);
                    break;

                case 3:
                    JobManagement.run(sc);
                    break;

                case 4:
                    ApplicationManagement.run(sc);
                    break;

                case 5:
                    InterviewManagement.run(sc);
                    break;

                case 6:
                    System.out.println("Thank you for using Smart Placement System!");
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }

        } while (choice != 6);

        sc.close();
    }
}

