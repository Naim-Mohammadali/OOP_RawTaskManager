import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Task {
    private int id;
    private String description;
    private int userId;

    public Task(String description, int userId) {
        this.description = description;
        this.userId = userId;
    }

    // Insert task into DB
    public void save(Connection conn) {
        String sql = "INSERT INTO tasks (description, user_id) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, this.description);
            stmt.setInt(2, this.userId);
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    this.id = keys.getInt(1);
                    System.out.println("✅ Task saved with ID: " + id);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Task save failed: " + e.getMessage());
        }
    }

    public int getId() { return id; }
    public String getDescription() { return description; }
    public int getUserId() { return userId; }
}
