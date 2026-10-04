public class Employee {

    private int employeeId;
    private String employeeName;
    private String emailId;
    private String dept;
    private String joiningDate;

    public Employee(String employeeName, String emailId, String dept, String joiningDate) {
        this.employeeName = employeeName;
        this.emailId = emailId;
        this.dept = dept;
        this.joiningDate = joiningDate;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getDept() {
        return dept;
    }

    public String getJoiningDate() {
        return joiningDate;
    }
}