import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        UserDAO userDAO = new UserDAO();
        ComplaintDAO complaintDAO = new ComplaintDAO();

        while (true) {

            System.out.println("\n===== COMPLAINT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add User");
            System.out.println("2. View Users");
            System.out.println("3. Register Complaint");
            System.out.println("4. View Complaints");
            System.out.println("5. Assign Officer");
            System.out.println("6. Update Status");
            System.out.println("7. Add Resolution");
            System.out.println("8. Exit");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

            case 1:
                System.out.print("Enter User Name: ");
                String name = sc.nextLine();

                System.out.print("Enter Email: ");
                String email = sc.nextLine();

                System.out.print("Enter Phone: ");
                String phone = sc.nextLine();

                User user = new User(0, name, email, phone);

                userDAO.addUser(user);
                break;

            case 2:
                userDAO.viewUsers();
                break;

            case 3:
                System.out.print("Enter User ID: ");
                int userId = sc.nextInt();

                System.out.print("Enter Officer ID: ");
                int officerId = sc.nextInt();
                sc.nextLine();

                System.out.print("Enter Complaint Title: ");
                String title = sc.nextLine();

                System.out.print("Enter Complaint Description: ");
                String description = sc.nextLine();

                Complaint complaint = new Complaint(
                    0,
                    userId,
                    officerId,
                    title,
                    description,
                    "Pending",
                    null
                );

                complaintDAO.registerComplaint(complaint);
                break;

            case 4:
                complaintDAO.viewComplaints();
                break;

            case 5:
                System.out.print("Enter Complaint ID: ");
                int complaintId = sc.nextInt();

                System.out.print("Enter Officer ID: ");
                int newOfficerId = sc.nextInt();

                complaintDAO.assignOfficer(complaintId, newOfficerId);
                break;

            case 6:
                System.out.print("Enter Complaint ID: ");
                int statusComplaintId = sc.nextInt();
                sc.nextLine();

                System.out.print("Enter New Status: ");
                String status = sc.nextLine();

                complaintDAO.updateStatus(statusComplaintId, status);
                break;

            case 7:
                System.out.print("Enter Complaint ID: ");
                int resolutionComplaintId = sc.nextInt();
                sc.nextLine();

                System.out.print("Enter Resolution: ");
                String resolution = sc.nextLine();

                complaintDAO.addResolution(
                    resolutionComplaintId,
                    resolution
                );
                break;

            case 8:
                System.out.println("Thank you!");
                sc.close();
                return;

            default:
                System.out.println("Invalid choice!");
            }
        }
    }
}