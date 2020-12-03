package be.heh.std.epm.application.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class DataTimeCardTest {

    private ObjectMapper objectMapper;

    @Before
    public void init() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void dateDeserialization() throws IOException {
        String json = "{ \"date\":\"2020-11-03\", \"hours\":\"8\" }";
        DataTimeCard dataTimeCard = objectMapper.readValue(json, DataTimeCard.class);

        assertEquals(dataTimeCard.getDate(), LocalDate.of(2020, 11, 3));
    }

    @Test
    public void dateSerialization() throws IOException {
        DataTimeCard dataTimeCard = new DataTimeCard();
        dataTimeCard.setDate(LocalDate.of(2020, 11, 3));
        dataTimeCard.setHours(8);

        String json = objectMapper.writeValueAsString(dataTimeCard);
        assertTrue(json.contains("2020-11-03"));
    }
}