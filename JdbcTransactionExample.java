import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcTransactionExample {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "root";
        String withdrawQuery = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?;";
        String depositQuery = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?;";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Drivers loaded successfully.");

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            // disable commit
            connection.setAutoCommit(false);
            try {
                PreparedStatement withdrawStmt = connection.prepareStatement(withdrawQuery);
                PreparedStatement depositStmt = connection.prepareStatement(depositQuery);

                withdrawStmt.setDouble(1, 100.00);
                withdrawStmt.setString(2, "account14567");

                depositStmt.setDouble(1, 100.00);
                depositStmt.setString(2, "account1122");

                int affectedwithdraw = withdrawStmt.executeUpdate();
                int affecteddeposit = depositStmt.executeUpdate();

                if (affecteddeposit > 0 && affectedwithdraw > 0) {
                    // commit connection
                    connection.commit();
                    System.out.println("Transaction successfully.");
                } else {
                    connection.rollback();
                    System.out.println("Transaction failed.");
                }

                withdrawStmt.close();
                depositStmt.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            connection.close();
        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
    }
}
