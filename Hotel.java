import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Hotel {

    private static final String url = "jdbc:mysql://localhost:3306/hotelmanagement";
    private static final String username = "root";
    private static final String password = "guru";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println();
                System.out.println("Hotel Management System");
                System.out.println("1. Reserve the room ");
                System.out.println("2. View Reservations");
                System.out.println("3. Get room number");
                System.out.println("4. Update Reservation");
                System.out.println("5. Delete the reservation");
                System.out.println("0. Exit");
                System.out.print("Enter choice: ");

                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        reserveRoom(connection, scanner);
                        break;
                    case 2:
                        viewReservations(connection);
                        break;
                    case 3:
                        getRoomNumber(connection, scanner);
                        break;
                    case 4:
                        updateReservation(connection, scanner);
                        break;
                    case 5:
                        deleteReservation(connection, scanner);
                        break;
                    case 0:
                        System.out.println("Exiting...");
                        scanner.close();
                        connection.close();
                        return;
                    default:
                        System.out.println("Invalid choice!");
                }}
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void reserveRoom(Connection connection, Scanner scanner) {
        try {
            System.out.print("Enter guest name: ");
            scanner.nextLine(); // consume newline
            String guestName = scanner.nextLine();

            System.out.print("Enter room number: ");
            int roomNumber = scanner.nextInt();

            System.out.print("Enter contact number: ");
            String contactNumber = scanner.next();

            String sql = "INSERT INTO reservations (guest_name, room_number, contact_number) VALUES ('"
                    + guestName + "', " + roomNumber + ", '" + contactNumber + "')";
            try (Statement statement = connection.createStatement()) {
                int affectedRows = statement.executeUpdate(sql);
                if (affectedRows > 0) {
                    System.out.println("Reservation successful!");
                } else {
                    System.out.println("Reservation failed.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error in reserveRoom: " + e.getMessage());
        }
    }

    private static void viewReservations(Connection connection) {
        try {
            String sql = "SELECT * FROM reservations";
            try (Statement statement = connection.createStatement();
                 ResultSet rs = statement.executeQuery(sql)) {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") +
                            ", Guest: " + rs.getString("guest_name") +
                            ", Room: " + rs.getInt("room_number") +
                            ", Contact: " + rs.getString("contact_number"));
                }
            }
        } catch (Exception e) {
            System.out.println("Error in viewReservations: " + e.getMessage());
        }
    }

    private static void getRoomNumber(Connection connection, Scanner scanner) {
        try {
            System.out.print("Enter guest name: ");
            scanner.nextLine();
            String guestName = scanner.nextLine();

            String sql = "SELECT room_number FROM reservations WHERE guest_name='" + guestName + "'";
            try (Statement statement = connection.createStatement();
                 ResultSet rs = statement.executeQuery(sql)) {
                if (rs.next()) {
                    System.out.println("Room number: " + rs.getInt("room_number"));
                } else {
                    System.out.println("No reservation found for " + guestName);
                }
            }
        } catch (Exception e) {
            System.out.println("Error in getRoomNumber: " + e.getMessage());
        }
    }

    private static void updateReservation(Connection connection, Scanner scanner) {
        try {
            System.out.print("Enter reservation ID to update: ");
            int id = scanner.nextInt();
            System.out.print("Enter new room number: ");
            int newRoom = scanner.nextInt();

            String sql = "UPDATE reservations SET room_number=" + newRoom + " WHERE id=" + id;
            try (Statement statement = connection.createStatement()) {
                int affectedRows = statement.executeUpdate(sql);
                if (affectedRows > 0) {
                    System.out.println("Reservation updated successfully!");
                } else {
                    System.out.println("Reservation update failed.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error in updateReservation: " + e.getMessage());
        }
    }

    private static void deleteReservation(Connection connection, Scanner scanner) {
        try {
            System.out.print("Enter reservation ID to delete: ");
            int id = scanner.nextInt();

            String sql = "DELETE FROM reservations WHERE id=" + id;
            try (Statement statement = connection.createStatement()) {
                int affectedRows = statement.executeUpdate(sql);
                if (affectedRows > 0) {
                    System.out.println("Reservation deleted successfully!");
                } else {
                    System.out.println("Reservation deletion failed.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error in deleteReservation: " + e.getMessage());
        }
    }
}
