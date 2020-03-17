import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.*;

public class CachedRowSetExample {
    static String username = "root";
    static String password = "database123";
    static String connectionUrl = "jdbc:mysql://localhost:3306/mydbtest";

    public static void main(String[] args) throws ClassNotFoundException, ParserConfigurationException, IOException, SQLException {
        ResultSet resultSet = getData();
        while (resultSet.next()) {
            System.out.println(resultSet.getInt("id") + " : " + resultSet.getString("name"));
        }
        // можно залезть в БД с помощью CachedRowSet
        /*
        CachedRowSet cachedRowSet = (CachedRowSet) resultSet;
        cachedRowSet.setUrl(connectionUrl);
        cachedRowSet.setUsername(username);
        cachedRowSet.setPassword(password);
        cachedRowSet.setCommand("select * from Books where id = ?");
        cachedRowSet.setInt(1,2);
        cachedRowSet.setPageSize(5);
        cachedRowSet.execute();
        do {
            while (cachedRowSet.next()) {
                System.out.println(resultSet.getInt("id") + " : "+ resultSet.getString("name"));
            }
        } while (cachedRowSet.nextPage());
        */
    }

    static ResultSet getData() throws ClassNotFoundException, ParserConfigurationException, IOException, SQLException {
        try (Connection connection = DriverManager.getConnection(connectionUrl, username, password)) {
            Statement newStatement = connection.createStatement();
            newStatement.execute("drop table Books");
            newStatement.executeUpdate("create table if not exists  Books (id MEDIUMINT NOT NULL AUTO_INCREMENT, name CHAR(30) NOT NULL, PRIMARY KEY (id))");
            newStatement.executeUpdate("insert into Books (name) values ('Uncanny Valley')", newStatement.RETURN_GENERATED_KEYS);
            newStatement.executeUpdate("insert into Books (name) values ('Harry Potter')", newStatement.RETURN_GENERATED_KEYS);
            newStatement.executeUpdate("insert into Books (name) values ('Martin Eden')", newStatement.RETURN_GENERATED_KEYS);

            // кэширование
            RowSetFactory factory = RowSetProvider.newFactory();
            CachedRowSet cachedRowSet = factory.createCachedRowSet();

            Statement stat = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = stat.executeQuery("select * from Books");

            cachedRowSet.populate(resultSet);
            return cachedRowSet;
        }
    }
}