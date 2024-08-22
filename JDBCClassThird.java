import java.sql.*;
                                    // Delete the record
public class JDBCClassThird {
    public static void main(String[] args) throws ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "root";
        String query = "DELETE from employees where id = 4";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded successfully.");

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            // create connection
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("Connection establish successfully.");
            // Create statement
            Statement stmt = con.createStatement();

            int rowsAffected = stmt.executeUpdate(query);
            if (rowsAffected > 0) {
                System.out.println("Deletion Complete successfully." + rowsAffected + " row's affected.");
            } else {
                System.out.println("Deletion failed !");
            }

            con.close();
            stmt.close();

            System.out.println("Connection closed successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
