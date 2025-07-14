import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Database db = Database.getInstance();

        // Ensure schema
        db.setupSchema();

        Connection conn = db.getConnection();


        User user = new User("Ali");
        user.save(conn);

        Task t1 = new Task("Finish JDBC project", user.getId());
        Task t2 = new Task("Write README", user.getId());
        t1.save(conn);
        t2.save(conn);

        User found = User.findById(conn, user.getId());
        if (found != null) {
            System.out.println("👤 Found User: " + found.getName());
        }
    }
}
