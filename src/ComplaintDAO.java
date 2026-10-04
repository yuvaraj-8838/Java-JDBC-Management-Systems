import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ComplaintDAO {

    // 1. Register Complaint
    public void registerComplaint(Complaint complaint) {

        String sql = "INSERT INTO complaints "
                   + "(user_id, officer_id, complaint_title, complaint_description, status, resolution) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, complaint.getUserId());
            ps.setInt(2, complaint.getOfficerId());
            ps.setString(3, complaint.getComplaintTitle());
            ps.setString(4, complaint.getComplaintDescription());
            ps.setString(5, complaint.getStatus());
            ps.setString(6, complaint.getResolution());

            ps.executeUpdate();

            System.out.println("Complaint registered successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 2. View Complaints
    public void viewComplaints() {

        String sql = "SELECT c.complaint_id, u.user_name, o.officer_name, "
                   + "c.complaint_title, c.status, c.resolution "
                   + "FROM complaints c "
                   + "JOIN users u ON c.user_id = u.user_id "
                   + "LEFT JOIN officers o ON c.officer_id = o.officer_id";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                System.out.println(
                    "Complaint ID: " + rs.getInt("complaint_id")
                    + " | User: " + rs.getString("user_name")
                    + " | Officer: " + rs.getString("officer_name")
                    + " | Title: " + rs.getString("complaint_title")
                    + " | Status: " + rs.getString("status")
                    + " | Resolution: " + rs.getString("resolution")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 3. Assign Officer
    public void assignOfficer(int complaintId, int officerId) {

        String sql = "UPDATE complaints "
                   + "SET officer_id = ?, status = 'Assigned' "
                   + "WHERE complaint_id = ?";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, officerId);
            ps.setInt(2, complaintId);

            ps.executeUpdate();

            System.out.println("Officer assigned successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 4. Update Status
    public void updateStatus(int complaintId, String status) {

        String sql = "UPDATE complaints "
                   + "SET status = ? "
                   + "WHERE complaint_id = ?";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, status);
            ps.setInt(2, complaintId);

            ps.executeUpdate();

            System.out.println("Complaint status updated successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 5. Add Resolution
    public void addResolution(int complaintId, String resolution) {

        String sql = "UPDATE complaints "
                   + "SET resolution = ?, status = 'Resolved' "
                   + "WHERE complaint_id = ?";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, resolution);
            ps.setInt(2, complaintId);

            ps.executeUpdate();

            System.out.println("Resolution added successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}