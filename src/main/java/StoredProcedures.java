import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.*;

public class StoredProcedures {
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

            CallableStatement callableStatement = connection.prepareCall("{call BooksCount(?)}");
            callableStatement.execute();
            System.out.println(callableStatement.getInt(1));
            System.out.println("----------");

            CallableStatement callableStatement2 = connection.prepareCall("{call getBooks(?)}");
            callableStatement2.setInt(1, 1);
            if (callableStatement2.execute()) {
                ResultSet resultSet = callableStatement2.getResultSet();
                while (resultSet.next()) {
                    System.out.println(resultSet.getInt("id"));
                    System.out.println(resultSet.getString("name"));
                }
            }
            System.out.println("----------");

        } catch (SQLException e) { e.printStackTrace(); }
    }
}
