import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class EmployeeDAO {

    public void addEmployee(Employee employee) {

        String employeeSql =
                "INSERT INTO employees "
              + "(employee_name, email_id, dept, joining_date) "
              + "VALUES (?, ?, ?, ?)";

        String balanceSql =
                "INSERT INTO leave_balance "
              + "(employee_id, used_leaves, remaining_leaves) "
              + "VALUES (?, 0, 12)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement employeePs =
                     con.prepareStatement(
                             employeeSql,
                             java.sql.Statement.RETURN_GENERATED_KEYS);
             PreparedStatement balancePs =
                     con.prepareStatement(balanceSql)) {

            // Insert employee
            employeePs.setString(1, employee.getEmployeeName());
            employeePs.setString(2, employee.getEmailId());
            employeePs.setString(3, employee.getDept());
            employeePs.setString(4, employee.getJoiningDate());

            employeePs.executeUpdate();

            // Get generated employee ID
            ResultSet generatedKeys =
                    employeePs.getGeneratedKeys();

            if (generatedKeys.next()) {

                int employeeId =
                        generatedKeys.getInt(1);

                // Create leave balance for new employee
                balancePs.setInt(1, employeeId);

                balancePs.executeUpdate();

                System.out.println(
                        "Employee added successfully!"
                );

                System.out.println(
                        "Employee ID: " + employeeId
                );

                System.out.println(
                        "Leave balance created: 12 days"
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Failed to add employee."
            );

            e.printStackTrace();
        }
    }


    public boolean employeeExists(int employeeId) {

        String sql =
                "SELECT employee_id "
              + "FROM employees "
              + "WHERE employee_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setInt(1, employeeId);

            ResultSet rs =
                    ps.executeQuery();

            return rs.next();

        } catch (SQLException e) {

            System.out.println(
                    "Error checking employee ID."
            );

            e.printStackTrace();

            return false;
        }
    }
}