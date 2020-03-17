import java.sql.*;

public class TransactionExample {
    public static void main(String[] args) throws SQLException {
        // транзакции - это набор операций, которые должны быть выполнены целостно
        // если одна не прошла, то другие отменены
        String url = "jdbc:mysql://localhost:3306/mydbtest";
        String username = "root";
        String password = "database123";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            Statement statement = connection.createStatement();

            connection.setAutoCommit(false); // операции ждут, когда их закоммитят
            statement.execute("drop table Books");
            statement.executeUpdate("create table if not exists  Books (id MEDIUMINT NOT NULL AUTO_INCREMENT, name CHAR(30) NOT NULL, PRIMARY KEY (id))");

            statement.executeUpdate("insert into Books (name) values ('Weather')");
            Savepoint savepoint = connection.setSavepoint(); // точка остановки
            statement.executeUpdate("insert into Books (name) values ('Uncanny Valley')");
            statement.executeUpdate("insert into Books (name) values ('Harry Potter')");

            connection.rollback(savepoint); // откат
            connection.commit(); // комиит того, что всё же произошло
            connection.releaseSavepoint(savepoint);
        }
    }
}
