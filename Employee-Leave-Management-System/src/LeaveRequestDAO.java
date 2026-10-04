import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LeaveRequestDAO {

    // ==============================
    // APPLY LEAVE
    // ==============================

    public void applyLeave(LeaveRequest leaveRequest) {

        String sql =
                "INSERT INTO leave_requests "
              + "(employee_id, leave_type, start_date, end_date, reason, status) "
              + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setInt(
                    1,
                    leaveRequest.getEmployeeId()
            );

            ps.setString(
                    2,
                    leaveRequest.getLeaveType()
            );

            ps.setString(
                    3,
                    leaveRequest.getStartDate()
            );

            ps.setString(
                    4,
                    leaveRequest.getEndDate()
            );

            ps.setString(
                    5,
                    leaveRequest.getReason()
            );

            ps.setString(
                    6,
                    leaveRequest.getStatus()
            );

            ps.executeUpdate();

            System.out.println(
                    "Leave applied successfully!"
            );

        } catch (SQLException e) {

            System.out.println(
                    "Failed to apply leave."
            );

            e.printStackTrace();
        }
    }


    // ==============================
    // VIEW LEAVE REQUESTS
    // ==============================

    public void viewLeaveRequests() {

        String sql =
                "SELECT * FROM leave_requests";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql);
             ResultSet rs =
                     ps.executeQuery()) {

            while (rs.next()) {

                System.out.println(
                        "----------------------------"
                );

                System.out.println(
                        "Leave ID: "
                        + rs.getInt("leave_id")
                );

                System.out.println(
                        "Employee ID: "
                        + rs.getInt("employee_id")
                );

                System.out.println(
                        "Leave Type: "
                        + rs.getString("leave_type")
                );

                System.out.println(
                        "Start Date: "
                        + rs.getDate("start_date")
                );

                System.out.println(
                        "End Date: "
                        + rs.getDate("end_date")
                );

                System.out.println(
                        "Reason: "
                        + rs.getString("reason")
                );

                System.out.println(
                        "Status: "
                        + rs.getString("status")
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Failed to retrieve leave requests."
            );

            e.printStackTrace();
        }
    }


    // ==============================
    // APPROVE / REJECT LEAVE
    // ==============================

    public void updateLeaveStatus(
            int leaveId,
            String status) {

        String selectSql =
                "SELECT employee_id, start_date, end_date, status "
              + "FROM leave_requests "
              + "WHERE leave_id = ?";

        String updateSql =
                "UPDATE leave_requests "
              + "SET status = ? "
              + "WHERE leave_id = ?";


        Connection con = null;

        try {

            con = DBConnection.getConnection();

            // Start transaction
            con.setAutoCommit(false);


            // ==============================
            // STEP 1: FIND LEAVE
            // ==============================

            try (PreparedStatement selectPs =
                         con.prepareStatement(selectSql)) {

                selectPs.setInt(1, leaveId);

                try (ResultSet rs =
                             selectPs.executeQuery()) {

                    if (!rs.next()) {

                        System.out.println(
                                "Leave ID not found!"
                        );

                        con.rollback();

                        return;
                    }


                    // ==============================
                    // STEP 2: CHECK CURRENT STATUS
                    // ==============================

                    int employeeId =
                            rs.getInt("employee_id");

                    String oldStatus =
                            rs.getString("status");

                    if (!oldStatus.equalsIgnoreCase(
                            "Pending")) {

                        System.out.println(
                                "Leave is already "
                                + oldStatus
                                + "!"
                        );

                        con.rollback();

                        return;
                    }


                    // ==============================
                    // STEP 3: APPROVE LEAVE
                    // ==============================

                    if (status.equalsIgnoreCase(
                            "Approved")) {

                        java.sql.Date startDate =
                                rs.getDate("start_date");

                        java.sql.Date endDate =
                                rs.getDate("end_date");


                        long leaveDays =
                                java.time.temporal.ChronoUnit.DAYS.between(
                                        startDate.toLocalDate(),
                                        endDate.toLocalDate()
                                ) + 1;


                        // Get leave balance
                        LeaveBalanceDAO balanceDAO =
                                new LeaveBalanceDAO();

                        int remainingLeaves =
                                balanceDAO.getRemainingLeaves(
                                        employeeId
                                );


                        if (leaveDays >
                                remainingLeaves) {

                            System.out.println(
                                    "Insufficient leave balance!"
                            );

                            con.rollback();

                            return;
                        }


                        // NOTE:
                        // Existing LeaveBalanceDAO creates
                        // its own connection.
                        //
                        // Therefore the balance update is
                        // still separate from this transaction.
                        //
                        // We will improve this after confirming
                        // your database structure.

                        balanceDAO.updateLeaveBalance(
                                employeeId,
                                (int) leaveDays
                        );
                    }


                    // ==============================
                    // STEP 4: UPDATE LEAVE STATUS
                    // ==============================

                    try (PreparedStatement updatePs =
                                 con.prepareStatement(updateSql)) {

                        updatePs.setString(
                                1,
                                status
                        );

                        updatePs.setInt(
                                2,
                                leaveId
                        );

                        int rows =
                                updatePs.executeUpdate();


                        if (rows > 0) {

                            con.commit();

                            System.out.println(
                                    "Leave status updated to "
                                    + status
                                    + "!"
                            );

                        } else {

                            con.rollback();

                            System.out.println(
                                    "Failed to update leave status."
                            );
                        }
                    }
                }
            }

        } catch (SQLException e) {

            if (con != null) {

                try {
                    con.rollback();

                } catch (SQLException rollbackError) {

                    rollbackError.printStackTrace();
                }
            }

            System.out.println(
                    "Failed to update leave status."
            );

            e.printStackTrace();

        } finally {

            if (con != null) {

                try {

                    con.setAutoCommit(true);
                    con.close();

                } catch (SQLException e) {

                    e.printStackTrace();
                }
            }
        }
    }
}