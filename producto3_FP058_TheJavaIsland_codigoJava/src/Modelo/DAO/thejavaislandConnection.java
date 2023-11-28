package Modelo.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class thejavaislandConnection {
static final String DB_URL = "jdbc:mysql://localhost:3306/theJavaIsland?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
static final String DB_USER = "root";
static final String DB_PASS = "Ianostyak2015.";

public static Connection getConnection () {
    Connection connection = null;
    try {
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
    catch (SQLException e) {
        System.out.println("Error al conectar con la base de datos: " + e.getMessage());
    }
    return connection;
    }
}
