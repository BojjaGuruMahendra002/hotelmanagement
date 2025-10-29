import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class dbc1 {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/testdb";
        String user = "root";
        String password = "yourpassword";

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        BufferedWriter writer = null;

        try {
            // 1. Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. Establish connection
            con = DriverManager.getConnection(url, user, password);

            // 3. Create statement and execute query
            String sql = "SELECT id, name, email FROM users";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            // 4. Create file writer
            writer = new BufferedWriter(new FileWriter("chinna.txt"));

            // 5. Write database data to file
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");

                String line = id + " | " + name + " | " + email;
                writer.write(line);
                writer.newLine(); // Move to next line
            }

            System.out.println("Data written to chinna.txt successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 6. Close resources
            try { if(rs != null) rs.close(); } catch(Exception e) {}
            try { if(stmt != null) stmt.close(); } catch(Exception e) {}
            try { if(con != null) con.close(); } catch(Exception e) {}
            try { if(writer != null) writer.close(); } catch(IOException e) {}
        }
    }
}