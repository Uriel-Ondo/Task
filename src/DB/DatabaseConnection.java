package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/task_manager";
    private static final String USER = "root"; // Remplace par ton utilisateur MySQL
    private static final String PASSWORD = ""; // Remplace par ton mot de passe MySQL
    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (ClassNotFoundException | SQLException exception) {
            exception.printStackTrace();
            throw new RuntimeException("Erreur de connexion à la base de données");
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            throw new RuntimeException("Erreur de fermeture de la connexion");
        }
    }
}