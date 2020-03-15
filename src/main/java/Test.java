import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.*;

public class Test {
    public static void main(String[] args) throws ClassNotFoundException, ParserConfigurationException, IOException {
        // Классы Connection, Statement, ResultSet главные в JDBC
        String username = "root";
        String password = "database123";
        String connectionUrl = "jdbc:mysql://localhost:3306/mydbtest";
        // Class.forName("com.mysql.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(connectionUrl, username, password)) {
            Statement statement = connection.createStatement();
            // executeQuery - если select, executeUpdate - inset, update, delete
            statement.executeUpdate("drop table Users");
            statement.executeUpdate("create table if not exists  Users (id MEDIUMINT NOT NULL AUTO_INCREMENT, name CHAR(30) NOT NULL, password CHAR(30) NOT NULL,PRIMARY KEY (id))");
            statement.executeUpdate("insert into Users (name, password) values ('ivan', '123')");
            statement.executeUpdate("insert into Users (name, password) values ('misha', '321')");


            String userId = "1";

            PreparedStatement preparedStatement = connection.prepareStatement("select * from Users where id = ?");
            preparedStatement.setString(1, userId ); // 1 это номер знака ?
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println("userName : " + resultSet.getString("name"));
                System.out.println("userPassword : " + resultSet.getString("password"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
