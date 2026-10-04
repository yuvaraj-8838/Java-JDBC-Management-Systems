public class Officer {

    private int officerId;
    private String officerName;
    private String department;

    public Officer(int officerId, String officerName, String department) {
        this.officerId = officerId;
        this.officerName = officerName;
        this.department = department;
    }

    public int getOfficerId() {
        return officerId;
    }

    public String getOfficerName() {
        return officerName;
    }

    public String getDepartment() {
        return department;
    }
}