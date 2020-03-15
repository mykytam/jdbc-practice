import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;

public class BlOBExample {
    public static void main(String[] args) throws ClassNotFoundException, ParserConfigurationException, IOException {
        String username = "root";
        String password = "database123";
        String connectionUrl = "jdbc:mysql://localhost:3306/mydbtest";
        try ( Connection connection = DriverManager.getConnection(connectionUrl, username, password)) {
            Statement statement = connection.createStatement();
            statement.execute("drop table Books");
            statement.executeUpdate("create table if not exists  Books (id MEDIUMINT NOT NULL AUTO_INCREMENT, name CHAR(30) NOT NULL, img BLOB, PRIMARY KEY (id))");

            BufferedImage image = ImageIO.read(new File("database.png"));
            Blob blob = connection.createBlob();

            try (OutputStream outputStream = blob.setBinaryStream(1)) {
            ImageIO.write(image, "png", outputStream); // запись картинки в blob
            }

            PreparedStatement preparedStatement = connection.prepareStatement("insert into Books (name, img) values (?, ?)");
            preparedStatement.setString(1, "database book");
            preparedStatement.setBlob(2, blob);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.executeQuery("select * from Books");
            while (resultSet.next()) {
                Blob blob2 = resultSet.getBlob("img"); // получили blob
                BufferedImage image2 = ImageIO.read(blob.getBinaryStream());
                File outputFile = new File("newSaveImage.png");
                ImageIO.write(image2, "png", outputFile);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
