import java.sql.*;
import java.util.Scanner;

// Hotel Management
public class HotelReservationSystem {
    private static  final  String url = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String username = "root";
    private static final String password = "root";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

         try{
             Class.forName("com.mysql.cj.jdbc.Driver");
         }catch (ClassNotFoundException e){
             System.out.println(e.getMessage());
         }

         try{
             Connection con = DriverManager.getConnection(url, username, password);
             while(true){
                 System.out.println();
                 System.out.println("HOTEL MANAGEMENT SYSTEM");
                 Scanner sc = new Scanner(System.in);
                 System.out.println("1. Reserve a room");
                 System.out.println("2. View Reservations");
                 System.out.println("3. Get Room Number");
                 System.out.println("4. Update Reservations");
                 System.out.println("5. Delete Reservations");
                 System.out.println("0. Exit");
                 System.out.print("Choose an option : ");
                 int choice = sc.nextInt();

                 switch (choice){
                     case 1 :
                         reserveRoom(con, sc);
                         break;
                     case 2 :
                         viewReservations(con);
                         break;
                     case 3:
                         getRoomNumber(con,sc);
                         break;
                     case 4:
                         updateReservation(con, sc);
                         break;
                     case 5:
                         deleteReservation(con, sc);
                         break;
                     case 0 :
                         exit();
                         sc.close();
                         return;
                     default:
                         System.out.println("Invalid choice. Try again !! ");

                 }
             }


         }catch (SQLException e){
             System.out.println(e.getMessage());
         }catch (InterruptedException e){
              throw new RuntimeException(e);
         }

    }

    private static void exit() throws InterruptedException{
        System.out.print("Exiting System");
        int i = 5;
        while(i!=0){
            System.out.print(". ");
            Thread.sleep(450);
            i--;
        }
        System.out.println();
        System.out.println("Thank you for using Hotel Reservation System !! ");
    }

    private static void deleteReservation(Connection con, Scanner sc) {
         try{
             System.out.print("Enter reservation Id to delete : ");
             int reservationId = sc.nextInt();

             if(!reservationExists(con, reservationId)){
                 System.out.println("Reservation not found for the given Id.");
                 return;
             }

             String query = "DELETE from reservations where reservation_id =" + reservationId +";";

             try(Statement statement = con.createStatement()){
                   int affectedRows = statement.executeUpdate(query);

                   if (affectedRows > 0){
                       System.out.println("Reservation deleted successfully.");
                   }else{
                       System.out.println("Reservation deletion failed.");
                   }
             }
         }catch (SQLException e){
              e.printStackTrace();
         }
    }

    private static boolean reservationExists(Connection con, int reservationId) {
            try{
                 String query = "SELECT reservation_id from reservations where reservation_id = "+ reservationId + ";";

                 try(Statement statement = con.createStatement()){
                      ResultSet resultSet = statement.executeQuery(query);
                      return resultSet.next();
                 }
            }catch (SQLException e){
                 e.printStackTrace();
                 return false;
            }

    }

    private static void updateReservation(Connection con, Scanner sc) {
             try{
                 System.out.print("Enter reservation Id to update : ");
                 int reservationId = sc.nextInt();
                 sc.nextLine();

                 if(!reservationExists(con,reservationId)){
                     System.out.println("Reservation not found for the given Id.");
                     return;
                 }

                 System.out.print("Enter new guest name : ");
                 String newGuestName = sc.next();
                 sc.nextLine();
                 System.out.print("Enter new room number : ");
                 int newRoomNumber = sc.nextInt();
                 System.out.print("Enter new Contact Number : ");
                 String newContactNumber = sc.next();
                 sc.nextLine();

                 String query = "UPDATE reservations SET guest_name = '" + newGuestName + "', room_number = " + newRoomNumber+", contact_number = '"+ newContactNumber+"' " +
                         "where reservation_id ="+reservationId+";";
                   try(Statement statement = con.createStatement()){
                          int affectedRows = statement.executeUpdate(query);
                          if(affectedRows > 0){
                              System.out.println("Reservation update successfully.");
                          }else{
                              System.out.println("Reservation update failed.");
                          }
                   }


             }catch (SQLException e){
                   e.printStackTrace();
             }
    }

    private static void getRoomNumber(Connection con, Scanner sc) {
           try{
               System.out.print("Enter Reservation Id : ");
               int reservation_id = sc.nextInt();
               System.out.print("Enter Guest Name :");
               String guestName = sc.next();

               String query = "SELECT room_number FROM reservations " +
                       "WHERE reservation_id = " + reservation_id + " AND guest_name = '" + guestName + "';";

               try(Statement statement = con.createStatement()){
                    ResultSet resultSet = statement.executeQuery(query);
                     if(resultSet.next()){
                          int roomNumber = resultSet.getInt("room_number");
                         System.out.println("Room number for Reservation Id " + reservation_id + " and Guest " + guestName + " is : " + roomNumber);
                     }else{
                         System.out.println("Reservation not fount for the given Id and guest name.");
                     }
               }
           }catch (SQLException e){
               e.printStackTrace();
           }
    }

    private static void viewReservations(Connection con) {
          String query = "SELECT reservation_id, guest_name, room_number, contact_number, reservation_date FROM reservations;";
          try(Statement statement = con.createStatement()){
              ResultSet resultSet = statement.executeQuery(query);
              System.out.println("Current Reservations:");
              System.out.println("+------------------+----------------+------------------+----------------------+----------------------+");
              System.out.println("| Reservation Id   |   Guest Name   |    Room Number   |    Contact Number    |   Reservation Date   |");
              System.out.println("+------------------+----------------+------------------+----------------------+----------------------+");

              while (resultSet.next()){
                    int reservationId = resultSet.getInt("reservation_id");
                    String guestName = resultSet.getString("guest_name");
                    int roomNumber = resultSet.getInt("room_number");
                    String contactNumber = resultSet.getString("contact_number");
                    String date = resultSet.getString("reservation_date");

                  System.out.printf("|  %-14d  | %-15s| %-13d    | %-20s | %-19s  |\n", reservationId,guestName,roomNumber,contactNumber,date);
                  System.out.println("+------------------+----------------+------------------+----------------------+----------------------+");

              }

          }catch (SQLException e){
                    e.printStackTrace();
          }
    }

    private static void reserveRoom(Connection con, Scanner sc) {
         try{
             System.out.print("Enter guest name : ");
             String guestName = sc.next();
             sc.nextLine();
             System.out.print("Enter room number : ");
             int roomNumber = sc.nextInt();
             System.out.print("Enter contact number : ");
             String contactNumber = sc.next();

             String query = "Insert into reservations(guest_name, room_number, contact_number)" +
                     "VALUES ('" + guestName +"'," + roomNumber + ", '"+ contactNumber +"')";
             try(Statement statement = con.createStatement()){
                  int affectedRooms = statement.executeUpdate(query);

                  if(affectedRooms > 0){
                      System.out.println("Reservation successful.");
                  }else {
                      System.out.println("Reservation failed.");
                  }
             }

         }catch (SQLException e){
             e.printStackTrace();
         }
    }
}
