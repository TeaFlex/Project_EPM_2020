package be.heh.std.epm.domain;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateDeserializer extends StdDeserializer<LocalDate> {
    protected LocalDateDeserializer(Class<?> vc) {
        super(vc);
    }

    public LocalDateDeserializer() {
        this(null);
    }

    @Override
    public LocalDate deserialize(com.fasterxml.jackson.core.JsonParser p, DeserializationContext ctxt) throws IOException {
        String date = p.getText();
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(date, dateTimeFormatter);
        } catch (Exception e) {
            return null;
        }
    }
}
