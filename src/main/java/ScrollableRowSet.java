import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.*;

public class ScrollableRowSet {
    public static void main(String[] args) throws ClassNotFoundException, ParserConfigurationException, IOException {
        String username = "root";
        String password = "database123";
        String connectionUrl = "jdbc:mysql://localhost:3306/mydbtest";
        try (Connection connection = DriverManager.getConnection(connectionUrl, username, password)) {
            Statement statement = connection.createStatement();
            statement.execute("drop table Books");
            statement.executeUpdate("create table if not exists  Books (id MEDIUMINT NOT NULL AUTO_INCREMENT, name CHAR(30) NOT NULL, PRIMARY KEY (id))");
            statement.executeUpdate("insert into Books (name) values ('Had I Known')", Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate("insert into Books (name) values ('Weather')");
            statement.executeUpdate("insert into Books (name) values ('Uncanny Valley')");
            statement.executeUpdate("insert into Books (name) values ('Harry Potter')");
            statement.executeUpdate("insert into Books (name) values ('Martin Eden')");

            // пробегание по строкам в разном порядке
            Statement newStatement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //PreparedStatement preparedStatement = connection.prepareStatement("SQL HERE", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery("select * from Books");

            if (resultSet.next()){ // следующий
                System.out.println(resultSet.getString("name"));
            }
            if (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
            }
            if (resultSet.previous()) { // предыдущий
                System.out.println(resultSet.getString("name"));
            }
            if (resultSet.relative(2)) { // на сколько row сдвинуться
                System.out.println(resultSet.getString("name"));
            }
            if (resultSet.relative(-2)) {
                System.out.println(resultSet.getString("name"));
            }
            if (resultSet.absolute(4)) { // номер строки от начала
                System.out.println(resultSet.getString("name"));
            }
            if (resultSet.first()) { // первый
                System.out.println(resultSet.getString("name"));
            }
            if (resultSet.last()) { // последний
                System.out.println(resultSet.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
