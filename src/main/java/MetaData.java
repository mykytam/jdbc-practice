import java.sql.*;

public class MetaData {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/mydbtest";
        String username = "root";
        String password = "database123";

        // metadata информацию о том, как хранится информация
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            Statement statement = connection.createStatement();
            statement.execute("drop table Books");
            statement.executeUpdate("create table if not exists  Books (id MEDIUMINT NOT NULL AUTO_INCREMENT, name CHAR(30) NOT NULL, PRIMARY KEY (id))");

            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getTables(null,  null,null, new String[] {"Table"});
            while (resultSet.next()) {
                System.out.println(resultSet.getString(3));
            }
            System.out.println("-----------");

            ResultSet resultSet1 = statement.executeQuery("select * from Books");
            ResultSetMetaData resultSetMetaData = resultSet1.getMetaData();
            for (int i = 1; i < resultSetMetaData.getColumnCount(); i++) {
                System.out.println(resultSetMetaData.getColumnLabel(i));
            }

        }
    }
}
