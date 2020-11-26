package be.heh.std.epm.web;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;

public class ErrorResponseEntity extends ResponseEntity<String> {

    private static Gson gson = new Gson();

    public ErrorResponseEntity(String message) {
        super(generateJson(message), HttpStatus.BAD_REQUEST);
    }

    private static String generateJson(String message) {
        JsonObject jsonObject = new JsonObject();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        jsonObject.addProperty("timestamp", now.toString());
        jsonObject.addProperty("error", message);
        return gson.toJson(jsonObject);
    }
}
