import java.sql.*;
import java.util.Scanner;

public class JdbcBatchExample {
    public static void main(String[] args) {

        // Credential
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "root";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Drivers loaded successfully.");

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

           try{

               Connection connection = DriverManager.getConnection(url, username, password);
               // disable commit
               connection.setAutoCommit(false);
               String query = "INSERT INTO employees(id, name,job_title,salary) VALUES (?, ?, ?, ?);";
               PreparedStatement preparedStatement  = connection.prepareStatement(query);

               // Scanner class
               Scanner sc = new Scanner(System.in);
               while(true){
                   System.out.print("Enter Id : ");
                   int id = sc.nextInt();
                   sc.nextLine();
                   System.out.print("Enter name : ");
                   String name = sc.nextLine();
                   System.out.print("Enter Job Title : ");
                   String job_title = sc.nextLine();
                   System.out.print("Enter salary : ");
                   Double salary = sc.nextDouble();
                   sc.nextLine();

                   preparedStatement.setInt(1,id);
                   preparedStatement.setString(2,name);
                   preparedStatement.setString(3,job_title);
                   preparedStatement.setDouble(4,salary);
                   preparedStatement.addBatch();

                   System.out.print("Add more data Y/N :");
                   String decision = sc.nextLine();
                   if(decision.toUpperCase().equals("N")){
                       break;
                   }
               }

               int[] batchResult = preparedStatement.executeBatch();
               connection.commit();
               System.out.println("Batch Executed successfully.");

               // closed
               preparedStatement.close();
               connection.close();
               sc.close();

           }catch (SQLException e){
               System.out.println(e.getMessage());
           }
    }
}
