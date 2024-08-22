import java.sql.*;

                    //                  Retrieve data
public class JDBCClassFirst {
    public static void main(String[] args) throws ClassNotFoundException {
           String url = "jdbc:mysql://localhost:3306/mydatabase";
           String username = "root";
           String password = "root";
           String query = "Select * from employees;";
          try{
              Class.forName("com.mysql.jdbc.Driver");
              System.out.println("Driver loaded successfully.");

          }catch (ClassNotFoundException e){
              System.out.println(e.getMessage());
          }

          try{
                // create connection
                Connection con = DriverManager.getConnection(url,username,password);
                System.out.println("Connection establish successfully.");
                // Create statement
                Statement stmt = con.createStatement();
                // Store the result.
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()){

                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String job_title = rs.getString("job_title");
                    Double salary = rs.getDouble("salary");
                    System.out.println("id: " + id);
                    System.out.println("name: "+ name);
                    System.out.println("Job title : "+ job_title);
                    System.out.println("Salary : " + salary);

                }
                con.close();
                stmt.close();
                rs.close();
              System.out.println("Connection closed successfully.");
          }catch (SQLException e){
              System.out.println(e.getMessage());
          }
    }
}
