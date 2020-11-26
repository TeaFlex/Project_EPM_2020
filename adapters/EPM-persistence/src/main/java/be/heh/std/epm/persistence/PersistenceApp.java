package be.heh.std.epm.persistence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class PersistenceApp {
    public static void main(String[] args) {
        SpringApplication.run(PersistenceApp.class, args);
    }
}
