package be.heh.std.epm.persistence.access;

public class PostGreSQLPersistence extends SQLikePersistence {

    public PostGreSQLPersistence(String server, String username, String password) {
        super("postgresql", "//"+server, username, password);
    }
}
