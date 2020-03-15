import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.*;

public class MultipleResults {
    public static void main(String[] args) throws ClassNotFoundException, ParserConfigurationException, IOException {
        String username = "root";
        String password = "database123";
        String connectionUrl = "jdbc:mysql://localhost:3306/mydbtest";
        try (Connection connection = DriverManager.getConnection(connectionUrl, username, password)) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("drop table Books");
            statement.executeUpdate("create table if not exists  Books (id MEDIUMINT NOT NULL AUTO_INCREMENT, name CHAR(30) NOT NULL, PRIMARY KEY (id))");
            statement.executeUpdate("insert into Books (name) values ('BookNew')");
            statement.executeUpdate("insert into Books (name) values ('SuperBook')");
            statement.executeUpdate("insert into Books (name) values ('MegaBook')");

            // множество результатов
            CallableStatement callableStatement = connection.prepareCall("{call getCount()}");
            boolean hasResults = callableStatement.execute();
            while (hasResults) {
                ResultSet resultSet = callableStatement.getResultSet();
                while (resultSet.next()) {
                    System.out.println(resultSet.getInt(1));
                }
                System.out.println("----------");
                hasResults = callableStatement.getMoreResults();
            }

        } catch (SQLException e) { e.printStackTrace(); }
    }
}
