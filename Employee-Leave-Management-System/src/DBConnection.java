import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DBConnection {

    private static String password;

    public static Connection getConnection() {

        String url = "jdbc:mysql://localhost:3306/emp_leave_management";
        String username = "root";

        if (password == null) {

            Scanner sc = new Scanner(System.in);

            System.out.print("Enter MySQL Password: ");

            password = sc.nextLine();
        }

        try {

            Connection con =
                    DriverManager.getConnection(url, username, password);

            System.out.println("Database Connected Successfully!");

            return con;

        } catch (SQLException e) {

            System.out.println("Wrong password!");

            password = null;

            return null;
        }
    }
}