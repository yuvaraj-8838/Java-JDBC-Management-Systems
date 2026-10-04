import java.util.Scanner;
import java.text.SimpleDateFormat;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("===== Employee Leave Management System =====");

        System.out.println("1. Add Employee");
        System.out.println("2. Apply Leave");
        System.out.println("3. View Leave Requests");
        System.out.println("4. Approve Leave");
        System.out.println("5. Reject Leave");
        System.out.println("6. View Leave Balance");
        System.out.println("7. Exit");

        System.out.print("Enter your choice: ");

        int choice = sc.nextInt();
        sc.nextLine();

        
        
        if (choice == 1) {

            System.out.println("===== Add Employee =====");

            System.out.print("Enter Employee Name: ");
            String name = sc.nextLine();

            if (name.trim().isEmpty()) {
                System.out.println("Employee name cannot be empty!");
                return;
            }


            System.out.print("Enter Email: ");
            String email = sc.nextLine();

            if (email.trim().isEmpty()) {
                System.out.println("E-mail is must!");
                return;
            }

            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                System.out.println("Invalid email format!");
                return;
            }


            System.out.print("Enter Department: ");
            String dept = sc.nextLine();

            if (dept.trim().isEmpty()) {
                System.out.println("Department cannot be empty!");
                return;
            }


            System.out.print("Enter Joining Date (YYYY-MM-DD): ");
            String joiningDate = sc.nextLine();

            try {

                SimpleDateFormat sdf =
                        new SimpleDateFormat("yyyy-MM-dd");

                sdf.setLenient(false);
                sdf.parse(joiningDate);

            } catch (Exception e) {

                System.out.println(
                    "Invalid joining date! Please use YYYY-MM-DD format."
                );

                return;
            }


            Employee employee = new Employee(
                    name,
                    email,
                    dept,
                    joiningDate
            );

            EmployeeDAO dao = new EmployeeDAO();

            dao.addEmployee(employee);
        }


        // ==============================
        // 2. APPLY LEAVE
        // ==============================

        else if (choice == 2) {

            System.out.println("===== Apply Leave =====");


            System.out.print("Enter Employee ID: ");
            int employeeId = sc.nextInt();
            sc.nextLine();

            if (employeeId <= 0) {

                System.out.println(
                    "Employee ID must be greater than 0!"
                );

                return;
            }


            EmployeeDAO employeeDAO = new EmployeeDAO();

            if (!employeeDAO.employeeExists(employeeId)) {

                System.out.println("Employee not found!");

                return;
            }


            System.out.print("Enter Leave Type: ");
            String leaveType = sc.nextLine();

            if (leaveType.trim().isEmpty()) {

                System.out.println("Leave type cannot be empty!");

                return;
            }


            if (!leaveType.equalsIgnoreCase("Casual")
                    && !leaveType.equalsIgnoreCase("Sick")
                    && !leaveType.equalsIgnoreCase("Earned")) {

                System.out.println("Invalid leave type!");

                return;
            }


            System.out.print("Enter Start Date (YYYY-MM-DD): ");
            String startDate = sc.nextLine();

            try {

                SimpleDateFormat sdf =
                        new SimpleDateFormat("yyyy-MM-dd");

                sdf.setLenient(false);
                sdf.parse(startDate);

            } catch (Exception e) {

                System.out.println(
                    "Invalid start date! Please use YYYY-MM-DD format."
                );

                return;
            }


            System.out.print("Enter End Date (YYYY-MM-DD): ");
            String endDate = sc.nextLine();

            try {

                SimpleDateFormat sdf =
                        new SimpleDateFormat("yyyy-MM-dd");

                sdf.setLenient(false);
                sdf.parse(endDate);

            } catch (Exception e) {

                System.out.println(
                    "Invalid end date! Please use YYYY-MM-DD format."
                );

                return;
            }


            try {

                SimpleDateFormat sdf =
                        new SimpleDateFormat("yyyy-MM-dd");

                sdf.setLenient(false);

                java.util.Date start = sdf.parse(startDate);
                java.util.Date end = sdf.parse(endDate);

                if (end.before(start)) {

                    System.out.println(
                        "End date cannot be before start date!"
                    );

                    return;
                }

            } catch (Exception e) {

                System.out.println("Date validation error!");

                return;
            }


            System.out.print("Enter Reason: ");
            String reason = sc.nextLine();

            if (reason.trim().isEmpty()) {

                System.out.println("Reason cannot be empty!");

                return;
            }


            LeaveBalanceDAO balanceDAO =
                    new LeaveBalanceDAO();

            int remainingLeaves =
                    balanceDAO.getRemainingLeaves(employeeId);


            long leaveDays =
                    java.time.temporal.ChronoUnit.DAYS.between(
                        java.time.LocalDate.parse(startDate),
                        java.time.LocalDate.parse(endDate)
                    ) + 1;


            if (leaveDays > remainingLeaves) {

                System.out.println(
                    "Insufficient leave balance!"
                );

                return;
            }


            LeaveRequest leaveRequest =
                    new LeaveRequest(
                        employeeId,
                        leaveType,
                        startDate,
                        endDate,
                        reason
                    );


            LeaveRequestDAO leaveRequestDAO =
                    new LeaveRequestDAO();

            leaveRequestDAO.applyLeave(leaveRequest);
        }


        // ==============================
        // 3. VIEW LEAVE REQUESTS
        // ==============================

        else if (choice == 3) {

            System.out.println("===== Leave Requests =====");

            LeaveRequestDAO leaveRequestDAO =
                    new LeaveRequestDAO();

            leaveRequestDAO.viewLeaveRequests();
        }


        // ==============================
        // 4. APPROVE LEAVE
        // ==============================

        else if (choice == 4) {

            System.out.println("===== Approve Leave =====");

            System.out.print("Enter Leave ID: ");
            int leaveId = sc.nextInt();
            sc.nextLine();

            if (leaveId <= 0) {

                System.out.println(
                    "Leave ID must be greater than 0!"
                );

                return;
            }


            LeaveRequestDAO leaveRequestDAO =
                    new LeaveRequestDAO();

            leaveRequestDAO.updateLeaveStatus(
                    leaveId,
                    "Approved"
            );
        }


        // ==============================
        // 5. REJECT LEAVE
        // ==============================

        else if (choice == 5) {

            System.out.println("===== Reject Leave =====");

            System.out.print("Enter Leave ID: ");
            int leaveId = sc.nextInt();
            sc.nextLine();

            if (leaveId <= 0) {

                System.out.println(
                    "Leave ID must be greater than 0!"
                );

                return;
            }


            LeaveRequestDAO leaveRequestDAO =
                    new LeaveRequestDAO();

            leaveRequestDAO.updateLeaveStatus(
                    leaveId,
                    "Rejected"
            );
        }


        // ==============================
        // 6. VIEW LEAVE BALANCE
        // ==============================

        else if (choice == 6) {

            System.out.println("===== Leave Balance =====");

            System.out.print("Enter Employee ID: ");
            int employeeId = sc.nextInt();
            sc.nextLine();

            if (employeeId <= 0) {

                System.out.println(
                    "Employee ID must be greater than 0!"
                );

                return;
            }


            EmployeeDAO employeeDAO =
                    new EmployeeDAO();

            if (!employeeDAO.employeeExists(employeeId)) {

                System.out.println("Employee not found!");

                return;
            }


            LeaveBalanceDAO balanceDAO =
                    new LeaveBalanceDAO();

            int remainingLeaves =
                    balanceDAO.getRemainingLeaves(employeeId);

            System.out.println(
                "Remaining Leaves: " + remainingLeaves
            );
        }


       

        else if (choice == 7) {

            System.out.println(
                "Thank you for using Employee Leave Management System!"
            );
        }


        // ==============================
        // INVALID CHOICE
        // ==============================

        else {

            System.out.println("Invalid choice!");
        }


        sc.close();
    }
}