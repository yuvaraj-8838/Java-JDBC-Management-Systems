public class Complaint {

    private int complaintId;
    private int userId;
    private int officerId;
    private String complaintTitle;
    private String complaintDescription;
    private String status;
    private String resolution;

    public Complaint(int complaintId, int userId, int officerId,
                     String complaintTitle, String complaintDescription,
                     String status, String resolution) {

        this.complaintId = complaintId;
        this.userId = userId;
        this.officerId = officerId;
        this.complaintTitle = complaintTitle;
        this.complaintDescription = complaintDescription;
        this.status = status;
        this.resolution = resolution;
    }

    public int getComplaintId() {
        return complaintId;
    }

    public int getUserId() {
        return userId;
    }

    public int getOfficerId() {
        return officerId;
    }

    public String getComplaintTitle() {
        return complaintTitle;
    }

    public String getComplaintDescription() {
        return complaintDescription;
    }

    public String getStatus() {
        return status;
    }

    public String getResolution() {
        return resolution;
    }
}