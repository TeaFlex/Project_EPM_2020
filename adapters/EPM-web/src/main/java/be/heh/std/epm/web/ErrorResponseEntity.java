package be.heh.std.epm.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;

public class ErrorResponseEntity extends ResponseEntity<String> {

    public ErrorResponseEntity(String message) {
        super(generateJson(message), HttpStatus.BAD_REQUEST);
    }

    private static String generateJson(String message) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        return String.format("{ \"timestamp\":\"%s\", \"error\":\"%s\" }", now.toString(), message);
    }
}
