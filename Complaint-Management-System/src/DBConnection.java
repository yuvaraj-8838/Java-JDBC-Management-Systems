import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection getConnection() {

        String url = "jdbc:mysql://localhost:3306/complaint_management";
        String username = "root";
        String password = "Yuvaraj@123";

        try {
            return DriverManager.getConnection(url, username, password);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}