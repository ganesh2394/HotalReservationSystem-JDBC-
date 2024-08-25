import java.sql.*;

public class StudentRecords {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/students";
        String username = "root";
        String password = "root";
        String query = "UPDATE student_tb SET name = 'Mohit' WHERE id = 2;";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Drivers loaded successfully.");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {

            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection established successfully.");

            Statement statement = connection.createStatement();

            int affectedRow = statement.executeUpdate(query);
            if (affectedRow > 0) {
                System.out.println("Update record successfully. " + affectedRow + " Row's affected.");
            } else {
                System.out.println("Update record failed.");
            }

            statement.close();
            connection.close();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
