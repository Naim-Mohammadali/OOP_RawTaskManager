import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Database instance;
    private Connection connection;

    private final String URL = "jdbc:mysql://localhost:3306/taskdb";
    private final String USER = "user";
    private final String PASSWORD = "secret";

    private Database() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // load MySQL driver
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Connected to MySQL!");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("❌ DB Connection failed: " + e.getMessage());
            System.exit(1);
        }
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
