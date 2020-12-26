package be.heh.std.epm.web;

import be.heh.std.epm.application.service.*;
import be.heh.std.epm.application.port.out.OutPersistence;
import be.heh.std.epm.domain.PayCheck;
import be.heh.std.epm.persistence.access.H2Persistence;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {

    private OutPersistence db;

    public EmployeeController() {
        db = new H2Persistence("file:./h2DBs/mydb", "user", "123");
    }

    private ResponseEntity<String> executeWriteOperation(WriteOperation writeOperation) {
        try {
            writeOperation.execute(db);
        } catch (Exception e) {
            return new ErrorResponseEntity(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/employee/commissioned", consumes =  MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> addCommissionedEmployee(@RequestBody AddCommissionEmployee addCommissionEmployee) {
        return executeWriteOperation(addCommissionEmployee);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/employee/hourly", consumes =  MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> addHourlyEmployee(@RequestBody AddHourlyEmployee addHourlyEmployee) {
        return executeWriteOperation(addHourlyEmployee);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/employee/salaried", consumes =  MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> addSalariedEmployee(@RequestBody AddSalariedEmployee addSalariedEmployee) {
        return executeWriteOperation(addSalariedEmployee);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping(value = "/employee", consumes =  MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> deleteEmployee(@RequestBody DeleteEmployee deleteEmployee) {
        return executeWriteOperation(deleteEmployee);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/timecard", consumes =  MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> postTimeCard(@RequestBody PostTimeCard postTimeCard) {
        return executeWriteOperation(postTimeCard);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/salesreceipt", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> postSalesReceipt(@RequestBody PostReceipt postReceipt) {
        return executeWriteOperation(postReceipt);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/payday", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> payday(@RequestBody PayDay payDay) {
        try {
            PayCheck payCheck = payDay.payday(db);
            ObjectMapper objectMapper = new ObjectMapper();
            return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(payCheck));
        } catch (Exception e) {
            return new ErrorResponseEntity(e.getMessage());
        }
    }
}
