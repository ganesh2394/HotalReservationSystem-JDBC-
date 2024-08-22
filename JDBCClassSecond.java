import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
                            //          Update data
public class JDBCClassSecond {
    public static void main(String[] args) throws ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "root";
        String query = "insert into employees(id,name,job_title,salary)values(4,'Rahul Lodhi','Data Analytics',70000)";
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

            int rowsAffected = stmt.executeUpdate(query);
            if(rowsAffected > 0){
                System.out.println("Insertion Complete successfully.");
            }else{
                System.out.println("Insertion failed !");
            }

            con.close();
            stmt.close();

            System.out.println("Connection closed successfully.");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
