import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, ParserConfigurationException, IOException {
        // Классы Connection, Statement, ResultSet главные в JDBC
        String username = "root";
        String password = "database123";
        String connectionUrl = "jdbc:mysql://localhost:3306/mydbtest";
        // Class.forName("com.mysql.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(connectionUrl, username, password)) {
            Statement statement = connection.createStatement();
            // executeQuery - если select, executeUpdate - inset, update, delete
            statement.executeUpdate("drop table Books");
            statement.executeUpdate("create table Books (id MEDIUMINT NOT NULL AUTO_INCREMENT, name VARCHAR(30) NOT NULL, PRIMARY KEY (id))");
            statement.executeUpdate("insert into Books set name = 'The Count of Monte Cristo'");
            statement.executeUpdate("insert into Books set name = 'Live And Let Die'");

            ResultSet resultSet = statement.executeQuery("select * from Books");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id"));
                System.out.println(resultSet.getString("name"));
            }

            System.out.println("__________________________");

            ResultSet resultSet2 = statement.executeQuery("select * from Books where id = 1");
            while (resultSet2.next()) {
                System.out.println(resultSet2.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
