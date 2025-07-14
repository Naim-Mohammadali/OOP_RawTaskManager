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
    public void setupSchema() {
        try {
            // Create 'users' table
            String createUsers = """
            CREATE TABLE IF NOT EXISTS users (
              id INT AUTO_INCREMENT PRIMARY KEY,
              name VARCHAR(100)
            )
            """;

            // Create 'tasks' table
            String createTasks = """
            CREATE TABLE IF NOT EXISTS tasks (
              id INT AUTO_INCREMENT PRIMARY KEY,
              description TEXT,
              user_id INT,
              FOREIGN KEY (user_id) REFERENCES users(id)
            )
            """;

            connection.createStatement().execute(createUsers);
            connection.createStatement().execute(createTasks);

            System.out.println("✅ Tables ensured (users, tasks)");
        } catch (SQLException e) {
            System.err.println("❌ Failed to create tables: " + e.getMessage());
        }
    }

}
