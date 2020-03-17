import java.sql.*;

public class SQLBatch {
    public static void main(String[] args) throws SQLException {

        String url = "jdbc:mysql://localhost:3306/mydbtest";
        String username = "root";
        String password = "database123";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            Statement statement = connection.createStatement();

            connection.setAutoCommit(false);
            // всё выполнится одним банчем
            statement.addBatch("drop table Books");
            statement.addBatch("create table if not exists  Books (id MEDIUMINT NOT NULL AUTO_INCREMENT, name CHAR(30) NOT NULL, PRIMARY KEY (id))");

            statement.addBatch("insert into Books (name) values ('Weather')");
            statement.addBatch("insert into Books (name) values ('Uncanny Valley')");
            statement.addBatch("insert into Books (name) values ('Harry Potter')");

            if (statement.executeBatch().length == 5) {
                connection.commit();
            } else {
                connection.rollback();
            }
        }
    }
}
