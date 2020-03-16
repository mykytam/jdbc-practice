import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.*;

public class UpdateResultSet {
    public static void main(String[] args) throws ClassNotFoundException, ParserConfigurationException, IOException {
        String username = "root";
        String password = "database123";
        String connectionUrl = "jdbc:mysql://localhost:3306/mydbtest";
        try (Connection connection = DriverManager.getConnection(connectionUrl, username, password)) {
            Statement statement = connection.createStatement();
            statement.execute("drop table Books");
            statement.executeUpdate("create table if not exists  Books (id MEDIUMINT NOT NULL AUTO_INCREMENT, name CHAR(30) NOT NULL, PRIMARY KEY (id))");
            statement.executeUpdate("insert into Books (name) values ('Had I Known')");
            statement.executeUpdate("insert into Books (name) values ('Weather')");
            statement.executeUpdate("insert into Books (name) values ('Uncanny Valley')");
            statement.executeUpdate("insert into Books (name) values ('Harry Potter')");
            statement.executeUpdate("insert into Books (name) values ('Martin Eden')");

            // изменение данных на лету
            Statement newStatement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
           //PreparedStatement preparedStatement = connection.prepareStatement("", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = newStatement.executeQuery("select * from Books");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id") + " : "+ resultSet.getString("name"));
            }

            System.out.println("------------------------");

            // изменение имени в последней записи
            resultSet.last();
            resultSet.updateString("name", "newValue");
            resultSet.updateRow();

            // добавление новой строки
            resultSet.moveToInsertRow();
            resultSet.updateString("name", "insertedRow");
            resultSet.insertRow();

            // удаление записи
            resultSet.absolute(2);
            resultSet.deleteRow();

            resultSet.beforeFirst();
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id") + " : "+ resultSet.getString("name"));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
