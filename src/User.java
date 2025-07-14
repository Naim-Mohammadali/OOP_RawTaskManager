import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private int id;
    private String name;

    public User(String name) {
        this.name = name;
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Insert this user into DB
    public void save(Connection conn) {
        String sql = "INSERT INTO users (name) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, this.name);
            stmt.executeUpdate();

            // Get the generated ID
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.id = generatedKeys.getInt(1);
                    System.out.println("✅ User saved with ID: " + id);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ User save failed: " + e.getMessage());
        }
    }

    // Static method to fetch by ID
    public static User findById(Connection conn, int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                return new User(id, name);
            }
        } catch (SQLException e) {
            System.err.println("❌ User fetch failed: " + e.getMessage());
        }
        return null;
    }

    public int getId() { return id; }
    public String getName() { return name; }
}
