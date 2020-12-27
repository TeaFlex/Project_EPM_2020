package be.heh.std.epm.persistence.access;

import java.io.File;
import java.sql.Statement;

public class H2Persistence extends SQLikePersistence {

    public H2Persistence () throws Exception {
        //for test purpose only
        super("h2", "mem:", "", "");
        String sqlScript = System.getProperty("user.dir") + "/src/main/resources/schema.sql";
        if(new File(sqlScript).exists()) {
            Statement s = getConnection().createStatement();
            s.execute(String.format("RUNSCRIPT from '%s';", sqlScript));
        }
    }

    public H2Persistence (String server, String username, String password) {
        super("h2", String.format("%s;AUTO_SERVER=TRUE", server), username, password);
    }

}
