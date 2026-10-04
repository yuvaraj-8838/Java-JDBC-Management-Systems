import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    // Add User
    public void addUser(User user) {

        String sql = "INSERT INTO users (user_name, email, phone) VALUES (?, ?, ?)";

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, user.getUserName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());

            ps.executeUpdate();

            System.out.println("User added successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // View Users
    public void viewUsers() {

        String sql = "SELECT * FROM users";

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                System.out.println(
                    rs.getInt("user_id") + " | " +
                    rs.getString("user_name") + " | " +
                    rs.getString("email") + " | " +
                    rs.getString("phone")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}