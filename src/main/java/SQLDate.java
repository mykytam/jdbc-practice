import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.*;

public class SQLDate {
    public static void main(String[] args) throws ClassNotFoundException, ParserConfigurationException, IOException {
        String username = "root";
        String password = "database123";
        String connectionUrl = "jdbc:mysql://localhost:3306/mydbtest";
        try (Connection connection = DriverManager.getConnection(connectionUrl, username, password)) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("drop table Books");
            statement.executeUpdate("create table if not exists  Books (id MEDIUMINT NOT NULL AUTO_INCREMENT, name CHAR(30) NOT NULL, dt DATE, PRIMARY KEY (id))");

            PreparedStatement preparedStatement = connection.prepareStatement("insert into Books (name, dt) values ('superBook', ?)");
            preparedStatement.setDate(1, new Date(1584294053189L));
            preparedStatement.execute();

            statement.executeUpdate("insert into Books (name, dt) values ('olderBook', {d '2020-03-14'})");

            ResultSet resultSet = statement.executeQuery("select * from Books");
            while (resultSet.next()) {
                System.out.println(resultSet.getDate("dt"));
            }

        } catch (SQLException e) { e.printStackTrace(); }
    }
}
