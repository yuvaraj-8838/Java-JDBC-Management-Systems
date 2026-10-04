import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LeaveBalanceDAO {

    // Get Remaining Leaves
    public int getRemainingLeaves(int employeeId) {

        String sql = "SELECT remaining_leaves "
                   + "FROM leave_balance "
                   + "WHERE employee_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employeeId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return rs.getInt("remaining_leaves");
            }

        } catch (SQLException e) {

            System.out.println("Error checking leave balance.");
            e.printStackTrace();
        }

        return 0;
    }


    // Update Leave Balance
    public void updateLeaveBalance(int employeeId, int leaveDays) {

        String sql = "UPDATE leave_balance "
                   + "SET used_leaves = used_leaves + ?, "
                   + "remaining_leaves = remaining_leaves - ? "
                   + "WHERE employee_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, leaveDays);
            ps.setInt(2, leaveDays);
            ps.setInt(3, employeeId);

            int rows = ps.executeUpdate();

            if (rows > 0) {

                System.out.println(
                    "Leave balance updated successfully!"
                );

            } else {

                System.out.println(
                    "Leave balance record not found!"
                );
            }

        } catch (SQLException e) {

            System.out.println(
                "Failed to update leave balance."
            );

            e.printStackTrace();
        }
    }
}